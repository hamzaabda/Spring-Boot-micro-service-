package tn.esprit.Authentication.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.Repository.RoleRepository;
import tn.esprit.Authentication.entities.Role;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles()
    {
        return roleRepository.findAll();
    }

}
