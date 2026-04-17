# BLOXDSTONE
Bloxdstone, is a redstone repository for Bloxd.io (unoffical)

# -GUIDE-

A modular, redstone-inspired logic system built using custom blocks.
This document explains power, wiring, direction helpers, machines, and log codes used for debugging.


# PART 1: POWER & WIRING

Power Sources:
- Red Concrete (always ON)
- Red Torch (always ON)
Place next to a wire or machine to energize it.

Wires:
- Red Concrete Slab
Carries power in all directions (up, down, left, right).

Switch:
- Crate (Lever)
Right-click to turn ON (Log 19).
Right-click again to turn OFF (Log 20).


# PART 2: HELPER BLOCK SYSTEM (DIRECTION CONTROL)

Machines require a Helper Block to set their facing direction.
Rule:
1. Place the machine.
2. Place the Helper Block on the face where you want the machine to output or act.
3. The Helper Block disappears and the direction is set.

Direction Setup Table:
- Piston: Machine = Diorite, Helper = Maple Wood Planks
- Observer: Machine = Iron Watermelon, Helper = Stone
- Repeater: Machine = Green Planks, Helper = Yellow Planks
- Comparator: Machine = Yellow Ceramic, Helper = Yellow Planks


# PART 3: MACHINES & BEHAVIOR

Piston (Diorite):
- Activates when powered.
- Pushes the block in front.
- Cannot push Bedrock or Obsidian.
- If pushing Moonstone, becomes a Sticky Piston and pulls the block back when power turns off.
- Error 14: Blocked or no room to push.

Observer (Iron Watermelon):
- Watches the block in front.
- If the block becomes not Air, it triggers (Log 18).
- Sends power to surrounding blocks.

Repeater (Green Planks):
- Takes power from behind and outputs forward.
- Delay cycles by right-clicking: 1s, 2s, 3s, 4s.
- Logs 21–24 indicate delay level.

Comparator (Yellow Ceramic):
- Requires a Chest behind it.
- Chest has items: ON (Log 25).
- Chest empty: OFF (Log 26).

Redstone Lamp (Dim Lamp Off):
- Turns into Dim Lamp On when powered.


# PART 4: CONSOLE LOG CODES

10 — Power Source placed
11 — Piston placed
12 — Piston direction set
15 — Observer placed
16 — Observer direction set
18 — Observer detected a block
19 — Lever ON
20 — Lever OFF
21–24 — Repeater delay set (1–4 seconds)
25 — Comparator: Chest has items
26 — Comparator: Chest empty
Error 14 — Piston blocked or no room


# SUMMARY TABLE

Machine      Block               Helper               Purpose
-----------------------------------------------------------------------
Wire         Red Concrete Slab   None                 Carries power
Switch       Crate               None                 Toggle power
Piston       Diorite             Maple Planks         Pushes blocks
Observer     Iron Watermelon     Stone                Detects block changes
Repeater     Green Planks        Yellow Planks        Delays power
Comparator   Yellow Ceramic      Yellow Planks        Detects chest items
Lamp         Dim Lamp Off        None                 Lights when powered

tbh, it will be very nice if anyone out there could help me out to fix bugs and make updates
