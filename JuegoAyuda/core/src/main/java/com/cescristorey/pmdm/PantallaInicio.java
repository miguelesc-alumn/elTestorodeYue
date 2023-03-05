package com.cescristorey.pmdm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaInicio implements Screen {

    MyGdxGame game;
    OrthographicCamera guiCam;
    Rectangle nextBounds;
    Vector3 touchPoint;

    TextureRegion helpRegion;
    Texture flecha;
    Table testTable;
    Stage stage;

    public PantallaInicio(MyGdxGame game) {
        this.game = game;
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 800, 480);
        testTable = new Table();
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("Inicio.png"))));
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
            game.setScreen(new SegundaPantalla(this.game));
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
