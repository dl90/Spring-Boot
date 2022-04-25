package com.example.SpringMVC.form;

import javax.validation.constraints.*;


public class FooForm {

    @NotNull
    @Min(value = 1, message = "ID should be greater than 0")
    private long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private String gender;

    @NotNull
    private String membership;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9 .,@!#$]", message = "alphanumeric only")
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
