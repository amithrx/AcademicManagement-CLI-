package softwareproject;
import java.util.*;
import java.sql.*;

public class Student {
    String name;
    String email_id;
    Connection conn;
    Student(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }
    public String display(Scanner scn){
        // Scanner scn = new Scanner(System.in);
        System.out.println("Press 1. for registering a course");
        System.out.println("Press 2. for de-registering a course");
        System.out.println("Press 3. for viewing grade");
        System.out.println("Press 4. for computing CGPA");
        System.out.println("Press 5. for checking whether graduated or not");
        System.out.println("Press 6. for logout");
        String input = scn.nextLine();
        // scn.close();
        return input;
    }
    public void log(int state){
        try {
            Statement statement = conn.createStatement();
            String status = "logged_in";
            if(state==1){
                status="logged_out";
            }
            String query = "INSERT INTO login_logout (email_id,status) VALUES('"+email_id+"','"+status+"')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Boolean check_elgible_for_enrolling(){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM config";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            // System.out.println(rs.getString(5));
            if(rs.getString(5).equals("t")){
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
    public String[] current_session(){
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
            // System.out.println(rs.getString(5));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return values;
        }
    }
    public boolean check_previous(String current_year,String current_semester){
        int year=Integer.parseInt(current_year);
        int semester=Integer.parseInt(current_semester);
        try {
            Statement statement = conn.createStatement();
            // only 2020csb1070@iitrpr.ac.in format email is allowed
            // System.out.println(email_id);  
            String table_name="s_"+email_id.substring(0,11);
            // System.out.println(table_name);
            if(semester==2){
                semester-=1;
            }else{
                semester+=1;
                year-=1;
            }
            String y=Integer.toString(year);
            String s=Integer.toString(semester);
            String query = "SELECT * FROM "+table_name+" WHERE academic_year= '" +y+ "' AND semester= '" +s+ "' ";
            // System.out.println(query);
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
    public boolean check_back_previous(String current_year,String current_semester){
        int year=Integer.parseInt(current_year);
        int semester=Integer.parseInt(current_semester);
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            year-=1;
            String y=Integer.toString(year);
            String s=Integer.toString(semester);
            String query = "SELECT * FROM "+table_name+" WHERE academic_year='"+y+"' AND semester='"+s+"'";
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
    public String[] check_offered_or_not(String course_code,String instructor_id){
        String values[]=new String[2];
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
            // TODO Auto-generated catch block
            e.printStackTrace();
            return values;
        }
    }
    public float calc_CGPA(){
        float cgpa=0;
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".grade!='F'";
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            // 7(grade),11(credit)
            while(rs.next()){
                credit+=Float.parseFloat(rs.getString(11));
                if(rs.getString(6).equals("A")){
                    cgpa+=(Float.parseFloat(rs.getString(11))*10);
                }
                if(rs.getString(6).equals("A-")){
                    cgpa+=(Float.parseFloat(rs.getString(11))*9);
                }
                if(rs.getString(6).equals("B")){
                    cgpa+=(Float.parseFloat(rs.getString(11))*8);
                }
                if(rs.getString(6).equals("B-")){
                    cgpa+=(Float.parseFloat(rs.getString(11))*7);
                }
                if(rs.getString(6).equals("C")){
                    cgpa+=(Float.parseFloat(rs.getString(11))*6);
                }
                if(rs.getString(6).equals("C-")){
                    cgpa+=(Float.parseFloat(rs.getString(11))*5);
                }
                if(rs.getString(6).equals("D")){
                    cgpa+=(Float.parseFloat(rs.getString(11))*4);
                }
            }
            System.out.println(cgpa);
            System.out.println(credit);
            return cgpa/credit;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return cgpa;
        }
    }
}
