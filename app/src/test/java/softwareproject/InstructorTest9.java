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

public class InstructorTest9 {
    App app =new App();
    Connection conn=DBconnect.connect();
    Instructor i=new Instructor("Balwinder Sodhi", "sodhi@iitrpr.ac.in", conn);

    @ParameterizedTest
    @CsvSource({"1,cs101"})
    public void test1(String choice,String course_code){
        boolean result;
        String input = choice + "\n" + course_code + "\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=i.instructorOption(scan);
        assertFalse(result);
        scan.close();
        return;
    }
    @ParameterizedTest
    @CsvSource({"2"})
    public void test2(String choice){
        boolean result;
        String input = choice + "\n" +"\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=i.instructorOption(scan);
        assertFalse(result);
        scan.close();
        return;
    }
    @ParameterizedTest
    @CsvSource({"3","4","5","8","6"})
    public void test3(String choice){
        boolean result;
        String input = choice + "\n" +"\n6\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=i.instructorOption(scan);
        assertFalse(result);
        scan.close();
        return;
    }
}
