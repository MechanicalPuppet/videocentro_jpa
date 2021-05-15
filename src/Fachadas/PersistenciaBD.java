/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fachadas;

import Entidades.Clientes;
import Entidades.Inventariovideojuegos;
import Entidades.Rentasvideojuegos;
import Entidades.Videojuegos;
import java.util.List;

/**
 *
 * @author Jbran
 */
public class PersistenciaBD {

    //    <>
    public boolean registrarCliente(Clientes cliente) {
        FachadaClientes fClientes = new FachadaClientes();
        return fClientes.registrarCliente(cliente);
    }

    public boolean eliminarCliente(Integer id) {
        FachadaClientes fClientes = new FachadaClientes();
        return fClientes.eliminarCliente(id);
    }

    public boolean actualizarCliente(Clientes cliente) {
        FachadaClientes fClientes = new FachadaClientes();
        return fClientes.editarCliente(cliente);
    }

    public List<Clientes> consultarClientes() {
        FachadaClientes fClientes = new FachadaClientes();
        return fClientes.consultarClientes();
    }

    public Clientes consultarClienteID(Integer id) {
        FachadaClientes fClientes = new FachadaClientes();
        return fClientes.consultarClienteId(id);
    }

    public boolean registrarVideojuego(Videojuegos videojuego) {
        FachadaVideojuegos fVideojuegos = new FachadaVideojuegos();
        return fVideojuegos.registrarVideojuego(videojuego);
    }

    public boolean eliminarVideojuego(Integer id) {
        FachadaVideojuegos fVideojuegos = new FachadaVideojuegos();
        return fVideojuegos.eliminarVideojuego(id);
    }

    public boolean actualizarVideojuego(Videojuegos videojuego) {
        FachadaVideojuegos fVideojuegos = new FachadaVideojuegos();
        return fVideojuegos.editarVideojuego(videojuego);
    }

    public List<Videojuegos> consultarVideojuegos() {
        FachadaVideojuegos fVideojuegos = new FachadaVideojuegos();
        return fVideojuegos.consultarVideojuegos();
    }

    public Videojuegos consultarVideojuegosID(Integer id) {
        FachadaVideojuegos fVideojuegos = new FachadaVideojuegos();
        return fVideojuegos.consultarVideojuegoID(id);
    }

    public boolean registrarInventario(Inventariovideojuegos inventario) {
        FachadaInventarioVideojuegos fInventario = new FachadaInventarioVideojuegos();
        return fInventario.registrarInventario(inventario);
    }

    public boolean eliminarInventario(Integer id) {
        FachadaInventarioVideojuegos fInventario = new FachadaInventarioVideojuegos();
        return fInventario.eliminarInventario(id);
    }

    public boolean actualizarInventario(Inventariovideojuegos inventario) {
        FachadaInventarioVideojuegos fInventario = new FachadaInventarioVideojuegos();
        return fInventario.editarInventario(inventario);
    }

    public List<Inventariovideojuegos> consultarInventario() {
        FachadaInventarioVideojuegos fInventario = new FachadaInventarioVideojuegos();
        return fInventario.consultarinventarioVideojuegos();
    }

    public Inventariovideojuegos consultarInventarioID(Integer id) {
        FachadaInventarioVideojuegos fInventario = new FachadaInventarioVideojuegos();
        return fInventario.consultarInventarioVideojuegosID(id);
    }

    public boolean registrarRenta(Rentasvideojuegos renta) {
        FachadaRentasVideojuegos fRentas = new FachadaRentasVideojuegos();
        return fRentas.registrarRenta(renta);
    }

    public boolean eliminarRenta(Rentasvideojuegos renta) {
        FachadaRentasVideojuegos fRentas = new FachadaRentasVideojuegos();
        return fRentas.eliminarRenta(renta);
    }

    public boolean actualizarRenta(Rentasvideojuegos renta) {
        FachadaRentasVideojuegos fRentas = new FachadaRentasVideojuegos();
        return fRentas.editarRenta(renta);
    }

    public List<Rentasvideojuegos> consultarRentas() {
        FachadaRentasVideojuegos fRentas = new FachadaRentasVideojuegos();
        return fRentas.consultarRentas();
    }

    public Rentasvideojuegos consultarInventarioID(Rentasvideojuegos renta) { // Metes un objeto vacío con id para que te devuelva el completo. Stonks
        FachadaRentasVideojuegos fRentas = new FachadaRentasVideojuegos();
        return fRentas.consultarRenta(renta);
    }



}
