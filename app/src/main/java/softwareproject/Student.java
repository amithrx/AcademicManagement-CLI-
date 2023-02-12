package softwareproject;
import java.util.*;
// import javax.swing.text.Position;
import java.sql.*;

public class Student extends Common{
    private String name;
    private String email_id;
    private Connection conn;
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
    
    public boolean check_previous(String current_year,String current_semester){
        int year=Integer.parseInt(current_year);
        int semester=Integer.parseInt(current_semester);
        try {
            Statement statement = conn.createStatement();
            // only 2020csb1070@iitrpr.ac.in format email is allowed
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
    
    public float calc_CGPA(){
        float cgpa=0;
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".grade!='F'";
            // System.out.println(query);
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
            // System.out.println(cgpa);
            // System.out.println(credit);
            // System.out.println(cgpa/credit);
            if(credit==0)return 0;
            else
            return cgpa/credit;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public boolean check_prerequisites(String course_code,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+academic_year+"' AND semester='"+semester+"' AND course_code='"+course_code+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                String input=rs.getString(8).substring(1,rs.getString(8).length()-1);
                int size=input.length();
                System.out.println(rs.getString(8));
                // System.out.println("size is "+size);
                if(size==0){
                    return true;
                }else{
                    String[] prerequisites=input.split(",");
                    boolean istrue=true;
                    for(int i=0;i<prerequisites.length;++i){
                        String course=prerequisites[i];
                        statement = conn.createStatement();
                        table_name="s_"+email_id.substring(0,11);
                        query = "SELECT * FROM "+table_name+" WHERE course_code='"+course+"' AND grade!='F'";
                        // System.out.println(query);
                        rs = statement.executeQuery(query);
                        if(!rs.next()){
                            istrue=false;
                            break;
                        }
                    }
                    if(istrue==true){
                        return true;
                    }else{
                        return false;
                    }
                }

            }else{
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public float check_current_credits(String academic_year,String semester){
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".grade!='F' AND "+table_name+".academic_year='"+academic_year+"' AND "+table_name+".semester='"+semester+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                credit+=Float.parseFloat(rs.getString(11));
            }
            return credit;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return credit;
        }
    }
    public float course_credit(String academic_year,String semester,String course_code){
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+academic_year+"' AND semester='"+semester+"' AND course_code='"+course_code+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                credit+=Float.parseFloat(rs.getString(5));
            }
            return credit;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return credit;
        }
    }
    public void register_course(String academic_year, String semester, String course_code,String instructor_id){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            // String query = "INSERT INTO login_logout (email_id,status) VALUES('"+email_id+"','"+status+"')";
            String query = "INSERT INTO "+table_name+" (academic_year,semester,name,course_code,instructor_id) VALUES('"+academic_year+"','"+semester+"','"+name+"','"+course_code+"','"+instructor_id+"')";
            statement.executeUpdate(query);
            table_name=course_code+"_"+instructor_id.substring(0,instructor_id.indexOf("@"));
            query = "INSERT INTO "+table_name+" (email_id,name) VALUES ('"+email_id+"','"+name+"')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public float calc_credit_prev_two(String current_year,String current_semester){
        float total=0;
        int c_year=Integer.parseInt(current_year);
        int c_semester=Integer.parseInt(current_semester);
        //prev credits
        if(c_semester==2){
            int semester=1;
            total+=check_current_credits(current_year, Integer.toString(semester));
        }else{
            int semester=2;
            int year=c_year-1;
            total+=check_current_credits(Integer.toString(year), Integer.toString(semester));
        }
        //prev-prev credits
        int year=c_year-1;
        total+=check_current_credits(Integer.toString(year), current_semester);
        return total;
    }
    public boolean check_enrolled(String academic_year, String semester, String course_code){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" WHERE academic_year='"+academic_year+"' AND semester='"+semester+"' AND course_code='"+course_code+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if(rs.next())return true;
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public int check_current_semester(String current_year,String current_semester){
        int ans=1;
        int c_year=Integer.parseInt(current_year);
        int c_semester=Integer.parseInt(current_semester);
        int entry=Integer.parseInt(email_id.substring(0,4));
        ans = 2*(c_year-entry)+c_semester;
        return ans;
    }
    public boolean check_min_requirements(String current_year,String current_semester,String course_code,Integer semester){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+current_year+"' AND semester='"+current_semester+"' AND course_code='"+course_code+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                if(semester>=Integer.parseInt(rs.getString(10))){
                    //check branch elligibility
                    String input=rs.getString(9).substring(1,rs.getString(9).length()-1);
                    // System.out.println("input="+input);
                    String branch[]=input.split(",");
                    // System.out.println(branch);
                    String stu_branch=email_id.substring(4,7);
                    // System.out.println(stu_branch);
                    if(branch.length==0){
                        return true;
                    }else{
                        boolean flag=false;
                        for(int i=0;i<branch.length;++i){
                            if(branch[i].equals(stu_branch)){
                                flag=true;
                                break;
                            }
                        }
                        return flag;
                    }
                }else{
                    return false;
                }
            }else{
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;     
        }
    }
    public boolean check_core_elective(String current_year,String current_semester,String course_code){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+current_year+"' AND semester='"+current_semester+"' AND course_code='"+course_code+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                String input_branch=rs.getString(9).substring(1,rs.getString(9).length()-1);
                String input_ce=rs.getString(11).substring(1,rs.getString(11).length()-1);
                String branch[]=input_branch.split(",");
                String ce[]=input_ce.split(",");
                String stu_branch=email_id.substring(4,7);
                if(branch.length==0){
                    return true;
                }else{
                    boolean flag=false;
                    for(int i=0;i<branch.length;++i){
                        if(branch[i].equals(stu_branch)){
                            if(ce[i].equals("PC")){
                            }else{
                                flag=true;
                                break;
                            }     
                        }
                    }
                    return flag;
                }
            }else{
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return true;
        }
    }
    public void derigster_course(String current_year,String current_semester, String course_code,String instructor_id){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            // String query = "INSERT INTO login_logout (email_id,status) VALUES('"+email_id+"','"+status+"')";
            String query = "DELETE FROM "+table_name+" WHERE academic_year='"+current_year+"' AND semester='"+current_semester+"' AND course_code='"+course_code+"' AND instructor_id='"+instructor_id+"'";
            statement.executeUpdate(query);
            table_name=course_code+"_"+instructor_id.substring(0,instructor_id.indexOf("@"));
            query = "DELETE FROM "+table_name+" WHERE email_id='"+email_id+"'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void show_grade(String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" WHERE academic_year='"+academic_year+"' AND semester='"+semester+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("Course_code   Instructor_id   Grade");
            while(rs.next()){
                System.out.println(rs.getString(4)+ "    "+rs.getString(5)+"   "+rs.getString(6));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public boolean isgraduated(){
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".grade!='F'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            boolean checkcp301=false;
            boolean checkcp302=false;
            boolean checkcp303=false;
            while(rs.next()){
                credit+=Float.parseFloat(rs.getString(11));
                if(rs.getString(4).equals("cp301")){
                    checkcp301=true;
                }
                if(rs.getString(4).equals("cp302")){
                    checkcp302=true;
                }
                if(rs.getString(4).equals("cp303")){
                    checkcp303=true;
                }
            }
            if(credit>=145 && checkcp301==true && checkcp302==true && checkcp303==true){
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
    public boolean check_already_done(String course_code){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" WHERE course_code='"+course_code+"' AND grade IS NOT NULL AND grade!='F'";
            // System.out.println(query);
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
