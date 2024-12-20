package com.tienda.services.impl;

import com.tienda.dao.UsuarioDao;
import com.tienda.domain.Rol;
import com.tienda.domain.Usuario;
import com.tienda.services.UsuarioDetailsService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsServiceImpl
        implements UsuarioDetailsService, UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Autowired
    private HttpSession session;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        //Se busca el usuario en la tabla de usuarios... por username
        Usuario usuario = usuarioDao.findByUsername(username);

        //Se valida si el usuario se encontró
        if (usuario ==null) {
            //El usuario NO se encontró
            throw new UsernameNotFoundException(username);
        }
        
        //Si esto se ejecuta es que SI se encontró
        session.removeAttribute("imagenUsuario");
        session.setAttribute("imagenUsuario", usuario.getRutaImagen());
        
        //Se procede a recuperar los roles que tiene el usuario...
        
        ArrayList roles = new ArrayList<GrantedAuthority>();
        
        //Se recorre el array list de los roles del usuario
        for (Rol r: usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_"+r.getNombre()));
        }
        
        return new User(usuario.getUsername(),usuario.getPassword(),roles);
    }

}
