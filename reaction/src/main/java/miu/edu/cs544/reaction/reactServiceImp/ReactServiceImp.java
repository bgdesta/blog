package miu.edu.cs544.reaction.reactServiceImp;

import miu.edu.cs544.reaction.domain.React;
import miu.edu.cs544.reaction.exception.ResourceNotFoundException;
import miu.edu.cs544.reaction.reactRepository.ReactRepository;
import miu.edu.cs544.reaction.reactService.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ReactServiceImp implements ReactService {
    @Autowired
    private ReactRepository reactRepository;

        public List<React> getAllReacts() {

        return this.reactRepository.findAll();
    }


    public React getReactById(long reacttId) {
        return this.reactRepository.findById(reacttId).orElseThrow(() -> new ResourceNotFoundException("React not found with id :" + reacttId));
    }


    public React createReact(React react) {

        return this.reactRepository.save(react);
    }


    public React updateReact(React react,  long reactId){

        React existingreact = this.reactRepository.findById(reactId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + reactId));
        existingreact.setPost_id(react.getPost_id());
        existingreact.setUser_id(react.getUser_id());
        existingreact.setCreatedAt(react.getCreatedAt());

        return this.reactRepository.save(existingreact);
    }

    public ResponseEntity<React> deleteReact( long reactId){
        React existingComment = this.reactRepository.findById(reactId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + reactId));
        this.reactRepository.delete(existingComment);
        return ResponseEntity.ok().build();
    }
}
