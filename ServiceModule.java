import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import creds.*;
import java.sql.*;

public class ServiceModule {
    static int serverPort = 7005;
    static int numServerCores = 50;  // Number of cores

    public static void main(String[] args) throws IOException{           
        ExecutorService executorService = Executors.newFixedThreadPool(numServerCores);
        ServerSocket serverSocket = new ServerSocket(serverPort);
        Socket socketConnection = null;
        
        while(true){
            System.out.println("Listening port : " + serverPort + "\nWaiting for clients...");
            socketConnection = serverSocket.accept();
            System.out.println("Accepted client :" + socketConnection.getRemoteSocketAddress().toString() + "\n");
            Runnable runnableTask = new QueryRunner(socketConnection);
            executorService.submit(runnableTask);   
        }
    }
}


class QueryRunner implements Runnable{
    protected Socket socketConnection;
    public QueryRunner(Socket clientSocket){
        this.socketConnection =  clientSocket;
    }

    public void run(){
        try{
            InputStreamReader inputStream = new InputStreamReader(socketConnection.getInputStream()) ;
            BufferedReader bufferedInput = new BufferedReader(inputStream);
            OutputStreamWriter outputStream = new OutputStreamWriter(socketConnection.getOutputStream()) ;
            BufferedWriter bufferedOutput = new BufferedWriter(outputStream) ;
            PrintWriter printWriter = new PrintWriter(bufferedOutput, true) ;
            
            String clientCommand = "" ;
            String responseQuery = "" ;
            String queryInput = "" ;

            while(true){
                clientCommand = bufferedInput.readLine();
                StringTokenizer tokenizer = new StringTokenizer(clientCommand);
                queryInput = tokenizer.nextToken();

                if(queryInput.equals("#")){
                    printWriter.println("#");
                    String returnMsg = "Connection Terminated - client : " + socketConnection.getRemoteSocketAddress().toString();
                    System.out.println(returnMsg);
                    inputStream.close();
                    bufferedInput.close();
                    outputStream.close();
                    bufferedOutput.close();
                    printWriter.close();
                    socketConnection.close();
                    return;
                }

                int pass_num = Integer.parseInt(queryInput);
                String pass_name[] = new String[pass_num];
                String pass_name_str = "";

                for (int i=0 ; i<pass_num ; i++){
                    queryInput = tokenizer.nextToken();
                    if (i!=pass_num-1){
                        queryInput = queryInput.substring(0,queryInput.length()-1);
                    }
                    pass_name[i] = queryInput;
                    pass_name_str = pass_name_str + queryInput;
                    if (i!=pass_num-1){
                        pass_name_str = pass_name_str + ",";
                    }
                }
                String train_no = queryInput = tokenizer.nextToken();
                String date = queryInput = tokenizer.nextToken();
                String coach_type = queryInput = tokenizer.nextToken();

                Connection c = null;

                try {
                    String server = creds.server;
                    String database = creds.database;
                    String port = creds.port;
                    String username = creds.username;
                    String password = creds.password;

                    c = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + database, username, password);

                    String pnr = train_no + date.substring(0,4) + date.substring(5,7) + date.substring(8,10) ;

                    String query = "select * from book_ticket("+
                        Integer.toString(pass_num)+"::int,"+  
                        "'{"+pass_name_str+"}'::text[],"+
                        train_no+","+  
                        "'"+date+"'::date,"+ 
                        "'"+coach_type+"'::text," + 
                        pnr+"::bigint);";

                    try {
                        Statement stmt = c.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        String status = "";
                        while (rs.next()) {
                            status = rs.getString("status__");
                            pnr = rs.getString("pnr__");
                        }
                        if (status.equals("-1")){
                            responseQuery = "TNA -- ";
                            responseQuery = responseQuery + "PNR: " + pnr;
                        }
                        else if (status.equals("-2")){
                            responseQuery = "SNA -- ";
                            responseQuery = responseQuery + "PNR: " + pnr;
                        }
                        else{
                            query = "select * from ticket_pass T, berth_info B where T.pnr="+pnr+" and B.coach_type='"+coach_type+"' and T.berth_no=B.berth_no;";
                            stmt = c.createStatement();
                            rs = stmt.executeQuery(query);
                            responseQuery = "CNF -- ";
                            responseQuery = responseQuery + "PNR: " + pnr;
                            responseQuery = responseQuery + " ; TRAIN_NO: " + train_no;
                            responseQuery = responseQuery + " ; DATE: " + date;
                            responseQuery = responseQuery + " ; COACH_TYPE: " + coach_type;
                            while (rs.next()) {
                                String name = rs.getString("name");
                                String coach_no = rs.getString("coach_no");
                                String berth_no = rs.getString("berth_no");
                                String berth_type = rs.getString("berth_type");
                                responseQuery = responseQuery + " ; " + name + " - " + coach_type.substring(0,1) + coach_no + "/" + berth_no + "/" + berth_type;
                            }
                        }
                        pnr = "";
                        
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    c.close();
                } catch (Exception e) {
                    System.out.println(e);
                }

                printWriter.println(responseQuery);                
            }
        }
        catch(IOException e)
        {
            return;
        }
    }
}