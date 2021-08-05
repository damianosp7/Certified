package com.example.certified.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegistrationDto {
    /**Data Transfer Object for transfer users details in to database
     * Is the same as User Model*/
    /** The User's FirstName*/
    @NotEmpty(message = "Field can't be empty!")
    @Size(min = 2, message = "The FirstName should have at least 2 characters")
    private String firstName;

    /** The User's LastName*/
    @NotEmpty(message = "Field can't be empty!")
    @Size(min = 2, message = "The LastName should have at least 2 characters")
    private String lastName;

    /** The User's email address (must be valid and unique)*/
    @NotEmpty(message = "Field can't be empty!")
    @Email
    private String email;

    /**Password must be at least 6 characters long*/
    @NotEmpty(message = "Field can't be empty!")
    @Size( min = 6, message = "The password must be at least 6 characters")
    private String password;

    public UserRegistrationDto(){

    }

    public UserRegistrationDto(String firstName, String lastName, String email, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}