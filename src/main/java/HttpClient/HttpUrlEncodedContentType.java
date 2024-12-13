package HttpClient;

import java.util.Map;

public class HttpUrlEncodedContentType extends HttpAbstractContentType{

    String consturctBody(Map<String, String> body) throws Exception {
        return super.constructQueryParams(body);
    }
}
