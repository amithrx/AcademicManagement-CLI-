package softwareproject;
import java.util.*;
import java.sql.*;

public class Student extends Person{
    Student(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }
    public String display(Scanner scn){
        System.out.println("Press 1. for registering a course");
        System.out.println("Press 2. for de-registering a course");
        System.out.println("Press 3. for viewing grade");
        System.out.println("Press 4. for computing CGPA");
        System.out.println("Press 5. for checking whether graduated or not");
        System.out.println("Press 6. for logout");
        String input = scn.nextLine();
        return input;
    }
    public boolean checkPrevious(String current_year,String current_semester){
        int year=Integer.parseInt(current_year);
        int semester=Integer.parseInt(current_semester);
        try {
            Statement statement = conn.createStatement();
            // only 2020csb1070@iitrpr.ac.in format email is allowed
            String table_name="s_"+email_id.substring(0,11);
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
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkBackPrevious(String current_year,String current_semester){
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
            e.printStackTrace();
            return false;
        }
    }
    
    public float calcCGPA(){
        float cgpa=0;
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".grade!='F'";
            ResultSet rs = statement.executeQuery(query);
    
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
            if(credit==0)return 0;
            else
            return cgpa/credit;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkPrerequisites(String course_code,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+academic_year+"' AND semester='"+semester+"' AND course_code='"+course_code+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                String input=rs.getString(8).substring(1,rs.getString(8).length()-1);
                int size=input.length();
                // System.out.println(rs.getString(8));
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
            e.printStackTrace();
            return false;
        }
    }
    public float checkCurrentCredits(String academic_year,String semester){
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".academic_year='"+academic_year+"' AND "+table_name+".semester='"+semester+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                credit+=Float.parseFloat(rs.getString(11));
            }
            return credit;
        } catch (SQLException e) {
            e.printStackTrace();
            return credit;
        }
    }

    public float courseCredit(String academic_year,String semester,String course_code){
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
            e.printStackTrace();
            return credit;
        }
    }
