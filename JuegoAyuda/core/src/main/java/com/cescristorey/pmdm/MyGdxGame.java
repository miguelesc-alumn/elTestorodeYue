package com.cescristorey.pmdm;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

	public void create() {
            batch = new SpriteBatch();
            this.font = new BitmapFont();
            this.setScreen(new PantallaInicio(this));
        }
}
