package tn.esprit.usermanagement.entities;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppUser implements Serializable {

    @Id
    private int id ;

    private String username ;

    private String password ;

    private String email;

    private String nom ;

    private String prenom;

    private Boolean isEnabled ;

    private String profileimageurl;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private String phone;

    private String adress;

    private String userimage;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns = {@JoinColumn(name = "user_id")}
            ,inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> roles = new HashSet<>();




}
