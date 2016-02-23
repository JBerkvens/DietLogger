package com.moridrin.lifelogger.utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jeroenhermkens on 22/02/16.
 */
public class GetAutocomplete extends AsyncTask<String, Void, List<String>> {

    protected List<String> doInBackground(String... args) {
        InputStream is = null;
        int len = 500;
        String contentAsString = null;
        try {
            String params = "?Category=" + args[0];
            URL url = new URL("http://moridrin.com/android/LifeLogger/getAutocomplete.php" + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            contentAsString = readIt(is, len);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentAsString != null) {
            return Arrays.asList(contentAsString.split(";"));
        } else {
            return null;
        }
    }

    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
