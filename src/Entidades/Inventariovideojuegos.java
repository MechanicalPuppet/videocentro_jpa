/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jbran
 */
@Entity
@Table(name = "inventariovideojuegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventariovideojuegos.findAll", query = "SELECT i FROM Inventariovideojuegos i")
    , @NamedQuery(name = "Inventariovideojuegos.findByNumCatalogo", query = "SELECT i FROM Inventariovideojuegos i WHERE i.numCatalogo = :numCatalogo")
    , @NamedQuery(name = "Inventariovideojuegos.findByExistencia", query = "SELECT i FROM Inventariovideojuegos i WHERE i.existencia = :existencia")
    , @NamedQuery(name = "Inventariovideojuegos.findByDisponibilidad", query = "SELECT i FROM Inventariovideojuegos i WHERE i.disponibilidad = :disponibilidad")})
public class Inventariovideojuegos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numCatalogo")
    private String numCatalogo;
    @Basic(optional = false)
    @Column(name = "existencia")
    private int existencia;
    @Basic(optional = false)
    @Column(name = "disponibilidad")
    private int disponibilidad;
    @JoinColumn(name = "numCatalogo", referencedColumnName = "numCatalogo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Videojuegos videojuegos;

    public Inventariovideojuegos() {
    }

    public Inventariovideojuegos(String numCatalogo) {
        this.numCatalogo = numCatalogo;
    }

    public Inventariovideojuegos(String numCatalogo, int existencia, int disponibilidad) {
        this.numCatalogo = numCatalogo;
        this.existencia = existencia;
        this.disponibilidad = disponibilidad;
    }

    public String getNumCatalogo() {
        return numCatalogo;
    }

    public void setNumCatalogo(String numCatalogo) {
        this.numCatalogo = numCatalogo;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Videojuegos getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(Videojuegos videojuegos) {
        this.videojuegos = videojuegos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numCatalogo != null ? numCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventariovideojuegos)) {
            return false;
        }
        Inventariovideojuegos other = (Inventariovideojuegos) object;
        if ((this.numCatalogo == null && other.numCatalogo != null) || (this.numCatalogo != null && !this.numCatalogo.equals(other.numCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Inventariovideojuegos[ numCatalogo=" + numCatalogo + " ]";
    }
    
}
