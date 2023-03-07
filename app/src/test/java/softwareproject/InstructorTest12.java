package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.Scanner;

public class InstructorTest12 {
    App app =new App();
    Connection conn=DBconnect.connect();
    Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=true";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @ParameterizedTest
    @CsvSource({"5,cs103"})
    public void test2(String choice,String course_code){
        boolean result;
        String input = choice + "\n" +course_code+"\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=i.instructorOption(scan);
        assertFalse(result);
        scan.close();
        return;
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=true,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
