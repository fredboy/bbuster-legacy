package ru.fredboy.ballsbuster.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import ru.fredboy.ballsbuster.AssetsLoader;
import ru.fredboy.ballsbuster.GameScreen;

public class GameWorld {

    public static RandomXS128 rand = new RandomXS128();

    private int maxPlanets = GameScreen.DIFFICULTY*2;

    public Array<Ball> balls = new Array();

    public Array<Integer> scores = new Array();

    public int score = 0;
    public int miss = 0;

    public Preferences prefs;

    public void loadScores() {
        prefs = Gdx.app.getPreferences("Disasterman");
        scores.clear();
        for (int i=0; i<8; i++) {
            if (!prefs.contains("scores"+i)) {
                scores.add(prefs.getInteger("scores"+i));
            } else {
                scores.add(prefs.getInteger("scores"+i));
            }
        }
        scores.sort();
        if (!prefs.contains("sound")) {
            AssetsLoader.useSound = true;
        } else {
            AssetsLoader.useSound = prefs.getBoolean("sound");
        }
    }

    public void saveScores() {
        prefs = Gdx.app.getPreferences("Disasterman");
        prefs.clear();
        for (int i=0; i<8; i++) {
            if (i<scores.size) prefs.putInteger("scores"+i,scores.get(i));
                else prefs.putInteger("scores"+i,0);
        }
        prefs.flush();
    }

    public void newGame() {
        GameScreen.DIFFICULTY = GameScreen.MEDIUM;
        maxPlanets = GameScreen.DIFFICULTY*2;
        score = 0;
        miss = 0;
        balls.clear();
        Ball.reset();
        rand.setSeed(TimeUtils.millis());
        for (int i=0; i<4; i++) {
            addBall(false);
        }
    }

    public void ballReset(Ball ball) {
        ball.resetExplosionState();
        ball.setExploding(false);
        ball.setY(-rand.nextInt((int)ball.getCircle().radius*3)-(int)ball.getCircle().radius);
        float x = rand.nextInt(6);
        while (!ball.isBoss() && /*bossExists() &&*/ (x==2 || x==3)) {
            x = rand.nextInt(6);
        }
        if (ball.isBoss()) {
            ball.addAsteroids();
            x=2.5f;
            ball.setY(-96);
        }
        ball.setX(x*120+180);
        if (!ball.isBoss()) {
            boolean collides = true;
            do {
                for (int i = 0; i < balls.size; i++) {
                    if (Intersector.overlaps(ball.getCircle(), balls.get(i).getCircle()) &&
                            ball != balls.get(i)) {
                        collides = true;
                        ball.setY(-rand.nextInt((int)ball.getCircle().radius*3)-(int)ball.getCircle().radius);
                        x = rand.nextInt(6);
                        while (/*bossExists() && */(x == 2 || x == 3)) {
                            x = rand.nextInt(6);
                        }
                        ball.setX(x * 120 + 180);
                        break;
                    }
                    collides = false;
                }
            } while (collides);
        }
        ball.setSprite(rand.nextInt(18));
    }

    public void removeAst(Ball ball) {
        ball.asteroids.removeIndex(ball.asteroids.size-1);
        //if (ball.asteroids.size<=0) ball.setBoss(false);
    }

    public void onClick(int x, int y) {
        for (int i=balls.size-1; i>=0; i--) {
            if (balls.get(i).getCircle().contains(x,y) && !balls.get(i).isExploding()) {
                if (balls.get(i).asteroids.size==0 || balls.get(i).allAsteroidsExploding()) {
                    if (balls.get(i).isBoss()) {
                        balls.get(i).setBoss(false);
                        balls.get(i).asteroids.clear();
                        miss=0;
                    }
                    balls.get(i).setExploding(true);
                    balls.get(i).resetExplosionState();
                    if (AssetsLoader.useSound) AssetsLoader.ExplosionSound.play();
                    score++;
                    if (score % 50 == 0) {
                        if (balls.size < maxPlanets && score % 100 == 0) addBall(true);
                            else balls.get(i).setBoss(true);
                    }
                } else {
                    for (int j=balls.get(i).asteroids.size-1; j>=0; j--) {
                        if (!balls.get(i).asteroids.get(j).isExploding()) {
                            balls.get(i).asteroids.get(j).setExploding(true);
                            balls.get(i).asteroids.get(j).resetExplosionState();
                            if (AssetsLoader.useSound) AssetsLoader.ExplosionSound.play(1);
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    public void addBall(boolean boss) {
        balls.add(new Ball(boss));
        ballReset(balls.get(balls.size-1));
    }

    public void update(float delta) {
        for (int i=0;i<balls.size;i++) {
            for (Asteroid ast : balls.get(i).asteroids) {
                ast.updateExplosionState(delta);
            }
            if (!balls.get(i).isExploding()) {
                balls.get(i).updatePos();
            }
            balls.get(i).updateExplosionState(delta);
            if (balls.get(i).getY() > GameScreen.getHeight()+balls.get(i).getSize()) {
                balls.get(i).setBoss(false);
                balls.get(i).asteroids.clear();
                miss++;
                if (miss<=10) ballReset(balls.get(i)); else balls.removeIndex(i);
                if (miss>10 && balls.size==0){
                    GameScreen.state = GameScreen.GameState.OVER;
                    GameScreen.adsHandler.showBanner(true);
                    GameScreen.adsHandler.showInterstitial();
                    if (scores.size<8) scores.add(score);
                        else scores.set(0,score);
                    scores.sort();
                    saveScores();

                }
            }
        }
        Ball.accelerate(delta);
    }

}
