package com.asparck.gdx.pong

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.Gdx
import scala.util.Random
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.ScreenAdapter

class PongScreen(bigFont: BitmapFont) extends ScreenAdapter {
  val r = new Random

  object Paddle {
    val SIZE = new Vector2(20f, 60f)
    val SPEED = 5f
  }

  class Paddle {
    import Paddle._
    val pos = new Vector2
    var score = 0

    def toRectangle(rectangle: Rectangle): Rectangle = {
      rectangle.set(pos.x, pos.y, SIZE.x, SIZE.y)
      rectangle
    }
  }

  object Ball {
    val SIZE = new Vector2(20f, 20f)
    val START_SPEED = 6f

    def calculateRandomStartingSpeed(output: Vector2): Unit = {
      val toPlayer2 = r.nextInt(2) == 1
      val angle = (Math.PI / 4d) + r.nextDouble() * (Math.PI / 2) + (if (toPlayer2) Math.PI else 0d)
      output.set(START_SPEED * Math.sin(angle).toFloat, START_SPEED * Math.cos(angle).toFloat)
    }
  }

  class Ball {
    import Ball._

    val pos = new Vector2
    val speed = new Vector2

    def toRectangle(rectangle: Rectangle): Rectangle = {
      rectangle.set(pos.x, pos.y, SIZE.x, SIZE.y)
      rectangle
    }
  }

  val WIDTH = 800;
  val HEIGHT = 600;

  val shapeRenderer = new ShapeRenderer
  val camera = new OrthographicCamera(WIDTH, HEIGHT)
  val player1 = new Paddle
  val player2 = new Paddle
  val ball = new Ball
  var batch: SpriteBatch = new SpriteBatch

  // "constructor"
  camera.position.set(WIDTH / 2, HEIGHT / 2, 0)
  camera.update
  startRound

  override def render(delta: Float) = {
    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
      Gdx.app.exit()
    }

    if (Gdx.input.isKeyPressed(Keys.UP)) {
      player2.pos.y = player2.pos.y + Paddle.SPEED
    } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
      player2.pos.y = player2.pos.y - Paddle.SPEED
    }

    if (Gdx.input.isKeyPressed(Keys.W)) {
      player1.pos.y = player1.pos.y + Paddle.SPEED
    } else if (Gdx.input.isKeyPressed(Keys.S)) {
      player1.pos.y = player1.pos.y - Paddle.SPEED
    }

    ball.pos.x = ball.pos.x + ball.speed.x
    ball.pos.y = ball.pos.y + ball.speed.y

    if (ball.pos.y + Ball.SIZE.y > HEIGHT || ball.pos.y < 0) {
      ball.speed.y *= -1
    }

    if (player1.pos.y < 0) {
      player1.pos.y = 0
    } else if (player1.pos.y > HEIGHT - Paddle.SIZE.y) {
      player1.pos.y = HEIGHT - Paddle.SIZE.y
    }
    if (player2.pos.y < 0) {
      player2.pos.y = 0
    } else if (player2.pos.y > HEIGHT - Paddle.SIZE.y) {
      player2.pos.y = HEIGHT - Paddle.SIZE.y
    }

    if (Intersector.overlaps(player1.toRectangle(Rectangle.tmp), ball.toRectangle(Rectangle.tmp2))) {
      ball.speed.x *= -1.1f
      ball.pos.x = Paddle.SIZE.x
    } else if (Intersector.overlaps(player2.toRectangle(Rectangle.tmp), ball.toRectangle(Rectangle.tmp2))) {
      ball.speed.x *= -1.1f
      ball.pos.x = WIDTH - Paddle.SIZE.x - Ball.SIZE.x
    }

    if (ball.pos.x < 0) {
      player2.score += 1
      startRound
    } else if (ball.pos.x > WIDTH) {
      player1.score += 1
      startRound
    }

    Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)

    shapeRenderer.setColor(Color.BLACK)
    shapeRenderer.rect(0, 0, WIDTH, HEIGHT)

    shapeRenderer.setColor(Color.GREEN)
    shapeRenderer.rect(player1.pos.x, player1.pos.y, Paddle.SIZE.x, Paddle.SIZE.y)
    shapeRenderer.rect(player2.pos.x, player2.pos.y, Paddle.SIZE.x, Paddle.SIZE.y)

    shapeRenderer.rect(ball.pos.x, ball.pos.y, Ball.SIZE.x, Ball.SIZE.y)

    shapeRenderer.end

    batch.setProjectionMatrix(camera.combined)
    batch.begin
    bigFont.draw(batch, Integer.toString(player1.score), 0, bigFont.getLineHeight)
    bigFont.draw(batch, Integer.toString(player2.score),
      WIDTH - bigFont.getBounds(Integer.toString(player2.score)).width, bigFont.getLineHeight)
    batch.end
  }

  def startRound = {
    Gdx.app.log("Score", "player 1: " + player1.score + " player 2: " + player2.score)
    player1.pos.set(0, HEIGHT / 2 - Paddle.SIZE.y / 2)
    player2.pos.set(WIDTH - Paddle.SIZE.x, HEIGHT / 2 - Paddle.SIZE.y / 2)
    ball.pos.set(WIDTH / 2 - Paddle.SIZE.x / 2, HEIGHT / 2 - Paddle.SIZE.y / 2)
    Ball.calculateRandomStartingSpeed(ball.speed)
  }

  override def dispose = {
    batch.dispose
    shapeRenderer.dispose
  }
}
