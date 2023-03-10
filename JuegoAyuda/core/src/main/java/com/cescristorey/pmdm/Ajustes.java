/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cescristorey.pmdm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static com.cescristorey.pmdm.MyGdxGame.puntos;
import static java.lang.System.exit;

/**
 *
 * @author chawy
 */
public class Ajustes implements Screen{

    MyGdxGame game;
    OrthographicCamera guiCam;
    Rectangle nextBounds;
    Vector3 touchPoint;
    Rectangle salir, volver, controles ;
    
    Texture flecha;
    Table testTable;
    Stage stage;
    int nivel; 
    
    public Ajustes(MyGdxGame game, int nivel){
        this.game = game;
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 800, 480);
        testTable = new Table();
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("menuPausa.png"))));
        testTable.setFillParent(true);
        testTable.setDebug(true);
        stage = new Stage();
        
        stage.addActor(testTable);
        nextBounds = new Rectangle(500, 0, 64, 64);
        salir = new Rectangle(350, 100, 300, 30);
        volver = new Rectangle(350, 250, 300, 40);
        controles = new Rectangle(350, 190, 300, 60);
        this.nivel = nivel; 
        touchPoint = new Vector3(); 
    
    }
    
    public void update(){
        
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        stage.act();
        stage.draw();
        this.game.batch.begin();
        this.game.batch.end();
        if (Gdx.input.justTouched()){
            
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (salir.contains(touchPoint.x, touchPoint.y)) {
                    
                    exit(0);
            }
            if (volver.contains(touchPoint.x, touchPoint.y)) {
                  if(nivel == 10){
                      game.setScreen(new Nivel1Facil(game));
                      dispose(); 
                  }
                  if(nivel == 11){
                      game.setScreen(new Nivel1Dificil(game));
                      dispose(); 
                  }
                  if(nivel == 21){
                      game.setScreen(new SegundoLvl(game));
                      dispose(); 
                  }
                  if(nivel == 22){
                      game.setScreen(new SegundoLvlDificil(game));
                      dispose(); 
                  }
            }
            if (controles.contains(touchPoint.x, touchPoint.y)) {
                   game.setScreen(new Controles(game, nivel));
                   dispose(); 
            }
        
        }


    }

    public void draw() {

    }

    @Override
    public void render(float delta) {
        draw();
        update();
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
    
}
