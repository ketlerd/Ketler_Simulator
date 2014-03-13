/*
 * COSC 3P92 Project
 * Motorola 6800 Emulator
 *
 * David Ketler 3394947, Frankie Lau 3844222
 */
package testHarness;

import _emulator.*;
import java.io.*;
/**
 *
 * CPU class
 * Class representing the 6800 CPU
 * Performs the fetch/decode cycle and executes instructions from
 * the system's simulated memory
 * 
 * @author David Ketler, 3394947
 */
public class CPU implements Runnable {
    //Constants
    static int MEMORYPOOL = 65536;
    
    public byte mem[]; //memory array
    public int PC;
    public int IX;
    public int SP;
    public byte ACCA;
    public byte ACCB;
    public byte CC;
    public String s;
    public boolean updated;
    public boolean step;
    public boolean stepThroughExecution;
    public boolean execute;
    public boolean NMI;
    public boolean MI;
    public int irq = 0xFF90;
    
    public boolean reset;

    int zero;

    public static void main(String args[]) {
        new Thread(new CPU()).start();
    }

    public void run() {
    
        //initialize the CPU
        init();
        step = false;
        stepThroughExecution = true;
        execute = false;
        flyingSpaghettiMethod();

        
    }

    public CPU() {

    }

    /**
     * init
     *
     * Method to initialize the registers and memory to 0
     * simulating a reboot
     */
    public void init() {
        //initialize registers to 0
        PC = 0x0000;
        IX = 0x0000;
        SP = 0xD0;
        ACCA = (byte)0x00;
        ACCB = (byte)0x00;
        CC = (byte)0x00;
        mem = new byte[MEMORYPOOL];
        updated = true;
        NMI = false;
        MI = false;

        //initialize memory to 0x0
        for(int i = 0; i < mem.length; i++) {
            mem[i] = (byte)0x0;        }

        loadROM();
    }

    public void reset() {
        loadROM();
        CC = (byte)(CC | 0x10);
        PC = (mem[0xFFFE] << 8) | mem[0xFFFF];

    }

    /**
     * flyingSpaghettiMethod (for now)
     *
     * method to perform the fetch/decode cycle and call the appropriate
     * instructions as they are encountered
     *
     * ~ a finite state machine that pulls an instruction, decodes it, executes
     * and pulls the next until reaching the end of the code in memory
     * 
     * @param bitpattern
     */
    public void flyingSpaghettiMethod() {
        byte bitpattern;

        while(true) {


            if(!execute) {
                while(true) {
                    try {
                        Thread.sleep(800); //sleep to lessen resource use
                        if(execute) {
                            break;
                        }
                    }
                catch(Exception e) {  }
                }
            }

            //get next instruction
            bitpattern = mem[((int)PC & 0x0FFFF)];
            System.out.println("Executing " +Integer.toHexString(bitpattern));
            PC = (PC + 1);

            //allow toggling of stepthrough execution
            if(stepThroughExecution) {
                try {
                    while(true) { //infinite loop ~ an event loop
                        Thread.sleep(800); //sleep to lessen resource use
                        if(step) {
                            step = false;
                            break;
                        }
                    }
                }
                catch(Exception e) {  }
            }//if

            updated = true;


            byte instH = (byte)((bitpattern >> 4) & 0x0F); //right shift to get only first nibble
            byte opcode = (byte)(bitpattern & 0x0F); //AND with 00001111 to get only opcode -- mmm bitmasking


            updated = true;

            //instructions with an upper nibble of < 0x3
            //IMPLIED ADDRESSING

            decode(bitpattern);
            updated = true;

            //check for interrupt
            if(NMI) {
                PC = (mem[0xFFFC] << 8) | (mem[0xFFFD]);
            }
            else if(MI) {
                mem[SP] = (byte)(PC & 0x00FF);
                SP = SP - 1;
                mem[SP] = (byte)((PC >> 8) & 0x00FF);
                SP = SP - 1;

                mem[SP] = (byte)(ACCA);
                SP = SP - 1;
                mem[SP] = (byte)(ACCB);
                SP = SP - 1;


                PC = (mem[0xFFF8] << 8) | (mem[0xFFF9]);
                MI = false;
            }//MI

            else if(reset)
                reset();
            
        }//loop
    }//FSM

    public int indexed() {             //DEBUG ME
        
        int offset = mem[PC];
        PC = (PC + 1);
        this.updated = true;

        byte result = (byte)((IX & 0x00FF) + offset);

        byte carry = (byte)(((((IX & 0x80) & (IX & 0x80))
                | ((IX & 0x80) & ~(result & 0x80))
                | (~(result & 0x80) & (IX & 0x80))) >> 7) & 0x01);

        int address = IX;
        address = address | (result & 0x00FF);
        return address;
    }

    public int extended() {
        
        byte higher = mem[(int)PC & 0x0FFFF];
        PC = (PC + 1);
        byte lower = mem[(int)PC & 0x0FFFF];
        PC = (PC + 1);

        int address = ((higher << 8) & 0xFF00);
        address = address | (lower & 0x00FF);

        return address;
    }

    public int immediate() {
        PC = (PC + 1);
        int addr = (PC - 1) & 0x0FFFF;
        return addr;
    }

    public int immediateLD() {
        PC = (PC + 2);
        int addr = (PC - 2) & 0x0FFFF;
        return addr;
    }

    public int direct() {
        PC = (PC + 1);
        int addr = mem[(int)(PC - 1) & 0x0FFFF] & 0x00FF;
        return addr;
    }

    public void nop() {
        try {
            Thread.sleep(200);
        }
        catch(Exception e) {}
    }

    public void tap() {
        CC = (byte)(CC | (ACCA & 0x003F)); //isolate the bits we want

        updated = true;
    }

    public void tpa() {
        clr('a');
        ACCA = (byte)((CC & 0x1F));
    }

    public void clr(char accum) {
        if(accum == 'a')  ACCA = (byte)0;
        else if(accum == 'b') ACCB = (byte)0;
    }

    public void inx() {
        IX = (IX + 1);
        if(IX == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);
    }

    public void dex() {
        IX = (byte)(IX - 1);
        if(IX == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);
    }

    public void clv() { //check
        CC = (byte)(CC & 0xFD);
    }

    public void sev() {
        CC = (byte)(CC | 0x02);
    }

    public void clc() {
        CC = (byte)(CC & 0xFE);
    }

    public void sec() {
        CC = (byte)(CC | 0x01);
    }

