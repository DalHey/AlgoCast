package com.iConomy.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URLConnection;
import java.net.URL;

public class Downloader {

    protected static int count, total, itemCount, itemTotal;
    protected static long lastModified;
    protected static String error;
    protected static boolean cancelled;

    public Downloader() { }

    public synchronized void cancel() {
        cancelled = true;
    }

    public static void install(String location, String filename) {
        try {
            cancelled = false;
            count = total = itemCount = itemTotal = 0;
            System.out.println("[iConomy] Downloading Dependencies");
            if (cancelled) {
                return;
            }
            System.out.println("   + " + filename + " downloading...");
            download(location, filename);
            System.out.println("   - " + filename + " finished.");
            System.out.println("[iConomy] Downloading " + filename + "...");
        } catch (IOException ex) {
            System.out.println("[iConomy] Error Downloading File: " + ex);
        }
    }

    protected static synchronized void download(String location, String filename)throws IOException {
        URLConnection connection = new URL(location).openConnection();
        connection.setUseCaches(false);
        lastModified = connection.getLastModified();
        int filesize = connection.getContentLength();
        String destination = "lib" + File.separator + filename;
        File parentDirectory = new File(destination).ge