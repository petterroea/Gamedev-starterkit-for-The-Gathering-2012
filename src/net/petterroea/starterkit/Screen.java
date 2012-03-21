/*
 * Screen.java
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
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
/**
 * Inspired(But not copied from) by Notch's Ludum dare game system. Game states are sorted into screens.<br />
 * For example, one screen can be the main menu, one the options, and one the game itself.
 * @author petterroea
 *
 */
public class Screen {
	/**
	 * Ticks/Updates the screen, is called every time the game updates.<br />
	 * Override this if you want to have a screen that draws
	 * @param delta Time since last update in milliseconds
	 * @param g Graphics object
	 */
	public void tick(int delta, Graphics g)
	{
		g.drawString("Empty screen", 20, 20);
	}
	/**
	 * Called when the mousewheel is scrolled/moved. Override this if you want to listen to this.
	 * @param arg0 The event object
	 */
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Called when the mouse is dragged(Moved while a mousebutton is pressed). Override this if you want to listen to this.
	 * @param arg0 The mouse event object
	 */
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the mouse is moved without a mouse button being pressed down. Override this if you want to listen to this.
	 * @param arg0 The mouse event object
	 */
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the mouse is clicked. Override this if you want to listen to this.
	 * @param arg0 The mouse event object
	 */
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the mouse enters the game window. Override this if you want to listen to this.
	 * @param arg0 The mouse event
	 */
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the mouse exits the game window. Override this if you want to listen to this.
	 * @param arg0 The mouse event
	 */
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when a mouse button is pressed. Override this if you want to listen to this.
	 * @param arg0 The mouse event
	 */
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when a mouse button is released. Override this if you want to listen to this.
	 * @param arg0 The mouse event
	 */
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Called when a key is typed. Not so smart to use, use Input.keys[] instead.<br />
	 * If you relly want it, override this if you want to listen to this.
	 * @param arg0 The key event
	 */
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Called when the game window gets focus. Override this if you want to listen to this
	 * @param e The focus event
	 */
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Called when the game window looses focus. Override this if you want to listen to this.
	 * @param e The focus event
	 */
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
}