    public void cli() {
        CC = (byte)(CC & 0xEF);
    }
    public void sei() {
        CC = (byte)(CC | 0x10);
    }
    public void sba() {
        int result = (byte)(ACCA - ACCB);

        //set condition codes

        //set N
        if((result & 0x80) != 0)
            CC = (byte)(CC | 0x08);
        else
            CC = (byte)(CC & 0xF7);
        //set Z
        if(result == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);
        //set V
        CC = (byte)(CC | (((ACCA & 0x80) & ~(ACCB & 0x80) & ~(result & 0x80))
                | (~(ACCA & 0x80) & (ACCB & 0x80) & (result & 0x80))) >> 6 & 0x02);
        //set C
        CC = (byte)(CC | ((~(ACCA & 0x80) & (ACCB & 0x80))
                | ((ACCB & 0x80) & (result & 0x80))
                | (result & 0x80) & ~(ACCA & 0x80)) >> 7);

        ACCA = (byte)result;
    }
    public void cba() {
        int result = ACCA - ACCB;
        //set N
        if((result & 0x80) != 0)
            CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
        else
            CC = (byte)(CC & 0xF7);
        //set Z
        if(result == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC | ((((ACCA & 0x80) & ~(ACCB & 0x80) & ~(result & 0x80))
                + (~(ACCA & 0x80) & (ACCB & 0x80) & (result & 0x80)) >> 6) & 0x02));

        //set C
        CC = (byte)(CC | ((((~(ACCA & 0x80) & (ACCB & 0x80))
                + ((ACCB & 0x80) & (result & 0x80))
                + (result & 0x80) & ~(ACCA & 0x80)) >> 7)));
    }
    public void tab() {
        ACCB = ACCA;

        //set N
        if((ACCB & 0x80) != 0)
            CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(ACCB == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC & 0xFD);
    }
    public void tba() {
        ACCA = ACCB;

        //set N
        if((ACCA & 0x80) != 0)
            CC = (byte)(CC | 0x08);
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(ACCA == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC & 0xFD);
    }
    public void daa() {
        int result;
        byte upper = (byte)(((ACCA & 0xF0) >> 4) & 0x0F);
        byte lower = (byte)(ACCA & 0x0F);

        if((CC & 0x01) == 0) {
            if(upper < 0xA) {
                if((CC & 0x40) != 0)
                    ACCA = (byte)(ACCA + 0x06);
                else if(lower > 0x9)
                    ACCA = (byte)(ACCA + 0x06);
            }

            if(upper > 0x9) {
                ACCA = (byte)(ACCA + 0x60);
                //set C
                CC = (byte)(CC | 0x01);
            }
            else {
                ACCA = (byte)(ACCA + 0x66);
                CC = (byte)(CC | 0x01);
            }
        }

        else if((CC & 0x01) != 0) {
            if(lower < 0xA && lower > 0x3) {
                ACCA = (byte)(ACCA + 0x6);
            }
            else {
                ACCA = (byte)(ACCA + 0x66);
            }
            CC = (byte)(CC | 0x01);
        }

        //set other flags
        //set N
        if((ACCA & 0x80) != 0)
            CC = (byte)(CC | 0x08);
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(ACCA == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);
    }//daa

