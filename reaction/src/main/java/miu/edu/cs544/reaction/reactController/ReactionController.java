package miu.edu.cs544.reaction.reactController;

import miu.edu.cs544.reaction.domain.Reaction;
import miu.edu.cs544.reaction.reactService.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reacts")
public class ReactionController {
    @Autowired
    ReactionService reactService;


    @GetMapping
    public List<Reaction> getAllReacts() {

        return this.reactService.getAllReacts();
    }

    @GetMapping("/{id}")
    public Reaction getReactById(@PathVariable(value = "id") long reactId) {
        return this.reactService.getReactById(reactId);

    }

    @PostMapping
    @PreAuthorize("#role == 'Consumer'")
    public Reaction createReact(@RequestBody Reaction react) {

            return this.reactService.createReact(react);
        }
//    @PostMapping
//    public ResponseEntity<Reaction> createReact(@RequestBody Reaction react) {
//
//        Reaction savedReact = this.reactService.createReact(react);
//        return new ResponseEntity<Reaction>(savedReact, HttpStatus.CREATED);
//    }

        @PutMapping("/{id}")
        public Reaction updateReact(@Valid @RequestBody Reaction react, @PathVariable(value = "id") long reactId){
            return  this.reactService.updateReact(react,reactId);

        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Reaction> deleteReact(@PathVariable(value = "id") long reactId){
            return this.reactService.deleteReact(reactId);

        }

}


