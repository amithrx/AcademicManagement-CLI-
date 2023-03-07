package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class AdminTest1 {
    App app =new App();
    Connection conn=DBconnect.connect();
   
    @Test
    void adminModifyCourseCatalog1(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.modifyCourseCatalog("cs103", "3", "2", "3", "2023", "1", "", "", "6", "", "0"));
    }
    @Test
    void adminModifyCourseCatalog2(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.modifyCourseCatalog("cs104", "3", "2", "3", "2023", "1", "cs101,cs201", "", "6", "", "0"));
    }
    @Test
    void adminModifyCourseCatalog3(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.modifyCourseCatalog("cs104", "3", "2", "3", "2023", "1", "cs101,cs201", "", "6", "", "1"));
    }
    @Test
    void adminModifyCourseCatalog4(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.modifyCourseCatalog("cs103", "3", "2", "3", "2023", "1", "", "", "6", "", "1"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM course_catalog WHERE academic_year=2023 AND semester=1 AND course_code='cs103'";
            statement.executeUpdate(query);
            query="DELETE FROM course_catalog WHERE academic_year=2023 AND semester=1 AND course_code='cs104'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
