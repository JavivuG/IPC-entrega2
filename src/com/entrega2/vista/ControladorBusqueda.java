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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        
        if (miModelo.getListaLibros().size()>0){

            miVista.actualizaListaLibros(convertirArray(miModelo.getListaLibros()));
        }
    }
    
    public void procesarRedirrecionInicial(){
        Main.getGestorVistas().mostrarVistaInicial();
    }
    
    public void procesarRedirrecionEdicion(){
        Main.getGestorVistas().mostrarVistaEdicion(null,null);
    }
    
    public void procesarEventoCargar(File ficheroImportar){
        try (BufferedReader reader = new BufferedReader(new FileReader(ficheroImportar))) {
            int contadorLibros=0, contadorLineas=0;
            String titulo, descripcion, isbn, estado;
            double precio=0;
            boolean error, leido=false, ficheroNoEsCorrecto=false;
            String linea;
            
            while ((linea=reader.readLine())!=null){
                error=false;
                String[] datosLibro= linea.split(";");
                
                if (datosLibro.length!=6){
                    ficheroNoEsCorrecto=true;
                    break;
                }

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
                miVista.actualizaListaLibros(convertirArray(miModelo.getListaLibros()));
                miVista.mostrarMensajeExito("Se han añadido "+ contadorLibros+" libros de "+ contadorLineas+ " entradas del fichero");
            }
            else {
                if (ficheroNoEsCorrecto){
                    miVista.mostrarMensajeError("El fichero no es correcto");
                }
                else {
                    miVista.mostrarMensajeError("No se han podido añadir nuevos libros");
                }
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
            if (miModelo.getListaLibros().isEmpty()){
                miVista.mostrarMensajeError("No hay libros en la lista");
            }
            else {
                miVista.mostrarMensajeError("Debe seleccionar un libro de la lista"); 
            }
        }
    }
    
     /**
     * Procesa el evento generado para borrar un libro de la lista
     * Debe haber un libro seleccionado
     */
    public void procesarEventoBorrar(){
        int indice=miVista.getElementoListaSeleccionado();
        if (indice!=-1){ // Si hay un elemento seleccionado
            miModelo.borraLibro(indice);
            ReferenciaLibro[] listaLibros = new ReferenciaLibro[miModelo.getListaLibros().size()];
            miVista.actualizaListaLibros(miModelo.getListaLibros().toArray(listaLibros));
            miVista.mostrarMensajeExito("El libro seleccionado se ha borrado de la lista");
        }
        else { // Si no hay ningun elemento seleccionado
            if (miModelo.getListaLibros().isEmpty()){
                miVista.mostrarMensajeError("No hay libros en la lista");
            }
            else {
                miVista.mostrarMensajeError("Debe seleccionar un libro de la lista");
            }
        }
    }
    
    public void procesarEventoOrdenar(int modoOrdenacion){
        ArrayList<ReferenciaLibro> listaLibros = miModelo.getListaLibros();
        
        if (modoOrdenacion==0){
            miVista.actualizaListaLibros(convertirArray(listaLibros));
            miVista.mostrarMensajeExito("Se ha restaurado el orden de los libros");
        }
        else if (modoOrdenacion==1){ // Alfabeticamente (menor a mayor)
            String[] datos=new String[listaLibros.size()];
            
            for (int i=0; i<listaLibros.size(); i++){
                ReferenciaLibro libro = listaLibros.get(i);
                datos[i]=libro.imprimirEnBusqueda();
            }
            
            Arrays.sort(datos);
            miVista.actualizaListaLibros(datos);
            miVista.mostrarMensajeExito("Los libros se han ordenado alfabeticamente de menor a mayor");
        }
        else if (modoOrdenacion==2){ // Alfabeticamente (mayor a menor)
            String[] datos=new String[listaLibros.size()];
            
            for (int i=0; i<listaLibros.size(); i++){
                ReferenciaLibro libro = listaLibros.get(i);
                datos[i]=libro.imprimirEnBusqueda();
            }
            
            Arrays.sort(datos);
            Collections.reverse(Arrays.asList(datos)); 
            miVista.actualizaListaLibros(datos);
            miVista.mostrarMensajeExito("Los libros se han ordenado alfabeticamente de mayor a menor");
        }
        else if (modoOrdenacion==3) {
            ReferenciaLibro[] datos=convertirArray(listaLibros);
            
            Arrays.sort(datos);
            miVista.actualizaListaLibros(datos);
            miVista.mostrarMensajeExito("Los libros se han ordenado segun su precio de menor a mayor");
        }
        else if (modoOrdenacion==4){
            ReferenciaLibro[] datos=convertirArray(listaLibros);
            
            for (int i=0; i<listaLibros.size(); i++){
                datos[i]=listaLibros.get(i);
            }
            
            Arrays.sort(datos);
            Collections.reverse(Arrays.asList(datos));
            miVista.actualizaListaLibros(datos);
            miVista.mostrarMensajeExito("Los libros se han ordenado segun su precio de mayor a menor");
        }
    }
   
    private ReferenciaLibro[] convertirArray(ArrayList<ReferenciaLibro> listaLibros){
        ReferenciaLibro[] listaNueva = new ReferenciaLibro[listaLibros.size()];
        return listaLibros.toArray(listaNueva);
    }
}
