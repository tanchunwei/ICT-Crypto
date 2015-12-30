/* Project Title:	IT3119 Information Security Case Study
 * Project Group:	05
 * Author:			Tan Chun Wei
 */

package ui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Point;
public class WaitingBarForm{

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="40,7"
	private JPanel jContentPane = null;
	private Label label = null;

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
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
			jFrame.setDefaultLookAndFeelDecorated(true);
			jFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
			jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
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
