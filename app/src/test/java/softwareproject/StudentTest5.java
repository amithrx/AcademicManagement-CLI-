package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
public class StudentTest5 {
    App app =new App();
    Connection conn=DBconnect.connect();
    @Test
    void studentCheckRegister8(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkRegister("hs301", "amritesh@iitrpr.ac.in"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2020csb1070 WHERE academic_year=2023 AND semester=1 AND course_code='hs301'";
            statement.executeUpdate(query);
            query="DELETE FROM hs301_amritesh WHERE email_id='2020csb1070@iitrpr.ac.in'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
