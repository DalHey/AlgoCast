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
        return (sender instanceof Player) ? (((Player)sender).getName().equalsIgnoreCase(name)) ? true : false : false;
    }

    public static int plural(Double amount) {
        if(amount != 1 || amount != -1) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public static int plural(Integer amount) {
        if(amount != 1 || amount != -1) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String BankCurrency(int which, String denom) {
        String[] denoms = denom.split(",");

        return denoms[which];
    }

    public static String formatted(String amount, List<String> maj, List<String> min) {
        String formatted = "";
        String famount = amount.replace(",", "");

        if(Constants.FormatMinor) {
            String[] pieces = null;
            String[] fpieces = null;

            if(amount.contains(".")) {
                pieces = amount.split("\\.");
                fpieces = new String[] { pieces[0].replace(",", ""), pieces[1] };
            } else {
                pieces = new String[] { amount, "0" };
                fpieces = new String[] { amount.replace(",", ""), "0" };
            }

            if(Constants.FormatSeperated) {
                String