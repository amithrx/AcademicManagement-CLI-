package softwareproject;
import java.util.*;
import java.sql.*;

public class Person {
    protected String name;
    protected String email_id;
    protected Connection conn;
    
    public void log(int state,Connection conn,String email_id){
        try {
            Statement statement = conn.createStatement();
            String status = "logged_in";
            if(state==1){
                status="logged_out";
            }
            String query = "INSERT INTO login_logout (email_id,status) VALUES('"+email_id+"','"+status+"')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String[] current_session(Connection conn){
        String[] values=new String[2];
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM current_sessions";
            ResultSet rs = statement.executeQuery(query);

            if(rs.next()){
                values[0]=rs.getString(1);
                values[1]=rs.getString(2);
            }
            return values;
        } catch (SQLException e) {
            e.printStackTrace();
            return values;
        }
    }
    public String[] checkOfferedOrNot(String course_code,String instructor_id,Connection conn){
        String values[]=new String[2];
        values[0]="false";
        values[1]="0";
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_offerings WHERE course_code='"+course_code+"' AND instructor_id='"+instructor_id+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                values[0]="true";
                values[1]=rs.getString(3);
                return values;
            }else{
                values[0]="false";
                return values;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return values;
        }
    }
    public boolean checkElligibility(String type,Connection conn){
        int check=Integer.parseInt(type);
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM config";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            // System.out.println(rs.getString(5));
            if(rs.getString(check).equals("t")){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void viewGrade(String stu_email,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+stu_email.substring(0,11);
            String query = "SELECT * FROM "+table_name+" WHERE academic_year='"+academic_year+"' AND semester='"+semester+"'";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Course_code   Instructor_id   Grade");
            while(rs.next()){
                System.out.println(rs.getString(4)+"   "+rs.getString(5)+"   "+rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
