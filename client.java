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
        int pools = 2;      // No of pools
        int threads_ = 50;   // No of threads

        for (int j=1 ; j<=pools ; j++){     
            // Creating a thread pool
            ExecutorService executorService = Executors.newFixedThreadPool(threads_) ;
            
            for(int i = 0; i < threads_; i++)
            {
                Runnable runnableTask = new sendQuery();
                executorService.submit(runnableTask);
            }
            
            executorService.shutdown();
            try
            {
                if (!executorService.awaitTermination(900, TimeUnit.MILLISECONDS))
                {
                    executorService.shutdownNow();
                } 
            } 
            catch (InterruptedException e)
            {
                executorService.shutdownNow();
            }
        }
    }
}

class sendQuery implements Runnable
{
    int sockPort = 7005 ;
    
    public void run()
    {
        try 
        {
            //Creating a client socket to send query requests
            Socket socketConnection = new Socket("localhost", sockPort) ;
            
            // Files for input queries and responses
            String inputfile = "./input/" + Thread.currentThread().getName() + "_input.txt" ;
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
    }
}
