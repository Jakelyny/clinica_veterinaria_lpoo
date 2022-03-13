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
    
    @Test //execução para recriar tabelas
    public void testConexaoGeracaoTabelas(){ 
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
    }
    //insert dos funcionários e médicos estão em JDBC
    // @Test
    public void testGeracaoPessoaLogin() throws Exception {

        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");

            Pessoa p = persistencia.doLogin("123456789", "12345");

            if(p == null){
                p = new Pessoa();
                p.setCpf("123456789");
                p.setSenha("12345");

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
