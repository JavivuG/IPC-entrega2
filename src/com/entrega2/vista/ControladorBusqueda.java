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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Clase que representa el controlador de la vista de Busqueda de libros
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
    
    /**
     * Cambia a la vista Inicial
     */
    public void procesarRedirrecionInicial(){
        Main.getGestorVistas().mostrarVistaInicial();
    }
    
    /**
     * Cambia a la vista de Edicion
     */
    public void procesarRedirrecionEdicion(){
        Main.getGestorVistas().mostrarVistaEdicion(null);
    }
    
    /**
     * Lee los libros existentes de un fichero del sistema e importa los correctos en la biblioteca
     * Es posible que el fichero no sea correcto (No cumpla con el formato indicado)
     * @param ficheroImportar fichero que contiene los libros que se van a importar
     */
    public void procesarEventoCargar(File ficheroImportar){
        try (BufferedReader reader = new BufferedReader(new FileReader(ficheroImportar))) {
            int contadorLibros=0, contadorLineas=0;
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
                    ReferenciaLibro libro=new ReferenciaLibro(datosLibro[0], datosLibro[1],
                            datosLibro[2], datosLibro[3], precio, leido);
                    if (miModelo.addLibro(libro)){
                        contadorLibros++;   
                    }
                }
                contadorLineas++;
            }
            if (contadorLibros>0){ // Si se han añadido libros entonces lo ordeno
                procesarEventoOrdenar(miVista.getOrdenarComboBoxIndex());
                miVista.mostrarMensaje("Se han añadido "+ contadorLibros+" libros de "+ contadorLineas+ " entradas del fichero", false);
            }
            else {
                if (ficheroNoEsCorrecto){
                    miVista.mostrarMensaje("El fichero no es correcto", true);
                }
                else {
                    miVista.mostrarMensaje("No se han podido añadir nuevos libros", true);
                }
            }
            reader.close();
        }
        catch (IOException e){
            miVista.mostrarMensaje("No se ha encontrado el fichero", true);
        }
    }
    
    /**
     * Comprueba que la lista este o no vacia antes de abrir la ventana de exportar libros
     * Esto se hace para evitar que se abra la ventana de exportar si no hay libros en la biblioteca
     */
    public void isBibliotecaVacia(){
        if( miModelo.getListaLibros().isEmpty()){
            miVista.mostrarMensaje("No hay libros en la lista", true);
        }
        else {
            miVista.abreVentanaExportar();
        }
    }
    
    /**
     * Exporta los libros existentes en la biblioteca a un fichero .txt
     * El fichero exportado tiene el mismo formato que cuando se importan 
     * @param fichero Fichero al que se exportan los libros de la biblioteca
     */
    public void procesarEventoExportar(File fichero){
        if (miModelo.getListaLibros().isEmpty()){
            miVista.mostrarMensaje("No hay libros en la lista", true);
        }
        else {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero))) {

                ArrayList<ReferenciaLibro> listaLibros=miModelo.getListaLibros();
                for (int i=0; i<listaLibros.size(); i++){
                    ReferenciaLibro libro=listaLibros.get(i);
                    escritor.write(libro.getTitulo()+";"+libro.getDescripcion()+";"
                            +libro.getISBN()+";"+libro.getEstado()+";"+libro.getPrecio()+";"+libro.isLeido()+"\n");
                }

                miVista.mostrarMensaje("Se han exportado "+ listaLibros.size()+ " libros", false);

                escritor.close();
            } catch (IOException e){
                miVista.mostrarMensaje("No se puedo crear el fichero de exportación", true);
            }            
        }

    }
    
    /**
     * Cambia a la vista de Edicion con el libro seleccionado cargado para editarlo
     */
    public void procesarEventoEditar(){
        int indice=getIndiceLibro(miVista.getLibro());
        if (indice!=-1){
            Main.getGestorVistas().mostrarVistaEdicion(miModelo.getListaLibros().get(indice));
        }
        else {
            if (miModelo.getListaLibros().isEmpty()){
                miVista.mostrarMensaje("No hay libros en la lista", true);
            }
            else {
                miVista.mostrarMensaje("Debe seleccionar un libro de la lista", true); 
            }
        }
    }
        
     /**
     * Procesa el evento generado para borrar un libro de la lista
     * Debe haber un libro seleccionado
     */
    public void procesarEventoBorrar(){
         int indice=getIndiceLibro(miVista.getLibro());

        if (indice!=-1){ // Si hay un elemento seleccionado
            ReferenciaLibro libroBorrado=miModelo.borraLibro(indice);
            miModelo.borraLibroBusqueda(libroBorrado);
            procesarEventoOrdenar(miVista.getOrdenarComboBoxIndex());
            miVista.mostrarMensaje("El libro \""+ libroBorrado.getTitulo() +"\" se ha borrado de la lista", false);
        }
        else { // Si no hay ningun elemento seleccionado
            if (miModelo.getListaLibros().isEmpty()){
                miVista.mostrarMensaje("No hay libros en la lista", true);
            }
            else {
                miVista.mostrarMensaje("Debe seleccionar un libro de la lista", true);
            }
        }
    }
    
    /**
     * Muestra un mensaje cuando el estado del ordenarComboBox cambia informando del cambio
     */
    public void cambioOrdenMensaje(){
        int modoOrdenacion=miVista.getOrdenarComboBoxIndex();
        switch (modoOrdenacion) {
            case 0:
                miVista.mostrarMensaje("Se ha restaurado el orden de los libros", false);
                break;
            case 1:
                miVista.mostrarMensaje("Los libros se han ordenado alfabeticamente de menor a mayor", false);
                break;
            case 2:
                miVista.mostrarMensaje("Los libros se han ordenado alfabeticamente de mayor a menor", false);
                break;
            case 3:
                miVista.mostrarMensaje("Los libros se han ordenado segun su precio de menor a mayor", false);
                break;
            case 4:
                miVista.mostrarMensaje("Los libros se han ordenado segun su precio de mayor a menor", false);
                break;
            default:
                miVista.mostrarMensaje("Opcion de ordenacion no valida", true);
        }               
        procesarEventoOrdenar(modoOrdenacion);
    }
    
    /**
     * Ordena los libros que se muestran en la lista de busqueda segun indique el ordenarComboBox
     * @param modoOrdenacion Modos de ordenacion para los libros de la lista puede ser: 0,1,2,3 o 4 
     */
    public void procesarEventoOrdenar(int modoOrdenacion){
        ArrayList<ReferenciaLibro> listaLibros;           
        String[] datos;
        ReferenciaLibro[] datosLibros;
       
        // Saber si se esta ante una busqueda o se esta mostrando la lista completa de todos los libros
        if (!miVista.getBuscarTextField().equals("") && miVista.isBuscarTextFieldFocused()){
            listaLibros=miModelo.getListaLibrosBuscada();
        }
        else {
            listaLibros=miModelo.getListaLibros();
        }
        
        switch (modoOrdenacion) {
            case 0: // Orden predefinido
                miVista.actualizaListaLibros(convertirArray(listaLibros));
                break;
            case 1: // Orden alfabeticamente (menor a mayor)        
                datos=convertirArrayStrings(listaLibros);

                Arrays.sort(datos);
                miVista.actualizaListaLibros(datos);
                break;
            case 2: // Orden alfabeticamente (mayor a menor)
                datos=convertirArrayStrings(listaLibros);

                Arrays.sort(datos);
                Collections.reverse(Arrays.asList(datos)); 
                miVista.actualizaListaLibros(datos);
                break;
            case 3: // Orden segun precios (menor a mayor)
                datosLibros=convertirArray(listaLibros);
                
                Arrays.sort(datosLibros); // Usa metodo compare de ReferenciaLibro
                miVista.actualizaListaLibros(datosLibros);
                break;
            case 4: // Orden segun precios (mayor a menor)
                datosLibros=convertirArray(listaLibros);
                
                Arrays.sort(datosLibros);
                Collections.reverse(Arrays.asList(datosLibros));
                miVista.actualizaListaLibros(datosLibros);
                break;
            default:
                miVista.mostrarMensaje("Opcion de ordenacion no valida", true);
        }
    }
    
    /**
     * Busca libros en la lista de libros de la vista de Busqueda
     */
    public void procesarEventoBuscar(){
        // Si indiceFiltrado==0 se buscara tanto en el ISBN como el titulo
        // Se buscara solo entre los titulos si indiceFiltrado==1,
        // Se buscara solo entre los ISBN de los libros si indiceFiltrado==2
        int indiceFiltrado=miVista.getFiltrarComboBoxIndex(); 
        boolean error=false;
        
        // Se convierte el texto a buscar para que ignore tildes, ñ, etc
        String textoABuscar=convertirCadena(miVista.getBuscarTextField());
        ArrayList<ReferenciaLibro> listaLibros=miModelo.getListaLibros();
        ArrayList<ReferenciaLibro> listaBuscada=new ArrayList<>();
        
        if (listaLibros.isEmpty()){
            miVista.mostrarMensaje("No hay libros en la lista", true);
            miVista.setFiltrarComboBoxIndex();
        }
        else if (!textoABuscar.equals("") && miVista.isBuscarTextFieldFocused()){
            switch (indiceFiltrado) {
                case 0:
                    for (ReferenciaLibro libro : listaLibros){
                        String titulo=convertirCadena(libro.getTitulo());
                        String isbn=convertirCadena(libro.getISBN());

                        if (titulo.contains(textoABuscar) || isbn.contains(textoABuscar)){
                            listaBuscada.add(libro);
                        }
                    }
                    break;
                case 1:
                    for (ReferenciaLibro libro : listaLibros){
                        String titulo=convertirCadena(libro.getTitulo());

                        if (titulo.contains(textoABuscar)){
                            listaBuscada.add(libro);
                        }
                    }                    
                    break;
                case 2:
                    for (ReferenciaLibro libro : listaLibros){
                        String isbn=convertirCadena(libro.getISBN());

                        if (isbn.contains(textoABuscar)){
                            listaBuscada.add(libro);
                        }
                    }                    
                    break;
                default:
                    miVista.mostrarMensaje("Opcion de filtrado no valida", true);
                    error=true;
            }

            if (!error){
                if (listaBuscada.isEmpty()){
                    miVista.mostrarMensaje("No se ha encontrado ningun libro", true);
                }
                else {
                    miVista.mostrarMensaje("Se han encontrado "+ listaBuscada.size() +" coincidencias con la busqueda", false);
                }

                miModelo.setListaLibrosBuscada(listaBuscada);
                procesarEventoOrdenar(miVista.getOrdenarComboBoxIndex());   
            }

        }
        else {
            miVista.mostrarMensaje("", false);
            miModelo.setListaLibrosBuscada(listaLibros);
            procesarEventoOrdenar(miVista.getOrdenarComboBoxIndex());
        }
        
    }
    
    // Convierte un ArrayList<ReferenciaLibro> a ReferenciaLibro[]
    private ReferenciaLibro[] convertirArray(ArrayList<ReferenciaLibro> listaLibros){
        ReferenciaLibro[] listaNueva = new ReferenciaLibro[listaLibros.size()];
        return listaLibros.toArray(listaNueva);
    }
    
    // Convierte ArrayList<ReferenciaLibro> a String[]
    private String[] convertirArrayStrings(ArrayList<ReferenciaLibro> listaLibros){
        String[] datos=new String[listaLibros.size()];
        for (int i=0; i<listaLibros.size(); i++){
            ReferenciaLibro libro = listaLibros.get(i);
            datos[i]=libro.imprimirEnBusqueda();
        }
        return datos;
    }
    
    // Ignora los tildes, ñ, mayusculas. (Se usa para que la busqueda sea más permisiva)
    private String convertirCadena(String texto){
        String palabraSinAcentos = Normalizer.normalize(texto.toLowerCase(), 
                Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return palabraSinAcentos;
 
    }
    
    // Busca el indice real de un libro en el modelo
    private int getIndiceLibro(String libro){
        if (libro!=null){
            for (int i=0;i<miModelo.getListaLibros().size(); i++){
                String libroBusqueda=miModelo.getLibro(i).imprimirEnBusqueda();
                if (libroBusqueda.equals(libro)){
                    return i;
                }
            }
        }
        return -1;
    }
}
