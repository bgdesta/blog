package edu.miu.cs544.identityprovider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class UserCreateDto {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String phoneNumber;
    @Email
    private String email;
    @NotNull
    private String password;
    @Past
    @JsonFormat(pattern = "MM/dd/yyyy")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
    private String imageUrl;
}
