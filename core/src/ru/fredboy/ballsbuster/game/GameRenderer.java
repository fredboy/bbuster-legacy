package ru.fredboy.ballsbuster.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import ru.fredboy.ballsbuster.AssetsLoader;
import ru.fredboy.ballsbuster.BallsBuster;
import ru.fredboy.ballsbuster.GameScreen;

public class GameRenderer {

    public Array<MenuButton> buttons = new Array();

    public int scoresScroll;

    private GameWorld gameWorld;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    public int resumeTime = 300;

    private int bg_y = 0;

    public GameRenderer(GameWorld gameWorld){
        scoresScroll=0;
        buttons.clear();
        //main
        buttons.add(new MenuButton("PLAY",GameScreen.getWidth()/2-150,120));
        buttons.add(new MenuButton("SCORES",GameScreen.getWidth()/2-150,220));
        buttons.add(new MenuButton("QUIT",GameScreen.getWidth()/2-150,320));
        //scores
        buttons.add(new MenuButton("BACK",GameScreen.getWidth()/2-150,
                GameScreen.getHeight()-100));
        buttons.add(new MenuButton("LEADERS",GameScreen.getWidth()/2+2,
                GameScreen.getHeight()-100));
        //pause
        buttons.add(new MenuButton("CONTINUE",GameScreen.getWidth()/2-150,220));
        buttons.add(new MenuButton("MAIN MENU",GameScreen.getWidth()/2-150,320));
        //hint
        buttons.add(new MenuButton("OK",GameScreen.getWidth()/2-150,GameScreen.getHeight()/2));
        //about
        buttons.add(new MenuButton("BACK",GameScreen.getWidth()/2-150,
                GameScreen.getHeight()-100));

        this.gameWorld = gameWorld;
        camera = new OrthographicCamera();
        camera.setToOrtho(true,GameScreen.getWidth(),GameScreen.getHeight());
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        setClearColor(0,0,0);
    }

    private void setClearColor(int r, int g, int b) {
        Gdx.gl.glClearColor(r/255.0f,g/255.0f,b/255.0f,1f);
    }

    private void clear() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void setColor(int r,int g,int b) {
        shapeRenderer.setColor(r/255.0f, g/255.0f, b/255.0f, 1f);
    }

    private void setFontColor(int r,int g,int b){
        AssetsLoader.Moonhouse.setColor(r/255.0f, g/255.0f, b/255.0f, 1f);
    }

