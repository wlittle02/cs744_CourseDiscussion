package com.ocds.users;

import java.util.ArrayList;
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
    
    public void addUser(User user, ArrayList<Role> roles) {
    	if(user.getAuthorities() == null) {
    		user.setAuthorities(new HashSet<Role>());
    	}    	
    	user.persist();
    	
    	for( int i=0;i<roles.size();i++){
    		if (roles.get(i).getName().equalsIgnoreCase("ROLE_ADMIN")){
        		Role role = Role.findRole(1L);    		
        		user.addRole(role);
        		
        	}    
    		if (roles.get(i).getName().equalsIgnoreCase("ROLE_INSTRUCTOR")){
       		Role role = Role.findRole(2L);    		
       		user.addRole(role);
       		
    		}   
    		if (roles.get(i).getName().equalsIgnoreCase("ROLE_STUDENT" )){
       		Role role = Role.findRole(3L);    		
       		user.addRole(role);
       		
    		}   
    		
    		
    	}
    	user.merge();
    }
    public void updateUser(User user, ArrayList<String> userroles) {
    	if(user.getAuthorities() == null) {
    		user.setAuthorities(new HashSet<Role>());
    	}    	
  
    	for(int i=0;i<userroles.size();i++){
	    	if (userroles.contains("ROLE_ADMIN")){
	    		Role role = Role.findRole(1L);    		
        		user.addRole(role);
        			    	
	    	}
	    	if (userroles.contains("ROLE_INSTRUCTOR")){
	    		Role role = Role.findRole(2L);    		
        		user.addRole(role);
        		    	
	    	}
	    	if (userroles.contains("ROLE_STUDENT")){
	    		Role role = Role.findRole(3L);    		
        		user.addRole(role);
        		    	
	    	}
	    	
    	}	
    	user.merge();
    }
    public List<User> getAllUsers(){
   	 List<User> users =  User.findUsers();
   	 return users;
        
   }
    public void deleteUser(String username) {
    	User user =  User.findUserByName(username);
    	user.setAuthorities(null);
    	user.merge();
    	user.remove();
    	    
    }
}
