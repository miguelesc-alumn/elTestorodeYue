package com.cescristorey.pmdm;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import static com.cescristorey.pmdm.MyGdxGame.puntos;

import java.util.ArrayList;


public class Nivel1Dificil implements Screen {
    
    MyGdxGame game;
    private float timer = 0f;
    Music musicaEpica;
    Music musicaMuerte;
    Music shurikenSonido;
    Music espadaSonido;
    Music rupiaSonido;
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
    ArrayList<Rupia> vRupia;
    TioShuriken enemigo;
    Murciegalo murciano;
    Funguito setito;
    Rectangle espana, ajustes; 
    Rupia rupi;
    Texture engranaje;
    Vector3 touchPoint;
    int contadorMonedas = 0;

    public Nivel1Dificil(MyGdxGame game) {
        this.game = game;
        puntos = 0;
        touchPoint = new Vector3();
        guiCam = new OrthographicCamera();
        guiCam.setToOrtho(false, 800, 480);
        map = new TmxMapLoader().load("sinnombre.tmx");
        final float pixelsPerTile = 32;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
        stage = new Stage();
        stage.getViewport().setCamera(camera);
        vRupia = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            rupi = new Rupia();
            vRupia.add(rupi);
        }
        
        vRupia.get(0).layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(vRupia.get(0));
        vRupia.get(0).setPosition(8, 15);
        
        vRupia.get(1).layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(vRupia.get(1));
        vRupia.get(1).setPosition(27, 4.2f);
        
        vRupia.get(2).layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(vRupia.get(2));
        vRupia.get(2).setPosition(60, 6);
        
        
        vShuri = new ArrayList<>();
        musicaEpica = Gdx.audio.newMusic(Gdx.files.internal("musiquita.mp3"));
        musicaEpica.setLooping(true);
        musicaEpica.play();
        
        musicaMuerte = Gdx.audio.newMusic(Gdx.files.internal("perderSonido.mp3"));
        musicaMuerte.setLooping(false);
        
        musicaMuerte = Gdx.audio.newMusic(Gdx.files.internal("perderSonido.mp3"));
        musicaMuerte.setLooping(false);
        
        shurikenSonido = Gdx.audio.newMusic(Gdx.files.internal("shurikenSonido.mp3"));
        shurikenSonido.setLooping(false);
        
        espadaSonido = Gdx.audio.newMusic(Gdx.files.internal("sonidoEspada.mp3"));
        espadaSonido.setLooping(false);
        
        rupiaSonido = Gdx.audio.newMusic(Gdx.files.internal("rupiaSonido.mp3"));
        rupiaSonido.setLooping(false);

        
        espana = new Rectangle(); 
        
        espana.width = 300;
        espana.height = 500;
        espana.setPosition(64, 6);
        ajustes = new Rectangle(5, 450, 39,64);
        
        
        
        setito = new Funguito();
        setito.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(setito);
        setito.setPosition(58, 6);
        
        enemigo = new TioShuriken();
        enemigo.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(enemigo);
        
        enemigo.setPosition(10, 6);
        
        shuri = new Shuriken();
        
        
        //stage.addActor(shuri);
        murciano = new Murciegalo();
        murciano.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        murciano.setPosition(44, 4);
        murciano.xInicial = 44;
        stage.addActor(murciano);
        yue = new Yue();
        yue.layer = (TiledMapTileLayer) map.getLayers().get("Principal");
        stage.addActor(yue);
        yue.setPosition(0, 10);
        
        engranaje = new Texture(Gdx.files.internal("engranaje.png"));
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
        comprobarRupias();
        pasarNivel2();
        menuAjustes();
        
        
        time = 0;
        if (timer > 2f && continuar == true && enemigo.pegar == true) {
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
        
        else if (yue.getX() > 48){
        
            camera.position.x = 48;
            
        }
        
        else
            camera.position.x = yue.getX();
        
        camera.update();
      
        

        renderer.setView(camera);
        renderer.render();

        stage.act(delta);
        stage.draw();
        
        this.game.batch.begin();
        this.game.font.draw(this.game.batch, "Puntos: " + puntos, 47, 470);
        this.game.batch.draw(engranaje,7, 450);  
        this.game.batch.end();
    }
    
    public void pasarNivel2(){
        
        if (espana.overlaps(yue.dimensiones())) {
            game.batch.setProjectionMatrix(guiCam.combined);
            stage.act();
            stage.draw();
            this.game.setScreen(new SegundoLvlDificil(game));
            musicaEpica.stop();
        }
    
    }
    
    public void menuAjustes(){
     
        if (Gdx.input.justTouched()){
            
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (ajustes.contains(touchPoint.x, touchPoint.y)) {
                game.batch.setProjectionMatrix(guiCam.combined);
                stage.act();
                stage.draw();
                this.game.setScreen(new Ajustes(game, 11));
                musicaEpica.stop();
            }
        }
    }
    