    public void aba() {
        int result = ACCA + ACCB;

        //set H
        CC = (byte)(CC | ((((ACCA & 0x08) & (ACCB & 0x08))
                | ((ACCB & 0x08) & ~(result & 0x08))
                | (~(result & 0x08) & (ACCA & 0x08))) >> 2 & 0x20));

        //set N
        if((result & 0x8) != 0)
            CC = (byte)(CC | 0x8);
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(result == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC | (((ACCA & 0x80) & (ACCB & 0x80) & ~(result & 0x80))
                | (~(ACCA & 0x80) & ~(ACCB & 0x80) & (result & 0x80))) >> 6 & 0x02);

        //set C
        CC = (byte)(CC | (((ACCA & 0x80) & (ACCB & 0x80)) + ((ACCB & 0x80) & ~(result & 0x80))
                + (~(result & 0x80) & (ACCA & 0x80)) >> 7));

        ACCA = (byte)result;
    }
    public void bra() {
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        PC = ((int)(PC & 0x0FFFF) + offset);

        this.updated = true;
    }
    public void bhi() {
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //if Z and C CC are clear, branch
        if((((CC & 0x01) << 3) & (CC & 0x04)) == 0)
            PC = ((int)(PC & 0x0FFFF)  + offset);

    }
    public void bls() {

        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch of c is set OR z is set
        if(((CC & 0x01) | (CC & 0x04)) != 0) 
            PC = ((int)(PC & 0x0FFFF)  + offset);
    }
    public void bcc() {

        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if C is clear
        if(((CC & 0x01)) == 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bcs() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if C is set
        if(((CC & 0x01)) != 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bne() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if not equal (Z is 0)
        if(((CC & 0x04)) == 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void beq() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if not equal (Z is 1)
        if(((CC & 0x04)) != 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bvc() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if overflow clear (V is 0)
        if(((CC & 0x02)) == 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bvs() {
       
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if overflow set (V is 1)
        if(((CC & 0x02)) != 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bpl() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if plus (N is 0)
        if(((CC & 0x08)) == 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bmi() {
        
        byte offset = mem[PC];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if minus (N is 1)
        if(((CC & 0x08)) != 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bge() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if greater or equal 0
        if((((CC & 0x08)) ^ (CC & 0x02)) == 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void blt() { //mmmmm BLT
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if less than 0
        if((((CC & 0x08)) ^ (CC & 0x02)) != 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void bgt() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if greater than 0
        if(((CC & 0x04) | ((CC & 0x08) ^ (CC & 0x02))) == 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    public void ble() {
        
        byte offset = mem[(int)PC & 0x0FFFF];
        PC = ((int)(PC & 0x0FFFF) + 1);

        //branch if less than or equal to 0
        if(((CC & 0x04) | ((CC & 0x08) ^ (CC & 0x02))) != 0)
            PC = ((int)(PC & 0x0FFFF) + offset);
    }
    //oh my god branches are done, someone up there likes me
    public void tsx() {
        IX = (mem[SP] + 1);
    }
    public void ins() {
        SP = (SP+1);
    }
    public void pul(char loc, int addr) {
        if(loc == 'a') {
            SP = (SP + 1);
            ACCA = mem[SP]; }
        else if(loc == 'b') {
            SP = (SP + 1);
            ACCB = mem[SP]; }
    }
    public void des() {
        SP = (SP - 1);
    }
    public void txs() {
        SP = (IX - 1);
    }
    public void psh(char loc, int addr) {
        addr = SP;

        if(loc == 'a')
            mem[SP] = ACCA;
        else if(loc == 'b')
            mem[SP] = ACCB;

        SP = (SP - 1);
    }
    public void neg(char loc, int addr) {
        if(loc == 'a') {
            ACCA = (byte)(0 - ACCA);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set C
            if((ACCA & 0x0FF) != 0x0)
                CC = (byte)(CC | 0x1);

            //set V
            if((ACCA & 0x0FF) == 0x80)
                CC = (byte)(CC | 0x2);


        }
        else if(loc == 'b') {
            ACCB = (byte)(0 - ACCB);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set C
            if((ACCB & 0x0FF) != 0x0)
                CC = (byte)(CC | 0x1);

            //set V
            if((ACCB & 0x0FF) == 0x80)
                CC = (byte)(CC | 0x2);
        }
        else if(loc == 'm') {
            mem[addr] = (byte)(0 - mem[addr]);

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set C
            if((mem[addr] & 0x0FF) != 0x0)
                CC = (byte)(CC | 0x1);

            //set V
            if((mem[addr] & 0x0FF) == 0x80)
                CC = (byte)(CC | 0x2);
        }
    }
    public void com(char loc, int addr) {
        if(loc == 'a') {
            ACCA = (byte)(0xFF - ACCA);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);

            //set C
            CC = (byte)(CC | 0x01);
        }
        else if(loc == 'b') {
            ACCB = (byte)(0xFF - ACCB);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);

            //set C
            CC = (byte)(CC | 0x01);
        }
        else if(loc == 'm') {
            mem[addr] = (byte)(0xFF - mem[addr]);

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | ((mem[addr] & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);

            //set C
            CC = (byte)(CC | 0x01);
        }
    }
    public void lsr(char loc, int addr) {
        if(loc == 'a') {
            //set C first so we can just use that bit before it's lost
            if((ACCA & 0x01) != 0)
                CC = (byte)(CC | (ACCA & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            ACCA = (byte)(ACCA >> 1); //LSR

            //set N
            CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x80) ^ ((CC & 0x1) << 7) >> 6));
        }
        else if(loc == 'b') {
            //set C first so we can just use that bit before it's lost
            if((ACCB & 0x01) != 0)
                CC = (byte)(CC | (ACCB & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            ACCB = (byte)(ACCB >> 1); //LSR

            //set N
            CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x80) ^ ((CC & 0x1) << 7) >> 6));
        }
        else if(loc == 'm') {
            //set C first so we can just use that bit before it's lost
            if((mem[addr] & 0x01) != 0)
                CC = (byte)(CC | (mem[addr] & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            mem[addr] = (byte)(mem[addr] >> 1); //LSR

            //set N
            CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x80) ^ ((CC & 0x1) << 7) >> 6));
        }
    }
    public void ror(char loc, int addr) {
        if(loc == 'a') {
            //set C first so we can just use that bit before it's lost
            if((ACCA & 0x01) != 0)
                CC = (byte)(CC | (ACCA & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            //perform the rotate and shift C in
            ACCA = (byte)(ACCA >> 1);
            if((CC & 0x01) != 0)
                ACCA = (byte)(ACCA | (0x01 << 7));

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
        if(loc == 'b') {
            //set C first so we can just use that bit before it's lost
            if((ACCB & 0x01) != 0)
                CC = (byte)(CC | (ACCB & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            //perform the rotate and shift C in
            ACCB = (byte)(ACCB >> 1);
            if((CC & 0x01) != 0)
                ACCA = (byte)(ACCA | (0x01 << 7));

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
        if(loc == 'm') {
            //set C first so we can just use that bit before it's lost
            if((mem[addr] & 0x01) != 0)
                CC = (byte)(CC | (mem[addr] & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            //perform the rotate and shift C in
            mem[addr] = (byte)(mem[addr] >> 1);
            if((CC & 0x01) != 0)
                mem[addr] = (byte)(mem[addr] | (0x01 << 7));

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | ((mem[addr] & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }

    }
    public void asr(char loc, int addr) {
        if(loc == 'a') {
            byte b7 = (byte)(ACCA & 0x80);

            //set C
            if((ACCA & 0x01) != 0)
                CC = (byte)(CC | 0x1);
            else
                CC = (byte)(CC & 0xFE);

            //shift right
            ACCA = (byte)(ACCA >> 1);
            ACCA = (byte)(ACCA | b7);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | 0x8);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
        else if(loc == 'b') {
            byte b7 = (byte)(ACCB & 0x80);

            //set C
            if((ACCB & 0x01) != 0)
                CC = (byte)(CC | (ACCB & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            //shift right
            ACCB = (byte)(ACCB >> 1);
            ACCB = (byte)(ACCB | b7);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
        else if(loc == 'm') {
            byte b7 = (byte)(mem[addr] & 0x80);

            //set C
            if((mem[addr] & 0x01) != 0)
                CC = (byte)(CC | (mem[addr] & 0x01));
            else
                CC = (byte)(CC & 0xFE);

            //shift right
            mem[addr] = (byte)(mem[addr] >> 1);
            mem[addr] = (byte)(mem[addr] | b7);

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | ((mem[addr] & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
    }//asr
    public void asl(char loc, int addr) {
        if(loc == 'a') {
            //set C
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | 0x1);
            else
                CC = (byte)(CC & 0xFE);

            //shift left
            ACCA = (byte)(ACCA << 1);


            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
        else if(loc == 'b') {

            //set C
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | 0x1);
            else
                CC = (byte)(CC & 0xFE);

            //shift left
            ACCB = (byte)(ACCB << 1);


            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
        else if(loc == 'm') {
            //set C
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | 0x1);
            else
                CC = (byte)(CC & 0xFE);

            //shift left
            mem[addr] = (byte)(mem[addr] << 1);


            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | 0x8);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x8) ^ ((CC & 0x1) << 3)) >> 2);
        }
    }//asl 25,m,ontario, u????

    public void rol(char loc, int addr) {
        if(loc == 'a') {
            byte b7 = (byte)(ACCA & 0x80);
            //set C
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | (ACCA & 0x80));
            else
                CC = (byte)(CC & 0xFE);

            //shift left
            ACCA = (byte)(ACCA << 1);
            ACCA = (byte)(ACCA | ((b7 >> 7) & 0x01));

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x08) ^ (CC & 0x01)));
        }
        else if(loc == 'b') {
            byte b7 = (byte)(ACCB & 0x80);
            //set C
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xFE);

            //shift left
            ACCB = (byte)(ACCB << 1);
            ACCB = (byte)(ACCB | ((b7 >> 7) & 0x01));

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x08) ^ (CC & 0x01 << 7)) >> 6 & 0x02);
        }
        else if(loc == 'm') {
            byte b7 = (byte)(mem[addr] & 0x80);
            //set C
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | ((mem[addr] & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xFE);

            //shift left
            mem[addr] = (byte)(mem[addr] << 1);
            mem[addr] = (byte)(mem[addr] | ((b7 >> 7) & 0x01));

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | ((mem[addr] & 0x80) >> 4));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC | ((CC & 0x08) ^ (CC & 0x01 << 7)) >> 6 & 0x02);
        }
    }//rol

    public void dec(char loc, int addr) {
        if(loc == 'a') {
            byte pre = ACCA;
            ACCA = (byte)(ACCA - 1);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x4);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((pre & 0x0FF) == 0x80)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'b') {
            byte pre = ACCB;
            ACCB = (byte)(ACCB - 1);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((pre & 0x0FF) == 0x80)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'm') {
            byte pre = mem[addr];
            mem[addr] = (byte)(mem[addr] - 1);

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | ((mem[addr] & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((pre & 0x0FF) == 0x80)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);
        }
    }//dec

    public void inc(char loc, int addr) {
        if(loc == 'a') {
            byte pre = ACCA;
            ACCA = (byte)(ACCA + 1);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | 0x4);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((pre & 0x0FF) == 0x7F)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'b') {
            byte pre = ACCB;
            ACCB = (byte)(ACCB + 1);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((pre & 0x0FF) == 0x7F)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'm') {
            byte pre = mem[addr];
            mem[addr] = (byte)(mem[addr] + 1);

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | ((mem[addr] & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((pre & 0x0FF) == 0x7F)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);
        }
    }//inc

    public void tst(char loc, int addr) {
        if(loc == 'a') {
            ACCA = (byte)(ACCA - 0x00);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | 0x8);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //clear V
            CC = (byte)(CC & 0xFD);

            //clear C
            CC = (byte)(CC & 0xFE);
        }
        else if(loc == 'b') {
            ACCB = (byte)(ACCB - 0x00);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | 0x8);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //clear V
            CC = (byte)(CC & 0xFD);

            //clear C
            CC = (byte)(CC & 0xFE);
        }
        else if(loc == 'm') {
            mem[addr] = (byte)(mem[addr] - 0x00);

            //set N
            if((mem[addr] & 0x80) != 0)
                CC = (byte)(CC | 0x8);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(mem[addr] == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //clear V
            CC = (byte)(CC & 0xFD);

            //clear C
            CC = (byte)(CC & 0xFE);
        }
    }//tst

    public void jmp(char loc, int addr) {
        PC = mem[addr];
    }//jmp

    public void clr(char loc, int addr) {
        if(loc == 'a') {
            ACCA = 0x0;

            //set N
            CC = (byte)(CC & 0xF7);

            //set Z
            CC = (byte)(CC | 0x04);

            //set V
            CC = (byte)(CC & 0xFD);

            //set C
            CC = (byte)(CC & 0xFE);
        }
        else if(loc == 'b') {
            ACCB = 0x0;

            //set N
            CC = (byte)(CC & 0xF7);

            //set Z
            CC = (byte)(CC | 0x04);

            //set V
            CC = (byte)(CC & 0xFD);

            //set C
            CC = (byte)(CC & 0xFE);
        }
        else if(loc == 'm') {
            mem[addr] = 0x0;

            //set N
            CC = (byte)(CC & 0xF7);

            //set Z
            CC = (byte)(CC | 0x04);

            //set V
            CC = (byte)(CC & 0xFD);

            //set C
            CC = (byte)(CC & 0xFE);
        }
    }//clr

    public void sub(char loc, int addr) {
        if(loc == 'a') {
            byte result = (byte)(ACCA - ((int)mem[addr] & 0x0FF));

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if(((ACCA & 0x80) & ~(mem[addr] & 0x80) & ~(result & 0x80)
                 & ~(ACCA & 0x80) & (mem[addr] & 0x80) & (result & 0x80)) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if(java.lang.Math.abs((int)mem[addr])
                    > java.lang.Math.abs((int)ACCA))
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            //set the Accumulator to the result
            ACCA = result;

        }
        else if(loc == 'b') {
            byte result = (byte)(ACCB - ((int)mem[addr] & 0x0FF));

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if(((ACCB & 0x80) & ~(mem[addr] & 0x80) & ~(result & 0x80)
                 & ~(ACCB & 0x80) & (mem[addr] & 0x80) & (result & 0x80)) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if(java.lang.Math.abs((int)mem[addr])
                    > java.lang.Math.abs((int)ACCB))
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            //set the Accumulator to the result
            ACCB = result;
        }
    }//sub

    public void cmp(char loc, int addr) {
        if(loc == 'a') {

            byte result = (byte)(ACCA - mem[addr]);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCA & 0x80) & ~(mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCA & 0x80) & (mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if(java.lang.Math.abs((int)mem[addr])
                    > java.lang.Math.abs((int)ACCA))
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

        }
        else if(loc == 'b') {
            byte result = (byte)(ACCB - mem[addr]);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCB & 0x80) & ~(mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCB & 0x80) & (mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if(java.lang.Math.abs((int)mem[addr])
                    > java.lang.Math.abs((int)ACCB))
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);
        }
    }//cmp

    public void sbc(char loc, int addr) {
        if(loc == 'a') {
            byte result = (byte)(ACCA - mem[addr] - (CC & 0x01));
            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | (((result & 0x80) >> 4) & 0x08));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCA & 0x80) & ~(mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCA & 0x80) & (mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if(java.lang.Math.abs((int)mem[addr]) + (CC & 0x01)
                    > java.lang.Math.abs((int)ACCA))
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            ACCA = result;
        }
        else if(loc == 'b') {
            byte result = (byte)(ACCB - mem[addr] - (CC & 0x01));

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | (((result & 0x80) >> 4) & 0x08));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCB & 0x80) & ~(mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCB & 0x80) & (mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if(java.lang.Math.abs((int)mem[addr]) + (CC & 0x01)
                    > java.lang.Math.abs((int)ACCB))
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            ACCB = result;
        }
    }//sbc
    
    public void and(char loc, int addr) {
        if(loc == 'a') {
            ACCA = (byte)(ACCA & mem[addr]);
            
            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | (((ACCA & 0x80) >> 4) & 0x08));
            else
                CC = (byte)(CC & 0xF7);
            
            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);
            
            //set V
            CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'b') {
            ACCB = (byte)(ACCB & mem[addr]);
            
            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | (((ACCB & 0x80) >> 4) & 0x08));
            else
                CC = (byte)(CC & 0xF7);
            
            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);
            
            //set V
            CC = (byte)(CC & 0xFD);
        }
    }//and

    public void bit(char loc, int addr) {
        if(loc == 'a') {
            byte result = (byte)(ACCA & mem[addr]);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | (((result & 0x80) >> 4) & 0x08));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);

        }
        else if(loc == 'b') {
            byte result = (byte)(ACCB & mem[addr]);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);
        }
    }//and

    public void lda(char loc, int addr) {
        if(loc == 'a') {
            ACCA = mem[addr];
            
            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'b') {
            ACCB = mem[addr];

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);
        }
    }//lda

    public void sta(char loc, int addr) {
        if(loc == 'a') {
            mem[addr] = ACCA;
            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'b') {
            mem[addr] = ACCB;

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);
        }
    }//sta

    public void eor(char loc, int addr) {
        if(loc == 'a') {
            ACCA = (byte)(ACCA ^ mem[addr]);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);
        }
        else if(loc == 'b') {
            ACCB = (byte)(ACCB ^ mem[addr]);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFD);
        }
    }//eor -- oh bother.

