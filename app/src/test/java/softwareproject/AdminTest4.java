package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class AdminTest4 {
    App app =new App();
    Connection conn=DBconnect.connect();

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','cs305','sodhi@iitrpr.ac.in')";
            statement.executeUpdate(query);
            query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=false,course_register_end=false,grade_start=false,grade_end=true,validation_check_end=false";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void adminValidate(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.validate("2023", "1"));
    }
    @Test
    void adminCheckValidate(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.checkValidate());
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cs305'";
            statement.executeUpdate(query);
            query="DELETE FROM report_validator WHERE course_code='cs305' AND instructor_id='sodhi@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="UPDATE config SET course_catalog_start=false,course_catalog_end=false,course_float_start=false,course_float_end=false,course_register_start=true,course_register_end=false,grade_start=false,grade_end=false,validation_check_end=false";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
