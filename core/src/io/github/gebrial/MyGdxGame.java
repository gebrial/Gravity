package io.github.gebrial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nbody.Body;
import nbody.Universe;

public class MyGdxGame extends Game {
	PerspectiveCamera cam;
	ShapeRenderer shapeRenderer;
	Universe universe;

	int fps = 60;


	@Override
	public void create() {
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		shapeRenderer = new ShapeRenderer();

		universe = new Universe();
		universe.populate(30);
		universe.initBodies(1f/fps);
	}

	@Override
	public void render () {
		universe.update(1f/fps);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.update();
		shapeRenderer.setProjectionMatrix(cam.combined);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
		shapeRenderer.setColor(Color.YELLOW);
		for(Body b : universe.getBodies())
			b.render(shapeRenderer);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
