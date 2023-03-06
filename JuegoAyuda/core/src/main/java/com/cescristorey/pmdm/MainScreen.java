package com.cescristorey.pmdm;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;

import java.util.ArrayList;


public class MainScreen implements Screen {
    
    MyGdxGame game;
    private float timer = 0f;
    Music musicaEpica;
    private float time = 0;
    private float tiempo = 1000000000;
    private final float ANIMATION_INTERVAL = 5f;
    float lastDropTime = 0f;
    float elapsedTime = 0f;
    int i = 0;
    boolean continuar = true;
    Stage stage;
    TiledMap map;
    
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera , guiCam;
    Yue yue;
    Shuriken shuri;
    ArrayList<Shuriken> vShuri;
    TioShuriken enemigo;
    Murciegalo murciano;
    Funguito setito;
    Rectangle espana; 
    
    int contadorMonedas = 0;

    public MainScreen(MyGdxGame game) {
        this.game = game;
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 800, 480);
        map = new TmxMapLoader().load("sinnombre.tmx");
        final float pixelsPerTile = 32;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
        vShuri = new ArrayList<>();
        musicaEpica = Gdx.audio.newMusic(Gdx.files.internal("musiquita.mp3"));
        musicaEpica.setLooping(true);
        musicaEpica.play();
        espana = new Rectangle(); 
        
        espana.width = 300;
        espana.height = 500;
        espana.setPosition(64, 6);
        
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        
        setito = new Funguito();
        setito.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(setito);
        setito.setPosition(20, 6);
        
        enemigo = new TioShuriken();
        enemigo.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(enemigo);
      
        enemigo.setPosition(10, 6);
        
