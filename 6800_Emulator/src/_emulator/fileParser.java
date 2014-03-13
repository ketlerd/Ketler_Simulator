/*
 * COSC 3P92 Project
 * Motorola 6800 Emulator
 *
 * David Ketler 3394947, Frankie Lau 3844222
 */
package _emulator;
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
            String line;
            int c;
            int numBytes;//number of bytes in string
            int address;
            int eof;
            //we're just going to manually parse the lines for now
            //make this easy minus tedious coding



            while(true) {
                line = br.readLine();
                if(line == null)
                    break;

                if(line.charAt(1) == '9')
                    break;          //don't read the last line
                else if(line.charAt(1) == '0')
                    continue;
                
                numBytes = parseHex(Character.toString((char)line.charAt(2)) + Character.toString((char)line.charAt(3)), 2);

                address  = parseHex(Character.toString((char)line.charAt(4)) +
                        Character.toString((char)line.charAt(5)) + Character.toString((char)line.charAt(6))
                        + Character.toString((char)line.charAt(7)),4);

                for(int i = 4; i <= numBytes; i++) {
                    processor.mem[address] = (byte)(parseHex(Character.toString((char)line.charAt((i*2))) + Character.toString((char)line.charAt((i*2)+1)), 2) & 0x00FF);
                    address++;
                }
                processor.updated = true;
            }         
        }
        catch(Exception e) {}
    }//parse

    public void print(String e) { System.out.println(e); }

    public int parseHex(String s, int length) {
        int value = 0;

        s.replace("0x", "");
        s.replace("x","");
        s.replace("0X", "");
        s.replace("X","");

        for(int i = 1; i <= length; i++) {
            if(s.charAt(i-1) == 'A')
                value = (value | (0xA << (length-i)*4));
            else if(s.charAt(i-1) == 'B')
                value = (value | 0xB << (length-i)*4);
            else if(s.charAt(i-1) == 'C')
                value = (value | 0xC << (length-i)*4);
            else if(s.charAt(i-1) == 'D')
                value = (value | 0xD << (length-i)*4);
            else if(s.charAt(i-1) == 'E')
                value = (value | 0xE << (length-i)*4);
            else if(s.charAt(i-1) == 'F')
                value = (value | 0xF << (length-i)*4);
            else
                value = (value | Integer.parseInt(Character.toString(s.charAt(i-1))) << (length-i)*4);
        }
        return value & 0x0FFFF;
    }//parseHex

    public void parseET(File f, CPU processor) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(f));
            FileReader in = new FileReader(f);
            String line;
            int c;
            int numBytes;//number of bytes in string
            int address = 0xFC00;
            int eof;

            while(true) {
                line = br.readLine();
                if(line == null)
                    break;

                for(int i = 0; i < line.length(); i = i + 2) {
                    processor.mem[address] = (byte)(parseHex(Character.toString((char)line.charAt(i)) + Character.toString((char)line.charAt(i+1)), 2) & 0x00FF);
                    address++;
                }
            }

        }
        catch(Exception e) {}
    }//parseET
}//fileParser
