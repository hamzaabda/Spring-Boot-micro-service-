package tn.esprit.usermanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.usermanagement.entities.Role;
import tn.esprit.usermanagement.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles()
    {
        return roleRepository.findAll();
    }

    public Optional<Role> findbyid(Long idrole)
    {
        return roleRepository.findById(idrole);
    }

}
