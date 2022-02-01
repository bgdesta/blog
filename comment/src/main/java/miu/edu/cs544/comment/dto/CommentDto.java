package miu.edu.cs544.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor

public class CommentDto {
    private Long comId;
    private Long userId;
    @NotBlank
    @NotEmpty(message = "Title Couldn't be empty")
    @Size(min = 2, message =  "Title Couldn't be empty")
    private String title;
    @NotBlank
    @NotEmpty(message = "Content Couldn't be empty")
    @Size(min = 2, message =  "Title Couldn't be empty")
    private String content;
}
