package edu.miu.cs544.identityprovider.repository;

import edu.miu.cs544.identityprovider.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
