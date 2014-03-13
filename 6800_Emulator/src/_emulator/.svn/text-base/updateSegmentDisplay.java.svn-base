/*
 * Ketler Simulator
 * Motorola 6800 Simulator
 *
 * updateSegmentDisplay.java
 *
 * method to update an 8-segment display
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
import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author dak
 */
public class updateSegmentDisplay {
    public boolean updated;
    public Map<Integer,ImageIcon> icons;
    //public HashMap icons;

    ImageIcon decimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentDecimal.png"));
    ImageIcon zero = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentZero.png"));
    ImageIcon zeroDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentZeroDecimal.png"));
    ImageIcon one = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentOne.png"));
    ImageIcon oneDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentOneDecimal.png"));
    ImageIcon two = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTwo.png"));
    ImageIcon twoDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTwoDecimal.png"));
    ImageIcon three = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentThree.png"));
    ImageIcon threeDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentThreeDecimal.png"));
    ImageIcon four = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentFour.png"));
    ImageIcon fourDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentFourDecimal.png"));
    ImageIcon five = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentFive.png"));
    ImageIcon fiveDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentFiveDecimal.png"));
    ImageIcon six = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentSix.png"));
    ImageIcon sixDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentSixDecimal.png"));
    ImageIcon seven = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentSeven.png"));
    ImageIcon sevenDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentSevenDecimal.png"));
    ImageIcon eight = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentEight.png"));
    ImageIcon eightDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentEightDecimal.png"));
    ImageIcon nine = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentNine.png"));
    ImageIcon nineDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentNineDecimal.png"));
    ImageIcon a = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentA.png"));
    ImageIcon aDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentADecimal.png"));
    ImageIcon b = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentB.png"));
    ImageIcon bDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBDecimal.png"));
    ImageIcon c = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentC.png"));
    ImageIcon cDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentCDecimal.png"));
    ImageIcon d_ = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentD.png"));
    ImageIcon dDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentDDecimal.png"));
    ImageIcon e = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentE.png"));
    ImageIcon eDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentEDecimal.png"));
    ImageIcon f = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentF.png"));
    ImageIcon off = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentOff.png"));
    ImageIcon fDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentFDecimal.png"));
    ImageIcon bottomDashes = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomDashes.png"));
    ImageIcon bottomRight = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomRight.png"));
    ImageIcon leftBottom = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentLeftBottom.png"));
    ImageIcon threeDashes = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentThreeDashes.png"));
    ImageIcon topLeft = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopLeft.png"));
    ImageIcon topRight = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopRight.png"));
    ImageIcon bottom = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottom.png"));
    ImageIcon bottomDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomDecimal.png"));
    ImageIcon twoRight = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTwoRight.png"));
    ImageIcon twoRightDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTwoRightDecimal.png"));
    ImageIcon topCLeft = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopCLeft.png"));
    ImageIcon topCLeftDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopCLeftDecimal.png"));
    ImageIcon bottomCRight = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomCRight.png"));
    ImageIcon bottomCRightDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomCRightDecimal.png"));
    ImageIcon topCRight = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopCRight.png"));
    ImageIcon topCRightDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopCRightDecimal.png"));
    ImageIcon bottomBars = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomBars.png"));
    ImageIcon bottomBarsDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomBarsDecimal.png"));
    ImageIcon bottomCLeft = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomCLeft.png"));
    ImageIcon bottomCLeftDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomCLeftDecimal.png"));
    ImageIcon bottomLeftL = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomLeftL.png"));
    ImageIcon bottomLeftLDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomLeftLDecimal.png"));
    ImageIcon bottomN = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomN.png"));
    ImageIcon bottomNDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentBottomNDecimal.png"));
    ImageIcon topBars = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopBars.png"));
    ImageIcon topBarsDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopBarsDecimal.png"));
    ImageIcon topLeftL = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopLeftL.png"));
    ImageIcon topLeftLDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopLeftLDecimal.png"));
    ImageIcon topN = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopN.png"));
    ImageIcon topNDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopNDecimal.png"));
    ImageIcon topRightL = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopRightL.png"));
    ImageIcon topRightLDecimal = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/segmentTopRightLDecimal.png"));
    //ImageIcon allLit = new javax.swing.ImageIcon(getClass().getResource("/_emulator/resources/Display/allLit.png"));

    
    public void updateSegmentDisplay() {
         //load hashmap
        icons = new HashMap();

        icons.put(0x80,decimal);
        icons.put(0x7E,zero);
        icons.put(0x30,one);
        icons.put(0x6D,two);
        icons.put(0x79,three);
        icons.put(0x33,four);
        icons.put(0x5B,five);
        icons.put(0x5F,six);
        icons.put(0x70,seven);
        icons.put(0x7F,eight);
        icons.put(0x7B,nine);
        icons.put(0x77,a);
        icons.put(0x1F,b);
        icons.put(0x4E,c);
        icons.put(0x3D,d_);
        icons.put(0x4F,e);
        icons.put(0x47,f);
        icons.put(0x0,off);

        icons.put(0xC,bottomDashes);
        icons.put(0x10,bottomRight);
        icons.put(0x4,leftBottom);
        icons.put(0x49,threeDashes);
        icons.put(0x2,topLeft);
        icons.put(0x20, topRight);
        icons.put(0x8,bottom);
        icons.put(0x88,bottomDecimal);
        icons.put(0x11,twoRight);
        icons.put(0x91,twoRightDecimal);
        icons.put(0x61,topCLeft);
        icons.put(0xE1,topCLeftDecimal);
        icons.put(0x0D,bottomCRight);
        icons.put(0x8D,bottomCRightDecimal);
        icons.put(0x43,topCRight);
        icons.put(0xC3,topCRightDecimal);
        icons.put(0x09,bottomBars);
        icons.put(0x89,bottomBarsDecimal);
        icons.put(0x19,bottomCLeft);
        icons.put(0x99,bottomCLeftDecimal);
        icons.put(0x05,bottomLeftL);
        icons.put(0x85,bottomLeftLDecimal);
        icons.put(0x15,bottomN);
        icons.put(0x95,bottomNDecimal);
        icons.put(0x41,topBars);
        icons.put(0xC1,topBarsDecimal);
        icons.put(0x03,topLeftL);
        icons.put(0x83,topLeftLDecimal);
        icons.put(0x62,topN);
        icons.put(0xE2,topNDecimal);
        icons.put(0x21,topRightL);
        icons.put(0xA1,topRightLDecimal);
        //icons.put((byte)0xFF,allLit);
    }

    public void update(button display) {
        updateSegmentDisplay();
        int mem = display.mem;
        CPU proc = display.proc;
        proc.updated = true;
        
        if(icons.get(Integer.valueOf(proc.mem[mem])) == null)
                display.setIcon(off);
            else {
                display.setIcon(icons.get(Integer.valueOf(proc.mem[mem])));
                display.validate();
            }

    }
}
