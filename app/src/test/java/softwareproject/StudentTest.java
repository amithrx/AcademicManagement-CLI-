package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class StudentTest {
    App app =new App();
    Connection conn=DBconnect.connect();
    @Test
    void studentCheckPrevious1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkPrevious("2023", "1"));
    }
    @Test
    void studentCheckPrevious2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkPrevious("2021", "2"));
    }
    @Test
    void studentCheckBackPrevious1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkBackPrevious("2023", "1"));
    }
    @Test
    void studentCheckBackPrevious2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkBackPrevious("2022", "1"));
    }
    //check
    @Test
    void studentCalcCGPA1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertEquals(7.239999771118164,stu.calcCGPA());
    }
    @Test
    void studentCalcCGPA2(){
        Student stu=new Student("Mohit Kumar", "2020csb1098@iitrpr.ac.in", conn);
        assertEquals(0,stu.calcCGPA());
    }
    @Test
    void studentCheckPrerequisites1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkPrerequisites("hs507", "2023", "1"));
    }
    @Test
    void studentCheckPrerequisites2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkPrerequisites("cs306", "2023", "1"));
    }
    @Test
    void studentCheckPrerequisites3(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkPrerequisites("cs532", "2023", "1"));
    }
    @Test
    void studentCourseCredit(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertEquals(4, stu.courseCredit("2023", "1", "cs305"));
    }
    @Test
    void studentCalculateCreditOfPreviousTwo1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertEquals(25, stu.calcCreditOfPrevTwo("2023", "1"));
    }
    @Test
    void studentCalculateCreditOfPreviousTwo2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertEquals(16, stu.calcCreditOfPrevTwo("2023", "2"));
    }
    @Test
    void studentCheckEnrolled1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkEnrolled("2023", "1", "cp301"));
    }
    @Test
    void studentMinReqiurements1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkMinRequirements("2023", "1", "cs305", 7));
    }
    @Test
    void studentMinReqiurements2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkMinRequirements("2023", "1", "cp302", 7));
    }
    @Test
    void studentMinReqiurements3(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkMinRequirements("2023", "1", "cp302", 1));
    }
    @Test
    void studentMinReqiurements4(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkMinRequirements("2023", "1", "cp402", 7));
    }
    @Test
    void studentCheckCoreElcective1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkCoreElective("2023", "1", "cp302"));
    }
    @Test
    void studentCheckCoreElcective2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkCoreElective("2023", "1", "cs308"));
    }
    @Test
    void studentCheckCoreElcective3(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkCoreElective("2023", "1", "cs305"));
    }
    @Test
    void studentCheckCoreElcective4(){
        Student stu=new Student("Amit Kumar", "2020mcb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkCoreElective("2023", "1", "cs502"));
    }
    @Test
    void studentCheckAlreadyDone1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.checkAlreadyDone("cs306"));
    }
    @Test
    void studentCheckAlreadyDone2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkAlreadyDone("ma202"));
    }
    @Test
    void studentCheckRegister1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("ma202", "arun@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cs518", "dhall@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister3(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("hs303", "amritesh@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister4(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cs517", "dhall@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister5(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cy111", "manignandan@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister6(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cs101", "anil@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister7(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cs306", "anil@iitrpr.ac.in"));
    }
    @Test
    void studentCheckRegister8(){
        Student stu=new Student("Mohit Kumar", "2020csb1098@iitrpr.ac.in", conn);
        assertFalse(stu.checkRegister("cs304", "sujata@iitrpr.ac.in"));
    }
    @Test
    void studentCheckDeRegister1(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkDeRegister("cs523", "dhall@iitrpr.ac.in"));
    }
    @Test
    void studentCheckDeRegister2(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkDeRegister("cs501", "sashi@iitrpr.ac.in"));
    }
    @Test
    void studentCheckDeRegister3(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkDeRegister("cs111", "sashi@iitrpr.ac.in"));
    }
    @Test
    void studentCheckDeRegister4(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertFalse(stu.checkDeRegister("cp302", "mukesh@iitrpr.ac.in"));
    }
    @Test
    void studentViewGrade(){
        Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);
        assertTrue(stu.viewGrade("2020csb1070@iitrpr.ac.in", "2022", "1"));
    }
}
