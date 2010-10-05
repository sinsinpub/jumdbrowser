package jpcsp;
/*
This file is part of jpcsp.

Jpcsp is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jpcsp is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Jpcsp.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jpcsp.util.Utilities;

/**
 * @author spip2001
 */
public class Settings {
	private final static String SETTINGS_FILE_NAME = "Settings.properties";
	private final static String DEFAULT_SETTINGS_FILE_NAME = "/jpcsp/DefaultSettings.properties";
	private static Settings instance = null;
	private Properties defaultSettings;
	private SortedProperties loadedSettings;

	public static Settings getInstance() {
		if (instance == null) instance = new Settings();
		return instance;
	}

	public void NullSettings() {
		instance = null;
	}

	private Settings() {
		defaultSettings = new Properties();
        InputStream defaultSettingsStream = null, loadedSettingsStream = null;
		try {
			defaultSettingsStream = getClass().getResourceAsStream(DEFAULT_SETTINGS_FILE_NAME);
			defaultSettings.load(defaultSettingsStream);
			loadedSettings = new SortedProperties(defaultSettings);
			File settingsFile = new File(SETTINGS_FILE_NAME);
			settingsFile.createNewFile();
            loadedSettingsStream = new BufferedInputStream(new FileInputStream(settingsFile));
			loadedSettings.load(loadedSettingsStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			Utilities.close(defaultSettingsStream, loadedSettingsStream);
		}
	}

	/**
	 * Write settings in file
	 *
	 * @param doc
	 *        Settings as XML document
	 */
	private void writeSettings() {
                BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(SETTINGS_FILE_NAME));
			loadedSettings.store(out, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			Utilities.close(out);
        }
	}

	public Point readWindowPos(String windowname) {
		String x = loadedSettings.getProperty("gui.windows." + windowname + ".x");
		String y = loadedSettings.getProperty("gui.windows." + windowname + ".y");

		Point position = new Point();
		position.x = x != null ? Integer.parseInt(x) : 0;
		position.y = y != null ? Integer.parseInt(y) : 0;

		return position;
	}

	public Dimension readWindowSize(String windowname, int defaultWidth, int defaultHeight) {
		String w = loadedSettings.getProperty("gui.windows." + windowname + ".w");
		String h = loadedSettings.getProperty("gui.windows." + windowname + ".h");

		Dimension dimension = new Dimension();
		dimension.width = w != null ? Integer.parseInt(w) : defaultWidth;
		dimension.height = h != null ? Integer.parseInt(h) : defaultHeight;

		return dimension;
	}

	public void writeWindowPos(String windowname, Point pos) {
		loadedSettings.setProperty("gui.windows." + windowname + ".x", Integer.toString(pos.x));
		loadedSettings.setProperty("gui.windows." + windowname + ".y", Integer.toString(pos.y));
		writeSettings();
	}

	public void writeWindowSize(String windowname, Dimension dimension) {
		loadedSettings.setProperty("gui.windows." + windowname + ".w", Integer.toString(dimension.width));
		loadedSettings.setProperty("gui.windows." + windowname + ".h", Integer.toString(dimension.height));
		writeSettings();
	}

	public boolean readBool(String option) {
		String bool = loadedSettings.getProperty(option);
		if(bool == null) return false;

		return Integer.parseInt(bool) != 0;
	}

	public void writeBool(String option, boolean value) {
		String state = value ? "1" : "0";
		loadedSettings.setProperty(option, state);
		writeSettings();
	}

	public String readString(String option) {
            return loadedSettings.getProperty(option, "");
	}

	public void writeString(String option, String value) {
		loadedSettings.setProperty(option, value);
		writeSettings();
	}

	private static class SortedProperties extends Properties {

		private static final long serialVersionUID = -8127868945637348944L;

		public SortedProperties(Properties defaultSettings) {
			super(defaultSettings);
		}
/*
		@Override
		public synchronized Enumeration keys() {
			Enumeration keysEnum = super.keys();
			List keyList = Collections.list(keysEnum);
            Collections.sort(keyList);
			return Collections.enumeration(keyList);
		}
*/
	}

    /**
     * Reads the following settings:
     * gui.memStickBrowser.font.name=SansSerif
     * gui.memStickBrowser.font.file=
     * gui.memStickBrowser.font.size=11
     * @return      Tries to return a font in this order:
     *              - Font from local file (somefont.ttf),
     *              - Font registered with the operating system,
     *              - SansSerif, Plain, 11.
     */
    private Font loadedFont = null;
    public Font getFont() {
        if (loadedFont != null) {
            return loadedFont;
        }

        Font font = new Font("SansSerif", Font.PLAIN, 1);
        int fontsize = 11;

        try {
            Font base = font; // Default font
            String fontname = readString("gui.font.name");
            String fontfilename = readString("gui.font.file");
            String fontsizestr = readString("gui.font.size");

            if (fontfilename.length() != 0) {
                // Load file font
                File fontfile = new File(fontfilename);
                if (fontfile.exists()) {
                    base = Font.createFont(Font.TRUETYPE_FONT, fontfile);
                } else {
                    System.err.println("gui.font.file '" + fontfilename + "' doesn't exist.");
                }
            } else if (fontname.length() != 0) {
                // Load system font
                base = new Font(fontname, Font.PLAIN, 1);
            }

            // Set font size
            if (fontsizestr.length() > 0) fontsize = Integer.parseInt(fontsizestr);
            else System.err.println("gui.font.size setting is missing.");

            font = base.deriveFont(Font.PLAIN, fontsize);

            // register font as a font family so we can use it in StyledDocument's
            java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(base);
        } catch(NumberFormatException e) {
            System.err.println("gui.font.size setting is invalid.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        loadedFont = font;
        return font;
    }
}