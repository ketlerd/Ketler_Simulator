/*
 * Ketler Simulator
 * Motorola 6800 Simulator
 *
 * updatePallet.java
 *
 * method to update the pallet (update all modules as needed)
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
import java.util.ArrayList;

public class updatePallet implements Runnable {
    CPU processor;
    palletP p;

    public void run() {
         try {
            while(true) { //infinite loop ~ an event loop
                Thread.sleep(350); //sleep to lessen resource use
                if(processor.updated) //if an update is flagged
                    this.update();
                    processor.updated = false; //reset flag
            }
        }
        catch(Exception e) { System.out.println(e.toString() + " updatePallet "); }
        
    }//run

    public void update() {
        ArrayList<button> l = p.list;

        for(int i = 0; i < l.size(); i++) {
            l.get(i).update();
        }
        ArrayList<teletype> t = p.teletypes;
        for(int j = 0; j < t.size(); j++) {
            t.get(j).update();
        }
    }


}
