package com.tienda.dao;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ProductoDao 
        extends JpaRepository<Producto, Long>{
    
    //Recupera el lsitado d eproducto que tienen un precio de un rango
    public List<Producto> findByPrecioBetweenOrderByDescripcion(
            double precioInf, double precioSup);
    
    //Recupera el listado de producto que tienen un precio de un rango
    @Query(value="SELECT a FROM Producto a "
            + "WHERE a.precio BETWEEN :precioInf and :precioSup "
            + "ORDER BY a.descripcion ASC")
    public List<Producto> consultaJPQL(
            double precioInf, double precioSup);
    
    //Recupera el listado de producto que tienen un precio de un rango
    @Query(nativeQuery=true,value="SELECT * FROM producto a "
            + "WHERE a.precio BETWEEN :precioInf and :precioSup "
            + "ORDER BY a.descripcion ASC")
    public List<Producto> consultaSQL(
            double precioInf, double precioSup);
    
    public List<Producto> findByDescripcionContainingOrderByDescripcion(String Nombre);
    


}

