package softwareproject;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Instructor extends Common{
    private String name;
    private String email_id;
    private Connection conn;
    Instructor(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }
    public String display(Scanner scn){
        // Scanner scn = new Scanner(System.in);
        System.out.println("Press 1. for viewing grade");
        System.out.println("Press 2. for updating grade");
        System.out.println("Press 3. for registering a course");
        System.out.println("Press 4. for deregestering a course");
        System.out.println("Press 5. for validating grade");
        System.out.println("Press 6. for logout");
        String input = scn.nextLine();
        // scn.close();
        return input;
    }
    public boolean elligible_update_grade(){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM config";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            // System.out.println(rs.getString(5));
            if(rs.getString(7).equals("t") || rs.getString(9).equals("t")){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public boolean elligible_register_course(){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM config";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            // System.out.println(rs.getString(5));
            if(rs.getString(3).equals("t")){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public boolean elligible_validate_course(){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM config";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            // System.out.println(rs.getString(5));
            if(rs.getString(9).equals("t")){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public void show_course_record(String course_code){
        try {
            Statement statement = conn.createStatement();
            String table_name=course_code+"_"+email_id.substring(0,email_id.indexOf("@"));
            String query = "SELECT * FROM "+table_name+"";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Email_id   Name   Grade");
            while(rs.next()){
                System.out.println(rs.getString(1)+"   "+rs.getString(2)+"   "+rs.getString(3));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void update_grade(String course_code){
        try {
            File file = new File(
                    "C:\\softwareProject\\assets\\grade.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st = "";
            while ((st = br.readLine()) != null && !(st.equals("#"))) {
                String[] parameters = st.split(",");
                String user_email=parameters[0];
                String user_name=parameters[1];
                String user_grade=parameters[2];
                // System.out.println(user_email+user_name+user_grade);
                try{
                    Statement statement = conn.createStatement();
                    String table_name=course_code+"_"+email_id.substring(0,email_id.indexOf("@"));
                    String query = "INSERT INTO "+table_name+" (email_id,name,grade) VALUES ('"+user_email+"','"+user_name+"','"+user_grade+"')";
                    statement.executeUpdate(query);
                    table_name="s_"+user_email.substring(0,11);
                    query="UPDATE "+table_name+" SET grade='"+user_grade+"' WHERE course_code='"+course_code+"' AND grade IS NULL";
                    statement.executeUpdate(query);
                }catch(SQLException e){
                    e.printStackTrace();
                }       
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean ispresentincatalog(String current_year,String current_semester,String course_code){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE course_code='"+course_code+"' AND academic_year='"+current_year+"' AND semester='"+current_semester+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public void addcourse(String course_code,String cgpa){
        try {
            Statement statement = conn.createStatement();
            String query = "INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints) VALUES ('"+email_id+"','"+course_code+"','"+cgpa+"')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void removecourse(String course_code){
        try {
            Statement statement = conn.createStatement();
            String query = "DELETE FROM course_offerings WHERE instructor_id='"+email_id+"' AND course_code='"+course_code+"'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void validatecourse(String course_code){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM report_validator WHERE course_code='"+course_code+"' AND instructor_id='"+email_id+"'";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Email_id");
            while(rs.next()){
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


