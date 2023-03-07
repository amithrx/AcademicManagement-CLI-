package softwareproject;
import java.util.*;
import java.sql.*;

public class Student extends Person{
    //constructor
    Student(String name,String email_id,Connection conn){
        this.name=name;
        this.email_id=email_id;
        this.conn=conn;    
    }
    //interface
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
    //checking if previous semester exists or not
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

    //checking if previous to previous semester exist or not
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
    
    //calculating cgpa
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

    //checking if prerequisites gets fulfilled or not
    public boolean checkPrerequisites(String course_code,String academic_year,String semester){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+academic_year+"' AND semester='"+semester+"' AND course_code='"+course_code+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                String input=rs.getString(8).substring(1,rs.getString(8).length()-1);
                int size=input.length();
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

    //checking for current number of registered credits
    public float checkCurrentCredits(String academic_year,String semester){
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "SELECT * FROM "+table_name+" JOIN course_catalog ON "+table_name+".academic_year=course_catalog.academic_year AND "+table_name+".semester=course_catalog.semester AND "+table_name+".course_code=course_catalog.course_code WHERE "+table_name+".academic_year='"+academic_year+"' AND "+table_name+".semester='"+semester+"'";
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

    //checking the course credits of a given course_code
    public float courseCredit(String academic_year,String semester,String course_code){
        float credit=0;
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_catalog WHERE academic_year='"+academic_year+"' AND semester='"+semester+"' AND course_code='"+course_code+"'";
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

    //registering a course
    public boolean registerCourse(String academic_year, String semester, String course_code,String instructor_id){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "INSERT INTO "+table_name+" (academic_year,semester,name,course_code,instructor_id) VALUES('"+academic_year+"','"+semester+"','"+name+"','"+course_code+"','"+instructor_id+"')";
            statement.executeUpdate(query);
            table_name=course_code+"_"+instructor_id.substring(0,instructor_id.indexOf("@"));
            query = "INSERT INTO "+table_name+" (email_id,name) VALUES ('"+email_id+"','"+name+"')";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //calculating earned credits of previous two semester
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

    //checking if user has already enrolled or not for a given course
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
            e.printStackTrace();
            return false;
        }
    }

    //checking currently how much semester user has covered
    public int checkCurrentSemester(String current_year,String current_semester){
        int ans=1;
        int c_year=Integer.parseInt(current_year);
        int c_semester=Integer.parseInt(current_semester);
        int entry=Integer.parseInt(email_id.substring(0,4));
        ans = 2*(c_year-entry)+c_semester;
        return ans;
    }

    //checking if user stisfying the minimum requirements or not
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

    //checking whether a given course is core or elective
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

    //derigstering the course
    public boolean deRegisterCourse(String current_year,String current_semester, String course_code,String instructor_id){
        try {
            Statement statement = conn.createStatement();
            String table_name="s_"+email_id.substring(0,11);
            String query = "DELETE FROM "+table_name+" WHERE academic_year='"+current_year+"' AND semester='"+current_semester+"' AND course_code='"+course_code+"' AND instructor_id='"+instructor_id+"'";
            statement.executeUpdate(query);
            table_name=course_code+"_"+instructor_id.substring(0,instructor_id.indexOf("@"));
            query = "DELETE FROM "+table_name+" WHERE email_id='"+email_id+"'";
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //graduation check whether a student is able to graduate or not
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
            boolean passingallcore=true;
            while(rs.next()){
                credit+=Float.parseFloat(rs.getString(11));
                System.out.println(rs.getString(4));
                if(rs.getString(4).equals("cp301")){
                    System.out.println("ne1");
                    checkcp301=true;
                }
                if(rs.getString(4).equals("cp302")){
                    System.out.println("ne2");
                    checkcp302=true;
                }
                if(rs.getString(4).equals("cp303")){
                    System.out.println("ne3");
                    checkcp303=true;
                }
            }

            //check for all the core-courses
            Statement statement1=conn.createStatement();
            String stu_branch=email_id.substring(4,7);
            String whether_ce="";
            String query1 = "SELECT * FROM "+table_name+" WHERE grade='F'";
            ResultSet rs1=statement1.executeQuery(query1);
            while(rs1.next()){
                Statement statement3=conn.createStatement();
                String query3="SELECT * FROM course_catalog WHERE course_code='"+rs1.getString(4)+"' AND academic_year='"+rs1.getString(1)+"' AND semester='"+rs1.getString(2)+"'";
                ResultSet rs3=statement3.executeQuery(query3);
                rs3.next();
                String input_branch=rs3.getString(9).substring(1,rs3.getString(9).length()-1);
                String branch[]=input_branch.split(",");
                String input_ce=rs3.getString(11).substring(1,rs3.getString(11).length()-1);
                String ce[]=input_ce.split(",");
                for(int i=0;i<ce.length;++i){
                    if(branch[i].equals(stu_branch)){
                        whether_ce=ce[i];
                        break;
                    }
                }
                if(whether_ce.equals("PC")){
                    //check if got passed or not
                    Statement statement4 = conn.createStatement();
                    String query4="SELECT * FROM "+table_name+" WHERE grade!='F' AND course_code='"+rs1.getString(4)+"'";
                    ResultSet rs4=statement4.executeQuery(query4);
                    if(!rs4.next()){
                        passingallcore=false;
                        break;
                    }
                }
            }  
            if(credit>=145 && checkcp301==true && checkcp302==true && checkcp303==true && passingallcore==true){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //checking whether student has already done a given course or not
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

    //checking whether user can able to register a course or not
    public boolean checkRegister(String course_code,String instructor_id){
        String[] result=current_session(conn);
        Integer current_semester=checkCurrentSemester(result[0],result[1]);
        if(checkAlreadyDone(course_code)){
            String check[]=checkOfferedOrNot(course_code,instructor_id,conn);
        if(check[0].equals("true")){
            if(checkMinRequirements(result[0],result[1],course_code,current_semester)){
                if(calcCGPA()>=Float.parseFloat(check[1])){
                    if(checkPrerequisites(course_code,result[0],result[1])){
                    boolean check_previous=checkPrevious(result[0], result[1]);
                    boolean check_back_previous=checkBackPrevious(result[0], result[1]);
                    float current_registered_credits=checkCurrentCredits(result[0],result[1]);
                    float course_credit=courseCredit(result[0],result[1],course_code);
                
                    if(check_back_previous==true && check_previous==true){
                        float earn_prev_two=(calcCreditOfPrevTwo(result[0],result[1]))/2;
                        if(course_credit+current_registered_credits<=1.25*earn_prev_two){
                            registerCourse(result[0],result[1],course_code,instructor_id);
                            System.out.println("Succesfully enrolled");
                            return true;
                        }else{
                            System.out.println("Credit limit exceeded");
                            return false;
                        }
                    }else{  
                        if(course_credit+current_registered_credits<=18){
                            registerCourse(result[0],result[1],course_code,instructor_id);
                            System.out.println("Succesfully enrolled");
                            return true;
                        }else{
                            System.out.println("Credit limit exceeded");
                            return false;
                        }                                 
                    }
                    }else{
                        System.out.println("Not fulfilling prerequisites");
                        return false;
                    } 
                }else{
                    System.out.println("Not fulfilling CGPA criteria");
                    return false;
                }
                
            }else{
                System.out.println("Either branch or your semester not elligible");
                return false;
            }

            }else{
                System.out.println("Course not offered");
                return false;
            }
        }else{
            System.out.println("Already enrolled");
            return false;
        } 
    }

    //cheking whether a student is able to derigster a course or not
    public boolean checkDeRegister(String course_code,String instructor_id){
        String[] result=current_session(conn);
        String check[]=checkOfferedOrNot(course_code,instructor_id,conn);
                    if(check[0].equals("true")){
                        if(checkEnrolled(result[0],result[1],course_code)){
                            //check for PC or PE
                            if(checkCoreElective(result[0],result[1],course_code)){
                                deRegisterCourse(result[0],result[1],course_code,instructor_id);
                                System.out.println("Successfully un-enrolled");
                                return true;
                            }else{
                                System.out.println("You can't unenroll PC coures");
                                return false;
                            }
                        }else{
                            System.out.println("Not enrolled in that course");
                            return false;
                        }   
                    }else{
                        System.out.println("Course not offered");
                        return false;
                    }
    }

    //interface for the option available to the student
    public boolean studentOption(Scanner scnS){
        log(0,conn,email_id);
        System.out.println("Welcome "+name);
        boolean result=false;
        while(true){
            // Scanner scnS = new Scanner(System.in);
            String inputS=display(scnS);
            System.out.println(inputS);
            if(inputS.equals("1")){
                //Registering a course
                if(checkElligibility("5",conn)==true){
                    System.out.println("Enter course_code");
                    String course_code = scnS.nextLine();
                    System.out.println(course_code);
                    System.out.println("Enter instructor_id");
                    String instructor_id = scnS.nextLine();
                    System.out.println(instructor_id);
                    result=checkRegister(course_code, instructor_id);
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
                    result=checkDeRegister(course_code, instructor_id);
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
                result=viewGrade(email_id,academic_year,semester);
            }else if(inputS.equals("4")){
                // calculating CGPA
                Float cgpa=calcCGPA();
                System.out.println("Your cgpa is: "+cgpa);
                result=true;
            }else if(inputS.equals("5")){
                // checking whether graduated or not
                if(isGraduated()){
                    System.out.println("Elligible for graduation");
                    result=true;
                }else{
                    System.out.println("Not elligible for graduation");
                }  
            }else if(inputS.equals("6")){
                // logout
                log(1,conn,email_id);
                // result=true;
                break;

            }else{ 
                System.out.println("Wrong input"); 
            }
        } 
        return result;
    }
}
