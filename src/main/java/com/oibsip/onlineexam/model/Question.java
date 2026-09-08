package com.oibsip.onlineexam.model;
public record Question(int id,String questionText,String optionA,String optionB,String optionC,String optionD,char correctAnswer,String explanation) { public String option(char c){return switch(Character.toUpperCase(c)){case 'A'->optionA;case 'B'->optionB;case 'C'->optionC;case 'D'->optionD;default->"";};} }
