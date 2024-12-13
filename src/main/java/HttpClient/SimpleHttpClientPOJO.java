package HttpClient;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleHttpClientPOJO
{
    protected String URL;
    protected Map<String, String> params;
    protected Map<String, String> queryParams;
    protected Map<String, String> headers;
    protected String method;

    protected SimpleHttpClientPOJO()
    {
        this.params = new LinkedHashMap<>();
        this.queryParams = new LinkedHashMap<>();
        this.headers = new HashMap<>();

    }

    protected void addParams(Object paramKey, Object paramValue)
    {
        this.params.put(paramKey.toString(), paramValue.toString());
    }

    protected void addQueryParams(Object paramKey, Object paramValue)
    {
        this.queryParams.put(paramKey.toString(), paramValue.toString());
    }

    protected void addHeaders(Object paramKey, Object paramValue)
    {
        this.headers.put(paramKey.toString(), paramValue.toString());
    }

    protected void setMethod(String method)
    {
        this.method = method;
    }

    protected void setURL(String url)
    {
        this.URL = url;
    }

}
