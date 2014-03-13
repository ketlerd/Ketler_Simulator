/*
 * Ketler Simulator
 * Motorola 6800 Simulator
 *
 * teletype.java
 *
 * object reprsenting the teletype
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
import java.awt.event.*;


public class teletype extends JTextArea {
    palletP j;
    public int t = 245;
    String type;
    boolean status;
    int mem = 0;
    int mem2 = 0;
    int irq = 0; //0 = none, 1 = MI, 2 = NMI
    int kind = 0;
    final JPopupMenu menu;
    int listLoc;
    teletype self = this;
    CPU proc;
    int component;


    public teletype() {
        type = "teletype";
        menu = new JPopupMenu();
        self.setEditable(false);
        self.setText("a");

        // Create and add a menu item
        JMenuItem item = new JMenuItem("Properties");

        item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teletypeOptions options = new teletypeOptions();
                options.setCaller(self);
                options.setVisible(true);
            }
        });
        menu.add(item);

        JMenuItem remove = new JMenuItem("Remove");
            remove.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    setVisible(false);
                }
            });
            menu.add(remove);

        setLocation(10, 10);
        setSize(100, 20);
        addListener();
        setVisible(true);
    }

    public void addListener() {
        addMouseListener(new MouseListener() {

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || e.getButton() == e.BUTTON3 || e.getModifiersEx() == e.META_DOWN_MASK) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
                
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                try {
                    setLocation(j.getMousePosition().x,j.getMousePosition().y);
                }
                catch(Exception ex) {}
            }
        });
    }

    public void setPal(palletP p) {
        j = p;
    }

    public void setMem(int m, int m2) {
        mem = m;
        mem2 = m2;
    }

    public void update() {

        System.out.println("update!");

        if(proc.mem[mem] == 1) {
            proc.mem[mem] = 2;
            this.append(Character.toString((char)proc.mem[mem2]));
            proc.mem[mem] = 0;
        }

        proc.updated = true;
    }
}
