package com.youthful.game.platformertest;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;

import java.lang.Math;

public class Player extends Sprite implements InputProcessor{

	private Vector2 velocity;
	
	private Item<Entity> item;
	
	private final int SPEED = 143, GRAVITY = 675;
	private final int ySPEED = 300;
	
	private boolean left, right;
	private boolean righthold, lefthold;
	private boolean canJump;
	
	public Player(float x, float y, int width, int height, FileHandle file) {
		super(new Texture(file)); //Calls super constructor of sprite
		
		setBounds(x, y, width, height);
		
		velocity = new Vector2(); //Vector2 is basically data type of x and y
		
		item = new Item<Entity>();
	}
	
	public void update(float dt) {
		if(dt > 0.1) { //Time cap 
			return;
		}
		
		velocity.y -= GRAVITY * dt;
		
		if (velocity.y > ySPEED) {
			velocity.y = ySPEED;
		}
		else if (velocity.y < -ySPEED) {
			velocity.y = -ySPEED;
		}
		
		if(lefthold) {
			if(right) {
				velocity.x = Math.min(SPEED, velocity.x+SPEED/4);
			} else {
				velocity.x = Math.max(-SPEED, velocity.x-SPEED/4);
			}
		} else if(righthold) {
			if(left) {
				velocity.x = Math.max(-SPEED, velocity.x-SPEED/4);
			} else {
				velocity.x = Math.min(SPEED, velocity.x+SPEED/4);
			}
		}
		
		if(!left && !right) {
			velocity.x = 0;
		}
		
		setX(getX() + velocity.x * dt);
		setY(getY() + velocity.y * dt);
	}
	
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.A: 
				left = true; 
				if(!righthold) 
					lefthold = true;
				break;
			case Input.Keys.D: 
				right = true;
				if(!lefthold)
					righthold = true;
				break;
			case Input.Keys.SPACE: if(canJump) {canJump = false; velocity.y = ySPEED;}
		}
		return true;
	}

	
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.A: 
				left = false; 
				lefthold = false; 
				if (right) {
					righthold = true;
				}
				break;
			case Input.Keys.D: 
				right = false; 
				righthold = false;
				if (left) {
					lefthold = true;
				}
		}
		return false;
	}

	public void setJump(boolean canJump) {
		this.canJump = canJump;
	}
	
	public void setFallVelocity(float velocity) {
		this.velocity.y = velocity;
	}
	
	public Item<Entity> getItem() {
		return item;
	}
	
	public boolean keyTyped(char character) {
		return false;
	}
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {	
		return false;
	}
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	public boolean scrolled(int amount) {
		return false;
	}
}
