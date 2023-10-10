package tn.esprit.usermanagement.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.usermanagement.entities.AppUser;
import tn.esprit.usermanagement.entities.Role;
import tn.esprit.usermanagement.repository.AppUserRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AppUserService {


    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleService roleService;


    public AppUser CreateUser(AppUser appUser){
        return appUserRepository.save(appUser);
    }

    public AppUser CreateAdmin(AppUser appUser){
        List<Role> roles = roleService.getRoles();
        appUser.getRoles().add(roles.get(3)); // Role Admin
        return appUserRepository.save(appUser);
    }

    public AppUser UpdateUser(AppUser appUser, int userId) {
        AppUser user = getUserById(userId);

        user.setUsername(appUser.getUsername());
        user.setEmail(appUser.getEmail());
        user.setNom(appUser.getNom());
        user.setPrenom(appUser.getPrenom());
        user.setIsEnabled(appUser.getIsEnabled());
        user.setRoles(appUser.getRoles());
        user.setBirthdate(appUser.getBirthdate());
        user.setPhone(appUser.getPhone());
        user.setProfileimageurl(appUser.getProfileimageurl());
        user.setAdress(appUser.getAdress());

        return appUserRepository.save(user);
    }



    public void removeUser(int idAppuser)
    {
        appUserRepository.deleteById(idAppuser);

    }

    public List<AppUser> getAllUsers()
    {
        return appUserRepository.findAll();
    }


    public AppUser findAppUserByEmail(String email)
    {
        if(appUserRepository.findAppUserByEmail(email).isPresent())
        {
          return  appUserRepository.findAppUserByEmail(email).get();
        }
        else return null;

    }

    public AppUser getUserById(int Id)
    {
        return (appUserRepository.findById(Id).isPresent())?appUserRepository.findById(Id).get():null;

    }

}
