package dev.jamesoliver.dwpassessmentjava.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Represents a user / person. Used for mapping both the incoming users and outgoing people. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "id",
    "first_name",
    "last_name",
    "email",
    "ip_address",
    "latitude",
    "longitude"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private int id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @JsonProperty("ip_address")
    private String ipAddress;

    private double latitude;
    private double longitude;
}
