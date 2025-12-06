package banking.src;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.lang.String;

public class ac {
    private Connection con;
    private Scanner sc;

    public ac(Connection con, Scanner sc){
        this.con = con;
        this.sc = sc;
    }

    public long open_ac(String email){
        if(ac_exist(email)){
            throw new RuntimeException("Account already exist : " + email);
        }
        sc.nextLine();
        System.out.println("Enter full name : ");
        String name = sc.nextLine();
        System.out.println("Enter initial amount : ");
        Double balance = sc.nextDouble();
        // System.out.println("Enter Security pin : ");
        // sc.nextLine();
        // String pin = sc.nextLine();
        // System.out.print("Enter amount : ");
        // double balance = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Security Pin : ");
        String security_pin = sc.nextLine();
        String query = "Insert into accounts (ac_no,name,email,balance,security_pin) values(?,?,?,?,?)";
    // p.setString(1,);
        try{
            long ac_no = genrate_ac_no();
            // PreparedStatement l = con.prepareStatement(query);
            PreparedStatement p = con.prepareStatement(query);

            p.setLong(1,ac_no);
            p.setString(2,name);
            p.setString(3,email);
            p.setDouble(4,balance);
            p.setString(5, security_pin);
            int result = p.executeUpdate();
            if(result > 0){
                System.out.println("Account Created Successfully....!");
                return ac_no;
            }
        }catch(Exception s){
            System.out.println("exception " + s.getMessage());
        }
        throw new RuntimeException(" exist : " + email);
    }

    public long get_Ac_no(String email){
        String query = "select ac_no from accounts where email = ?";
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1,email);
            ResultSet result = p.executeQuery();
            if(result.next()){
                return result.getLong("ac_no");
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
        throw new RuntimeException("Account no. doesn't exist try again.....!");
    }

    public long genrate_ac_no(){
        try{
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery("Select ac_no from accounts ORDER by ac_no DESC LIMIT 1");
            if(result.next()){
                long a = result.getLong("ac_no");
                return a+1;
            }else{
                return 550000000L;
            }
        }catch(Exception e){
           throw new RuntimeException("Failed to generate Account no.......!");
        }
    }

    public boolean ac_exist(String email){
        String query = "Select ac_no from accounts where email = ?";
        try{
            PreparedStatement p = con.prepareStatement(query);
            p.setString(1,email);
            ResultSet result = p.executeQuery();
            if(result.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
        return false;
    }
}
