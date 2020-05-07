package org.greenmango.service.membership.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Date;

public final  class MemberRequest {
    private final String firstName;
    private final String lastName;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyy-MM-dd")
    private final LocalDate dateOfBirth;
    private final ImageRequest image;
    private final String postalCode;

    public MemberRequest(String firstName, String lastName, LocalDate dateOfBirth, ImageRequest image, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.image = image;
        this.postalCode = postalCode;

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

    public ImageRequest getImage() {
        return image;
    }

    public String getPostalCode() {
        return postalCode;
    }


}
