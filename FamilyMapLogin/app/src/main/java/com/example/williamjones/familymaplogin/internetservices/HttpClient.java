package com.example.williamjones.familymaplogin.internetservices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by williamjones on 3/12/16.
 */
public class HttpClient
{

    public String getUrl(String server_host, String server_port, String whatIwant, String acesstoken)
    {
        try
        {
            URL url = new URL("http://"+server_host+":"+server_port+"/"+whatIwant);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            // Set HTTP request headers, if necessary
             connection.addRequestProperty("Authorization", acesstoken);
            InputStream responseBody = connection.getInputStream();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                // Convert response body bytes to a string
                return baos.toString();
            }
            else {
                System.out.println("Server Failure");
                // SERVER RETURNED AN HTTP ERROR
            }
        }
        catch (IOException e) {
            System.out.println("Io error");
            e.printStackTrace();
            // IO ERROR
        }

        return null;
    }

    public String postUrl(String username, String password, String server_host, String server_port)
    {
        String postData = "{"+"username:\""+username+"\","+"password"+":\""+password+"\"}";
        try {
            URL url = new URL("http://"+server_host+":"+server_port+"/user/login");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();
            // Write post data to request body
            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(postData.getBytes());
            requestBody.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                // Convert response body bytes to a string

                return baos.toString();
            }
            else {
              System.out.println("Server Failure");
                // SERVER RETURNED AN HTTP ERROR
            }
        }
        catch (IOException e) {
            System.out.println("Io error");
            // IO ERROR
        }
        return null;
    }
}
