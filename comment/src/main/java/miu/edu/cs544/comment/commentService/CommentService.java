package miu.edu.cs544.comment.commentService;

import miu.edu.cs544.comment.domain.Comment;
import miu.edu.cs544.comment.dto.CommentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CommentService {

    // get all comments

    public List<CommentDto> getAllComments();

    // get comment by id

    public CommentDto getCommentById(long commentId);

    // create comment

    public CommentDto createComment(@RequestBody CommentDto commentDto);

    // update comment

    public CommentDto updateComment(CommentDto commentDto,  long commentId);

    public ResponseEntity<Comment> deleteComment(long commentId);
}
