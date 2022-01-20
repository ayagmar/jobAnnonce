package com.emsi.project.jobannonce.dao;

import com.emsi.project.jobannonce.model.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface OffreRepository extends JpaRepository<Offre, Long> {
    Offre findByCode(String code);
}