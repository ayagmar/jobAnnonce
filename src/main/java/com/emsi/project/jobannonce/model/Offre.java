package com.emsi.project.jobannonce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offre implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;
    private Boolean expired=false;
    private String code;
    private Integer nombrePostuler=0;

    @ManyToOne
    private User entreprise;

    @ManyToMany
    private Collection<User> usersPostuler=new ArrayList<>();


}
