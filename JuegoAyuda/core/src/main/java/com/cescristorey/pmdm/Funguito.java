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
public class Funguito extends Image{
    TextureRegion dead;
    Animation runing, stand,jumping, falling, attacking;

    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    boolean isAttacking = false;
    boolean pegar = false;
    TiledMapTileLayer layer;
    boolean die = false;
    final float GRAVITY = -1.2f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;
    
    public Funguito(){
    
        final float width = 200;
        final float height = 260;
        this.setSize(1, height / width);
        
        TextureRegion[] idle = new TextureRegion[3]; 
        TextureRegion[] attack = new TextureRegion[10];
        
        for (int i = 0; i < 3; i++) {
            idle[i]  = new TextureRegion(new Texture(Gdx.files.internal("seta" + i + ".png") )); 
        }
        
        for (int i = 3; i < 8; i++) {
            attack[i]  = new TextureRegion(new Texture(Gdx.files.internal("seta" + i + ".png") )); 
        }
        
        dead = new TextureRegion(new Texture(Gdx.files.internal("setaMuerta.png"))); 
        
        stand = new Animation(0.20f, idle[0], idle[1], idle[2]);
        stand.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        
        attacking = new Animation(0.30f, attack[3], attack[4], attack[5], attack[6], attack[7]);
        attacking.setPlayMode(Animation.PlayMode.LOOP);
        
    }
    
    
    public Rectangle dimensiones(){
        return new Rectangle(getX(),getY(), getWidth(), getHeight());
    }
    @Override
    public void act(float delta) {
        time += delta;
        setPosition(getX() + xVelocity * delta, getY() + yVelocity * delta);
        
    }
    
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = (TextureRegion) stand.getKeyFrame(time);
        
        if (die == true) {
            frame = dead;   
        }
        
        if (pegar == true ) {
            frame = (TextureRegion) attacking.getKeyFrame(time);
        }
        
        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } 

        else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
        pegar = false;
        
    }
}
