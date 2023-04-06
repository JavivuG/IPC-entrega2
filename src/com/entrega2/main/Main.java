/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.entrega2.main;

import com.entrega2.vista.GestorVistas;

/**
 *
 * @author jagarci
 */
public class Main {
    private static GestorVistas gestor;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        gestor = new GestorVistas();
        gestor.mostrarVistaInicial();
    }

    /**
     * 
     * @return 
     */
    public static GestorVistas getGestorVistas() {
        return gestor;
    }


}
