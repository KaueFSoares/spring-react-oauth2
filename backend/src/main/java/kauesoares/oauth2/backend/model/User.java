package kauesoares.oauth2.backend.model;

import jakarta.persistence.*;
import kauesoares.oauth2.backend.base.BaseEntity;
import kauesoares.oauth2.backend.domain.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Data
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private Set<Role> roles = new HashSet<>();
}
