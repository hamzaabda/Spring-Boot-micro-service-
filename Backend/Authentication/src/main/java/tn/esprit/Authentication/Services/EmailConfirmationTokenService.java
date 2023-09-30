package tn.esprit.Authentication.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.Authentication.Repository.EmailConfirmationTokenRepository;
import tn.esprit.Authentication.entities.EmailConfirmationToken;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmailConfirmationTokenService {

    @Autowired
    private EmailConfirmationTokenRepository emailConfirmationTokenRepository;

    private final UserServiceImpl userService;

    public void saveConfirmationToken(EmailConfirmationToken emailConfirmationToken) {
        emailConfirmationTokenRepository.save(emailConfirmationToken);
    }

    void deleteConfirmationToken(Long id){

        emailConfirmationTokenRepository.deleteById(id);
    }

    public Optional<EmailConfirmationToken> getByToken(String token) {
        return emailConfirmationTokenRepository.findByToken(token);
    }


    public int updateConfirmedAt(String token) {
        return emailConfirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    @Transactional
    public String confirmToken(String token) {
        String msg = "";
        EmailConfirmationToken confirmationToken = getByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            msg = "email already confirmed";
        } else {
            LocalDateTime expiredAt = confirmationToken.getExpiredAt();
            if (expiredAt.isBefore(LocalDateTime.now())) {
                msg = "token expired";
            } else {
                updateConfirmedAt(token);
//                userService.enableUser(confirmationToken.getUser().getUsername());
                msg = "confirmed";
            }
        }
        return msg;
    }


    @Transactional
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        emailConfirmationTokenRepository.deleteByExpiredAtBefore(now);
    }


}