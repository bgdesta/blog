package miu.edu.cs544.reaction.reactController;


import miu.edu.cs544.reaction.domain.Reaction;
import miu.edu.cs544.reaction.enums.ReactName;
import miu.edu.cs544.reaction.enums.ReactType;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RestReactClient {

    private static final String GET_ALL_REACTS_API ="http://localhost:8086/reactions";
    private static final String GET_REACT_BY_ID_API ="http://localhost:8086/reactions/{id}";
    private static final String CREATE_REACT_API ="http://localhost:8086/reactions";
    private static final String UPDATE_REACT_API ="http://localhost:8086/reactions/{id}";
    private static final String DELETE_REACT_API ="http://localhost:8086/reactions/{id}";

    static RestTemplate restTemplate = new RestTemplate();

    public static void  main(String[] args){

        callCreateReactAPI();
//        callCreateReactAPI();
//
//        callGetAllReactAPI();
//        callGetReactByIdAPI();
//
//        callUpdateReactAPI();
//        callGetAllReactAPI();
//        callDeleteReactAPI();
    }
    private static void callGetAllReactAPI(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> result = restTemplate.exchange(GET_ALL_REACTS_API, HttpMethod.GET, entity, String.class);
        System.out.println(result);
    }

    private static void callGetReactByIdAPI(){
        Map<String, Integer> param = new HashMap<>();
        param.put("id", 2); //to update react with id = 1
        Reaction react = restTemplate.getForObject(GET_REACT_BY_ID_API, Reaction.class, param);
        System.out.println(react.getCreatedat());
        System.out.println(react.getPostid());
        System.out.println(react.getUserid());

    }
    private static void callCreateReactAPI(){
        Reaction react = new Reaction(102, 123, ReactName.LIKE, ReactType.ARTICLE);
        ResponseEntity<Reaction> react2 =restTemplate.postForEntity(CREATE_REACT_API, react, Reaction.class);
        System.out.println(react2.getBody());

        react = new Reaction(321, 5678, ReactName.DISLIKE, ReactType.POST);
        react2 =restTemplate.postForEntity(CREATE_REACT_API,react, Reaction.class);
        System.out.println(react2.getBody());

        react = new Reaction(1054, 4567, ReactName.LIKE, ReactType.COMMENT);
        react2 =restTemplate.postForEntity(CREATE_REACT_API, react, Reaction.class);
        System.out.println(react2.getBody());

    }

    private static void callUpdateReactAPI(){
        Map<String, Integer> param = new HashMap<>();
        param.put("id", 2); //to update comment with id = 1
        Reaction updateReact = new Reaction(1112, 34521, ReactName.DISLIKE, ReactType.POST);
        restTemplate.put(UPDATE_REACT_API,updateReact, param);

    }
    private static void callDeleteReactAPI(){
        Map<String, Integer> param = new HashMap<>();
        param.put("id", 3);
        restTemplate.delete(DELETE_REACT_API, param);
    }
}
