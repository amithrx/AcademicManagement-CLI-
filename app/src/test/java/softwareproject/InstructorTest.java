package softwareproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class InstructorTest {
    App app =new App();
    Connection conn=DBconnect.connect();
    
    @Test
    void instructorIsPresentInCatalog1(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.isPresentInCatalog("2023", "1", "cs305"));
    }
    @Test
    void instructorIsPresentInCatalog2(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertFalse(i.isPresentInCatalog("2023", "1", "cs618"));
    }
    @Test
    void instructorValidateCourse(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.validateCourse("cs305"));
    }
    @Test
    void instructorCheckViewGrade1(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertFalse(i.checkViewGrade("cs301"));
    }
}
