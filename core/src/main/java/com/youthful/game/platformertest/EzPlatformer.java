package com.youthful.game.platformertest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import com.dongbat.jbump.Response.Result;

public class EzPlatformer extends ApplicationAdapter {
	
	private OrthographicCamera cam;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	private Player player;
	private World<Entity> world;
	private Result result;
	
	public void create () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		cam = new OrthographicCamera(240, 135);
		map = new TmxMapLoader().load("Test.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		player = new Player(120, 240, 16, 31, Gdx.files.internal("sprite.png"));
		world = new World<Entity>();
		TiledMapTileLayer collision = (TiledMapTileLayer) map.getLayers().get(0);
		for (int i = 0; i < collision.getWidth(); i++) {
			for (int j = 0; j < collision.getHeight(); j++) {
				if (collision.getCell(i, j) != null) {
					world.add(new Item<Entity>(), i * 16, j * 16, 16, 16);
				}
			}
		}
		world.add(player.getItem(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
		Gdx.input.setInputProcessor(player);
		Gdx.input.setCursorCatched(true);
	}

	public void render () {
		player.update(Gdx.graphics.getDeltaTime());

		result = world.move(player.getItem(), player.getX(), player.getY(), CollisionFilter.defaultFilter);
		
		player.setJump(false);
		for (int i = 0; i < result.projectedCollisions.size(); i++) {
			if (result.projectedCollisions.get(i).normal.y == 1) {
				player.setJump(true);
				player.setFallVelocity(0);
			}
		}
		
		player.setX(world.getRect(player.getItem()).x);
		player.setY(world.getRect(player.getItem()).y);
		
		cam.position.x = player.getX() + player.getWidth() / 2;
		cam.position.y = player.getY() + player.getHeight() / 2;
		cam.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		renderer.setView(cam);
		renderer.render();
		
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end();
	}
	
	public void dispose () {
		renderer.dispose();
		map.dispose();
		player.getTexture().dispose();
	}
}
