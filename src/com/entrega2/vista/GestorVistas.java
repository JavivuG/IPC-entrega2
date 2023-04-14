/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.entrega2.vista;

import com.entrega2.modelo.ReferenciaLibro;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author jagarci
 */
public class GestorVistas {
    private JFrame vistaActual;
    

    public void mostrarVistaInicial() {
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaInicial();
        vistaActual.setMinimumSize(new Dimension(341,221)); // Cambio tamaño minimo de la vista
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaEdicion(){
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaEdicion();
        vistaActual.setMinimumSize(new Dimension(600,350)); // Cambio tamaño minimo de la vista
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaEdicion(ReferenciaLibro libro, int indice){ // Con los datos para editar
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaEdicion(libro,  indice);
        vistaActual.setMinimumSize(new Dimension(600,350)); // Cambio tamaño minimo de la vista
        vistaActual.setVisible(true);
    }
    
    public void mostrarVistaBusqueda(){
        if (vistaActual != null) {
            vistaActual.setVisible(false);
            vistaActual.dispose();
        }
        vistaActual = new VistaBusqueda();
        vistaActual.setMinimumSize(new Dimension(689,259)); // Cambio tamaño minimo de la vista
        vistaActual.setVisible(true);
    }



}
