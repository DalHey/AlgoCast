
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
            Messaging.send("`G  /money `gstats &e Check all economic stats.");
        }

        Messaging.send(" ");
    }

    /**
     * Help documentation for iConomy all in one method.
     *
     * Allows us to easily utilize all throughout the class without having multiple
     * instances of the same help lines.
     */
    private void getBankHelp(CommandSender player) {
        Messaging.send("&e ");
        Messaging.send("&f iConomy (&c" + Constants.Codename + "&f)");
        Messaging.send("&e ");
        Messaging.send("&f [] Required, () Optional");
        Messaging.send(" ");
        Messaging.send("`G  /bank &e Check your bank accounts");
        Messaging.send("`G  /bank `g? &e For help & Information");

        if (iConomy.hasPermissions(player, "iConomy.bank.list")) {
            Messaging.send("`G  /bank `glist `G(`w#`G) &e Paged list of banks.");
        }

        if (iConomy.hasPermissions(player, "iConomy.bank.main")) {
            Messaging.send("`G  /bank `gmain &e View your main bank.");
        }

        if (iConomy.hasPermissions(player, "iConomy.bank.main.view")) {
            Messaging.send("`G  /bank `gmain `G[`waccount`G] &e View an accounts main bank.");
        }

        if (iConomy.hasPermissions(player, "iConomy.bank.main.set")) {
            Messaging.send("`G  /bank `gmain set `G[`wbank`G] &e Set your main bank.");
        }

        if (iConomy.hasPermissions(player, "iConomy.bank.join")) {
            Messaging.send("`G  /bank `gjoin `G[`wbank`G] &e Create an account with a bank.");
        }
        
        if (iConomy.hasPermissions(player, "iConomy.bank.leave")) {
            Messaging.send("`G  /bank `gleave `G[`wbank`G] &e Close an account with a bank.");
        }

        if (iConomy.hasPermissions(player, "iConomy.bank.transfer")) {
            Messaging.send("`G  /bank `gsend `G[`wto`G] `r[`wamount`r] &e Send money to another players bank.");
        }

        if (iConomy.hasPermissions(player, "iConomy.bank.transfer.multiple")) {
            Messaging.send("`G  /bank `G[`wfrom-bank`G] `gsend `G[`wto`G] `G[`wamount`G]");
        }

        if (iConomy.hasPermissions(player, "iConomy.bank.transfer.multiple")) {
            Messaging.send("`G  /bank `G[`wfrom-bank`G] `gsend `G[`wto-bank`G] `G[`wto`G] `G[`wamount`G]");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.bank.create")) {
            Messaging.send("`G  /bank `gcreate `G[`wbank`G] &e Create a bank.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.bank.remove")) {
            Messaging.send("`G  /bank `gremove `G[`wbank`G] &e Close a bank.");
        }

        if (iConomy.hasPermissions(player, "iConomy.admin.bank.set")) {
            Messaging.send("`G  /bank `G[`wbank`G] `gset `G[`wkey`G] `G[`wvalue`G] &e Create a bank.");
            Messaging.send("`y   Keys: `Yname`y, `Yinitial`y, `Ymajor`y, `Yminor`y, `Yfee");
        }

        Messaging.send(" ");
    }

    public boolean setHidden(String name, boolean hidden) {
        return iConomy.getAccount(name).setHidden(hidden);
    }

    /**
     * Account Creation
     */
    public void createAccount(String name) {
        iConomy.getAccount(name);
        Messaging.send(Template.color("tag.money") + Template.parse("accounts.create", new String[]{ "+name,+n" }, new String[]{ name }));
    }

    public void createBank(CommandSender sender, String bank) {
        if(iConomy.Banks.exists(bank)) {
            Messaging.send(sender, Template.color("error.bank.exists"));
            return;
        }

        Bank Bank = iConomy.Banks.create(bank);

        if(Bank == null) {
            Messaging.send(sender, Template.parse("error.bank.couldnt", new String[]{ "+bank,+b,+name,+n" }, new String[]{ bank }));
        } else {
            Messaging.send(sender, Template.parse("banks.create", new String[]{ "+bank,+b,+name,+n" }, new String[]{ bank }));
        }

        return;
    }

    public void createBank(CommandSender sender, String bank, Double initial, Double fee) {