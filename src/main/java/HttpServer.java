import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running =true;
        while(running) {


            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstLine = true;
            String path ="";
            while ((inputLine = in.readLine()) != null) {

                if(firstLine){
                    String[] receive = inputLine.split(" ");
                    path = receive[1];
                }
                firstLine = false;
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }

            }
            URI pathURL = null;
            try {
                pathURL = new URI(path);
            }catch (URISyntaxException e){

            }
            String body="";
            if (path.startsWith("/hello")){
                body = "Hello " + pathURL.getQuery().substring(5);
            }else {
                body = HttpServer.getFile("/index.html");
            }
            outputLine = "HTTP/1.1 200 OK\r\n"
                    + "Content-type: text/html\r\n"
                    + "\r\n"
                    + body;

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
            serverSocket.close();
        }
        public static String getForm(){
            return "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <title>Form Example</title>\n" +
                    "        <meta charset=\"UTF-8\">\n" +
                    "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <h1>Form with GET</h1>\n" +
                    "        <form action=\"/hello\">\n" +
                    "            <label for=\"name\">Name:</label><br>\n" +
                    "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                    "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                    "        </form> \n" +
                    "        <div id=\"getrespmsg\"></div>\n" +
                    "\n" +
                    "        <script>\n" +
                    "            function loadGetMsg() {\n" +
                    "                let nameVar = document.getElementById(\"name\").value;\n" +
                    "                const xhttp = new XMLHttpRequest();\n" +
                    "                xhttp.onload = function() {\n" +
                    "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                    "                    this.responseText;\n" +
                    "                }\n" +
                    "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                    "                xhttp.send();\n" +
                    "            }\n" +
                    "        </script>\n" +
                    "\n" +
                    "        <h1>Form with POST</h1>\n" +
                    "        <form action=\"/hellopost\">\n" +
                    "            <label for=\"postname\">Name:</label><br>\n" +
                    "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n" +
                    "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n" +
                    "        </form>\n" +
                    "        \n" +
                    "        <div id=\"postrespmsg\"></div>\n" +
                    "        \n" +
                    "        <script>\n" +
                    "            function loadPostMsg(name){\n" +
                    "                let url = \"/hellopost?name=\" + name.value;\n" +
                    "\n" +
                    "                fetch (url, {method: 'POST'})\n" +
                    "                    .then(x => x.text())\n" +
                    "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n" +
                    "            }\n" +
                    "        </script>\n" +
                    "    </body>\n" +
                    "</html>";
        }

        public static String getFile(String path){
            StringBuilder output = new StringBuilder();
            Path file = Paths.get("target/classes/public"+path);

            try (InputStream in = Files.newInputStream(file);
                 BufferedReader reader =
                         new BufferedReader(new InputStreamReader(in))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    output.append(line);
                }
            } catch (IOException x) {
                System.err.println(x);
            }

            return output.toString();
        }



    }