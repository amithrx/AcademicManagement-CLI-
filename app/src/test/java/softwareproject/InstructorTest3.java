package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class InstructorTest3 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO course_offerings(instructor_id,course_code) VALUES ('sodhi@iitrpr.ac.in','cs502')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void instructorRemoveSymbol(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.removeSymbol("cs502"));
    }
}
