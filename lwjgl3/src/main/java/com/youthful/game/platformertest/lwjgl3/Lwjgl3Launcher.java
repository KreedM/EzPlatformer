package com.youthful.game.platformertest.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.youthful.game.platformertest.EzPlatformer;

public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static Lwjgl3Application createApplication() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.setWindowedMode(640, 480);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		
		return new Lwjgl3Application(new EzPlatformer(), config);
	}
}