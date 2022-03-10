package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cv.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cv.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cv.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cv.gui.funcionario.JPanelAFuncionario;
import br.edu.ifsul.cc.lpoo.cv.gui.medico.JPanelAMedico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import javax.swing.JOptionPane;

/**
 *
 * @author Jakelyny Sousa
 */

public class Controle {

    private PersistenciaJDBC conexaoJDBC;

    private JFramePrincipal frame; // frame principal da minha aplicação gráfica
    
    private JPanelAutenticacao pnlAutenticacao; //painel para a autenticacao do Jogador.

    private JMenuBarHome menuBar; //menu principal
    
    private JPanelAFuncionario pnlFuncionario;
        
    private JPanelAMedico pnlMedico;


    private JPanelHome pnlHome; // paine de boas vindas (home)


    //construtor.
    public Controle(){

    }

    public boolean conectarBD() throws Exception {

            conexaoJDBC = new PersistenciaJDBC();

            if(conexaoJDBC!= null){

                return conexaoJDBC.conexaoAberta();
            }

            return false;
    }

    public void fecharBD(){

        System.out.println("Fechando conexao com o Banco de Dados");
        conexaoJDBC.fecharConexao();

    }

    public void initComponents(){


        //inicia a interface gráfica.
        //"caminho feliz" : passo 5

        frame = new JFramePrincipal();
        
        pnlAutenticacao = new JPanelAutenticacao(this);
        
        pnlFuncionario = new JPanelAFuncionario(this);
                
        pnlMedico = new JPanelAMedico(this);

        menuBar = new JMenuBarHome(this);

        pnlHome = new JPanelHome(this);

        frame.addTela(pnlAutenticacao, "tela_autenticacao");//carta 1
        frame.addTela(pnlHome, "tela_home");//carta 2
        frame.addTela(pnlFuncionario, "tela_funcionario");
        frame.addTela(pnlMedico, "tela_medico");

        frame.showTela("tela_autenticacao"); // apreseta a carta cujo nome é "tela_autenticacao"

        frame.setVisible(true); // torna visível o jframe

    }
    
    public void autenticar(String cpf, String senha) {

        try{

            Pessoa p =  conexaoJDBC.doLogin(cpf, senha);

            if(p != null){

                JOptionPane.showMessageDialog(pnlAutenticacao, "O usuário "+p.getCpf()+" foi autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);

                frame.setJMenuBar(menuBar);//adiciona o menu de barra no frame
                frame.showTela("tela_home");//muda a tela para o painel de boas vindas (home)

            }else{

                JOptionPane.showMessageDialog(pnlAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(pnlAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    public void showTela(String nomeTela){


        frame.showTela(nomeTela);
    }
    
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }
}

