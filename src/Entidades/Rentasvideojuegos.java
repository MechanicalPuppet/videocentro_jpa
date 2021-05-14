/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jbran
 */
@Entity
@Table(name = "rentasvideojuegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rentasvideojuegos.findAll", query = "SELECT r FROM Rentasvideojuegos r")
    , @NamedQuery(name = "Rentasvideojuegos.findByNumCredencial", query = "SELECT r FROM Rentasvideojuegos r WHERE r.rentasvideojuegosPK.numCredencial = :numCredencial")
    , @NamedQuery(name = "Rentasvideojuegos.findByNumCatalogo", query = "SELECT r FROM Rentasvideojuegos r WHERE r.rentasvideojuegosPK.numCatalogo = :numCatalogo")
    , @NamedQuery(name = "Rentasvideojuegos.findByFechaRenta", query = "SELECT r FROM Rentasvideojuegos r WHERE r.fechaRenta = :fechaRenta")
    , @NamedQuery(name = "Rentasvideojuegos.findByTiempoRenta", query = "SELECT r FROM Rentasvideojuegos r WHERE r.tiempoRenta = :tiempoRenta")})
public class Rentasvideojuegos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RentasvideojuegosPK rentasvideojuegosPK;
    @Basic(optional = false)
    @Column(name = "fechaRenta")
    @Temporal(TemporalType.DATE)
    private Date fechaRenta;
    @Basic(optional = false)
    @Column(name = "tiempoRenta")
    private int tiempoRenta;
    @JoinColumn(name = "numCredencial", referencedColumnName = "numCredencial", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "numCatalogo", referencedColumnName = "numCatalogo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Videojuegos videojuegos;

    public Rentasvideojuegos() {
    }

    public Rentasvideojuegos(RentasvideojuegosPK rentasvideojuegosPK) {
        this.rentasvideojuegosPK = rentasvideojuegosPK;
    }

    public Rentasvideojuegos(RentasvideojuegosPK rentasvideojuegosPK, Date fechaRenta, int tiempoRenta) {
        this.rentasvideojuegosPK = rentasvideojuegosPK;
        this.fechaRenta = fechaRenta;
        this.tiempoRenta = tiempoRenta;
    }

    public Rentasvideojuegos(String numCredencial, String numCatalogo) {
        this.rentasvideojuegosPK = new RentasvideojuegosPK(numCredencial, numCatalogo);
    }

    public RentasvideojuegosPK getRentasvideojuegosPK() {
        return rentasvideojuegosPK;
    }

    public void setRentasvideojuegosPK(RentasvideojuegosPK rentasvideojuegosPK) {
        this.rentasvideojuegosPK = rentasvideojuegosPK;
    }

    public Date getFechaRenta() {
        return fechaRenta;
    }

    public void setFechaRenta(Date fechaRenta) {
        this.fechaRenta = fechaRenta;
    }

    public int getTiempoRenta() {
        return tiempoRenta;
    }

    public void setTiempoRenta(int tiempoRenta) {
        this.tiempoRenta = tiempoRenta;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
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
        hash += (rentasvideojuegosPK != null ? rentasvideojuegosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rentasvideojuegos)) {
            return false;
        }
        Rentasvideojuegos other = (Rentasvideojuegos) object;
        if ((this.rentasvideojuegosPK == null && other.rentasvideojuegosPK != null) || (this.rentasvideojuegosPK != null && !this.rentasvideojuegosPK.equals(other.rentasvideojuegosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Rentasvideojuegos[ rentasvideojuegosPK=" + rentasvideojuegosPK + " ]";
    }
    
}
