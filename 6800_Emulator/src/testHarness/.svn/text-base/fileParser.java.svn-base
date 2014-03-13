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
 * fileParser class
 * Class used to parse the s19 files used for the emulator
 * takes a passed File object and parses it, loading it into memory
 * perhaps testing the checksums and ensuring a correct file
 * even though it is out of the scope of the project, meh
 *
 * @author David Ketler, 3394947
 */
public class fileParser {
    
    public void parse(File f, CPU processor) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            FileReader in = new FileReader(f);
            int c;
            int numBytes;//number of bytes in string
            int address;
            int eof;
            //we're just going to manually parse the lines for now
            //make this easy minus tedious coding



            while(true) {
                c = in.read(); //S
                c = in.read(); //address size, ignoredish for now

                if(c == -1)
                    break;

                numBytes = parseHex(Character.toString((char)in.read()) + Character.toString((char)in.read()),2);
                
                String add = Character.toString((char)in.read()) + Character.toString((char)in.read())
                        + Character.toString((char)in.read()) + Character.toString((char)in.read());

                address = parseHex(add,4);

                for(int i = 0; i < (numBytes - 3); i++) {
                    processor.mem[address] = (byte)(parseHex(Character.toString((char)in.read()) + Character.toString((char)in.read()), 2) & 0x00FF);
                    address++;
                }

                //read checksum
                c = in.read();
                c = in.read();
                c = in.read(); //carriage return mutter mutter
                c = in.read(); //line feed mutter mutter
                processor.updated = true;
            }         
        }
        catch(Exception e) {}
    }//parse

    public void print(String e) { System.out.println(e); }

    public int parseHex(String s, int length) {
        int value = 0;

        if(s.contains("0x")) {
            s = s.replace("0x", "");
            length = length -2;
        }


        for(int i = 1; i <= length; i++) {
            if(s.charAt(i-1) == 'A' || s.charAt(i-1) == 'a')
                value = (value | (0xA << (length-i)*4));
            else if(s.charAt(i-1) == 'B' || s.charAt(i-1) == 'b')
                value = (value | 0xB << (length-i)*4);
            else if(s.charAt(i-1) == 'C' || s.charAt(i-1) == 'c')
                value = (value | 0xC << (length-i)*4);
            else if(s.charAt(i-1) == 'D' || s.charAt(i-1) == 'd')
                value = (value | 0xD << (length-i)*4);
            else if(s.charAt(i-1) == 'E' || s.charAt(i-1) == 'e')
                value = (value | 0xE << (length-i)*4);
            else if(s.charAt(i-1) == 'F' || s.charAt(i-1) == 'f')
                value = (value | 0xF << (length-i)*4);
            else
                value = (value | Integer.parseInt(Character.toString(s.charAt(i-1))) << (length-i)*4);
        }
        return value & 0x0FFFF;
    }//parseHex
}//fileParser
