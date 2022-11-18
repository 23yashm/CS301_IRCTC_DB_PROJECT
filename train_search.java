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
import java.sql.*;
import creds.*;

public class train_search
{
    public static float time_conv (float time){
        float ans=0;
        int h = (int)time;
        float m = time-h;
        h = h%24;
        return h+m;
    }

    public static boolean compatible(float t1, float t2){
        float diff = t2-t1;
        if (diff<0){
            diff = diff+24;
        }
        if (diff>=10/60 && diff<=3) return true;
        else return false;
    }

    public static String time24(float t){
        int h = (int)t;
        int m = (int)((t-h)*60);
        String ans = h + ":" + m;
        return ans;
    }

    public static void main(String args[])throws IOException
    {
        try 
        {
            String inputfile = "./input/train_search.txt";
            String outputfile = "./output/train_search.txt";
            File queries = new File(inputfile); 
            File output = new File(outputfile); 
            FileWriter filewriter = new FileWriter(output);
            Scanner sc = new Scanner(queries);
            String query = "";

            String dep_station = "";
            String arr_station = "";

            while(sc.hasNextLine())
            {
                query = sc.nextLine();
                if (Objects.equals(query,"#")) {
                    break;
                }
                String[] Q = query.split("\\s+");
                dep_station = Q[0];
                arr_station = Q[1];
            
                filewriter.write("----- FROM: "+dep_station+" -> TO: "+arr_station+" -----\n");
            
                Connection c = null;

                List<Train> dep_train = new ArrayList<>();
                List<Train> arr_train = new ArrayList<>();

                try {
                    String server = creds.server;
                    String database = creds.database;
                    String port = creds.port;
                    String username = creds.username;
                    String password = creds.password;

                    c = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + database, username, password);

                    String sql_query1 = "select * from train_station_info where dep_station='"+dep_station+"';";
                    String sql_query2 = "select * from train_station_info where arr_station='"+arr_station+"';";

                    try {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery(sql_query1);
                        while (rs.next()) {
                            Train temp  = new Train();
                            temp.train_no = rs.getString("train_no");
                            temp.train_name = rs.getString("train_name");
                            temp.dep_station = rs.getString("dep_station");
                            temp.dep_time = Float.parseFloat(rs.getString("dep_time"));
                            temp.arr_station = rs.getString("arr_station");
                            temp.run_duration = Float.parseFloat(rs.getString("run_duration"));
                            dep_train.add(temp);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    try {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery(sql_query2);
                        while (rs.next()) {
                            Train temp  = new Train();
                            temp.train_no = rs.getString("train_no");
                            temp.train_name = rs.getString("train_name");
                            temp.dep_station = rs.getString("dep_station");
                            temp.dep_time = Float.parseFloat(rs.getString("dep_time"));
                            temp.arr_station = rs.getString("arr_station");
                            temp.run_duration = Float.parseFloat(rs.getString("run_duration"));
                            arr_train.add(temp);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    int total_routes = 0;

                    for (int i=0 ; i<dep_train.size() ; i++){
                        if (dep_train.get(i).arr_station.equals(arr_station)){
                            total_routes = total_routes+1;

                            String t1 = dep_train.get(i).train_name;
                            String tn1 = dep_train.get(i).train_no;

                            String dep1 = dep_train.get(i).dep_station;
                            String arr1 = dep_train.get(i).arr_station;
                            Float t_dep1 = dep_train.get(i).dep_time;
                            Float t_arr1 = time_conv(t_dep1 + dep_train.get(i).run_duration);

                            Float total_time = dep_train.get(i).run_duration;

                            filewriter.write(time24(total_time) + " | " + tn1 + " - " + t1 + ": " + dep1 + "(" + time24(t_dep1) + ")" + " -> " + arr1 + "(" + time24(t_arr1) + ")" + "\n");
                        }
                    }

                    for (int i=0 ; i<dep_train.size() ; i++){
                        for (int j=0 ; j<arr_train.size() ; j++){
                            String t1 = dep_train.get(i).train_name;
                            String t2 = arr_train.get(j).train_name;
                            String tn1 = dep_train.get(i).train_no;
                            String tn2 = arr_train.get(j).train_no;

                            String dep1 = dep_train.get(i).dep_station;
                            String arr1 = dep_train.get(i).arr_station;
                            String dep2 = arr_train.get(j).dep_station;
                            String arr2 = arr_train.get(j).arr_station;
                            Float t_dep1 = dep_train.get(i).dep_time;
                            Float t_arr1 = time_conv(t_dep1 + dep_train.get(i).run_duration);
                            Float t_dep2 = arr_train.get(j).dep_time;
                            Float t_arr2 = time_conv(t_dep2 + arr_train.get(j).run_duration);

                            Float total_time = dep_train.get(i).run_duration + time_conv(t_dep2-t_arr1+24) + arr_train.get(j).run_duration;

                            if (arr1.equals(dep2) && compatible(t_arr1,t_dep2)){
                                total_routes = total_routes+1;
                                filewriter.write(time24(total_time) + " | " + tn1 + " - " + t1 + ": " + dep1 + "(" + time24(t_dep1) + ")" + " -> " + arr1 + "(" + time24(t_arr1) + ")" + " ------" + time24(time_conv(t_dep2-t_arr1+24)) + "------ " + tn2 + " - " + t2 + ": " + dep2 + "(" + time24(t_dep2) + ")" + " -> " + arr2 + "(" + time24(t_arr2) + ")" + "\n");
                            }
                        }
                    }

                    if (total_routes==0){
                        filewriter.write("Sorry, no trains availaible for this route.\n");
                    }
                    filewriter.write("\n");
                    
                    c.close();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            System.out.println("---------- Train release status saved in " + outputfile + " ----------");
            
            filewriter.close();
            sc.close();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    } 
}

public class Train{
    String train_no;
    String train_name;
    String dep_station;
    float dep_time;
    String arr_station;
    float run_duration;
}