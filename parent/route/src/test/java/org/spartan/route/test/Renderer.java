package org.spartan.route.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.imageio.ImageIO;

import org.spartan.model.entity.map.CollisionMap;
import org.spartan.model.entity.map.GameObject;
import org.spartan.model.entity.map.Region;
import org.spartan.model.entity.map.TileDefinition;
import org.spartan.model.entity.map.TiledMap;
import org.spartan.route.Node;

public class Renderer {

	/**
	 * 8 pixels per tile
	 */
	private static final int BRUSH_SIZE = 8;

	/**
	 * The image
	 */
	private final BufferedImage image;

	/**
	 * The graphics
	 */
	private final Graphics graphics;

	/**
	 * 
	 */
	public Renderer() {
		this.image = new BufferedImage(Region.WIDTH * BRUSH_SIZE, Region.HEIGHT * BRUSH_SIZE, BufferedImage.TYPE_INT_RGB);
		this.graphics = image.createGraphics();
		this.renderRaster();
	}

	/**
	 * Renders the landscape
	 * @param region
	 */
	public void render(TiledMap map) {
		for (int x = 0; x < Region.WIDTH; x++) {
			for (int y = 0; y < Region.HEIGHT; y++) {
				int rgb = TileDefinition.get(map.get(x, y, 0).getTexture() + 1).getRgb();
				
				int red = rgb >> 16 & 0xff;
				int green = rgb >> 8 & 0xff;
				int blue = rgb & 0xff;
				
				mark(x, y, new Color(red, green, blue));
			}
		}
		renderRaster();
	}
	
	/**
	 * Renders the game objects
	 * @param map
	 */
	public void render(Set<GameObject> map) {
		map.forEach(object -> {
			if (object.getType() == 0 && object.getDefinition().isSolid() && object.getLocation().getZ() == 0) {
				mark_line(object.getLocation().getX(), object.getLocation().getY(), object.getOrientation(), object.getDefinition().isInteractable() ? Color.RED : Color.WHITE);
			}
			else if (object.getType() == 2 && object.getDefinition().isSolid() && object.getLocation().getZ() == 0) {
				mark_corner(object.getLocation().getX(), object.getLocation().getY(), object.getOrientation(), Color.WHITE);
			}
			else if (object.getType() == 9 && object.getDefinition().isSolid() && object.getLocation().getZ() == 0) {
				mark_diagonal_line(object.getLocation().getX(), object.getLocation().getY(), object.getOrientation(), Color.WHITE);
			}
		});
	}
	
	/**
	 * Renders the game objects
	 * @param map
	 */
	public void render(Collection<Node> map, Region region) {
		map.forEach(node -> this.mark(node.getLocation().clone().subtract(region.getLocation().inflate()).getX(), 
					node.getLocation().clone().subtract(region.getLocation().inflate()).getY(), Color.BLUE));
	}
	
	/**
	 * Renders the collision map
	 * @param map
	 */
	public void render(CollisionMap map, Region region) {
		for (int x = 0; x < region.getCollisionMap().getWidth(); x++) {
			for (int y = 0; y < region.getCollisionMap().getHeight(); y++) {
				
				if (map.collides(region.getLocation().inflate().getX() + x, region.getLocation().inflate().getY() + y, CollisionMap.PROJECTILE_BLOCK)) {
					mark_diagonal_line(x, y, 0, Color.RED);
					mark_diagonal_line(x, y, 1, Color.RED);
				}

				/*
				 * Red cross to mark terrain projectiles can't pass through
				 */
				if (map.collides(region.getLocation().inflate().getX() + x, region.getLocation().inflate().getY() + y, CollisionMap.IMPASSABLE_TERRAIN)) {
					mark_line(x, y, 0, Color.GREEN);
					mark_line(x, y, 1, Color.GREEN);
					mark_line(x, y, 2, Color.GREEN);
					mark_line(x, y, 3, Color.GREEN);
				}

				/*
				 * Straight walls
				 */
				if (map.collides(region.getLocation().inflate().getX() + x, region.getLocation().inflate().getY() + y, CollisionMap.WEST)) {
					mark_line(x, y, 0, Color.WHITE);
				}
				if (map.collides(region.getLocation().inflate().getX() + x, region.getLocation().inflate().getY() + y, CollisionMap.NORTH)) {
					mark_line(x, y, 1, Color.WHITE);
				}
				if (map.collides(region.getLocation().inflate().getX() + x, region.getLocation().inflate().getY() + y, CollisionMap.EAST)) {
					mark_line(x, y, 2, Color.WHITE);
				}
				if (map.collides(region.getLocation().inflate().getX() + x, region.getLocation().inflate().getY() + y, CollisionMap.SOUTH)) {
					mark_line(x, y, 3, Color.WHITE);
				}

				/*
				 * Straight walls
				 */
//				if (map.collides(x, y, CollisionMap.SOUTH_EAST)) {
//					mark_line(x, y, 2, Color.YELLOW);
//					mark_line(x, y, 3, Color.YELLOW);
//				}
//				if (map.collides(x, y, CollisionMap.SOUTH_WEST)) {
//					mark_line(x, y, 0, Color.YELLOW);
//					mark_line(x, y, 3, Color.YELLOW);
//				}
//				if (map.collides(x, y, CollisionMap.NORTH_EAST)) {
//					mark_line(x, y, 2, Color.YELLOW);
//					mark_line(x, y, 1, Color.YELLOW);
//				}
//				if (map.collides(x, y, CollisionMap.NORTH_WEST)) {
//					mark_line(x, y, 0, Color.YELLOW);
//					mark_line(x, y, 1, Color.YELLOW);
//				}
			}
		}
	}
	
