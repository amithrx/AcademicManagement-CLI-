package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class AdminTest2 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @Test
    void adminSetGradeDeadline1(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setGradeDeadline("T", "F", "F"));
    }
    @Test
    void adminSetGradeDeadline2(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setGradeDeadline("F", "T", "F"));
    }
    @Test
    void adminSetGradeDeadline3(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setGradeDeadline("F", "F", "T"));
    }
    @Test
    void adminSetGradeDeadline4(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setGradeDeadline("F", "F", "F"));
    }
    @Test
    void adminSetRestDeadline1(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("4", "T", "T"));
    }
    @Test
    void adminSetRestDeadline2(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("1", "T", "F"));
    }
    @Test
    void adminSetRestDeadline3(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("1", "F", "T"));
    }
    @Test
    void adminSetRestDeadline4(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("1", "F", "F"));
    }
    @Test
    void adminSetRestDeadline5(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("2", "T", "F"));
    }
    @Test
    void adminSetRestDeadline6(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("2", "F", "T"));
    }
    @Test
    void adminSetRestDeadline7(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("2", "F", "F"));
    }
    @Test
    void adminSetRestDeadline8(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("3", "T", "F"));
    }
    @Test
    void adminSetRestDeadline9(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("3", "F", "T"));
    }
    @Test
    void adminSetRestDeadline10(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.setRestDeadline("3", "F", "F"));
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
