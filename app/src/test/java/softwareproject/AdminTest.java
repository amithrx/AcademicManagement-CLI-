package softwareproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
public class AdminTest {
    App app =new App();
    Connection conn=DBconnect.connect();

    @Test
    void adminCheckCatalogOffered1(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.checkCatalogOffered("cs103", "2023", "1"));
    }
    @Test
    void adminCheckCatalogOffered2(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertFalse(a.checkCatalogOffered("cs201", "2023", "1"));
    }
    @Test
    void adminCheckStudentEmail1(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.checkStudentEmail("2020csb1070@iitrpr.ac.in"));
    }
    @Test
    void adminCheckStudentEmail2(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertFalse(a.checkStudentEmail("2020csb1102@iitrpr.ac.in"));
    }
    @Test
    void adminGenerateTranscripts(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.checkStudentEmail("2020csb1070@iitrpr.ac.in"));
    }
    @Test
    void adminCheckGenerateTranscripts1(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertFalse(a.checkGenerateTranscript("2020csb1036@iitrpr.ac.in"));
    }
    @Test
    void adminCheckGenerateTranscripts2(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertTrue(a.checkGenerateTranscript("2020csb1070@iitrpr.ac.in"));
    }
    @Test
    void adminCheckValidate(){
        Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);
        assertFalse(a.checkValidate());
    }
}
