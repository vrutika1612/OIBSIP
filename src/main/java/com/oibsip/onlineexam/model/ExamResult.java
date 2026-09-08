package com.oibsip.onlineexam.model;
import java.time.Duration; import java.util.*;
public record ExamResult(int score,int correct,int incorrect,int unanswered,int total,double percentage,Duration timeTaken,boolean automatic,Map<Integer,Character> answers,List<Question> questions) {}
