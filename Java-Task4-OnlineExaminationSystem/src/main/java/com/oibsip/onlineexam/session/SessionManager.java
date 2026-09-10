package com.oibsip.onlineexam.session;
import com.oibsip.onlineexam.model.*;
public class SessionManager {private User user; private ExamSession exam; public void login(User u){user=u;} public User getUser(){return user;} public void startExam(ExamSession e){exam=e;} public ExamSession getExam(){return exam;} public void clear(){user=null;exam=null;} public boolean loggedIn(){return user!=null;}}
