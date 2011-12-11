package pl.jhouse.test;

import javax.microedition.lcdui.*;

/**
 * @author tomekk
 * @since 2010-01-02 01:21:27
 */
public class GraphCanvas extends Canvas {

	private Image image;
	private String info;

	public GraphCanvas(final Display display, final Form backForm) {
		this.setFullScreenMode(true);
		addCommand(new Command("Back",Command.BACK,0));
		setCommandListener(new CommandListener() {
			public void commandAction(Command command, Displayable displayable) {
				display.setCurrent(backForm);
			}
		});
	}

	protected void paint(Graphics graphics) {
		setFullScreenMode(true);
		if (image != null) {
			graphics.drawImage(image,0,0,Graphics.TOP | Graphics.LEFT);
		} else {
			graphics.drawString(info,0,0,Graphics.HCENTER | Graphics.VCENTER);
		}
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
