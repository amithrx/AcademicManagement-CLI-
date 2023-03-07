package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class StudentTest8 {
    App app =new App();
    Connection conn=DBconnect.connect();

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2022,1,'Amit Kumar','cs201','parwinder@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void studentCheckPrerequisites1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkPrerequisites("cs304", "2023", "1"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2022 AND semester=1 AND course_code='cs201'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
