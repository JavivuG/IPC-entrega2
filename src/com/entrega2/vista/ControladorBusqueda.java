/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega2.vista;

import com.entrega2.main.Main;
import com.entrega2.modelo.Biblioteca;
import com.entrega2.modelo.ReferenciaLibro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
        this.miModelo=Main.getBiblioteca();
        ArrayList<ReferenciaLibro> listaLibros=miModelo.getListaLibros();
        if (listaLibros.size()>0){
            miVista.actualizaListaLibros(listaLibros);
        }
    }
    
    public void procesarRedirrecionInicial(){
        Main.getGestorVistas().mostrarVistaInicial();
    }
    
    public void procesarRedirrecionBusqueda(){
        Main.getGestorVistas().mostrarVistaEdicion();
    }
    
    public void procesarEventoCargar(File ficheroImportar){
        try (BufferedReader reader = new BufferedReader(new FileReader(ficheroImportar))) {
            int contadorLibros=0, contadorLineas=0;
            String titulo, descripcion, isbn, estado;
            double precio=0;
            boolean error, leido=false;
            String linea;
            
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
                    ReferenciaLibro libro=new ReferenciaLibro(titulo, descripcion, isbn, estado, precio, leido);
                    if (miModelo.addLibro(libro)){
                        contadorLibros++;   
                    }
                }
                contadorLineas++;
            }
            if (contadorLibros>0){
                miVista.actualizaListaLibros(miModelo.getListaLibros());
                miVista.mostrarMensajeExito("Se han añadido "+ contadorLibros+" libros de "+ contadorLineas+ " entradas del fichero");
            }
            else {
                miVista.mostrarMensajeError("No se han podido añadir nuevos libros");
            }
            reader.close();
        }
        catch (IOException e){
            miVista.mostrarMensajeError("No se ha encontrado el fichero");
        }

    }
    
    public void procesarEventoExportar(File fichero){
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero))) {

            ArrayList<ReferenciaLibro> listaLibros=miModelo.getListaLibros();
            for (int i=0; i<listaLibros.size(); i++){
                ReferenciaLibro libro=listaLibros.get(i);
                escritor.write(libro.getTitulo()+";"+libro.getDescripcion()+";"
                        +libro.getISBN()+";"+libro.getEstado()+";"+libro.getPrecio()+";"+libro.isLeido()+"\n");
            }
            
            miVista.mostrarMensajeExito("Se han exportado "+ listaLibros.size()+ " libros");

            escritor.close();
        } catch (IOException e){
            miVista.mostrarMensajeError("No se puedo crear el fichero de exportación");
        }
    }
    
    public void procesarEventoEditar(){
        int indice=miVista.getElementoListaSeleccionado();
        if (indice!=-1){
            Main.getGestorVistas().mostrarVistaEdicion(miModelo.getListaLibros().get(indice), indice);
        }
        else {
            miVista.mostrarMensajeError("Debe seleccionar un libro de la lista");
        }
    }
}
