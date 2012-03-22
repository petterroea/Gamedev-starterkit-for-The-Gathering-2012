/*
 * Sprite.java
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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Wrapper for making a sprite(that may be animated)
 * @author petterroea
 *
 */
public class Sprite {
	/**
	 * Source image
	 */
	private BufferedImage[][] src;
	/**
	 * True if the sprite is animated
	 */
	private boolean animated = false;
	/**
	 * True if the sprite is a spritesheet
	 */
	private boolean sheet = false;
	/**
	 * True if the animation only loops one time
	 */
	private boolean oneLoop = false;
	/**
	 * Width of the sheet(in sprites)
	 */
	private int w = 0; //Width in segments
	/**
	 * Height of sheet(in sprites)
	 */
	private int h = 0; //Height in segments
	/**
	 * Length of the animation in sprites
	 */
	private int animationl = 0; //Animation length in segments
	/**
	 * Speed of animation in milliseconds
	 */
	private int animationspeed = 0; //Animation speed in milliseconds
	/**
	 * Time when the animation was started. The time is 0 if the animation is not running
	 */
	private long animstart = 0; //Time when the animation was started if 0 then the animation is off
	/**
	 * Constructor for a grid of sprites(Spritesheet)
	 * @param name Filename of the image
	 * @param segmentw Width of each segment(sprite in the sheet)
	 * @param segmenth Height of each segment(sprite in the sheet)
	 * @param inJar True if the image is in the jarfile
	 * @throws Exception Either if image is not found or if the image is of wrong dimensions
	 */
	public Sprite(String name, int segmentw, int segmenth, boolean inJar) throws Exception
	{
		sheet = true;
		BufferedImage temp = null;
		if(inJar)
		{
			temp = ImageIO.read(Sprite.class.getResourceAsStream(name));
		}
		else
		{
			File file = new File(name);
			temp = ImageIO.read(file);
		}
		if(temp.getWidth() % segmentw == 0 && temp.getHeight() % segmenth == 0)
		{
			src = new BufferedImage[temp.getWidth() / segmentw][temp.getHeight() / segmenth];
			for(int xpos = 0; xpos < (temp.getWidth() / segmentw); xpos++)
			{
				for(int ypos = 0; ypos < (temp.getHeight() / segmenth); ypos++)
				{
					src[xpos][ypos] = temp.getSubimage(xpos * segmentw, ypos * segmenth, segmentw, segmenth);
				}
			}
			w = temp.getWidth() / segmentw;
			h = temp.getHeight() / segmenth;
		}
		else
		{
			throw new Exception("Image is of wrong dimensions");
		}
	}
	/**
	 * Constructor to make the sprite animated
	 * @param name Filename of the image
	 * @param segmentw Width of each segment
	 * @param segmenth Height of each segment
	 * @param animl Length of animation in segments
	 * @param animspeed Speed of animation in milliseconds per segment
	 * @param inJar True if the image is in the jar file
	 * @param loopOnce True if the animation should only loop once
	 * @throws Exception If the image is not found, the image is of wrong dimensions, or the animation is longer then the spritesheet
	 */
	public Sprite(String name, int segmentw, int segmenth, int animl, int animspeed, boolean inJar, boolean loopOnce) throws Exception
	{
		animationl = animl;
		animationspeed = animspeed;
		animated = true;
		oneLoop = loopOnce;
		BufferedImage temp = null;
		if(inJar)
		{
			temp = ImageIO.read(Sprite.class.getResourceAsStream(name));
		}
		else
		{
			File file = new File(name);
			temp = ImageIO.read(file);
		}
		if(temp.getWidth() % segmentw == 0 && temp.getHeight() % segmenth == 0)
		{
			src = new BufferedImage[temp.getWidth() / segmentw][temp.getHeight() / segmenth];
			for(int xpos = 0; xpos < (temp.getWidth() / segmentw); xpos++)
			{
				for(int ypos = 0; ypos < (temp.getHeight() / segmenth); ypos++)
				{
					src[xpos][ypos] = temp.getSubimage(xpos * segmentw, ypos * segmenth, segmentw, segmenth);
				}
			}
			w = temp.getWidth() / segmentw;
			h = temp.getHeight() / segmenth;
		}
		else
		{
			throw new Exception("Image is of wrong dimensions");
		}
		if(animationl > w * h)
		{
			throw new Exception("Animation longer then contents of spritesheet");
		}
	}
	/**
	 * Constructor with an option to import from the filesystem. Here are some examples for filenames:
	 * 
	 * Inside jar:
	 * 
	 * test.png
	 * person.png
	 * wall.png
	 * 
	 * Outside jar:
	 * 
	 * test.png
	 * images/person.png
	 * C:/game/images.png - NOT SMART TO USE. PERIOD.
	 * @param name The filename of the file
	 * @param inJar True if the image is in the jar file.
	 * @throws IOException If the image is not found
	 */
	public Sprite(String name, boolean inJar) throws IOException
	{
		src = new BufferedImage[1][1];
		if(inJar)
		{
			src[0][0] = ImageIO.read(Sprite.class.getResourceAsStream(name));
		}
		else
		{
			File file = new File(name);
			src[0][0] = ImageIO.read(file);
		}
	}
	/**
	 * What it says on the tin, starts the animation(If the sprite is a animation)
	 */
	public void startAnimation()
	{
		animstart = System.currentTimeMillis();
	}
	/**
	 * Stops the animation(If the sprite is a animation)
	 */
	public void stopAnimation()
	{
		animstart = 0;
	}
	/**
	 * Get the image contained in this sprite
	 * @return The image
	 */
	public BufferedImage getBufferedImage()
	{
		if(animated)
		{
			long framesElapsed = animstart / (long)animationspeed;
			int currentframe = (int)(framesElapsed % (long)animationl);
			if(oneLoop)
			{
				if(framesElapsed > animationl)
				{
					stopAnimation();
					currentframe = animationl;
				}
			}
			int tempx = currentframe % w;
			int tempy = (int) Math.floor((double)(currentframe / w));
			return src[tempx][tempy];
		}
		return src[0][0];
	}
	/**
	 * Get the image contained in the specified coordinate. This is only used if this is a grid.
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return The bufferedImage at specified coordinate
	 */
	public BufferedImage getBufferedImage(int x, int y)
	{
		if(animated)
		{
			return null;
		}
		return src[x][y];
	}
	/**
	 * Returns the entire grid of sprites
	 * @return The grid of sprites
	 */
	public BufferedImage[][] getGrid() {
		return src;
	}
}
