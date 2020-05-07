package org.greenmango.service.membership.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

public class MemberResponse  {
    private final String firstName;
    private final String lastName;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dateOfBirth;
    private final String url;
    private final String postalCode;
    private final Integer id;

    public MemberResponse(String firstName, String lastName, LocalDate dateOfBirth, String url, String postalCode, Integer id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.url = url;
        this.postalCode = postalCode;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getUrl() {
        return url;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Integer getId() {
        return id;
    }
}
