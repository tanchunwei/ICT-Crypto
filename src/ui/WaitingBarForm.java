/* Project Title:	IT3119 Information Security Case Study
 * Project Group:	05
 * Author:			Tan Chun Wei
 */

package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Rectangle;
public class WaitingBarForm{

	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private Label label = null;

	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(316, 79));
			jFrame.setLocationRelativeTo(null);
			jFrame.setTitle("Processing");
			//jFrame.setLocation(new Point(0, 0));
			jFrame.setContentPane(getJContentPane());
			jFrame.setAlwaysOnTop(true);
			
			jFrame.setResizable(false);
			jFrame.setUndecorated(true);
			JFrame.setDefaultLookAndFeelDecorated(true);
			jFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
			jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
		}
		return jFrame;
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			label = new Label();
			label.setBounds(new Rectangle(19, 12, 284, 23));
			label.setText("Decrypting In Progress...");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(label, null);
		}
		return jContentPane;
	}

	public void show(String message){
		this.getJFrame().setVisible(true);
		label.setText(message);
	}
	
	public void dispose(){
		this.getJFrame().dispose();
	}
	
	
	public static void main(String args[]){
		WaitingBarForm Page = new WaitingBarForm();
		Page.show("test");
	}
}
