package com.tienda.services;

import com.tienda.domain.Producto;
import java.util.List;

public interface ProductoService {

    //Metodo para recuperar todos los registros de la tabla
    //producto, solo activos... o todos.
    public List<Producto> getCaterias(boolean activo);
    
    // Se obtiene un listado de productos en un List
    public List<Producto> getProductos(boolean activos);
    
   // Se obtiene un Producto, a partir del id de un producto
    public Producto getProducto(Producto producto);
    
    // Se inserta un nuevo producto si el id del producto esta vacío
    // Se actualiza un producto si el id del producto NO esta vacío
    public void save(Producto producto);
    
    // Se elimina el producto que tiene el id pasado por parámetro
    public void delete(Producto producto);
}

    

