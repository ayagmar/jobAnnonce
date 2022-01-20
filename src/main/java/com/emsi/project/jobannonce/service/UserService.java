package com.emsi.project.jobannonce.service;

import com.emsi.project.jobannonce.dao.OffreRepository;
import com.emsi.project.jobannonce.dao.RoleRepository;
import com.emsi.project.jobannonce.dao.UserRepository;
import com.emsi.project.jobannonce.model.Offre;
import com.emsi.project.jobannonce.model.User;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<String> CreateUser(userForm user){
       User newUser=new User();
       if(userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername())){
           return ResponseEntity.badRequest().body("Email or username already exists");
       }
       newUser.setUsername(user.getUsername());
       newUser.setEmail(user.getEmail());
       newUser.getRoles().add(roleRepository.getById(user.getRoleID()));
       newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       userRepository.save(newUser);
       return ResponseEntity.ok().body("Created avec success");
    }

    public ResponseEntity<String> postulerOffre(String offreCode){
        Offre offre=offreRepository.findByCode(offreCode);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        if (Objects.isNull(offre) || offre.getExpired() ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offre n'existe pas ou il a été expiré ");
        }
        offre.getUsersPostuler().add(LoggedInUser);
        offre.setNombrePostuler(offre.getNombrePostuler()+1);
        return ResponseEntity.ok().body("Postuler avec success!");
    }


    public ResponseEntity<String> createOffre(offreForm offre){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        Offre newOffre=new Offre();
        newOffre.setEntreprise(LoggedInUser);
        newOffre.setTitle(offre.getTitle());
        newOffre.setDescription(offre.getDescription());
        newOffre.setCode(UUID.randomUUID().toString().replaceAll("-", "").trim().substring(0,8));
        newOffre.setExpirationDate(LocalDateTime.now().plusWeeks(offre.getWeeksExpiration()));
        offreRepository.save(newOffre);
        return ResponseEntity.ok().body("Created avec success");
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Collection<Offre> getMyOffresPoster(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        return LoggedInUser.getJobOffers();
    }

    public Collection<Offre> getMyOffresPostuler(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        return LoggedInUser.getJobsPostuler();
    }



    @Data
    public static class userForm{
        private Long roleID;
        private String username;
        private String email;
        private String password;
    }

    @Data
    public static class offreForm{
        private String title;
        private String description;
        private Long weeksExpiration;

    }
}
