import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class aURL {

    public static boolean isWebsite(String url) throws IOException {
        try {
            InputStream eee = new URL(url).openStream();
            return true;
        }catch (IOException e){
            return false;
        }
    }

    public static String findMail(String url) throws IOException {

        String regEx = "([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})";//mail
        String test = "<span lang='NO-BOK'>sgd.no@scandichotels.com</span>";
        //ArrayList<String> mails = new ArrayList<String>();
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

            /*Pattern pp = Pattern.compile(regEx);
            Matcher mm = pp.matcher(test);
            if(mm.find()) System.out.println(mm.group());*/


            if (sb.toString().equals("")) outText = "No mails found";
            if (!sb.toString().isEmpty()) outText = sb.toString();

            //System.out.println(sb);

        } catch (ProtocolException e) {

            outText = e.toString();
        }

        return outText;
    }
}
