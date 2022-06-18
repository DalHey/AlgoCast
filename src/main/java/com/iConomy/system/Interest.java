
package com.iConomy.system;

import org.bukkit.entity.Player;

import com.iConomy.iConomy;
import com.iConomy.util.Constants;
import com.iConomy.util.Messaging;
import com.iConomy.util.Template;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

public class Interest extends TimerTask {
    Template Template = null;

    public Interest(String directory) {
        Template = new Template(directory, "Messages.yml");
    }

    @Override
    public void run() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        DecimalFormat DecimalFormat = new DecimalFormat("#.##");
        List<String> players = new ArrayList<String>();
        HashMap<String, Integer> bankPlayers = new HashMap<String, Integer>();

        if(Constants.InterestOnline) {
            Player[] player = iConomy.getBukkitServer().getOnlinePlayers();
            
            if(Constants.InterestType.equalsIgnoreCase("players") || !Constants.Banking) {
                for(Player p : player) {
                    players.add(p.getName());
                }
            } else {
                for(Player p : player) {
                    Account account = iConomy.getAccount(p.getName());
                    
                    if(account != null) {