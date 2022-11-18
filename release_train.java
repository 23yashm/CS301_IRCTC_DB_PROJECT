import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException  ;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import creds.*;
import java.sql.*;

public class release_train
{
    public static void main(String args[])throws IOException
    {
        try 
        {
            String inputfile = "./input/Trainschedule.txt";
            String outputfile = "./output/Trainschedule_status.txt";
            File queries = new File(inputfile); 
            File output = new File(outputfile); 
            FileWriter filewriter = new FileWriter(output);
            Scanner sc = new Scanner(queries);
            String query = "";
            int num_train = 0;
            String train_no_string = "";
            String date_string = "";
            String ac_coach_string = "";
            String sl_coach_string = "";

            while(sc.hasNextLine())
            {
                query = sc.nextLine();
                if (Objects.equals(query,"#")) {
                    break;
                }
                num_train = num_train+1;
                String[] Q = query.split("\\s+");
                train_no_string = train_no_string + Q[0] + ",";
                date_string = date_string + Q[1] + ",";
                ac_coach_string = ac_coach_string + Q[2] + ",";
                sl_coach_string = sl_coach_string + Q[3] + ",";
            }

            train_no_string = train_no_string.substring(0,train_no_string.length()-1);
            date_string = date_string.substring(0,date_string.length()-1);
            ac_coach_string = ac_coach_string.substring(0,ac_coach_string.length()-1);
            sl_coach_string = sl_coach_string.substring(0,sl_coach_string.length()-1);
           
            Connection c = null;

            try {
                String server = creds.server;
                String database = creds.database;
                String port = creds.port;
                String username = creds.username;
                String password = creds.password;

                c = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + database, username, password);

                String sql_query = "select * from release_trains("+
                    Integer.toString(num_train)+"::int,"+  
                    "'{"+train_no_string+"}'::int[],"+
                    "'{"+date_string+"}'::date[],"+
                    "'{"+ac_coach_string+"}'::int[],"+
                    "'{"+sl_coach_string+"}'::int[]"+
                    ");";

                try {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery(sql_query);
                    while (rs.next()) {
                        String train_no = rs.getString("train_no__");
                        String date = rs.getString("date__");
                        String status = rs.getString("status__");
                        filewriter.write(train_no + " " + date + " " + status + "\n");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

                System.out.println("---------- Train release status saved in " + outputfile + " ----------");

                // close the buffers
                filewriter.close();
                sc.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    } 
}