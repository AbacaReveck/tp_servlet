import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class Client
{
    public static void main(String[] args) throws IOException
    {
        URL url = new URL("http://localhost:8080/HelloWorld/helloworld");
        URLConnection connection = url.openConnection();
        InputStream inStream = connection.getInputStream();
        Scanner sc = new Scanner(inStream);

        while (sc.hasNext())
            System.out.println(sc.nextLine());

        sc.close();
        inStream.close();
    }

}