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