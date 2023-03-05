/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cescristorey.pmdm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author chawy
 */
public class Murciegalo extends Image{
   TextureRegion dead;
    Animation flying;

    float time = 0;
    float xVelocity = 5f;
    float xInicial = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    boolean isAttacking = false;
    boolean die = false;
    TiledMapTileLayer layer;

    final float GRAVITY = -1.2f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;
    
    public Murciegalo(){
        final float width = 200;
        final float height = 260;
        this.setSize(1, height / width);
        
        TextureRegion[] fly = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            fly[i]  = new TextureRegion(new Texture(Gdx.files.internal("Fly" + i + ".png") ));
        }
        
        flying = new Animation(0.20f, fly[0], fly[1], fly[2], fly[3], fly[4]);
        flying.setPlayMode(Animation.PlayMode.LOOP);
        
        dead = new TextureRegion(new Texture(Gdx.files.internal("deadMurcielago.png")));
        
    }
    
    public Rectangle dimensiones(){
        return new Rectangle(getX(),getY(), getWidth(), getHeight());
    }
    
    @Override
    public void act(float delta) {
    
        time = time + delta;
        
        setPosition(getX() + xVelocity * delta, getY() + yVelocity * delta);
        

        
    }
    
    
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = (TextureRegion) flying.getKeyFrame(time);
        
        if (die == true) {
            frame = dead;   
        }
        
        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } 

        else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
        
    }
}
