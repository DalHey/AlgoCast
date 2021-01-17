package com.iConomy.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AccountUpdateEvent extends Event {

	private final String account;
	private double balance;
	private double previous;
	privat