package miu.edu.cs544.comment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comId;
    private Long userId;

    //@ManyToOne
    //private Post post;
    //parent Id
    @NotBlank
    @NotEmpty(message = "Title Couldn't be empty")
    @Size(min = 2, max = 100, message = "Title Couldn't be empty")
    private String title; //The comment title

    private boolean isPublished;//    Published It can be used to identify whether the comment is publicly available.
    private Date createdAt; // 	It stores the date and time at which the comment is submitted.
    private Date publishedAt;// 	It stores the date and time at which the comment is published.
    @Lob
    @NotEmpty(message = "content Couldn't be empty")
    @Size(min = 2, max = 100, message = "Title Couldn't be empty")
    private String content; // 	The column used to store the comment data.

    public Comment(String title, boolean isPublished, String content) {

        this.title = title;
        this.isPublished = isPublished;
        this.content = content;

    }
}
