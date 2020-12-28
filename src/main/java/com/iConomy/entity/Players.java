
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
        if(iConomy.Banks.exists(bank)) {
            Messaging.send(sender, Template.color("error.bank.exists"));
            return;
        }

        Bank Bank = iConomy.Banks.create(bank);

        if(Bank == null) {
            Messaging.send(sender, Template.parse("error.bank.couldnt", new String[]{ "+bank,+b,+name,+n" }, new String[]{ bank }));
        } else {
            Bank.setInitialHoldings(initial);
            Bank.setFee(fee);

            Messaging.send(sender, Template.parse("banks.create", new String[]{ "+bank,+b,+name,+n" }, new String[]{ bank }));
        }

        return;
    }

    public void setBankValue(CommandSender sender, String bank, String key, Object value) {
        if(!iConomy.Banks.exists(bank)) {
            Messaging.send(sender, Template.parse("error.bank.doesnt", new String[]{ "+bank,+b,+name,+n" }, new String[]{ bank }));
            return;
        }
        
        Bank Bank = iConomy.getBank(bank);

        if(key.equals("initial")) {
            Double initial = Double.valueOf(value.toString());
            Bank.setInitialHoldings(initial);
        } else if(key.equals("major")) {
            if(!value.toString().contains(",")) {
                Messaging.send(sender, "`rMajor value is missing seperator between single and plural.");
                Messaging.send(sender, "`r  Ex: `s/bank [`Sname`s] set `Smajor`s Dollar`S,`sDollars");
                return;
            }

            String[] line = value.toString().split(",");

            if(line[0].isEmpty() || line.length < 1 || line[1].isEmpty()) {
                Messaging.send(sender, "`rMinor value is missing a single `Ror`r plural.");
                Messaging.send(sender, "`r  Ex: `s/bank [`Sname`s] set `Smajor`s Dollar`S,`sDollars");
                return;
            }

            Bank.setMajor(line[0], line[1]);
        } else if(key.equals("minor")) {
            if(!value.toString().contains(",")) {
                Messaging.send(sender, "`rMinor value is missing seperator between single and plural.");
                Messaging.send(sender, "`r  Ex: `s/bank [`Sname`s] set `Sminor`s Coin`S,`sCoins");
                return;
            }

            String[] line = value.toString().split(",");

            if(line[0].isEmpty() || line.length < 1 || line[1].isEmpty()) {
                Messaging.send(sender, "`rMinor value is missing a single `Ror`r plural.");
                Messaging.send(sender, "`r  Ex: `s/bank [`Sname`s] set `Sminor`s Coin`S,`sCoins");
                return;
            }

            Bank.setMinor(line[0], line[1]);
        } else if(key.equals("fee")) {
            Double fee = Double.valueOf(value.toString());
            Bank.setFee(fee);
        } else if(key.equals("name")) {
            Bank.setName(value.toString());
        }

        Messaging.send(sender,
            Template.color("tag.bank") + Template.parse("banks.set",
                new String[] { "+bank,+name,+n,+b", "+key,+k", "+value,+val,+v" },
                new Object[] { bank, key, value }
            )
        );
    }

    public void createBankAccount(CommandSender sender, String name, String player) {
        Bank bank = iConomy.getBank(name);

        if(!iConomy.hasAccount(player)) {
            Messaging.send(sender, Template.color("error.bank.account.none"));
            return;
        }

        Account account = iConomy.getAccount(player);

        if(bank == null) {
            Messaging.send(sender, Template.parse("error.bank.doesnt", new String[] { "+bank,+name,+b,+n" }, new String[] { name }));
            return;
        }

        int count = iConomy.Banks.count(player);

        if(count > 1 && !Constants.BankingMultiple || !iConomy.hasPermissions(sender, "iConomy.bank.join.multiple")) {
            Messaging.send(sender, Template.color("error.bank.account.maxed"));
            return;
        }

        if(bank != null) {
            double fee = bank.getFee();
            if(fee > account.getHoldings().balance()) {
                Messaging.send(sender, Template.color("error.bank.account.funds"));
                return;
            }

            if(bank.createAccount(player)){
                account.getHoldings().subtract(fee);

                Messaging.send(sender, Template.color("tag.bank") + Template.parse("accounts.bank.create",
                    new String[] { "+bank,+b", "+name,+n" },
                    new String[] { name, player })
                );

                if(count == 0) {
                    iConomy.getAccount(player).setMainBank(bank.getId());
                }

                return;
            } else {
                Messaging.send(sender, Template.color("error.bank.account.failed"));
            }
        } else {
            Messaging.send(sender, Template.color("error.bank.account.none"));
        }
    }
    
    /**
     * Account Removal
     */
    public void removeAccount(String name) {
        iConomy.Accounts.remove(name);
        Messaging.send(Template.color("tag.money") + Template.parse("accounts.remove", new String[]{ "+name,+n" }, new String[]{ name }));
    }

    public void removeBankAccount(CommandSender sender, String name, String player) {
        Bank bank = iConomy.getBank(name);

        if(!iConomy.hasAccount(player)) {
            Messaging.send(Template.color("error.bank.account.none"));
            return;
        }

        if(bank == null) {
            Messaging.send(Template.parse("error.bank.doesnt", new String[] { "+bank,+name,+b,+n" }, new String[] { name }));
            return;
        }

        if(!bank.hasAccount(player)) {
            Messaging.send(Template.parse("error.bank.account.doesnt", new String[] { "+name,+n" }, new String[] { player }));
            return;
        }

        bank.removeAccount(player);

        Messaging.send(Template.color("tag.bank") + Template.parse("accounts.bank.remove",
            new String[] { "+bank,+b", "+name,+n" },
            new String[] { name, player })
        );
    }

    /**
     * Show list of banks
     *
     * @param player
     * @param name
     */
    public void showBankList(CommandSender player, int current) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        LinkedList<Bank> banks = new LinkedList<Bank>();
        int total = iConomy.Banks.count();
        int perPage = 7;
        int page = (current <= 0) ? 1 : current;
        int totalPages = (int) (((total % perPage) == 0) ? total / perPage : Math.floor(total / perPage) + 1);
        int start = (page-1) * perPage;
        String entry = (Constants.BankFee != 0.0) ? (Constants.FormatMinor) ? "list.banks.all-entry" : "list.banks.fee-major-entry" : (Constants.FormatMinor) ? "list.banks.entry" : "list.banks.major-entry";

        page = (page > totalPages) ? totalPages : page;

        if(total == -1){
            Messaging.send(player, Template.parse("list.banks.opening", new String[]{ "+amount,+a", "+total,+t" }, new Object[]{ 0, 0 }));
            Messaging.send(player, Template.color("list.banks.empty"));
            return;
        }

        try {
            conn = iConomy.getiCoDatabase().getConnection();
            ps = conn.prepareStatement("SELECT name FROM " + Constants.SQLTable + "_Banks ORDER BY name ASC LIMIT ?, ?");
            ps.setInt(1, start);
            ps.setInt(2, perPage);
            rs = ps.executeQuery();

            while(rs.next()) {
                banks.add(new Bank(rs.getString("name")));
            }
        } catch (Exception e) {
            System.out.println("[iConomy] Error while listing banks: " + e.getMessage()); return;
        } finally {
            if(ps != null)
                try { ps.close(); } catch (SQLException ex) { }

            if(conn != null)
                try { conn.close(); } catch (SQLException ex) { }
        }

        if(banks.isEmpty()) {
            Messaging.send(player, Template.parse("list.banks.opening", new String[]{ "+amount,+a", "+total,+t" }, new Object[]{ 0, 0 }));
            Messaging.send(player, Template.color("list.banks.empty"));
            return;
        }

        Messaging.send(player, Template.parse("list.banks.opening", new String[]{ "+amount,+a", "+total,+t" }, new Object[]{ page, totalPages }));

        for(Bank bank : banks) {
            if(bank == null) continue;

            String major = bank.getMajor().get(1);
            String minor = bank.getMinor().get(1);
            
            Messaging.send(player, Template.parse(
                entry,
                new String[]{ "+name,+bank,+b,+n", "+fee,+f", "+initial,+holdings,+i,+h", "+major", "+minor" },
                new Object[]{ bank.getName(), iConomy.format(bank.getFee()), iConomy.format(bank.getInitialHoldings()), major, minor }
            ));
        }
    }

    /**
     * Shows the balance to the requesting player.
     *
     * @param name The name of the player we are viewing
     * @param viewing The player who is viewing the account
     * @param mine Is it the player who is trying to view?
     */
    public void showBalance(String name, CommandSender viewing, boolean mine) {
        if (mine) {
            Messaging.send(viewing, Template.color("tag.money") + Template.parse("personal.balance", new String[]{"+balance,+b"}, new String[]{ iConomy.format(name) }));
        } else {
            Messaging.send(viewing, Template.color("tag.money") + Template.parse("player.balance", new String[]{"+balance,+b", "+name,+n"}, new String[]{ iConomy.format(name), name }));
        }
    }

    public void showBankAccounts(CommandSender player, String name) {
        List<BankAccount> Accounts = null;
        boolean self = Misc.isSelf(player, name);

        if(!iConomy.hasAccount(name)) {
            Messaging.send(Template.parse("error.account", new String[]{"+name,+n"}, new String[]{ name }));
            return;
        }

        Accounts = iConomy.getAccount(name).getBankAccounts();

        if(Accounts == null || Accounts.isEmpty()) {
            Messaging.send(Template.color("error.bank.account.none"));
            return;
        }

        for(BankAccount account : Accounts) {
            if(account == null) { continue; }

            Messaging.send(
                player,
                Template.color("tag.bank") +
                Template.parse((self) ? "personal.bank.balance" : "player.bank.balance", new String[]{ "+balance,+holdings,+h", "+bank,+b", "+name,+n" }, new String[]{ account.getHoldings().toString(), account.getBankName(), name })
            );
        }
    }

    public void showBankAccount(CommandSender player, String bank, String name) {
        Bank Bank = null;
        BankAccount account = null;
        Holdings holdings = null;
        boolean self = Misc.isSelf(player, name);

        if(!iConomy.Banks.exists(bank)) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ bank }));
            return;
        }

        Bank = iConomy.getBank(bank);

        if(Bank == null) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ bank }));
            return;
        }

        if(!Bank.hasAccount(name)) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+bank,+b", "+name,+n" }, new String[]{ bank, name }));
            return;
        }

        account = Bank.getAccount(name);

        if(account == null) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+bank,+b", "+name,+n" }, new String[]{ bank, name }));
            return;
        }

        holdings = account.getHoldings();

        Messaging.send(
            player,
            Template.color("tag.bank") +
            Template.parse((self) ? "personal.bank.balance" : "player.bank.balance", new String[]{ "+balance,+holdings,+h", "+bank,+b", "+name,+n" }, new String[]{ holdings.toString(), account.getBankName(), name })
        );
    }
    
    public void showBankWithdrawal(CommandSender player, String bank, String name, double amount) {
        if(!iConomy.hasAccount(name)) {
            Messaging.send(Template.color("error.bank.account.none"));
            return;
        }
        
        Bank Bank = null;
        Account Account = iConomy.getAccount(name);
        BankAccount account = null;
        Holdings holdings = null;
        Holdings held = null;
        boolean self = Misc.isSelf(player, name);

        if(!iConomy.Banks.exists(bank)) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ bank }));
            return;
        }

        Bank = iConomy.getBank(bank);

        if(Bank == null) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ bank }));
            return;
        }

        if(!Bank.hasAccount(name)) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+bank,+b", "+name,+n" }, new String[]{ bank, name }));
            return;
        }

        account = Bank.getAccount(name);

        if(account == null) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+bank,+b", "+name,+n" }, new String[]{ bank, name }));
            return;
        }

        held = Account.getHoldings();
        holdings = account.getHoldings();
        Double onHand = held.balance();
        Double balance = holdings.balance();

        if (balance < 0.0 || !holdings.hasEnough(amount)) {
            if (player != null) {
                Messaging.send(player, Template.color("error.bank.account.funds"));
            }
        } else {
            holdings.subtract(amount);
            held.add(amount);

            onHand = held.balance();
            balance = holdings.balance();

            iConomy.getTransactions().insert("[Bank] " + bank, name, balance, onHand, 0.0, 0.0, amount);
            iConomy.getTransactions().insert(name, "[Bank] " + bank, onHand, balance, 0.0, amount, 0.0);

            if (player != null) {
                Messaging.send(
                    player,
                    Template.color("tag.bank") + 
                    Template.parse( "personal.bank.withdraw",
                    new String[]{ "+bank,+b,+name,+n", "+amount,+a" },
                    new String[]{ bank, iConomy.format(amount) })
                );

                showBalance(name, player, true);
                showBankAccount(player, bank, name);
            }
        }
    }

    public void showBankDeposit(CommandSender player, String bank, String name, double amount) {
        if(!iConomy.hasAccount(name)) {
            Messaging.send(Template.color("error.bank.account.none"));
            return;
        }

        Bank Bank = null;
        Account Account = iConomy.getAccount(name);
        BankAccount account = null;
        Holdings holdings = null;
        Holdings held = null;
        boolean self = Misc.isSelf(player, name);

        if(!iConomy.Banks.exists(bank)) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ bank }));
            return;
        }

        Bank = iConomy.getBank(bank);

        if(Bank == null) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ bank }));
            return;
        }

        if(!Bank.hasAccount(name)) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+bank,+b", "+name,+n" }, new String[]{ bank, name }));
            return;
        }

        account = Bank.getAccount(name);

        if(account == null) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+bank,+b", "+name,+n" }, new String[]{ bank, name }));
            return;
        }

        held = Account.getHoldings();
        holdings = account.getHoldings();
        Double onHand = held.balance();
        Double balance = holdings.balance();

        if (onHand < 0.0 || !held.hasEnough(amount)) {
            if (player != null) {
                Messaging.send(player, Template.color("error.funds"));
            }
        } else {
            held.subtract(amount);
            holdings.add(amount);

            onHand = held.balance();
            balance = holdings.balance();

            iConomy.getTransactions().insert(name, "[Bank] " + bank, onHand, balance, 0.0, 0.0, amount);
            iConomy.getTransactions().insert("[Bank] " + bank, name, balance, onHand, 0.0, amount, 0.0);

            if (player != null) {
                Messaging.send(
                    player,
                    Template.color("tag.bank") +
                    Template.parse( "personal.bank.deposit",
                    new String[]{ "+bank,+b,+name,+n", "+amount,+a" },
                    new String[]{ bank, iConomy.format(amount) })
                );

                showBalance(name, player, true);
                showBankAccount(player, bank, name);
            }
        }
    }

    public void showBankTransaction(CommandSender player, String from, String to, double amount) {
        if(from.toLowerCase().equalsIgnoreCase(to.toLowerCase())) {
            Messaging.send(player, Template.color("payment.self"));
            return;
        }

        if(!iConomy.hasAccount(from) || !iConomy.hasAccount(to)) {
            Messaging.send(player, Template.color("error.bank.account.none"));
            return;
        }

        Bank Bank = null;
        Bank from_bank = iConomy.getAccount(from).getMainBank();
        Bank to_bank = iConomy.getAccount(to).getMainBank();
        BankAccount from_account = iConomy.getAccount(from).getMainBankAccount();
        BankAccount to_account = iConomy.getAccount(to).getMainBankAccount();
        String from_bank_name = from_account.getBankName();
        String to_bank_name = to_account.getBankName();
        Holdings from_holdings = null;
        Holdings to_holdings = null;

        if(from_bank == null || from_account == null) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+name,+n" }, new String[]{ from }));
            return;
        }

        if(to_bank == null || from_account == null) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+name,+n" }, new String[]{ to }));
            return;
        }

        from_holdings = from_account.getHoldings();
        to_holdings = to_account.getHoldings();

        if (from_holdings.balance() < 0.0 || !from_holdings.hasEnough(amount)) {
            if (player != null) {
                Messaging.send(player, Template.color("error.bank.account.funds"));
            }
        } else {
            from_holdings.subtract(amount);
            to_holdings.add(amount);

            Double from_current = from_holdings.balance();
            Double to_current = to_holdings.balance();

            iConomy.getTransactions().insert(from, to, to_current, from_current, 0.0, 0.0, amount);
            iConomy.getTransactions().insert(to, from, from_current, to_current, 0.0, amount, 0.0);

            if (player != null) {
                Messaging.send(
                    player,
                    Template.color("tag.bank") +
                    Template.parse( (from.equalsIgnoreCase(to)) ? "personal.bank.transfer" : "personal.bank.between",
                    new String[]{ "+bank,+b", "+bankAlt,+ba,+bA", "+name,+n", "+amount,+a" },
                    new String[]{ from_bank_name, to_bank_name, to, iConomy.format(amount) })
                );

                showBankAccount(player, from_bank_name, from);
            }

            if(!from.equalsIgnoreCase(to)) {
                Player playerTo = iConomy.getBukkitServer().getPlayer(to);

                if(playerTo != null) {
                    Messaging.send(
                        player,
                        Template.color("tag.bank") +
                        Template.parse( "personal.bank.recieved",
                        new String[]{ "+bank,+b", "+amount,+a" },
                        new String[]{ to_bank_name, iConomy.format(amount) })
                    );
                    showBankAccount(playerTo, to_bank_name, to);
                }
            }
        }
    }

    public void showBankTransfer(CommandSender player, String from, String from_bank, String to, String to_bank, double amount) {
        Bank Bank = null;
        Bank fBank = iConomy.getBank(from_bank);
        Bank tBank = iConomy.getBank(to_bank);
        Holdings from_holdings = null;
        Holdings to_holdings = null;

        if(fBank == null) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ from_bank }));
            return;
        }

        if(tBank == null) {
            Messaging.send(player, Template.parse("error.bank.doesnt", new String[]{ "+bank,+name,+n,+b" }, new String[]{ to_bank }));
            return;
        }

        BankAccount fAccount = iConomy.getAccount(from).getMainBankAccount();
        BankAccount tAccount = iConomy.getAccount(to).getMainBankAccount();

        if(fAccount == null) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+name,+n" }, new String[]{ from }));
            return;
        }

        if(tAccount == null) {
            Messaging.send(player, Template.parse("error.bank.account.doesnt", new String[]{ "+name,+n" }, new String[]{ to }));
            return;
        }

        String from_bank_name = fAccount.getBankName();
        String to_bank_name = tAccount.getBankName();
        from_holdings = fAccount.getHoldings();
        to_holdings = tAccount.getHoldings();

        if (from_holdings.balance() < 0.0 || !from_holdings.hasEnough(amount)) {
            if (player != null) {
                Messaging.send(player, Template.color("error.bank.account.funds"));
            }
        } else {
            from_holdings.subtract(amount);
            to_holdings.add(amount);

            Double from_current = from_holdings.balance();
            Double to_current = to_holdings.balance();

            iConomy.getTransactions().insert(from, to, to_current, from_current, 0.0, 0.0, amount);