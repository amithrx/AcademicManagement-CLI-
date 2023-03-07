package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class InstructorTest1 {
    App app =new App();
    Connection conn=DBconnect.connect();

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','cs305','sodhi@iitrpr.ac.in')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1072(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Ankit Sharma','cs305','sodhi@iitrpr.ac.in')";
            statement.executeUpdate(query);
            query="INSERT INTO cs305_sodhi(email_id,name) VALUES('2020csb1070@iitrpr.ac.in','Amit Kumar')";
            statement.executeUpdate(query);
            query="INSERT INTO cs305_sodhi(email_id,name) VALUES('2020csb1072@iitrpr.ac.in','Ankit Sharma')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void instructorUpdateGrade(){
        Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);
        assertTrue(i.updateGrade("cs305"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='cs305'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1072 WHERE academic_year=2023 AND semester=1 AND course_code='cs305'";
            statement.executeUpdate(query);
            query="DELETE FROM cs305_sodhi WHERE email_id='2020csb1070@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM cs305_sodhi WHERE email_id='2020csb1072@iitrpr.ac.in'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
