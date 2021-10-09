package com.commerce.core.security.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.commerce.core.security.dto.MemberDto;

@FeignClient(name = MemberService.SERVICE_NAME)
public interface MemberService {
    
	public static final String SERVICE_NAME = "service-member";
	
    @GetMapping(value = "findByEmailOrUsername/{email}/{username}", produces = "application/json")
    public MemberDto findByEmailOrUsername(@PathVariable("email") String email, @PathVariable("username") String username);

    @GetMapping(value = "findByEmailOrUsernameOrPhone/{email}/{username}/{phone}", produces = "application/json")
    public MemberDto findByEmailOrUsernameOrPhone(@PathVariable("email") String email, @PathVariable("username") String username, @PathVariable("phone") String phone);

    @GetMapping(value = "findByUsername/{username}", produces = "application/json")
    public MemberDto findByUsername(@PathVariable("username") String username);
    
    @PostMapping(value = "registerMember", consumes = "application/json")
    public Long registerMember(@RequestBody MemberDto member);
    
    @PostMapping(value = "updateMember", consumes = "application/json")
    public Long updateMember(@RequestBody MemberDto member);
}

