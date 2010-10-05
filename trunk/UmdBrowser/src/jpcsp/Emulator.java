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
package jpcsp;

import org.apache.log4j.Logger;

/**
 * just a mock & logger
 */
public class Emulator {
	private static Emulator instance;
	public static boolean run = false;
	public static boolean pause = false;
	public static Logger log = Logger.getLogger("emu");

	public static final int EMU_STATUS_OK = 0x00;
	public static final int EMU_STATUS_UNKNOWN = 0xFFFFFFFF;
	public static final int EMU_STATUS_WDT_IDLE = 0x01;
	public static final int EMU_STATUS_WDT_HOG = 0x02;
	public static final int EMU_STATUS_WDT_ANY = EMU_STATUS_WDT_IDLE
			| EMU_STATUS_WDT_HOG;
	public static final int EMU_STATUS_MEM_READ = 0x04;
	public static final int EMU_STATUS_MEM_WRITE = 0x08;
	public static final int EMU_STATUS_MEM_ANY = EMU_STATUS_MEM_READ
			| EMU_STATUS_MEM_WRITE;
	public static final int EMU_STATUS_BREAKPOINT = 0x10;
	public static final int EMU_STATUS_UNIMPLEMENTED = 0x20;
	public static final int EMU_STATUS_PAUSE = 0x40;
	public static final int EMU_STATUS_JUMPSELF = 0x80;

	public static Emulator getInstance() {
		return instance;
	}

}