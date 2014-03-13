/*
 * Ketler Simulator
 * Motorola 6800 Emulator
 * Copyright 2011 David Ketler 3394947, Brock University
 *
 * This file is part of the Ketler Simulator.

The Ketler Simulator is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Ketler Simulator is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Ketler Simulator.  If not, see <http://www.gnu.org/licenses/>.
 */
package _emulator;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class _EmulatorApp extends SingleFrameApplication {
    static _EmulatorView window;
    static IDE ide;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        //yeah... no idea what is causing that exception
        //so we're just going to catch it and deal with it
        //look ma, I'm a programmer!
        try {
            ide = new IDE(this);
            //show(window = new _EmulatorView(this));
            ide.setVisible(true);
        }
        catch(Exception e) {System.out.println(e.toString());}
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of _EmulatorApp
     */
    public static _EmulatorApp getApplication() {
        return Application.getInstance(_EmulatorApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(_EmulatorApp.class, args);

    }
}
