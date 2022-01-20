package com.emsi.project.jobannonce.service;

import com.emsi.project.jobannonce.dao.OffreRepository;
import com.emsi.project.jobannonce.model.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OffreService {
    @Autowired
    public OffreRepository offreRepository;

    public List<Offre> getAllOffre(){
        return offreRepository.findAll();
    }
}
