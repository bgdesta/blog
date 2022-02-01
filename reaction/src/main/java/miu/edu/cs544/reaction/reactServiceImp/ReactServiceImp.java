package miu.edu.cs544.reaction.reactServiceImp;

import miu.edu.cs544.reaction.domain.Reaction;
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

        public List<Reaction> getAllReacts() {

        return this.reactRepository.findAll();
    }


    public Reaction getReactById(long reactId) {
        return this.reactRepository.findById(reactId).orElseThrow(() -> new ResourceNotFoundException("React not found with id :" + reactId));
    }


    public Reaction createReact(Reaction react) {

        return this.reactRepository.save(react);
    }


    public Reaction updateReact(Reaction react, long reactId){

        Reaction existingreact = this.reactRepository.findById(reactId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + reactId));
        existingreact.setPostid(react.getPostid());
        existingreact.setUserid(react.getUserid());
        existingreact.setCreatedat(react.getCreatedat());

        return this.reactRepository.save(existingreact);
    }

    public ResponseEntity<Reaction> deleteReact(long reactId){
        Reaction existingComment = this.reactRepository.findById(reactId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + reactId));
        this.reactRepository.delete(existingComment);
        return ResponseEntity.ok().build();
    }
}
