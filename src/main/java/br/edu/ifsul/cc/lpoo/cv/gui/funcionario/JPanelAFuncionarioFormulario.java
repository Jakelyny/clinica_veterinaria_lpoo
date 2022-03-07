package br.edu.ifsul.cc.lpoo.cv.gui.funcionario;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Jakelyny Sousa
 */
public class JPanelAFuncionarioFormulario extends JPanel implements ActionListener{
    
    
    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;
    private BorderLayout borderLayout;
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblCpf;
    private JTextField txfCpf;
    private JLabel lblRg;
    private JTextField txfRg;
    private JLabel lblCep;
    private JTextField txfCep;
    private JLabel lblComplemento;
    private JTextField txfComplemento;
    private JLabel lblData_nascimento;
    private JTextField txfData_nascimento;
    private JLabel lblEmail;
    private JTextField txfEmail;
    private JLabel lblEndereco;
    private JTextField txfEndereco;
    private JLabel lblNome;
    private JTextField txfNome;
    private JLabel lblNumero_celular;
    private JTextField txfNumero_celular;
    private JLabel lblCargo;
    private JComboBox txfCargo;
    private JLabel lblNumero_pis;
    private JTextField txfNumero_pis;
    private JLabel lblNumero_ctps;
    private JTextField txfNumero_ctps;
    private JLabel lblSenha;
    private JPasswordField txfSenha;
    private Calendar dataCadastroFunc;
    private Funcionario funcionario;
    private SimpleDateFormat format;
    private JTabbedPane tbpAbas;
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    private JPanel pnlDadosFuncionario;
    
    public JPanelAFuncionarioFormulario(JPanelAFuncionario pnlAFuncionario, Controle controle){
        
        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;
        
        initComponents();
        
    }

    
    public Funcionario getFuncionariobyFormulario() throws ParseException{
        
        //validacao do formulario
         if(txfCpf.getText().trim().length() > 4 && 
            new String(txfSenha.getPassword()).trim().length() > 3 && 
            txfCargo.getSelectedIndex() > 0){

            Funcionario f = new Funcionario();
            f.setCpf(txfCpf.getText().trim());            
            f.setSenha(new String(txfSenha.getPassword()).trim());
            f.setCargo((Cargo) txfCargo.getSelectedItem());
            f.setNumero_ctps(txfNumero_ctps.getText().trim());
            f.setNumero_pis(txfNumero_pis.getText().trim());
            f.setCep(txfCep.getText().trim());
            f.setComplemento(txfComplemento.getText().trim());
            f.setEndereco(txfEndereco.getText().trim());
            f.setRg(txfRg.getText().trim());
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(txfData_nascimento.getText()));
            f.setData_nascimento(c);
            f.setEmail(txfRg.getText().trim());
            f.setNome(txfNome.getText().trim());
            f.setNumero_celular(txfNumero_celular.getText().trim());
            f.setCep(txfCep.getText().trim());
            f.setTipo("F");
            if(funcionario != null)
                f.setData_cadastro(funcionario.getData_cadastro());

            return f;
         }

