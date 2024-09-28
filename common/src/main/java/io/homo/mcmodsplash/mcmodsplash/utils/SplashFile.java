package io.homo.mcmodsplash.mcmodsplash.utils;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.nio.charset.StandardCharsets;

public class SplashFile {
    public static final ArrayList<String> SplashList = new ArrayList<>();
    private static final Random  random = new Random();

    public SplashFile(String path){
        this.load(path);
    }

    private void load(String f) {
        try {
            InputStream stream = SplashFile.class.getClassLoader().getResourceAsStream(f);
            if (stream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(stream,StandardCharsets.UTF_8); //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    SplashList.add(line);
                }
                bufferedReader.close();
            }else {
                MCMODSplash.LOGGER.warn("标语文件 {} 加载失败",f);
            }
        } catch (IOException e) {
            MCMODSplash.LOGGER.warn("标语文件 {} 加载失败",f);
        }
    }
    public CustomSplashRenderer getRandomSplash(){
        if (SplashList.isEmpty()) return null;
        return new CustomSplashRenderer(SplashList.get(random.nextInt(SplashList.size())));
    }
}
