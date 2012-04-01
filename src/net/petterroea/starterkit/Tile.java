/*
 * Tile.java
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

import java.awt.image.BufferedImage;

/**
 * A tile.
 * @author petterroea
 *
 */
public class Tile {
	/**
	 * Where the tile is in the tile grid(x coordinate)
	 */
	public int gridx;
	/**
	 * Where the tile is in the tile grid(y coordinate)
	 */
	public int gridy;
	/**
	 * True if the tile is collidable with a player object
	 */
	public boolean collidesWithPlayer = true;
	/**
	 * True if the tile collides with particles
	 */
	public boolean collidesWithParticles = true;
	/**
	 * Constructor for a tile. Override this!
	 */
	public Tile(int gridx, int gridy)
	{
		this.gridx = gridx;
		this.gridy = gridy;
	}
	/**
	 * This is used for getting a specific tile sprite image.
	 * 
	 * I added parameters for more advanced users who wants more advanced games, which allows you to get the tiles close to
	 * this tile and calculate the sprite from that.
	 * @param map So you can get if there are tiles nearby, and make it use a sprite that fits for ajacent sprites
	 * @return The image
	 */
	public BufferedImage getTile(Map map)
	{
		return null;
	}
	/**
	 * Checks if the tile collides with the specific entity
	 * @param ent The entity to check with. Use "ent instanceof NAMEOFYOURENTITYCLASS" to check if the entity is a specific entity
	 * @return True if the tile collides with that entity
	 */
	public boolean collidesWith(Entity ent)
	{
		return true;
	}
	/**
	 * Override this if you want this to be "usable"
	 * @return True if it is used. This blocks further tiles nearby to be processed.
	 */
	public boolean use()
	{
		return false;
	}
	/**
	 * Checks if this is drawable in the window(In viewing range)
	 * @param xoff X axis offset
	 * @param yoff Y axis offset
	 * @param tilew Width of one tile
	 * @param tileh Height of one tile
	 * @return True if the entity is vivible
	 */
	public boolean isVisible(int xoff, int yoff, int tilew, int tileh)
	{
		if((gridx*tilew) + xoff > Game.WIDTH || (gridy*tileh) + yoff > Game.HEIGHT)
		{
			return false;
		}
		return true;
	}
}
