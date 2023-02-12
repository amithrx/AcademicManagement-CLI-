package softwareproject;
import java.util.*;
import java.sql.*;

public class Admin extends Common{
    private String name;
    private String email_id;
    private Connection conn;
    Admin(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }
    public String display(Scanner scn){
        // Scanner scn = new Scanner(System.in);
        System.out.println("Press 1. for editing course_catalog");
        System.out.println("Press 2. for viewing grade");
        System.out.println("Press 3. for generating transcripts");
        System.out.println("Press 4. for editing deadlines");
        System.out.println("Press 5. for editing current sessions");
        System.out.println("Press 6. for validating grade");
        System.out.println("Press 7. for logout");
        String input = scn.nextLine();
        // scn.close();
        return input;
    }
}
