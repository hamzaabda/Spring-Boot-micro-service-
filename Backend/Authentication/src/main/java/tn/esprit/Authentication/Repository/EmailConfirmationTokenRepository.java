package tn.esprit.Authentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.Authentication.entities.AppUser;
import tn.esprit.Authentication.entities.EmailConfirmationToken;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {

    Optional<EmailConfirmationToken> findByToken(String token);

    EmailConfirmationToken findByUser(AppUser user);

    @Modifying
    @Query("update EmailConfirmationToken c set c.confirmedAt = :confirmedAt where c.token = :token")
    int updateConfirmedAt(@Param("token") String token, @Param("confirmedAt") LocalDateTime confirmedAt);


    @Modifying
    void deleteByExpiredAtBefore(LocalDateTime expiryDate);

}
