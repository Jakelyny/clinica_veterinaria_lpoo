package br.edu.ifsul.cc.lpoo.cv.test;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJPA;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Jakelyny Sousa
 */
public class TestPersistenciaJPA {
    
    @Test
    public void testConexaoGeracaoTabelas(){
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
    }
    
     @Test
    public void testGeracaoPessoaLogin() throws Exception {

        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");

            Pessoa p = persistencia.doLogin("teste@", "1234");

            if(p == null){
                p = new Pessoa();
                p.setNome("teste@");
                p.setSenha("1234");

                persistencia.persist(p);
                System.out.println("Cadastrou nova pessoa!");
            }else{
                System.out.println("Encontrou pessoa cadastrada!");
            }


            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }

    }
}
