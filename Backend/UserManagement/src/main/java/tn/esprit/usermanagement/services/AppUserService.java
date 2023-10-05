package tn.esprit.usermanagement.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.usermanagement.entities.AppUser;
import tn.esprit.usermanagement.repository.AppUserRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AppUserService {


    @Autowired
    private AppUserRepository appUserRepository;


    public AppUser CreateUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    public AppUser UpdateUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }


    public void removeUser(int idAppuser)
    {
        appUserRepository.deleteById(idAppuser);

    }

    public List<AppUser> getAllUsers()
    {
        return appUserRepository.findAll();
    }




}
