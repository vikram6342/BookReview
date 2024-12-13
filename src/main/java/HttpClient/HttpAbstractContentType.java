package HttpClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public abstract class HttpAbstractContentType {

    String invalidChars = "[&, =]";
    abstract String consturctBody(Map<String, String> body) throws Exception;

    String constructQueryParams(Map<String, String> queryParams) throws Exception
    {
        String constructedQueryParams = "";
        Iterator<Map.Entry<String, String>> iterator =  queryParams.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.matches(invalidChars) || value.matches(invalidChars))
            {
                throw new Exception("Check with the key and value either one of them is invalid");
            }

            constructedQueryParams += URLEncoder.encode(key) + '=' + URLEncoder.encode(value, StandardCharsets.UTF_8);
            if(iterator.hasNext())
            {
                constructedQueryParams += '&';
            }

        }
        return constructedQueryParams;
    }
}
