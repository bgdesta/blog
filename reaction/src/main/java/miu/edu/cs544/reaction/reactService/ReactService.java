package miu.edu.cs544.reaction.reactService;


import miu.edu.cs544.reaction.domain.React;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReactService {


    public List<React> getAllReacts();

    public React getReactById(long reactId);

    public React createReact(React react);

    public ResponseEntity<React> deleteReact(long reactId);

    React updateReact(React react, long reactId);
}
