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

public class AdminTest5 {
    App app =new App();
    Connection conn=DBconnect.connect();
    Admin a=new Admin("admin", "admin@iitrpr.ac.in", conn);

    @ParameterizedTest
    @CsvSource({"1","6"})
    public void test1(String choice){
        boolean result;
        String input = choice +"\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertFalse(result);
        scan.close();
        return;
    } 
    @ParameterizedTest
    @CsvSource({"7"})
    public void test2(String choice){
        boolean result;
        String input = choice;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scan = new Scanner(System.in);
        result=a.adminOption(scan);
        assertFalse(result);
        scan.close();
        return;
    } 
}
