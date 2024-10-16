package com.tienda.services.impl;

import com.tienda.dao.CategoriaDao;
import com.tienda.domain.Categoria;
import com.tienda.services.CategoriaService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl 
        implements CategoriaService{
    
    @Autowired
    private CategoriaDao categoriaDao;
    
    @Override
    @Transactional(readOnly=true) //Genera una transaccion
    public List<Categoria> getCaterias(boolean activo) {
        var lista=categoriaDao.findAll();
        
        if (activo) {
            //Se debe eliminar los inactivos
            lista.removeIf(c -> !c.isActivo());
        }
        
        return lista;
    }
    
}
