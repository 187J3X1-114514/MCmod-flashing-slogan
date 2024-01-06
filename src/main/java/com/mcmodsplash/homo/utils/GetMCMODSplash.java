package com.mcmodsplash.homo.utils;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import java.io.BufferedReader;
import java.net.ConnectException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
public class GetMCMODSplash {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<String> errorTextList = new ArrayList<String>();
    public static String get(boolean online) {
        errorTextList.add("错误：在解析返回信息时出现错误，建议宁更新下版本，说不定就修了（大概）,将使用离线标语");
        errorTextList.add("错误：在获得离线标语时又又又又又又失败了，将使用原版标语（要是再失败建议更新版本，已无语）");
        errorTextList.add("错误：在尝试向MCMOD官网获得标语时失败，宁可以检查下网，将使用离线标语，服务器返回错误码：");
        errorTextList.add("错误：获取标语时发生意外的错误，宁可以检查下网，将使用离线标语，错误内容：");
        errorTextList.add("错误：获取标语时发生意外的错误，将使用离线标语，错误内容：");
        errorTextList.add("错误：在尝试向MCMOD官网获得标语时失败，宁可以检查下网，将使用离线标语。");
        errorTextList.add("错误：在获得离线标语时失败了，将使用原版标语。");
        LOGGER.info("正在尝试获得标语");
        if (online){
            try {
                URL url = new URL("https://www.mcmod.cn");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
                    Pattern pattern = Pattern.compile("<div class=\"shadow\">(.+?)</div>");
                    Matcher matcher = pattern.matcher(htmlContent);
                    if (matcher.find()) {
                        LOGGER.info("标语："+matcher.group(1));
                        return matcher.group(1);
                    }
                    else{
                        LOGGER.error(errorTextList.get(0));
                        String s = SplashFile.get("/assets/mcmodsplash/splash/splash.txt");
                        if (Objects.equals(s, "null")){
                            LOGGER.error(errorTextList.get(1));
                            s = SplashFile.get("/assets/minecraft/texts/splashes.txt");
                        }
                        LOGGER.info("标语："+s);
                        return s;
                    }
                } else {
                    LOGGER.error(errorTextList.get(2));
                    String s = SplashFile.get("/assets/mcmodsplash/splash/splash.txt");
                    if (Objects.equals(s, "null")){
                        LOGGER.error(errorTextList.get(1));
                        s = SplashFile.get("/assets/minecraft/texts/splashes.txt");
                    }
                    LOGGER.info("标语："+s);
                    return s;
                }

            }catch (ConnectException e){
                LOGGER.error(errorTextList.get(5)+e);
                String s = SplashFile.get("/assets/mcmodsplash/splash/splash.txt");
                if (Objects.equals(s, "null")){
                    LOGGER.error(errorTextList.get(1));
                    s = SplashFile.get("/assets/minecraft/texts/splashes.txt");
                }
                LOGGER.info("标语："+s);
                return s;
            }catch (Error e) {
                LOGGER.error(errorTextList.get(4)+e);
                String s = SplashFile.get("/assets/mcmodsplash/splash/splash.txt");
                if (Objects.equals(s, "null")){
                    LOGGER.error(errorTextList.get(1));
                    s = SplashFile.get("/assets/minecraft/texts/splashes.txt");
                }
                LOGGER.info("标语："+s);
                return s;
            } catch (Exception e) {
                LOGGER.error(errorTextList.get(3)+e);
                String s = SplashFile.get("/assets/mcmodsplash/splash/splash.txt");
                if (Objects.equals(s, "null")){
                    LOGGER.error(errorTextList.get(1));
                    s = SplashFile.get("/assets/minecraft/texts/splashes.txt");
                }
                LOGGER.info("标语："+s);
                return s;
            }
        }else {
            try {
                String s = SplashFile.get("/assets/mcmodsplash/splash/splash.txt");
                if (Objects.equals(s, "null")){
                    LOGGER.error(errorTextList.get(6));
                    s = SplashFile.get("/assets/minecraft/texts/splashes.txt");
                }
                LOGGER.info("标语："+s);
                return s;
            }
            catch (Error e){
                LOGGER.error(errorTextList.get(6));
                return SplashFile.get("/assets/minecraft/texts/splashes.txt");
            }catch (Exception er){
                LOGGER.error(errorTextList.get(6));
                return SplashFile.get("/assets/minecraft/texts/splashes.txt");
            }
        }

    }
}

