package com.moridrin.lifelogger.utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jeroenhermkens on 22/02/16.
 */
public class SaveFoodLog extends AsyncTask<String, Void, Boolean> {

    protected Boolean doInBackground(String... args) {
        boolean success = false;
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].replace(" ", "%20");
        }
        InputStream is = null;
        int len = 500;
        String contentAsString = null;
        try {
            String params = "?Category=" + args[0] + "&Name=" + args[1] + "&Amount=" + args[2] + "&Unit=" + args[3];
            URL url = new URL("http://moridrin.com/android/LifeLogger/saveFoodLog.php" + params);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            contentAsString = readIt(is, len);
            if (contentAsString.contains("New record created successfully")) {
                success = true;
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
