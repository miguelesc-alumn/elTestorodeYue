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
import static java.lang.System.exit;

public class SegundaPantalla implements Screen {

    MyGdxGame game;
    OrthographicCamera guiCam;
    Rectangle nextBounds;
    Vector3 touchPoint;
    Rectangle salir, apagar, jugar, ajustes;
    TextureRegion helpRegion;
    Texture flecha;
    Table testTable;
    Stage stage;
    

    public SegundaPantalla(MyGdxGame game){
        
        this.game = game;
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 800, 480);
        testTable = new Table();
        testTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("SegundaPantalla.png"))));
        testTable.setFillParent(true);
        testTable.setDebug(true);
        stage = new Stage();
        
        stage.addActor(testTable);
        nextBounds = new Rectangle(500, 0, 64, 64);
        salir = new Rectangle(25, 12, 130, 30);
        jugar = new Rectangle(325, 230, 155, 30);
        apagar = new Rectangle(500, 0, 64, 64);
        touchPoint = new Vector3();

        // flecha = new Texture(Gdx.files.internal("adelante.png"));

    }

    public void update() {
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        stage.act();
        stage.draw();
        
        this.game.batch.begin();
        this.game.font.draw(game.batch,"Ajustes", 333, 242);
        this.game.batch.end();
         if (Gdx.input.justTouched()) {
            //System.out.println("x" + touchPoint.x + "y" + touchPoint.y);
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (salir.contains(touchPoint.x, touchPoint.y)) {
                    
                    exit(0);
            }
        }
         
         
        if (Gdx.input.justTouched()) {
            //System.out.println("x" + touchPoint.x + "y" + touchPoint.y);
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (jugar.contains(touchPoint.x, touchPoint.y)) {
                   //System.out.println("XXX" + touchPoint.x + "YYY" + touchPoint.y);
                   game.setScreen(new MainScreen(this.game));
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
