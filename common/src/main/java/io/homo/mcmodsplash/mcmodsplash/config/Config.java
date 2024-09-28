package io.homo.mcmodsplash.mcmodsplash.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public String MCMOD_URL = "https://www.mcmod.cn";
    public String SPLASH_REGEX = "<div class=\"shadow\">(.+?)</div>";
    public int MCMOD_TIMEOUT = 1000;
    public boolean obtainSplashOnline = true;
    public boolean colourfulSplash = false;
    public int splashChangeInterval = -1;

    public static final Path configPath = Path.of(Minecraft.getInstance().gameDirectory.toString(),"config","mcmodsplash.json");
    public void write(){
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(configPath.toString()), StandardCharsets.UTF_8)) {
            osw.write(build());
        } catch (IOException e) {
            MCMODSplash.LOGGER.error("配置写入失败 {}",e.toString());
        }
    }

    public void read(){
        if (!exists()) write();
        String text = readText();
        if (text == null) return;
        Gson gson = new Gson();
        JsonElement json = gson.fromJson(text, JsonElement.class);
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            MCMOD_URL = getJsonElementString(jsonObject,"mcmod_url",MCMOD_URL);
            SPLASH_REGEX = getJsonElementString(jsonObject,"splash_regex",SPLASH_REGEX);
            MCMOD_TIMEOUT = getJsonElementInt(jsonObject,"mcmod_timeout",MCMOD_TIMEOUT);
            obtainSplashOnline = getJsonElementBoolean(jsonObject,"obtain_splash_online",obtainSplashOnline);
            colourfulSplash = getJsonElementBoolean(jsonObject,"colourful_splash",colourfulSplash);
            splashChangeInterval = getJsonElementInt(jsonObject,"splash_change_interval",splashChangeInterval);
        }
    }

    private boolean getJsonElementBoolean(JsonObject jsonObject, String name, boolean d){
        return jsonObject.get(name) != null ? jsonObject.get(name).getAsBoolean() : d;
    }

    private int getJsonElementInt(JsonObject jsonObject, String name, int d){
        return jsonObject.get(name) != null ? jsonObject.get(name).getAsInt() : d;
    }

    private String getJsonElementString(JsonObject jsonObject, String name, String d){
        return jsonObject.get(name) != null ? jsonObject.get(name).getAsString() : d;
    }

    public boolean exists(){
        return configPath.toFile().isFile();
    }

    public String build(){
        Map<String, Object> config = new HashMap<>();
        config.put("mcmod_url", MCMOD_URL);
        config.put("splash_regex", SPLASH_REGEX);
        config.put("mcmod_timeout", MCMOD_TIMEOUT);
        config.put("obtain_splash_online", obtainSplashOnline);
        config.put("colourful_splash", colourfulSplash);
        config.put("splash_change_interval", splashChangeInterval);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();
        Gson gson = gsonBuilder.create();
        return gson.toJson(config);
    }

    private String readText() {
        try (BufferedReader br = new BufferedReader(new FileReader(configPath.toString(), StandardCharsets.UTF_8))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonBuilder.append(line).append("\n");
            }
            return jsonBuilder.toString();
        } catch (IOException e) {
            MCMODSplash.LOGGER.error("读取配置文件失败 {}", e.toString());
        }
        return null;
    }

}
