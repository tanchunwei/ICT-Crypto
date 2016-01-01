package ui;

import ict.util.CryptoUtil;
import ict.util.GeneralUtil;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

public class Main {
	WaitingBarForm wbf = new WaitingBarForm();
	Image image;;
	Icon closeIco;
	Icon decryptIco;
	Icon encryptIco;

	TrayIcon trayIcon;
	public char currentDrive;
	
	JScrollPane scroll = new JScrollPane();
	JPopupMenu popup = new JPopupMenu();
	JMenuItem exitItem;
	JMenuItem decryptItem;
	JMenuItem encryptItem;

	public void init() {
		initImages();
		initDrivePath();
		initItems();
		showTrayIcon();
	}

	public void initImages() {
		image = Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/images/main-icon.png"));
		closeIco = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/images/close-icon.png")));
		decryptIco = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/images/Unlock-icon.png")));
		encryptIco = new ImageIcon(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("/images/Lock-icon.png")));
	}
	
	public void initDrivePath(){
		File f = new File(".");
		try {
			String s;
			s = f.getCanonicalPath();
			currentDrive = s.charAt(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initItems(){
		initEncryptItem();
		initDecryptItem();
		initExitItem();
	}

	public void initDecryptItem() {
		decryptItem = new JMenuItem("Decrypt", decryptIco);
		decryptItem.addActionListener(initDecryptListener());

		popup.add(decryptItem);
	}
	
	private ActionListener initDecryptListener(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputFilepath = promptFileChooser();
				String outputFilepath = GeneralUtil.replaceUrlFileName(
						GeneralUtil.convertUrlToJavaUrl(inputFilepath),
						"(Decryption powered by Ice Turtle)");
				String password = promptPassword();
				String errorMsg = validateInputs(outputFilepath,
						password);

				if ("".equals(errorMsg)) {
					processDecrypt(inputFilepath, outputFilepath, password);
				} else {
					JOptionPane.showMessageDialog(null, errorMsg, "Alert",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private void processDecrypt(String inputFilepath,
					String outputFilepath, String password) {
				try {
					wbf.show("Decrypting file...");
					Runnable r1 = new Runnable() {
						public void run() {
							try {
								Thread.sleep(10);
							} catch (InterruptedException x) {
								x.printStackTrace();
							}
						}
					};
					new ProcessingDialog(new JFrame(), "Starting", true, r1);
					CryptoUtil.decryptFilePassword(
							GeneralUtil.convertUrlToJavaUrl(inputFilepath),
							GeneralUtil.convertUrlToJavaUrl(outputFilepath),
							password);

					wbf.dispose();
					JOptionPane.showMessageDialog(null, "File decrypted",
							"Alert", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace();
					wbf.dispose();
					JOptionPane.showMessageDialog(null,
							"Fail to decrypt\nInvaild password", "Alert",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	public void initEncryptItem() {
		encryptItem = new JMenuItem("Encrypt", encryptIco);
		encryptItem.addActionListener(initEncryptListener());

		popup.add(encryptItem);
	}
	
	private ActionListener initEncryptListener(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputFilepath = promptFileChooser();
				String outputFilepath = GeneralUtil.replaceUrlFileName(
						GeneralUtil.convertUrlToJavaUrl(inputFilepath),
						"(Encryption powered by Ice Turtle)");
				String password = promptPassword();
				String errorMsg = validateInputs(outputFilepath,
						password);
				
				if ("".equals(errorMsg)) {
					processEncrypt(inputFilepath, outputFilepath, password);
				} else {
					JOptionPane.showMessageDialog(null, errorMsg, "Alert",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private void processEncrypt(String inputFilepath,
					String outputFilepath, String password) {
				try {
					wbf.show("Encrypting file...");
					Runnable r1 = new Runnable() {
						public void run() {
							try {
								Thread.sleep(10);
							} catch (InterruptedException x) {
								x.printStackTrace();
							}
						}
					};
					new ProcessingDialog(new JFrame(), "Starting", true, r1);

					CryptoUtil.encryptFilePassword(
							GeneralUtil.convertUrlToJavaUrl(inputFilepath),
							GeneralUtil.convertUrlToJavaUrl(outputFilepath),
							password);

					wbf.dispose();
					JOptionPane.showMessageDialog(null, "File encrypted",
							"Alert", JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception e1) {
					e1.printStackTrace();
					wbf.dispose();
					JOptionPane.showMessageDialog(null, "Fail to encrypt",
							"Alert", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}
	
	private String promptFileChooser() {
		String filePath = "";
		JFileChooser chooser = new JFileChooser();
		int r = chooser.showOpenDialog(new JFrame());
		if (r == JFileChooser.APPROVE_OPTION) {
			filePath = chooser.getSelectedFile().getAbsolutePath();
		}
		return filePath;
	}
	
	private String promptPassword() {
		String password = "";
		JPasswordField passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		Object[] obj = { "password\n\n", passwordField };
		Object stringArray[] = { "OK", "Cancel" };
		if (JOptionPane.showOptionDialog(null, obj, "Need password",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION)
			password = new String(passwordField.getPassword());
		return password;
	}
	
	private String validateInputs(String filepathWithSignature,
			String password) {
		String errorMsg = "";
		if ("".equals(filepathWithSignature) || filepathWithSignature == null) {
			errorMsg = "Select a file";
		}
		if ("".equals(password) || password == null) {
			errorMsg = "Please enter a password";
		}
		return errorMsg;
	}
	
	
	public void initExitItem(){
		exitItem =  new JMenuItem("Exit", closeIco);
		exitItem.addActionListener(initExitListener());
		popup.add(exitItem);
		trayIcon = new TrayIcon(image, "Crypto", null);
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.setLocation(e.getX(), e.getY() - 115);
					popup.setInvoker(popup);
					popup.setVisible(true);
				}
			}
		});
	}
	
	private ActionListener initExitListener(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exiting...");
				System.exit(0);
			}
		};
	}
	
	public void showTrayIcon(){
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();

			trayIcon.setImageAutoSize(true);
			trayIcon.setImage(image);
			trayIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}
			});

			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}
		}
	}

	// Explorer /N,::{645FF040-5081-101B-9F08-00AA002F954E}
	public static void main(String[] a) throws Exception {
		new Main().init();

	}
}
