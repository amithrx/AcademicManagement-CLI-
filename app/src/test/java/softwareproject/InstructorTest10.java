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

public class InstructorTest10 {
    App app =new App();
    Connection conn=DBconnect.connect();
    Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=true,grade_end=false,validation_check_end=false";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings(instructor_id,course_code) VALUES ('sodhi@iitrpr.ac.in','cs305')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','cs305','sodhi@iitrpr.ac.in')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1072(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Ankit Sharma','cs305','sodhi@iitrpr.ac.in')";
            statement.executeUpdate(query);
            query="INSERT INTO cs305_sodhi(email_id,name,grade) VALUES('2020csb1070@iitrpr.ac.in','Amit Kumar')";
            statement.executeUpdate(query);
            query="INSERT INTO cs305_sodhi(email_id,name,grade) VALUES('2020csb1072@iitrpr.ac.in','Ankit Sharma')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @ParameterizedTest
    @CsvSource({"2,cs305"})
    public void test1(String choice,String course_code){
        boolean result;
        String input = choice + "\n" +course_code+"\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=i.instructorOption(scan);
        assertTrue(result);
        scan.close();
        return;
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cs305'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1072 WHERE academic_year=2023 AND semester=1 AND course_code='cs305'";
            statement.executeUpdate(query);
            query="DELETE FROM cs305_sodhi WHERE email_id='2020csb1070@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM cs305_sodhi WHERE email_id='2020csb1072@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM course_offerings WHERE instructor_id='sodhi@iitrpr.ac.in' AND course_code='cs305'";
            statement.executeUpdate(query);
            query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=true,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
