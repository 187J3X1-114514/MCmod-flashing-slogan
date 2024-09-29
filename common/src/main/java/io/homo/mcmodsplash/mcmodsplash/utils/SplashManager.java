package io.homo.mcmodsplash.mcmodsplash.utils;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplashManager {
    public final SplashFile MINECRAFT_SPLASH;
    public final SplashFile MCMOD_SPLASH;

    public SplashManager(){
        MCMOD_SPLASH = new SplashFile("assets/mcmodsplash/splash/splashes.txt");
        MINECRAFT_SPLASH = new SplashFile("assets/minecraft/texts/splashes.txt");
    }

    public static CustomSplashRenderer toSplashRenderer(String text){
        return new CustomSplashRenderer(text);
    }

    public CustomSplashRenderer getOnlineSplash(){
        try{
            URL url = new URL(MCMODSplash.getInstance().config.MCMOD_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(MCMODSplash.getInstance().config.MCMOD_TIMEOUT);
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)); //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                String htmlContent = content.toString();
                Pattern pattern = Pattern.compile(MCMODSplash.getInstance().config.SPLASH_REGEX);
                Matcher matcher = pattern.matcher(htmlContent);
                if (matcher.find()) {
                    MCMODSplash.LOGGER.info("从MCMOD主页获取标语：{}", matcher.group(1));
                    return toSplashRenderer(matcher.group(1));
                }
                else{
                    MCMODSplash.LOGGER.warn("无法从MCMOD主页提取标语，请联系作者");
                    return null;
                }
            }else {
                MCMODSplash.LOGGER.warn("无法从MCMOD主页提取标语，连接状态 {}",responseCode);
                return null;
            }
        } catch (ProtocolException e) {
            MCMODSplash.LOGGER.warn("无法从MCMOD主页获取标语，错误：{}",e.toString());
        } catch (MalformedURLException e) {
            MCMODSplash.LOGGER.error("MCMOD主页网址有误，错误：{}",e.toString());
        } catch (IOException e) {
            MCMODSplash.LOGGER.warn("从MCMOD主页获取标语失败，错误：{}",e.toString());
        }
        return null;
    }

    public boolean is41(){
        //return true;
        LocalDate dateToCheck = LocalDate.now();
        return dateToCheck.getMonthValue() == 4 && dateToCheck.getDayOfMonth() == 1;
    }

    public CustomSplashRenderer getSplash(){
        if (is41()) return FoolSplash.getRandomSplash();
        CustomSplashRenderer splash = MCMODSplash.getInstance().config.obtainSplashOnline ? getOnlineSplash() : null;
        if (splash == null) splash = MCMOD_SPLASH.getRandomSplash();
        if (splash == null) splash = MCMODSplash.getInstance().getDefaultSplash();
        return splash;
    }

    public static float colorSpeed = 0.15f;
    public static float degreesSpeed = 0.6f;

    public HSLtoRGB.RGBColor getSplashColor(){
        return HSLtoRGB.hsl2Rgb((double) Util.getMillis() * colorSpeed % 360,1.0,0.5);
    }
    public float getSplashDegrees(){
        return (float) (Math.sin(((double) Util.getMillis() /1000)*degreesSpeed)*360);
    }

    public boolean shouldUseSplashColor(){
        return is41() || MCMODSplash.getInstance().config.colourfulSplash;
    }
    public boolean shouldUseSplashDegrees(){
        return is41();
    }
}
