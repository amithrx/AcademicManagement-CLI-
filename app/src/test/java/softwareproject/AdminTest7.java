package softwareproject;

import org.checkerframework.checker.units.qual.min;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.Scanner;

public class AdminTest7 {
    App app =new App();
    Connection conn=DBconnect.connect();
    Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="UPDATE config SET course_catalog_start=true,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @ParameterizedTest
    @CsvSource({"1,1,cs103,3,0,0,'cs101','csb',6,'PE'","1,2,cs101,3,0,0,'','',6,''"})
    public void test2(String choice,String press,String course_code,String l,String t,String p,String prereq,String branch,String minmsem,String ce){
        boolean result;
        String input = choice +"\n"+press+"\n"+course_code+"\n"+l+"\n"+t+"\n"+p+"\n"+prereq+"\n"+branch+"\n"+minmsem+"\n"+ce+"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertTrue(result);
        scan.close();
        return;
    }
    @ParameterizedTest
    @CsvSource({"4,4,T,F,F"})
    public void test3(String choice,String press,String start,String end,String validate){
        boolean result;
        String input = choice +"\n"+press+"\n"+start+"\n"+end+"\n"+validate+"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertTrue(result);
        scan.close();
        return;
    }
    @ParameterizedTest
    @CsvSource({"4,1,T,F","4,2,T,F","4,3,T,F"})
    public void test4(String choice,String press,String start,String end){
        boolean result;
        String input = choice +"\n"+press+"\n"+start+"\n"+end+"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertTrue(result);
        scan.close();
        return;
    }
    @AfterEach
    public void tearDown2(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM course_catalog WHERE academic_year=2023 AND semester=1 AND course_code='cs103'";
            statement.executeUpdate(query);
            query="UPDATE course_catalog SET L=3,T=0,P=0 WHERE course_code='cs101'";
            query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=true,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
}
