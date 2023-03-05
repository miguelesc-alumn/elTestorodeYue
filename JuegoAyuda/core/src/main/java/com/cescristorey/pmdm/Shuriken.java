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
public class Shuriken extends Image{
    TextureRegion fly;
   
    float xVelocity = 0;
    float yVelocity = 0;
    float time = 0;
    boolean destroyed = false;
    
    TiledMapTileLayer layer;

    final float MAX_VELOCITY = 10f;
    
    public Shuriken () {
    
        final float width = 33;
        final float height = 33;
        this.setSize(1, height / width);
        

        TextureRegion[] attack = new TextureRegion[9];
        
       
       // TextureRegion[][] grid = TextureRegion.(koalaTexture, (int) width, (int) height);
        fly = new TextureRegion(new Texture(Gdx.files.internal("shuriken.png")));
        //stand = new TextureRegion(new Texture(Gdx.files.internal("shuriken-dude0.png")));
        
        
        
    }
    public Rectangle dimensiones(){
        return new Rectangle(getX(),getY(), getWidth(), getHeight());
    }
    @Override
    public void act(float delta) {
        time = time + delta;
        
        setPosition(getX() + xVelocity * delta, getY() + yVelocity * delta);
        
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = fly;
        
         batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
    




