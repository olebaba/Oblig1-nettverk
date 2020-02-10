/**
 * Socket programming example: TCP Multi-client Server
 * DATA2410 Networking and Cloud Computing, Spring 2020
 * Raju Shrestha, OsloMet
 **/
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class MultiClientServerTCP
{
    public static void main(String[] args) throws IOException
    {

        int portNumber = 5555; // Default port to use

        if (args.length > 0) //
        {
            if (args.length == 1)
                portNumber = Integer.parseInt(args[0]);
            else
            {
                System.err.println("Usage: java EchoUcaseServerMutiClients [<port number>]");
                System.exit(1);
            }
        }

        System.out.println("Hi, I am the Multi-client TCP server. port number " + portNumber);

         try (
                // Create server socket with the given port number
                ServerSocket serverSocket =
                        new ServerSocket(portNumber);
          )
        {
            String receivedText;
            // continuously listening for clients
            while (true)
            {
                // create and start a new ClientServer thread for each connected client
                ClientService clientserver = new ClientService(serverSocket.accept());
                clientserver.start();
            }
        } catch (IOException e)
        {

            System.out.println("Exception occurred when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

    }


    /***
     * This class serves a client in a separate thread
     */
    static class ClientService extends Thread
    {
        History hist = new History();
        Socket connectSocket;
        InetAddress clientAddr;
        int serverPort, clientPort;

        public ClientService(Socket connectSocket)
        {
            this.connectSocket = connectSocket;
            clientAddr = connectSocket.getInetAddress();
            clientPort = connectSocket.getPort();
            serverPort = connectSocket.getLocalPort();
        }

        public void run()
        {
            try (
                    // Create server socket with the given port number
                    PrintWriter out =
                            new PrintWriter(connectSocket.getOutputStream(), true);
                    // Stream reader from the connection socket
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connectSocket.getInputStream()));
            )
            {

                String receivedText;
                // read from the connection socket
                while (((receivedText = in.readLine()) != null))
                {
                    System.out.println("Client [" + clientAddr.getHostAddress() +  ":" + clientPort +"] > " + receivedText);

                    String outText = "Mails found: ";
                    //sjekker om url finnes
                    if(SingleClientServerTCP.isWebsite(receivedText)){
                        outText += SingleClientServerTCP.findMail(receivedText);
                        hist.add(receivedText);
                    }else if(receivedText.equals("last")){ //gives last working website as recievedText
                        if(hist.getLast().equals("0")) outText = "No history";
                        else outText += SingleClientServerTCP.findMail(hist.getLast());
                    }else if(receivedText.equals("hist")){
                        outText = "Your recent history: " + Arrays.toString(hist.history.toArray());
                    }else {
                        outText = "No such website";
                    }

                    if(outText.equals("No such website")) out.println(outText);
                    else {
                        out.println(outText);
                        System.out.println(outText + "\tThis is history: " + hist.history);
                    }

                    //out.println(mail);
                    //System.out.println(mail);
                }

                // close the connection socket
                connectSocket.close();

            } catch (IOException e)
            {
                System.out.println("Exception occurred when trying to communicate with the client " + clientAddr.getHostAddress());
                System.out.println(e.getMessage());
            }
        }
    }
}
