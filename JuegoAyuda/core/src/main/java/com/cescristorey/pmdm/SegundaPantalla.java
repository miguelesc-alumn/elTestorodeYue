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
import java.io.IOException;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SegundaPantalla implements Screen {

    MyGdxGame game;
    OrthographicCamera guiCam;
    Rectangle nextBounds;
    Vector3 touchPoint;
    Rectangle salir, apagar, jugar, ajustes, facil, dificil;
    TextureRegion helpRegion;
    Texture flecha;
    Table testTable;
    Stage stage;
    float x, y; 
    boolean dificultad = false;
    

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
        apagar = new Rectangle(650, 20, 155, 64);
        facil = new Rectangle(250,120,64,64); 
        dificil = new Rectangle(500,150,64,64);
        ajustes = new Rectangle(325, 100, 155, 30);
        touchPoint = new Vector3();
        
        flecha = new Texture(Gdx.files.internal("flecha.png"));
          x= facil.x - 120f; 
          y = facil.y; 
    }

    public void update() throws IOException{
        
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        
        stage.act();
        stage.draw();
        
        
        this.game.batch.begin();
        this.game.batch.draw(flecha, x, y);
        this.game.batch.end();
        
        if (Gdx.input.justTouched()){
            
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (salir.contains(touchPoint.x, touchPoint.y)) {
                    
                    exit(0);
            }
            if(facil.contains(touchPoint.x, touchPoint.y)){
               x= facil.x - 120f; 
               y = facil.y; 
               dificultad = false;
            }
         
            if (jugar.contains(touchPoint.x, touchPoint.y)) {
               
                game.setScreen(new historia1(this.game, dificultad));
                
            }
            if(dificil.contains(touchPoint.x, touchPoint.y)){
                x =  dificil.x -110f; 
                y = dificil.y -25f; 
                dificultad = true;
            }
            if(apagar.contains(touchPoint.x, touchPoint.y)){
                
                apagarpc();
            }
            
        }


    }
    
    public void apagarpc() throws IOException{
            String shutdownCommand;
            String operatingSystem = System.getProperty("os.name");

            shutdownCommand = "shutdown.exe -s -t 0";
	    Runtime.getRuntime().exec(shutdownCommand);
	    System.exit(0);
	   }

    public void draw() {

    }

    @Override
    public void render(float delta) {
        draw();
        try {
            update();
        } catch (IOException ex) {
            Logger.getLogger(SegundaPantalla.class.getName()).log(Level.SEVERE, null, ex);
        }
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
