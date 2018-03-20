package ru.fredboy.ballsbuster.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ru.fredboy.ballsbuster.GameScreen;

public class Ball {

    private static final int BALL_RAD = 60;
    private static float START_SPEED;
    private static float MAX_SPEED;

    private static Vector2 speed ;
    private static  Vector2 acceleration  = new Vector2(0,0.0125f);

    private Circle circle;
    private int sprite;
    private boolean exploding;
    private boolean boss;
    private float explosionState;

    public Array<Asteroid> asteroids = new Array();

    public void addAsteroids() {
        asteroids.clear();
        for (int i=0; i<3; i++) {
            asteroids.add(new Asteroid(this,120*i));
        }
    }

    public Ball(boolean boss) {
        exploding = false;
        explosionState = 0;
        this.sprite = GameWorld.rand.nextInt(18);
        circle = new Circle(0,0,BALL_RAD);
        this.boss = boss;
    }

    public static void reset() {
        START_SPEED = GameScreen.DIFFICULTY;
        MAX_SPEED = GameScreen.DIFFICULTY*2;
        speed = new Vector2(0,START_SPEED);
        acceleration  = new Vector2(0,0.01f);
    }

    public static float getSpeed() {
        return speed.y;
    }

    public void updatePos() {
        if (!boss) setY(getY()+speed.y);
            else setY(getY()+speed.y/2);
        if (boss) {
            for (Asteroid ast : asteroids) {
                ast.updatePos();
            }
        }
    }

    public boolean allAsteroidsExploding() {
        if (!boss) return false;
        for (Asteroid ast : asteroids) {
            if (!ast.isExploding()) return false;
        }
        return true;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public static void accelerate(float delta) {
        speed.add(acceleration.cpy().scl(delta));
        if (speed.y>MAX_SPEED) speed.y=MAX_SPEED;
    }

    public Circle getCircle() {
        return circle;
    }

    public float getX() {
        return circle.x;
    }

    public float getY() {
        return circle.y;
    }

    public float getSize() {
        return circle.radius;
    }

    public void setX(float x) {
        circle.x = x;
    }

    public void setY(float y) {
        circle.y = y;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public float getExplosionState() {
        return explosionState;
    }

    public void resetExplosionState() {
        this.explosionState = 0;
    }

    public void updateExplosionState(float delta) {
        this.explosionState+=delta;
    }
}
