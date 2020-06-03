import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.net.UnknownHostException;

public class ImageParser {

    static void parseImage() throws InterruptedException {
        try {
            Document doc = Jsoup.connect("https://www.bing.com/?toWww=1&redig=7D6D9A57DDD741A9A4C36F2CAB139296").get();

            Elements links = doc.select("head").select("link");
            String[] parsedLinks = new String[10];
            int i = 0;
            for (Element link : links){
                parsedLinks[i] = link.attr("href").toString();
                i++;
            }

            String imageLink = "https://www.bing.com" + parsedLinks[1];  //Reference to image link
            //System.out.println(imageLink);

            URL url = new URL(imageLink);

            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            String imageName = parsedLinks[1].substring(parsedLinks[1].indexOf("OHR") + 4, parsedLinks[1].indexOf("&") - 4);  //image name
            //System.out.println(imageName);
            //System.out.println(parsedLinks[1]);
            String imagePath = "D:\\Programs\\WallpaperChanger\\" + imageName + ".jpg";

            FileOutputStream fos = new FileOutputStream(imagePath);
            fos.write(response);
            fos.close();

            WallpaperChanger.changeWallpaper(imagePath);

        } catch (SSLException ssl){
            System.out.println("SSLException occurred");
            Thread.sleep(10000);
            parseImage();
        } catch (NoRouteToHostException no){
            System.out.println("NoRouteToHostException occurred");
            Thread.sleep(10000);
            parseImage();
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException occurred");
            Thread.sleep(10000);
            parseImage();
        } catch (IOException ex){
            System.out.println("IOException occurred");
            Thread.sleep(10000);
            parseImage();
        }
    }

}
