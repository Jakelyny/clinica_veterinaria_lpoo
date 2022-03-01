package br.edu.ifsul.cc.lpoo.cv;

import br.edu.ifsul.cc.lpoo.cv.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;

/**
 *
 * @author Jakelyny Sousa
 */

public class Controle {

    private PersistenciaJDBC conexaoJDBC;

    private JFramePrincipal frame; // frame principal da minha aplicação gráfica


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

        frame.setVisible(true); // torna visível o jframe

    }
}

