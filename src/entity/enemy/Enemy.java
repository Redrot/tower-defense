package entity.enemy;

import static gui.Game.TILE_SIZE;

import entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public abstract class Enemy extends Entity {
	
	private int health;
	private char dir;
	private double rotationAngle, speed;
	
	int reward = 1;
	
	Image img;
	
	public Enemy(double x, double y, int health, double speed) {
		super(x, y);
		this.health = health;
		this.speed = speed;
	}
	
	public int getReward() {
		return reward;
	}
	
	public void setDir(char dir) {
		this.dir = dir;
	}
	
	public void takeDamage(int damage) {
		health -= damage;
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	public void move(char[][] grid) {
		int gx, gy;
		
		switch(dir) {
		case 'N':
			gx = (int)((getX() - TILE_SIZE / 2) / TILE_SIZE);
			gy = (int)(getY() / TILE_SIZE);
			break;
		case 'S':
			gx = (int)((getX() - TILE_SIZE / 2) / TILE_SIZE);
			gy = (int)(getY() / TILE_SIZE) - 1;
			break;
		case 'E':
			gx = (int)(getX() / TILE_SIZE) - 1;
			gy = (int)((getY() - TILE_SIZE / 2) / TILE_SIZE);
			break;
		case 'W':
			gx = (int)(getX() / TILE_SIZE);
			gy = (int)((getY() - TILE_SIZE / 2) / TILE_SIZE);
			break;
		default:
			gx = (int)((getX() - TILE_SIZE / 2) / TILE_SIZE);
			gy = (int)((getY() - TILE_SIZE / 2) / TILE_SIZE);
			break;
		}
		
		if (gx < 0) { gx = 0; }
		if (gy < 0) { gy = 0; }
		
		char newDir = grid[gy][gx];
		
		if (newDir != 'X') {
			dir = newDir;
		}
		
		switch(dir) {
		case 'N':
			setY(getY() - speed);
			rotationAngle = -90;
			break;
		case 'S':
			setY(getY() + speed);
			rotationAngle = 90;
			break;
		case 'E':
			setX(getX() + speed);
			rotationAngle = 0;
			break;
		case 'W':
			setX(getX() - speed);
			rotationAngle = 180;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
		double xi = getX() - TILE_SIZE / 2;
		double yi = getY() - TILE_SIZE / 2;
		
		Rotate r = new Rotate(rotationAngle, getX(), getY());
		gc.save();
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		gc.drawImage(img, xi, yi, TILE_SIZE, TILE_SIZE);
		gc.restore();
	}
	
}
