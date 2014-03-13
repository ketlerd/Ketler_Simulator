/* Ketler Simulator
 * Morotola 6800 Simulator
 *
 * button.java
 * class representing the button objects for the pallet
 * (display, switch, LED, etc)
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
import java.util.HashMap;
import java.util.ArrayList;

public class button extends JButton {
    int startX, startY;
    palletP j;
    final JPopupMenu menu;
    public int t = 245;
    String type;
    boolean status;
    int mem = 0;
    int irq = 0; //0 = none, 1 = MI, 2 = NMI
    int kind = 0;
    int ID = 1; //default ID 1
    CPU proc;
    char key = 'e';
    HashMap icons;
    boolean encoding = false; //true for hex, false for ET-3400 style
    updateSegmentDisplay upDisp = new updateSegmentDisplay();

    int listLoc;
    button self = this;

    public button(String s) {
        if(s.compareTo("display") == 0)
            newB();
        else if(s.compareTo("displayEt") == 0)
            newBEt();
        else if(s.compareTo("switch") == 0)
            newS();
        else if(s.compareTo("LED") == 0)
            newLED();
        else if(s.compareTo("kb") == 0)
            newKB();
        else if(s.compareTo("key") == 0)
            newKey();

        setFocusable(false);

        type = s;
        menu = new JPopupMenu();

        // Create and add a menu item
        JMenuItem item = new JMenuItem("Properties");
        if(type.compareTo("displayEt") == 0)
            item.setEnabled(false);
        if(type.compareTo("switch") == 0) {
            JMenuItem swi = new JMenuItem("Toggle");

            swi.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(status) {
                        status = false;
                        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/switch_open.png")));
                    }
                    else {
                        status = true;
                        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/switch_closed.png")));
                    }
                    toggle();
                }//actionPerformed
            });
            menu.add(swi);
        }//switch if


        item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(type.compareTo("display") == 0) {
                    displayOptions options = new displayOptions();
                    options.setCaller(self);
                    options.setList(j.list);
                    options.setVisible(true);
                }
                else if(type.compareTo("switch") == 0) {
                    switchOptions options = new switchOptions();
                    options.setCaller(self);
                    options.setList(j.list);
                    options.setVisible(true);
                }
                else if(type.compareTo("LED") == 0) {
                    ledOptions options = new ledOptions();
                    options.setCaller(self);
                    options.setList(j.list);
                    options.setVisible(true);
                }
                else if(type.compareTo("kb") == 0) {
                    keyOptions options = new keyOptions();
                    options.setCaller(self);
                    options.setList(j.list);
                    options.setVisible(true);
                }
                else if(type.compareTo("key") == 0) {
                    hexOptions options = new hexOptions();
                    options.setCaller(self);
                    options.setList(j.list);
                    options.setVisible(true);
                }

            }
        });
        menu.add(item);

        JMenuItem remove = new JMenuItem("Remove");
            remove.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if(self.type.compareTo("kb") == 0)
                        self.j.p.key = false;

                    if(self.type.compareTo("displayEt") == 0) {
                        ArrayList<button> l = j.list;
                        button b;

                        for(int k = 0; k < 3; k++) {
                        for (int i = 0; i < l.size(); i++) {
                            b = l.get(i);
                            if (b.type.compareTo("displayEt") == 0) {
                                j.list.remove(i);
                                b.setVisible(false);
                            }
                            j.removal();
                        }
                        }
                        
                    }//displayEt
                    else if(self.type.compareTo("key") == 0) {
                        ArrayList<button> l = j.list;
                        button b;

                        for(int k = 0; k < 5; k++) {
                        for (int i = 0; i < l.size(); i++) {
                            b = l.get(i);
                            if (b.type.compareTo("key") == 0) {
                                j.list.remove(i);
                                b.setVisible(false);
                            }
                        } }
                        j.removal();
                    }//key

                    else {
                        j.list.remove(listLoc);
                        j.removal();
                        self.setVisible(false);
                    }
                }
            });
            menu.add(remove);
    }//button

    public void addListener() {
        addMouseListener(new MouseListener() {

            public void mousePressed(MouseEvent e) {
                startX = e.getXOnScreen();
                startY = e.getXOnScreen();
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || e.getButton() == e.BUTTON3 || e.getModifiersEx() == e.META_DOWN_MASK) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
                j.list.set(listLoc, self);
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {

                if(type.compareTo("switch") == 0) {
                    if(status) {
                        status = false;
                        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/switch_open.png")));
                    }
                    else {
                        status = true;
                        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/switch_closed.png")));
                    }
                    toggle();
                }//switch

                if (type.compareTo("key") == 0 && encoding) {
                    switch (key) {
                        case '0':
                            proc.mem[mem] = 0x0;
                            break;
                        case '1':
                            proc.mem[mem] = 0x1;
                            System.out.println("0x1");
                            break;
                        case '2':
                            proc.mem[mem] = 0x2;
                            break;
                        case '3':
                            proc.mem[mem] = 0x3;
                            break;
                        case '4':
                            proc.mem[mem] = 0x4;
                            break;
                        case '5':
                            proc.mem[mem] = 0x5;
                            break;
                        case '6':
                            proc.mem[mem] = 0x6;
                            break;
                        case '7':
                            proc.mem[mem] = 0x7;
                            break;
                        case '8':
                            proc.mem[mem] = 0x8;
                            break;
                        case '9':
                            proc.mem[mem] = 0x9;
                            break;
                        case 'a':
                            proc.mem[mem] = 0xA;
                            break;
                        case 'b':
                            proc.mem[mem] = 0xB;
                            break;
                        case 'c':
                            proc.mem[mem] = 0xC;
                            break;
                        case 'd':
                            proc.mem[mem] = 0xD;
                            break;
                        case 'e':
                            proc.mem[mem] = 0xE;
                            break;
                        case 'f':
                            proc.mem[mem] = 0xF;
                            break;
                    }//switch
                }//key compare if
                else if(type.compareTo("key") == 0 && !encoding) {
                    switch (key) {
                        case '0':
                            proc.mem[mem] = (byte)0xDF;
                            break;
                        case '1':
                            proc.mem[mem] = (byte)0xEF;
                            System.out.println("0x1");
                            break;
                        case '2':
                            proc.mem[mem] = (byte)0xEF;
                            break;
                        case '3':
                            proc.mem[mem] = (byte)0xEF;
                            break;
                        case '4':
                            proc.mem[mem] = (byte)0xF7;
                            break;
                        case '5':
                            proc.mem[mem] = (byte)0xF7;
                            break;
                        case '6':
                            proc.mem[mem] = (byte)0xF7;
                            break;
                        case '7':
                            proc.mem[mem] = (byte)0xFB;
                            break;
                        case '8':
                            proc.mem[mem] = (byte)0xFB;
                            break;
                        case '9':
                            proc.mem[mem] = (byte)0xFB;
                            break;
                        case 'a':
                            proc.mem[mem] = (byte)0xFD;
                            break;
                        case 'b':
                            proc.mem[mem] = (byte)0xFD;
                            break;
                        case 'c':
                            proc.mem[mem] = (byte)0xFD;
                            break;
                        case 'd':
                            proc.mem[mem] = (byte)0xFE;
                            break;
                        case 'e':
                            proc.mem[mem] = (byte)0xFE;
                            break;
                        case 'f':
                            proc.mem[mem] = (byte)0xFE;
                            break;
                    }//switch
                }
            }//mouseClicked
        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                int keyPadLocation = 0;
                int displayLocation = 0;
                int x,y;
                int count = 0;
                x = y = 0;
                try {
                    setLocation(j.getMousePosition().x,j.getMousePosition().y);
                }
                catch(Exception ex) {}

                if(type.compareTo("key") == 0) {
                    ArrayList<button> l = j.list;
                    button b;

                    for(int i = 0; i < l.size() - 1; i++) {
                        b = l.get(i);
                        if(b.type.compareTo("key") == 0) {
                            keyPadLocation = i;
                            break;
                        }
                    }

                    x = j.getMousePosition().x;
                    y = j.getMousePosition().y - 150;

                    for (int i = keyPadLocation; i < l.size(); i++) {
                        b = l.get(i);
                        
                        if(b.type.compareTo("key") != 0)
                            break;

                        if (count%3 == 0) {
                            y = y + 60;
                            x = j.getMousePosition().x;
                        }
                        if (count == 15) {
                            x = x + 60;
                        }

                        
                        b.setLocation(x, y);
                        b.proc = proc;
                        x = x + 60;
                        count++;
                    }
                }//move whole keypad if
                else if(type.compareTo("displayEt") == 0) {
                    ArrayList<button> l = j.list;
                    button b;

                    for(int i = 0; i < l.size() - 1; i++) {
                        b = l.get(i);
                        if(b.type.compareTo("displayEt") == 0) {
                            displayLocation = i;
                            break;
                        }
                    }

                    x = j.getMousePosition().x;
                    y = j.getMousePosition().y;

                    for (int i = displayLocation; i < l.size(); i++) {
                        b = l.get(i);
                        
                        if(b.type.compareTo("displayEt") != 0)
                            break;
                        
                        b.setLocation(x, y);
                        b.proc = proc;
                        x = x + 75;
                        count++;
                    }
                }//display else
            }
        });
    }

    public void newB() {
        setLocation(10, 10);
        setSize(70, 102);
        addListener();
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentOff.png")));
        setVisible(true);
        kind = 0;
    }

    public void newBEt() {
        setLocation(10, 10);
        setSize(70, 102);
        addListener();
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentOff.png")));
        setVisible(true);
        kind = 0;
    }

    public void newS() {
        setLocation(10, 10);
        setSize(133,69);
        addListener();
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/switch_open.png")));
        setVisible(true);
        status = false;
        kind = 1;
    }

    public void newLED() {
        setLocation(10, 10);
        setSize(30,61);
        addListener();
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/ledUnlit.png")));
        setVisible(true);
        status = false;
        kind = 2;
    }

    public void newKB() {
        setLocation(10, 10);
        setSize(101,53);
        addListener();
        setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/keyboard.png")));
        setVisible(true);
        status = false;
        kind = 3;
    }
    
    public void newKey() {
        setLocation(10, 10);
        setSize(59,58);
        addListener();
        String filename = "/_emulator/resources/KeyPad/" +key +"Key.png";
        
        switch(key) {
            case '0':
                filename = "/_emulator/resources/KeyPad/" +"zeroKey.png";
                break;
            case '1':
                filename = "/_emulator/resources/KeyPad/" +"oneKey.png";
                break;
            case '2':
                filename = "/_emulator/resources/KeyPad/" +"twoKey.png";
                break;
            case '3':
                filename = "/_emulator/resources/KeyPad/" +"threeKey.png";
                break;
            case '4':
                filename = "/_emulator/resources/KeyPad/" +"fourKey.png";
                break;
            case '5':
                filename = "/_emulator/resources/KeyPad/" +"fiveKey.png";
                break;
            case '6':
                filename = "/_emulator/resources/KeyPad/" +"sixKey.png";
                break;
            case '7':
                filename = "/_emulator/resources/KeyPad/" +"sevenKey.png";
                break;
            case '8':
                filename = "/_emulator/resources/KeyPad/" +"eightKey.png";                
                break;
            case '9':
                filename = "/_emulator/resources/KeyPad/" +"nineKey.png";
                break;
        }//switch
        
        setIcon(new javax.swing.ImageIcon(getClass().getResource(filename)));
        setVisible(true);
        status = false;
        kind = 4;
        
    }
    
    public void setKey(char c) {
        key = c;
    }

    public void setPal(palletP p) {
        j = p;
    }

    public void setMem(int m) {
        mem = m;
    }
    
    public void setCPU(CPU p) {
        proc = p;
    }

    public void toggle() {
        if(this.status)
            proc.mem[mem] = (byte)ID;
        if(!this.status)
            proc.mem[mem] = (byte)(~ID);
        
        proc.updated = true;
    }//toggle

    public void update() {
        if(type.compareTo("display") == 0)
            upDisp.update(this);
        else if(type.compareTo("LED") == 0)
            if(proc.mem[mem] != 0)
                setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/ledLit.png")));
            else
                setIcon(new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/pallet/ledUnlit.png")));

        proc.updated = true;
    }


}//class
