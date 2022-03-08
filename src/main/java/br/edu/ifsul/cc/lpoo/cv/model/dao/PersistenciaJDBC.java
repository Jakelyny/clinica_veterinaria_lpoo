package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
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
                               
                ps.close();
                return m;                
            }            
        }        
        
        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        
        //descobrir a instancia do Object 
        if (o instanceof Medico) {
            
            Medico m = (Medico) o;

            if (m.getData_cadastro() == null) {
                System.out.println("Iniciando Insert de Pessoa...");
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa (data_cadastro, tipo, "
                        + "cpf, cep, complemento, data_nascimento, email, endereco, nome, numero_celular, rg, "
                        + "senha) values (now(),?,?,?,?,?,?,?,?,?,?,?)");

                ps.setString(1, m.getTipo());
                ps.setString(2, m.getCpf());
                ps.setString(3, m.getCep());
                ps.setString(4, m.getComplemento());
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(m.getData_nascimento().getTimeInMillis());
                ps.setDate(5, dtU);
                ps.setString(6, m.getEmail());
                ps.setString(7, m.getEndereco());
                ps.setString(8, m.getNome());
                ps.setString(9, m.getNumero_celular());
                ps.setString(10, m.getRg());
                ps.setString(11, m.getSenha());

                ps.executeUpdate();
                System.out.println("Iniciando Insert de Médico...");
                ps = this.con.prepareStatement("insert into tb_medico (data_cadastro_medico, cpf, numero_crmv) values (now(),?,?)");
                ps.setString(1, m.getCpf());
                ps.setString(2, m.getNumero_crmv());
                System.out.println("Insert Medico");

                ps.executeUpdate();
            } else {
                System.out.println("Inciando Update de Pessoa...");
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set tipo = ?, cep = ?, "
                        + "complemento = ?, data_nascimento = ?, email = ?, endereco = ?, nome = ?, numero_celular = ?, "
                        + "rg = ?, senha = ?, data_cadastro = now() where cpf = ?");
                ps.setString(1, m.getTipo());
                ps.setString(2, m.getCep());
                ps.setString(3, m.getComplemento());
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(m.getData_nascimento().getTimeInMillis());
                ps.setDate(4, dtU);
                ps.setString(5, m.getEmail());
                ps.setString(6, m.getEndereco());
                ps.setString(7, m.getNome());
                ps.setString(8, m.getNumero_celular());
                ps.setString(9, m.getRg());
                ps.setString(10, m.getSenha());
                ps.setString(11, m.getCpf());
                ps.execute();

                System.out.println("Iniciando Update de Funcionario...");
                        ps = this.con.prepareStatement("update tb_medico set "
                        + "cpf = ? numero_crmv = ? where data_cadastro_medico = ?");
                ps.setString(1, m.getCpf());
                ps.setString(2, m.getNumero_crmv());
                Date dn = new Date(System.currentTimeMillis());
                dn.setTime(m.getData_cadastro_medico().getTimeInMillis());
                ps.setDate(3, (java.sql.Date) dn);
                System.out.println("Update Medico");
                ps.execute();
            }
             
        } else if (o instanceof Funcionario) {

            Funcionario c = (Funcionario) o;

            if (c.getData_cadastro() == null) {
                System.out.println("Iniciando Insert de Pessoa...");
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa (data_cadastro, tipo, "
                        + "cpf, cep, complemento, data_nascimento, email, endereco, nome, numero_celular, rg, "
                        + "senha) values (now(),?,?,?,?,?,?,?,?,?,?,?)");

                ps.setString(1, c.getTipo());
                ps.setString(2, c.getCpf());
                ps.setString(3, c.getCep());
                ps.setString(4, c.getComplemento());
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(c.getData_nascimento().getTimeInMillis());
                ps.setDate(5, dtU);
                ps.setString(6, c.getEmail());
                ps.setString(7, c.getEndereco());
                ps.setString(8, c.getNome());
                ps.setString(9, c.getNumero_celular());
                ps.setString(10, c.getRg());
                ps.setString(11, c.getSenha());

                ps.executeUpdate();
                System.out.println("Iniciando Insert de Funcionario...");
                ps = this.con.prepareStatement("insert into tb_funcionario (cargo,numero_ctps, numero_pis, cpf) values (?,?,?,?)");
                ps.setString(1, c.getCargo().toString());
                ps.setString(2, c.getNumero_ctps());
                ps.setString(3, c.getNumero_pis());
                ps.setString(4, c.getCpf());

                ps.executeUpdate();
            } else {
                System.out.println("Inciando Update de Pessoa...");
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set tipo = ?, cep = ?, "
                        + "complemento = ?, data_nascimento = ?, email = ?, endereco = ?, nome = ?, numero_celular = ?, "
                        + "rg = ?, senha = ?, data_cadastro = now() where cpf = ?");
                ps.setString(1, c.getTipo());
                ps.setString(2, c.getCep());
                ps.setString(3, c.getComplemento());
                Date dtU = new Date(System.currentTimeMillis());
                dtU.setTime(c.getData_nascimento().getTimeInMillis());
                ps.setDate(4, dtU);
                ps.setString(5, c.getEmail());
                ps.setString(6, c.getEndereco());
                ps.setString(7, c.getNome());
                ps.setString(8, c.getNumero_celular());
                ps.setString(9, c.getRg());
                ps.setString(10, c.getSenha());
                ps.setString(11, c.getCpf());
                ps.execute();

                System.out.println("Iniciando Update de Funcionario...");
                        ps = this.con.prepareStatement("update tb_funcionario set "
                        + "cargo = ?, numero_ctps = ?, numero_pis = ? where cpf = ?");
                ps.setString(1, c.getCargo().toString());
                ps.setString(2, c.getNumero_ctps());
                ps.setString(3, c.getNumero_pis());
                ps.setString(4, c.getCpf());
                ps.execute();
            }

                
            }else if (o instanceof Pessoa) {

                Pessoa p = (Pessoa) o;

                if (p.getData_cadastro() == null) {
                    PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa "
                            + "(data_cadastro, cpf, rg, nome, senha, numero_celular, email, data_nascimento, cep, "
                            + "endereco, complemento, tipo) values (now(),?,?,?,?,?,?,?,?,?,?,?)");

                ps.setString(1, p.getCpf());
                ps.setString(2, p.getRg());
                ps.setString(3, p.getNome());
                ps.setString(4, p.getSenha());
                ps.setString(5, p.getNumero_celular());
                ps.setString(6, p.getEmail());
                Date dn = new Date(System.currentTimeMillis());
                dn.setTime(p.getData_nascimento().getTimeInMillis());
                ps.setDate(7, dn);
                ps.setString(8, p.getCep());
                ps.setString(9, p.getEndereco());
                ps.setString(10, p.getComplemento());
                ps.setString(11, p.getTipo());

                System.out.println("Insert em Pessoa");               
                ps.executeUpdate();
            } else {
                
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set rg = ?, nome = ?, senha = ?, "
                        + "numero_celular = ?, email = ?, data_nascimento = ?, cep = ?, endereco = ?, "
                        + "complemento = ? where cpf = ?");
                
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

        } 
    }

    @Override
    public void remover(Object o) throws Exception {

        if(o instanceof Medico){
            
            Medico m = (Medico) o; //converter o para o e que é    
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_medico where cpf = ?");
            ps.setString(1, m.getCpf());            
            ps.execute(); 
            PreparedStatement psPessoa = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            psPessoa.setString(1, m.getCpf());            
            psPessoa.execute();  
            
        }else if(o instanceof Funcionario){

            Funcionario p = (Funcionario) o; //converter o para o e que é 
            PreparedStatement ps = this.con.prepareStatement("delete from tb_funcionario where cpf = ?");
            ps.setString(1, p.getCpf());            
            ps.execute();
            PreparedStatement psPessoa = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            psPessoa.setString(1, p.getCpf());            
            psPessoa.execute();            
            
        }else if(o instanceof Pessoa){

            Pessoa p = (Pessoa) o; //converter o para o e que é 
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");
            ps.setString(1, p.getCpf());            
            ps.execute();            
            
        }
    }
    
    public List<Pessoa> listPessoas() throws Exception {
        
        List<Pessoa> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select cpf, rg, nome, senha, numero_celular, email,"
                    + " data_cadastro, data_nascimento, cep, endereco, complemento, tipo from tb_pessoa");
        
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
            p.setTipo(rs.getString("tipo"));
            
            lista.add(p);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.
            
        }                
        return lista;
    }
    
    public List<Medico> listMedicos() throws Exception {
        
        List<Medico> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select cpf, numero_crmv, data_cadastro_medico from tb_medico");
        
        ResultSet rs = ps.executeQuery();//executa a query        
      
        lista = new ArrayList();
        while(rs.next()){
            
            Medico m = new Medico();
            m.setCpf(rs.getString("cpf"));
            m.setNumero_crmv(rs.getString("numero_crmv"));
            Calendar dcm = Calendar.getInstance();
            dcm.setTimeInMillis(rs.getDate("data_cadastro_medico").getTime());
            m.setData_cadastro_medico(dcm);
            
            PreparedStatement psP = this.con.prepareStatement("select rg, nome, senha, numero_celular, email, "
                    + "data_cadastro, data_nascimento, cep, endereco, complemento from tb_pessoa where cpf = ?");

            psP.setString(1, m.getCpf());
            ResultSet rsP = psP.executeQuery();

            if (rsP.next()) {
                m.setRg(rsP.getString("rg"));
                m.setNome(rsP.getString("nome"));
                m.setSenha(rsP.getString("senha"));
                m.setNumero_celular(rsP.getString("numero_celular"));
                m.setEmail(rsP.getString("email"));
                Calendar dc = Calendar.getInstance();
                dc.setTimeInMillis(rsP.getDate("data_cadastro").getTime());
                m.setData_cadastro(dc);
                Calendar dn = Calendar.getInstance();
                dn.setTimeInMillis(rsP.getDate("data_nascimento").getTime());
                m.setData_nascimento(dn);
                m.setCep(rsP.getString("cep"));
                m.setEndereco(rsP.getString("endereco"));
                m.setComplemento(rsP.getString("complemento"));

                psP.close();
            }
            lista.add(m);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.
            
        }          
        ps.close();
        return lista;
    }
    
    
    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {


        Pessoa pessoa = null;

         PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.senha from tb_pessoa p where p.cpf= ? and p.senha = ? ");

            ps.setString(1, cpf);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();//o ponteiro do REsultSet inicialmente está na linha -1

            if(rs.next()){//se a matriz (ResultSet) tem uma linha

                pessoa = new Pessoa();
                pessoa.setCpf(rs.getString("cpf"));
                pessoa.setSenha(rs.getString("senha"));    
                
                
            }

            ps.close();
            return pessoa;        

    }

    public List<Funcionario> listFuncionarios() throws SQLException {
        List<Funcionario> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select cargo, numero_ctps, numero_pis, cpf from tb_funcionario");
        ResultSet rs = ps.executeQuery();

        lista = new ArrayList<>();
        while (rs.next()) {
            Funcionario f = new Funcionario();
            f.setCpf(rs.getString("cpf"));
            f.setCargo(Cargo.valueOf(rs.getString("cargo").toUpperCase()));
            f.setNumero_ctps(rs.getString("numero_ctps"));
            f.setNumero_pis(rs.getString("numero_pis"));

            PreparedStatement psP = this.con.prepareStatement("select rg, nome, senha, numero_celular, email, "
                    + "data_cadastro, data_nascimento, cep, endereco, complemento from tb_pessoa where cpf = ?");

            psP.setString(1, f.getCpf());
            ResultSet rsP = psP.executeQuery();

            if (rsP.next()) {
                f.setRg(rsP.getString("rg"));
                f.setNome(rsP.getString("nome"));
                f.setSenha(rsP.getString("senha"));
                f.setNumero_celular(rsP.getString("numero_celular"));
                f.setEmail(rsP.getString("email"));
                Calendar dc = Calendar.getInstance();
                dc.setTimeInMillis(rsP.getDate("data_cadastro").getTime());
                f.setData_cadastro(dc);
                Calendar dn = Calendar.getInstance();
                dn.setTimeInMillis(rsP.getDate("data_nascimento").getTime());
                f.setData_nascimento(dn);
                f.setCep(rsP.getString("cep"));
                f.setEndereco(rsP.getString("endereco"));
                f.setComplemento(rsP.getString("complemento"));

                psP.close();
            }
            lista.add(f);
        }
        ps.close();
        return lista;
    }
}
