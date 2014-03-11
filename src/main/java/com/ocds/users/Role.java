package com.ocds.users;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;



@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Role implements GrantedAuthority {
	    private static final long serialVersionUID = 1L;
	    private String name;

	    public static String[] roleNames = { "ROLE_ADMIN","ROLE_INSTRUCTOR","ROLE_STUDENT" };
	    
	    public Role(String n) {
	    	this.name = n;
	    }

	    
	    @Override
	    public String getAuthority() {
	        return this.name;
	    }


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Role other = (Role) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	    

	
}
