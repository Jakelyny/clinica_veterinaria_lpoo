package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * @author Jakelyny Sousa
 */
public class PersistenciaJDBC implements InterfacePersistencia {
    
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "nini";
    public static final String URL = "jdbc:postgresql://localhost:5432/clinica_veterinaria";
    private Connection con = null;

    public PersistenciaJDBC () throws Exception {
        
        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");
            
        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA); 
        
    }
    
    @Override
    public Boolean conexaoAberta() {
        
        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return false;
        
    }

    @Override
    public void fecharConexao() {        
        
        try{                               
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        }catch(SQLException e){            
            e.printStackTrace();//gera uma pilha de erro na saida.
        } 
        
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        
        if(c == Pessoa.class){
            
            PreparedStatement ps = this.con.prepareStatement("select cpf, rg, nome, senha, numero_celular, email, data_cadastro, data_nascimento, cep, endereco, complemento from tb_pessoa where cpf = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            
                Pessoa p = new Pessoa();
                p.setCpf(rs.getString("cpf"));
                p.setRg(rs.getString("rg"));
                p.setNome(rs.getString("nome"));
                p.setSenha(rs.getString("senha"));
                p.setNumero_celular(rs.getString("numero_celular"));
                p.setEmail(rs.getString("email"));
                Calendar dc = Calendar.getInstance();
                dc.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                p.setData_cadastro(dc);
                Calendar dn = Calendar.getInstance();
                dn.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                p.setData_nascimento(dn);
                p.setCep(rs.getString("cep"));
                p.setEndereco(rs.getString("endereco"));
                p.setComplemento(rs.getString("complemento"));
                
                ps.close();
                
                return p;                
            }
            
        }else if(c == Medico.class){
           
            PreparedStatement ps = this.con.prepareStatement("select cpf, numero_crmv from tb_medico where cpf = ?");
            
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
            
                Medico m = new Medico();
                m.setCpf(rs.getString("cpf"));
                m.setNumero_crmv(rs.getString("numero_crmv"));
                               
                ps.close();
                
                return m;                
            }            
        }        
        
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
