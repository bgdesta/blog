package miu.edu.cs544.reaction.reactRepository;

import miu.edu.cs544.reaction.domain.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {
}
