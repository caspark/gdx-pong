package com.asparck.gdx.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GdxPongGame extends Game {

	public SpriteBatch batch;
	public BitmapFont bigFont;

	@Override
	public void create() {
		batch = new SpriteBatch();
		bigFont = generateFont(36);
		this.setScreen(new PongScreen(this));
	}

	private static BitmapFont generateFont(int fontSize) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = fontSize;
		parameter.magFilter = TextureFilter.Linear;
		parameter.minFilter = TextureFilter.Linear;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

	@Override
	public void dispose() {
		batch.dispose();
		bigFont.dispose();
	}

}
