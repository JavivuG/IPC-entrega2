/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega2.vista;

import com.entrega2.main.Main;
import com.entrega2.modelo.Biblioteca;

/**
 *
 * @author javivu
 */
public class ControladorInicial {
    private VistaInicial miVista; // VistaEdicion de la aplicación
    private Biblioteca miModelo;
    
    /**
     * Constructor del controlador. Inicializa el modelo
     * @param vista VistaEdicion de la aplicacion. 
     */
    public ControladorInicial(VistaInicial vista){
        this.miVista=vista;
        this.miModelo=Main.getBiblioteca();
        setContadores();
    }
    
    public void procesarRedireccionEdicion(){
        Main.getGestorVistas().mostrarVistaEdicion();
    }
    
    public void procesarRedireccionBusqueda(){
        Main.getGestorVistas().mostrarVistaBusqueda();
    }
    
    public void setContadores(){
        miVista.setCantidadLibros(miModelo.getListaLibros().size()+"");
        
        double precioMedio=miModelo.getPrecioMedio();
        if (precioMedio!=0){
            String precio = precioMedio+"";
        
            miVista.setPrecioMedio(precio.substring(0,precio.indexOf(".")+3));
        }
        else {
            miVista.setPrecioMedio(precioMedio+"");
        }


        int cantidadLibrosLeidos=miModelo.getCantidadLibrosLeidos();
        miVista.setLibrosLeidos(cantidadLibrosLeidos+"/"+(miModelo.getListaLibros().size()-cantidadLibrosLeidos));
    }
}