    private void fillRect(int x,int y,int w,int h) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x,y,w,h);
        shapeRenderer.end();
    }

    private void drawString(String str,int x,int y,boolean roboto) {
        spriteBatch.begin();
        spriteBatch.enableBlending();
        if (roboto) AssetsLoader.Roboto.draw(spriteBatch,str,x,y);
            else AssetsLoader.Moonhouse.draw(spriteBatch,str,x,y);
        spriteBatch.end();
    }

    private void drawString(String str,int x,int y) {
        spriteBatch.begin();
        spriteBatch.enableBlending();
        //AssetsLoader.Roboto.draw(spriteBatch,str,x,y);
        AssetsLoader.Moonhouse.draw(spriteBatch,str,x,y);
        spriteBatch.end();
    }

    private void drawSprite(Sprite sprite, float x, float y) {
        spriteBatch.begin();
        spriteBatch.enableBlending();
        sprite.setPosition(x,y);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    private void drawSprite(Sprite sprite, float x, float y, float rot) {
        spriteBatch.begin();
        spriteBatch.enableBlending();
        sprite.setPosition(x,y);
        sprite.setRotation(rot);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    private void drawBackGround() {
        clear();
        drawSprite(AssetsLoader.Background,0,bg_y);
        drawSprite(AssetsLoader.Background,0,bg_y-AssetsLoader.Background.getHeight());
        bg_y++;
        if (bg_y-AssetsLoader.Background.getHeight()>=0) bg_y=0;
    }

    private void drawGameOver() {
        drawBackGround();
        setFontColor(0,127,255);
        drawString("GAME OVER!",
                GameScreen.getWidth()/2-AssetsLoader.getStringWidth("GAME OVER!")/2,
                GameScreen.getHeight()/2-AssetsLoader.getFontHeight());
        drawString("SCORE:"+gameWorld.score,
                GameScreen.getWidth()/2-AssetsLoader.getStringWidth("SCORE:"+gameWorld.score)/2,
                GameScreen.getHeight()/2);
        if (gameWorld.score>gameWorld.scores.get(gameWorld.scores.size-2)) {
            drawString("NEW HIGH SCORE!",
                    GameScreen.getWidth()/2-AssetsLoader.getStringWidth("NEW HIGH SCORE")/2,
                    GameScreen.getHeight()/2+AssetsLoader.getFontHeight());
        }
    }

    private void drawScores() {
        drawBackGround();
        drawSprite(AssetsLoader.GameLogo,0,0);
        setFontColor(0,127,255);
        drawString("YOUR SCORES",
                GameScreen.getWidth()/2-AssetsLoader.getStringWidth("YOUR SCORES")/2,
                (int)AssetsLoader.GameLogo.getHeight());
        for (int i=9; i>=0; i--) {
            if (i<gameWorld.scores.size) {
                setFontColor(0,127,255);
                drawString((gameWorld.scores.size-i)+".",
                        GameScreen.getWidth()/2-AssetsLoader.getStringWidth("YOUR SCORES")/2,
                        (int)AssetsLoader.GameLogo.getHeight()+
                                AssetsLoader.getFontHeight()*(gameWorld.scores.size-i));
                drawString(""+gameWorld.scores.get(i),
                        GameScreen.getWidth()/2+AssetsLoader.getStringWidth("YOUR SCORES")/2-AssetsLoader.getStringWidth(gameWorld.scores.get(i)+""),
                        (int)AssetsLoader.GameLogo.getHeight()+
                                AssetsLoader.getFontHeight()*(gameWorld.scores.size-i));
            }
        }
        for (int i=3;i<4/*5*/;i++) {
            drawSprite(AssetsLoader.Button.get(buttons.get(i).getType()),
                    buttons.get(i).getX(),buttons.get(i).getY());
            switch (buttons.get(i).getType()) {
                case 0:
                    setFontColor(64,64,64);
                    break;
                default:
                    setFontColor(255,255,255);
            }
            drawString(buttons.get(i).getText(),
                    buttons.get(i).getX()+150-AssetsLoader.getStringWidth(buttons.get(i).getText())/2,
                    buttons.get(i).getY()+40-AssetsLoader.getFontHeight()/2);
        }
    }

    private void drawAbout() {
        drawBackGround();
        drawSprite(AssetsLoader.GameLogo,0,0);
        AssetsLoader.Roboto.setColor(0f,0.5f,1f, 1f);
        int x = GameScreen.getWidth()/2-150;
        drawString("DISASTERMAN "+ BallsBuster.VERSION,
                x,(int)AssetsLoader.GameLogo.getHeight(),true);
        drawString("Copyright 2018 fred-boy",
                x,(int)AssetsLoader.GameLogo.getHeight()+AssetsLoader.getRobotoHeight()+4,true);
        drawString("Special Thanks:\n"+
                        "Blender Foundation\n"+
                        "Darklighter Designs\n"+
                        "HorrorPen\n"+
                        "Justin Nichol\n"+
                        "www.opengameart.org",
                x,(int)AssetsLoader.GameLogo.getHeight()+AssetsLoader.getRobotoHeight()*4,true);
        for (int i=8;i<9;i++) {
            drawSprite(AssetsLoader.Button.get(buttons.get(i).getType()),
                    buttons.get(i).getX(),buttons.get(i).getY());
            switch (buttons.get(i).getType()) {
                case 0:
                    setFontColor(64,64,64);
                    break;
                default:
                    setFontColor(255,255,255);
            }
            drawString(buttons.get(i).getText(),
                    buttons.get(i).getX()+150-AssetsLoader.getStringWidth(buttons.get(i).getText())/2,
                    buttons.get(i).getY()+40-AssetsLoader.getFontHeight()/2);
        }
    }

    private void drawMenu() {
        drawBackGround();
        drawSprite(AssetsLoader.GameLogo,0,0);
        for (int i=0;i<3;i++) {
            drawSprite(AssetsLoader.Button.get(buttons.get(i).getType()),
                    buttons.get(i).getX(),buttons.get(i).getY());
            switch (buttons.get(i).getType()) {
                case 0:
                    setFontColor(64,64,64);
                    break;
                default:
                    setFontColor(255,255,255);
            }
            drawString(buttons.get(i).getText(),
                    buttons.get(i).getX()+150-AssetsLoader.getStringWidth(buttons.get(i).getText())/2,
                    buttons.get(i).getY()+40-AssetsLoader.getFontHeight()/2);
        }
        if (AssetsLoader.useSound) {
            drawSprite(AssetsLoader.SoundBtn.get(1),
                    GameScreen.getWidth()-96,GameScreen.getHeight()-96);
        } else {
            drawSprite(AssetsLoader.SoundBtn.get(0),
                    GameScreen.getWidth()-96,GameScreen.getHeight()-96);
        }
        drawSprite(AssetsLoader.InfoBtn,0,
                GameScreen.getHeight()-96);
    }

    private void drawGame() {
        drawBackGround();
        for (Ball ball : gameWorld.balls) {
            if (!ball.isExploding() || ball.getExplosionState()<0.32f) {
                drawSprite(AssetsLoader.Planets.get(ball.getSprite()),
                        ball.getX() - 90, ball.getY() - 90);
            }
            if (ball.isExploding()){
                drawSprite((Sprite) AssetsLoader.ExplosionAnim.getKeyFrame(ball.getExplosionState()),
                        ball.getX() - 128, ball.getY() - 128);
                if (AssetsLoader.ExplosionAnim.isAnimationFinished(ball.getExplosionState())) {
                    gameWorld.ballReset(ball);
                }
            }
            if (ball.isBoss()) {
                for (Asteroid ast : ball.asteroids) {
                    if (!ast.isExploding() || ast.getExplosionState()<0.16f) {
                        drawSprite(AssetsLoader.Asteroid, ast.getX() - 21, ast.getY() - 21, ast.getRotation());
                    }
                    if (ast.isExploding()) {
                        drawSprite((Sprite)AssetsLoader.SmallExplosionAnim.getKeyFrame(ast.getExplosionState()),
                                ast.getX()-48, ast.getY()-48);
                        if (AssetsLoader.SmallExplosionAnim.isAnimationFinished(ast.getExplosionState())) {
                            gameWorld.removeAst(ball);
                        }
                    }
                }
            }
        }
        /*
        setFontColor(0,127,255);
        drawString("FPS: "+GameScreen.FPS,0,AssetsLoader.getFontHeight());
        drawString("Score: "+gameWorld.score,0,AssetsLoader.getFontHeight()*2);
        drawString("Missed: "+gameWorld.miss,0,AssetsLoader.getFontHeight()*3);
        drawString("Speed: "+Ball.getSpeed(),0,AssetsLoader.getFontHeight()*4);
        */
        if (GameScreen.state == GameScreen.GameState.PLAY) {
            setColor(192, 192, 192);
            if (gameWorld.miss<=10) fillRect(0, 0, GameScreen.getWidth(), 4);
            if (gameWorld.miss < 8) setColor(0, 127, 255);
            else setColor(255, 0, 0);
            fillRect(0, 0,
                    (int) (GameScreen.getWidth() * ((float) (10 - gameWorld.miss) / 10)),
                    4);
            setFontColor(0, 127, 255);
            drawString("" + gameWorld.score,
                    GameScreen.getWidth() / 2 - AssetsLoader.getStringWidth("" + gameWorld.score) / 2,
                    0);
        }
    }

    private void drawResume() {
        drawGame();
        setFontColor(0,127,255);
        drawString(""+resumeTime/60,
                GameScreen.getWidth()/2,GameScreen.getHeight()/2);
        resumeTime--;
        if (resumeTime/60<=0) GameScreen.state = GameScreen.GameState.PLAY;
    }

    public void drawHint() {
        drawGame();
        setFontColor(0,127,255);
        drawString("TAP PLANETS TO DESTROY THEM",
                GameScreen.getWidth()/2-AssetsLoader.getStringWidth("TAP PLANETS TO DESTROY THEM")/2,
                GameScreen.getHeight()/2-AssetsLoader.getFontHeight()*2);
        for (int i=7;i<8;i++) {
            drawSprite(AssetsLoader.Button.get(buttons.get(i).getType()),
                    buttons.get(i).getX(),buttons.get(i).getY());
            switch (buttons.get(i).getType()) {
                case 0:
                    setFontColor(64,64,64);
                    break;
                default:
                    setFontColor(255,255,255);
            }
            drawString(buttons.get(i).getText(),
                    buttons.get(i).getX()+150-AssetsLoader.getStringWidth(buttons.get(i).getText())/2,
                    buttons.get(i).getY()+40-AssetsLoader.getFontHeight()/2);
        }
    }

    private void drawPause() {
        drawGame();
        setFontColor(0,127,255);
        drawString("PAUSE",GameScreen.getWidth()/2-AssetsLoader.getStringWidth("PAUSE")/2,
                AssetsLoader.getFontHeight()*4);
        for (int i=5;i<7;i++) {
            drawSprite(AssetsLoader.Button.get(buttons.get(i).getType()),
                    buttons.get(i).getX(),buttons.get(i).getY());
            switch (buttons.get(i).getType()) {
                case 0:
                    setFontColor(64,64,64);
                    break;
                default:
                    setFontColor(255,255,255);
            }
            drawString(buttons.get(i).getText(),
                    buttons.get(i).getX()+150-AssetsLoader.getStringWidth(buttons.get(i).getText())/2,
                    buttons.get(i).getY()+40-AssetsLoader.getFontHeight()/2);
        }
    }

    public void render() {
        if (GameScreen.state == GameScreen.GameState.PLAY) {
            drawGame();
        } else if (GameScreen.state == GameScreen.GameState.MENU_MAIN){
            drawMenu();
        } else if (GameScreen.state == GameScreen.GameState.OVER){
            drawGameOver();
        } else if (GameScreen.state == GameScreen.GameState.PAUSED){
            drawPause();
        } else if (GameScreen.state == GameScreen.GameState.RESUMED){
            drawResume();
        } else if (GameScreen.state == GameScreen.GameState.MENU_SCORES){
            drawScores();
        } else if (GameScreen.state == GameScreen.GameState.HINT){
            drawHint();
        } else if (GameScreen.state == GameScreen.GameState.ABOUT){
            drawAbout();
        }
    }

}
