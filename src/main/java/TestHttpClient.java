import HttpClient.SimpleHttpClient;

import java.util.Arrays;

public class TestHttpClient {
    public static void main(String[] args)
    {
        try
        {
            SimpleHttpClient client = new SimpleHttpClient("https://webhook.site/0f817188-5f67-4e09-854f-a7d6f956f9cd");
            client.addParam("This is param key", "this is param value");
            client.addParam("Vikram", "Sangamithra");
            client.addQueryParam("this is query key", "this is query value");
            client.setMethod("POST");
            String[] response = client.sendRequest();
        }
        catch (Exception e)
        {
            System.out.println("Exception occured" + Arrays.toString(e.getStackTrace()));
        }


    }
}
