package io.homo.mcmodsplash.mcmodsplash;

import io.homo.mcmodsplash.mcmodsplash.config.Config;
import io.homo.mcmodsplash.mcmodsplash.utils.CustomSplashRenderer;

import io.homo.mcmodsplash.mcmodsplash.utils.SplashManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCMODSplash
{
	public static final String MOD_ID = "mcmodsplash";
	public static final Logger LOGGER = LogManager.getLogger("MCMODSplash");
	public Config config;
	public SplashManager splashManager;
	public static CustomSplashRenderer currentSplashRenderer;
	private static MCMODSplash instance;
	public static MCMODSplash getInstance() {
		return instance;
	}
	public void init() {
		config = new Config();
		try {
			config.read();
		}catch (Exception e){
			LOGGER.error("读取配置时发生未知错误 {}",e.toString());
			config.write();
		}
		instance = this;
		splashManager = new SplashManager();
	}

	public CustomSplashRenderer getDefaultSplash(){
		return splashManager.MINECRAFT_SPLASH.getRandomSplash();
	}
}