	/**
	 * Renders the region's assets
	 * @param region
	 */
	public void render(Region region) {
		render(region.getTiles());
		renderRaster();
	}
	
	/**
	 * Draws the raster
	 */
	private void renderRaster() {
		graphics.setColor(Color.BLUE);
		for (int i = 0; i < Region.WIDTH; i++) {
			graphics.drawLine(0, i * BRUSH_SIZE, Region.WIDTH * BRUSH_SIZE, i * BRUSH_SIZE);
			graphics.drawLine(i * BRUSH_SIZE, 0, i * BRUSH_SIZE, Region.WIDTH * BRUSH_SIZE);
		}
	}
	
	/**
	 * Saves the image to a given file
	 * @param path
	 */
	public void save(String path) throws IOException {
		this.save(new File(path));
	}
	
	/**
	 * Saves the image to a given file
	 * @param path
	 */
	public void save(File path) throws IOException {
		ImageIO.write(image, "png", path);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	private void mark(int x, int y, Color color) {
		graphics.setColor(color);
		graphics.fillRect(x * BRUSH_SIZE, y * BRUSH_SIZE, BRUSH_SIZE, BRUSH_SIZE);
	}
	
	/**
	 * Marks a line
	 * 
	 * @param x
	 * @param y
	 * @param orientation
	 */
	private void mark_line(int x, int y, int orientation, Color color) {
		graphics.setColor(color);
		
		int draw_x = x * BRUSH_SIZE; //graphics.drawLine(, y * BRUSH_SIZE + BRUSH_SIZE, x * BRUSH_SIZE + BRUSH_SIZE, y * BRUSH_SIZE + BRUSH_SIZE);
		int draw_y = y * BRUSH_SIZE; 
		
		switch (orientation) {
		case 0:
			graphics.drawLine(draw_x, draw_y, draw_x, draw_y + BRUSH_SIZE);
			break;
		case 1:
			graphics.drawLine(draw_x, draw_y + BRUSH_SIZE, draw_x + BRUSH_SIZE, draw_y + BRUSH_SIZE);
			break;
		case 2:
			graphics.drawLine(draw_x + BRUSH_SIZE, draw_y, draw_x + BRUSH_SIZE, draw_y + BRUSH_SIZE);
			break;
		case 3:
			graphics.drawLine(draw_x, draw_y, draw_x + BRUSH_SIZE, draw_y);
			break;
		}
	}
	
	/**
	 * Marks a line
	 * 
	 * @param x
	 * @param y
	 * @param orientation
	 */
	private void mark_diagonal_line(int x, int y, int orientation, Color color) {
		graphics.setColor(color);
		
		int draw_x = x * BRUSH_SIZE; //graphics.drawLine(, y * BRUSH_SIZE + BRUSH_SIZE, x * BRUSH_SIZE + BRUSH_SIZE, y * BRUSH_SIZE + BRUSH_SIZE);
		int draw_y = y * BRUSH_SIZE; 
		
		switch (orientation) {
		case 0:
		case 2:
			graphics.drawLine(draw_x, draw_y, draw_x + BRUSH_SIZE, draw_y + BRUSH_SIZE);
			break;
		case 1:
		case 3:
			graphics.drawLine(draw_x + BRUSH_SIZE, draw_y, draw_x, draw_y + BRUSH_SIZE);
			break;
		}
	}
	
	/**
	 * Marks a line
	 * 
	 * @param x
	 * @param y
	 * @param orientation
	 */
	private void mark_corner(int x, int y, int orientation, Color color) {
		graphics.setColor(color);
		
		switch (orientation) {
		case 0:
			mark_line(x, y, 1, color);
			mark_line(x, y, 0, color);
			break;
		case 1:
			mark_line(x, y, 1, color);
			mark_line(x, y, 2, color);
			break;
		case 2:
			mark_line(x, y, 2, color);
			mark_line(x, y, 3, color);
			break;
		case 3:
			mark_line(x, y, 0, color);
			mark_line(x, y, 3, color);
			break;
		}
	}

}
