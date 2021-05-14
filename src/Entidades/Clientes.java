/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jbran
 */
@Entity
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByNumCredencial", query = "SELECT c FROM Clientes c WHERE c.numCredencial = :numCredencial")
    , @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "Clientes.findByTelefono", query = "SELECT c FROM Clientes c WHERE c.telefono = :telefono")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numCredencial")
    private String numCredencial;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private List<Rentasvideojuegos> rentasvideojuegosList;

    public Clientes() {
    }

    public Clientes(String numCredencial) {
        this.numCredencial = numCredencial;
    }

    public Clientes(String numCredencial, String nombre, String direccion) {
        this.numCredencial = numCredencial;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNumCredencial() {
        return numCredencial;
    }

    public void setNumCredencial(String numCredencial) {
        this.numCredencial = numCredencial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @XmlTransient
    public List<Rentasvideojuegos> getRentasvideojuegosList() {
        return rentasvideojuegosList;
    }

    public void setRentasvideojuegosList(List<Rentasvideojuegos> rentasvideojuegosList) {
        this.rentasvideojuegosList = rentasvideojuegosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numCredencial != null ? numCredencial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.numCredencial == null && other.numCredencial != null) || (this.numCredencial != null && !this.numCredencial.equals(other.numCredencial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Clientes[ numCredencial=" + numCredencial + " ]";
    }
    
}
