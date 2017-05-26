/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.IOUtils;

/**
 *
 * @author will
 */
public class Comandos {

    public String getAutores() {
        return "William Mehler, Tiago Schwab, Daniel Moraes, Jocemar Pereira";
    }

    public String getTemperatura(String latitude, String longitude) {

        String sURL = "https://api.darksky.net/forecast/baa3bd555593ed8f45232544699ec5a2/" + latitude + "," + longitude;

        JSONObject json;
        try {
            json = new JSONObject(Utils.readUrl(sURL));
//            System.out.println(""+json);

            String previsaoAtualStr = (String) json.get("currently").toString();

            JSONObject previsaoAtualJSON = new JSONObject(previsaoAtualStr);

            String temperaturaFahrenheit = (String) previsaoAtualJSON.get("temperature").toString();

            String temperaturaCelsius = Utils.fahrenheitToCelsius(Float.parseFloat(temperaturaFahrenheit));

            return temperaturaCelsius;

        } catch (Exception ex) {
            Logger.getLogger(Comandos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

}
