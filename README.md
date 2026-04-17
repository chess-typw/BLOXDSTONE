# BLOXDSTONE
Bloxdstone, is a redstone repository for Bloxd.io (unoffical)


USAGE:



⚡ Part 1: Power & Wiring
Power Sources
Block	Behavior
Red Concrete	Always ON
Red Torch	Always ON


Place next to a wire or machine to energize it.

Wires
Block	Behavior
Red Concrete Slab	Carries power in all directions (up, down, left, right)


Switch
Block	Behavior
Crate (Lever)	Right‑click to toggle ON (Log 19) / OFF (Log 20)


🧭 Part 2: Helper Block System (Direction Control)
Machines require a “Helper Block” to set their facing direction.

Rule:

Place the machine

Place the Helper Block on the face where you want the machine to output/act

Helper Block disappears, direction is set

Direction Setup Table
Machine	Machine Block	Helper Block	Result
Piston	Diorite	Maple Wood Planks	Sets push direction
Observer	Iron Watermelon	Stone	Sets observation direction
Repeater	Green Planks	Yellow Planks	Sets output direction
Comparator	Yellow Ceramic	Yellow Planks	Sets output direction


⚙️ Part 3: Machines & Behavior
Piston (Diorite)
Activates when powered

Pushes block in front

Cannot push Bedrock or Obsidian

If pushing Moonstone, becomes a Sticky Piston (pulls block back when power turns off)

Error 14: Blocked or no room to push

Observer (Iron Watermelon)
Watches the block in front

If the block becomes not Air, it triggers (Log 18)

Sends power to surrounding blocks

Repeater (Green Planks)
Input from behind → output forward

Delay cycles by right‑clicking: 1s → 2s → 3s → 4s

Logs 21–24 indicate delay level

Comparator (Yellow Ceramic)
Requires a Chest behind it

Chest has items → ON (Log 25)

Chest empty → OFF (Log 26)

Redstone Lamp
Block	Behavior
Dim Lamp Off	Turns into Dim Lamp On when powered


🖥️ Part 4: Console Log Codes
Log Code	Meaning
10	Power Source placed
11	Piston placed
12	Piston direction set
15	Observer placed
16	Observer direction set
18	Observer detected a block
19	Lever ON
20	Lever OFF
21–24	Repeater delay set (1–4 seconds)
25	Comparator: Chest has items
26	Comparator: Chest empty
Error 14	Piston blocked or no room


📘 Summary Table
Machine	Block	Helper	Purpose
Wire	Red Concrete Slab	None	Carries power
Switch	Crate	None	Toggle power
Piston	Diorite	Maple Planks	Pushes blocks
Observer	Iron Watermelon	Stone	Detects block changes
Repeater	Green Planks	Yellow Planks	Delays power
Comparator	Yellow Ceramic	Yellow Planks	Detects chest items
Lamp	Dim Lamp Off	None	Lights when powered
