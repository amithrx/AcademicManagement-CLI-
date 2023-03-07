package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class AdminTest3 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @Test
    void adminUpdateSessions(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.updateSession("2023", "1", "2023", "1"));
    }
    @Test
    void adminCheckUpdateCurrentSessions1(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.checkUpdateCurrentSession("2023", "2"));
    }
    @Test
    void adminCheckUpdateCurrentSessions3(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertFalse(a.checkUpdateCurrentSession("2023", "1"));
    }
    @Test
    void adminCheckUpdateCurrentSessions2(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.checkUpdateCurrentSession("2024", "2"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="UPDATE current_sessions SET academic_year='2023',semester='1'";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code)VALUES ('amritesh@iitrpr.ac.in','hs301')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints) VALUES ('sujata@iitrpr.ac.in','cs304',6)";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints) VALUES ('puneet@iitrpr.ac.in','cp301',6.5)";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code,cgpa_constraints) VALUES ('anil@iitrpr.ac.in','cs201',10)";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('sodhi@iitrpr.ac.in','cs305')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('anil@iitrpr.ac.in','cs306')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('nssoffice@iitrpr.ac.in','ns103')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('mukesh@iitrpr.ac.in','cp302')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('parwinder@iitrpr.ac.in','hs507')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('dhall@iitrpr.ac.in','cs518')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('manignandan@iitrpr.ac.in','cy111')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('sashi@iitrpr.ac.in','cs501')";
            statement.executeUpdate(query);
            query="INSERT INTO course_offerings (instructor_id,course_code) VALUES ('sashi@iitrpr.ac.in','cs111')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
