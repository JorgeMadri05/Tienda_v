package com.tienda.services;

import com.tienda.domain.Categoria;
import java.util.List;

public interface CategoriaService {

    //Metodo para recuperar todos los registros de la tabla
    //categoria, solo activos... o todos.
    public List<Categoria> getCaterias(boolean activo);
    
}
