package com.emsi.project.jobannonce.web;

import com.emsi.project.jobannonce.model.Offre;
import com.emsi.project.jobannonce.service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OffreController {
    @Autowired
    private OffreService offreService;

    @GetMapping("/api/offres")
    public List<Offre> getAll(){
        return offreService.getAllOffre();
    }

}
