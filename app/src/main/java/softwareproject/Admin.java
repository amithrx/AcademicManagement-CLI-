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
    public boolean elligiible_edit_catalog(){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM config";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            // System.out.println(rs.getString(5));
            if(rs.getString(1).equals("t")){
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
    public void addcoursecatalog(String course_code,String l,String t,String p,
    String academic_year,String semester,String prerequisites,String branch_elligible,
    String minm_semester,String core_elective){
        String query="";
        branch_elligible="'"+branch_elligible+"'";
        branch_elligible=branch_elligible.replace(",", "','");
        core_elective="'"+core_elective+"'";
        core_elective=core_elective.replace(",", "','");

        if(prerequisites.length()==0){
            query = "INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,branch_elligible,minm_semester_elligible,core_elective) VALUES('"+course_code+"','"+l+"','"+t+"','"+p+"','"+academic_year+"','"+semester+"',ARRAY["+branch_elligible+"],'"+minm_semester+"',ARRAY["+core_elective+"])";
        }else{
            prerequisites="'"+prerequisites+"'";
            // System.out.println(prerequisites);
            prerequisites=prerequisites.replace(",", "','");
            // System.out.println(prerequisites);
            query = "INSERT INTO course_catalog(course_code,L,T,P,academic_year,semester,prerequisites,branch_elligible,minm_semester_elligible,core_elective) VALUES('"+course_code+"','"+l+"','"+t+"','"+p+"','"+academic_year+"','"+semester+"',ARRAY["+prerequisites+"],ARRAY["+branch_elligible+"],'"+minm_semester+"',ARRAY["+core_elective+"])";
        }  
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void updatecoursecatalog(String course_code,String l,String t,String p,
    String academic_year,String semester,String prerequisites,String branch_elligible,
    String minm_semester,String core_elective){
        String query="";
        branch_elligible="'"+branch_elligible+"'";
        branch_elligible=branch_elligible.replace(",", "','");
        core_elective="'"+core_elective+"'";
        core_elective=core_elective.replace(",", "','");
        if(prerequisites.length()==0){
            query = "UPDATE course_catalog SET L='"+l+"',T='"+t+"',P='"+p+"',branch_elligible=ARRAY["+branch_elligible+"],minm_semester_elligible='"+minm_semester+"',core_elective=ARRAY["+core_elective+"] WHERE course_code='"+course_code+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
        }else{
            prerequisites="'"+prerequisites+"'";
            // System.out.println(prerequisites);
            prerequisites=prerequisites.replace(",", "','");
            // System.out.println(prerequisites);
            query = "UPDATE course_catalog SET L='"+l+"',T='"+t+"',P='"+p+"',prerequisites=ARRAY["+prerequisites+"],branch_elligible=ARRAY["+branch_elligible+"],minm_semester_elligible='"+minm_semester+"',core_elective=ARRAY["+core_elective+"] WHERE course_code='"+course_code+"' AND academic_year='"+academic_year+"' AND semester='"+semester+"'";
        }  
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean check_catalog_offered(String course_code,String academic_year,String semester){
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
            // TODO Auto-generated catch block
            e.printStackTrace();
            return true;
        }
    }
}
