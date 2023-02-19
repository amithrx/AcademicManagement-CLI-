
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
    
    private final String url = "jdbc:postgresql://localhost/miniproject";
    private final String user = "postgres";
    private final String password = "Amit@7870";
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public String[] validate(String email_id,String password,Connection conn){
        String values[]=new String[2]; //name,role
        values[0]="";
        values[1]="";
        try (Statement statement = conn.createStatement()) {
            String query = "SELECT * FROM users WHERE email_id='"+email_id+"' AND password='"+password+"' ";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                values[0]=rs.getString(2);
                values[1]=rs.getString(4);
                return values;
            }else{
                return values;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return values;
        }
    }
    public static void main(String[] args) {
        App app= new App();
        Connection conn = app.connect();
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
              String[] userCredentials=app.validate(email,pass,conn);
                  if(!userCredentials[0].equals("")){   
                      String name=userCredentials[0];
                      String role=userCredentials[1];
                      if(role.equals("s")){
                        Student s = new Student(name,email,conn);
                        s.studentOption();
                      }else if(role.equals("i")){
                        Instructor i = new Instructor(name,email,conn);
                        i.instructorOption();
                      }else{
                        Admin a = new Admin(name,email,conn);
                        a.adminOption();
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
