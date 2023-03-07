package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class StudentTest12 {
    App app =new App();
    Connection conn=DBconnect.connect();

    @BeforeEach
    public void setUp(){
        try {
            Statement statement=conn.createStatement();
            String query="INSERT INTO s_2023csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Arshdeep Singh','cs502','xyz@iitrpr.ac.in','F')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2023csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Arshdeep Singh','cp301','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2023csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Arshdeep Singh','cp302','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2023csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Arshdeep Singh','cp303','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2023csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Arshdeep Singh','cs101','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2023csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Arshdeep Singh','cs501','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2023csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Arshdeep Singh','hs301','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cs502','xyz@iitrpr.ac.in','F')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cp301','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cp302','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cp303','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cs101','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','cs501','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
            query="INSERT INTO s_2020csb1070(academic_year,semester,name,course_code,instructor_id,grade) VALUES(2023,1,'Amit Kumar','hs301','xyz@iitrpr.ac.in','A')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void studentCheckRegister8(){
        Student stu=new Student("Asrshdeep Singh", "2023csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cs306", "anil@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister9(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cs306", "anil@iitrpr.ac.in"));
    }
    @AfterEach
    public void tearDown(){
        try {
            Statement statement=conn.createStatement();
            String query="DELETE FROM s_2023csb1070 WHERE course_code='hs301' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2023csb1070 WHERE course_code='cp301' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2023csb1070 WHERE course_code='cp302' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2023csb1070 WHERE course_code='cp303' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2023csb1070 WHERE course_code='cs501' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2023csb1070 WHERE course_code='cs101' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2023csb1070 WHERE course_code='cs502' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='hs301' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='cp301' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='cp302' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='cp303' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='cs501' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='cs101' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
            query="DELETE FROM s_2020csb1070 WHERE course_code='cs502' AND instructor_id='xyz@iitrpr.ac.in'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
