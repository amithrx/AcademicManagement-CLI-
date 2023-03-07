package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.Scanner;

public class AdminTest6 {
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
    @CsvSource({"1,5","4,8"})
    public void test1(String choice,String press){
        boolean result;
        String input = choice +"\n"+press+"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertFalse(result);
        scan.close();
        return;
    } 
    @ParameterizedTest
    @CsvSource({"1,1,cs305","1,2,cy121"})
    public void test2(String choice,String press,String course_code){
        boolean result;
        String input = choice +"\n"+press+"\n"+course_code+"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertFalse(result);
        scan.close();
        return;
    } 
    @ParameterizedTest
    @CsvSource({"2,2020csb1198@iitrpr.ac.in,1","3,2020csb1070@iitrpr.ac.in,2"})
    public void test3(String choice,String email,Integer expect){
        boolean result;
        String input = choice +"\n"+email+"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        if(expect==1)
        assertFalse(result);
        else
        assertTrue(result);
        scan.close();
        return;
    } 
    @ParameterizedTest
    @CsvSource({"2,2020csb1070@iitrpr.ac.in,2022,2"})
    public void test4(String choice,String email,String acad,String sem){
        boolean result;
        String input = choice +"\n"+email+"\n"+acad+"\n"+sem+"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertTrue(result);
        scan.close();
        return;
    } 
}
