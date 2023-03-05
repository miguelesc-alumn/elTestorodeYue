/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cescristorey.pmdm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
public class TioShuriken extends Image{
    
    TextureRegion dead, stand;
    Animation attacking;

    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    boolean isAttacking = false;
    float intervalo = 5f;
    boolean pegar = false;
    boolean muerto = false;
    Shuriken shurikender, shurikenizq;
    TiledMapTileLayer layer;

    final float GRAVITY = -1.2f;
    final float MAX_VELOCITY = 10f;
    
    public TioShuriken(){
        final float width = 200;
        final float height = 260;
        this.setSize(1, height / width);
        

        TextureRegion[] attack = new TextureRegion[9];
        
        
        for (int i = 0; i < 9; i++) {
            attack[i] = new TextureRegion(new Texture(Gdx.files.internal("shuriken-dude" + i + ".png") ));
        }
       // TextureRegion[][] grid = TextureRegion.(koalaTexture, (int) width, (int) height);
        dead = new TextureRegion(new Texture(Gdx.files.internal("shurikenDudeDead.png")));
        stand = new TextureRegion(new Texture(Gdx.files.internal("shuriken-dude0.png")));
        
        
        attacking = new Animation (0.40f,attack[0],attack[1], attack[2] ,attack[3], attack[4], attack[5], attack[6], attack[7] ,attack[8]);
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

    void pegar(boolean atacar){
        this.pegar = atacar;
    
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = stand;
        
        if (pegar) {
            shurikender = new Shuriken();
            shurikenizq = new Shuriken();
            frame = (TextureRegion) attacking.getKeyFrame(time); 
            if (isFacingRight) {
                shurikender.xVelocity= 4;
            } 

            else {
                shurikenizq.xVelocity= -4;
            }
        }
        
        if (muerto == true) {
            frame = dead;
        }
        
       
       
        if (isFacingRight) {
            //shuriken.xVelocity= 4;
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } 

        else {
            
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
        this.pegar= false;
    }
}
