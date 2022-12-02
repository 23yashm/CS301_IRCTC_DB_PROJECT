import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class client
{
    public static void main(String args[])throws IOException
    {   
        double start_time = System.nanoTime();
        int pools = 4;      // No of pools
        int threads_ = 2;   // No of threads
 
        for (int j=1 ; j<=pools ; j++){     
            ExecutorService executorService = Executors.newFixedThreadPool(threads_) ;
            
            for(int i = 0; i < threads_; i++)
            {
                Runnable runnableTask = new sendQuery(start_time);
                executorService.submit(runnableTask);
            }
            
            executorService.shutdown();
        }
    }
}

class sendQuery implements Runnable
{
    int sockPort = 7005 ;
    double start_time = 0;
    double end_time = 0;

    public sendQuery(double start_time1) {
        start_time = start_time1;
    }
    
    public void run()
    {
        try 
        {
            //Creating a client socket to send query requests
            Socket socketConnection = new Socket("localhost", sockPort) ;
            
            // Files for input queries and responses
            String inputfile = "./input/input_8/" + Thread.currentThread().getName() + "_input.txt" ;
            String outputfile = "./output/" + Thread.currentThread().getName() + "_output.txt" ;

            //-----Initialising the Input & ouput file-streams and buffers-------
            OutputStreamWriter outputStream = new OutputStreamWriter(socketConnection.getOutputStream());
            BufferedWriter bufferedOutput = new BufferedWriter(outputStream);
            InputStreamReader inputStream = new InputStreamReader(socketConnection.getInputStream());
            BufferedReader bufferedInput = new BufferedReader(inputStream);
            PrintWriter printWriter = new PrintWriter(bufferedOutput,true);
            File queries = new File(inputfile); 
            File output = new File(outputfile); 
            FileWriter filewriter = new FileWriter(output);
            Scanner sc = new Scanner(queries);
            String query = "";
            //--------------------------------------------------------------------

            // Read input queries
            while(sc.hasNextLine())
            {
                query = sc.nextLine();
                printWriter.println(query);
                if (Objects.equals(query,"#")) {
                    break;
                }
            }

            // Get query responses from the input end of the socket of client
            char c;
            while((c = (char) bufferedInput.read()) != '#')      
            {
                filewriter.write(c);
            }

            // close the buffers and socket
            filewriter.close();
            sc.close();
            socketConnection.close();
        } 
        catch (IOException e1)
        {
            e1.printStackTrace();
        }

        end_time = System.nanoTime();
        double total_time = end_time-start_time;
        System.out.println("Total Time taken is: " +  total_time);
        
    }
}
