package com.asparck.gdx.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GdxPongGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear); //slightly better upscaling
		font.setScale(2f);
        this.setScreen(new PongScreen(this));
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
