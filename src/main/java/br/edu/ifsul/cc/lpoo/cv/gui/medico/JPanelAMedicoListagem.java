package br.edu.ifsul.cc.lpoo.cv.gui.medico;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.gui.medico.JPanelAMedico;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Jakelyny Sousa
 */

public class JPanelAMedicoListagem extends JPanel implements ActionListener{
    
    private JPanelAMedico pnlAMedico;
    private Controle controle;
    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txfFiltro;
    private JButton btnFiltro;
    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;
    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnAlterar;
    private JButton btnRemover;
    private SimpleDateFormat format;
    
    
    public JPanelAMedicoListagem(JPanelAMedico pnlAMedico, Controle controle){
        
        this.pnlAMedico = pnlAMedico;
        this.controle = controle;
        
        initComponents();
    }
    
    
    public void populaTable(){
        
        DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel();//recuperacao do modelo da tabela

        model.setRowCount(0);//elimina as linhas existentes (reset na tabela)

        try {

            List<Medico> listMedicos = controle.getConexaoJDBC().listMedicos();
            for(Medico m : listMedicos){
                if(txfFiltro.getText().equals("")){
                    model.addRow(new Object[]{m, m.getNome(), format.format(m.getData_cadastro().getTime()), m.getNumero_crmv()});
                }else if(txfFiltro.getText().equals(m.getCpf())){
                    model.addRow(new Object[]{m, m.getNome(), format.format(m.getData_cadastro().getTime()), m.getNumero_crmv()});
                }
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Medicos -:"+ex.getLocalizedMessage(), "Medicos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }        
        
    }
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);//seta o gerenciado border para este painel
        
        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());
        
        lblFiltro = new JLabel("Filtrar por CPF:");
        pnlNorte.add(lblFiltro);
        
        txfFiltro = new JTextField(20);
        pnlNorte.add(txfFiltro);
        
        btnFiltro = new JButton("Filtrar");
        btnFiltro.addActionListener(this);
        btnFiltro.setFocusable(true);    //acessibilidade    
        btnFiltro.setToolTipText("btnFiltrar"); //acessibilidade  
        btnFiltro.setActionCommand("botao_filtro");
        pnlNorte.add(btnFiltro);
        
        this.add(pnlNorte, BorderLayout.NORTH);//adiciona o painel na posicao norte.
        
        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());
        
            
        scpListagem = new JScrollPane();
        tblListagem =  new JTable();
        
        modeloTabela = new DefaultTableModel(
            new String [] {
                "CPF", "Médico", "Data Cadastro", "Número CRMV",
            }, 0){
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };
        
        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);
    
        pnlCentro.add(scpListagem, BorderLayout.CENTER);
    
        
        this.add(pnlCentro, BorderLayout.CENTER);//adiciona o painel na posicao norte.
        
        
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setFocusable(true);    //acessibilidade    
        btnNovo.setToolTipText("btnNovo"); //acessibilidade
        btnNovo.setMnemonic(KeyEvent.VK_N);
        btnNovo.setActionCommand("botao_novo");
        
        pnlSul.add(btnNovo);
        
        btnAlterar = new JButton("Editar");
        btnAlterar.addActionListener(this);
        btnAlterar.setFocusable(true);    //acessibilidade    
        btnAlterar.setToolTipText("btnAlterar"); //acessibilidade
        btnAlterar.setActionCommand("botao_alterar");
        
        pnlSul.add(btnAlterar);
        
        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true);    //acessibilidade    
        btnRemover.setToolTipText("btnRemvoer"); //acessibilidade
        btnRemover.setActionCommand("botao_remover");
        
        pnlSul.add(btnRemover);//adiciona o botao na fila organizada pelo flowlayout
        
        
        this.add(pnlSul, BorderLayout.SOUTH);//adiciona o painel na posicao norte.
      
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    
        if(e.getActionCommand().equals(btnNovo.getActionCommand())){
            
            pnlAMedico.showTela("tela_medico_formulario");            
            
            pnlAMedico.getFormulario().setMedicoFormulario(null); //limpando o formulário.                        
            
        }else if(e.getActionCommand().equals(btnAlterar.getActionCommand())){
            
            
            int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){

                DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table

                Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada

                Medico m = (Medico) linha.get(0); 

                pnlAMedico.showTela("tela_medico_formulario");
                pnlAMedico.getFormulario().setMedicoFormulario(m); 

            }else{
                  JOptionPane.showMessageDialog(this, "Selecione uma linha para editar!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        }else if(e.getActionCommand().equals(btnRemover.getActionCommand())){
            
            
            int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){

                DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table

                Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada

                Medico m = (Medico) linha.get(0); 

                try {
                    pnlAMedico.getControle().getConexaoJDBC().remover(m);
                    JOptionPane.showMessageDialog(this, "Medico removido!", "Medico", JOptionPane.INFORMATION_MESSAGE);
                    populaTable(); //refresh na tabela

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover Medico -:"+ex.getLocalizedMessage(), "Medicoes", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }                        

            }else{
                  JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
            }
 
        }else if(e.getActionCommand().equals(btnFiltro.getActionCommand())){
            populaTable();
        }
    
    
    }
}
