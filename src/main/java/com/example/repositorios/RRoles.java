/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repositorios;

import com.example.modelos.Roles;
import com.example.servicios.AbstractDao;
import com.example.servicios.SRoles;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author meyam
 */
@Transactional
@Repository("SRoles")
public class RRoles extends AbstractDao<Integer, Roles> implements SRoles {

    @Override
    public Roles findByName(String name) {
        Criteria c = createEntityCriteria().add(Restrictions.eq("name", name));
        Roles rol = (Roles) c.uniqueResult();
        return rol;
    }

}
