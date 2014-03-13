/*
 * Ketler Simulator
 * Motorola 6800 Device Interface
 * 
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

public interface Device6800 {
    void AddMemoryLocation(String s);
    void AddInterrupt (String s);
}