        shuri = new Shuriken();
        
        
        //stage.addActor(shuri);
        murciano = new Murciegalo();
        murciano.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        murciano.setPosition(20, 10);
        murciano.xInicial = 20;
        stage.addActor(murciano);
        yue = new Yue();
        yue.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(yue);
        yue.setPosition(0, 10);
    }
    

    @Override
    public void render(float delta) {
        
     //   delta = Gdx.graphics.getDeltaTime();
        time += delta;
        timer += delta;
        elapsedTime += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
        
            yue.isAttacking = true;
        
        }
        
        
        murciegaloRango();
        murcielagodead();
        comprobarCaida();
        Enemigovista();
        muerteEnemigo();
        shurikenColisiones();
        setaColision();
        murcielagoColision();
        comprobarMuerte();
        
        if (espana.overlaps(yue.dimensiones())) {
            game.batch.setProjectionMatrix(guiCam.combined);
            stage.act();
            stage.draw();
            this.game.setScreen(new SegundoLvl(game));
            musicaEpica.stop();
        }
        
        time = 0;
        if (timer > 4f && continuar == true && enemigo.pegar == true) {
            timer = 0;
            // Disparar la bala
            shuri = disparar();
        }
        //shuri.setX(shuri.getX() + 4 * Gdx.graphics.getDeltaTime());
        
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (yue.getX() < 17) {
            camera.position.x = 17;
        }
        
        else
            camera.position.x = yue.getX();
        
        camera.update();
      
        

        renderer.setView(camera);
        renderer.render();

        stage.act(delta);
        stage.draw();
        
        
    }
    
    public void murcielagodead(){
    
        if (murciano.dimensiones().overlaps(yue.dimensiones()) && yue.isAttacking == true) {
            
            //System.out.println("aaaaaaaaa");
            murciano.die = true;
            murciano.yVelocity = -10f;
            murciano.xVelocity=0;
            
            if(murciano.getY() <=0 ){
                murciano.remove();
            }
        }
        
    }
    
    public void murcielagoColision(){
    
        if (murciano.dimensiones().overlaps(yue.dimensiones()) && yue.isAttacking == false && murciano.die == false) {
            
            muerteYue();
            
        }
        
    }
    
    public void murciegaloRango(){
    
        if (murciano.getX() >= murciano.xInicial + 5) {
            murciano.xVelocity = -4f;
            murciano.isFacingRight = false;
        }
        
        else if (murciano.getX() <= murciano.xInicial - 5) {
            murciano.xVelocity = 4f;
            murciano.isFacingRight = true;
        }
        
        
    }
    
    
    public void comprobarMuerte(){
    
        if (yue.getY() <= 0) {
            System.out.println("bajkshkhajl");
            yue.remove();
            guiCam.update();
            game.batch.setProjectionMatrix(guiCam.combined);
            stage.act();
            stage.draw();
            this.game.setScreen(new PantallaMuerte(game));
            musicaEpica.stop();
        }
    
    
    }
    
    public void comprobarCaida(){
    
        if (yue.getX() < 0) {
            yue.setX(0);
        }
    
    }
    
    public Shuriken disparar(){
        
        Shuriken shu;
        shu = new Shuriken();
        shu.setPosition(10, 6);
        if (enemigo.isFacingRight) {
            shu.xVelocity = -10f;
        }
        
        else{
            shu.xVelocity = 10f;
        }
        
            System.out.println("yue" + yue.getY());

        if (yue.getY() < 6.2) {
            shu.yVelocity = 0;
            System.out.println("aaaaaa");
        }

        else{
            shu.yVelocity = yue.getY()+6;
        }
        
        vShuri.add(shu);
        
        stage.addActor(vShuri.get(i));
        i++;
        
        return vShuri.get(i-1);
        
    }
    
    public void shurikenColisiones(){
        
        if (shuri.dimensiones().overlaps(yue.dimensiones())&& yue.isAttacking == true && yue.isFacingRight && shuri.xVelocity < 0) {
            shuri.destroyed =true;
            shuri.xVelocity = 0;
            shuri.yVelocity = -10f;
            if (shuri.getY() < 0) {
                shuri.remove();
            }
        }
        
        else if (shuri.dimensiones().overlaps(yue.dimensiones())&& yue.isAttacking == true && yue.isFacingRight == false && shuri.xVelocity > 0) {
            shuri.destroyed =true;
            shuri.xVelocity = 0;
            shuri.yVelocity = -10f;
            if (shuri.getY() < 0) {
                shuri.remove();
            }
        }
        
        else if (shuri.dimensiones().overlaps(yue.dimensiones()) && yue.isAttacking == false && shuri.destroyed == false || shuri.dimensiones().overlaps(yue.dimensiones()) && 
                yue.isFacingRight == true && shuri.xVelocity > 0 || shuri.dimensiones().overlaps(yue.dimensiones()) && yue.isFacingRight == false &&
                shuri.xVelocity < 0 && shuri.destroyed == false) {
            shuri.remove();
            muerteYue();
        }
        
        
        if (shuri.getX() > enemigo.getX() + 20 || shuri.getX() < enemigo.getX() - 20) {
            
            shuri.destroyed =true;
            shuri.xVelocity = 0;
            shuri.yVelocity = -10f;
            if (shuri.getY() < 0) {
                shuri.remove();
            }
            
        }
    }
    
    public void muerteYue(){
        
        yue.muerta = true;
        yue.xVelocity = 0;
        yue.yVelocity = -10f;
        
        
    }
    
    public void setaColision(){
    
        if (yue.dimensiones().overlaps(setito.dimensiones())) {
            setito.pegar = true;
        }
        
        if(yue.dimensiones().overlaps(setito.dimensiones())&& yue.isAttacking == true){
        
            setito.die = true;
            setito.yVelocity = -10f;
            if (setito.getY() < 0) {
                
                setito.remove();
            }
            
        }
        
        if(yue.dimensiones().overlaps(setito.dimensiones())&& yue.isAttacking == false && setito.die == false){
        
            muerteYue();
            
        }
        
    }
    
    public void muerteEnemigo(){
        if (enemigo.dimensiones().overlaps(yue.dimensiones()) && yue.isAttacking == true) {
            continuar = false;
            enemigo.muerto = true;
            enemigo.yVelocity = -10f;
        }
        
        if (enemigo.getY() < 0) {
            enemigo.remove();
        }
    }
    
    public void Enemigovista(){
        if (yue.getX() < enemigo.getX() + 10 && yue.getX() > enemigo.getX() && enemigo.muerto == false) {
            enemigo.isFacingRight = false;
            enemigo.pegar(true);
            continuar = true;
        }
        
        else if (yue.getX() > enemigo.getX() - 5 && yue.getX() < enemigo.getX() && enemigo.muerto == false){
            enemigo.isFacingRight = true;
            enemigo.pegar(true);
            continuar = true;
        }
        
        else{
        
            continuar = false;
            
        }
    
    }
    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        
        if (width > 0 && height > 0) {
            camera.setToOrtho(false, 20 * width / height, 20);
        }
    }

    public void resume() {
    }

    @Override
    public void show() {

    }
}
