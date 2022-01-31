package miu.edu.cs544.comment.commentServiceImp;

import miu.edu.cs544.comment.commentRepository.CommentRepository;
import miu.edu.cs544.comment.commentService.CommentService;
import miu.edu.cs544.comment.domain.Comment;
import miu.edu.cs544.comment.dto.CommentDto;
import miu.edu.cs544.comment.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper ;

        public List<CommentDto> getAllComments() {
            List<CommentDto> commentDto = new ArrayList<>();
            List<Comment> comment = new ArrayList<>();
            comment = this.commentRepository.findAll();

            for(Comment comment1: comment){
                commentDto.add(convertToDto(comment1));
            }

            return commentDto;
    }

    public CommentDto getCommentById(long commentId) {
        Comment comment =this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        return convertToDto(comment);
    }



    public CommentDto createComment(CommentDto commentDto) {
        this.commentRepository.save(convertToEntity(commentDto));
        return commentDto;
    }


    public CommentDto updateComment(CommentDto commentDto,  long commentId){

        Comment existingComment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + commentId));
        Comment comment = convertToEntity(commentDto);
        this.commentRepository.save(comment);
        //existingComment.setContent(comment.getContent());
        //existingComment.setTitle(comment.getTitle());
        //existingComment.setCreatedAt(comment.getCreatedAt());

        return commentDto;
    }

    public ResponseEntity<Comment> deleteComment( long commentId){
        Comment existingComment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id :" + commentId));
        this.commentRepository.delete(existingComment);
        return ResponseEntity.ok().build();
    }


    // Method for conversion from Comment Entity object to Comment object.
    private CommentDto convertToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    // Method for conversion from CommentDto object to Comment object.
    private Comment convertToEntity(CommentDto employeeDto) {
        Comment comment = modelMapper.map(employeeDto, Comment.class);
        comment.setCreatedAt(new Date());
        comment.setPublishedAt(new Date());
        return comment;
    }
}
