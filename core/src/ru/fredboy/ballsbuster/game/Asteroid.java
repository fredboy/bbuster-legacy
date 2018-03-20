package ru.fredboy.ballsbuster.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid {
    private static final int RAD = 96;
    
    private Circle circle;
    private Ball ball;
    private float rotation;

    private boolean exploding;
    private float explosionState;

    public Asteroid(Ball ball, float rotation) {
        this.ball = ball;
        circle = new Circle(ball.getX()+ RAD *MathUtils.cos(MathUtils.degRad*rotation),
                ball.getY()+ RAD *MathUtils.sin(MathUtils.degRad*rotation),20);
        this.rotation = rotation;
    }

    public float getX(){
        return circle.x;
    }

    public float getY(){
        return circle.y;
    }

    public float getRotation() {
        return rotation;
    }
    
    public void updatePos() {
        rotation++;
        if (rotation>=360) rotation=0;
        circle.x = ball.getX()+ RAD *MathUtils.cos(MathUtils.degRad*rotation);
        circle.y = ball.getY()+ RAD *MathUtils.sin(MathUtils.degRad*rotation);
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
