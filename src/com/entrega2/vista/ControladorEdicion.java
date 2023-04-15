/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega2.vista;

import com.entrega2.main.Main;
import com.entrega2.modelo.Biblioteca;
import com.entrega2.modelo.ReferenciaLibro;
import java.util.ArrayList;

/**
 * Clase que representa el controlador de la aplicación, consulta/actualiza los elementos de la vista e informacion del modelo
 * @author jagarci
 * @author mgonzal
 */
public class ControladorEdicion {
    private Biblioteca miModelo; // Biblioteca de la aplicación
    private VistaEdicion miVista; // VistaEdicion de la aplicación
    
    /**
     * Constructor del controlador. Inicializa el modelo
     * @param vista VistaEdicion de la aplicacion. 
     */
    public ControladorEdicion(VistaEdicion vista){
        this.miVista=vista;
        this.miModelo=Main.getBiblioteca();
        ArrayList<ReferenciaLibro> listaLibros=miModelo.getListaLibros();
        if (listaLibros.size()>0){
            miVista.actualizaListaLibros(listaLibros);
        }
    }
    
    /**
     * Procesa el evento de guardar/actualizar un libro
     * Obtiene toda la informacion necesaria para crear un libro y comprueba que esta informacion sea correcta
     * Si alguna de esta informacion no es correcta manda a mensaje de error a la vista
     */
    public void procesarEventoGuardar(){
        String titulo=miVista.getTituloLibro();
        String isbn = miVista.getISBNLibro();
        String descripcion = miVista.getDescripcionLibro();
        String estado=miVista.getEstadoLibro();
        boolean leido=miVista.isLeidoLibro();
        int unidades = miVista.getUnidadesLibro();
        int decimales = miVista.getDecimalesLibro();
        String mensajeError="";
        boolean error=false;
        ReferenciaLibro libro=new ReferenciaLibro(titulo, descripcion, isbn, estado, unidades, decimales, leido); // libro nuevo


        if (libro.compruebaLibro(titulo, isbn)==0){ // Comprueba que los caracteres son los que corresponden para el titulo y el isbn
            int indice = miVista.elementoListaSeleccionado();
 
            if (indice!=-1){ // Si hay un libro seleccionado
                
                if (indice>=miModelo.getListaLibros().size()){
                    miVista.mostrarMensajeError("El indice del libro seleccionado es incorrecto");
                    error=true;
                }
                
                if (!miModelo.getLibro(indice).equals(libro)){ // Si los isbn son iguales entonces puedo cambiar el libro
                    miVista.mostrarMensajeError("No puedes modificar el ISBN de un libro existente");
                    error=true;
                }
                else {
                    if (miModelo.reemplazarLibro(libro, indice)){ // Actualizo la informacion del libro seleccionado
                        miVista.mostrarMensajeExito("El libro se ha actualizado correctamente");
                    }
                    else {
                        miVista.mostrarMensajeError("Ya existe un libro con el mismo ISBN en la lista.");
                    }
                }
            }
            else { // Si no hay ningun libro seleccionado
                if (miModelo.addLibro(libro)){ // Añade el libro a la lista
                    miVista.mostrarMensajeExito("El libro se ha añadido a la lista satisfactoriamente");
                } 
                else {
                    miVista.mostrarMensajeError("Ya existe un libro con el mismo ISBN en la lista.");
                }
            }
            
            if (!error){ // Si no ha habido errores entonces actualiza la lista de libros
                miVista.actualizaListaLibros(miModelo.getListaLibros());
                miVista.limpiarCamposTexto();
                
            }  
        }
        if (libro.compruebaLibro(titulo, isbn)==1){
            mensajeError += "El titulo del libro debe tener entre 1 y 20 caracteres. ";
            error=true;
        }
        if (libro.compruebaLibro(titulo, isbn)==2){
            mensajeError += "El isbn del libro debe tener entre 1 y 13 caracteres. ";
            error=true;
        }
        
        if (error){
            miVista.mostrarMensajeError(mensajeError);
        }
    }
    
    /**
     * Procesa el evento generado para editar un libro.
     * Para editar un libro debe seleccionar un libro de la lista
     */
    public void procesarEventoEditar(){
        int indice=miVista.elementoListaSeleccionado();
        if (indice!=-1){ // Si hay elemento seleccionado
            miVista.editarLibro(miModelo.getLibro(indice));
            miVista.mostrarMensajeExito("Editando libro...");
        }
        else {
            if (miModelo.getListaLibros().isEmpty()){ // Si no hay libros
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
        int indice=miVista.elementoListaSeleccionado();
        if (indice!=-1){ // Si hay un elemento seleccionado
            miModelo.borraLibro(indice);
            miVista.actualizaListaLibros(miModelo.getListaLibros());
            miVista.mostrarMensajeExito("El libro se ha borrado de la lista");
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
    
    public void procesarRedirrecionInicial(){
        Main.getGestorVistas().mostrarVistaInicial();
    }
         
    public void procesarRedirrecionBusqueda(){
        Main.getGestorVistas().mostrarVistaBusqueda();
    }
}
