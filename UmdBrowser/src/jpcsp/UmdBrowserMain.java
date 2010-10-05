package jpcsp;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.swing.UIManager;

import jpcsp.GUI.UmdBrowser;

import org.apache.log4j.PropertyConfigurator;

/**
 * UmdBrowser boot strap
 * 
 * @author sin_sin
 */
public class UmdBrowserMain {

	private static UmdBrowser umdBrowser;

	public static void main(String[] args) {
		// Load log4j settings
		try {
			Properties log4j = new Properties();
			log4j.load(UmdBrowserMain.class
					.getResourceAsStream("/jpcsp/log4j.properties"));
			PropertyConfigurator.configure(log4j);
		} catch (IOException e) {
		}
		// Set Swing Look&feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		// Load message resources
		Resource.add("jpcsp.languages."
				+ Settings.getInstance().readString("emu.language"));
		// Start UmdBrowser instance
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Thread.currentThread()
						.setName(UmdBrowser.windowNameForSettings);
				String umdPath = Settings.getInstance().readString(
						"emu.umdpath");
				if (!umdPath.endsWith(File.separator))
					umdPath = umdPath.concat(File.separator);
				umdBrowser = new UmdBrowser(new File(umdPath));
				umdBrowser.setVisible(true);
			}
		});
	}
}
