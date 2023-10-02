package tn.esprit.Authentication.Controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.Authentication.Services.RoleService;
import tn.esprit.Authentication.entities.Role;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin("http://localhost:4300/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("getroles")
    public List<Role> allroles()
    {
        return roleService.getRoles();
    }
}
