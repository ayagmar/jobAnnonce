package com.emsi.project.jobannonce.web;

import com.emsi.project.jobannonce.model.Offre;
import com.emsi.project.jobannonce.model.User;
import com.emsi.project.jobannonce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> createUser(@RequestBody UserService.userForm user){
        return userService.CreateUser(user);
    }

    @PostMapping("/api/user/postuler/{code}")
    public ResponseEntity<String> postulerJob(@RequestParam String code){
        return userService.postulerOffre(code);
    }

    @PostMapping("/api/user/createOffre")
    public ResponseEntity<String> postulerJob(@RequestBody UserService.offreForm form){
        return userService.createOffre(form);
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/api/users/myOffres")
    public Collection<Offre> getMyJobPostuler(){
        return userService.getMyOffresPoster();
    }

    @GetMapping("/api/entreprise/myOffres")
    public Collection<Offre> getMyJobPosted(){
        return userService.getMyOffresPostuler();
    }

}
