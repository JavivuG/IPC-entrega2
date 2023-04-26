/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega2.modelo;

import java.util.ArrayList;

/**
 * Clase que representa el modelo de la aplicacion almacena el arraylist de libros
 * @author jagarci
 * @author mgonzal
 */
public class Biblioteca {
    private ArrayList<ReferenciaLibro> listaLibros;
    private ArrayList<ReferenciaLibro> listaLibrosBuscada;
    
    /**
     * Constructor del modelo de la aplicación
     */
    public Biblioteca(){
        listaLibros=new ArrayList<>();
        listaLibrosBuscada=new ArrayList<>();
    }
    
    /**
     * Añade un libro a la lista
     * @param libro Libro que se desea añadir
     * @return -1 si el libro ya existe en la lista
     */
    public boolean addLibro(ReferenciaLibro libro){
       if (libroYaExiste(libro)){
           return false;
       }
       listaLibros.add(libro);
       return true;
    }
    
    // Comprueba si el libro ya existe en la lista
    private boolean libroYaExiste(ReferenciaLibro libro){
        for (int i=0; i<getListaLibros().size(); i++){
            if (libro.getISBN().equals(getListaLibros().get(i).getISBN())){
                return true;
            }
        }
        return false;
    }
    
    // Comprueba si el libro ya existe en la lista
    private boolean libroYaExiste(ReferenciaLibro libro, int indice){
        if (getListaLibros().size()==0){
            return false;
        }
        for (int i=0; i<getListaLibros().size(); i++){
            if (libro.getISBN().equals(getListaLibros().get(i).getISBN()) && i!=indice){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Devuelve la lista de los libros
     * @return lista de libros
     */
    public ArrayList<ReferenciaLibro> getListaLibros(){
        return listaLibros;
    }
    
    /**
     * Devuelve el libro en la posición indicada
     * @param indice Indice del libro que se desea obtener
     * @return libro en el indice que se indica
     */
    public ReferenciaLibro getLibro(int indice){
        return listaLibros.get(indice);
    }
    
    /**
     * Reemplaza un libro dado en el indice dado
     * @param libro Libro nuevo que se quiere introducir
     * @param indice Indice en el que se quiere introducir el nuevo libro
     * @return true si se ha reemplazado el libro, false si el libro ya existia y por tanto, no se ha reemplazado
     */
    public boolean reemplazarLibro(ReferenciaLibro libro, int indice){
        if (libroYaExiste(libro, indice)){
            return false;
        }
        listaLibros.set(indice, libro);
        return true;
    }
    
    /**
     * Borra el libro que esta en la posición indice
     * @param indice Posición del libro que se desea borrar
     * @return Libro que se ha borrado de la lista de libros
     */
    public ReferenciaLibro borraLibro(int indice){
        ReferenciaLibro libro=listaLibros.get(indice);
        listaLibros.remove(indice);
        return libro;
    }
    
    /**
     * Borra un libro de la lista de busqueda
     * @param libro Libro que se desea eliminar de la lista Buscada
     */
    public void borraLibroBusqueda(ReferenciaLibro libro){
        for (int i =0; i<listaLibrosBuscada.size(); i++){
            if (listaLibrosBuscada.get(i).equals(libro)){
                listaLibrosBuscada.remove(i);
            }
        }
    }
    
    /**
     * Devuelve el precio medio de los libros de la biblioteca
     * @return Precio medio de los libros de la biblioteca
     */
    public double getPrecioMedio(){
        double sumaPrecio=0;
        if (listaLibros.size()>0){
            for (int i=0; i<listaLibros.size(); i++){
                sumaPrecio+=listaLibros.get(i).getPrecio();
            }
            return sumaPrecio/listaLibros.size();
        }
        return 0;
    }
    
    /**
     * Devuelve la cantidad de libros leido
     * @return Cantidad de libros leidos
     */
    public int getCantidadLibrosLeidos(){
        int contadorLibrosLeidos=0;
        for (int i=0; i<listaLibros.size(); i++){
            if (listaLibros.get(i).isLeido()){
                contadorLibrosLeidos++;
            }
        }
        return contadorLibrosLeidos;
    }
    
    /**
     * Establece la lista Buscada con los libros que coincidan con la busqueda correspondiente
     * @param lista Lista Buscada
     */
    public void setListaLibrosBuscada(ArrayList<ReferenciaLibro> lista){
        this.listaLibrosBuscada=lista;
    }
    
    /**
     * Devuelve la lista de libros buscado
     * @return Lista de libros buscados
     */
    public ArrayList<ReferenciaLibro> getListaLibrosBuscada(){
        return listaLibrosBuscada;
    }
    

    

}
