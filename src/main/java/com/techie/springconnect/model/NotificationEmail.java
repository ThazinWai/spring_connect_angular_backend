package com.techie.springconnect.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NotificationEmail {
    private String subject;
    private String reciepient;
    private String body;
}
