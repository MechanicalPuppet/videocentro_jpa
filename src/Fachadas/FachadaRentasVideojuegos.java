/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fachadas;

import Controladores.RentasvideojuegosJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Rentasvideojuegos;
import java.util.List;

/**
 *
 * @author Jbran
 */
public class FachadaRentasVideojuegos {
    RentasvideojuegosJpaController CRentas;

    public FachadaRentasVideojuegos(){
        CRentas = new RentasvideojuegosJpaController();
    }

    public boolean registrarRenta(Rentasvideojuegos renta) {
        try {
            CRentas.create(renta);
            return true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }
    }
    
    public boolean editarRenta(Rentasvideojuegos renta) {
        try {
            CRentas.edit(renta);
            return true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
            return false;
        }
    }
    
    public List<Rentasvideojuegos> consultarRentas(){
         List<Rentasvideojuegos> rentas = CRentas.findRentasvideojuegosEntities();
        
        if(rentas.isEmpty()){
            System.out.println("No hay videojuegos registrados");   
        }
        return rentas;
    }
    
    public Rentasvideojuegos consultarRenta(Rentasvideojuegos renta){
        return CRentas.findRentasvideojuegos(renta.getRentasvideojuegosPK());
    }
    
    public boolean eliminarRenta(Rentasvideojuegos renta){
         try{
            CRentas.destroy(renta.getRentasvideojuegosPK());
            return true;
        } catch (NonexistentEntityException x){
            System.out.println(x.getMessage());
            return false;
        }
    }
    
    

}
