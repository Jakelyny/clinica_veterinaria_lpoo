package br.edu.ifsul.cc.lpoo.cv.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Jakelyny Sousa
 */
@Entity
@Table(name = "tb_medico")
public class Medico implements Serializable{
    
    @Id
    private String numero_crmv;

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
    
    
}
