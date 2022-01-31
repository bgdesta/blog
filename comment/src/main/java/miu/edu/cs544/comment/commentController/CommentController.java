package miu.edu.cs544.comment.commentController;

import miu.edu.cs544.comment.commentService.CommentService;
import miu.edu.cs544.comment.domain.Comment;
import miu.edu.cs544.comment.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
@Validated
@RestController
@RequestMapping("/api/comments")
public class CommentController implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    CommentService commentService;


    @GetMapping
    public List<CommentDto> getAllComments() {

        return this.commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable(value = "id") long commentId) {
        return this.commentService.getCommentById(commentId);

    }
        @PostMapping
        @NotEmpty(message = "Input movie list cannot be empty.")
        public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto) {

            CommentDto savedComment = this.commentService.createComment(commentDto);
            return new ResponseEntity<CommentDto>(savedComment,HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public CommentDto updateComment(@RequestBody CommentDto commentDto,  @PathVariable(value = "id") long commentId){

            return  this.commentService.updateComment(commentDto,commentId);


        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Comment> deleteComment( @PathVariable(value = "id") long commentId){
            return this.commentService.deleteComment(commentId);

        }

}


