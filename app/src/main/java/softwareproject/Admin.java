package softwareproject;
import java.util.*;
import java.sql.*;
import java.io.*;

public class Admin extends Person{
    Admin(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }

    public String display(Scanner scn){
        System.out.println("Press 1. for editing course_catalog");
        System.out.println("Press 2. for viewing grade");
        System.out.println("Press 3. for generating transcripts");
        System.out.println("Press 4. for editing deadlines");
        System.out.println("Press 5. for editing current sessions");
        System.out.println("Press 6. for validating grade");
        System.out.println("Press 7. for logout");
        String input = scn.nextLine();
        return input;
    } 

    public void modifyCourseCatalog(String course_code,String l,String t,String p,
    String academic_year,String semester,String prerequisites,String branch_elligible,
    String minm_semester,String core_elective,String type){
        String query="";
        branch_elligible="'"+branch_elligible+"'";
        branch_elligible=branch_elligible.replace(",", "','");
        core_elective="'"+core_elective+"'";
        core_elective=core_elective.replace(",", "','");
        if(type.equals("0")){
            //add course_catalog
                if(prerequisites.length()==0){
            query = "INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,branch_elligible,minm_semester_elligible,core_elective) VALUES('"+course_code+"','"+l+"','"+t+"','"+p+"','"+academic_year+"','"+semester+"',ARRAY["+branch_elligible+"],'"+minm_semester+"',ARRAY["+core_elective+"])";
        }else{
            prerequisites="'"+prerequisites+"'";
            prerequisites=prerequisites.replace(",", "','");
            query = "INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,prerequisites,branch_elligible,minm_semester_elligible,core_elective) VALUES('"+course_code+"','"+l+"','"+t+"','"+p+"','"+academic_year+"','"+semester+"',ARRAY["+prerequisites+"],ARRAY["+branch_elligible+"],'"+minm_semester+"',ARRAY["+core_elective+"])";
        }  
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }else{
            //update course_catalog
            if(prerequisites.length()==0){
                query = "UPDATE course_catalog SET L='"+l+"',T='"+t+"',P='"+p+"',branch_elligible=ARRAY["+branch_elligible+"],minm_semester_elligible='"+minm_semester+"',core_elective=ARRAY["+core_elective+"] WHERE course_code='"+course_code+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
            }else{
                prerequisites="'"+prerequisites+"'";
                prerequisites=prerequisites.replace(",", "','");
                query = "UPDATE course_catalog SET L='"+l+"',T='"+t+"',P='"+p+"',prerequisites=ARRAY["+prerequisites+"],branch_elligible=ARRAY["+branch_elligible+"],minm_semester_elligible='"+minm_semester+"',core_elective=ARRAY["+core_elective+"] WHERE course_code='"+course_code+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
            }  
            try {
                Statement statement = conn.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkCatalogOffered(String course_code,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE course_code='"+course_code+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                return false;
            }else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean checkStudentEmail(String stu_email){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM users WHERE email_id='"+stu_email+"'";
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

    public void generateTranscripts(String stu_email){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+stu_email.substring(0,11);
            String query = "SELECT * FROM "+table_name+" ORDER BY academic_year,semester";
            ResultSet rs = statement.executeQuery(query);
            File file = new File("C:\\softwareProject\\assets\\"+table_name+".txt");
            try (FileWriter writer = new FileWriter(file)) {
                while (rs.next()) {
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        if(i==rs.getMetaData().getColumnCount()){
                            writer.write(rs.getString(i)+"");
                        }else{
                            writer.write(rs.getString(i) + ",");
                        }
                    }
                    writer.write("\n");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGradeDeadline(String start, String end, String validation_check){
        try {
            Statement statement = conn.createStatement();
            String query = "";
            if(start.equals("T") && end.equals("F") && validation_check.equals("F")){
                query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=true,grade_end=false,validation_check_end=false";

            }else if(start.equals("F") && end.equals("T") && validation_check.equals("F")){
                query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=true,validation_check_end=false";
            }else if(start.equals("F") && end.equals("F") && validation_check.equals("T")){
                query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=true";
            }else{
                System.out.println("Not correct format input");
            }
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRestDeadline(String type, String start, String end){
        try {
            Statement statement = conn.createStatement();
            String query = "";
            if(type.equals("1")){
                if(start.equals("T") && end.equals("F")){
                    query="UPDATE config SET course_catalog_start=true,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
                }else if(start.equals("F") && end.equals("T")){
                    query="UPDATE config SET course_catalog_start=false,course_catalog_end=true,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
                }else{
                    System.out.println("Not correct format input");
                }
            }else if(type.equals("2")){
                if(start.equals("T") && end.equals("F")){
                    query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=true,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
                }else if(start.equals("F") && end.equals("T")){
                    query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=true,course_register_start=false,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
                }else{
                    System.out.println("Not correct format input");
                }

            }else if(type.equals("3")){
                if(start.equals("T") && end.equals("F")){
                    query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=true,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
                }else if(start.equals("F") && end.equals("T")){
                    query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=true,grade_start=false,grade_end=false,validation_check_end=false";
                }else{
                    System.out.println("Not correct format input");
                }

            }else{
                System.out.println("Invalid input");
            }
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSession(String newyear,String newsem,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String query = "UPDATE current_sessions SET academic_year='"+newyear+"',semester='"+newsem+"'";
            statement.executeUpdate(query);
            query="SELECT * FROM users WHERE role='s'";
            ResultSet rs=statement.executeQuery(query);

            while(rs.next()){
                Statement statement1=conn.createStatement();
                String table_name="s_"+rs.getString(1).substring(0,11);
                String stu_branch=rs.getString(1).substring(4,7);
                String whether_ce="";
                String query1 = "SELECT * FROM "+table_name+" WHERE grade='F' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
                ResultSet rs1=statement1.executeQuery(query1);

                while(rs1.next()){
                    Statement statement3=conn.createStatement();
                    String query3="SELECT * FROM course_catalog WHERE course_code='"+rs1.getString(4)+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
                    ResultSet rs3=statement3.executeQuery(query3);
                    rs3.next();
                    String input_branch=rs3.getString(9).substring(1,rs.getString(9).length()-1);
                    String branch[]=input_branch.split(",");
                    String input_ce=rs3.getString(11).substring(1,rs.getString(11).length()-1);
                    String ce[]=input_ce.split(",");
                    for(int i=0;i<ce.length;++i){
                        if(branch[i].equals(stu_branch)){
                            whether_ce=ce[i];
                            break;
                        }
                    }
                    if(whether_ce.equals("PC")){
                        Statement statement2 = conn.createStatement();
                        String query2 = "INSERT INTO "+table_name+" (academic_year,semester,name,course_code,instructor_id) VALUES('"+newyear+"','"+newsem+"','"+rs.getString(2)+"','"+rs1.getString(4)+"','"+rs1.getString(5)+"')";
                        statement2.executeUpdate(query2);
                        table_name=rs1.getString(4)+"_"+rs1.getString(5).substring(0,rs1.getString(5).indexOf("@"));
                        query2 = "INSERT INTO "+table_name+" (email_id,name) VALUES ('"+rs.getString(1)+"','"+rs.getString(2)+"')";
                        statement2.executeUpdate(query2);
                    }
                }  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void validate(String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM users WHERE role='s'";
            ResultSet rs=statement.executeQuery(query);
            while(rs.next()){
                Statement statement1=conn.createStatement();
                String table_name="s_"+rs.getString(1).substring(0,11);
                String query1 = "SELECT * FROM "+table_name+" WHERE grade IS NULL AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
                ResultSet rs1=statement1.executeQuery(query1);
                while(rs1.next()){
                    Statement statement2 = conn.createStatement();
                    String query2 = "INSERT INTO report_validator(course_code,student_id,instructor_id) VALUES('"+rs1.getString(4)+"','"+rs.getString(1)+"','"+rs1.getString(5)+"')";
                    statement2.executeUpdate(query2);
                }  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
