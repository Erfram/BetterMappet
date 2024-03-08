package llama.bettermappet.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IllegalSkin {
    public static SkinData getSkinlink(String url, String nickname){
        String json = readJsonFromUrl(url + nickname);
        Gson gson = new Gson();

        return gson.fromJson(json, SkinObject.class).SKIN;
    }

    public static String readJsonFromUrl(String url) {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class SkinObject {
        private SkinData SKIN;
    }
    public static class SkinData {
        private String url;
        private SkinMetadata metadata;

        public String getURL() {
            return url;
        }

        public String getType() {
            return metadata.model;
        }
    }

    public static class SkinMetadata {
        private String model;
    }
}
