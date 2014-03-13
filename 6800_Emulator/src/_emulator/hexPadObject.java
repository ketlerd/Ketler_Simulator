/*
 * Ketler Simulator
 * Motorola 6800 Simulator
 *
 * hexPadObject.java
 *
 * object representing the et-3400 hexpad
 *
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

import java.awt.*;
import javax.swing.*;


/**
 *
 * @author dak
 */
public class hexPadObject {
    palletP p;
    
    
    public void setPallet(palletP pal) {
        p = pal;
    }//setPallet
    
    public void addButtons() {
        
        button b = new button("display");
        b.addListener();
        p.add(b);
        p.updateUI();
        p.validate();
        
    }//addButtons
    
}
