package edu.miu.cs544.identityprovider.repository;

import edu.miu.cs544.identityprovider.domain.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {
    Optional<UserSecurity> findByUserName(String username);

    @Query("select u from UserSecurity u where u.passwordResetCode.code = :code")
    Optional<UserSecurity> findByResetCode(@Param("code") String code);
}
