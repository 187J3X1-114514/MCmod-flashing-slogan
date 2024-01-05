package com.mcmodsplash.homo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.nio.charset.StandardCharsets;

public class SplashFile {
    public static String get(String f) {
        try {
            List<String> splashlist = new ArrayList<>();
            InputStream splashfileS = SplashFile.class.getResourceAsStream(f);
            if (splashfileS != null){
                InputStreamReader splashfileSR = new InputStreamReader(splashfileS,StandardCharsets.UTF_8); //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
                BufferedReader splashfileB = new BufferedReader(splashfileSR);
                String line;
                while ((line = splashfileB.readLine()) != null) {
                    splashlist.add(line);
                }
                splashfileB.close();
                Random random = new Random();
                return splashlist.get(random.nextInt(splashlist.size()));
            }
            else{
                return "null";
            }
        } catch (IOException e) {
            return "null";
        }

    }
}
