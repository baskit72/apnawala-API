package com.APIwebsitebuilder.websitebuilder.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "widget")
public class Widget {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    private String type;
    private int x;
    private int y;
    private int width;
    private int height;
    private String mode;

    @Column(columnDefinition = "json")
    private String properties;  // Store properties as JSON string

    // Getters and Setters
}
