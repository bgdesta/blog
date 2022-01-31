package edu.miu.cs544.identityprovider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @JsonFormat(pattern = "MM/dd/yyyy")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
    private String imageUrl;
}
