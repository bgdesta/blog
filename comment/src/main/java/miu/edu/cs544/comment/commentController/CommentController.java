package miu.edu.cs544.comment.commentController;
import miu.edu.cs544.comment.commentService.CommentService;
import miu.edu.cs544.comment.domain.Comment;
import miu.edu.cs544.comment.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/comments")
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
        //
        @PreAuthorize("#role == 'Consumer'")
        public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @RequestHeader String userid, @RequestHeader String role ) {

            commentDto.setUserId(Long.parseLong(userid));
            CommentDto savedComment = this.commentService.createComment(commentDto);
            ClientRestTemplate.GetUserByIdAPI(1l);
            return new ResponseEntity<CommentDto>(savedComment,HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        @PreAuthorize("#role == 'Consumer'")
        public CommentDto updateComment(@Valid @RequestBody CommentDto commentDto,  @PathVariable(value = "id") long commentId, @RequestHeader String userId, @RequestHeader String role ){
            commentDto.setUserId(Long.parseLong(userId));
            return  this.commentService.updateComment(commentDto,commentId);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Comment> deleteComment( @PathVariable(value = "id") long commentId){
            return this.commentService.deleteComment(commentId);

        }

}


