/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fachadas;

import Controladores.InventariovideojuegosJpaController;
import Entidades.Inventariovideojuegos;
import Entidades.Videojuegos;
import java.util.List;

/**
 *
 * @author Jbran
 */
public class FachadaInventarioVideojuegos {
    
    InventariovideojuegosJpaController CInventario;
    
    public FachadaInventarioVideojuegos(){
        CInventario = new InventariovideojuegosJpaController();
    }
    
    public boolean registrarInventario(Inventariovideojuegos inventario) {
        try {
            CInventario.create(inventario);
            return true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }
    }
    
     public boolean editarInventario(Inventariovideojuegos inventarioAActualizar){
        try{
            CInventario.edit(inventarioAActualizar);
            return true;
        } catch (Exception x){
            System.out.println(x.getMessage());
            return false;
        }
    }
     
     public List<Inventariovideojuegos> consultarinventarioVideojuegos(){
         List<Inventariovideojuegos> inventarios = CInventario.findInventariovideojuegosEntities();
        
        if(inventarios.isEmpty()){
            System.out.println("No hay ingredientes registrados");
        } 
        return inventarios;
     }
   
     public Inventariovideojuegos consultarInventarioVideojuegosID(Integer id){
        return CInventario.findInventariovideojuegos(id + "");
    }
     
     public boolean eliminarInventario(Integer idInventario){
         try {
            CInventario.destroy(idInventario+"");
            return true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }

     }
}
