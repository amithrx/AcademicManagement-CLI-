package softwareproject;
import java.util.*;
import java.sql.*;
import java.io.*;

public class Admin extends Person{
    //constructor for admin
    Admin(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }

    //admin interface
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

    //function to modify the existing course_catalog or to add a new course catalog
    public boolean modifyCourseCatalog(String course_code,String l,String t,String p,
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    //function to check whether a course has been present in the catalog or not for a particular academic_year and semester
    public boolean checkCatalogOffered(String course_code,String academic_year,String semester){
        //return false when a course is in catalog else true
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

    //function to check whether a particular email id is correct or not
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

    //function to generate the trascript for a particular student
    public boolean generateTranscripts(String stu_email){
        
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+stu_email.substring(0,11);
            String query = "SELECT * FROM "+table_name+" ORDER BY academic_year,semester";
            ResultSet rs = statement.executeQuery(query);
            File file = new File("C:\\softwareProject\\assets\\"+table_name+".txt");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Transcripts for studentId: "+stu_email+"\n");
                writer.write("\n");
                writer.write("Academic_year  Semester  Course_code  Grade");
                writer.write("\n");
                while (rs.next()) {
                    writer.write(rs.getString(1));
                    writer.write("            ");
                    writer.write(rs.getString(2));
                    writer.write("          ");
                    writer.write(rs.getString(4));
                    writer.write("        ");
                    writer.write(rs.getString(6));
                    writer.write("\n");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file: " + e.getMessage());
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //function to set the deadline for grade submission and grade validation
    public boolean setGradeDeadline(String start, String end, String validation_check){
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //function to set the rest of the deadline such as course_register, course_float e.tc
    public boolean setRestDeadline(String type, String start, String end){
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //function to update the current sessions
    public boolean updateSession(String newyear,String newsem,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String query = "UPDATE current_sessions SET academic_year='"+newyear+"',semester='"+newsem+"'";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("ttt");
            return false;
        }
    }

    //function to validate the grade after the grade submission ends so that instructor can see if certain students gets left or not after grade submission ends
    public boolean validate(String academic_year,String semester){
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
                    String query2 = "INSERT INTO report_validator(course_code,student_id,instructor_id) VALUES('"+rs1.getString(4)+"','"+rs.getString(1)+"','"+rs1.getString(5)+"') ON CONFLICT DO NOTHING;";
                    statement2.executeUpdate(query2);
                }  
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //function to check for conditions whether admin will be able to generate the transcripts or not
    public boolean checkGenerateTranscript(String stu_email){
        if(checkStudentEmail(stu_email)){
            generateTranscripts(stu_email);
            System.out.println("Transcript generated");
            return true;
        }else{
            System.out.println("No such student");
            return false;
        }
    }

    //function to check for updating the sessions, if entered input by the user is of same as or previous it gets returned without modifications
    public boolean checkUpdateCurrentSession(String year,String sem){
        String[] result=current_session(conn);
        if(!year.equals(result[0])){
            updateSession(year,sem,result[0],result[1]);
            System.out.println("Succesfully updated");
            return true;
        }else if(!sem.equals(result[1])){
            updateSession(year,sem,result[0],result[1]);
            System.out.println("Succesfully updated");
            return true;
        }
        return false;
    }

    //function to check for the admin whether he can able to validate the grade submission or not
    public boolean checkValidate(){
        String[] result=current_session(conn);
        if(checkElligibility("8",conn)){
            validate(result[0],result[1]);
            System.out.println("Successfully validated");
            return true;
        }else{
            System.out.println("Not elligible for validating");
            return false;
        }
    }

    //interface for admin to enter the respective input
    public boolean adminOption(Scanner scnA){
        log(0,conn,email_id);
        String[] result=current_session(conn);
        boolean output=false;
        System.out.println("Welcome "+name);
        while(true){
            String inputA=display(scnA);
            if(inputA.equals("1")){
                //edit course_Catalog
                if(checkElligibility("1",conn)){
                    System.out.println("Press 1. for adding course, 2. for editing course");
                    String take=scnA.nextLine();
                    if(take.equals("1")){
                        System.out.println("Enter course_code");
                        String input_code=scnA.nextLine();
                        if(checkCatalogOffered(input_code,result[0],result[1])){
                            System.out.println("Enter L");
                            String l=scnA.nextLine();
                            System.out.println("Enter T");
                            String t=scnA.nextLine();
                            System.out.println("Enter P");
                            String p=scnA.nextLine();
                            System.out.println("Enter prerequisites(comma separated)");
                            String prerequisites=scnA.nextLine();
                            System.out.println("Enter branch_elligible(comma separated)");
                            String branch_elligible=scnA.nextLine();
                            System.out.println("Enter minm semester required");
                            String minm_sem=scnA.nextLine();
                            System.out.println("Enter core_elective(comma separated) (PC for core and PE for elective)");
                            String core_elective=scnA.nextLine();
                            output=modifyCourseCatalog(input_code,l,t,p,result[0],result[1],prerequisites,branch_elligible,minm_sem,core_elective,"0");
                        }else{
                            System.out.println("Already in catalog");
                        }
                        
                    }else if(take.equals("2")){
                        System.out.println("Enter course_code");
                        String input_code=scnA.nextLine();
                        if(checkCatalogOffered(input_code, result[0], result[1])){
                            System.out.println("Not offered");
                        }else{
                            System.out.println("Enter L");
                            String l=scnA.nextLine();
                            System.out.println("Enter T");
                            String t=scnA.nextLine();
                            System.out.println("Enter P");
                            String p=scnA.nextLine();
                            System.out.println("Enter prerequisites(comma separated)");
                            String prerequisites=scnA.nextLine();
                            System.out.println("Enter branch_elligible(comma separated)(Needed)");
                            String branch_elligible=scnA.nextLine();
                            System.out.println("Enter minm semester required(Needed)");
                            String minm_sem=scnA.nextLine();
                            System.out.println("Enter core_elective(comma separated) (PC for core and PE for elective)(Needed)");
                            String core_elective=scnA.nextLine();
                            output= modifyCourseCatalog(input_code,l,t,p,result[0],result[1],prerequisites,branch_elligible,minm_sem,core_elective,"1");
                        }

                    }else{
                        System.out.println("Wrong input");
                    }
                }else{
                    System.out.println("Can't edit course catalog now");
                }

            }else if(inputA.equals("2")){
                //viewing grade
                System.out.println("Enter student email_id");
                String stu_email=scnA.nextLine();
                if(checkStudentEmail(stu_email)){
                    System.out.println("Enter academic_year");
                    String ac_year=scnA.nextLine();
                    System.out.println("Enter semester");
                    String sem=scnA.nextLine();
                    output=viewGrade(stu_email,ac_year,sem);
                }else{
                    System.out.println("No such student");
                }
            }else if(inputA.equals("3")){
                //generating transcripts
                System.out.println("Enter student email_id");
                String stu_email=scnA.nextLine();
                output=checkGenerateTranscript(stu_email);
            }else if(inputA.equals("4")){
                //edit deadlines
                System.out.println("Press 1. for course_catalog, 2. for course_float, 3. for course_register, 4. for grade");
                String user_input=scnA.nextLine();
                if(user_input.equals("4")){
                    System.out.println("Input should be 'T' or 'F', with one 'T' at max");
                    System.out.println("Set start time");
                    String start = scnA.nextLine();
                    System.out.println("Set end time");
                    String end= scnA.nextLine();
                    System.out.println("Set validation_check time");
                    String validation_check=scnA.nextLine();
                    output=setGradeDeadline(start,end,validation_check);
                }else if(user_input.equals("1")){
                    System.out.println("Input should be 'T' or 'F', with one 'T' at max");
                    System.out.println("Set start time");
                    String start = scnA.nextLine();
                    System.out.println("Set end time");
                    String end= scnA.nextLine();
                    output=setRestDeadline(user_input,start,end);
                }
                else if(user_input.equals("2")){
                    System.out.println("Input should be 'T' or 'F', with one 'T' at max");
                    System.out.println("Set start time");
                    String start = scnA.nextLine();
                    System.out.println("Set end time");
                    String end= scnA.nextLine();
                    output=setRestDeadline(user_input,start,end);
                }
                else if(user_input.equals("3")){
                    System.out.println("Input should be 'T' or 'F', with one 'T' at max");
                    System.out.println("Set start time");
                    String start = scnA.nextLine();
                    System.out.println("Set end time");
                    String end= scnA.nextLine();
                    output=setRestDeadline(user_input,start,end);
                }
                else{
                    System.out.println("Wrong input");
                }
            }else if(inputA.equals("5")){
                //update current sessions
                System.out.println("Enter academic_year");
                String year=scnA.nextLine();
                System.out.println("Enter semester");
                String sem=scnA.nextLine();
                output=checkUpdateCurrentSession(year, sem);
            }else if(inputA.equals("6")){
                //validating grade
                output=checkValidate();
            }else if(inputA.equals("7")){
                log(1,conn,email_id);
                    break;
            }
            else{
            }
        }
        return output;
    }
}
