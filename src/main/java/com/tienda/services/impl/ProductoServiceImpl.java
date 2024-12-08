package com.tienda.services.impl;

import com.tienda.dao.ProductoDao;
import com.tienda.domain.Producto;
import com.tienda.services.ProductoService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl 
        implements ProductoService{
    
    @Autowired
    private ProductoDao productoDao;
    
    @Override
    @Transactional(readOnly=true) //Genera una transaccion
    public List<Producto> getProductos(boolean activo) {
        var lista=productoDao.findAll();
        
        if (activo) {
            //Se debe eliminar los inactivos
            lista.removeIf(c -> !c.isActivo());
        }
        
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    

    @Override
    @Transactional
    public void save(Producto producto) {
        productoDao.save(producto);
    }
    
    @Override
    @Transactional
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }
    
    //Recupera el lsitado d eproducto que tienen un precio de un rango
    @Override
    @Transactional(readOnly = true)
    public List<Producto> consultaAmpliada(double precioInf, double precioSup){
        return productoDao.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
    }
    
    //Recupera el listado de producto que tienen un precio de un rango usando JPQL
    @Override
    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(double precioInf, double precioSup){
        return productoDao.consultaJPQL(precioInf, precioSup);
    }
    
    //Recupera el listado de producto que tienen un precio de un rango usando SQL
    @Override
    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(double precioInf, double precioSup){
        return productoDao.consultaSQL(precioInf, precioSup);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> consultaPorNombre(String Nombre){
        return productoDao.findByDescripcionContainingOrderByDescripcion(Nombre);
        
    }
}
