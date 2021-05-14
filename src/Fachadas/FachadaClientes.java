/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fachadas;

import Controladores.ClientesJpaController;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Clientes;
import java.util.List;

/**
 *
 * @author Jbran
 */
public class FachadaClientes {
    ClientesJpaController CClientes;
    
    public FachadaClientes(){
        CClientes = new ClientesJpaController();
    }
    
    public boolean registrarCliente(Clientes cliente){
        try {
            CClientes.create(cliente);
            return true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }
    }

    public boolean editarCliente(Clientes cliente) {
        try {
            CClientes.edit(cliente);
            return true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }
    }

    public List<Clientes> consultarClientes() {
        List<Clientes> clientes = CClientes.findClientesEntities();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        }

        return clientes;
    }

    public Clientes consultarClienteId(Integer id) {
        return CClientes.findClientes(id + "");
    }

    public boolean eliminarCliente(Integer id) {
        try {
            CClientes.destroy(id + "");
            return true;
        } catch (IllegalOrphanException | NonexistentEntityException x) {
            System.out.println(x.getMessage());
            return false;
        }
    }


}
