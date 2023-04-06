/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entrega1.modelo;

/**
 * Clase que representa un libro
 * @author javivu
 * @author mgonzal
 */
public class ReferenciaLibro {
    private String titulo;
    private String isbn;
    private String descripcion;
    private int unidades;
    private int decimales;
    private String estado;
    private boolean leido;
    
    /**
     * Constructor de referenciaLibro
     * @param titulo Titulo del libro
     * @param descripcion Descripcion de un libro
     * @param isbn ISBN de un libro. No se puede modificar despues de inicializar el libro
     * @param estado Estado  de un libro. Puede ser Malo, Regular o Bueno
     * @param unidades Unidades del precio del libro. Ejemplo: si el precio es 14.60 unidades será 14
     * @param decimales Decimales del precio del libro. Ejemplo: si el precio es 14.60 decimales será 60
     * @param leido Si el libro se ha leido o no
     */
    public ReferenciaLibro(String titulo, String descripcion, String isbn, String estado, int unidades, int decimales, boolean leido){
        this.isbn = isbn;
        setTitulo(titulo);
        setDescripcion(descripcion);
        setLeido(leido);
        setUnidades(unidades);
        setDecimales(decimales);
        setEstado(estado);
    }
    
    /**
     * Devuelve el titulo de un libro
     * @return titulo del libro
     */
    public String getTitulo(){
        return titulo;
    }
    
    /**
     * Devuelve el isbn de un libro
     * @return isbn del libro
     */
    public String getISBN(){
        return isbn;
    }
    
    /**
     * Devuelve la descripción de un libro
     * @return descripción del libro
     */
    public String getDescripcion(){
        return descripcion;
    }
    
    /**
     * Devuelve las unidades del precio del libro
     * @return Unidades del precio del libro
     */
    public int getUnidadesLibro(){
        return unidades;
    }
    
    /**
     * Devuelve los decimales del precio del libro
     * @return Decimales del precio del libro
     */
    public int getDecimalesLibro(){
        return decimales;
    }
    
    /**
     * Devuelve el precio del libro
     * @return Precio del libro
     */
    public double getPrecio(){
        int n = (int)Math.log10(getDecimalesLibro())+1;
        if (n==1){
            n=2;
        }
        Double decimalesDouble = (double)getDecimalesLibro()/(Math.pow(10, n));
        return getUnidadesLibro()+decimalesDouble;
    }
    
    /**
     * Devuelve el estado del libro. Puede ser Malo, Bueno o Regular
     * @return Estaado del libro
     */
    public String getEstado(){
        return estado;
    }
    
    /**
     * Devuelve si un libro ha sido leido o no
     * @return Si el libro ha sido leido o no
     */
    public boolean isLeido(){
        return leido;
    }
    
    /**
     * Proporciona un titulo para el libro
     * @param titulo Titulo del libro
     */
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    /**
     * Proporciona una descripción para el libro
     * @param descripcion Descripción del libro
     */
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    /**
     * Proporciona un nuevo valor para las unidades del precio del libro
     * @param unidades Unidades del precio del libro
     */
    public void setUnidades(int unidades){
        this.unidades=unidades;
    }
    
    /**
     * Proporciona un nuevo valor para los decimales del precio del libro
     * @param decimales Decimales del precio del libro
     */
    public void setDecimales(int decimales){
        this.decimales=decimales;
    }
    
    /**
     * Proporciona un nuevo estado para el libro
     * @param estado Estado del libro
     */
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    /**
     * Proporciona un valor para saber si el libro se ha leido o no
     * @param leido Valor que indica si el libro se ha leido o no
     */
    public void setLeido(boolean leido){
        this.leido = leido;
    }
    
    /**
     * Proporciona un String para representar el libro
     * @return String que representa al libro
     */
    @Override
    public String toString(){
        return "Titulo:"+getTitulo()+" ISBN:"+getISBN();
    }
    
    /**
     * Comprueba un libro es igual que otro
     * @param o Libro con el que se quiere comprobar 
     * @return Devuelve true si los libros son iguales false de lo contrario
     */
    @Override
    public boolean equals(Object o){
        if (o instanceof ReferenciaLibro){
            String isbnOtroLibro = ((ReferenciaLibro) o).getISBN();
            if (getISBN().equals(isbnOtroLibro)){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
    }
     
}
