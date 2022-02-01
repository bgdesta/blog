package miu.edu.cs544.reaction.reactService;


import miu.edu.cs544.reaction.domain.Reaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReactionService {


    public List<Reaction> getAllReacts();

    public Reaction getReactById(long reactId);

    public Reaction createReact(Reaction react);

    public ResponseEntity<Reaction> deleteReact(long reactId);

    Reaction updateReact(Reaction react, long reactId);
}
