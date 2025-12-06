package banking.src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class admin {
    private Connection con;
    private Scanner sc;

    public admin(Connection con, Scanner sc){
        this.con = con;
        this.sc = sc;
    }

    public void credit(long ac_no)throws SQLException{
        sc.nextLine();
        System.out.print("Enter amount : ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Security pin : ");
        String pin = sc.nextLine();
        try{
            con.setAutoCommit(false);
            if(ac_no != 0){
                PreparedStatement p = con.prepareStatement("select * from accounts where ac_no = ? and security_pin = ?");
                p.setLong(1, ac_no);
                p.setString(2, pin);
                ResultSet result = p.executeQuery();
                if(result.next()){
                    String query = "update accounts set balance = balance + ? Where ac_no = ?";
                    PreparedStatement q = con.prepareStatement(query);
                    q.setDouble(1, amount);
                    q.setLong(2, ac_no);
                    int i = q.executeUpdate();
                    if(i>0){
                        System.out.println("Rs. "+amount+" credit Successfully.....!");
                        con.commit();
                        con.setAutoCommit(true);
                        return;
                    }else{
                        System.out.println("Transaction Failed ");
                        con.rollback();
                        con.setAutoCommit(true);
                    }
                }else{
                    System.out.println("Invalid Security pin");
                }
                
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
        con.setAutoCommit(true);
    }


     public void Debit(long ac_no)throws SQLException{
        sc.nextLine();
        System.out.print("Enter amount : ");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Security pin : ");
        String pin = sc.nextLine();
        try{
            con.setAutoCommit(false);
            if(ac_no != 0){
                PreparedStatement p = con.prepareStatement("select * from accounts where ac_no = ? and security_pin = ?");
                p.setLong(1, ac_no);
                p.setString(2, pin);
                ResultSet result = p.executeQuery();
                if(result.next()){
                    String query = "update accounts set balance = balance - ? where ac_no = ?";
                    PreparedStatement q = con.prepareStatement(query);
                    q.setDouble(1, amount);
                    q.setLong(2, ac_no);
                    int i = q.executeUpdate();
                    if(i>0){
                        System.out.println("Rs. "+amount+" Debit Successfully.....!");
                        con.commit();
                        con.setAutoCommit(true);
                        return;
                    }else{
                        System.out.println("Transaction Failed ");
                        con.rollback();
                        con.setAutoCommit(true);
                    }
                }else{
                    System.out.println("Invalid Security pin");
                }
            }
        }catch(SQLException s){
            System.out.println("error");
            s.printStackTrace();
        }
        con.setAutoCommit(true);
    }


    public void Transfer(long ac_no)throws SQLException{
        sc.nextLine();
        System.out.print("Enter Receiver ac no. : ");
        long receive = sc.nextLong();
        System.out.print("Enter amount : ");
        double amt = sc.nextDouble();
        System.out.print("Enter Security pin ");
        sc.nextLine();
        String pin = sc.nextLine();
        try{
            con.setAutoCommit(false);
            if(ac_no != 0){
                PreparedStatement p = con.prepareStatement("Select * from accounts where security_pin = ?");
                p.setString(1, pin);
                ResultSet result = p.executeQuery();
                if(result.next()){
                    double cur_bal = result.getDouble("balance");
                    if(cur_bal >= 0){
                        String d_query = "update accounts set balance = balance - ? Where ac_no = ?";
                        String c_query = "update accounts set balance = balance + ? Where ac_no = ?";
                        PreparedStatement c_p = con.prepareStatement(c_query);
                        PreparedStatement d_p = con.prepareStatement(d_query);
                        c_p.setDouble(1, amt);
                        c_p.setLong(2,receive);
                        d_p.setDouble(1, amt);
                        d_p.setLong(2, ac_no);
                        int rAffect = c_p.executeUpdate();
                        int rAffect1 = d_p.executeUpdate();
                        if(rAffect>0 && rAffect1>0){
                            System.out.println("Transaction success....!");
                            con.commit();
                            con.setAutoCommit(true);
                        }else{
                            System.out.println("Transaction Failed....!");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                        
                    }else{
                        System.out.println("Insufficient Balance ");
                    }
                }else{
                    System.out.println("Invalid pin...!");
                }
            }
            else{
                    System.out.println("Invalid account no...!");
            }
        }catch(SQLException s){
            s.printStackTrace();
        }

    }

    public void get_bal(long ac_no){
        sc.nextLine();
        System.out.print("Enter Security pin : ");
        String pin = sc.nextLine();
        try{
            PreparedStatement p = con.prepareStatement("select * from accounts where ac_no = ? and security_pin = ?");
            p.setLong(1, ac_no);
            p.setString(2, pin);
            ResultSet result = p.executeQuery();
            if(result.next()){
                double bal = result.getDouble("balance");
                System.out.println("Balance "+bal);
            }else{
                System.out.println("Invalid pin");
            }
        }catch(SQLException s){
            s.printStackTrace();
        }
    }

}
