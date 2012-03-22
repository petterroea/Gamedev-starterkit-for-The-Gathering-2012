package net.petterroea.starterkit;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 * This class enables you to create custom fonts for use in the game.
 * @author petterroea
 *
 */
public class Font {
	/**
	 * Map used to find the position of a char in the image
	 */
	private String[] chars = {"ABCDEFGHIJ",
					  "KLMNOPQRST",
					  "UVWXYZ.,:;",
					  "!?)([]0123",
					  "456789"};
	/**
	 * The grid of sprites used in the font
	 */
	private BufferedImage[][] src;
	/**
	 * Constructor for a font
	 * @param sprite The sprite to use
	 */
	public Font(Sprite sprite)
	{
		this.src = sprite.getGrid();
	}
	/**
	 * Draws a string to the selected x and y coordinates. I am too lazy to support newline.
	 * @param g The graphics object
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param text The string to be written to the screen
	 * @param shader The FontShader object. It is not actually a shader
	 */
	public void draw(Graphics g, int x, int y, String text, FontShader shader)
	{
		int yOffset = 0;
		int fontw = src[0][0].getWidth();
		text = text.toUpperCase();
		for(int i = 0; i < text.length(); i++)
		{
			//New letter, search through for position of the character
			for(int a = 0; a < chars.length; a++)
			{
				if(chars[a].indexOf(text.charAt(i)) > -1) //If it exists in this line...
				{
					int index = chars[a].indexOf(text.charAt(i));
					if(shader == null)
					{
						g.drawImage(src[index][a], x + (i * fontw) + (i * 2), y, null);
					}
					else
					{
						//System.out.println("String " + text + " is using a shader!");
						g.drawImage(shader.render(src[index][a], x + (i * fontw) + (i * 2), y), x + (i * fontw) + (i * 2), y, null);
					}
				}
			}
		}
	}
}
