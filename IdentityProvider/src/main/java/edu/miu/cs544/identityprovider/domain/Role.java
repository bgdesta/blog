package edu.miu.cs544.identityprovider.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @OneToMany
    @Setter(AccessLevel.NONE)
    private List<Scope> scopes = new ArrayList<>();

    @OneToOne
    private User createdBy;

    private LocalDateTime createdAt;

    public void addScope(Scope scope) {
        scopes.add(scope);
    }

    public void removeScope(Scope scope) {
        scopes = scopes.stream().filter(s -> s.getId() != scope.getId()).collect(Collectors.toList());
    }

    public boolean containsScope(Scope scope) {
        return null != scopes.stream().filter(r -> r.getId() == scope.getId()).findAny().orElse(null);
    }
}
