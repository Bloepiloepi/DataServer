package io.github.bloepiloepi.DataServer.security;

import io.github.bloepiloepi.DataServer.config.Settings;

import java.util.ArrayList;

public class SecurityManager {
	
	private static ArrayList<String> allowedIPs = null;
	
	public static boolean isAllowed(String hostAddress) {
		if (allowedIPs == null) {
			allowedIPs = Settings.getTrustedHostAddresses();
		}
		return allowedIPs.contains(hostAddress);
	}
}