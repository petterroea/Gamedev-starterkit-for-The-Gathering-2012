/*
 * Entity.java
 * 
 * Part of the GameSDK starterkit made for the seminar at "The Gathering 2012 - At the end of the universe"
 * 
 * This source code is provided AS-IS and without any warranty. Use at own risk.
 * You can use and modify this source any way you want, but please send me an E-mail to petterroea@skymiastudios.com
 * if you make a game from it - I would like to keep a list of all games, as i am curious. You may also use this for games
 * that cost money. So it is basically public domain, except that i own the rights to it, but give you the rights to use
 * it for the purposes above. You cant sell this kit. You can sell a game based on it, but not the source code to this kit.
 * Also, this big comment must stay in all code files that originated from the starterkit, no matter how much they are
 * modified.
 * 
 * If you want support for this SDK, feel free to contact me. If you are at "The Gathering 2012", you can
 * look for me in the creative lounge(Where i sit). If not, feel free to send me an E-Mail to the above
 * adress. If you need normal java help, ask a mentor.
 * 
 */
package net.petterroea.starterkit;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
/**
 * A standard entity for use in a game
 * @author petterroea
 *
 */
public class Entity {
	/**
	 * X position with decimals, for greater accuracy
	 */
	public double x = 0;
	/**
	 * Y position with decimals, for greater accuracy
	 */
	public double y = 0;
	/**
	 * X axis speed in decimals, for greater accuracy
	 */
	public double xspeed = 0;
	/**
	 * Y axis speed in decimals, for greater accuracy
	 */
	public double yspeed = 0;
	/**
	 * Width of the entity
	 */
	public int width = 32;
	/**
	 * Height of the entity
	 */
	public int height = 32;
	/**
	 * The collisionbox of the entity
	 */
	public Rectangle collisionbox;
	/**
	 * The sprite used by the entity
	 */
	public Sprite entitysprite;
	/**
	 * True if the entity is dead and should be removed
	 */
	public boolean removeMe = false;
	/**
	 * Constructor for a entity
	 * @param x The x axis position of the entity
	 * @param y The y axis position of the entity
	 */
	public Entity(int x, int y)
	{
		this.x = x;
		this.y = y;
		collisionbox = new Rectangle((int)x, (int)y, width, height); //Oppdater kollisjonsboksen
	}
	/**
	 * Updates the entity's x and y postition based on x and y speed
	 * @param delta Milliseconds since last update
	 */
	public void update(int delta) {
		x = x + (xspeed * delta);
		y = y + (yspeed * delta);
		collisionbox = new Rectangle((int)x, (int)y, width, height); //Oppdater kollisjonsboksen
		
	}
	/**
	 * Updates the entity's position, while paying attention to other entities
	 * @param delta Time since last update
	 * @param map The list of entities
	 * @param pos This enties position in the list
	 */
	public void update(int delta, LinkedList<Entity> map, int pos) {
		double oldx = x;
		double oldy = y;
		x = x + (xspeed * delta);
		Rectangle me = new Rectangle((int)x, (int)y, width, height);
		//Sjekk x
		for(int i = 0; i < map.size(); i++)
		{
			if(i != pos && me.intersects(map.get(i).collisionbox) && (map.get(i).canCollideWith(this) || canCollideWith(map.get(i))))
			{
				x = oldx;
				break;
			}
		}
		//Sjekk y
		y = y + (yspeed * delta);
		me = new Rectangle((int)x, (int)y, 32, 32);
		for(int i = 0; i < map.size(); i++)
		{
			if(i != pos && me.intersects(map.get(i).collisionbox) && (map.get(i).canCollideWith(this) || canCollideWith(map.get(i))))
			{
				y = oldy;
				break;
			}
		}
		collisionbox = new Rectangle((int)x, (int)y, width, height); //Oppdater kollisjonsboksen
		
	}
	/**
	 * Updates the entity's position, while paying attention to Both tiles and entities in a map.
	 * @param delta Time since last update in ms
	 * @param map The map this is using
	 * @param pos This enties position in the list
	 */
	public void update(int delta, Map map, int pos) {
		double oldx = x;
		double oldy = y;
		x = x + (xspeed * delta);
		Rectangle me = new Rectangle((int)x, (int)y, width, height);
		//Sjekk x
		for(int i = 0; i < map.entities.size(); i++)
		{
			if(i != pos && me.intersects(map.entities.get(i).collisionbox) && (map.entities.get(i).canCollideWith(this) || canCollideWith(map.entities.get(i))))
			{
				x = oldx;
				break;
			}
		}
		if(x != oldx)
		{
			top: for(int xp = 0; xp < map.w; xp++)
			{
				for(int yp = 0; yp < map.h; yp++)
				{
					Rectangle col = new Rectangle(xp * map.tilew, yp * map.tileh, map.tilew, map.tileh);
					if(me.intersects(col) && map.tiles[xp][yp].collidesWith(this))
					{
						x = oldx;
						break top;
					}
				}
			}
		}
		//Sjekk y
		y = y + (yspeed * delta);
		me = new Rectangle((int)x, (int)y, this.width, this.height);
		for(int i = 0; i < map.entities.size(); i++)
		{
			if(i != pos && me.intersects(map.entities.get(i).collisionbox) && (map.entities.get(i).canCollideWith(this) || canCollideWith(map.entities.get(i))))
			{
				y = oldy;
				break;
			}
		}
		if(y != oldy)
		{
			top: for(int xp = 0; xp < map.w; xp++)
			{
				for(int yp = 0; yp < map.h; yp++)
				{
					Rectangle col = new Rectangle(xp * map.tilew, yp * map.tileh, map.tilew, map.tileh);
					if(me.intersects(col) && map.tiles[xp][yp].collidesWith(this))
					{
						y = oldy;
						break top;
					}
				}
			}
		}
		collisionbox = new Rectangle((int)x, (int)y, width, height); //Oppdater kollisjonsboksen
		
	}
	/**
	 * Checks if an entity can collide with another entity
	 * @param entity The entity to check against
	 * @return true if they are collidable with each other
	 */
	private boolean canCollideWith(Entity entity) {
		return false;
	}
	/**
	 * Draws the entity
	 * @param g The graphics object
	 * @param xoff x offset in pixels
	 * @param yoff y offset in pixels
	 */
	public void draw(Graphics g, int xoff, int yoff) {
		if(isVisible(xoff, yoff))
		{
			g.drawImage(entitysprite.getBufferedImage(), (int)x + xoff, (int)y + yoff, null);
		}
	}
	/**
	 * Checks if this is drawable in the window(In viewing range)
	 * @param xoff X axis offset
	 * @param yoff Y axis offset
	 * @return True if the entity is vivible
	 */
	public boolean isVisible(int xoff, int yoff)
	{
		if((int)x + xoff > Game.WIDTH || (int)y + yoff > Game.HEIGHT)
		{
			return false;
		}
		return true;
	}
}
