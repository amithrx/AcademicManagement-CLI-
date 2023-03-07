package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class StudentTest7 {
    App app =new App();
    Connection conn=DBconnect.connect();

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cs502','xyz@iitrpr.ac.in','F')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cp301','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cp302','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cp303','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void studentIsGraduated(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.isGraduated());
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cp301'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cp302'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cp303'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cs502'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
