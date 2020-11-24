import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import entities.URLCodeResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Logic {

    private final Gson gson = new Gson();

    public void cleanDB(String user) {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:postgresql://192.168.243.189:5432/detectidQA2", "detectid", "detectid");
            Statement stmt = c.createStatement();
            String sql_1 = "DELETE FROM mobile_token WHERE mobile_auth_device_id IN\n" +
                    "(SELECT mobile_auth_device_id FROM public.mobile_auth_device WHERE\n" +
                    "client_id IN (SELECT cli_id_client FROM client WHERE cli_shared_key = '" + user + "'))\n";
            System.out.println("Opened database successfully, executing query: " + sql_1);
            stmt.executeUpdate(sql_1);
            String sql_2 = "DELETE FROM public.mobile_auth_device WHERE client_id IN\n" +
                    "(SELECT cli_id_client FROM client WHERE cli_shared_key = '" + user + "')";
            System.out.println("Opened database successfully, executing query: " + sql_2);
            stmt.executeUpdate(sql_2);
            stmt.close();
            c.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getCode(String user) throws UnirestException {
        String URL = "http://192.168.243.189:8080/mobile-auth-service/api/v1/clients/" + user + "/activation/codes";
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/json");
        String body = "{\"notify\": false }";
        HttpResponse responseURLCode = APIrest.postRequest(URL, header, body);
        URLCodeResponse urlCode = gson.fromJson(responseURLCode.getBody().toString(), URLCodeResponse.class);
        return urlCode.details.registrationUrl;
    }


}
