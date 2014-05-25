package com.asparck.gdx.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PongScreen implements Screen {
	public static class Paddle {
		public static final Vector2 SIZE = new Vector2(20f, 60f);
		public static final float SPEED = 5f;
		public Vector2 pos = new Vector2();
		public int score;

		public Rectangle toRectangle(Rectangle rectangle) {
			rectangle.set(pos.x, pos.y, SIZE.x, SIZE.y);
			return rectangle;
		}
	}

	public static class Ball {
		public static final Vector2 SIZE = new Vector2(20f, 20f);
		public static final Vector2 INITIAL_SPEED = new Vector2(5f, 2f);
		public Vector2 pos = new Vector2();
		public Vector2 speed = new Vector2();

		public Rectangle toRectangle(Rectangle rectangle) {
			rectangle.set(pos.x, pos.y, SIZE.x, SIZE.y);
			return rectangle;
		}
	}

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private final ShapeRenderer shapeRenderer = new ShapeRenderer();
	private final OrthographicCamera camera = new OrthographicCamera(WIDTH, HEIGHT);
	private final Paddle player1 = new Paddle();
	private final Paddle player2 = new Paddle();
	private final Ball ball = new Ball();
	private final GdxPongGame game;


	public PongScreen(GdxPongGame gdxPongGame) {
		this.game = gdxPongGame;
		camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
		camera.update();
		startRound();
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyPressed(Keys.UP)) {
			player2.pos.y = player2.pos.y + Paddle.SPEED;
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			player2.pos.y = player2.pos.y - Paddle.SPEED;
		}

		if (Gdx.input.isKeyPressed(Keys.W)) {
			player1.pos.y = player1.pos.y + Paddle.SPEED;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			player1.pos.y = player1.pos.y - Paddle.SPEED;
		}

		ball.pos.x = ball.pos.x + ball.speed.x;
		ball.pos.y = ball.pos.y + ball.speed.y;

		if (ball.pos.y + Ball.SIZE.y > HEIGHT || ball.pos.y < 0) {
			ball.speed.y *= -1;
		}

		if (player1.pos.y < 0) {
			player1.pos.y = 0;
		} else if (player1.pos.y > HEIGHT - Paddle.SIZE.y) {
			player1.pos.y = HEIGHT - Paddle.SIZE.y;
		}
		if (player2.pos.y < 0) {
			player2.pos.y = 0;
		} else if (player2.pos.y > HEIGHT - Paddle.SIZE.y) {
			player2.pos.y = HEIGHT - Paddle.SIZE.y;
		}

		if (Intersector.overlaps(player1.toRectangle(Rectangle.tmp), ball.toRectangle(Rectangle.tmp2))) {
			ball.speed.x *= -1.1f;
			ball.pos.x = Paddle.SIZE.x;
		} else if (Intersector.overlaps(player2.toRectangle(Rectangle.tmp), ball.toRectangle(Rectangle.tmp2))) {
			ball.speed.x *= -1.1f;
			ball.pos.x = WIDTH - Paddle.SIZE.x - Ball.SIZE.x;
		}

		if (ball.pos.x < 0) {
			player2.score++;
			startRound();
		} else if (ball.pos.x > WIDTH) {
			player1.score++;
			startRound();
		}

		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);

		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(0, 0, WIDTH, HEIGHT);

		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(player1.pos.x, player1.pos.y, Paddle.SIZE.x, Paddle.SIZE.y);
		shapeRenderer.rect(player2.pos.x, player2.pos.y, Paddle.SIZE.x, Paddle.SIZE.y);

		shapeRenderer.rect(ball.pos.x, ball.pos.y, Ball.SIZE.x, Ball.SIZE.y);

		shapeRenderer.end();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.bigFont.draw(game.batch, Integer.toString(player1.score), 0, game.bigFont.getLineHeight());
		game.bigFont.draw(game.batch, Integer.toString(player2.score),
				WIDTH - game.bigFont.getBounds(Integer.toString(player2.score)).width, game.bigFont.getLineHeight());
		game.batch.end();
	}

	public void startRound() {
		Gdx.app.log("Score", "player 1: " + player1.score + " player 2: " + player2.score);
		player1.pos.set(0, HEIGHT / 2 - Paddle.SIZE.y / 2);
		player2.pos.set(WIDTH - Paddle.SIZE.x, HEIGHT / 2 - Paddle.SIZE.y / 2);
		ball.pos.set(WIDTH / 2 - Paddle.SIZE.x / 2, HEIGHT / 2 - Paddle.SIZE.y / 2);
		ball.speed.set(Ball.INITIAL_SPEED);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}
