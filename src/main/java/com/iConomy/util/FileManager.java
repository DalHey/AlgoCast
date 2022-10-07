
package com.iConomy.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Easy File Management Class
 *
 * @copyright Copyright AniGaiku LLC (C) 2010-2011
 * @author          Nijikokun <nijikokun@gmail.com>
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
public final class FileManager {

    private String directory = "";
    private String file = "";
    private String source = "";
    private LinkedList<String> lines = new LinkedList<String>();

    public FileManager(String directory, String file, boolean create) {
        this.directory = directory;
        this.file = file;

        if (create) {
            this.existsCreate();
        }
    }

    public String getSource() {
        return source;
    }

    public LinkedList<String> getLines() {
        return lines;
    }

    public String getDirectory() {
        return directory;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setFile(String file, boolean create) {
        this.file = file;

        if (create) {
            this.create();
        }
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void setDirectory(String directory, boolean create) {
        this.directory = directory;

        if (create) {
            this.createDirectory();
        }
    }

    private void log(Level level, Object message) {
        Logger.getLogger("FileManager").log(level, null, message);
    }

    public boolean exists() {