    public void adc(char loc, int addr) {
        if(loc == 'a') {
            byte result = (byte)(ACCA + mem[addr] + (CC & 0x01));

            //set H
            if((((ACCA & 0x08) & (mem[addr] & 0x08))) + ((mem[addr] & 0x08)
               & ~(result & 0x08)) + ((~(result & 0x08) & (ACCA & 0x08))) != 0)
               CC = (byte)(CC | 0x20);
            else
                CC = (byte)(CC & 0xDF);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCA & 0x80) & (mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCA & 0x80) & ~(mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if((((ACCA & 0x80) & (mem[addr] & 0x80)) + ((mem[addr] & 0x80)
                & ~(result & 0x80)) + (~(result & 0x80) & (ACCA & 0x80))) != 0)
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            //assign result to accumulator
            ACCA = result;
        }
        else if(loc == 'b') {
            byte result = (byte)(ACCB + mem[addr] + (CC & 0x01));

            //set H
            if((((ACCB & 0x08) & (mem[addr] & 0x08))) + ((mem[addr] & 0x08)
               & ~(result & 0x08)) + ((~(result & 0x08) & (ACCB & 0x08))) != 0)
               CC = (byte)(CC | 0x20);
            else
                CC = (byte)(CC & 0xDF);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((result & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCB & 0x80) & (mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCB & 0x80) & ~(mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if((((ACCB & 0x80) & (mem[addr] & 0x80)) + ((mem[addr] & 0x80)
                & ~(result & 0x80)) + (~(result & 0x80) & (ACCB & 0x80))) != 0)
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            //assign result to accumulator
            ACCB = result;
        }
    }//adc

    public void ora(char loc, int addr) {
        if(loc == 'a') {
            ACCA = (byte)(ACCA | mem[addr]);

            //set N
            if((ACCA & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCA == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFE);
        }
        else if(loc == 'b') {
            ACCB = (byte)(ACCB | mem[addr]);

            //set N
            if((ACCB & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4));
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(ACCB == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            CC = (byte)(CC & 0xFE);
        }
    }//ora

    public void add(char loc, int addr) {
        if(loc == 'a') {
            byte result = (byte)(ACCA + mem[addr]);

            //set H
            if((((ACCA & 0x08) & (mem[addr] & 0x08))) + ((mem[addr] & 0x08)
               & ~(result & 0x08)) + ((~(result & 0x08) & (ACCA & 0x08))) != 0)
               CC = (byte)(CC | 0x20);
            else
                CC = (byte)(CC & 0xDF);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((ACCA & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCA & 0x80) & (mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCA & 0x80) & ~(mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if((((ACCA & 0x80) & (mem[addr] & 0x80)) + ((mem[addr] & 0x80)
                & ~(result & 0x80)) + (~(result & 0x80) & (ACCA & 0x80))) != 0)
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            ACCA = result;
        }
        else if(loc == 'b') {
            byte result = (byte)(ACCB + mem[addr]);

            //set H
            if((((ACCB & 0x08) & (mem[addr] & 0x08))) + ((mem[addr] & 0x08)
               & ~(result & 0x08)) + ((~(result & 0x08) & (ACCB & 0x08))) != 0)
               CC = (byte)(CC | 0x20);
            else
                CC = (byte)(CC & 0xDF);

            //set N
            if((result & 0x80) != 0)
                CC = (byte)(CC | ((ACCB & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);

            //set Z
            if(result == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);

            //set V
            if((((ACCB & 0x80) & (mem[addr] & 0x80) & ~(result & 0x80))
               + (~(ACCB & 0x80) & ~(mem[addr] & 0x80) & (result & 0x80))) != 0)
                CC = (byte)(CC | 0x02);
            else
                CC = (byte)(CC & 0xFD);

            //set C
            if((((ACCB & 0x80) & (mem[addr] & 0x80)) + ((mem[addr] & 0x80)
                & ~(result & 0x80)) + (~(result & 0x80) & (ACCB & 0x80))) != 0)
                CC = (byte)(CC | 0x01);
            else
                CC = (byte)(CC & 0xFE);

            ACCB = result;
        }
    }//add -- I think this is giving me add

    public void cpx(int addr) {
        byte high;
        byte low;
        
        high = (byte)((((IX & 0xFF00) >> 8) & 0x00FF) - mem[addr]);
        PC = (PC + 1);
        low = (byte)((IX & 0x00FF) - mem[addr+1]);
        
        //set N
        if((high & 0x80) != 0)
                CC = (byte)(CC | ((high & 0x80) >> 4) & 0x08);
            else
                CC = (byte)(CC & 0xF7);
        
        //set Z
            if(high == 0 && low == 0)
                CC = (byte)(CC | 0x04);
            else
                CC = (byte)(CC & 0xFB);
        
        //set V
        if((((IX & 0x8000) & ~(mem[addr]) & ~(high & 0x80)) + (~(IX & 0x8000)
                & (mem[addr]) & (high & 0x80))) != 0)
            CC = (byte)(CC | 0x02);
        else
            CC = (byte)(CC & 0xFD);
    }//cpx

    public void bsr() {
        byte rel = mem[PC + 1];

        PC = (byte)(PC + 2);
        mem[SP] = (byte)(PC & 0x00FF);
        SP = (SP - 1);

        mem[SP] = (byte)(((PC & 0xFF00) >> 8) & 0x00FF);
        SP = (SP - 1);

        PC = (byte)(PC + rel);
    }//bsr

    public void jsr(int addr) {

        mem[SP] = (byte)(PC & 0x00FF);
        SP = (SP - 1);

        mem[SP] = (byte)(((PC & 0xFF00) >> 8) & 0x00FF);
        SP = (SP - 1);

        PC = addr;
    }//jsr

    public void lds(int addr) {
        SP = 0;
        int high = (byte)(((SP & 0xFF00) | (mem[addr] << 8) >> 8) & 0x00FF);
        int low = (byte)((SP & 0x00FF) | (mem[addr + 1] & 0x00FF));

        //set N
        if((high & 0x80) != 0)
            CC = (byte)(CC | ((high & 0x80) >> 4));
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(high == 0 && low == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC & 0xFD);

        SP = ((high << 8) & 0xFF00);
        SP = SP | (low & 0x00FF);
    }//lds

    public void ldx(int addr) {
        IX = 0;
        int high = (byte)((IX & 0xFF00) | (mem[addr]) & 0x00FF);
        int low = (byte)((IX & 0x00FF) | (mem[addr + 1] & 0x00FF));

        //set N
        if((high & 0x80) != 0)
            CC = (byte)(CC | ((high & 0x80) >> 4));
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(high == 0 && low == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC & 0xFD);

        IX = ((high << 8) & 0xFF00);
        IX = IX | (low & 0x00FF);

        System.out.println("loading " +Integer.toHexString(IX) +" from " +Integer.toHexString(addr) +" at PC " +Integer.toHexString(PC));
    }//ldx

    public void sts(int addr) {
        byte high = (byte)(((SP & 0xFF00) >> 8) & 0x00FF);
        byte low = (byte)((SP & 0x00FF));

        //set N
        if((high & 0x80) != 0)
            CC = (byte)(CC | ((high & 0x80) >> 4));
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(high == 0 && low == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC & 0xFD);

        mem[addr] = high;
        mem[addr+1] = low;

    }//sts

    public void stx(int addr) {
        byte high = (byte)(((IX & 0xFF00) >> 8) & 0x00FF);
        byte low = (byte)((IX & 0x00FF));

        //set N
        if((high & 0x80) != 0)
            CC = (byte)(CC | ((high & 0x80) >> 4));
        else
            CC = (byte)(CC & 0xF7);

        //set Z
        if(high == 0 && low == 0)
            CC = (byte)(CC | 0x04);
        else
            CC = (byte)(CC & 0xFB);

        //set V
        CC = (byte)(CC & 0xFD);

        mem[addr] = high;
        mem[addr+1] = low;
    }//stx

    public void wai() {
        try {
            while(((CC & 0x10) == 0) & !NMI & !MI)
                Thread.sleep(500);
        }
        catch(Exception e) { }
    }//wai

    public void rts() {
        SP = SP + 1;
        PC = (mem[SP] << 8);
        SP = SP + 1;
        PC = (PC | mem[SP]);
    }//rts

    public void rti() {
        SP = SP + 1;
        CC = mem[SP];
        SP = SP + 1;
        ACCB = mem[SP];
        SP = SP + 1;
        ACCA = mem[SP];
        SP = SP + 1;

        IX = (mem[SP] << 8);
        SP = SP + 1;
        IX = (IX | mem[SP]);
        SP = SP + 1;

        PC = (mem[SP] << 8);
        SP = SP + 1;
        PC = (PC | mem[SP]);
    }//rti

    public void swi() {

        mem[SP] = (byte)((PC & 0x00FF));
        SP = SP - 1;
        mem[SP] = (byte)(((PC & 0xFF00) >> 8) & 0x00FF);
        SP = SP - 1;

        mem[SP] = (byte)((IX & 0x00FF));
        SP = SP - 1;
        mem[SP] = (byte)(((IX & 0xFF00) >> 8) & 0x00FF);
        SP = SP - 1;

        mem[SP] = ACCA;
        SP = SP - 1;
        mem[SP] = ACCB;
        SP = SP - 1;

        mem[SP] = CC;
        SP = SP - 1;

        CC = (byte)(CC | 0x10);

        PC = (((mem[0xFFFA]) << 8));
        PC = (PC | (mem[0xFFFB]));
        
    }//swi

    public void loadROM() {
       fileParser f = new fileParser();

       File file = new File(getClass().getResource("/_emulator/resources/ROM/RESET.S19").getFile());
       f.parse(file, this);

       file = new File(getClass().getResource("/_emulator/resources/ROM/OUTHEX.S19").getFile());
       f.parse(file, this);

       file = new File(getClass().getResource("/_emulator/resources/ROM/INTER.S19").getFile());
       f.parse(file, this);

       file = new File(getClass().getResource("/_emulator/resources/ROM/VECTOR.S19").getFile());
       f.parse(file, this);

       file = new File(getClass().getResource("/_emulator/resources/ROM/GETCHAR.S19").getFile());
       f.parse(file, this);

       file = new File(getClass().getResource("/_emulator/resources/ROM/LOOKUPTABLE.S19").getFile());
       f.parse(file, this);

       mem[0xCB00] = (byte)0xC0;
       mem[0xCB01] = (byte)0x00;
       mem[0xCB02] = (byte)0xC0;
       mem[0xCB03] = (byte)0x00;
       
       this.updated = true;
    }
    
    public int[] test(int[] in) {
        int memLocation;
        int instruction;

        System.out.println("in test");

        this.ACCA = (byte)in[0];
        this.ACCB = (byte)in[1];
        this.CC = (byte)in[2];
        this.SP = (byte)in[3];
        this.IX = (byte)in[4];
        memLocation = in[5];
        this.mem[memLocation] = (byte)in[6];
        this.PC = in[7];
        instruction = in[8];

        System.out.println("in read");

        decode(instruction);       //call the test instruction

        //create output array
        int[] output = new int[8];
        output[0] = this.ACCA;
        output[1] = this.ACCB;
        output[2] = this.CC;
        output[3] = this.SP;
        output[4] = this.IX;
        output[5] = in[6];
        output[6] = this.mem[memLocation];
        output[7] = this.PC;
        
        System.out.println("out done");

        return output;
    }//test

    private void decode(int bitpattern) {
        byte instH = (byte)((bitpattern >> 4) & 0x0F); //right shift to get only first nibble
        byte opcode = (byte)(bitpattern & 0x0F); //AND with 00001111 to get only opcode -- mmm bitmasking
        updated = true;


        if (instH < 0x03) {
            switch (bitpattern) {
                case 0x01:
                    nop();
                    break;
                case 0x06:
                    tap();
                    break;
                case 0x07:
                    tpa();
                    break;
                case 0x08:
                    inx();
                    break;
                case 0x09:
                    dex();
                    break;
                case 0x0A:
                    clv();
                    break;
                case 0x0B:
                    sev();
                    break;
                case 0x0C:
                    clc();
                    break;
                case 0x0D:
                    sec();
                    break;
                case 0x0E:
                    cli();
                    break;
                case 0x0F:
                    sei();
                    break;
                case 0x10:
                    sba();
                    break;
                case 0x11:
                    cba();
                    break;
                case 0x14:
                    ;
                    break;
                case 0x16:
                    tab();
                    break;
                case 0x17:
                    tba();
                    break;
                case 0x19:
                    daa();
                    break;
                case 0x1B:
                    aba();
                    break;
                case 0x20:
                    bra();
                    break;
                case 0x22:
                    bhi();
                    break;
                case 0x23:
                    bls();
                    break;
                case 0x24:
                    bcc();
                    break;
                case 0x25:
                    bcs();
                    break;
                case 0x26:
                    bne();
                    break;
                case 0x27:
                    beq();
                    break;
                case 0x28:
                    bvc();
                    break;
                case 0x29:
                    bvs();
                    break;
                case 0x2A:
                    bpl();
                    break;
                case 0x2B:
                    bmi();
                    break;
                case 0x2C:
                    bge();
                    break;
                case 0x2D:
                    blt();
                    break;
                case 0x2E:
                    bgt();
                    break;
                case 0x2F:
                    ble();
                    break;
            }//switch
        }//if implied addressing
        //instructions with upper nibble < 0x04
        //various modes, ACCUM and interrupt instructions
        else if (instH < 0x04 && instH > 0x02) {
            switch (bitpattern) {
                case 0x30:
                    tsx();
                    break;
                case 0x31:
                    ins();
                    break;
                case 0x32:
                    pul('a', 0);
                    break;
                case 0x33:
                    pul('b', 0);
                    break;
                case 0x34:
                    des();
                    break;
                case 0x35:
                    txs();
                    break;
                case 0x36:
                    psh('a', 0);
                    break;
                case 0x37:
                    psh('b', 0);
                    break;
                case 0x39:
                    rts();
                    break;
                case 0x3B:          //implemented in stage 3
                    rti();
                    break;          //as they're sw interrupts */
                case 0x3E:
                    wai();
                    break;
                case 0x3F:
                    swi();
                    break;
            }//switch
        }//else stach/interrupts addressing
        else if (instH < 0x08 && instH > 0x03) {
            switch (opcode) {
                case 0x00:
                    if (instH == 0x4) {
                        neg('a', 0);
                    } else if (instH == 0x5) {
                        neg('b', 0);
                    } else if (instH == 0x6) {
                        neg('m', indexed());
                    } else if (instH == 0x7) {
                        neg('m', extended());
                    }
                    break;
                case 0x03:
                    if (instH == 0x4) {
                        com('a', 0);
                    } else if (instH == 0x5) {
                        com('b', 0);
                    } else if (instH == 0x6) {
                        com('m', indexed());
                    } else if (instH == 0x7) {
                        com('m', extended());
                    }
                    break;
                case 0x04:
                    if (instH == 0x4) {
                        lsr('a', 0);
                    } else if (instH == 0x5) {
                        lsr('b', 0);
                    } else if (instH == 0x6) {
                        lsr('m', indexed());
                    } else if (instH == 0x7) {
                        lsr('m', extended());
                    }
                    break;
                case 0x06:
                    if (instH == 0x4) {
                        ror('a', 0);
                    } else if (instH == 0x5) {
                        ror('b', 0);
                    } else if (instH == 0x6) {
                        ror('m', indexed());
                    } else if (instH == 0x7) {
                        ror('m', extended());
                    }
                    break;
                case 0x07:
                    if (instH == 0x4) {
                        asr('a', 0);
                    } else if (instH == 0x5) {
                        asr('b', 0);
                    } else if (instH == 0x6) {
                        asr('m', indexed());
                    } else if (instH == 0x7) {
                        asr('m', extended());
                    }
                    break;
                case 0x08:
                    if (instH == 0x4) {
                        asl('a', 0);
                    } else if (instH == 0x5) {
                        asl('b', 0);
                    } else if (instH == 0x6) {
                        asl('m', indexed());
                    } else if (instH == 0x7) {
                        asl('m', extended());
                    }
                    break;
                case 0x09:
                    if (instH == 0x4) {
                        rol('a', 0);
                    } else if (instH == 0x5) {
                        rol('b', 0);
                    } else if (instH == 0x6) {
                        rol('m', indexed());
                    } else if (instH == 0x7) {
                        rol('m', extended());
                    }
                    break;
                case 0x0A:
                    if (instH == 0x4) {
                        dec('a', 0);
                    } else if (instH == 0x5) {
                        dec('b', 0);
                    } else if (instH == 0x6) {
                        dec('m', indexed());
                    } else if (instH == 0x7) {
                        dec('m', extended());
                    }
                    break;
                case 0x0C:
                    if (instH == 0x4) {
                        inc('a', 0);
                    } else if (instH == 0x5) {
                        inc('b', 0);
                    } else if (instH == 0x6) {
                        inc('m', indexed());
                    } else if (instH == 0x7) {
                        inc('m', extended());
                    }
                    break;
                case 0x0E:
                    if (instH == 0x6) {
                        jmp('m', indexed());
                    } else if (instH == 0x7) {
                        jmp('m', extended());
                    }
                    break;
                case 0x0F:
                    if (instH == 0x4) {
                        clr('a', 0);
                    } else if (instH == 0x5) {
                        clr('b', 0);
                    } else if (instH == 0x6) {
                        clr('m', indexed());
                    } else if (instH == 0x7) {
                        clr('m', extended());
                    }
                    break;
            }//switch
        }//else
        else {
            switch (opcode) {
                case 0x00:
                    if (instH == 0x8) {
                        sub('a', immediate());
                    } else if (instH == 0x9) {
                        sub('a', direct());
                    } else if (instH == 0xA) {
                        sub('a', indexed());
                    } else if (instH == 0xB) {
                        sub('a', extended());
                    } else if (instH == 0xC) {
                        sub('b', immediate());
                    } else if (instH == 0xD) {
                        sub('b', direct());
                    } else if (instH == 0xE) {
                        sub('b', indexed());
                    } else if (instH == 0xF) {
                        sub('b', extended());
                    }
                    break;
                case 0x01:
                    if (instH == 0x8) {
                        cmp('a', immediate());
                    } else if (instH == 0x9) {
                        cmp('a', direct());
                    } else if (instH == 0xA) {
                        cmp('a', indexed());
                    } else if (instH == 0xB) {
                        cmp('a', extended());
                    } else if (instH == 0xC) {
                        cmp('b', immediate());
                    } else if (instH == 0xD) {
                        cmp('b', direct());
                    } else if (instH == 0xE) {
                        cmp('b', indexed());
                    } else if (instH == 0xF) {
                        cmp('b', extended());
                    }
                    break;
                case 0x02:
                    if (instH == 0x8) {
                        sbc('a', immediate());
                    } else if (instH == 0x9) {
                        sbc('a', direct());
                    } else if (instH == 0xA) {
                        sbc('a', indexed());
                    } else if (instH == 0xB) {
                        sbc('a', extended());
                    } else if (instH == 0xC) {
                        sbc('b', immediate());
                    } else if (instH == 0xD) {
                        sbc('b', direct());
                    } else if (instH == 0xE) {
                        sbc('b', indexed());
                    } else if (instH == 0xF) {
                        sbc('b', extended());
                    }
                    break;
                case 0x04:
                    if (instH == 0x8) {
                        and('a', immediate());
                    } else if (instH == 0x9) {
                        and('a', direct());
                    } else if (instH == 0xA) {
                        and('a', indexed());
                    } else if (instH == 0xB) {
                        and('a', extended());
                    } else if (instH == 0xC) {
                        and('b', immediate());
                    } else if (instH == 0xD) {
                        and('b', direct());
                    } else if (instH == 0xE) {
                        and('b', indexed());
                    } else if (instH == 0xF) {
                        and('b', extended());
                    }
                    break;
                case 0x05:
                    if (instH == 0x8) {
                        bit('a', immediate());
                    } else if (instH == 0x9) {
                        bit('a', direct());
                    } else if (instH == 0xA) {
                        bit('a', indexed());
                    } else if (instH == 0xB) {
                        bit('a', extended());
                    } else if (instH == 0xC) {
                        bit('b', immediate());
                    } else if (instH == 0xD) {
                        bit('b', direct());
                    } else if (instH == 0xE) {
                        bit('b', indexed());
                    } else if (instH == 0xF) {
                        bit('b', extended());
                    }
                    break;
                case 0x06:
                    if (instH == 0x8) {
                        lda('a', immediate());
                    } else if (instH == 0x9) {
                        lda('a', direct());
                    } else if (instH == 0xA) {
                        lda('a', indexed());
                    } else if (instH == 0xB) {
                        lda('a', extended());
                    } else if (instH == 0xC) {
                        lda('b', immediate());
                    } else if (instH == 0xD) {
                        lda('b', direct());
                    } else if (instH == 0xE) {
                        lda('b', indexed());
                    } else if (instH == 0xF) {
                        lda('b', extended());
                    }
                    break;
                case 0x07:
                    if (instH == 0x8); //no immediate instruction
                    else if (instH == 0x9) {
                        sta('a', direct());
                    } else if (instH == 0xA) {
                        sta('a', indexed());
                    } else if (instH == 0xB) {
                        sta('a', extended());
                    } else if (instH == 0xC); //no immediate instruction
                    else if (instH == 0xD) {
                        sta('b', direct());
                    } else if (instH == 0xE) {
                        sta('b', indexed());
                    } else if (instH == 0xF) {
                        sta('b', extended());
                    }
                    break;
                case 0x08:
                    if (instH == 0x8) {
                        eor('a', immediate());
                    } else if (instH == 0x9) {
                        eor('a', direct());
                    } else if (instH == 0xA) {
                        eor('a', indexed());
                    } else if (instH == 0xB) {
                        eor('a', extended());
                    } else if (instH == 0xC) {
                        eor('b', immediate());
                    } else if (instH == 0xD) {
                        eor('b', direct());
                    } else if (instH == 0xE) {
                        eor('b', indexed());
                    } else if (instH == 0xF) {
                        eor('b', extended());
                    }
                    break;
                case 0x09:
                    if (instH == 0x8) {
                        adc('a', immediate());
                    } else if (instH == 0x9) {
                        adc('a', direct());
                    } else if (instH == 0xA) {
                        adc('a', indexed());
                    } else if (instH == 0xB) {
                        adc('a', extended());
                    } else if (instH == 0xC) {
                        adc('b', immediate());
                    } else if (instH == 0xD) {
                        adc('b', direct());
                    } else if (instH == 0xE) {
                        adc('b', indexed());
                    } else if (instH == 0xF) {
                        adc('b', extended());
                    }
                    break;
                case 0x0A:
                    if (instH == 0x8) {
                        ora('a', immediate());
                    } else if (instH == 0x9) {
                        ora('a', direct());
                    } else if (instH == 0xA) {
                        ora('a', indexed());
                    } else if (instH == 0xB) {
                        ora('a', extended());
                    } else if (instH == 0xC) {
                        ora('b', immediate());
                    } else if (instH == 0xD) {
                        ora('b', direct());
                    } else if (instH == 0xE) {
                        ora('b', indexed());
                    } else if (instH == 0xF) {
                        ora('b', extended());
                    }
                    break;
                case 0x0B:
                    if (instH == 0x8) {
                        add('a', immediate());
                    } else if (instH == 0x9) {
                        add('a', direct());
                    } else if (instH == 0xA) {
                        add('a', indexed());
                    } else if (instH == 0xB) {
                        add('a', extended());
                    } else if (instH == 0xC) {
                        add('b', immediate());
                    } else if (instH == 0xD) {
                        add('b', direct());
                    } else if (instH == 0xE) {
                        add('b', indexed());
                    } else if (instH == 0xF) {
                        add('b', extended());
                    }
                    break;
                case 0x0C:
                    if (instH == 0x8) {
                        cpx(immediate());
                    } else if (instH == 0x9) {
                        cpx(direct());
                    } else if (instH == 0xA) {
                        cpx(indexed());
                    } else if (instH == 0xB) {
                        cpx(extended());
                    }
                    break;
                case 0x0D:
                    if (instH == 0x8) {
                        bsr();
                    } else if (instH == 0x9); //halt & catch fire
                    else if (instH == 0xA) {
                        jsr(indexed());
                    } else if (instH == 0xB) {
                        jsr(extended());
                    }
                    break;
                case 0x0E:
                    if (instH == 0x8) {
                        lds(immediateLD());
                    } else if (instH == 0x9) {
                        lds(direct());
                    } else if (instH == 0xA) {
                        lds(indexed());
                    } else if (instH == 0xB) {
                        lds(extended());
                    } else if (instH == 0xC) {
                        ldx(immediateLD());
                    } else if (instH == 0xD) {
                        ldx(direct());
                    } else if (instH == 0xE) {
                        ldx(indexed());
                    } else if (instH == 0xF) {
                        ldx(extended());
                    }
                    break;
                case 0x0F:
                    if (instH == 0x9) {
                        sts(direct());
                    } else if (instH == 0xA) {
                        sts(indexed());
                    } else if (instH == 0xB) {
                        sts(extended());
                    } else if (instH == 0xD) {
                        stx(direct());
                    } else if (instH == 0xE) {
                        stx(indexed());
                    } else if (instH == 0xF) {
                        stx(extended());
                    }
                    break;
            }
        }//else
    }//decode
}//CPU
