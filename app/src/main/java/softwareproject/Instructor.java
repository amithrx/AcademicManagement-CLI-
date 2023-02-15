package softwareproject;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Instructor extends Person{
    Instructor(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }

    public String display(Scanner scn){
        System.out.println("Press 1. for viewing grade");
        System.out.println("Press 2. for updating grade");
        System.out.println("Press 3. for registering a course");
        System.out.println("Press 4. for deregestering a course");
        System.out.println("Press 5. for validating grade");
        System.out.println("Press 6. for logout");
        String input = scn.nextLine();
        return input;
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
                try{
                    // TODO: insert into course if student not elligible
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
            e.printStackTrace();
        } catch (IOException e) {
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
            e.printStackTrace();
        }
    }

    public void removecourse(String course_code){
        try {
            Statement statement = conn.createStatement();
            String query = "DELETE FROM course_offerings WHERE instructor_id='"+email_id+"' AND course_code='"+course_code+"'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
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
            e.printStackTrace();
        }
    }

    public void addPCcourse(String course_code,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE course_code='"+course_code+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                Integer minm_semester_elligible=Integer.parseInt(rs.getString(10));
                Integer year=Integer.parseInt(academic_year);
                Integer sem=Integer.parseInt(semester);
                String input_branch=rs.getString(9).substring(1,rs.getString(9).length()-1);
                String branch[]=input_branch.split(",");
                String input_ce=rs.getString(11).substring(1,rs.getString(11).length()-1);
                String ce[]=input_ce.split(",");
                Integer size=branch.length;
                for(int i=0;i<size;++i){
                    if(ce[i].equals("PC")){
                        String b=branch[i];
                        if((minm_semester_elligible-sem)%2==0){
                            minm_semester_elligible=(minm_semester_elligible-sem)/2;
                        }else{
                            minm_semester_elligible=(minm_semester_elligible-sem)/2+1;
                        }
                        minm_semester_elligible=year-minm_semester_elligible;
                        String temp=minm_semester_elligible.toString()+b;
                        Statement statement1 = conn.createStatement();
                        String query1 = "SELECT * FROM users";
                        ResultSet rs1 = statement1.executeQuery(query1);
                        while(rs1.next()){
                            if((rs1.getString(1).substring(0,7)).equals(temp)){
                                String stu_name=rs1.getString(2);
                                Statement statement2 = conn.createStatement();
                                String table_name="s_"+rs1.getString(1).substring(0,11);
                                String query2 = "INSERT INTO "+table_name+" (academic_year,semester,name,course_code,instructor_id) VALUES('"+academic_year+"','"+semester+"','"+stu_name+"','"+course_code+"','"+email_id+"')";
                                statement2.executeUpdate(query2);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removepccourse(String course_code,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE course_code='"+course_code+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                Integer minm_semester_elligible=Integer.parseInt(rs.getString(10));
                Integer year=Integer.parseInt(academic_year);
                Integer sem=Integer.parseInt(semester);
                String input_branch=rs.getString(9).substring(1,rs.getString(9).length()-1);
                String branch[]=input_branch.split(",");
                String input_ce=rs.getString(11).substring(1,rs.getString(11).length()-1);
                String ce[]=input_ce.split(",");
                Integer size=branch.length;
                for(int i=0;i<size;++i){
                    if(ce[i].equals("PC")){
                        String b=branch[i];
                        if((minm_semester_elligible-sem)%2==0){
                            minm_semester_elligible=(minm_semester_elligible-sem)/2;
                        }else{
                            minm_semester_elligible=(minm_semester_elligible-sem)/2+1;
                        }
                        minm_semester_elligible=year-minm_semester_elligible;
                        String temp=minm_semester_elligible.toString()+b;
                        Statement statement1 = conn.createStatement();
                        String query1 = "SELECT * FROM users";
                        ResultSet rs1 = statement1.executeQuery(query1);
                        while(rs1.next()){
                            if((rs1.getString(1).substring(0,7)).equals(temp)){
                                Statement statement2 = conn.createStatement();
                                String table_name="s_"+rs1.getString(1).substring(0,11);
                                String query2 = "DELETE FROM "+table_name+" WHERE academic_year='"+academic_year+"' AND semester='"+semester+"' AND course_code='"+course_code+"' AND instructor_id='"+email_id+"'";
                                statement2.executeUpdate(query2);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


