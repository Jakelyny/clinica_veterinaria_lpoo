package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Pessoa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

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
            
            PreparedStatement ps = this.con.prepareStatement("select cpf, rg, nome, senha, numero_celular, email,"
                    + " data_cadastro, data_nascimento, cep, endereco, complemento from tb_pessoa where cpf = ? ");
            
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
                Calendar dm = Calendar.getInstance();
                dm.setTimeInMillis(rs.getDate("data_cadastro_medico").getTime());
                m.setData_cadastro(dm);
                               
                ps.close();
                return m;                
            }            
        }        
        
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        
        //descobrir a instancia do Object 
         if (o instanceof Pessoa) {

            Pessoa p = (Pessoa) o;

            if (p.getData_cadastro() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                        + "(data_cadastro, cpf, rg, nome, senha, numero_celular, email, data_nascimento, cep, endereco, "
                        + "complemento, tipo) values " + "(now(),?,?,?,?,?,?,?,?,?,?,?)");

                ps.setString(1, p.getCpf());
                ps.setString(2, p.getRg());
                ps.setString(3, p.getNome());
                ps.setString(4, p.getSenha());
                ps.setString(5, p.getNumero_celular());
                ps.setString(6, p.getEmail());
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(p.getData_nascimento().getTimeInMillis());
                ps.setDate(7, dtU);
                ps.setString(8, p.getCep());
                ps.setString(9, p.getEndereco());
                ps.setString(10, p.getComplemento());
                ps.setString(11, p.getTipo());

                System.out.println("Insert em Pessoa");               
                ps.executeUpdate();
            } else {
                
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set "
                        + "rg = ?, " + "nome = ?, " + "senha = ?, " + "numero_celular = ?, " + "email = ?, "
                        + "data_nascimento = ?, " + "cep = ?, " + "endereco = ?, " + "complemento = ? "
                        + "where cpf = ?");
                
                ps.setString(1, p.getRg());
                ps.setString(2, p.getNome());
                ps.setString(3, p.getSenha());
                ps.setString(4, p.getNumero_celular());
                ps.setString(5, p.getEmail());
                Date dn = new Date(System.currentTimeMillis());
                dn.setTime(p.getData_nascimento().getTimeInMillis());
                ps.setDate(6, (java.sql.Date) dn);
                ps.setString(7, p.getCep());
                ps.setString(8, p.getEndereco());
                ps.setString(9, p.getComplemento());
                ps.setString(10, p.getCpf());

                
                System.out.println("Update em Pessoa");               
                ps.execute();
            }

        } else if (o instanceof Medico) {
            Medico m = (Medico) o;

             if (m.getData_cadastro_medico() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_medico "
                        + "(data_cadastro_medico, cpf, numero_crmv) values " + "(now(),?,?)");
                ps.setString(1, m.getCpf());
                ps.setString(2, m.getNumero_crmv());
                System.out.println("Insert Medico");
                
                ps.executeUpdate();
            } else {

                PreparedStatement ps = this.con.prepareStatement("update tb_medico set "
                        +  "cpf = ? " + "numero_crmv = ?, " + "where data_cadastro_medico = ?");
                ps.setString(1, m.getCpf());
                ps.setString(2, m.getNumero_crmv());
                Date dc = null;
                dc.setTime(m.getData_cadastro_medico().getTimeInMillis());
                ps.setDate(3, dc);
                System.out.println("Update Medico");

                ps.execute();
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        
        if(o instanceof Pessoa){

            Pessoa p = (Pessoa) o; //converter o para o e que é 
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps.setString(1, p.getCpf());            
            ps.execute();            
            
        }else if(o instanceof Medico){
            
            Medico m = (Medico) o; //converter o para o e que é    
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_medico where cpf = ?");
            ps.setString(1, m.getCpf());            
            ps.execute(); 
        } 
    }
    
    public List<Pessoa> listPessoas() throws Exception {
        
        List<Pessoa> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select cpf, rg, nome, senha, numero_celular, email,"
                    + " data_cadastro, data_nascimento, cep, endereco, complemento from tb_pessoa");
        
        ResultSet rs = ps.executeQuery();//executa a query        
      
        lista = new ArrayList();
        while(rs.next()){
            
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
            
            lista.add(p);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.
            
        }                
        return lista;
    }
    
    public List<Medico> listMedicos() throws Exception {
        
        List<Medico> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select cpf, numero_crmv from tb_medico");
        
        ResultSet rs = ps.executeQuery();//executa a query        
      
        lista = new ArrayList();
        while(rs.next()){
            
            Medico m = new Medico();
            m.setCpf(rs.getString("cpf"));
            m.setNumero_crmv(rs.getString("numero_crmv"));
            Calendar dc = null;
            dc.setTimeInMillis(rs.getDate("data_cadastro_medico").getTime());
            m.setData_cadastro(dc);
            
            lista.add(m);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.
            
        }                
        return lista;
    }
}
