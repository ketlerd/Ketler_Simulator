/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testHarness;

import java.io.*;
import java.util.*;

/**
 *
 * @author dak
 */
public class state {
    public int ACCA;
    public int ACCB;
    public int SP;
    public int FLAGS;
    public int INX;
    public int memoryLocation;
    public int data1;
    public int data2;
    public int PC;
    public int instruction;
    public int operand;

    String acca_in;
    String accb_in;
    String sp_in;
    String flags_in;
    String inx_in;
    String location_in;
    String data_in;
    String data2_in;
    String pc_in;
    String instruction_in;
    String operand_in;

    public void state() { }

    public void write(PrintWriter out) {
        acca_in = ("0x" +ACCA);
        accb_in = ("0x" +ACCB);
        sp_in = ("0x" +SP);
        flags_in = ("0x" +FLAGS);
        inx_in = ("0x" +INX);
        location_in = ("0x" +memoryLocation);
        data_in = ("0x" +data1);
        data2_in = ("0x" +data2);
        pc_in = ("0x" +PC);
        instruction_in = ("0x" +instruction);

        out.print(acca_in);
        testInterface.pad(acca_in, out);
        out.print(accb_in);
        testInterface.pad(accb_in, out);
        out.print(sp_in);
        testInterface.pad(sp_in, out);
        out.print(flags_in);
        testInterface.pad(flags_in, out);
        out.print(inx_in);
        testInterface.pad(inx_in, out);
        out.print(location_in);
        testInterface.pad(location_in, out);
        out.print(data_in);
        testInterface.pad(data_in, out);
        out.print(data2_in);
        testInterface.pad(data2_in, out);
        out.print(pc_in);
        testInterface.pad(pc_in, out);
        out.print(instruction_in);
        testInterface.pad(instruction_in, out);
    }
}
