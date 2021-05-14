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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jbran
 */
@Entity
@Table(name = "videojuegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videojuegos.findAll", query = "SELECT v FROM Videojuegos v")
    , @NamedQuery(name = "Videojuegos.findByNumCatalogo", query = "SELECT v FROM Videojuegos v WHERE v.numCatalogo = :numCatalogo")
    , @NamedQuery(name = "Videojuegos.findByTitulo", query = "SELECT v FROM Videojuegos v WHERE v.titulo = :titulo")
    , @NamedQuery(name = "Videojuegos.findByGenero", query = "SELECT v FROM Videojuegos v WHERE v.genero = :genero")
    , @NamedQuery(name = "Videojuegos.findByClasificacion", query = "SELECT v FROM Videojuegos v WHERE v.clasificacion = :clasificacion")
    , @NamedQuery(name = "Videojuegos.findByConsola", query = "SELECT v FROM Videojuegos v WHERE v.consola = :consola")
    , @NamedQuery(name = "Videojuegos.findByFabricante", query = "SELECT v FROM Videojuegos v WHERE v.fabricante = :fabricante")
    , @NamedQuery(name = "Videojuegos.findByVersion", query = "SELECT v FROM Videojuegos v WHERE v.version = :version")})
public class Videojuegos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "numCatalogo")
    private String numCatalogo;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "genero")
    private String genero;
    @Basic(optional = false)
    @Column(name = "clasificacion")
    private String clasificacion;
    @Basic(optional = false)
    @Column(name = "consola")
    private String consola;
    @Basic(optional = false)
    @Column(name = "fabricante")
    private String fabricante;
    @Basic(optional = false)
    @Column(name = "version")
    private int version;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "videojuegos")
    private Inventariovideojuegos inventariovideojuegos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videojuegos")
    private List<Rentasvideojuegos> rentasvideojuegosList;

    public Videojuegos() {
    }

    public Videojuegos(String numCatalogo) {
        this.numCatalogo = numCatalogo;
    }

    public Videojuegos(String numCatalogo, String titulo, String genero, String clasificacion, String consola, String fabricante, int version) {
        this.numCatalogo = numCatalogo;
        this.titulo = titulo;
        this.genero = genero;
        this.clasificacion = clasificacion;
        this.consola = consola;
        this.fabricante = fabricante;
        this.version = version;
    }

    public String getNumCatalogo() {
        return numCatalogo;
    }

    public void setNumCatalogo(String numCatalogo) {
        this.numCatalogo = numCatalogo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Inventariovideojuegos getInventariovideojuegos() {
        return inventariovideojuegos;
    }

    public void setInventariovideojuegos(Inventariovideojuegos inventariovideojuegos) {
        this.inventariovideojuegos = inventariovideojuegos;
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
        hash += (numCatalogo != null ? numCatalogo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videojuegos)) {
            return false;
        }
        Videojuegos other = (Videojuegos) object;
        if ((this.numCatalogo == null && other.numCatalogo != null) || (this.numCatalogo != null && !this.numCatalogo.equals(other.numCatalogo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Videojuegos[ numCatalogo=" + numCatalogo + " ]";
    }
    
}
