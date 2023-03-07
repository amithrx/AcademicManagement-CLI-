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

public class AdminTest8 {
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
    @CsvSource({"5,2023,2"})
    public void test3(String choice,String year,String sem){
        boolean result;
        String input = choice +"\n"+year+"\n"+sem+"\n"+"\n7\n";
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
            String query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=true,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
            statement.executeUpdate(query);
            query="UPDATE current_sessions SET academic_year='2023',semester='1'";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code)VALUES ('amritesh@iitrpr.ac.in','hs301')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints) VALUES ('sujata@iitrpr.ac.in','cs304',6)";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints) VALUES ('puneet@iitrpr.ac.in','cp301',6.5)";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints) VALUES ('anil@iitrpr.ac.in','cs201',10)";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('sodhi@iitrpr.ac.in','cs305')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('anil@iitrpr.ac.in','cs306')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('nssoffice@iitrpr.ac.in','ns103')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('mukesh@iitrpr.ac.in','cp302')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('parwinder@iitrpr.ac.in','hs507')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('dhall@iitrpr.ac.in','cs518')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('manignandan@iitrpr.ac.in','cy111')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('sashi@iitrpr.ac.in','cs501')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('sashi@iitrpr.ac.in','cs111')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
}
