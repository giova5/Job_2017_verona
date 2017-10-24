package com.emis.job2017;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jo5 on 23/10/17.
 * This class contains all server requests as static method.
 */

public class ServerOperations {

    private URL url;
    private static String urlHeaderAsString = "http://job2017.webiac.it/access/";
    private static String ACVISESPELENCO = "acvisespelenco/";
    private static String ACORGPROFILO = "acorgprofilo/";
    private static String ACVISLOGIN = "acvislogin/";
    private static String ACVISPROGRAMMA = "acvisprogramma/";
    private static String ACVISQUESTIONARIO = "acvisquestionario/";
    private static String ACORGVISINGRESSOREGISTRA = "acorgvisingressoregistra/";
    private static String ACVISPROFILO = "acvisprofilo/";

    private static String AUTHENTICATE = "json_login.php";
    private static String GET_ACCESS_TOKEN = "json_conversione_token.php";
    private static String GET_EVENT_INFO = "json.php?app=1";
    private static String GET_EXHIBITORS_CATEGORIES = "json_categorie.php?app=1";
    private static String GET_EXHIBITORS_PADIGLIONI = "json_padiglioni.php?app=1";
    private static String GET_EXHIBITORS_INFO = "json_espositori.php?app=1";
    private static String GET_PROGRAM = "/json.php?app=1";


    public ServerOperations(Utils.EventType eventType){

        String currentEndPointForRequest = null;

        try {

            switch (eventType){
                case GET_ACCESS_TOKEN:
                    currentEndPointForRequest = urlHeaderAsString + ACVISLOGIN + AUTHENTICATE;
                    break;
                case USER_AUTHENTICATE:
                    currentEndPointForRequest = urlHeaderAsString + ACVISLOGIN + GET_ACCESS_TOKEN;
                    break;
                case GET_EVENT_INFO:
                    currentEndPointForRequest = urlHeaderAsString + ACORGPROFILO + GET_EVENT_INFO;
                    break;
                case GET_EXHIBITORS_CATEGORIES:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + GET_EXHIBITORS_CATEGORIES;
                    break;
                case GET_EXHIBITORS_PADIGLIONI:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + GET_EXHIBITORS_PADIGLIONI;
                    break;
                case GET_EXHIBITORS_INFO:
                    currentEndPointForRequest = urlHeaderAsString + ACVISESPELENCO + GET_EXHIBITORS_INFO;
                    break;
                case GET_PROGRAM:
                    currentEndPointForRequest = urlHeaderAsString + ACVISPROGRAMMA + GET_PROGRAM;
                    break;
                //TODO: TO BE CONTINUED
                default:
                    currentEndPointForRequest = null;
                    break;

            }

            url = new URL(currentEndPointForRequest);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


}
