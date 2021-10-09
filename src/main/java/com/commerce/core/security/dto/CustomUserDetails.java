package com.commerce.core.security.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    
    private Long id;
    
    private String email;
    
    private String company;

    private Integer companyId;
    
    private String image;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    private boolean enabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;
    
    public CustomUserDetails(MemberDto member, Collection<? extends GrantedAuthority> authorities) {
        
        super(member.getUsername(), member.getPassword(), authorities);
        
        setId(member.getId());
        setEmail(member.getEmail());
        setImage(member.getImage());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
