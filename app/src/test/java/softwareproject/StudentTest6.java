package softwareproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.Scanner;

public class StudentTest6 {
    App app =new App();
    Connection conn=DBconnect.connect();
    Student stu=new Student("Amit Kumar", "2020csb1070@iitrpr.ac.in", conn);

    @ParameterizedTest
    @CsvSource({"1,hs507,parwinder@iitrpr.ac.in","2,hs507,parwinder@iitrpr.ac.in","3,2022,2"})
    public void test1(String choice,String course_code,String instructor_id){
        boolean result;
        String input = choice + "\n" + course_code + "\n"+instructor_id+"\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=stu.studentOption(scan);
        assertTrue(result);
        scan.close();
        return;
    }
    @ParameterizedTest
    @CsvSource({"4,1","5,2"})
    public void test2(String choice1,Integer expect){
        boolean result;
        String input = choice1 +"\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=stu.studentOption(scan);
        if(expect==1)
        assertTrue(result);
        else
        assertFalse(result); 
        scan.close();
        return;
    }
    @ParameterizedTest
    @CsvSource({"6"})
    public void test3(String choice1){
        boolean result;
        String input = choice1 +"\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=stu.studentOption(scan);
        assertFalse(result);
        scan.close();
        return;
    }
    @ParameterizedTest
    @CsvSource({"7"})
    public void test7(String choice1){
        boolean result;
        String input = choice1 +"\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=stu.studentOption(scan);
        assertFalse(result);
        scan.close();
        return;
    }
}
