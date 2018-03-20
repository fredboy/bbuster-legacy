package ru.fredboy.ballsbuster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import ru.fredboy.ballsbuster.game.GameRenderer;
import ru.fredboy.ballsbuster.game.GameWorld;

public class GameScreen extends ScreenAdapter{

    public enum GameState {
        MENU_MAIN,
        MENU_SCORES,
        PAUSED,
        RESUMED,
        PLAY,
        OVER,
        HINT,
        ABOUT,
    }

    public static GameState state;

    public static boolean firstLaunch;

    private static int WIDTH;
    private static int HEIGHT;

    public static int FPS;

    public static int EASY = 4;
    public static int MEDIUM = 6;
    public static int HARD = 8;
    public static int DIFFICULTY;

    public static IActivityRequestHandler adsHandler;

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    public GameScreen(int width, int height) {
        state = GameState.MENU_MAIN;
        AssetsLoader.load();
        WIDTH = width;
        HEIGHT = height;
        gameWorld = new GameWorld();
        gameRenderer = new GameRenderer(gameWorld);
        Gdx.input.setInputProcessor(new InputHandler(gameRenderer,gameWorld));
        gameWorld.loadScores();
        if (gameWorld.prefs.contains("first")) {
            firstLaunch = false;
        } else {
            firstLaunch = true;
        }
        if (AssetsLoader.useSound) {
            AssetsLoader.GameMusic.setLooping(true);
            AssetsLoader.GameMusic.play();
        }
        adsHandler.showBanner(true);
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    @Override
    public void show() {
        Gdx.app.debug("GameScreen","show");
    }

    private static float acc = 0f;
    @Override
    public void render(float delta) {
        acc += delta;
        if (acc < 1/60f) {
            return;
        }
        FPS = (int) (1 / acc);
        if (state == GameState.PLAY) {
            gameWorld.update(acc);
        }
        gameRenderer.render();
        acc = 0f;
    }

    @Override
    public void pause() {
        Gdx.app.debug("GameScreen","pause");
        if (state == GameState.PLAY) {
            state = GameState.PAUSED;
        }
    }

    @Override
    public void resume() {
        Gdx.app.debug("GameScreen","resume");
    }

    @Override
    public void hide() {
        Gdx.app.debug("GameScreen","hidden");
    }

    @Override
    public void dispose() {
        Gdx.app.debug("GameScreen","dispose");
    }

}
