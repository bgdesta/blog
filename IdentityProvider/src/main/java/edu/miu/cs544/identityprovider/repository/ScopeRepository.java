package edu.miu.cs544.identityprovider.repository;

import edu.miu.cs544.identityprovider.domain.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ScopeRepository extends JpaRepository<Scope, Long> {

}
