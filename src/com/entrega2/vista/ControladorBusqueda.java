/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega2.vista;

import com.entrega2.main.Main;
import com.entrega2.modelo.Biblioteca;
import com.entrega2.modelo.ReferenciaLibro;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author javivu
 * @author mgonzal
 */
public class ControladorBusqueda {
    private VistaBusqueda miVista; // VistaEdicion de la aplicación
    private Biblioteca miModelo;
    
    /**
     * Constructor del controlador. Inicializa el modelo
     * @param vista VistaEdicion de la aplicacion. 
     */
    public ControladorBusqueda(VistaBusqueda vista){
        this.miVista=vista;
        this.miModelo=new Biblioteca();
    }
    
    public void procesarRedirrecionInicial(){
        Main.getGestorVistas().mostrarVistaInicial();
    }
    
    public void procesarRedirrecionBusqueda(){
        Main.getGestorVistas().mostrarVistaEdicion();
    }
    
    public void procesarEventoCargar(File ficheroImportar){
        try {
            int contadorLibros=0, contadorLineas=0;
            String titulo, descripcion, isbn, estado;
            double precio=0;
            boolean error, leido;
            String linea;
            BufferedReader reader = new BufferedReader(new FileReader(ficheroImportar));
            
            while ((linea=reader.readLine())!=null){
                error=false;
                String[] datosLibro= linea.split(";");
                titulo=datosLibro[0];
                descripcion=datosLibro[1];
                isbn=datosLibro[2];
                estado=datosLibro[3];
                try {
                    precio=Double.parseDouble(datosLibro[4]);                    
                }
                catch (NumberFormatException e){
                    error=true;
                }
                
                if (datosLibro[5].equalsIgnoreCase("true")){
                    leido=true;
                }
                else if (datosLibro[5].equalsIgnoreCase("false")){
                    leido=false;
                }

                if (!error){
                    ReferenciaLibro libro=new ReferenciaLibro(titulo, descripcion, isbn, titulo, precio, true);
                    System.out.println(libro);
                    miModelo.addLibro(libro);
                    contadorLibros++;   
                }
                contadorLineas++;
            }
            if (contadorLibros>0){
                miVista.mostrarMensaje("Se han añadido "+ contadorLibros+" libros");
            }
            else {
                miVista.mostrarMensaje("No se han podido añadir nuevos libros");
            }
            reader.close();
        }
        catch (IOException e){
            miVista.mostrarMensaje("No se ha encontrado el fichero");
        }

    }
}
