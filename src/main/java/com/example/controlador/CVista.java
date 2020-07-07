/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controlador;

import com.example.modelos.Roles;
import com.example.modelos.Users;
import com.example.seguridad.DIUsuario;
import com.example.seguridad.JwtResponse;
import com.example.seguridad.JwtUtils;
import com.example.seguridad.MessageResponse;
import com.example.servicios.SRoles;
import com.example.servicios.SUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author meyam
 */
@Controller
public class CVista {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SUsuario userRepository;

    @Autowired
    SRoles roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @RequestMapping("/")
    public @ResponseBody
    String index() {
        return "Spring boot";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(Users loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        DIUsuario userDetails = (DIUsuario) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(Users signUpRequest, Set<String> roles) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Users user = new Users();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = roles;
        List<Roles> rol = new ArrayList<>();

        if (strRoles == null) {
            Roles userRole = roleRepository.findByName("ROLE_USER");
            rol.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Roles userRole = roleRepository.findByName(role);
                if (userRole != null) {
                    rol.add(userRole);
                }
            });
        }

        user.setRolesList(rol);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
