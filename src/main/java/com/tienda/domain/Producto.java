package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="producto")
public class Producto implements Serializable{
    private static final long serialVersionUID = 1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long idProducto; 
    //private Long idCategoria;Se comenta por q ahora el idCategoria esta en el  
    private String descripcion;
    private String detalle;
    private double precio;
    private int existencias;
    private String rutaImagen;
    private boolean activo;   
    
    
    @ManyToOne
    @JoinColumn(name="id_categoria")
    private Categoria categoria;
}


