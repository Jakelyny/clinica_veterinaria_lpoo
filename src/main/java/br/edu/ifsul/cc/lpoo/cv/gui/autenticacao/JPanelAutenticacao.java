package br.edu.ifsul.cc.lpoo.cv.gui.autenticacao;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Jakelyny Sousa
 */
public class JPanelAutenticacao extends JPanel implements ActionListener {

    private Controle controle;
    private GridBagLayout gridLayout;
    private GridBagConstraints posicionador;

    private JLabel  lblNickname;
    private JLabel lblSenha;
    private JTextField txfNickname;
    private JPasswordField psfSenha;
    private JButton btnLogar;

    //construtor da classe que recebe um parametro.
    public JPanelAutenticacao(Controle controle){

        this.controle = controle;
        initComponents();
    }

    private void initComponents(){

        gridLayout = new GridBagLayout();//inicializando o gerenciador de layout
        this.setLayout(gridLayout);//definie o gerenciador para este painel.

        lblNickname = new JLabel("Nickname:");
        lblNickname.setFocusable(true);    //acessibilidade    
        lblNickname.setToolTipText("lblNickname"); //acessibilidade
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        this.add(lblNickname, posicionador);//o add adiciona o rotulo no painel

        txfNickname = new JTextField(10);
        txfNickname.setFocusable(true);    //acessibilidade    
        txfNickname.setToolTipText("txfNome"); //acessibilidade
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        this.add(txfNickname, posicionador);//o add adiciona o rotulo no painel        

        lblSenha = new JLabel("Senha:");
        lblSenha.setFocusable(true);    //acessibilidade    
        lblSenha.setToolTipText("lblSenha"); //acessibilidade

        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        this.add(lblSenha, posicionador);//o add adiciona o rotulo no painel

        psfSenha = new JPasswordField(10);
        psfSenha.setFocusable(true);    //acessibilidade    
        psfSenha.setToolTipText("psfSenha"); //acessibilidade        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        this.add(psfSenha, posicionador);//o add adiciona o rotulo no painel  

        btnLogar = new JButton("Autenticar");
        btnLogar.setFocusable(true);    //acessibilidade    
        btnLogar.setToolTipText("btnLogar"); //acessibilidade        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        btnLogar.addActionListener(this);//registrar o botão no Listener
        btnLogar.setActionCommand("comando_autenticar");
        this.add(btnLogar, posicionador);//o add adiciona o rotulo no painel

    }


    @Override
    public void actionPerformed(ActionEvent e) {

            //testa para verificar se o botão btnLogar foi clicado.
            if(e.getActionCommand().equals(btnLogar.getActionCommand())){

                //validacao do formulario.
                if(txfNickname.getText().trim().length() > 4 && new String(psfSenha.getPassword()).trim().length() > 3 ){

                    controle.autenticar(txfNome.getText().trim(), new String(psfSenha.getPassword()).trim());

                }else{

                    JOptionPane.showMessageDialog(this, "Informe os dados para CPF e Senha!", "Autenticação", JOptionPane.ERROR_MESSAGE);

                }

        } 

    }

}
