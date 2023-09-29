package tn.esprit.Authentication.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.Interface.IUserService;
import tn.esprit.Authentication.Repository.UserAuthRepository;
import tn.esprit.Authentication.entities.AppUser;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl  implements UserDetailsService, IUserService {


    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;


    public AppUser createUser(AppUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userAuthRepository.save(user);
    }

    public AppUser updateuser(AppUser user)
    {

        return userAuthRepository.save(user);
    }

    public void removeUser(int idAppuser)
    {
        userAuthRepository.deleteById(idAppuser);

    }

    public List<AppUser> getAllUsers()
    {
        return userAuthRepository.findAll();
    }



    public AppUser finduserbyid(int id)
    {
        Optional<AppUser> appUser = userAuthRepository.findById(id);
        return appUser.orElse(null);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userAuthRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
