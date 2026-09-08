package com.oibsip.onlineexam.repository;
import com.oibsip.onlineexam.model.Question; import java.util.*;
public class QuestionRepository { public List<Question> findAll(){ return List.of(
new Question(1,"What is the size of int in Java?","2 bytes","4 bytes","8 bytes","Depends on OS",'B',"Java int is always 32 bits."),
new Question(2,"Which keyword is used to inherit a class?","implement","inherits","extends","super",'C',"extends creates class inheritance."),
new Question(3,"Which collection does not allow duplicate elements?","List","Set","Map","Queue",'B',"Set models unique elements."),
new Question(4,"Which exception is unchecked?","IOException","SQLException","NullPointerException","ClassNotFoundException",'C',"RuntimeException subclasses are unchecked."),
new Question(5,"Which interface is used by lambda expressions?","FunctionalInterface","Serializable","Cloneable","Marker",'A',"A functional interface has one abstract method."),
new Question(6,"Which class represents immutable character sequences?","String","StringBuilder","StringBuffer","CharArray",'A',"String objects are immutable."),
new Question(7,"Which method starts a new Thread?","run()","start()","begin()","execute()",'B',"start() schedules the thread and invokes run()."),
new Question(8,"JDBC is primarily used for what?","GUI","Database connectivity","Networking","Compilation",'B',"JDBC provides Java database connectivity."),
new Question(9,"Which SQL command retrieves rows?","INSERT","UPDATE","SELECT","DELETE",'C',"SELECT queries data."),
new Question(10,"Which annotation commonly marks a Spring component?","@Component","@JavaBean","@Object","@Injectable",'A',"@Component enables component scanning."),
new Question(11,"Which OOP concept hides implementation details?","Inheritance","Encapsulation","Polymorphism","Casting",'B',"Encapsulation controls access to state and behavior."),
new Question(12,"Which is a checked exception?","ArithmeticException","IllegalArgumentException","IOException","NullPointerException",'C',"IOException is checked."),
new Question(13,"What does Stream.filter() do?","Sorts","Transforms","Selects matching elements","Terminates JVM",'C',"filter retains elements matching a predicate."),
new Question(14,"Which keyword prevents method overriding?","static","final","private","protected",'B',"A final method cannot be overridden."),
new Question(15,"Which Map permits one null key?","Hashtable","HashMap","EnumMap","ConcurrentHashMap",'B',"HashMap permits a null key."),
new Question(16,"Which SQL constraint uniquely identifies a row?","FOREIGN KEY","PRIMARY KEY","CHECK","DEFAULT",'B',"A primary key uniquely identifies rows."),
new Question(17,"Spring Boot mainly helps by providing what?","Manual server setup","Auto-configuration and starters","Only frontend UI","Only SQL",'B',"Boot reduces configuration using conventions and starters."),
new Question(18,"Which keyword handles an exception?","catch","throwable","error","handle",'A',"catch handles exceptions thrown from try."),
new Question(19,"Which class is preferred for mutable strings in single-threaded code?","String","StringBuilder","StringBuffer","Character",'B',"StringBuilder avoids synchronization overhead."),
new Question(20,"Which principle says a class should have one reason to change?","DRY","SRP","KISS","DIP",'B',"Single Responsibility Principle.") ); } }
