/*
 * Ketler Simulator
 * Motorola 6800 Simulator
 *
 * palletP.java
 *
 * pallet panel to contain the modules
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
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class palletP extends javax.swing.JPanel {
    public ArrayList<button> list;
    public ArrayList<teletype> teletypes;
    public boolean key;
    public pallet p;
    CPU proc;

    /** Creates new form palletP */
    public palletP() {
        list = new ArrayList<button>();
        teletypes = new ArrayList<teletype>();
        initComponents();
    }

    public void addButtons(String s) {
        button b = new button(s);
        b.setCPU(proc);
        b.proc.updated = true;
        b.setPal(this);
        this.add(b);
        this.validate();
        list.add(b);
        b.listLoc = list.size() - 1;
        //load();

        if(s.compareTo("kb") == 0)
            p.key = true;
    }
    
    public void addButtons(String s, char c) {
        button b = new button(s);
        b.setCPU(proc);
        b.setPal(this);
        b.setKey(c);
        b.newKey();         //clean this up later
        this.add(b);
        this.validate();
        list.add(b);
        b.listLoc = list.size() - 1;
    }
    

    public void addTeletype() {
        teletype t = new teletype();
        t.setPal(this);
        t.setSize(200,100);
        t.proc = proc;
        t.setText(Character.toString('>'));
        teletypes.add(t);
        this.add(t);
        this.validate();

    }

    public void save(File f) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            button b;

            out.print(list.size() +"_"+ teletypes.size());
            out.print("\t");

            for(int i = 0; i < list.size(); i++) {
                b = list.get(i);
                out.print(b.mem);
                out.print("\t");
                out.print(b.irq);
                out.print("\t");
                out.print(b.kind);
                out.print("\t");
                out.print(b.type);
                out.print("\t");
                out.print(b.ID);
                out.print("\t");
                out.print(b.getX());
                out.print("\t");
                out.print(b.getY());
                out.print("\t");
            }
            for(int j = 0; j < teletypes.size(); j++) {
                teletype t = teletypes.get(j);
                out.print(t.mem);
                out.print("\t");
                out.print(t.mem2);
                out.print("\t");
                out.print(t.getX());
                out.print("\t");
                out.print(t.getY());
                out.print("\t");
            }

            out.flush();
            out.close();
        } catch (Exception e) {    }
    }

    public void load(File f) {
        this.removeAll();
        int mem,irq,kind,ID,x,y;
        String type;
        list = new ArrayList<button>();
        teletypes = new ArrayList<teletype>();

        try {
            BufferedReader in
                    = new BufferedReader(new FileReader(f));
            StringTokenizer st = new StringTokenizer(in.readLine());

            String num = st.nextToken();

            int separator = num.indexOf('_');
            int numElements = Integer.parseInt(num.substring(0,separator));
            int numTele = Integer.parseInt(num.substring(separator+1,num.length()));

            System.out.println(numElements +"  " +numTele);

            for(int i = 0; i < numElements; i++) {
                mem = Integer.parseInt(st.nextToken());
                irq = Integer.parseInt(st.nextToken());
                kind = Integer.parseInt(st.nextToken());
                type = st.nextToken();
                ID = Integer.parseInt(st.nextToken());
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());

                System.out.println("parsed");

                addButtons(type);
                button b = list.get(i);
                b.mem = mem;
                b.irq = irq;
                b.kind = kind;
                b.ID = ID;
                b.setLocation(x,y);
                b.proc = proc;
                list.set(i, b);
                this.updateUI();
            }
            System.out.println("done others");
            for(int j = 0; j < numTele; j++) {
                int memT = Integer.parseInt(st.nextToken());
                int mem2T = Integer.parseInt(st.nextToken());
                int xT = Integer.parseInt(st.nextToken());
                int xY = Integer.parseInt(st.nextToken());

                addTeletype();
                teletype t = teletypes.get(j);
                t.mem = memT;
                t.mem2 = mem2T;
                t.setLocation(xT,xY);
                t.proc = proc;
                teletypes.set(j, t);
                this.updateUI();
            }
            in.close();
        }
        catch(Exception e) {System.out.println(e.toString()); }

    }

    public void removal() {
        button b;

        for(int i = 0; i < list.size(); i++) {
            b = list.get(i);
            b.listLoc = i;
            list.set(i, b);
        }
    }

    public void reset() {
        this.removeAll();
        list = new ArrayList<button>();
        this.updateUI();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setName("Form"); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped

    }//GEN-LAST:event_formKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
