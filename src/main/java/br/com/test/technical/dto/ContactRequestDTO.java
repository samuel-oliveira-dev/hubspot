package br.com.test.technical.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactRequestDTO {
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("email")
    private String email;

    public ContactRequestDTO() {

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

    public ContactRequestDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
