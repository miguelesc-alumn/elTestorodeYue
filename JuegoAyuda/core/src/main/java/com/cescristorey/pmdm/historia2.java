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

/**
 *
 * @author chawy
 */
public class historia2 implements Screen{
    MyGdxGame game;
    OrthographicCamera guiCam;
    Rectangle nextBounds;
    Vector3 touchPoint;

    TextureRegion helpRegion;
    Texture flecha;
    Table testTable;
    Stage stage;
    boolean dificultad;

    public historia2 (MyGdxGame game, boolean dif) {
        dificultad = dif;
        this.game = game;
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 800, 480);
        testTable = new Table();
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("historia2.png"))));
        testTable.setFillParent(true);
        testTable.setDebug(true);
        stage = new Stage();
        

        stage.addActor(testTable);
        nextBounds = new Rectangle(500, 0, 64, 64);

        touchPoint = new Vector3();

        // flecha = new Texture(Gdx.files.internal("adelante.png"));

    }

    public void update() {
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        stage.act();
        stage.draw();

        if (Gdx.input.justTouched()) {
            
            if (dificultad) {
                    game.setScreen(new Nivel1Dificil(this.game));
                }
                
                else
                    game.setScreen(new Nivel1Facil(this.game));
                
                
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
