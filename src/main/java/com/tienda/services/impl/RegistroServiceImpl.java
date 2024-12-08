package com.tienda.services.impl;

import com.tienda.domain.Usuario;
import com.tienda.services.CorreoService;
import com.tienda.services.FirebaseStorageService;
import com.tienda.services.RegistroService;
import com.tienda.services.UsuarioService;
import jakarta.mail.MessagingException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistroServiceImpl implements RegistroService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CorreoService correoService;

    @Override
    public Model crear(Model model, Usuario usuario) throws MessagingException {
        String mensaje;
        if (!usuarioService.existeUsuarioPorUsernameOCorreo(
                usuario.getUsername(), usuario.getCorreo())) {
            //Todo bien...
            usuario.setPassword(demeClave());
            usuario.setActivo(false);
            usuarioService.save(usuario, true);
            enviarCorreoActivar(usuario);
            mensaje = messageSource
                    .getMessage(
                            "registro.mensaje.activacion.ok",
                            null,
                            Locale.getDefault());
            mensaje = String.format(mensaje, usuario.getCorreo());

        } else {
            //Algo ya existe en usuarios
            mensaje = messageSource
                    .getMessage(
                            "registro.mensaje.usuario.o.correo",
                            null,
                            Locale.getDefault());
            mensaje = String.format(mensaje,
                    usuario.getUsername(),
                    usuario.getCorreo());
        }

        model.addAttribute("titulo", messageSource
                .getMessage("registro.activar", null, Locale.getDefault()));
        model.addAttribute("mensaje", mensaje);

        return model;
    }

    private void enviarCorreoActivar(Usuario usuario) throws MessagingException {
        String texto = messageSource
                .getMessage(
                        "registro.correo.activar",
                        null,
                        Locale.getDefault());
        texto = String.format(texto,
                usuario.getNombre(),
                usuario.getApellidos(),
                "localhost",
                usuario.getUsername(),
                usuario.getPassword());
        String asunto = messageSource
                .getMessage(
                        "registro.mensaje.activacion",
                        null,
                        Locale.getDefault());
        correoService.enviarCorreoHtml(usuario.getCorreo(), asunto, texto);
    }

    private String demeClave() {
        String clave = "";
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 40; i++) {
            clave += base.charAt((int) (Math.random() * base.length()));
        }
        return clave;
    }

    @Override
    public Model activar(Model model, String username, String password) {
        Usuario usuario = usuarioService.getUsuarioPorUsernameYPassword(username, password);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("titulo", messageSource
                    .getMessage("registro.activar", null, Locale.getDefault()));
            model.addAttribute("mensaje", messageSource
                    .getMessage("registro.activar.error", null, Locale.getDefault()));
        }
        return model;
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    
    @Override
    public void activar(Usuario usuario, MultipartFile imagenFile) {
        var codigo = new BCryptPasswordEncoder();
        usuario.setPassword(
                codigo.encode(usuario.getPassword()));
        if (!imagenFile.isEmpty()) {
            String ruta=firebaseStorageService
                    .cargaImagen(imagenFile,
                            "usuarios",
                            usuario.getIdUsuario());
            usuario.setRutaImagen(ruta);
        }
        usuarioService.save(usuario, true);
    }

}
