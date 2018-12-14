package com.mirea.petshop.services;

import com.mirea.petshop.model.Person;
import com.mirea.petshop.model.Role;
import com.mirea.petshop.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
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
        personRepository.save(new Person("tamafjpj",new BCryptPasswordEncoder().encode("pass"),
               List.of(Role.ADMIN,Role.USER),1000000,
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
