/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.entrega2.vista;

import com.entrega2.modelo.ReferenciaLibro;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Clase que representa un gestor de vista para cambiar entre las vista existentes
 * @author jagarci
 * @author mgonzal
 */
public class GestorVistas {
    private JFrame vistaActual;
    

    /**
     * Muestra la vista inicial
     */
    public void mostrarVistaInicial() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaInicial();
        vistaActual.setLocationRelativeTo(null); // Para que salga en el centro de la pantalla
        vistaActual.setMinimumSize(new Dimension(370,235)); // Cambio tamaño minimo de la vista
        vistaActual.setVisible(true);
    }
    
    /**
     * Muestra la vista de Edicion
     * Si se indica un libro entonces se ira a vista de Edicion los datos del libro precargados
     * @param libro Libro que se desea editar, Si es null se carga la vista de Edicion de modo normal
     */
    public void mostrarVistaEdicion(ReferenciaLibro libro){ // Con los datos para editar
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        if (libro!=null){
            vistaActual = new VistaEdicion(libro);
        }
        else {
            vistaActual = new VistaEdicion();
        }
        vistaActual.setLocationRelativeTo(null); // Para que salga en el centro de la pantalla
        vistaActual.setMinimumSize(new Dimension(600,430)); // Cambio tamaño minimo de la vista
        vistaActual.setVisible(true);
    }
    
    /**
     * Muestra la vista de Busqueda
     */
    public void mostrarVistaBusqueda(){
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaBusqueda();
        vistaActual.setLocationRelativeTo(null); // Para que salga en el centro de la pantalla
        vistaActual.setMinimumSize(new Dimension(608,259)); // Cambio tamaño minimo de la vista
        vistaActual.setVisible(true);
    }



}
