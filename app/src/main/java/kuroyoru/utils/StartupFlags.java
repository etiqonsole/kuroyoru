package kuroyoru.utils;

import org.tinylog.Logger;

public class StartupFlags {
	private int __flags = 0;

	public static final int DEVEL = 1;
	public static final int LAUNCH_HUB = 2;

	public static StartupFlags parse(String[] args) {
		StartupFlags flags = new StartupFlags();

		for (String arg: args) {
			switch (arg) {
				case "dev":
					flags.__flags += StartupFlags.DEVEL;
					break;
				case "launch-hub":
					flags.__flags += StartupFlags.LAUNCH_HUB;
					break;
			}
		}

		return flags;
	}

	public boolean contains(int flags) {
		return (this.__flags & flags) > 0;
	}
}