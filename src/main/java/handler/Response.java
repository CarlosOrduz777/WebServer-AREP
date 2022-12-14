package handler;

import org.example.firstChallenge.HttpServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;

public class Response {
    static String filesPath = "src/main/";

    public static String getDefaultResponse() {
        String res = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n"
                + "</head>"
                + "<body>"
                + "<h2>This is the default server response :)</h2>"
                + "</body>"
                + "</html>";

        return res;
    }

    public static String getNotFoundResponse(String message) {
        String res = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Resource not found</title>\n"
                + "</head>"
                + "<body>"
                + "<h2>Oh no, something went wrong...</h2>"
                + "<h3>A repetir AREP papá</h3>"
                + "<p>" + message + "</p>"
                + "</body>"
                + "</html>";

        return res;
    }

    private static boolean isBinary(String extension) {
        return extension.endsWith(".jpg") || extension.endsWith(".png");
    }

    private static String prepareImage(URI uri, OutputStream outputStream) {
        String response;
        String extension = uri.toString().substring(uri.getPath().lastIndexOf(".") + 1);

        System.out.println("   ");
        System.out.println("   ");
        System.out.println("   ");
        System.out.println("handler.Response handlers: ");
        System.out.println(HttpServer.types.get(extension.toUpperCase()));
        String contentType = "application/json";
        System.out.println(HttpServer.types.get(contentType.toUpperCase()));
        response = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: " + HttpServer.types.get(contentType.toUpperCase()) + "\r\n"
                + "\r\n";

        File file = new File(filesPath + uri.getPath());
        System.out.println("-------------"+uri.getPath());

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            ImageIO.write(bufferedImage, extension, byteArrayOutputStream);
            dataOutputStream.writeBytes(response);
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static String prepareResponse(URI uri, OutputStream outputStream) {
        StringBuilder response;

        // TODO
        // $
        String extension = uri.toString();
        System.out.println("URI (responseHandlers): " + extension);
        System.out.println("   ");
        System.out.println("   ");
        System.out.println("   ");

        if (isBinary(extension)) {
            return prepareImage(uri, outputStream);
        }

        response = new StringBuilder("HTTP/1.1 200 OK \r\n"
                + "Content-Type: " + HttpServer.types.get(extension.toUpperCase()) + "\r\n"
                + "\r\n");

        File file = new File(filesPath + uri.getPath());

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String fileLine;

            while ((fileLine = bufferedReader.readLine()) != null) {
                response.append(fileLine);
            }

        } catch (FileNotFoundException e) {
            return getNotFoundResponse(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
