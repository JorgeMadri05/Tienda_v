package com.tienda.services;

import com.tienda.domain.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface RegistroService {
    
    public Model crear(Model model, Usuario usuario) throws MessagingException;

    public Model activar(Model model, String username, String password);

    public void activar(Usuario usuario, MultipartFile imagenFile);

}