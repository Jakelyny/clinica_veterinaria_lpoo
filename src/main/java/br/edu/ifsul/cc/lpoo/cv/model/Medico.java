package br.edu.ifsul.cc.lpoo.cv.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jakelyny Sousa
 */
@Entity
@Table(name = "tb_medico")
@DiscriminatorValue("M")
public class Medico extends Pessoa{
    
    @Column(nullable = false)
    private String numero_crmv;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_cadastro_medico;

    public Medico() {
        
    }
    
    /**
     * @return the numero_crmv
     */
    public String getNumero_crmv() {
        return numero_crmv;
    }

    /**
     * @param numero_crmv the numero_crmv to set
     */
    public void setNumero_crmv(String numero_crmv) {
        this.numero_crmv = numero_crmv;
    }

    /**
     * @return the data_cadastro_medico
     */
    public Calendar getData_cadastro_medico() {
        return data_cadastro_medico;
    }

    /**
     * @param data_cadastro_medico the data_cadastro_medico to set
     */
    public void setData_cadastro_medico(Calendar data_cadastro_medico) {
        this.data_cadastro_medico = data_cadastro_medico;
    }

    @Override
    public String toString() {
        return getCpf();
    }
    
    
    
}
