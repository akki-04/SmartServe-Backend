package banking.src;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
public class main {
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("error "+ e.getMessage());
        }
        Scanner sc = new Scanner(System.in);
        try(Connection con = dbCon.getConnection()){
            
            
            user user = new user(con,sc);
            admin admin = new admin(con,sc);
            ac ac = new ac(con,sc);

            String email;
            long ac_no;

            while(true){
                System.out.println("----Welcome To Banking System----");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit ");
                System.out.print("Enter your Choice : ");
                int a = sc.nextInt();

                switch (a) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        email = user.Login();
                        if(email != null){
                            System.out.println();
                            System.out.println("User Logged IN");
                            if(!ac.ac_exist(email)){
                                System.out.println();
                                System.out.println("1. Open new Bank Account ");
                                System.out.println("2. Exit ");
                                System.out.println("Enter you choice : ");
                                if(sc.nextInt() == 1){
                                    // String query = "select "
                                    ac_no = ac.open_ac(email);
                                    System.out.println("Account created Successfully....!");
                                    System.out.println("YOur Account no. is : " + ac_no);
                                }else{
                                    exit();
                                    break;
                                }
                            }
                            
                            ac_no = ac.get_Ac_no(email);
                            int x = 0;
                            while(x != 5){
                                System.out.println("1. Debit money");
                                System.out.println("2. creadit money");
                                System.out.println("3. Transfer money");
                                System.out.println("4. check balance ");
                                System.out.println("5. Exit");
                                System.out.print("Enter your choice : ");
                                x = sc.nextInt();
                                switch (x) {
                                    case 1:
                                        admin.Debit(ac_no);
                                        break;
                                    case 2:
                                        admin.credit(ac_no);
                                        break;
                                    case 3:
                                        admin.Transfer(ac_no);
                                        break;
                                    case 4:
                                        admin.get_bal(ac_no);
                                        break;
                                    case 5:
                                        exit();
                                        break;
                                    default:
                                        System.out.println("Enter valid case....!");
                                        break;
                                }
                            }

                        }else{
                            System.out.println("Incorrect Email or Password");
                        }
                        break;
                    case 3:
                        exit();
                        break;
                    default:
                        System.out.println("Enter correct choice");
                        break;
                }
            }
        }catch(Exception e ){
            System.out.println("error "+e.getMessage());
        }finally{
        sc.close();
        }
    }

    public static void exit(){
        try{
            System.out.printf("Exiting System");
            int i = 5;
            while (i!=0){
                System.out.printf(".");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            System.out.println("Exiting");
        }catch(Exception e){
            System.out.println("Exception .....");
        }
    }

}
