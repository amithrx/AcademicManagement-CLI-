package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class InstructorTest5 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @Test
    void instructorRemovePCCourse(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.removePCCourse("cs502", "2023", "1"));
    }
}
