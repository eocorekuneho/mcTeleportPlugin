# TeleportPlugin
This is a really simple and basic teleportation system for Paper Minecraft servers.

## Features
* Create, edit, list and delete Home points
* Create, edit, list and delete Waypoints
* Teleport to Home point or Waypoint.
* Works between worlds.
* Has a cost in XP, based on distance

Home and Waypoints are basically the same, just separated, for convenience.

## Commands
| Command  | Arguments  | Description  |
| ------------ | ------------ | ------------ |
| /gohome  | *homeName*  | Teleports the player to *homeName* Home point. If *homeName* is empty, the firstly recorded Home point will be selected.|
| /gowp  | *waypointName* | Teleports the player to *waypointName* Waypoint. If *waypointName* is empty, the last used Waypoint will be used. |
| /sethome  | *homeName*  | Creates a Home point for the player with the name *homeName*. If *homeName* is empty, *"default"* will be used. If a Home point with the same name exists, it will be updated. |
| /setwp  | waypointName | Creates a Waypoint for the player with the name *waypointName*. *waypointName* is mandatory. If a Waypoint with the same name exists, it will be updated.|
| /delhome  | homeName  | Deletes the player's Home point with the name *homeName*. *homeName* is mandatory  |
| /delwp  | waypointName  | Deletes the player's Waypoint with the name *waypointName*. *waypointName* is mandatory  |
| /listhome  | -  | Lists the player's Home points  |
| /listwp  | -  | Lists the player's Waypoints |

Home point and Waypoint data is saved to TeleportPlugin/data.yml
