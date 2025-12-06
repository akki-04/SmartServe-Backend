package banking.src;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class user {
    private Connection con;
    private Scanner sc;

    public user(Connection con, Scanner sc){
        this.con = con;
        this.sc = sc;
    }

    public void register(){
        sc.nextLine();
        System.out.print("Enter your full name : ");
        String name = sc.nextLine();
        System.out.print("Enter your E-mail Id : ");
        String email = sc.nextLine();
        System.out.print("Enter Password : ");
        String pass = sc.nextLine();
        if(user_exist(email)){
            System.out.println("User already exist....! ");
            return;
        }
        
        String query = "Insert into user (name,email,password) value(?,?,?)";
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, name);
            p.setString(2, email);
            p.setString(3, pass);
            int affect = p.executeUpdate();
            if(affect>0){
                System.out.println("Register Successfully.....:)");
            }else{
                System.out.println("Not Register Try Again.....:(");
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
        return ;
    }

    public boolean user_exist(String email){
        String query =  "select * from user where email = ?";
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1,email);
            ResultSet res = p.executeQuery();
            if(res.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    public String Login(){
        sc.nextLine();
        System.out.print("Email : ");
        String email = sc.nextLine();
        System.out.print("pass : ");
        String pass = sc.nextLine();
        String query = "select * from user where email = ? AND password = ? ";
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1, email);
            p.setString(2,pass);
            ResultSet res = p.executeQuery();
            if(res.next()){
                return email;
            }else{
                return null;
            }
        }
        catch(SQLException s){
            s.printStackTrace();
        }
        return null;
    }
}
