package com.tausif.beans;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    private String email;
    private String name;
    private String username;
    private String password;
//    @CreationTimestamp
    private LocalDateTime createdAt;
    private Boolean isActive;

}
