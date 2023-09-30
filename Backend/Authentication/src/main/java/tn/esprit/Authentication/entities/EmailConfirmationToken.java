package tn.esprit.Authentication.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailConfirmationToken implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String token;
    LocalDateTime localDateTime;
    LocalDateTime expiredAt;
    LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private AppUser user;

    public EmailConfirmationToken(String token, LocalDateTime localDateTime, LocalDateTime expiredAt, AppUser user) {
        super();
        this.token = token;
        this.localDateTime = localDateTime;
        this.expiredAt = expiredAt;
        this.user = user;
    }

}