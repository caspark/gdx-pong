package com.asparck.gdx.pong

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication

object Main extends App {
  val config = new LwjglApplicationConfiguration();
  new LwjglApplication(new GdxPongGame, config);
}
