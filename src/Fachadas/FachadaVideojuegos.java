/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fachadas;

import Controladores.VideojuegosJpaController;
import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Videojuegos;
import java.util.List;

/**
 *
 * @author Jbran
 */
public class FachadaVideojuegos {
    
    VideojuegosJpaController CVideojuegos;
    
    public FachadaVideojuegos(){
        CVideojuegos = new VideojuegosJpaController();
    }

    public boolean registrarVideojuego(Videojuegos videojuego) {
        try {
            CVideojuegos.create(videojuego);
            return true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }
    }
    public boolean editarVideojuego(Videojuegos videojuegoAActualizar){
        try{
            CVideojuegos.edit(videojuegoAActualizar);
            return true;
        } catch (Exception x){
            System.out.println(x.getMessage());
            return false;
        }
    }
    
    public List<Videojuegos> consultarVideojuegos(){
        List<Videojuegos> videojuegos = CVideojuegos.findVideojuegosEntities();
        
        if(videojuegos.isEmpty()){
            System.out.println("No hay videojuegos registrados");   
        }
        return videojuegos;
    }
    
    public Videojuegos consultarVideojuegoID(Integer id){
        return CVideojuegos.findVideojuegos(""+id);
    }
    
    public boolean eliminarVideojuego(Integer idIngrediente){
        try{
            CVideojuegos.destroy(""+idIngrediente);
            return true;
        } catch (IllegalOrphanException | NonexistentEntityException x){
            System.out.println(x.getMessage());
            return false;
        }
    }
    
    
}
