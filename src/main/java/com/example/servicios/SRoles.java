/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.servicios;

import com.example.modelos.Roles;

/**
 *
 * @author meyam
 */
public interface SRoles {

    Roles findByName(String name);
}
