package pl.jhouse.test;

import com.caucho.hessian.micro.MicroHessianInput;
import com.caucho.hessian.micro.MicroHessianOutput;

import javax.microedition.io.Connector;
import javax.microedition.io.ContentConnection;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import java.io.*;

/**
 * @author tomekk
 * @since 2009-11-27 02:00:13
 */
public class HessianMicroClient extends MIDlet implements CommandListener {

	private Form mMainForm;
	private Display mDisplay;
	private StringItem mMessageItem = new StringItem(null, "");

	private GraphCanvas pngForm;

	private Command hessian = new Command("Hessian", Command.SCREEN, 1);
	private Command exit = new Command("Exit", Command.EXIT, 0);
	private Command graph = new Command("Graph", Command.SCREEN, 2);
	private Command canvas = new Command("Canvas", Command.SCREEN, 2);

	public HessianMicroClient() {
		mMainForm = new Form("JHouse window");

		mMessageItem.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_SMALL));
		mMainForm.append(mMessageItem);
//		mMainForm.
		mMainForm.addCommand(exit);
		mMainForm.addCommand(hessian);
		mMainForm.addCommand(graph);
		mMainForm.addCommand(canvas);
		mMainForm.setCommandListener(this);

	}

	public void startApp() {
		mDisplay = Display.getDisplay(this);
		mDisplay.setCurrent(mMainForm);
		pngForm = new GraphCanvas(mDisplay,mMainForm);
	}

	public void pauseApp() {}

	public void destroyApp(boolean unconditional) {}

	public void commandAction(Command c, Displayable s) {
		if (hessian.equals(c)) {
			Form waitForm = new Form("Waiting...");
			mDisplay.setCurrent(waitForm);
			Thread t =  new Thread() {
				public void run() {
					String result = invokeHessian();
					mMessageItem.setText(result);
					mDisplay.setCurrent(mMainForm);
				}
			};
			t.start();
		} else if (exit.equals(c)) {
			notifyDestroyed();
		} else if (canvas.equals(c)) {
			mDisplay.setCurrent(new RectangleCanvas());
		} else if (graph.equals(c)) {
			Form waitForm = new Form("Waiting...");
			mDisplay.setCurrent(waitForm);
			Thread t =  new Thread() {
				public void run() {
					try
					{
						//get width and height
						int width = mDisplay.getCurrent().getWidth();
						int height = mDisplay.getCurrent().getHeight();
						System.out.println("width:"+width);
						System.out.println("height:"+height);

						Image im = getImage("http://localhost:8080/jhouse-server-0.0.1-SNAPSHOT/graph/my.png?width="+width+"&height="+height+"&devices=sd/sd/sd");
						pngForm.setImage(im);
						if (im == null) {
							pngForm.setInfo("Unsuccessful download.");
						}

						// Display the form with the image
						mDisplay.setCurrent(pngForm);
					}
					catch (Exception e)
					{
						System.err.println("Msg: " + e.toString());
					}
				}
			};
			t.start();
		}
	}

	public String invokeHessian() {
		//To change body of implemented methods use File | Settings | File Templates.
		String url = "http://kucharski.homedns.org:8080/jhouse-server-0.0.1-SNAPSHOT/remoting/test";
		System.out.println(url);
		try {
			System.out.println("getting connecting");
			HttpConnection c = (HttpConnection) Connector.open(url);
			System.out.println("connection established");
			c.setRequestMethod(HttpConnection.POST);

			System.out.println("opening stream");
			OutputStream os = c.openOutputStream();
			MicroHessianOutput out = new MicroHessianOutput(os);
			out.startCall("getTheNewestValues");
			out.writeString("BLEBLE");
			out.completeCall();
			os.flush();

			InputStream is = c.openInputStream();
			MicroHessianInput in = new MicroHessianInput(is);
			in.startReply();
			String result = in.readString();
			in.completeReply();
			return result;

		} catch (Exception e) {
			e.printStackTrace();

			return "ERROR: ErrorClass:'"+e.getClass()+"', Message: '" + e.getMessage()+"'";
		}
	}

	private Image getImage(String url) throws IOException
	{
		ContentConnection connection = (ContentConnection) Connector.open(url);

		InputStream iStrm = connection.openInputStream();

		ByteArrayOutputStream bStrm = null;
		Image im = null;

		try
		{
			// ContentConnection includes a length method
			byte imageData[];
			int length = (int) connection.getLength();
			if (length != -1)
			{
				imageData = new byte[length];
				// Read the png into an array
				iStrm.read(imageData);
			}
			// Length not available.
			else  {
				bStrm = new ByteArrayOutputStream();

				int ch;
				while ((ch = iStrm.read()) != -1)
					bStrm.write(ch);

				imageData = bStrm.toByteArray();
				bStrm.close();
			}

			// Create the image from the byte array
			im = Image.createImage(imageData, 0, imageData.length);
		}
		finally
		{
			// Clean up
			if (iStrm != null) {
				iStrm.close();
			}
			connection.close();
			if (bStrm != null) {
				bStrm.close();
			}
		}
		return (im == null ? null : im);
	}
}
