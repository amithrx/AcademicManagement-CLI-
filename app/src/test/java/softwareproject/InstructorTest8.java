package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class InstructorTest8 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO course_offerings(instructor_id,course_code) VALUES ('sodhi@iitrpr.ac.in','cs305')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void instructorCheckValidate1(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertFalse(i.checkValidate("cs301"));
    }
    @Test
    void instructorCheckValidate2(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.checkValidate("cs305"));
    }
    @Test
    void instructorCheckViewGrade2(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.checkViewGrade("cs305"));
    }
    @Test
    void instructorShowCourseRecord(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.showCourseRecord("cs305"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM course_offerings WHERE instructor_id='sodhi@iitrpr.ac.in' AND course_code='cs305'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
