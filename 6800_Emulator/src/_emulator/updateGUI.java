/*
 * Ketler Simulator
 * Motorola 6800 Simulator
 *
 * updateGUI.java
 *
 * function to update the CPU GUI
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
import javax.swing.*;

public class updateGUI implements Runnable {
    private CPU processor;
    private _EmulatorView view;

    public void run() {
         try {
            while(true) { //infinite loop ~ an event loop
                Thread.sleep(350); //sleep to lessen resource use
                if(processor.updated) { //if an update is flagged
                    this.update();
                    processor.updated = false; //reset flag
                }
            }//while loop
        }//try
        catch(Exception e) {  }
    }//run

    public static void main(String args[]) {    }

    public updateGUI(CPU p, _EmulatorView v) {
        this.processor = p;
        this.view = v;
    }

    /**
     * update
     *
     * method to update the interface to display any changes to
     * memory and registers
     */
    private void update() {
        //run the update process in a thread
        Runnable r = new Runnable() {
        public void run() {
            try {
                checkFlags();
                view.reg_hex.clear(); //clear the jlist
                view.reg_hex.addElement(Integer.toHexString((int)processor.PC & 0xFFFF));
                view.reg_hex.addElement(Integer.toHexString((int)processor.SP));
                view.reg_hex.addElement(Integer.toHexString((int)processor.IX & 0xFFFF));
                view.reg_hex.addElement(Integer.toHexString((int)processor.ACCA & 0x00FF));
                view.reg_hex.addElement(Integer.toHexString((int)processor.ACCB & 0x00FF));
                //view.reg_hex.addElement(Integer.toHexString((int)processor.CC & 0x00FF));

                view.reg_binary.clear(); //clear the jlist
                view.reg_binary.addElement(Integer.toBinaryString((int)processor.PC & 0x0FFFF));
                view.reg_binary.addElement(Integer.toBinaryString((int)processor.SP));
                view.reg_binary.addElement(Integer.toBinaryString((int)processor.IX & 0x0FFFF));
                view.reg_binary.addElement(Integer.toBinaryString((int)processor.ACCA & 0x00FF));
                view.reg_binary.addElement(Integer.toBinaryString((int)processor.ACCB & 0x00FF));
                //view.reg_binary.addElement(Integer.toBinaryString((int)processor.CC & 0x00FF));

                
                view.ram_hex.clear(); //clear the jlist
                for(int i = 0; i < processor.mem.length; i++) {
                    String val = "";

                    val = Integer.toHexString(i).toUpperCase();

                    if(i < 16)
                        val = "000" + val;
                    else if(i < 256)
                        val = "00" + val;
                    else if(i < 4096)
                        val = "0" + val;

                    val = val + "         ";
                    //val = val + "0x" + Integer.toHexString((int)processor.mem[i] & 0x00FF).toUpperCase();


                    if(((int)processor.mem[i] & 0x00FF) < 16)
                        val = val  + "0" + Integer.toHexString((int)processor.mem[i] & 0x00FF).toUpperCase();
                    else
                        val = val  + Integer.toHexString((int)processor.mem[i] & 0x00FF).toUpperCase();

                    view.ram_hex.addElement(val);
                }

                view.ram_bin.clear(); //clear the jlist
                for(int i = 0; i < processor.mem.length; i++) {
                    view.ram_bin.addElement(Integer.toBinaryString((int)processor.mem[i]).toUpperCase());
                }
                
            }
            catch (Exception ex) { }

            if(processor.stepThroughExecution)
                view.jButton4.setText("Toggle stepthrough exec off");
            else
                view.jButton4.setText("Toggle stepthrough exec on");

        }//run
    };
    SwingUtilities.invokeLater(r);

    }//update

    private void checkFlags() {
        //set flags if checked
        if(view.jCheckBox1.isSelected())
            processor.CC = (byte)(processor.CC | 0x1);
        if(view.jCheckBox2.isSelected())
            processor.CC = (byte)(processor.CC | 0x2);
        if(view.jCheckBox3.isSelected())
            processor.CC = (byte)(processor.CC | 0x4);
        if(view.jCheckBox4.isSelected())
            processor.CC = (byte)(processor.CC | 8);
        if(view.jCheckBox5.isSelected())
            processor.CC = (byte)(processor.CC | 16);
        if(view.jCheckBox6.isSelected())
            processor.CC = (byte)(processor.CC | 32);

        //set checkboxes if flag set
        if((processor.CC & 0x1) != 0) {
            view.jCheckBox1.setSelected(true);}
        if((processor.CC & 0x2) != 0) {
            view.jCheckBox2.setSelected(true);}
        if((processor.CC & 0x4) != 0) {
            view.jCheckBox3.setSelected(true);}
        if((processor.CC & 0x8) != 0) {
            view.jCheckBox4.setSelected(true);}
        if((processor.CC & 0x10) != 0) {
            view.jCheckBox5.setSelected(true);}
        if((processor.CC & 0x20) != 0){
            view.jCheckBox6.setSelected(true);}     
    }

    public void uncheck() {
                //unset flag if checkbox not set
        if(!view.jCheckBox1.isSelected())
            processor.CC = (byte)(processor.CC & 0xFE);
        if(!view.jCheckBox2.isSelected())
            processor.CC = (byte)(processor.CC & 0xFD);
        if(!view.jCheckBox3.isSelected())
            processor.CC = (byte)(processor.CC & 0xFB);
        if(!view.jCheckBox4.isSelected())
            processor.CC = (byte)(processor.CC & 0xF7);
        if(!view.jCheckBox5.isSelected())
            processor.CC = (byte)(processor.CC & 0xEF);
        if(!view.jCheckBox6.isSelected())
            processor.CC = (byte)(processor.CC & 0xDF);
    }
}//updateGUI
