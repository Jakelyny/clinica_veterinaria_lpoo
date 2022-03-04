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

    private JLabel  lblNome;
    private JLabel lblSenha;
    private JTextField txfNome;
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

        lblNome = new JLabel("Nome:");
        lblNome.setFocusable(true);    //acessibilidade    
        lblNome.setToolTipText("lblNome"); //acessibilidade
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        this.add(lblNome, posicionador);//o add adiciona o rotulo no painel

        txfNome = new JTextField(10);
        txfNome.setFocusable(true);    //acessibilidade    
        txfNome.setToolTipText("txfNome"); //acessibilidade
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        this.add(txfNome, posicionador);//o add adiciona o rotulo no painel        

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
            if(txfNome.getText().trim().length() > 4 && new String(psfSenha.getPassword()).trim().length() > 3 ){

                controle.autenticar(txfNome.getText().trim(), new String(psfSenha.getPassword()).trim());

            } 
            else if(txfNome.getText().trim().length() == 0){
                JOptionPane.showMessageDialog(this, "Necessário inserir o nome!");
            }
            else if(new String(psfSenha.getPassword()).trim().length() == 0){
                JOptionPane.showMessageDialog(this, "Necessário inserir a senha!");

            }
            else{

                JOptionPane.showMessageDialog(this, "Informações inválidas, verifique-as e tente novamente!", "Autenticação", JOptionPane.ERROR_MESSAGE);

            }

        } 

    }

}
