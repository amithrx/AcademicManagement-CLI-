package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
public class StudentTest4 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id) VALUES(2023,1,'Amit Kumar','hs507','parwinder@iitrpr.ac.in')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void studentCheckDeRegister5(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkDeRegister("hs507", "parwinder@iitrpr.ac.in"));
    }
    
}
