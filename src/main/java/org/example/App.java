package org.example;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            URL mySite = new URL("https://www.homecenter.com.co/homecenter-co/product/350356/piso-gres-made-tarn-gold-cafe-15x90-cm-caj-108-m2/350356/?cid=dailydealHome#enlace");
            System.out.println("Protocol: "+ mySite.getProtocol());
            System.out.println("Authority: " + mySite.getAuthority());
            System.out.println("Host: " + mySite.getHost());
            System.out.println("Port: " + mySite.getPort());
            System.out.println("Path: " + mySite.getPath());
            System.out.println("Query: " + mySite.getQuery());
            System.out.println("File: " + mySite.getFile());
            System.out.println("Ref: " + mySite.getRef());
        } catch (MalformedURLException e) {
            //Logger.getLogger(App.class.getName().log)
        }
    }
}
