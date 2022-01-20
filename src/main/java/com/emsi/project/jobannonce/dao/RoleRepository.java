package com.emsi.project.jobannonce.dao;

import com.emsi.project.jobannonce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}