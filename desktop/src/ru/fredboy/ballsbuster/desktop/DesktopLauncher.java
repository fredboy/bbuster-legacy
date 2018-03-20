package ru.fredboy.ballsbuster.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.fredboy.ballsbuster.BallsBuster;
import ru.fredboy.ballsbuster.IActivityRequestHandler;

public class DesktopLauncher implements IActivityRequestHandler{

    @Override
    public void showInterstitial() {
    }
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Disasterman";
		config.width = 960;
		config.height = 540;
//		config.fullscreen = true;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		config.resizable = false;
		new LwjglApplication(new BallsBuster(new DesktopLauncher()), config);
	}

	@Override
	public void showBanner(boolean show) {
	}
}
