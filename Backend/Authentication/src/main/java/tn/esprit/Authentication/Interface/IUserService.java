package tn.esprit.Authentication.Interface;

import tn.esprit.Authentication.entities.AppUser;

import java.util.List;

public interface IUserService {

    public AppUser createUser(AppUser user);

    public AppUser updateuser(AppUser user);

    public void removeUser(int idAppuser);

    public List<AppUser> getAllUsers();

    public AppUser finduserbyid(int id);

}
