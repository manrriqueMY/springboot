/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.servicios;

import com.example.modelos.Users;
import java.util.List;

/**
 *
 * @author meyam
 */
public interface SUsuario {

    Users findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<Users> lusuario();

    void save(Users user);
}
