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
    
    /**
     * Constructor del modelo de la aplicación
     */
    public Biblioteca(){
        listaLibros=new ArrayList<>();
    }
    
    /**
     * Añade un libro a la lista
     * @param libro Libro que se desea añadir
     * @returns -1 si el libro ya existe en la lista
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
     */
    public void borraLibro(int indice){
        listaLibros.remove(indice);
    }
    
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
    
    public int getCantidadLibrosLeidos(){
        int contadorLibrosLeidos=0;
        for (int i=0; i<listaLibros.size(); i++){
            if (listaLibros.get(i).isLeido()){
                contadorLibrosLeidos++;
            }
        }
        return contadorLibrosLeidos;
    }
    

}
