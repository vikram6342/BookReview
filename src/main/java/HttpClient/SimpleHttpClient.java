package HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SimpleHttpClient
{
    SimpleHttpClientPOJO pojo;
    final String DEFAULT_CONTENT_TYPE = ContentType.URL_ENCODED.getContentType();
    String CONTENT_TYPE;
    int readTimeOut = 10000; //10 sec
    boolean READ_RESPONSE = true;
    public SimpleHttpClient(String url)
    {
        this.pojo = new SimpleHttpClientPOJO();
        this.CONTENT_TYPE = DEFAULT_CONTENT_TYPE;
        setURL(url);
    }

    public SimpleHttpClient(String url, String contentType)
    {
        this.pojo = new SimpleHttpClientPOJO();
        this.CONTENT_TYPE = contentType;
        setURL(url);
    }

    public SimpleHttpClient(String url, String contentType, int readTimeOut)
    {
        this.pojo = new SimpleHttpClientPOJO();
        this.CONTENT_TYPE = contentType;
        this.readTimeOut = readTimeOut;
        setURL(url);
    }

    public void addParam(Object key, Object value)
    {
        this.pojo.addParams(key, value);
    }

    public void addQueryParam(Object key, Object value)
    {
        this.pojo.addQueryParams(key, value);
    }

    public void addHeaders(Object key, Object value)
    {
        this.pojo.addHeaders(key, value);
    }

    public void setURL(String url)
    {
        if(!url.endsWith("/"))
        {
            url += '/';
        }
        pojo.setURL(url);
    }

    public void setMethod(String method) //Need to add enum for this
    {
        this.pojo.setMethod(method);
    }

    public String[] sendRequest() throws Exception
    {
        String[] response = new String[3];
        HttpAbstractContentType typeObj = ContentType.getObjFromType(this.CONTENT_TYPE);
        String url = "";
        url = this.pojo.URL + "?" + typeObj.constructQueryParams(this.pojo.queryParams);
        URL connectionUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) connectionUrl.openConnection();
        con.setConnectTimeout(this.readTimeOut);
        con.setRequestMethod(this.pojo.method);

        for(Map.Entry<String, String> entry : this.pojo.headers.entrySet()) //adding headers
        {
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }

        con.setRequestProperty("Content-Type", this.CONTENT_TYPE);

        // Constructing the body

        String body = typeObj.consturctBody(this.pojo.params);
        con.setDoOutput(true);
        try(OutputStream out = con.getOutputStream())
        {
            byte[] dataToSend = body.getBytes(StandardCharsets.UTF_8);
            out.write(dataToSend);
        }

        int responseCode = con.getResponseCode();
        response[0] = responseCode + "";
        response[1] = con.getResponseMessage();
        StringBuffer responseContent = new StringBuffer();
        if(READ_RESPONSE)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                responseContent.append(line);
            }
        }
        con.disconnect();

        response[2] = responseContent.toString();

        return response;
    }

    public enum RequestMethods
    {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE");
        private String method;

        RequestMethods(String requestMethod)
        {

        }

    }

    public enum ContentType
    {
        URL_ENCODED("application/x-www-form-urlencoded", new HttpUrlEncodedContentType());

        String contentType;
        HttpAbstractContentType obj;

        ContentType(String contentType, HttpAbstractContentType obj)
        {
            this.contentType = contentType;
            this.obj = obj;
        }

        public String getContentType()
        {
            return this.contentType;
        }

        public static HttpAbstractContentType getObjFromType(String contentType)
        {

            for(ContentType type : ContentType.values())
            {
                if(type.contentType.equals(contentType))
                {
                    return type.obj;
                }
            }
            return null;
        }
    }

}
