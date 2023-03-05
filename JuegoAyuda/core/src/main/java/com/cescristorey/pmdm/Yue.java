package com.cescristorey.pmdm;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Yue extends Image {
    TextureRegion dead;
    Animation runing, stand,jumping, falling, attacking;

    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    boolean isAttacking = false;
    //boolean pegar = false;
    boolean muerta = false;
    TiledMapTileLayer layer;

    final float GRAVITY = -1.2f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Yue() {
        final float width = 200;
        final float height = 260;
        this.setSize(1, height / width);
        
        TextureRegion[] idle = new TextureRegion[4]; 
        TextureRegion[] run = new TextureRegion[8];
        TextureRegion[] jump = new TextureRegion[4];
        TextureRegion[] fall = new TextureRegion[2];
        TextureRegion[] attack = new TextureRegion[8];
        
        for (int i = 0; i < 4; i++) {
            idle[i]  = new TextureRegion(new Texture(Gdx.files.internal("idle" + i + ".png") )); 
        }
        
        for (int i = 0; i < 8; i++) {
            run[i]  = new TextureRegion(new Texture(Gdx.files.internal("run" + i + ".png") )); 
        }
        
        for (int i = 0; i < 3; i++) {
            jump[i] = new TextureRegion(new Texture(Gdx.files.internal("jump" + i + ".png")));
        }
        
        for (int i = 0; i < 2; i++) {
            fall[i] = new TextureRegion(new Texture(Gdx.files.internal("fall" + i + ".png")));
        }
        
        for (int i = 4; i <= 7; i++) {
            attack[i] = new TextureRegion(new Texture(Gdx.files.internal("attack" + i + ".png") ));
        }

        dead = new TextureRegion(new Texture(Gdx.files.internal("hurt.png")));
        
        falling = new Animation(0.30f, fall[0], fall[1]);
        falling.setPlayMode(Animation.PlayMode.LOOP);
        
        stand = new Animation(0.30f, idle[0], idle[1], idle[2], idle[3]);
        stand.setPlayMode(Animation.PlayMode.LOOP);
        
        jumping = new Animation(0.25f, jump[0], jump[1], jump[2]);
        jumping.setPlayMode(Animation.PlayMode.LOOP);
        
        runing = new Animation(0.15f, run[0], run[1], run[2], run[4], run[5], run[6], run[7]);
        runing.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        
        attacking = new Animation (0.20f, attack[4], attack[5], attack[6], attack[7]);
        attacking.setPlayMode(Animation.PlayMode.LOOP);
    }
    public Rectangle dimensiones(){
        return new Rectangle(getX(),getY(), getWidth(), getHeight());
    }
    @Override
    public void act(float delta) {
        time = time + delta;
        if (muerta == false) {
            boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (canJump) {
                    yVelocity = yVelocity + MAX_VELOCITY * 2.7f;
                }
                canJump = false;
            }

            boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xVelocity = -1 * MAX_VELOCITY;
                isFacingRight = false;
            }

            boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                xVelocity = MAX_VELOCITY;
                isFacingRight = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.F)) {

                isAttacking = true;
            }

            yVelocity = yVelocity + GRAVITY;

            float x = this.getX();
            float y = this.getY();
            float xChange = xVelocity * delta;
            float yChange = yVelocity * delta;

            if (canMoveTo(x + xChange, y, false) == false) {
                xVelocity = xChange = 0;
            }

            if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
                canJump = yVelocity < 0;
                yVelocity = yChange = 0;
            }

            this.setPosition(x + xChange, y + yChange);

            xVelocity = xVelocity * DAMPING;
            if (Math.abs(xVelocity) < 0.5f) {
                xVelocity = 0;
            }
        }
        else{
            setPosition(getX() + xVelocity * delta, getY() + yVelocity * delta);
        }
        
    }
    
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = (TextureRegion) stand.getKeyFrame(time);
        
        if (muerta == true) {
            frame = dead;
        }
        
        else{
            if (isAttacking) {
                frame = (TextureRegion) attacking.getKeyFrame(time);
            }

            else{

                if (yVelocity > 0) {
                    frame = (TextureRegion) jumping.getKeyFrame(time);
                    if (isFacingRight) {
                        batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
                    } else {
                        batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
                    }
                } 

                else if (yVelocity < 0) {
                    frame = (TextureRegion) falling.getKeyFrame(time);
                }

                else if (xVelocity != 0) {
                    frame = (TextureRegion) runing.getKeyFrame(time);
                } 

                if (xVelocity == 0 && yVelocity == 0) {
                    frame = (TextureRegion) stand.getKeyFrame(time);
                }

            }
        
        isAttacking = false;
        }
        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } 

        else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
}
