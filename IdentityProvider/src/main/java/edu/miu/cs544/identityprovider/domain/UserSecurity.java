package edu.miu.cs544.identityprovider.domain;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class UserSecurity implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "userSecurity")
    private User user;

    @Column(unique = true)
    private String userName;
    private String password;

    @Embedded
    private SecurityCode emailVerificationCode;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "code", column = @Column(name="resetCode")),
        @AttributeOverride(name = "expiration", column = @Column(name="resetExpiration"))
    })
    private SecurityCode passwordResetCode;

    private boolean isVerified;
    private boolean isEnabled;
    private boolean isLocked;

    private LocalDateTime accountExpiration;
    private LocalDateTime passwordExpiration;
    private LocalDateTime lastLogin;

    @Setter(AccessLevel.NONE)
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany
    @Setter(AccessLevel.NONE)
    private List<Scope> scopes = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addScope(Scope scope) {
        scopes.add(scope);
    }

    public void removeRole(Role role) {
        roles = roles.stream().filter(r -> r.getId() != role.getId()).collect(Collectors.toList());
    }

    public void removeScope(Scope scope) {
        scopes = scopes.stream().filter(s -> s.getId() != scope.getId()).collect(Collectors.toList());
    }

    public boolean containsRole(Role role) {
        return null != roles.stream().filter(r -> r.getId() == role.getId()).findAny().orElse(null);
    }

    public boolean containsScope(Scope scope) {
        return null != scopes.stream().filter(r -> r.getId() == scope.getId()).findAny().orElse(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpiration.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !passwordExpiration.isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "**UserSecurity**";
    }
}
