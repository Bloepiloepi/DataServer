package io.github.bloepiloepi.DataServer.config;

import io.github.bloepiloepi.DataServer.console.Console;
import io.github.bloepiloepi.spear.objects.SPData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsBuilder {
	
	public static void setupSettings(File jarFileDirectory) throws IOException {
		File settingsFile = new File(jarFileDirectory, "settings.sp");
		if (!settingsFile.exists()) {
			SPData settings = SPData.loadFromString("");
			settings.setInteger("port", 1);
			settings.setString("root", "/root");
			
			ArrayList<Object> trustedIPs = new ArrayList<>();
			trustedIPs.add("exampleIP");
			settings.setList("trustedIPs", trustedIPs);
			
			settings.save(settingsFile);
			
			Console.fatal("Settings are not set! Please configure the settings file " + settingsFile.getAbsolutePath());
		} else {
			SPData settings = SPData.load(settingsFile);
			
			Integer port = settings.getInteger("port");
			String root = settings.getString("root");
			ArrayList<Object> objects = settings.getList("trustedIPs");
			
			if (port == null || root == null || objects == null) {
				Console.fatal("Settings are not set! Please configure the settings file " + settingsFile.getAbsolutePath());
			} else {
				
				ArrayList<String> trustedIPs = new ArrayList<>();
				for (Object object : objects) {
					if (object instanceof String) {
						trustedIPs.add((String) object);
					}
				}
				
				Settings.setup(root, port, trustedIPs);
			}
		}
	}
}
