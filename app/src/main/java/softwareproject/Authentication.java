package softwareproject;

import java.sql.*;

public class Authentication {
    String email_id;
    String password;
    Connection conn;
    Authentication(String email_id,String password,Connection conn){
        this.email_id=email_id;
        this.password=password;
        this.conn=conn;    
    }
    public String[] validate(){
        String values[]=new String[2]; //name,role
        values[0]="";
        values[1]="";
        try (Statement statement = conn.createStatement()) {
            String query = "SELECT * FROM users WHERE email_id='"+email_id+"' AND password='"+password+"' ";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                values[0]=rs.getString(2);
                values[1]=rs.getString(4);
                return values;
            }else{
                return values;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return values;
        }
    }
}
