/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cescristorey.pmdm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author chawy
 */
public class Rupia extends Image{
    TextureRegion normal;

    public Rupia(){
        final float width = 80;
        final float height = 100;
        this.setSize(1, height / width);
        
        normal = new TextureRegion(new Texture(Gdx.files.internal("rupia.png"))); 
    }
    
    public Rectangle dimensiones(){
        return new Rectangle(getX(),getY(), getWidth(), getHeight());
    }
    @Override
    public void act(float delta) {
    }
    
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = normal;
        batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
