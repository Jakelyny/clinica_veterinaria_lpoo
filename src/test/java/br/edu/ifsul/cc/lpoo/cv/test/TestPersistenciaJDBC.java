package br.edu.ifsul.cc.lpoo.cv.test;

import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
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
    public void testListPersistenciaPessoa() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            
            List<Pessoa> lista = persistencia.listPessoas();
            
            if(!lista.isEmpty()){
            
                for(Pessoa p : lista){
                    System.out.println("\n---- Pessoa ---- \n");
                    System.out.println("CPF: "+p.getCpf()+ " \nRG: "+p.getRg() + " \nNome: "+p.getNome()
                    + " \nSenha: "+p.getSenha() + " \nNúmero do celular: "+p.getNumero_celular() + " \nEmail: "+p.getEmail()
                    + " \nData do cadastro: "+p.getData_cadastro() + " \nData de nascimento: "+p.getData_nascimento() 
                    + " \nCep: "+p.getCep() + " \nEndereço: "+p.getEndereco() + " \nComplemento: "+p.getComplemento());
                    
                    persistencia.remover(p);
                }
            }else{
                
                System.out.println("NÃO encontrou a pessoa");
                
                Pessoa p = new Pessoa();
                p.setCpf("958.519.932-78");
                p.setTipo("M");
                p.setRg("37.269.510-3");   
                p.setNome("Milena Rosâ"); 
                p.setSenha("caNQupXSDT"); 
                p.setNumero_celular("(27) 2633-4800"); 
                p.setEmail("milenaro@carvajal.com");               
                p.setData_nascimento(Calendar.getInstance());
                p.setCep("29203-510"); 
                p.setEndereco("Rua Icó, 379 Guarapari SP");
                p.setComplemento("Coroado");
                
                persistencia.persist(p); //insert na tabela.                
                System.out.println("Cadastrou a pessoa do CPF "+p.getCpf());

                p = new Pessoa();//reset com a nova instancia que é gerada aqui.
                p.setCpf("058.341.539-33");
                p.setTipo("M");
                p.setRg("18.557.554-7");   
                p.setNome("Lucca Gustavo da Mata"); 
                p.setSenha("5748"); 
                p.setNumero_celular("(86) 98633-9936"); 
                p.setEmail("luccagustavodamata-86@bakerhughes.com");               
                p.setData_nascimento(Calendar.getInstance());
                p.setCep("64028-675");  
                p.setEndereco("Rua Acesita, 538 Santa Cruz PI");
                p.setComplemento("Teresina");
                
                persistencia.persist(p); //insert na tabela.
                System.out.println("Cadastrou a pessoa do CPF "+p.getCpf());
                
            }
            
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
                    System.out.println("\n---- Médico ---- \n");
                    System.out.println("CPF: "+m.getCpf()+ " \nNumero CRMV: "+m.getNumero_crmv() + " \nData de Cadastro: "+m.getData_cadastro_medico());
                    
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
