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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import sun.misc.IOUtils;

/**
 *
 * @author will
 */
public class Comandos {

    // Chaves para validacao dos comandos enviados pelo clientes
    public static String COMMAND_KEY_AUTORES = "/autores";
    public static String COMMAND_KEY_TEMPERATURA = "/temperatura";
    public static String COMMAND_KEY_NOTICIAS = "/noticias";

    /**
     * Retorna os autores
     * @return 
     */
    public static String getAutores() {
        String autores = "<html><body>William Mehler<br/>Tiago Schwab<br/>Daniel Moraes<br/>Jocemar Pereira</body></html>";
        return autores;
    }

    /**
     * * A partir da url, passando as coordenadas como parametro, 
     * obtem o JSON que contem as informações relativas a previsao do tempo, e
     * retorna a temperatura 
     * 
     * @param coordenadas
     * @return 
     */
    public static String getTemperatura(String coordenadas) {

        String sURL = "https://api.darksky.net/forecast/baa3bd555593ed8f45232544699ec5a2/" + coordenadas;

        JSONObject json;
        try {
            json = new JSONObject(Utils.readUrl(sURL));
//            System.out.println(""+json);

            String previsaoAtualStr = (String) json.get("currently").toString();

            JSONObject previsaoAtualJSON = new JSONObject(previsaoAtualStr);

            String temperaturaFahrenheit = (String) previsaoAtualJSON.get("temperature").toString();

            String temperaturaCelsius = Utils.fahrenheitToCelsius(Float.parseFloat(temperaturaFahrenheit));

            String retorno = "<html><head <meta charset=\"utf-8\"></head><body style='font-size: 150px;'>" + temperaturaCelsius + "&#186;" + "</body></html>";

            return retorno;

        } catch (Exception ex) {
            Logger.getLogger(Comandos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    /**
     * A partir da url, obtem o xml sobre noticias de tecnologia do site g1, e
     * retorna um titulo sorteado dentre as varias noticias contidas no xml
     *
     * @return
     */
    public static String getNoticia() {
        URL url;

        try {
            url = new URL("http://g1.globo.com/dynamo/tecnologia/rss2.xml");

            SAXReader reader = new SAXReader();
            Document doc = reader.read(url);

            Element root = doc.getRootElement();

            List<String> noticias = new ArrayList();

            // Percorre os itens do conteudo do xml
            for (Object studentObj : root.elements("channel")) {
                Element content = (Element) studentObj;
                // Percorre o conteudo dos itens
                for (Object item : content.elements("item")) {
                    Element title = (Element) item;
                    // obtem o titulo
                    noticias.add(title.element("title").getText());
//                    System.out.println(title.element("title").getText());
                }
            }

            int noticiasLenght = noticias.size();

            Random rand = new Random();
            int randomNum = rand.nextInt(noticiasLenght);
            if (randomNum > noticiasLenght) {
                randomNum = 0;
            }

            // As instrucoes abaixo foram feitos para quebrar a linha da noticia, 
            // pois se for muito grande corta na exibicao do label no lado do cliente
            String noticia = noticias.get(randomNum);
            char[] charListNoticia = noticia.toCharArray();
            int range = 30;
            String stringAux = "";
            for (int i = 0; i < charListNoticia.length; i++) {
                stringAux += charListNoticia[i];
                if (range < charListNoticia.length) {
                    if (range == i) {
                        if (charListNoticia[i] == ' ') {
                            stringAux += "<br/>";
                        } else {
                            stringAux += "-<br/>";
                        }
                        range += 30;
                    }
                }
            }

            String retorno = "<html><head <meta charset=\"utf-8\"></head><body>" + stringAux + "</body></html>";

            return retorno;

        } catch (MalformedURLException ex) {
            Logger.getLogger(Comandos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Comandos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

}
