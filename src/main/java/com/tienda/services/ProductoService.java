package com.tienda.services;

import com.tienda.domain.Producto;
import java.util.List;

public interface ProductoService {

    //Metodo para recuperar todos los registros de la tabla
    //producto, solo activos... o todos.
    public List<Producto> getProductos(boolean activo);
   
   // Se obtiene un Producto, a partir del id de un producto
    public Producto getProducto(Producto producto);
    
    // Se inserta un nuevo producto si el id del producto esta vacío
    // Se actualiza un producto si el id del producto NO esta vacío
    public void save(Producto producto);
    
    // Se elimina el producto que tiene el id pasado por parámetro
    public void delete(Producto producto);
    
    //Recupera el listado d eproducto que tienen un precio de un rango
    public List<Producto> consultaAmpliada(
            double precioInf, double precioSup);
    
    //Recupera el listado de producto que tienen un precio de un rango usando JPQL
    public List<Producto> consultaJPQL(double precioInf, double precioSup);
    
    //Recupera el listado de producto que tienen un precio de un rango usando SQL
    public List<Producto> consultaSQL(double precioInf, double precioSup);
    
    public List<Producto> consultaPorNombre(String Nombre);
    
}

    

