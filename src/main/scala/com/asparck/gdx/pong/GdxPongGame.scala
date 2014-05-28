package com.asparck.gdx.pong

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.Texture.TextureFilter

class GdxPongGame extends Game {
  def generateFont(fontSize: Int): BitmapFont = {
    val generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"))
    val parameter = new FreeTypeFontParameter()
    parameter.size = fontSize
    parameter.magFilter = TextureFilter.Linear
    parameter.minFilter = TextureFilter.Linear
    val font = generator.generateFont(parameter)
    generator.dispose
    font
  }

  override def create = {
    bigFont = generateFont(36)
    setScreen(new PongScreen(bigFont))
  }

  override def dispose = {
    bigFont.dispose
    bigFont = null
  }

  var bigFont: BitmapFont = null
}
