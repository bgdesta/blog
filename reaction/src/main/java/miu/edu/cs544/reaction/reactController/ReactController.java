package miu.edu.cs544.reaction.reactController;

import miu.edu.cs544.reaction.domain.React;
import miu.edu.cs544.reaction.reactService.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reacts")
public class ReactController {
    @Autowired
    ReactService reactService;


    @GetMapping
    public List<React> getAllReacts() {

        return this.reactService.getAllReacts();
    }

    @GetMapping("/{id}")
    public React getReactById(@PathVariable(value = "id") long reactId) {
        return this.reactService.getReactById(reactId);

    }
        @PostMapping
        public React createReact(@RequestBody React react) {

            return this.reactService.createReact(react);
        }

        @PutMapping("/{id}")
        public React updateReact(@RequestBody React react, @PathVariable(value = "id") long reactId){

            return  this.reactService.updateReact(react,reactId);


        }
        @DeleteMapping("/{id}")
        public ResponseEntity<React> deleteReact( @PathVariable(value = "id") long reactId){
            return this.reactService.deleteReact(reactId);

        }

}


