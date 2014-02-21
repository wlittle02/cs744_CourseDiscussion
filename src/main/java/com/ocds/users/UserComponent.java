package com.ocds.users;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Component
public class UserComponent implements UserDetailsService {
    
    public UserComponent() {
    	System.out.println("MAKING A USER COMPONENT");
    }
 
    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {    	    	
        User user =  User.findUserByName(username);
        System.out.println("found user(" + user + ")");
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username/password.");
        }

        return user;
    }
    
    public void addUser(User user) {
    	if(user.getAuthorities() == null) {
    		user.setAuthorities(new HashSet<Role>());
    	}    	

    	user.persist();
    	
    	if(user.getAuthorities().isEmpty()) {
    		Role role = Role.findRole(2L);    		
    		user.addRole(role);
    		user.merge();
    	}    
    }
    public List<User> getAllUsers(){
   	 List<User> users =  User.findUsers();
   	 return users;
        
   }
}
