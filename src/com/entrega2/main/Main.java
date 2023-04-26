/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.entrega2.main;

import com.entrega2.modelo.Biblioteca;
import com.entrega2.vista.GestorVistas;

/**
 * Clase que representa el metodo Main para inicializar la aplicacion
 * @author jagarci
 * @author mgonzal
 */
public class Main {
    private static GestorVistas gestor;
    private static Biblioteca biblioteca;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestorVistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestorVistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestorVistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestorVistas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
                //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        gestor = new GestorVistas();
        biblioteca=new Biblioteca();
        gestor.mostrarVistaInicial();
    }

    /**
     * Devuelve el gestor de vistas para cambiar entre las vistas de la aplicacion
     * @return Gestor de vista 
     */
    public static GestorVistas getGestorVistas() {
        return gestor;
    }
    
    /**
     * Devuelve la biblioteca 
     * @return Biblioteca
     */
    public static Biblioteca getBiblioteca(){
        return biblioteca;
    }


}
