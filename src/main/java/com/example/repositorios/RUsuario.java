/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repositorios;

import com.example.modelos.Users;
import com.example.servicios.AbstractDao;
import com.example.servicios.SUsuario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author meyam
 */
@Transactional
@Repository("SUsuario")
public class RUsuario extends AbstractDao<Integer, Users> implements SUsuario {

    @Override
    public Users findByUsername(String username) {
        Criteria c = createEntityCriteria().add(Restrictions.eq("username", username));
        Users us = (Users) c.uniqueResult();
        return us;
    }

    @Override
    public Boolean existsByUsername(String username) {
        List c = createEntityCriteria().add(Restrictions.eq("username", username)).list();
        return c.isEmpty();
    }

    @Override
    public Boolean existsByEmail(String email) {
        List c = createEntityCriteria().add(Restrictions.eq("email", email)).list();
        return c.isEmpty();
    }

    @Override
    public List<Users> lusuario() {
        return createEntityCriteria().list();
    }

    @Override
    public void save(Users user) {
        persist(user);
    }
    
}
