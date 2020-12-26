
package com.iConomy.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.iConomy.system.Bank;
import com.iConomy.system.BankAccount;
import com.iConomy.system.Holdings;
import com.iConomy.util.Constants;
import com.iConomy.util.Messaging;
import com.iConomy.util.Misc;
import com.iConomy.util.Template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Handles the command usage and account creation upon a
 * player joining the server.
 *
 * @author Nijikokun
 */
public class Players implements Listener{
    private Template Template = null;

    /**
     * Initialize the class as well as the template for various
     * messages throughout the commands.
     *
     * @param directory
     */
    public Players(String directory,iConomy plugin) {
        this.Template = new Template(directory, "Template.yml");
				Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Help documentation for iConomy all in one method.
     *
     * Allows us to easily utilize all throughout the class without having multiple
     * instances of the same help lines.
     */
    private void getMoneyHelp(CommandSender player) {
        Messaging.send("&e ");
        Messaging.send("&f iConomy (&c" + Constants.Codename + "&f)");
        Messaging.send("&e ");
        Messaging.send("&f [] Required, () Optional");
        Messaging.send(" ");
        Messaging.send("`G  /money &e Check your balance");
        Messaging.send("`G  /money `g? &e For help & Information");

        if (iConomy.hasPermissions(player, "iConomy.rank")) {
            Messaging.send("`G  /money `grank `G(`wplayer`G) &e Rank on the topcharts.   ");
        }
        
        if (iConomy.hasPermissions(player, "iConomy.list")) {
            Messaging.send("`G  /money `gtop `G(`wamount`G) &e Richest players listing.  ");
        }

        if (iConomy.hasPermissions(player, "iConomy.payment")) {
            Messaging.send("`G  /money `gpay `G[`wplayer`G] [`wamount`G] &e Send money to a player.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.grant")) {
            Messaging.send("`G  /money `ggrant `G[`wplayer`G] [`wamount`G] &e Give money.");
            Messaging.send("`G  /money `ggrant `G[`wplayer`G] -[`wamount`G] &e Take money.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.set")) {
            Messaging.send("`G  /money `gset `G[`wplayer`G] [`wamount`G] &e Sets a players balance.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.hide")) {
            Messaging.send("`G  /money `ghide `G[`wplayer`G] `wtrue`G/`wfalse &e Hide or show an account.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.account.create")) {
            Messaging.send("`G  /money `gcreate `G[`wplayer`G] &e Create player account.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.account.remove")) {
            Messaging.send("`G  /money `gremove `G[`wplayer`G] &e Remove player account.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.reset")) {
            Messaging.send("`G  /money `greset `G[`wplayer`G] &e Reset player account.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.purge")) {
            Messaging.send("`G  /money `gpurge &e Remove all accounts with inital holdings.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.empty")) {
            Messaging.send("`G  /money `gempty &e Empties database.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.stats")) {