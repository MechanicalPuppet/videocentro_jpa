/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Jbran
 */
@Embeddable
public class RentasvideojuegosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "numCredencial")
    private String numCredencial;
    @Basic(optional = false)
    @Column(name = "numCatalogo")
    private String numCatalogo;

    public RentasvideojuegosPK() {
    }

    public RentasvideojuegosPK(String numCredencial, String numCatalogo) {
        this.numCredencial = numCredencial;
        this.numCatalogo = numCatalogo;
    }

    public String getNumCredencial() {
        return numCredencial;
    }

    public void setNumCredencial(String numCredencial) {
        this.numCredencial = numCredencial;
    }

    public String getNumCatalogo() {
        return numCatalogo;
    }

    public void setNumCatalogo(String numCatalogo) {
        this.numCatalogo = numCatalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numCredencial != null ? numCredencial.hashCode() : 0);
        hash += (numCatalogo != null ? numCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RentasvideojuegosPK)) {
            return false;
        }
        RentasvideojuegosPK other = (RentasvideojuegosPK) object;
        if ((this.numCredencial == null && other.numCredencial != null) || (this.numCredencial != null && !this.numCredencial.equals(other.numCredencial))) {
            return false;
        }
        if ((this.numCatalogo == null && other.numCatalogo != null) || (this.numCatalogo != null && !this.numCatalogo.equals(other.numCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RentasvideojuegosPK[ numCredencial=" + numCredencial + ", numCatalogo=" + numCatalogo + " ]";
    }
    
}
