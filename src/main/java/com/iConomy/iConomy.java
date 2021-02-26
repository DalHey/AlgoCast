
package com.iConomy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.entity.Players;
import com.iConomy.net.Database;
import com.iConomy.system.Account;
import com.iConomy.system.Accounts;
import com.iConomy.system.Bank;
import com.iConomy.system.Banks;
import com.iConomy.system.Interest;
import com.iConomy.util.Constants;
import com.iConomy.system.Transactions;
import com.iConomy.util.Downloader;
import com.iConomy.util.FileManager;
import com.iConomy.util.Misc;

import java.text.DecimalFormat;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.server.PluginEnableEvent;

/**
 * iConomy by Team iCo
 *
 * @copyright     Copyright AniGaiku LLC (C) 2010-2011
 * @author          Nijikokun <nijikokun@gmail.com>
 * @author          Coelho <robertcoelho@live.com>
 * @author       ShadowDrakken <shadowdrakken@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class iConomy extends JavaPlugin {
    public static Banks Banks = null;
    public static Accounts Accounts = null;

    private static Server Server = null;
    private static Database Database = null;
    private static Transactions Transactions = null;
    private static Players playerListener = null;
    private static Timer Interest_Timer = null;

    @Override
    public void onEnable() {
        Locale.setDefault(Locale.US);

        // Get the server
        Server = getServer();

        // Lib Directory
        (new File("lib" + File.separator)).mkdir();
        (new File("lib" + File.separator)).setWritable(true);
        (new File("lib" + File.separator)).setExecutable(true);

        // Plugin Directory
        getDataFolder().mkdir();
        getDataFolder().setWritable(true);
        getDataFolder().setExecutable(true);

        // Setup the path.
        Constants.Plugin_Directory = getDataFolder().getPath();

        // Grab plugin details
        PluginDescriptionFile pdfFile = this.getDescription();

        // Versioning File
        FileManager file = new FileManager(getDataFolder().getPath(), "VERSION", false);

        // Default Files
        extract("Config.yml");
        extract("Template.yml");

        try {
            Constants.load(YamlConfiguration.loadConfiguration(new File(getDataFolder(), "Config.yml")));
        } catch (Exception e) {
            Server.getPluginManager().disablePlugin(this);
            System.out.println("[iConomy] Failed to retrieve configuration from directory.");
            System.out.println("[iConomy] Please back up your current settings and let iConomy recreate it.");
            return;
        }

        if(Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h2sql", "h2db" })) {
        } else {
            if(!(new File("lib" + File.separator, "mysql-connector-java-bin.jar").exists())) {
                Downloader.install(Constants.MySQL_Jar_Location, "mysql-connector-java-bin.jar");
            }
        }

        try {
            Database = new Database();
            Database.setupAccountTable();

            if(Constants.Banking) {
                Database.setupBankTable();
                Database.setupBankRelationTable();
            }
        } catch (Exception e) {
            System.out.println("[iConomy] Database initialization failed: " + e);
            Server.getPluginManager().disablePlugin(this);
            return;

        }

        try {
            Transactions = new Transactions();
            Database.setupTransactionTable();
        } catch (Exception e) {
            System.out.println("[iConomy] Could not load transaction logger: " + e);
        }

        // Check version details before the system loads
        update(file, Double.valueOf(pdfFile.getVersion()));

        // Initialize default systems
        Accounts = new Accounts();
        
        // Initialize the banks
        if(Constants.Banking)
            Banks = new Banks();

        try {
            if (Constants.Interest) {
                long time = Constants.InterestSeconds * 1000L;

                Interest_Timer = new Timer();
                Interest_Timer.scheduleAtFixedRate(new Interest(getDataFolder().getPath()), time, time);
            }
        } catch (Exception e) {
            System.out.println("[iConomy] Failed to start interest system: " + e);
            Server.getPluginManager().disablePlugin(this);
            return;
        }

        // Initializing Listeners
        playerListener = new Players(getDataFolder().getPath(),this);
        
        // Console Detail
        System.out.println("[iConomy] v" + pdfFile.getVersion() + " (" + Constants.Codename + ") loaded.");
        System.out.println("[iConomy] Developed by: " + pdfFile.getAuthors());
    }

    @Override
    public void onDisable() {
        try {
            if(Misc.is(Constants.DatabaseType, new String[] { "sqlite", "h2", "h2sql", "h2db" })) {
                Database.connectionPool().dispose();
            }
            
            System.out.println("[iConomy] Plugin disabled.");
        } catch (Exception e) {
            System.out.println("[iConomy] Plugin disabled.");
        } finally {
            if (Interest_Timer != null) {
                Interest_Timer.cancel();
            }

            Server = null;
            Banks = null;
            Accounts = null;
            Database = null;
            Transactions = null;
            playerListener = null;
            Interest_Timer = null;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String[] split = new String[args.length + 1];
        split[0] = cmd.getName().toLowerCase();
        System.arraycopy(args, 0, split, 1, args.length);

        playerListener.onPlayerCommand(sender, split);
        return false;
    }

    private void update(FileManager file, double version) {
        if (file.exists()) {
            file.read();

            try {
                double current = Double.parseDouble(file.getSource());
                LinkedList<String> MySQL = new LinkedList<String>();
                LinkedList<String> GENERIC = new LinkedList<String>();
                LinkedList<String> SQL = new LinkedList<String>();

                if(current != version) {
                    if(current < 4.64) {
                        MySQL.add("ALTER TABLE " + Constants.SQLTable + " ADD hidden boolean DEFAULT '0';");
                        GENERIC.add("ALTER TABLE " + Constants.SQLTable + " ADD HIDDEN BOOLEAN DEFAULT '0';");
                    }

                    if(current < 4.62) {
                        MySQL.add("ALTER IGNORE TABLE " + Constants.SQLTable + " ADD UNIQUE INDEX(username(32));");
                        GENERIC.add("ALTER TABLE " + Constants.SQLTable + " ADD UNIQUE(username);");