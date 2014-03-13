/*
 * test.java
 * Test Harness for the Motorola 6800 Emulator
 *
 * Running this class will test every instruction in the instruction
 * set and return errors for instructions that do not return the correct
 * values and set the correct flags
 *
 * David Ketler, 3394947
 *
 */

package testHarness;


public class test {
    static CPU processor;
    static Thread cpu;
    static int flags;

    public static void main(String args[]) {
        processor = new CPU();
        processor.init();

        //NOP
        flags = processor.CC;
        processor.nop();
        if(flags != processor.CC)
            System.out.println("error in NOP");  //NOP does not affect flags

       //TAP
       processor.ACCA = 0x13;
       processor.tap();
       if(processor.CC != 0x13)
           System.out.println("error in TAP");
       processor.ACCA = processor.CC = 0;

       //TPA
       processor.ACCA = 0x00;
       processor.CC = 0x13;
       processor.tpa();
       if(processor.ACCA != 0x13)
           System.out.println("error in TPA");
       processor.ACCA = processor.CC = 0;

       //INX
       processor.IX = -1;
       processor.inx();
       if(processor.CC != 0x4)
           System.out.println("error in INX");
       processor.inx();
       if(processor.CC != 0)
           System.out.println("error in INX");

       //DEX
       processor.IX = 1;
       processor.dex();
       if(processor.CC != 0x4)
           System.out.println("error DEX");
       processor.IX = 2;
       processor.dex();
       if(processor.CC != 0x0)
           System.out.println("error DEX");

       //CLV
       processor.CC = 0x2;
       processor.clv();
       if(processor.CC != 0x0)
           System.out.println("error CLV");

       //SEV
       processor.sev();
       if(processor.CC != 0x2)
           System.out.println("error SEV");

       //CLC
       processor.CC = 0x1;
       processor.clc();
       if(processor.CC != 0x0)
           System.out.println("error SEV");

       //SEC
       processor.CC = 0x0;
       processor.sec();
       if(processor.CC != 1)
           System.out.println("error SEC");

       //CLI
       processor.CC = 0x10;
       processor.cli();
       if(processor.CC != 0)
           System.out.println("error CLI");

       //SEI
       processor.CC = 0x0;
       processor.sei();
       if(processor.CC != 0x10)
           System.out.println("error SEI");
       processor.CC = 0;
       
       //SBA
       processor.ACCB = 0x5;
       processor.ACCA = 0x3;
       processor.sba();
       if(processor.CC != 0x9)
           System.out.println("error SBA");
       processor.CC = 0;
       
       processor.ACCB = 0;
       processor.ACCA = 0;
       processor.sba();
       if(processor.CC != 0x4)
           System.out.println("error SBA");
       //TODO: test V

       //CBA
       processor.ACCB = 0x5;
       processor.ACCA = 0x3;
       processor.cba();
       if(processor.CC != 0x9)
           System.out.println("error CBA");
       processor.CC = 0;

       processor.ACCB = 0;
       processor.ACCA = 0;
       processor.cba();
       if(processor.CC != 0x4)
           System.out.println("error CBA");
       //TODO: test V

       //TAB
       processor.CC = 0;
       processor.ACCA = -2;
       processor.tab();
       if(processor.CC != 0x8)
           System.out.println("error TAB");
       processor.CC = 0;

       processor.ACCA = 0;
       processor.tab();
       if(processor.CC != 0x4)
           System.out.println("error TAB");
       processor.CC = 0;
       processor.ACCA = 0;
       processor.ACCB = 0;
       
       //TBA
       processor.CC = 0;
       processor.ACCB = -2;
       processor.tba();
       if(processor.CC != 0x8)
           System.out.println("error TBA");
       processor.CC = 0;

       processor.ACCB = 0;
       processor.tba();
       if(processor.CC != 0x4)
           System.out.println("error TBA");
       processor.CC = 0;
       processor.ACCA = 0;
       processor.ACCB = 0;

       //ABA
       processor.ACCB = -2;
       processor.aba();
       //if(processor.CC != 0x88)
           //System.out.println("error " +Integer.toBinaryString(processor.CC & 0x0FF));
       //System.out.println(processor.ACCA);

       processor.ACCB = 0;
       processor.ACCA = 0;
       processor.aba();
       if(processor.CC != 0x4)
           System.out.println("error ABA");

       //COM
       processor.ACCA = 0;
       processor.CC = 2; //2 to ensure V is cleared
       processor.com('a',0);
       if(processor.CC != 0x9)
           System.out.println("error COM");
       processor.ACCA = 0;
       processor.CC = 0;

       processor.ACCA = (byte)0xFF;
       processor.com('a',0);
       if(processor.CC != 0x5)
           System.out.println("error COM");

       processor.mem[0] = 0;
       processor.CC = 2;
       processor.com('m',0);
       if(processor.CC != 0x9)
           System.out.println("error COM");
       processor.ACCA = 0;
       processor.CC = 0;

       processor.mem[0] = (byte)0xFF;
       processor.com('m',0);
       if(processor.CC != 0x5)
           System.out.println("error COM");
       processor.CC = 0;
       processor.ACCA = 0;


       //LSR
       processor.CC = 0x8; //ensure N is cleared
       processor.ACCA = 0x1;
       processor.lsr('a',0);
       if(processor.CC != 7)
           System.out.println("error LSR");

       processor.CC = 0x8; //ensure N is cleared
       processor.ACCA = 0x2;
       processor.lsr('a',0);
       if(processor.CC != 0)
           System.out.println("error LSR");

       processor.CC = 0x8; //ensure N is cleared
       processor.mem[0] = 0x1;
       processor.lsr('m',0);
       if(processor.CC != 7)
           System.out.println("error LSR");

       processor.CC = 0x8; //ensure N is cleared
       processor.mem[0] = 0x2;
       processor.lsr('m',0);
       if(processor.CC != 0)
           System.out.println("error LSR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.lsr('m',processor.direct());
       if(processor.CC != 7)
           System.out.println("error direct LSR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.lsr('m',processor.immediate());
       if(processor.CC != 7)
           System.out.println("error immediate LSR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = 0x1;
       processor.lsr('m',processor.extended());
       if(processor.CC != 7)
           System.out.println("error extended LSR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = 0x1;
       processor.lsr('m',processor.indexed());
       if(processor.CC != 7)
           System.out.println("error indexed LSR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //ROR
       processor.CC = 0x8; //ensure N is cleared
       processor.ACCA = 0x1;
       processor.ror('a',0);
       if(processor.CC != 0x9)
           System.out.println("error ACCA ROR");

       processor.CC = 0x8; //ensure N is cleared
       processor.ACCB = 0x2;
       processor.ror('b',0);
       if(processor.CC != 0)
          System.out.println("error ACCB ROR");

       /* TEST MEMORY **************************************************/

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.ror('m',processor.direct());
       if(processor.CC != 9)
           System.out.println("error direct ROR");
              
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.ror('m',processor.immediate());
       if(processor.CC != 9)
           System.out.println("error immediate ROR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = 0x1;
       processor.ror('m',processor.extended());
       if(processor.CC != 9)
           System.out.println("error extended ROR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = 0x1;
       processor.ror('m',processor.indexed());
       if(processor.CC != 9)
           System.out.println("error indexed ROR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //ASR
       processor.CC = 0x0;
       processor.ACCA = (byte)0x80;
       processor.asr('a',0);
       if(processor.CC != 0xA)
           System.out.println("error ACCA ASR");
       
       processor.CC = 0;
       processor.ACCA = 0x1;
       processor.asr('a',0);
       if(processor.CC != 0x7 | processor.ACCA != 0)
           System.out.println("error ACCA ASR");

       /* TEST MEMORY **************************************************/

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.asr('m',processor.direct());
       if(processor.CC != 7 & processor.mem[0x1] != 0)
           System.out.println("err direct ASR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.asr('m',processor.immediate());
       if(processor.CC != 7 & processor.mem[0] != 0)
           System.out.println("err immediate ASR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = (byte)0x80;
       processor.asr('m',processor.extended());
       if(processor.CC != 0xA | (processor.mem[0x101] & 0x0FF) != 0xC0)
           System.out.println("err extended ASR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = (byte)0x80;
       processor.asr('m',processor.indexed());
       if(processor.CC != 0xA | (processor.mem[0x11] & 0x0FF) != 0xC0)
           System.out.println("err indexed ASR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;




       //ASL
       processor.CC = 0x0;
       processor.ACCA = (byte)0x1;
       processor.asl('a',0);
       if(processor.CC != 0x0 | processor.ACCA != 0x2)
           System.out.println("error ACCA ASL");

       processor.CC = 0x0;
       processor.ACCB = (byte)0x81;
       processor.asl('b',0);
       if(processor.CC != 0x3 | processor.ACCB != 0x2)
           System.out.println("error ACCB ASL");
       
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x40;
       processor.asl('m',processor.direct());
       if(processor.CC != 0xA & processor.mem[0x1] != 0x2)
           System.out.println("error direct ASL");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x40;
       processor.asl('m',processor.immediate());
       if(processor.CC != 0xA & processor.mem[0] != 0x2)
           System.out.println("error immediate ASL");

       processor.PC = 0;
       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = (byte)0x40;
       processor.asl('m',processor.indexed());
       if(processor.CC != 0xA & processor.mem[0x11] != 0x2)
           System.out.println("error indexed ASL");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //ROL

       //DEC
       processor.ACCA = 1;
       processor.CC = 0;
       processor.dec('a', 0);
       if(processor.CC != 0x4)
           System.out.println("error ACCA DEC");

       processor.ACCA = 0;
       processor.CC = 0;
       processor.dec('a', 0);
       if(processor.CC != 0x8)
           System.out.println("error ACCA DEC"); //check N

       processor.ACCB = (byte)0x80;
       processor.CC = 0;
       processor.dec('b',0);
       if(processor.CC != 0x2)
           System.out.println("error ACCB DEC");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = (byte)0x80;
       processor.dec('m',processor.direct());
       if(processor.CC != 0x2 | processor.mem[1] != 127)
           System.out.println("error direct DEC");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = (byte)0x80;
       processor.dec('m',processor.immediate());
       if(processor.CC != 0x2 | processor.mem[0] != 127)
           System.out.println("err immediate DEC");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = (byte)0x80;
       processor.dec('m',processor.extended());
       if(processor.CC != 0x2 | (byte)processor.mem[0x101] != 127)
           System.out.println("err extended DEC");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = (byte)0x80;
       processor.dec('m',processor.indexed());
       if(processor.CC != 0x2 | processor.mem[0x11] != 127)
           System.out.println("err indexed DEC");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //INC
       processor.ACCA = -1;
       processor.CC = 0x2;
       processor.inc('a', 0);
       if(processor.CC != 0x4 | processor.ACCA != 0)
           System.out.println("error ACCA INC");

       processor.ACCA = 0x7F;
       processor.CC = 0;
       processor.inc('a', 0);
       if(processor.CC != 0x2)
           System.out.println("error ACCA INC");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = (byte)0x4;
       processor.inc('m',processor.direct());
       if(processor.CC != 0x0 | processor.mem[1] != 0x5)
           System.out.println("error direct INC");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = (byte)0x4;
       processor.inc('m',processor.immediate());
       if(processor.CC != 0x0 | processor.mem[0] != 0x5)
           System.out.println("error immediate INC");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = -1; //test Z
       processor.inc('m',processor.extended());
       if(processor.CC != 0x4 | (byte)processor.mem[0x101] != 0x0)
           System.out.println("error extended INC");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = -1;
       processor.inc('m',processor.indexed());
       if(processor.CC != 0x4 | processor.mem[0x11] !=0x0)
           System.out.println("error indexed INC");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       //TST
       processor.CC = 0;
       processor.ACCA = 0;
       processor.tst('a',0);
       if(processor.CC != 0x4 | processor.ACCA != 0)
           System.out.println("error ACCA TST");

       processor.CC = 0;
       processor.ACCB = (byte)0x80;
       processor.tst('b',0);
       if(processor.CC != 0x8)
           System.out.println("error ACCB TST");

       processor.CC = 0x3;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = (byte)0x4;
       processor.tst('m',processor.direct());
       if(processor.CC != 0x0 | processor.mem[1] != 0x4)
           System.out.println("error direct TST");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = (byte)0x4;
       processor.tst('m',processor.immediate());
       if(processor.CC != 0x0 | processor.mem[0] != 0x4)
           System.out.println("error immediate TST");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = -1; //test Z
       processor.tst('m',processor.extended());
       if(processor.CC != 0x8 | (byte)processor.mem[0x101] != -1)
           System.out.println("error extended TST");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = -1;
       processor.tst('m',processor.indexed());
       if(processor.CC != 0x8 | processor.mem[0x11] != -1)
           System.out.println("error indexed TST");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       //CLR
       processor.CC = 0xB;
       processor.ACCA = 0x44;
       processor.clr('a',0);
       if(processor.CC != 0x4 | processor.ACCA != 0)
           System.out.println("error ACCA CLR");

       processor.CC = 0x0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = (byte)0x4;
       processor.clr('m',processor.direct());
       if(processor.CC != 0x4 | processor.mem[1] != 0x0)
           System.out.println("error direct CLR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = (byte)0x4;
       processor.clr('m',processor.immediate());
       if(processor.CC != 0x4 | processor.mem[0] != 0x0)
           System.out.println("error immediate CLR");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = -1; //test Z
       processor.clr('m',processor.extended());
       if(processor.CC != 0x4 | (byte)processor.mem[0x101] != 0x0)
           System.out.println("error extended CLR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = (byte)0x4;
       processor.clr('m',processor.indexed());
       if(processor.CC != 0x4 | processor.mem[0x11] != 0x0)
           System.out.println("error indexed CLR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //NEG
       processor.CC = 0x0;
       processor.ACCA = 1;
       processor.neg('a',0);
       if(processor.CC != 0x9 | processor.ACCA != -1)
           System.out.println("error ACCA NEG");

       processor.CC = 0x0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = (byte)0x4;
       processor.neg('m',processor.direct());
       if(processor.CC != 0x9 | processor.mem[1] != -4)
           System.out.println("error direct NEG");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = (byte)0x80;
       processor.neg('m',processor.immediate());
       if(processor.CC != 0xB)
           System.out.println("error immediate NEG");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = 0;
       processor.neg('m',processor.extended());
       if(processor.CC != 0x4 | (byte)processor.mem[0x101] != 0x0)
           System.out.println("error extended NEG");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = -2;
       processor.neg('m',processor.indexed());
       if(processor.CC != 0x1 | processor.mem[0x11] != 2)
           System.out.println("error indexed NEG");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //CMP
       processor.CC = 0x0;
       processor.ACCA = 1;
       processor.mem[0] = 1;
       processor.cmp('a',processor.immediate());
       if(processor.CC != 0x4)
           System.out.println("error ACCA Immediate CMP");

       processor.CC = 0x0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x4;
       processor.ACCA = 0x1;
       processor.cmp('a',processor.direct());
       if(processor.CC != 0x9)
           System.out.println("error ACCA direct CMP");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = 0;
       processor.ACCB = 0;
       processor.cmp('b',processor.extended());
       if(processor.CC != 0x4)
           System.out.println("error ACCB extended CMP");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = -2;
       processor.ACCB = -2;
       processor.cmp('b',processor.indexed());
       if(processor.CC != 0x4)
           System.out.println("error ACCB indexed CMP");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       //SBC
       processor.CC = 0x0;
       processor.ACCA = 1;
       processor.mem[0] = 2;
       processor.sbc('a',processor.immediate());
       if(processor.CC != 0x9 | processor.ACCA != -1)
           System.out.println("error ACCA Immediate SBC");


       processor.CC = 0x1;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 2;
       processor.ACCA = 1;
       processor.sbc('a',processor.direct());
       if(processor.CC != 0x9 | processor.ACCA != -2)
           System.out.println("error ACCA direct SBC");

       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = 2;
       processor.ACCB = 4;
       processor.sbc('b',processor.extended());
       if(processor.CC != 0)
           System.out.println("error ACCB extended SBC");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = -2;
       processor.ACCB = -2;
       processor.sbc('b',processor.indexed());
       if(processor.CC != 0x4)
           System.out.println("error ACCB indexed SBC");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       //AND
       processor.CC = 0x0;
       processor.ACCA = 1;
       processor.mem[0] = 2;
       processor.and('a',processor.immediate());
       if(processor.CC != 0x4 | processor.ACCA != 0)
           System.out.println("error ACCA Immediate AND");

       processor.CC = 0x0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 2;
       processor.ACCA = 2;
       processor.and('a',processor.direct());
       if(processor.CC != 0x0 | processor.ACCA != 2)
           System.out.println("error ACCA direct AND");

       processor.CC = 0x2; //check for clearing V
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = (byte)0xFF;
       processor.ACCB = 4;
       processor.and('b',processor.extended());
       if(processor.CC != 0 | processor.ACCB != 0x4)
           System.out.println("error ACCB extended AND");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       processor.IX = 0x10;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = -2;
       processor.ACCB = -2;
       processor.and('b',processor.indexed());
       if(processor.CC != 0x8 | processor.ACCB != -2)
           System.out.println("error ACCB indexed AND");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;


       //BIT
       processor.CC = 0x0;
       processor.ACCA = 1;
       processor.mem[0] = 2;
       processor.and('a',processor.immediate());
       if(processor.CC != 0x4)
           System.out.println("error ACCA Immediate BIT");

       processor.CC = 0x0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 2;
       processor.ACCA = 2;
       processor.and('a',processor.direct());
       if(processor.CC != 0x0)
           System.out.println("error ACCA direct BIT");

       processor.CC = 0x2; //check for clearing V
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = (byte)0xFF;
       processor.ACCB = 4;
       processor.and('b',processor.extended());
       if(processor.CC != 0)
           System.out.println("error ACCB extended BIT");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.CC = 0x1; //make sure C not affected
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = -2;
       processor.ACCB = -2;
       processor.and('b',processor.indexed());
       if(processor.CC != 0x9)
           System.out.println("error ACCB indexed BIT");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //LDA
       processor.CC = 0x2; //check if V cleared
       processor.ACCA = 1;
       processor.mem[0] = 2;
       processor.lda('a',processor.immediate());
       if(processor.ACCA != 2)
           System.out.println("error ACCA Immediate LDA");

       processor.CC = 0x0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = -2;
       processor.lda('a',processor.direct());
       if(processor.CC != 0x8 | processor.ACCA != -2)
           System.out.println("error ACCA direct LDA");

       processor.CC = 0x1; //check C not affected
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = 0;
       processor.lda('b',processor.extended());
       if(processor.CC != 0x5 | processor.ACCB != 0)
           System.out.println("error ACCB extended LDA");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.CC = 0x0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = (byte)0xBE;
       processor.lda('b',processor.indexed());
       if(processor.CC != 0x8 | (processor.ACCB & 0x0FF) != 0xBE)
           System.out.println("error ACCB indexed LDA");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       //EOR
       processor.CC = 0x2; //check if V cleared
       processor.ACCA = 1;
       processor.mem[0] = 2;
       processor.eor('a',processor.immediate());
       if(processor.CC != 0 | processor.ACCA != 3)
           System.out.println("error ACCA Immediate EOR");

       processor.CC = 0x0;
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = -2;
       processor.ACCA = -2;
       processor.eor('a',processor.direct());
       if(processor.CC != 0x4 | processor.ACCA != 0)
           System.out.println("error ACCA direct EOR");

       processor.CC = 0x1; //check C not affected
       processor.PC = 0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x1;
       processor.mem[0x101] = (byte)0xFE;
       processor.ACCB = (byte)0xFE;
       processor.eor('b',processor.extended());
       if(processor.CC != 0x5 | processor.ACCB != 0)
           System.out.println("error ACCB extended EOR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x101] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;

       processor.IX = 0x10;
       processor.CC = 0x0;
       processor.mem[0] = 0x1;
       processor.mem[1] = 0x0;
       processor.mem[0x11] = (byte)0x8E;
       processor.ACCB = 0x01;
       processor.eor('b',processor.indexed());
       if(processor.CC != 0x8 | (processor.ACCB & 0x0FF) != 0x8F)
           System.out.println("error ACCB indexed EOR");
       processor.CC = 0;
       processor.PC = 0;
       processor.mem[0x11] = 0;
       processor.mem[0x0] = 0;
       processor.mem[0x1] = 0;



       


       

       System.out.println("------------------------");
       System.out.println("   TEST SUCCESSFUL");
       System.out.println("------------------------");
    }//main
}//test
