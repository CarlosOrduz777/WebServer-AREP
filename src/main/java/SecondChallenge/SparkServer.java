package SecondChallenge;

import handler.Response;
import org.example.firstChallenge.HttpServer;

import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class SparkServer {
    private static SparkServer _instance;
    private HttpServer httpServer = new HttpServer();

    public static void main(String[] args) {
        SparkServer eciSparkServer = SparkServer.getInstance();
        eciSparkServer.startServer();
    }

    public static SparkServer getInstance() {
        if (_instance == null) {
            _instance = new SparkServer();
        }
        return _instance;
    }

    public void startServer() {
        httpServer.runServer();
    }

    public static String get(String request, OutputStream outputStream) {
        try {
            System.out.println("Request: "+request);
            String contentType = request.split(" ")[1];
            System.out.println("Request: "+contentType);
            // $
            System.out.println("   ");
            System.out.println("   ");
            System.out.println("   ");
            System.out.println("ContenType (EciSParkServer): " + contentType);

            URI uri = new URI(contentType);

            if (uri.getPath().startsWith("/public")) {
                return Response.prepareResponse(uri, outputStream);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return Response.getDefaultResponse();
    }



}
