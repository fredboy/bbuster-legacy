package ru.fredboy.ballsbuster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import de.golfgl.gdxgamesvcs.IGameServiceClient;
//import de.golfgl.gdxgamesvcs.IGameServiceListener;
//import de.golfgl.gdxgamesvcs.NoGameServiceClient;

public class BallsBuster extends Game {//implements IGameServiceListener {

	public static final String VERSION = "v1.1";

	//public static IGameServiceClient GS_CLIENT;

	private static final int LOG_LEVEL = Gdx.app.LOG_NONE;

	private int width,height;

	public BallsBuster(IActivityRequestHandler handler) {
		if (handler!=null) {
			GameScreen.adsHandler = handler;
		}
	}

	@Override
	public void create () {
		Gdx.app.setLogLevel(LOG_LEVEL);
		Gdx.app.debug("BallsBuster","create");
		width = 960;
		height = (int)(960/((float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight()));
		setScreen(new GameScreen(width,height));
        /*
		if (GS_CLIENT == null)
			GS_CLIENT = new NoGameServiceClient();
		GS_CLIENT.setListener(this);
		GS_CLIENT.resumeSession();
		GS_CLIENT.logIn();
		*/
	}

	@Override
	public void pause() {
		super.pause();
		//GS_CLIENT.pauseSession();
	}

	@Override
	public void resume() {
		super.resume();
		//GS_CLIENT.resumeSession();
	}
	
	@Override
	public void dispose () {
		//GS_CLIENT.logOff();
		super.dispose();
	}

	/*
	@Override
	public void gsOnSessionActive() {

	}

	@Override
	public void gsOnSessionInactive() {

	}

	@Override
	public void gsShowErrorToUser(GsErrorType et, String msg, Throwable t) {

	}
	*/
}
