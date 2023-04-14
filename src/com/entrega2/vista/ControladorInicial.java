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
    
    /**
     * Constructor del controlador. Inicializa el modelo
     * @param vista VistaEdicion de la aplicacion. 
     */
    public ControladorInicial(VistaInicial vista){
        this.miVista=vista;
        setContadores();
    }
    
    public void procesarRedireccionEdicion(){
        Main.getGestorVistas().mostrarVistaEdicion();
    }
    
    public void procesarRedireccionBusqueda(){
        Main.getGestorVistas().mostrarVistaBusqueda();
    }
    
    public void setContadores(){
        if (miVista.getCantidadLibros().equals("")){
            miVista.setCantidadLibros("0");
        }
        
        if (miVista.getPrecioMedio().equals("")){
            miVista.setPrecioMedio("0");
        }
        
        if (miVista.getLibrosLeidos().equals("")){
            miVista.setLibrosLeidos("0/0");
        }
    }
}
