package com.iConomy.util;

import org.bukkit.entity.Player;

import com.iConomy.iConomy;
import java.util.List;
import org.bukkit.command.CommandSender;

public class Misc {

    /**
     * Checks text against two variables, if it equals at least one returns true.
     *
     * @param text The text that we were provided with.
     * @param against The first variable that needs to be checked against
     * @param or The second variable that it could possibly be.
     *
     * @return <code>Boolean</code> - True or false based on text.
     */
    public static boolean is(String text, String[] is) {
        for (String s : is) {
            if (text.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSelf(CommandSender sender, String name) {
        return (sender instanceof Player) ? (((Player)sender).getName().equalsIgnore