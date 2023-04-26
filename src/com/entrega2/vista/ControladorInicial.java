/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega2.vista;

import com.entrega2.main.Main;
import com.entrega2.modelo.Biblioteca;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Clase que representa el controlador de la vista Inicial
 * @author javivu
 * @author mgonzal
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
    
    /**
     * Redirijo a la vista de Edicion
     */
    public void procesarRedireccionEdicion(){
        Main.getGestorVistas().mostrarVistaEdicion(null);
    }
    
    /**
     * Redirijo a la vista de Busqueda
     */
    public void procesarRedireccionBusqueda(){
        Main.getGestorVistas().mostrarVistaBusqueda();
    }
    
    /**
     * Inicializo los contadores que se muestran en la vista con estadisticas del sistema
     * Las estadisticas son:
     * Cantidad de libros del sistema
     * Precio medio de los libros
     * Cantidad de libros leidos/no leidos
     */
    public void setContadores(){
        miVista.setCantidadLibros(miModelo.getListaLibros().size()+"");
        
        double precioMedio=miModelo.getPrecioMedio();
        if (precioMedio!=0){
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat formato = new DecimalFormat("####.##", simbolos);
            miVista.setPrecioMedio(formato.format(precioMedio));
        }
        else {
            miVista.setPrecioMedio(precioMedio+"");
        }


        int cantidadLibrosLeidos=miModelo.getCantidadLibrosLeidos();
        miVista.setLibrosLeidos(cantidadLibrosLeidos+"/"+(miModelo.getListaLibros().size()-cantidadLibrosLeidos));
    }
}
