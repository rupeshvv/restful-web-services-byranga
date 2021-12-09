package com.spring.rest.webservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
public class UserJPAResource {
  
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
    @GetMapping("jpa/users")
    public List<User> retrieveAllUsers(){ 	
    	
    	return userRepository.findAll();
    
    }
    @GetMapping("jpa/users/{id}")
    public EntityModel<User> retriveUser(@PathVariable Integer id){ 
    			Optional<User> user = userRepository.findById(id);
    			if(!user.isPresent())
    				throw new UserNotFoundException("Id: "+id);
    			
    			EntityModel<User> resource=EntityModel.of(user.get());
    			
    			WebMvcLinkBuilder linkTo=linkTo(methodOn(this.getClass()).retrieveAllUsers());
    			
    			resource.add(linkTo.withRel("all-users"));
    			
               return resource;
    }
	
    @PostMapping("jpa/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user){ 	
    	//input-details of user
        //output-CREATED &Return the created URI
    	User savedUser = userRepository.save(user);
    	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    	.path("/{id}")
    	.buildAndExpand(savedUser.getId()).toUri();
    	
    	return ResponseEntity.created(location).build();
    
    }
	
    @DeleteMapping("jpa/users/{id}")
    public void deleteUser(@PathVariable Integer id){ 	
    
    	         userRepository.deleteById(id);
    					
    }
    @GetMapping("jpa/users/{id}/posts")
    public List<Post> retrieveAllUserPosts(@PathVariable Integer id){ 	
    	 
    	Optional<User> optionalUser = userRepository.findById(id);
    	if(!optionalUser.isPresent())
    		throw new UserNotFoundException("Id: "+id);	
    	
    	
    	return optionalUser.get().getPosts();
    }
    
    
    @PostMapping("jpa/users/{id}/posts")
    public ResponseEntity<Object> addPost(@PathVariable Integer id,@RequestBody Post post){ 	
    	//input-details of user
        //output-CREATED &Return the created URI
    	
    	Optional<User> optionalUser = userRepository.findById(id);
    	if(!optionalUser.isPresent())
    		throw new UserNotFoundException("Id: "+id);	
    	
    	User user = optionalUser.get();
    	
    	post.setUser(user);
    	postRepository.save(post);
    	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    	.path("/{id}")
    	.buildAndExpand(post.getId()).toUri();
    	
    	return ResponseEntity.created(location).build();
    
    }
    
    
    
    
    
    
    
    
    
    
}
