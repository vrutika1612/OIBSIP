package com.oibsip.onlineexam.repository;
import com.oibsip.onlineexam.model.User; import com.oibsip.onlineexam.util.PasswordUtil; import java.util.*;
public class UserRepository { private final Map<String,User> users=new HashMap<>(); public UserRepository(){users.put("student",new User("student",PasswordUtil.hash("student123"),"Vrutika Patel"));} public Optional<User> find(String u){return Optional.ofNullable(users.get(u));} }
