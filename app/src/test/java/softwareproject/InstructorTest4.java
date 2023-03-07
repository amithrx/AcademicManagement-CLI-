package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class InstructorTest4 {
    App app =new App();
    Connection conn=DBconnect.connect();

    @Test
    void instructorAddPCCourse(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.addPCCourse("cs502", "2023", "1"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cs502'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1072 WHERE academic_year=2023 AND semester=1 AND course_code='cs502'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1098 WHERE academic_year=2023 AND semester=1 AND course_code='cs502'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1068 WHERE academic_year=2023 AND semester=1 AND course_code='cs502'";
            statement.executeUpdate(query);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
