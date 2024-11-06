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
    
}
