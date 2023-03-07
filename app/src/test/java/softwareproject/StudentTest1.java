package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class StudentTest1 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','hs507','parwinder@iitrpr.ac.in')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','cp301','puneet@iitrpr.ac.in')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','cs111','sashi@iitrpr.ac.in')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void studentCheckCurrentCredits(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertEquals(9, stu.checkCurrentCredits("2023", "1"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='hs507'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cp301'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cs111'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
