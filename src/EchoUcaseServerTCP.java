/**
 * Socket programming example: TCP Server
 * DATA410 Networking and Cloud Computing, Spring 2020
 * Raju Shrestha, OsloMet
 **/
import java.net.*;
import java.io.*;

public class EchoUcaseServerTCP
{
    public static void main(String[] args) throws IOException
    /*{
        int portNumber = 5555; // Default port to use

        if (args.length > 0)
        {
            if (args.length == 1)
                portNumber = Integer.parseInt(args[0]);
            else
            {
                System.err.println("Usage: java EchoUcaseServerTCP [<port number>]");
                System.exit(1);
            }
        }

        System.out.println("Hi, I am EchoUCase TCP server");

        // try() with resource makes sure that all the resources are automatically
        // closed whether there is any exception or not!!!
        try (
                // Create server socket with the given port number
                ServerSocket serverSocket =
                        new ServerSocket(portNumber);
                // create connection socket, server begins listening
                // for incoming TCP requests
                Socket connectSocket = serverSocket.accept();

                // Stream writer to the connection socket
                PrintWriter out =
                        new PrintWriter(connectSocket.getOutputStream(), true);
                // Stream reader from the connection socket
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connectSocket.getInputStream()));
        )
        {
            InetAddress clientAddr = connectSocket.getInetAddress();
            int clientPort = connectSocket.getPort();
            String receivedText;
            // read from the connection socket
            while ((receivedText = in.readLine())!=null)
            {
                System.out.println("Client [" + clientAddr.getHostAddress() +  ":" + clientPort +"] > " + receivedText);

                String outText = receivedText.toUpperCase();
                // Write the converted uppercase string to the connection socket

                //legg til kode her?
                String mail = findMail(outText);

                out.println(mail);
                System.out.println("Found mail: " + mail);
            }

            System.out.println("I am done, Bye!");
        } catch (IOException e)
        {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }*/
    {
        findMail("https://student.oslomet.no/om-e-post");
    }

    public static String findMail(String url) throws IOException {

        String regEx = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        String mail = "";
        //gå til outText og søk etter mail med regEx

        var webpageUrl = new URL(url);
        try(var br = new BufferedReader(new InputStreamReader(webpageUrl.openStream()))){
            String line;

            var sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                for(String w: words){
                    if(w.contains(regEx)){
                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }
                }
            }

            mail = sb.toString();

            System.out.println(mail);

        }
        return mail;
    }
}
