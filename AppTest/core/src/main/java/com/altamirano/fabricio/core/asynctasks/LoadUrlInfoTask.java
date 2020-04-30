package com.altamirano.fabricio.core.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.altamirano.fabricio.core.commons.InfoUrl;
import com.altamirano.fabricio.core.listeners.UrlResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @autor Created by Fabricio Altamirano on 27/3/18.
 */

public class LoadUrlInfoTask extends AsyncTask<Void, Void, InfoUrl> {

    private String url;
    private UrlResponse listener;

    public LoadUrlInfoTask(String url, UrlResponse listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected InfoUrl doInBackground(Void... voids) {

        InfoUrl infoUrl = new InfoUrl();

        try {

            if(url !=null && url.length()>4){
                if(!url.startsWith("http://") || !url.startsWith("https://")){
                    url = "http://"+url;
                }
            }else{
                return null;
            }

            URL url = new URL(this.url);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;

            String urlIco = "";
            int index;
            int our;
            while ((inputLine = in.readLine()) != null) {
                Log.w("LoadUrlInfoTask", inputLine);
                if (inputLine.contains("<link rel=\"icon\"") && infoUrl.getUrl_icon()==null) {
                     index = inputLine.indexOf("href") + 6;
                     our = inputLine.indexOf("\"", index);
                    infoUrl.setUrl_icon(inputLine.substring(index, our));
                }else if(inputLine.contains("<title>") && infoUrl.get_title()==null){
                    index = inputLine.indexOf("<title>") + 7;
                    our = inputLine.indexOf("</title>", index);
                    infoUrl.set_title(inputLine.substring(index, our));
                }
                else if(inputLine.contains("<description>") && infoUrl.getDescription()==null){
                    index = inputLine.indexOf("<description>") + 13;
                    our = inputLine.indexOf("</description>", index);
                    infoUrl.set_description(inputLine.substring(index, our));
                }

            }


            in.close();
        } catch (Exception ex) {

            ex.printStackTrace();
            return null;

        }

        return infoUrl;
    }

    @Override
    protected void onPostExecute(InfoUrl s) {
        super.onPostExecute(s);
        if (this.listener != null) {
            this.listener.result(s);
        }
    }
}
