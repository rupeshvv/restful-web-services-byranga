package com.spring.rest.webservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
public class UserResource {
    @Autowired
	private UserServiceDao userServiceDao;
	
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){ 	
    	
    	return userServiceDao.findAll();
    
    }
    @GetMapping("/users/{id}")
    public EntityModel<User> retriveUser(@PathVariable Integer id){ 
    			User user = userServiceDao.findOne(id);
    			if(user == null)
    				throw new UserNotFoundException("Id: "+id);
    			
    			EntityModel<User> resource=EntityModel.of(user);
    			
    			WebMvcLinkBuilder linkTo=linkTo(methodOn(this.getClass()).retrieveAllUsers());
    			
    			resource.add(linkTo.withRel("all-users"));
    			
               return resource;
    }
	
    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user){ 	
    	//input-details of user
        //output-CREATED &Return the created URI
    	User savedUser = userServiceDao.save(user);
    	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    	.path("/{id}")
    	.buildAndExpand(savedUser.getId()).toUri();
    	
    	return ResponseEntity.created(location).build();
    
    }
	
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){ 	
    
    	    User user = userServiceDao.deleteById(id);
    			if(user == null) 
    				throw new UserNotFoundException("Id: "+id);
                
    			
    }
	
	
}