         return null;
    }
    
    public void setFuncionarioFormulario(Funcionario f){

        if(f == null){ //Se o parametro estiver nullo, limpa o formulario
            txfCpf.setText("");
            txfSenha.setText("");
            txfCargo.setSelectedIndex(0);
            txfNumero_ctps.setText("");
            txfNumero_pis.setText("");
            txfCep.setText("");
            txfComplemento.setText("");
            txfEndereco.setText("");
            txfRg.setText("");
            txfData_nascimento.setText("");
            txfEmail.setText("");
            txfNome.setText("");
            txfNumero_celular.setText("");
            txfCep.setText("");
            txfCpf.setEditable(true);
            funcionario = null;

        }else{ //Se não, ele exibe 
            
            funcionario = f;
            txfCpf.setEditable(false);
            txfCpf.setText(funcionario.getCpf());
            txfSenha.setText(funcionario.getSenha());
            txfCargo.getModel().setSelectedItem(funcionario.getCargo());
            txfNumero_ctps.setText(funcionario.getNumero_ctps());
            txfNumero_pis.setText(funcionario.getNumero_pis());
            txfCep.setText(funcionario.getCep());
            txfComplemento.setText(funcionario.getComplemento());
            txfEndereco.setText(funcionario.getEndereco());
            txfRg.setText(funcionario.getRg());
            txfData_nascimento.setText(format.format(f.getData_nascimento().getTime()));
            txfEmail.setText(funcionario.getEmail());
            txfNome.setText(funcionario.getNome());
            txfNumero_celular.setText(funcionario.getNumero_celular());
            txfCep.setText(funcionario.getCep());
            
        }

    }
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);
        
        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);
        
        lblCpf = new JLabel("CPF:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblCpf, posicionador);//o add adiciona o rotulo no painel

        txfCpf = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfCpf, posicionador);//o add adiciona o rotulo no painel

        lblCargo = new JLabel("Cargo:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblCargo, posicionador);//o add adiciona o rotulo no painel

        txfCargo = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        txfCargo.addItem(Cargo.ADESTRADOR);
        txfCargo.addItem(Cargo.ATENDENTE);
        txfCargo.addItem(Cargo.AUXILIAR_VETERINARIO);
        pnlDadosCadastrais.add(txfCargo, posicionador);//o add adiciona o rotulo no painel

        lblNumero_ctps = new JLabel("CTPS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblNumero_ctps, posicionador);//o add adiciona o rotulo no painel

        txfNumero_ctps = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfNumero_ctps, posicionador);//o add adiciona o rotulo no painel

        lblNumero_pis = new JLabel("PIS:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblNumero_pis, posicionador);//o add adiciona o rotulo no painel

        txfNumero_pis = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfNumero_pis, posicionador);//o add adiciona o rotulo no painel

        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblSenha, posicionador);//o add adiciona o rotulo no painel

        txfSenha = new JPasswordField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfSenha, posicionador);//o add adiciona o rotulo no painel

        lblRg = new JLabel("RG:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblRg, posicionador);//o add adiciona o rotulo no painel

        txfRg = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfRg, posicionador);//o add adiciona o rotulo no painel

        lblData_nascimento = new JLabel("Data de Nacimento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblData_nascimento, posicionador);//o add adiciona o rotulo no painel

        txfData_nascimento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfData_nascimento, posicionador);//o add adiciona o rotulo no painel

        lblEmail = new JLabel("Email:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblEmail, posicionador);//o add adiciona o rotulo no painel

        txfEmail = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfEmail, posicionador);//o add adiciona o rotulo no painel

        lblNome = new JLabel("Nome:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblNome, posicionador);//o add adiciona o rotulo no painel

        txfNome = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfNome, posicionador);//o add adiciona o rotulo no painel

        lblNumero_celular = new JLabel("Numero Celular:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblNumero_celular, posicionador);//o add adiciona o rotulo no painel

        txfNumero_celular = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfNumero_celular, posicionador);//o add adiciona o rotulo no painel

        lblCep = new JLabel("Cep:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblCep, posicionador);//o add adiciona o rotulo no painel

        txfCep = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfCep, posicionador);//o add adiciona o rotulo no painel

        lblComplemento = new JLabel("Complemento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblComplemento, posicionador);//o add adiciona o rotulo no painel

        txfComplemento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfComplemento, posicionador);//o add adiciona o rotulo no painel

        lblEndereco = new JLabel("Endereco:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;//posicao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblEndereco, posicionador);//o add adiciona o rotulo no painel

        txfEndereco = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 12;//posicao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfEndereco, posicionador);//o add adiciona o rotulo no painel

        tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);
        
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    //acessibilidade    
        btnGravar.setToolTipText("btnGravarFuncionario"); //acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_funcionario");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);    //acessibilidade    
        btnCancelar.setToolTipText("btnCancelarFuncionario"); //acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_funcionario");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if(e.getActionCommand().equals(btnGravar.getActionCommand())){//testa para verificar se o botão btnLogar foi clicado.
            
            Funcionario f = null;
            try {
                f = getFuncionariobyFormulario(); //recupera os dados do formulario
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de dado inválido", "Formulário", JOptionPane.INFORMATION_MESSAGE);
            }
            
            if(f != null){

                try {
                    
                    pnlAFuncionario.getControle().getConexaoJDBC().persist(f);
                    
                    JOptionPane.showMessageDialog(this, "Funcionario armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    
                    pnlAFuncionario.showTela("tela_funcionario_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Funcionario! : "+ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            }else{

                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        }else if(e.getActionCommand().equals(btnCancelar.getActionCommand())){
            
            
                pnlAFuncionario.showTela("tela_funcionario_listagem");
            
        }
    }
}
