package net.petterroea.starterkit;

import java.awt.Color;
import java.awt.image.BufferedImage;
/**
 * THIS IS NOT ACTUALLY A SHADER!!!<br />
 * It is a piece of code that you use for manipulating the font, like adding fancy sine waves^^<br />
 * To use it, create a instance of this, and override the doPixel method.
 * @author petterroea
 *
 */
public class FontShader {
	/**
	 * You should not pay attention to this. It is used by the Font class to modify a letter. Can be used for all BufferedImages, tho ;)(hint, hint)
	 * @param src The source image
	 * @param screenx X coordinate on screen that the image is going to be drawn
	 * @param screeny Y coordinate on screen that the image is going to be drawn
	 * @return A modified, edited, manimpulated image
	 */
	public BufferedImage render(BufferedImage src, int screenx, int screeny)
	{
		BufferedImage product = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < src.getWidth(); x++)
		{
			for(int y = 0; y < src.getHeight(); y++)
			{
				if((src.getRGB(x, y) >>> 24) > 180)
				{
					product.setRGB(x, y, doPixel(x, y, src.getRGB(x, y), screenx, screeny));
				}
				else if(src.getRGB(x, y) + "" == "-1")
				{
					product.setRGB(x, y, doPixel(x, y, src.getRGB(x, y), screenx, screeny));
					System.out.println("Yay");
				}
				else
				{
					product.setRGB(x, y, src.getRGB(x, y));
				}
				//System.out.println("Was pixel color " + src.getRGB(x, y) + ", is pixel color " + product.getRGB(x, y));
			}
		}
		return product;
	}
	/**
	 * Override this to edit pixels
	 * @param x The x coordinate in the image
	 * @param y The y coordinate in the image
	 * @param src The rgb color value. (HINT: Use Color(src) to and Color.getRed() and so on to get the different colors, and then Color.setRed() and so on and Color.getRGB() to get the returning int
	 * @param screenx X coordinate on the screen that the IMAGE is drawn. Use screenx + x to get the absolute position
	 * @param screeny Y coordinate on the screen that the IMAGE is drawn. Use screeny + y to get the absolute position.
	 * @return The rgb color to put back into the image.
	 */
	public int doPixel(int x, int y, int src, int screenx, int screeny)
	{
		System.out.println("This is called. That is an error");
		//Override this
		return src;
	}
}
