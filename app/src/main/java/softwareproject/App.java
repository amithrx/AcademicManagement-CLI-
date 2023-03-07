
/*
 * grading system:
 * A:10, A-:9, B:8, B-:7, C:6, C-:5, D:4, F:0
 * CGPA=(summation(f(grade)*credit))/(total credit)
 * Exclude credit contribution by 'F' grade
 */
package softwareproject;
import java.sql.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        // App app= new App();
        Connection conn = DBconnect.connect();
        //interface
        while(true){
          System.out.println("!!!<-- Welcome user -->!!!");
          System.out.println("Press 1. for Login");
          System.out.println("Press 2. for Exit");
          Scanner scn = new Scanner(System.in);
          String input = scn.nextLine();
            if(input.equals("1")){
              System.out.println("Enter Email");
              String email = scn.nextLine();
              System.out.println("Enter Password");
              String pass = scn.nextLine();
              String[] userCredentials=ValidateUsers.validate(email,pass,conn);
                  if(!userCredentials[0].equals("")){   
                      String name=userCredentials[0];
                      String role=userCredentials[1];
                      if(role.equals("s")){
                        //student interface
                        Student s = new Student(name,email,conn);
                        s.studentOption(scn);
                      }else if(role.equals("i")){
                        //instructor interface
                        Instructor i = new Instructor(name,email,conn);
                        i.instructorOption(scn);
                      }else{
                        //admin interface
                        Admin a = new Admin(name,email,conn);
                        a.adminOption(scn);
                      }
                  }else{
                      System.out.println("Credential invalid");
                  }
  
            }else if(input.equals("2")){
                System.exit(0);
            }
            else{
                System.out.println("Wrong input");
            }
        }
    }
}
