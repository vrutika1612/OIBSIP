package com.oibsip.onlineexam.service;
import com.oibsip.onlineexam.model.User; import com.oibsip.onlineexam.repository.UserRepository; import com.oibsip.onlineexam.util.PasswordUtil;
public class AuthenticationService {private final UserRepository repo; public AuthenticationService(UserRepository r){repo=r;} public User authenticate(String u,String p){if(u==null||u.isBlank()||p==null||p.isBlank())return null; return repo.find(u).filter(x->PasswordUtil.matches(p,x.getPasswordHash())).orElse(null);}}