// TODO: testcases for this function 
    public void registerCourse(String academic_year, String semester, String course_code,String instructor_id){
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
            e.printStackTrace();
        }
    }

    public float calcCreditOfPrevTwo(String current_year,String current_semester){
        float total=0;
        int c_year=Integer.parseInt(current_year);
        int c_semester=Integer.parseInt(current_semester);
        //prev credits
        if(c_semester==2){
            int semester=1;
            total+=checkCurrentCredits(current_year, Integer.toString(semester));
            // System.out.println(total);
        }else{
            int semester=2;
            int year=c_year-1;
            total+=checkCurrentCredits(Integer.toString(year), Integer.toString(semester));
            // System.out.println(total);
        }
        //prev-prev credits
        int year=c_year-1;
        total+=checkCurrentCredits(Integer.toString(year), current_semester);
        return total;
    }

    public boolean checkEnrolled(String academic_year, String semester, String course_code){
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

    public int checkCurrentSemester(String current_year,String current_semester){
        int ans=1;
        int c_year=Integer.parseInt(current_year);
        int c_semester=Integer.parseInt(current_semester);
        int entry=Integer.parseInt(email_id.substring(0,4));
        ans = 2*(c_year-entry)+c_semester;
        return ans;
    }

    public boolean checkMinRequirements(String current_year,String current_semester,String course_code,Integer semester){
        //checking for minm semester elligibility and that branch haas been offered or not
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+current_year+"' AND semester='"+current_semester+"' AND course_code='"+course_code+"'";
            // System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                if(semester>=Integer.parseInt(rs.getString(10))){
                    //check branch elligibility
                    // System.out.println("semester_elligible");
                    String input=rs.getString(9).substring(1,rs.getString(9).length()-1);
                    String branch[]=input.split(",");
                    String stu_branch=email_id.substring(4,7);
                    if(branch.length==1){
                        // System.out.println("branchlength1");
                        return true;
                    }else{
                        // System.out.println("branchlengthmore");
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
            e.printStackTrace();
            return false;     
        }
    }

    public boolean checkCoreElective(String current_year,String current_semester,String course_code){
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+current_year+"' AND semester='"+current_semester+"' AND course_code='"+course_code+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                String input_branch=rs.getString(9).substring(1,rs.getString(9).length()-1);
                String input_ce=rs.getString(11).substring(1,rs.getString(11).length()-1);
                String branch[]=input_branch.split(",");
                String ce[]=input_ce.split(",");
                String stu_branch=email_id.substring(4,7);
                if(branch.length==1){
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
            e.printStackTrace();
            return true;
        }
    }
// TODO: Needed to be tested
    public void deRegisterCourse(String current_year,String current_semester, String course_code,String instructor_id){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "DELETE FROM "+table_name+" WHERE academic_year='"+current_year+"' AND semester='"+current_semester+"' AND course_code='"+course_code+"' AND instructor_id='"+instructor_id+"'";
            statement.executeUpdate(query);
            table_name=course_code+"_"+instructor_id.substring(0,instructor_id.indexOf("@"));
            query = "DELETE FROM "+table_name+" WHERE email_id='"+email_id+"'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isGraduated(){
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".grade!='F'";
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
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkAlreadyDone(String course_code){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" WHERE course_code='"+course_code+"' AND grade IS NOT NULL AND grade!='F'";
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
// TODO:Needed to be tested
    public void studentOption(){
        log(0,conn,email_id);
        String[] result=current_session(conn);
        Integer current_semester=checkCurrentSemester(result[0],result[1]);
        System.out.println("Welcome "+name);
        while(true){
            Scanner scnS = new Scanner(System.in);
            String inputS=display(scnS);
            if(inputS.equals("1")){
                //Registering a course
                if(checkElligibility("5",conn)==true){
                    System.out.println("Enter course_code");
                    String course_code = scnS.nextLine();
                    System.out.println("Enter instructor_id");
                    String instructor_id = scnS.nextLine();
                    if(checkAlreadyDone(course_code)){
                        String check[]=checkOfferedOrNot(course_code,instructor_id,conn);
                    if(check[0].equals("true")){
                        if(checkMinRequirements(result[0],result[1],course_code,current_semester)){
                            if(calcCGPA()>=Float.parseFloat(check[1])){
                                if(checkPrerequisites(course_code,result[0],result[1])){
                                boolean check_previous=checkPrevious(result[0], result[1]);
                                boolean check_back_previous=checkBackPrevious(result[0], result[1]);
                                float current_registered_credits=checkCurrentCredits(result[0],result[1]);
                                // System.out.println("current"+current_registered_credits);
                                float course_credit=courseCredit(result[0],result[1],course_code);
                                // System.out.println(check_back_previous);
                                // System.out.println(check_previous);
                                if(check_back_previous==true && check_previous==true){
                                    float earn_prev_two=(calcCreditOfPrevTwo(result[0],result[1]))/2;
                                    System.out.println(calcCreditOfPrevTwo(result[0],result[1]));
                                    if(course_credit+current_registered_credits<=1.25*earn_prev_two){
                                        registerCourse(result[0],result[1],course_code,instructor_id);
                                        System.out.println("Succesfully enrolled");
                                    }else{
                                        System.out.println("Credit limit exceeded");
                                    }
                                }else{  
                                    if(course_credit+current_registered_credits<=18){
                                        registerCourse(result[0],result[1],course_code,instructor_id);
                                        System.out.println("Succesfully enrolled");
                                    }else{
                                        System.out.println("Credit limit exceeded");
                                    }                                 
                                }
                                }else{
                                    System.out.println("Not fulfilling prerequisites");
                                } 
                            }else{
                                System.out.println("Not fulfilling CGPA criteria");
                            }
                            
                        }else{
                            System.out.println("Either branch or your semester not elligible");
                        }

                        }else{
                            System.out.println("Course not offered");
                        }
                    }else{
                        System.out.println("Already enrolled");
                    }  
                }else{
                    System.out.println("Not elligible for enrolling");
                }

            }else if(inputS.equals("2")){
                //Deregistering a course
                if(checkElligibility("5",conn)==true){
                    System.out.println("Enter course_code");
                    String course_code = scnS.nextLine();
                    System.out.println("Enter instructor_id");
                    String instructor_id = scnS.nextLine();
                    String check[]=checkOfferedOrNot(course_code,instructor_id,conn);
                    if(check[0].equals("true")){
                        if(checkEnrolled(result[0],result[1],course_code)){
                            //check for PC or PE
                            if(checkCoreElective(result[0],result[1],course_code)){
                                deRegisterCourse(result[0],result[1],course_code,instructor_id);
                                System.out.println("Successfully un-enrolled");
                            }else{
                                System.out.println("You can't unenroll PC coures");
                            }
                        }else{
                            System.out.println("Not enrolled in that course");
                        }   
                    }else{
                        System.out.println("Course not offered");
                    }
                }else{
                    System.out.println("Not elligible for de-registering a course");
                }      
            }else if(inputS.equals("3")){
                // viewing grade
                System.out.println("Enter Academic year");
                String academic_year = scnS.nextLine();
                System.out.println("Enter semester");
                String semester = scnS.nextLine();
                System.out.println("Your grade");
                viewGrade(email_id,academic_year,semester);
            }else if(inputS.equals("4")){
                // calculating CGPA
                Float cgpa=calcCGPA();
                System.out.println("Your cgpa is: "+cgpa);

            }else if(inputS.equals("5")){
                // checking whether graduated or not
                if(isGraduated()){
                    System.out.println("Elligible for graduation");
                }else{
                    System.out.println("Not elligible for graduation");
                }  
            }else if(inputS.equals("6")){
                // logout
                log(1,conn,email_id);
                break;

            }else{ 
                System.out.println("Wrong input"); 
            }
        } 
    }
}
