package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class InstructorTest2 {
    App app =new App();
    Connection conn=DBconnect.connect();

    @Test
    void instructorAddCourse(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.addCourse("cs502", "0"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM course_offerings WHERE instructor_id='sodhi@iitrpr.ac.in' AND course_code='cs502'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
