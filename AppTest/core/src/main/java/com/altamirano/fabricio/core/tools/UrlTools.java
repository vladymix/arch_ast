package com.altamirano.fabricio.core.tools;

import com.altamirano.fabricio.core.listeners.UrlResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @autor Created by Fabricio Altamirano on 27/3/18.
 */

public class UrlTools {

    public static void getInfo(String url, UrlResponse listnerUrlResponse){

    }

    public static String getUrlSource(String site) throws IOException {
        //GNU Public, from ZunoZap Web Browser
        URL url = new URL(site);
        URLConnection urlc = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                urlc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();
    }

}
