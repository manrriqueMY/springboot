package com.example.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.modelos.Users;
import com.example.servicios.SUsuario;

@Service
public class DISUsuario implements UserDetailsService {

    @Autowired
    SUsuario userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return DIUsuario.build(user);
    }

}
