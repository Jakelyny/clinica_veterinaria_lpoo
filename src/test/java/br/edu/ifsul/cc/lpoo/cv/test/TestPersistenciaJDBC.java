package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Jakelyny Sousa
 */
public class TestPersistenciaJDBC {
    
    @Test
    public void testConexao() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");

            persistencia.fecharConexao();

        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }

    }

    @Test
    public void testListPersistenciaMedico() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            
            List<Medico> lista = persistencia.listMedicos();
            
            if(!lista.isEmpty()){
            
                for(Medico m : lista){
                    System.out.println("\n-- Médico -- \n");
                    System.out.println("Cpf: "+m.getCpf()+ " \nNumero CRMV: "+m.getNumero_crmv() + " \nData de Cadastro: "+m.getData_cadastro_medico());
                    
                    persistencia.remover(m);
                }
            }else{
                
                System.out.println("NÃO encontrou o médico");
                
                Medico m = new Medico();
                m.setCpf("091.536.910-91");
                m.setNumero_crmv("1111");    
                Calendar data_cadastro_m = Calendar.getInstance();
                data_cadastro_m.set(2022,02,04);
                m.setData_cadastro_medico(data_cadastro_m);
                
                persistencia.persist(m); //insert na tabela.                
                System.out.println("Cadastrou o medico do CPF "+m.getCpf());

                m = new Medico();//reset com a nova instancia que é gerada aqui.
                m.setCpf("748.995.640-40");
                m.setNumero_crmv("2222");    
                Calendar data_cadastro_me = Calendar.getInstance();
                data_cadastro_me.set(2022,05,06);
                m.setData_cadastro_medico(data_cadastro_me);
                
                persistencia.persist(m); //insert na tabela.
                System.out.println("Cadastrou o medico do CPF "+m.getCpf());
                
            }
            
            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }
}
