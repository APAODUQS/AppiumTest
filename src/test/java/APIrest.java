import java.util.HashMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class APIrest {

    public static HttpResponse getRequest(String URL, HashMap<String, Object> queryParam, HashMap<String, String> headerParam) throws UnirestException {
        return Unirest.get(URL)
                .queryString(queryParam)
                .headers(headerParam)
                .asString();
    }

    public static HttpResponse postRequest(String URL, HashMap<String, String> headerParam, String bodyParam) throws UnirestException {
        return Unirest.post(URL)
                .headers(headerParam)
                .body(bodyParam)
                .asJson();
    }

}
