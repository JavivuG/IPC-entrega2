/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega1.vista;

import com.entrega1.modelo.Modelo;
import com.entrega1.modelo.ReferenciaLibro;

/**
 * Clase que representa el controlador de la aplicación, consulta/actualiza los elementos de la vista e informacion del modelo
 * @author jagarci
 * @author mgonzal
 */
public class Controlador {
    private Modelo miModelo; // Modelo de la aplicación
    private Vista miVista; // Vista de la aplicación
    
    /**
     * Constructor del controlador. Inicializa el modelo
     * @param vista Vista de la aplicacion. 
     */
    public Controlador(Vista vista){
        this.miVista=vista;
        this.miModelo=new Modelo();
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
        
        boolean error=false;

        if (compruebaLibro(titulo, isbn)){ // Comprueba que los caracteres son los que corresponden para el libro y el isbn
            ReferenciaLibro libro=new ReferenciaLibro(titulo, descripcion, isbn, estado, unidades, decimales, leido); // libro nuevo
            int indice = miVista.elementoListaSeleccionado();
            if (indice!=-1){ // Si hay un libro seleccionado
                if (!miModelo.getLibro(indice).equals(libro)){ // Si los isbn son iguales entonces puedo cambiar el libro
                    miVista.mostrarMensajeError("No puedes modificar el ISBN de un libro existente");
                    error=true;
                }
                else {
                    miModelo.reemplazarLibro(libro, indice); // Actualizo la informacion del libro seleccionado
                    miVista.mostrarMensajeExito("El libro se ha actualizado correctamente");
                }
            }
            else { // Si no hay ningun libro seleccionado
                miModelo.addLibro(libro); // Añade el libro a la lista
                miVista.mostrarMensajeExito("El libro se ha añadido a la lista satisfactoriamente");
            }
            
            if (!error){ // Si no ha habido errores entonces actualiza la lista de libros
                miVista.actualizaListaLibros(miModelo.getListaLibros());
                miVista.limpiarCamposTexto();
                
            }  
        }
    }
    
    // Comprueba que la información para un libro es correcta
    private boolean compruebaLibro(String titulo, String isbn){
        boolean guardarLibro = true;
        String error = "";

        if (titulo.length() < 1 || titulo.length() > 20){
            error += "El titulo del libro debe tener entre 1 y 20 caracteres. ";
        }
        if (isbn.length() < 1 || isbn.length() > 13){
            error += "El isbn del libro debe tener entre 1 y 13 caracteres. ";
        }
        if (libroYaExiste(isbn)){ // Si el libro ya existe en la lista de libros
            error += "Ya existe un libro con el mismo ISBN en la lista.";
        }
        if (!error.equals("")){
            guardarLibro = false;
            miVista.mostrarMensajeError(error);
        }
        return guardarLibro;
    }
    
    // Comprueba si el libro ya existe en la lista
    private boolean libroYaExiste(String isbn){
        for (int i=0; i<miModelo.getListaLibros().size(); i++){
            if (isbn.equals(miModelo.getLibro(i).getISBN()) && i!=miVista.elementoListaSeleccionado()){
                return true;
            }
        }
        return false;
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
            if (miModelo.getListaLibros().size()==0){ // Si no hay libros
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
            if (miModelo.getListaLibros().size()==0){
                miVista.mostrarMensajeError("No hay libros en la lista");
            }
            else {
                miVista.mostrarMensajeError("Debe seleccionar un libro de la lista");
            }
        }
    }
}
