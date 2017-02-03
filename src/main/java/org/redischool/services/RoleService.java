package org.redischool.services;

import org.redischool.models.Role;
import org.redischool.services.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by raouf on 1/20/17.
 */
@Service
public class RoleService extends AbstractService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.roleRepository = repository;
    }

    @Transactional(readOnly = true)
    public Role findById(UUID id) {
        return roleRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role findByName(String name){return repository.findByName(name);}

}
