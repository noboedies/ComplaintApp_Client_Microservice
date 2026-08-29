package com.tausif.beans;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    private Long id;
    private String username;
    private String title;
    private String description;
    private String category;
    private LocalDate incidentDate;
    private LocalDateTime createdAt;
    private String latitude;
    private String longitude;
    private String location;
    private String zipCode;
    private byte[] evidence1;
    private byte[] evidence2;
    private byte[] evidence3;
}
