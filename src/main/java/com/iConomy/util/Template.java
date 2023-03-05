
package com.iConomy.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Template {

	private FileConfiguration tpl = null;
	private File f;

	public Template(String directory, String filename) {
		f = new File(directory, filename);
		this.tpl = YamlConfiguration.loadConfiguration(f);

		upgrade();
	}

	public void upgrade() {
		LinkedHashMap<String, String> nodes = new LinkedHashMap<String, String>();

		if (this.tpl.getString("error.bank.exists") == null) {
			nodes.put("tag.money", "<green>[<white>Money<green>] ");
			nodes.put("tag.bank", "<green>[<white>Bank<green>] ");