package tn.esprit.usermanagement.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.usermanagement.entities.AppUser;
import tn.esprit.usermanagement.services.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class AppUserController {


    @Autowired
    private AppUserService appUserService;

    @PostMapping("CreateUser")
    public ResponseEntity<AppUser> CreateUser(@RequestBody AppUser appUser)
    {
        return new ResponseEntity<>(appUserService.CreateUser(appUser), HttpStatus.OK);
    }

    @GetMapping("getusers")
    public ResponseEntity<List<AppUser>> getallusers()
    {
        return new ResponseEntity<>(appUserService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("edituser")
    public ResponseEntity<AppUser> edituser(@RequestBody AppUser appUser)
    {
        return new ResponseEntity<>(appUserService.UpdateUser(appUser), HttpStatus.OK);
    }

    @PutMapping("deleteuserbyid/{idUser}")
    public ResponseEntity<?> edituser(@PathVariable("idUser")int idUser)
    {
        appUserService.removeUser(idUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
