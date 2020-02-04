/**
 * Socket programming example: TCP Server
 * DATA410 Networking and Cloud Computing, Spring 2020
 * Raju Shrestha, OsloMet
 **/
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EchoUcaseServerTCP
{
    public static void main(String[] args) throws IOException {
        int portNumber = 5555; // Default port to use

        if (args.length > 0) {
            if (args.length == 1)
                portNumber = Integer.parseInt(args[0]);
            else {
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
        ) {
            InetAddress clientAddr = connectSocket.getInetAddress();
            int clientPort = connectSocket.getPort();
            String receivedText;
            // read from the connection socket
            while ((receivedText = in.readLine()) != null) {
                System.out.println("Client [" + clientAddr.getHostAddress() + ":" + clientPort + "] > " + receivedText);

                String mail = "";

                //sjekker om url finnes
                if(isWebsite(receivedText)){
                    mail = findMail(receivedText);
                }else {
                    mail = "No such website";
                }

                if(mail.equals("No such website")) out.println("No such website");
                else {
                    out.println("Found mail(s): " + mail); //blir sendt til client
                    System.out.println("Found mail(s): " + mail);
                }
            }

            System.out.println("I am done, Bye!");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
    /*{
        findMail("https://www.elkjop.no/om-oss/Kontakt-oss");
    }*/

    public static boolean isWebsite(String url) throws IOException {
        try { //prøver å lage imputstream av url-en
            InputStream eee = new URL(url).openStream();
            return true;
        }catch (IOException e){
            return false;
        }
    }

    public static String findMail(String url) throws IOException {

        String regEx = "([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})";//mail regex
        //String test = "ssh s123456@oslomet.no.</p>";
        String outText = "";

        var webpageUrl = new URL(url);

        try (var br = new BufferedReader(new InputStreamReader(webpageUrl.openStream()))) {
            String line;

            var sb = new StringBuilder();


            while ((line = br.readLine()) != null) {
                String[] words = line.split(" ");
                for (String w : words) {
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(w);
                    if (m.find()) sb.append(m.group()).append(", ");
                }
            }

            if (sb.toString().equals("")) outText = "No mails found";
            if (!sb.toString().isEmpty()) outText = sb.toString();


        } catch (ProtocolException e) {
            System.out.println(e.toString());
        }

        return outText;
    }
}
