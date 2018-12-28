package com.mirea.Auth.services;

import com.mirea.Auth.model.Person;
import com.mirea.Auth.model.Role;
import com.mirea.Auth.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /*@PostConstruct
    public void init(){
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);
        personRepository.save(new Person("tamafjpj",new BCryptPasswordEncoder().encode("pass"),
                roles,1000000,
                true,true,true,true));
    }*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("user with name: " + username + " not found"));
    }

    public Optional<Person> findById(Integer id) {
        return personRepository.findById(id);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }
}