    public void murcielagodead(){
        if (murciano.die == false) {
            
        
            if (murciano.dimensiones().overlaps(yue.dimensiones()) && yue.isAttacking == true) {

                //System.out.println("aaaaaaaaa");
                murciano.die = true;
                murciano.yVelocity = -10f;
                murciano.xVelocity=0;
                puntos+=200;
                espadaSonido.play();
                if(murciano.getY() <=0 ){
                    murciano.remove();

                }
            }
            
        }
    }
    
    public void murcielagoColision(){
    
        if (murciano.dimensiones().overlaps(yue.dimensiones()) && yue.isAttacking == false && murciano.die == false) {
            
            muerteYue();
            
        }
        
    }
    
    public void murciegaloRango(){
    
        if (murciano.die == false) {
            
        
            if (murciano.getX() >= murciano.xInicial + 10) {
                murciano.xVelocity = -7f;
                murciano.isFacingRight = false;
            }

            else if (murciano.getX() <= murciano.xInicial - 10) {
                murciano.xVelocity = 7f;
                murciano.isFacingRight = true;
            }

            if (murciano.getY() < yue.getY()) {
                murciano.yVelocity = 4f;
            }

            else if (murciano.getY() > yue.getY()) {
                murciano.yVelocity = -4f;
            }

            else if (murciano.getY() == yue.getY()) {
                murciano.yVelocity = 0f;
            }
        
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
            musicaMuerte.play();
        }
    
    
    }
    
    public void comprobarCaida(){
    
        if (yue.getX() < 0) {
            yue.setX(0);
        }
        else if (yue.getX() > 64) {
            yue.setX(64);
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
            shurikenSonido.play();
            if (shuri.getY() < 0) {
                shuri.remove();
            }
        }
        
        else if (shuri.dimensiones().overlaps(yue.dimensiones())&& yue.isAttacking == true && yue.isFacingRight == false && shuri.xVelocity > 0) {
            shuri.destroyed =true;
            shuri.xVelocity = 0;
            shuri.yVelocity = -10f;
            shurikenSonido.play();
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
        musicaEpica.pause();
        musicaMuerte.play();
        
    }
    
    public void setaColision(){
        if (setito.die == false) {
            
        
            if (yue.dimensiones().overlaps(setito.dimensiones())) {
                setito.pegar = true;
            }

            if(yue.dimensiones().overlaps(setito.dimensiones())&& yue.isAttacking == true){

                setito.die = true;
                setito.yVelocity = -10f;
                puntos+=150;
                espadaSonido.play();
                if (setito.getY() < 0) {
                    
                    setito.remove();
                }

            }

            if(yue.dimensiones().overlaps(setito.dimensiones())&& yue.isAttacking == false && setito.die == false){

                muerteYue();

            }
            
        }
        
    }
    
    public void muerteEnemigo(){
        if (enemigo.muerto == false) {
            
            if (enemigo.dimensiones().overlaps(yue.dimensiones()) && yue.isAttacking == true) {
                continuar = false;
                enemigo.muerto = true;
                enemigo.yVelocity = -10f;
                puntos+=200;
                espadaSonido.play();
            }

            if (enemigo.getY() < 0) {
                enemigo.remove();
            }
        }
    }
    
    public void Enemigovista(){
        if (yue.getX() < enemigo.getX() + 20 && yue.getX() > enemigo.getX() && enemigo.muerto == false) {
            enemigo.isFacingRight = false;
            enemigo.pegar(true);
            continuar = true;
        }
        
        else if (yue.getX() > enemigo.getX() - 10 && yue.getX() < enemigo.getX() && enemigo.muerto == false){
            enemigo.isFacingRight = true;
            enemigo.pegar(true);
            continuar = true;
        }
        
        else{
        
            continuar = false;
            
        }
    
    }
    
    public void comprobarRupias(){
    
        if (yue.dimensiones().overlaps(vRupia.get(0).dimensiones())) {
            vRupia.get(0).setPosition(-500, -500);
            vRupia.get(0).remove();
            rupiaSonido.play();
            puntos += 100;
        }
    
        if (yue.dimensiones().overlaps(vRupia.get(1).dimensiones())) {
            vRupia.get(1).setPosition(-500, -500);    
            vRupia.get(1).remove();
            rupiaSonido.play();
            puntos += 100;
        }
        
        if (yue.dimensiones().overlaps(vRupia.get(2).dimensiones())) {
            vRupia.get(2).setPosition(-500, -500);
            vRupia.get(2).remove();
            rupiaSonido.play();
            puntos += 100;
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
