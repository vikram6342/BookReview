package org.example;

import HttpClient.SimpleHttpClient;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    {
        try(PrintWriter writer = res.getWriter())
        {

            SimpleHttpClient client = new SimpleHttpClient("https://webhook.site/0f817188-5f67-4e09-854f-a7d6f956f9cd");
            client.addParam("This is param key", "this is param value");
            client.addQueryParam("this is query key", "this is query value");
            client.setMethod("POST");
            String[] response = client.sendRequest();
            res.getWriter().println(response[0]);
            res.getWriter().println(response[1]);
        }
        catch(Exception e)
        {
            try
            {
                res.getWriter().println("Exception Occured" + e);
            }
            catch (Exception err)
            {

            }

        }
    }
}
