package miu.edu.cs544.comment.commentController;

import miu.edu.cs544.comment.domain.Comment;
import miu.edu.cs544.comment.dto.UserReadDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientRestTemplate {

    private static final String GET_ALL_COMMENTS_API ="http://localhost:8087/comments";
    private static final String GET_COMMENT_BY_ID_API ="http://localhost:8087/comments/{id}";
    private static final String CREATE_COMMENT_API ="http://localhost:8087/comments";
    private static final String UPDATE_COMMENT_API ="http://localhost:8087/comments/{id}";
    private static final String DELETE_COMMENT_API ="http://localhost:8087/comments/{id}";


    static RestTemplate restTemplate = new RestTemplate();

    private static final String USER_INFO_API ="http://localhost:8080/users/{id}";

    public static void  main(String[] args){

//        callCreateCommentAPI();
//        callCreateCommentAPI();
//
//        callGetAllCommentAPI();
//        callGetCommentByIdAPI();
//
//        callUpdateCommentAPI();
//        callGetAllCommentAPI();
//        callDeleteAPI();

    }
    //-------------------------------------------
    public static void GetUserByIdAPI(Long id){
        Map<String, Long> param = new HashMap<>();
        param.put("id", id); //to update comment with id = 1
        UserReadDto user = restTemplate.getForObject(USER_INFO_API, UserReadDto.class, param);
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getPhoneNumber());

    }

    //---------------------------------------------------------
    private static void callGetAllCommentAPI(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(GET_ALL_COMMENTS_API, HttpMethod.GET, entity, String.class);
        System.out.println(result);
    }

    private static void callGetCommentByIdAPI(){
        Map<String, Integer> param = new HashMap<>();
        param.put("id", 2); //to update comment with id = 1
        Comment comment = restTemplate.getForObject(GET_COMMENT_BY_ID_API, Comment.class, param);
        System.out.println(comment.getContent());
        System.out.println(comment.getTitle());


    }
    private static void callCreateCommentAPI(){
        Comment comment = new Comment("Technology", true, "about Technology");
        ResponseEntity<Comment> comment2 =restTemplate.postForEntity(CREATE_COMMENT_API,comment, Comment.class);
        System.out.println(comment2.getBody());

        comment = new Comment("Programming in Java", true, "this is the content about java progamming using Intelij");
        comment2 =restTemplate.postForEntity(CREATE_COMMENT_API,comment, Comment.class);
        System.out.println(comment2.getBody());

        comment = new Comment("Python Progamming", true, "Conditonal staments work based on indentations");
        comment2 =restTemplate.postForEntity(CREATE_COMMENT_API,comment, Comment.class);
        System.out.println(comment2.getBody());


    }

    private static void callUpdateCommentAPI(){
        Map<String, Integer> param = new HashMap<>();
        param.put("id", 2); //to update comment with id = 1
        Comment updateComment = new Comment("Science", true, "about Technology");
        restTemplate.put(UPDATE_COMMENT_API,updateComment, param);

    }
    private static void callDeleteAPI(){
        Map<String, Integer> param = new HashMap<>();
        param.put("id", 1); //to update comment with id = 1
        restTemplate.delete(DELETE_COMMENT_API, param);

    }
}
