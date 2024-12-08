package com.tienda.services;


import jakarta.mail.MessagingException;

public interface CorreoService {
   
    //Envia un correo a un destinatario
    public void enviarCorreoHtml(String para, String asunto, String mensaje) 
            throws MessagingException;
   
}
