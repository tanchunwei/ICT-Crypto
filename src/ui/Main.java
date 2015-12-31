package ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import util.CryptoUtil;
import util.GeneralUtil;
import util.LnkParser;

public class Main {
	WaitingBarForm wbf = new WaitingBarForm();
	Image image;;
	Icon closeIco;
	Icon decryptIco;
	Icon encryptIco;

	TrayIcon trayIcon;
	public char currentDrive;
	public String filename;
	public boolean isIcon;
	public Icon icon;
	public LnkParser lnkFile;
	String[] files;

	ActionListener exitListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Exiting...");
			System.exit(0);
		}
	};

	ActionListener viewListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		}
	};

	JScrollPane scroll = new JScrollPane();
	ArrayList<JMenuItem> games = new ArrayList<JMenuItem>();
	ArrayList<ActionListener> gamesAl = new ArrayList<ActionListener>();
	ArrayList<JMenuItem> applications = new ArrayList<JMenuItem>();
	ArrayList<ActionListener> applicationsAl = new ArrayList<ActionListener>();
	JPopupMenu popup = new JPopupMenu();
	MenuItem openTrayItem = new MenuItem("View Info");
	MenuItem manageItem = new MenuItem("Manage Info");
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
		decryptItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				String name = "";
				String password = "";
				int r = chooser.showOpenDialog(new JFrame());
				if (r == JFileChooser.APPROVE_OPTION) {
					name = chooser.getSelectedFile().getAbsolutePath();
				}
				// ****************
				chooser = new JFileChooser();

				String name1 = GeneralUtil.replaceUrlFileName(
						GeneralUtil.convertUrlToJavaUrl(name),
						"(Decryption powered by Ice Turtle)");
				String errorMsg = "";

				JPasswordField passwordField = new JPasswordField();
				passwordField.setEchoChar('*');
				Object[] obj = { "password\n\n", passwordField };
				Object stringArray[] = { "OK", "Cancel" };
				if (JOptionPane.showOptionDialog(null, obj, "Need password",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION)
					password = passwordField.getText();

				if ("".equals(name1) || name1 == null) {
					errorMsg = "Select a file";
				}
				if ("".equals(password) || password == null) {
					errorMsg = "Please enter a password";
				}

				if ("".equals(errorMsg)) {
					try {
						/*
						 * long fileSize =
						 * GeneralUtil.getFileSize(GeneralUtil.convertUrlToJavaUrl
						 * (name)); System.out.print(fileSize);
						 */

						/*
						 * UIManager.put("ProgressMonitor.progressText",
						 * "This is progress?");
						 * UIManager.put("OptionPane.cancelButtonText",
						 * "Cancel"); new ProgressMonitorExample(fileSize);
						 */

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
								GeneralUtil.convertUrlToJavaUrl(name),
								GeneralUtil.convertUrlToJavaUrl(name1),
								password);

						wbf.dispose();
						JOptionPane.showMessageDialog(null, "File decrypted",
								"Alert", JOptionPane.INFORMATION_MESSAGE);
						name = "";
						name1 = "";
						password = "";

					} catch (Exception e1) {
						e1.printStackTrace();
						wbf.dispose();
						JOptionPane.showMessageDialog(null,
								"Fail to decrypt\nInvaild password", "Alert",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, errorMsg, "Alert",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		popup.add(decryptItem);
	}

	public void initEncryptItem() {
		encryptItem = new JMenuItem("Encrypt", encryptIco);
		encryptItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				String name = "";
				String password = "";
				int r = chooser.showOpenDialog(new JFrame());
				if (r == JFileChooser.APPROVE_OPTION) {
					name = chooser.getSelectedFile().getAbsolutePath();
				}
				// ****************
				chooser = new JFileChooser();

				String name1 = GeneralUtil.replaceUrlFileName(
						GeneralUtil.convertUrlToJavaUrl(name),
						"(Encryption powered by Ice Turtle)");
				String errorMsg = "";

				JPasswordField passwordField = new JPasswordField();
				passwordField.setEchoChar('*');
				Object[] obj = { "password\n\n", passwordField };
				Object stringArray[] = { "OK", "Cancel" };
				if (JOptionPane.showOptionDialog(null, obj, "Need password",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION)
					password = passwordField.getText();

				if ("".equals(name1) || name1 == null) {
					errorMsg = "Select a file";
				}
				if ("".equals(password) || password == null) {
					errorMsg = "Please enter a password";
				}

				if ("".equals(errorMsg)) {
					try {
						/*
						 * long fileSize =
						 * GeneralUtil.getFileSize(GeneralUtil.convertUrlToJavaUrl
						 * (name)); System.out.print(fileSize);
						 */

						/*
						 * UIManager.put("ProgressMonitor.progressText",
						 * "This is progress?");
						 * UIManager.put("OptionPane.cancelButtonText",
						 * "Cancel"); new ProgressMonitorExample(fileSize);
						 */

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
								GeneralUtil.convertUrlToJavaUrl(name),
								GeneralUtil.convertUrlToJavaUrl(name1),
								password);

						wbf.dispose();
						JOptionPane.showMessageDialog(null, "File encrypted",
								"Alert", JOptionPane.INFORMATION_MESSAGE);
						name = "";
						name1 = "";
						password = "";

					} catch (Exception e1) {
						e1.printStackTrace();
						wbf.dispose();
						JOptionPane.showMessageDialog(null, "Fail to encrypt",
								"Alert", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, errorMsg, "Alert",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		popup.add(encryptItem);
	}
	
	public void initExitItem(){
		exitItem =  new JMenuItem("Exit", closeIco);
		exitItem.addActionListener(exitListener);
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
