package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
public class StudentTest2 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @Test
    void studentRegisterCourse(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.registerCourse("2023", "1", "cp302", "mukesh@iitrpr.ac.in"));
    }
    @AfterEach
    public void tearDown2(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cp302'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
