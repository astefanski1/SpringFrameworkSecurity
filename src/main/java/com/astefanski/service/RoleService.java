package com.astefanski.service;

import com.astefanski.exceptions.RoleDoesNotExistsException;
import com.astefanski.model.Role;
import com.astefanski.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(RoleDoesNotExistsException::new);
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

}
