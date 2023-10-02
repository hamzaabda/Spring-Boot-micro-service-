package tn.esprit.Authentication.entities;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")

    private Long id;

    @Column(name="role_name")
    private String nameRole;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> appusers = new HashSet<>();

}
