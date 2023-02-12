package softwareproject;
import java.util.*;
import java.sql.*;

public class Common {
    public void log(int state,Connection conn,String email_id){
        try {
            Statement statement = conn.createStatement();
            String status = "logged_in";
            if(state==1){
                status="logged_out";
            }
            String query = "INSERT INTO login_logout (email_id,status) VALUES('"+email_id+"','"+status+"')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String[] current_session(Connection conn){
        String[] values=new String[2];
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM current_sessions";
            ResultSet rs = statement.executeQuery(query);

            if(rs.next()){
                values[0]=rs.getString(1);
                values[1]=rs.getString(2);
            }
            return values;
            // System.out.println(rs.getString(5));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return values;
        }
    }
    public String[] check_offered_or_not(String course_code,String instructor_id,Connection conn){
        String values[]=new String[2];
        values[0]="false";
        values[1]="0";
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM course_offerings WHERE course_code='"+course_code+"' AND instructor_id='"+instructor_id+"'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                values[0]="true";
                values[1]=rs.getString(3);
                // System.out.println("value"+values[1]);
                return values;
            }else{
                values[0]="false";
                return values;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return values;
        }
    }
}
