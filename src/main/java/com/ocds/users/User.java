package com.ocds.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ocds.Domain.Course;





@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;

	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="user_has_roles", joinColumns= {@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="role_id")})	
	private Set<Role> authorities = new HashSet<Role>();

	private Boolean accountNonExpired = true;
	private Boolean accountNonLocked = true;
	private Boolean credentialsNonExpired = true;
	private Boolean enabled = true;
	
	public void addRole(Role role) {
		authorities.add(role);
	}
	
	public Set<Role> getRole()
	{
		return authorities;
	}
	
	public Boolean hasRole(String pRole)
	{
		Boolean hasRole = false;
		Iterator<Role> itr = authorities.iterator();
		while (itr.hasNext())
		{
			if (itr.next().getAuthority().equals(pRole))
			{
				hasRole = true;
				break;
			}
		}
		return hasRole;
	}
	
	public static User findUserByName(String name) {
		if (name == null)
			return null;

		List<User> list = entityManager().createQuery("SELECT user FROM User user where user.username = ?1", User.class).setParameter(1, name)
				.getResultList();

		User user =  (list == null || list.size() == 0 ? null : list.get(0));
		if(user != null) {
			user = User.findUser(user.getId());
		}
		return user;
	}
	public static List<User> findUsers() {
		List<User> users = new ArrayList<User>(); 
		List<User> list = entityManager().createQuery("SELECT user FROM User user", User.class)
				.getResultList();	
		if (list!=null){
			for(int i=0;i<list.size();i++){
				User user = list.get(i);
//				String role =  list.get(i).getAuthorities().iterator().next().getAuthority();
//				if (role.equalsIgnoreCase("ROLE_INSTRUCTOR"))
					users.add(user);
			}
		}
				
		return users;
	}
	
	

	public static class Builder {
		private String username;
		private String password;
		private String email;
		private String firstName;
		private String lastName;

		
		private Set<Role> authorities;
		private boolean accountNonExpired = true;
		private boolean accountNonLocked = true;
		private boolean credentialsNonExpired = true;
		private boolean enabled = true;

		
		
		public Builder username(String u) {
			this.username = u;
			return this;
		}

		public Builder password(String p) {
			this.password = p;
			return this;
		}

		public Builder email(String em) {
			this.email = em;
			return this;
		}

		public Builder firstName(String fn) {
			this.firstName = fn;
			return this;
		}

		public Builder lastName(String ln) {
			this.lastName = ln;
			return this;
		}

		public Builder authorities(Set<Role> authorities) {
			this.authorities = new HashSet<Role>(authorities);
			return this;
		}

		public Builder accountNonExpired(Boolean b) {
			this.accountNonExpired = b;
			return this;
		}

		public Builder accountNonLocked(Boolean b) {
			this.accountNonLocked = b;
			return this;
		}

		public Builder credentialsNonExpired(Boolean b) {
			this.credentialsNonExpired = b;
			return this;
		}

		public Builder enabled(Boolean b) {
			this.enabled = b;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

	private User(Builder b) {
		username = b.username;
		password = b.password;
		email = b.email;
		firstName = b.firstName;
		lastName = b.lastName;

		if(b.authorities != null) 
			authorities = b.authorities;
		
		accountNonExpired = b.accountNonExpired;
		accountNonLocked = b.accountNonLocked;
		credentialsNonExpired = b.credentialsNonExpired;
		enabled = b.enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	
	
	//Gong's adding
	
	/*private Set<Course> courses = new HashSet<Course>();

	
	@ManyToMany(mappedBy="user")
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}*/
	
	
	
}
