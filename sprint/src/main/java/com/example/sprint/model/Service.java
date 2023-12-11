package com.example.sprint.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Service {
    @Id
	String subtype;
	Long price;
	Long term;
}
