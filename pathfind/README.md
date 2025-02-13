# Pathing
*************************************Multi-Level Mapper****************************************

@author Joshua Ni
@author Justin Ely

The Multi-Level Mapper is an implementation of a pathfinding algorithm to find a time-efficient path between two locations in multi-level buildings, such as a shopping mall. The user selects two locations on 2D maps representing each floor of the building, and the program will find a path between them. The goal is to create an application that improves efficiency while navigating complicated, multi-layer indoor spaces.

*********************Tutorial*********************

1. Run the “Startup.java” file. After the application opens, select a folder to read a Map from (recommended: New Map Images).

2. After the images load, left click on a selectable pixel to select it as a starting position. The text at the top left should update if it is done successfully and a green circle should appear where you have clicked.

3. Then right click a selectable pixel to select it as the end position. The text at the top left should once again update if it is done successfully and a red circle should appear where you have clicked. 

4. After a start position and end position are set, the Route button should be enabled. 

5. Pressing this button will display the route between the start and end locations (Note: If the end or start location is inside an area with an entrance, it will navigate from or to the entrance(s))

