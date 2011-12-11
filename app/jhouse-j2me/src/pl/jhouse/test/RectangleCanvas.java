package pl.jhouse.test;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 * @author tomekk
 * @since 2009-11-29 00:31:30
 */
public class RectangleCanvas extends Canvas {

	private final static int boxSize = 20;

	private int x;
	private int y;

	protected void paint(Graphics graphics) {
		graphics.setColor(0xffffff);
		graphics.fillRect(0,0,getWidth(),getHeight());
		graphics.setColor(0x000000);
		graphics.fillRect(x*boxSize,y*boxSize,boxSize,boxSize);
		System.out.println("repainted x:"+x);
	}

	protected void keyPressed(int keyCode) {
		int gameKey = getGameAction(keyCode);
		switch (gameKey) {
			case LEFT : x--;
				break;
			case RIGHT : x++;
				break;
			case UP : y--;
				break;
			case DOWN : y++;
				break;
		}
		repaint();
	}


	private void clear(Graphics graphics) {
		graphics.setColor(0xffffff);
		graphics.fillRect(0,0,getWidth(),getHeight());
	}

	private void drawRectangle(Graphics graphics) {
		graphics.setColor(0x000000);
		graphics.fillRect(x*boxSize,y*boxSize,boxSize,boxSize);
	}

	protected void keyRepeated(int keyCode) {
		System.out.println(keyCode);
	}
}
