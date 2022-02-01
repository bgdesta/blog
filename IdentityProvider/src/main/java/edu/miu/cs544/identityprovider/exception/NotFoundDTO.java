package edu.miu.cs544.identityprovider.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotFoundDTO {
    private Date timestamp;
    private String name;
    private String error;
    private String message;
    private String path;
    public NotFoundDTO(String n, String e, String m, String p) {
        timestamp = new Date();
        name = n;
        error = e;
        message = m;
        path = p;
    }
}