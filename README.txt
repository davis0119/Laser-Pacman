=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: davisdt
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the three core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections

Well, I started out with 2D arrays, but I changed most of my storage to collections.
I found it easier to use ArrayLists to store walls and ghosts as I can easily 
make use of the dynamic size of Collections and just iterate through it to 
detect collisions. I would be able to remove them with ease if they are shot
with lasers.

  2. JUnit testing

I test to make sure my game project is fully encapsulated. I also tested
to make sure that my game over state was being properly caught. I was able
to check if each ghost in the ghost collection was a different type of ghost as well.
A lot of function testing and making sure GameCourt was flying as planned.

  3. Inheritance/Subtyping
  
I had a main Ghost class with a couple of abstract methods. Each 
type of ghost extended this class and handled lasers and attacks differently. 
The red ghost hurts the play 3x as much as walls. The cyan ghost reflects lasers and
eats all of the laser juice that the use collected. The orange ghost
saps laserfuel and health. The pink ghost hurts the player to the 
brink of death but gives them a lot of fuel.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

The Player class constructs a yellow square (Squareman) 
that can be controlled with arrow keys. The player can get hurt by walls
constructed by the wall class. The goal is to collect all the laserfuel,
but it could be fun to shoot at walls and lasers.

The Ghost class is an abstract class that is a superclass for different types
of ghosts, which are enemies with all different types of abilities. 

Again, the red ghost hurts the play 3x as much as walls. The cyan ghost reflects lasers and
eats all of the laser juice that the use collected. The orange ghost
saps laserfuel and health. The pink ghost hurts the player to the 
brink of death but gives them a lot of fuel.

The LaserFuel class and Laser class both extend the GameObj class to detect
collisions. 

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

Yes, there was pretty good separation of functionaliyu. The private state
is completely encapsulated. If I would refactor, I might make the gameOver and
playing boolean one collective thing rather than complementing each other.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

  I just used some sprite images for the ghosts found on Google Images.
  Else I just used Javadocs to figure out rectangles.