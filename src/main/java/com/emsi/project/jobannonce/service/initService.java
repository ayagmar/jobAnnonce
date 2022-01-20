package com.emsi.project.jobannonce.service;

import com.emsi.project.jobannonce.dao.OffreRepository;
import com.emsi.project.jobannonce.dao.RoleRepository;
import com.emsi.project.jobannonce.dao.UserRepository;
import com.emsi.project.jobannonce.model.Offre;
import com.emsi.project.jobannonce.model.Role;
import com.emsi.project.jobannonce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class initService implements CommandLineRunner, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void initRole() {
        Role R = new Role();
        Role U = new Role();
        Role R3 = new Role();
        R.setName("ADMIN");
        R3.setName("ENTREPRISE");
        U.setName("USER");
        roleRepository.save(R);
        roleRepository.save(R3);
        roleRepository.save(U);

    }

    public void initUser() {
        User u = new User();
        User u2 = new User();
        User u4 = new User();

        u.setEmail("Admin1Email@gmail.com");
        u.setUsername("Admin1");
        u.setFirstName("Anas");
        u.setPassword(bCryptPasswordEncoder.encode("password"));
        u.setLastName("Jamali");
        u.setTelephone("0606060606");
        u.getRoles().add(roleRepository.findByName("ADMIN"));



        u2.setEmail("employeeEmail@gmail.com");
        u2.setUsername("Entreprise1");
        u2.setFirstName("Mohamed");
        u2.setPassword(bCryptPasswordEncoder.encode("password"));
        u2.setLastName("Oussou");
        u2.setTelephone("0606060606");
        u2.getRoles().add(roleRepository.findByName("ENTREPRISE"));



        u4.setEmail("employssseeEmail@gmail.com");
        u4.setUsername("Student1");
        u4.setFirstName("Hakim");
        u4.setPassword(bCryptPasswordEncoder.encode("password"));
        u4.setLastName("Ziyech");
        u4.setTelephone("0606060606");
        u4.getRoles().add(roleRepository.findByName("USER"));
        u4.setCV("Hakim Ziyech CV PFE");

        userRepository.save(u);
        userRepository.save(u2);
        userRepository.save(u4);
    }

    void initOffre(){
        Offre o1=new Offre();
        o1.setTitle("STAGE PFE JAVA");
        o1.setEntreprise(userRepository.findByUsername("Entreprise1"));
        o1.setExpirationDate(LocalDateTime.now().plusWeeks(2L));
        o1.getUsersPostuler().add(userRepository.findByUsername("Student1"));
        o1.setNombrePostuler(o1.getNombrePostuler()+1);
        o1.setDescription("Offre stage full stack JAVA ANGULAR ");
        o1.setCode(UUID.randomUUID().toString().replaceAll("-", "").trim().substring(0,8));
        offreRepository.save(o1);
    }

    @Override
    public void run(String... args) throws Exception {
        initRole();
        initUser();
        initOffre();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Username not found in database !");
        }
        Collection<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
    }
}
