package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
public class StudentTest10 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO course_offerings(instructor_id,course_code) VALUES('abc@iitrpr.ac.in','cs305')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','cs305','abc@iitrpr.ac.in')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void studentCheckDeRegister6(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkDeRegister("cs305", "abc@iitrpr.ac.in"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM course_offerings WHERE course_code='cs305' AND instructor_id='abc@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='cs305' AND instructor_id='abc@iitrpr.ac.in'";
            statement.executeUpdate(query);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
