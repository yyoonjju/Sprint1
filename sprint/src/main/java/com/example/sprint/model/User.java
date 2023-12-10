package com.example.sprint.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    String userId;
    String userPassword;
    String userNickname;
    String phone;
    String address;
}
