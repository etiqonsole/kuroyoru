# Kuroyoru Communication Protocol (KCP-rev1)


## Message format

A command is made of one first byte indicating the type of the message, followed by optional data.


## Message types

### Commands (range `00`-`3f`)

Requests made by the clients to the server.

|Code|Name                |Data                          |Usage                                             |Response                      |
|----|--------------------|------------------------------|--------------------------------------------------|------------------------------|
|`00`|IDENT               |ClientId                      |Identify the client                               |IDENT-INFO                    |
|`01`|IDENT-CHECK         |                              |Ask if the client is identified                   |IDENT-INFO                    |
|`08`|MAP-ENABLE          |                              |Enable keyboard mapping for the session           |MAP-INFO                      |
|`09`|MAP-DISABLE         |                              |Disable keyboard mapping for the session          |MAP-INFO                      |
|`0a`|MAP-IS-ENABLED      |                              |Ask if the keyboard mapping is enabled            |MAP-INFO                      |
|`0f`|MAP-CUSTOM          |ControllerEvent + KeyCode     |Map a controller event to a key                   |MAP-OK / MAP-FAILURE          |
|`10`|MSGBOX-INFO         |String + String               |Displays a message box with a simple message      |MSGBOX-OK / MSGBOX-FAILURE    |
|`11`|MSGBOX-QUESTION     |String + String               |Display a message box with yes/no question        |MSGBOX-YES/NO/FAILURE         |
|`12`|MSGBOX-CONFIRM      |String + String               |Display a message box for ok/cancel confirmation  |MSGBOX-OK/CANCEL/FAILURE      |
|`13`|MSGBOX-WARNING      |String + String               |Display a warning message box                     |MSGBOX-OK / MSGBOX-FAILURE    |
|`13`|MSGBOX-ERROR        |String + String               |Display an error message box                      |MSGBOX-OK / MSGBOX-FAILURE    |
|`14`|MSGBOX-FATAL        |String + String               |Display an error after the client shuts down      |MSGBOX-OK / MSGBOX-FAILURE    |
|`18`|QUIT                |                              |Exit the application                              |ACTIVITY-OK / ACTIVITY-FAILURE|
|`19`|CRASH               |                              |Exit the application because of an error          |ACTIVITY-OK / ACTIVITY-FAILURE|
|`1a`|LAUNCH              |ClientId                      |Move to background and launch a child application |ACTIVITY-OK / ACTIVITY-FAILURE|
|`1b`|SWITCH              |ClientId                      |Replace this app with another                     |ACTIVITY-OK / ACTIVITY-FAILURE|
|`20`|VKBD-ENABLE         |                              |Enable the visual keyboard for the session        |VKBD-INFO                     |
|`21`|VKBD-DISABLE        |                              |Disable the visual keyboard for the session       |VKBD-INFO                     |
|`22`|VKBD-IS-ENABLED     |                              |Ask if the visual keyboard is enabled             |VKBD-INFO                     |
|`23`|BEGIN-INPUT         |                              |Start text input                                  |VKBD-INFO                     |
|`24`|BEGIN-ML-INPUT      |                              |Start text input in multi-line mode               |VKBD-INFO                     |
|`25`|BEGIN-NUM-INPUT     |                              |Start text input in numeric mode                  |VKBD-INFO                     |
|`27`|CANCEL-INPUT        |                              |Stop text input                                   |VKBD-INFO                     |
|`28`|WAKELOCK-ACQUIRE    |                              |Blocks the console from entering auto-standby mode|WAKELOCK-INFO                 |
|`2f`|WAKELOCK-RELEASE    |                              |Allow the console to enter auto-standby mode      |WAKELOCK-INFO                 |


### Responses (range `40`-`7f`)

Responses to the commands of the clients.

|Code|Name                |Data                          |Usage                                             |Response to                   |
|----|--------------------|------------------------------|--------------------------------------------------|------------------------------|
|`40`|IDENT-INFO          |Boolean                       |Tell if the client is identified or not           |IDENT / IDENT-CHECK           |
|`46`|NOT-IDENT           |                              |The client isn't identified                       |*any command*                 |
|`47`|NOT-PERM            |                              |The client is missing permissions                 |*any command*                 |
|`48`|MAP-INFO            |Boolean                       |Tell if keyboard mapping is enabled               |MAP-ENABLE/DISABLE/IS-ENABLED |
|`49`|MAP-OK              |                              |The key has been mapped                           |MAP-CUSTOM                    |
|`4f`|MAP-FAILURE         |                              |The key couldn't be mapped                        |MAP-CUSTOM                    |
|`50`|MSGBOX-OK           |                              |The message box was closed by clicking on OK      |MSGBOX-INFO/WARNING/ERROR/FATAL|
|`51`|MSGBOX-CANCEL       |                              |The message box was closed by clicking on cancel  |MSGBOX-CONFIRM                |
|`52`|MSGBOX-YES          |                              |The message box was closed by clicking on yes     |MSGBOX-QUESTION               |
|`53`|MSGBOX-NO           |                              |The message box was closed by clicking on no      |MSGBOX-QUESTION               |
|`57`|MSGBOX-FAILURE      |                              |The message box couldn't be shown                 |*any MSGBOX*                  |
|`58`|ACTIVITY-OK         |                              |The activity change has been taken into account   |QUIT/CRASH/LAUNCH/SWITCH      |
|`5f`|ACTIVITY-FAILURE    |                              |The activity change couldn't be handled           |QUIT/CRASH/LAUNCH/SWITCH      |
|`60`|VKBD-INFO           |Boolean                       |Tell if the visual keyboard is enabled            |*any VKBD/INPUT*              |
|`68`|WAKELOCK-INFO       |Boolean                       |Tell if the console can enter standby mode        |WAKELOCK-ACQUIRE/RELEASE      |


### Notifications (range `c0`-`ff`)

Informations sent by the server to a client.

|Code|Name                |Data                          |Usage                                             |Response                      |
|----|--------------------|------------------------------|--------------------------------------------------|------------------------------|
|`d8`|WILL-QUIT           |                              |The application is about to be closed             |ACK / BUSY                    |
|`e8`|WILL-SLEEP          |                              |The console is about to enter standby mode        |ACK / BUSY                    |
|`e8`|WOKE-UP             |                              |The console has left standby mode                 |ACK / BUSY                    |


### Acknowledgements (range `80`-`bf`)

Responses to notifications.

|Code|Name                |Data                          |Usage                                             |Response to                   |
|----|--------------------|------------------------------|--------------------------------------------------|------------------------------|
|`b0`|ACK                 |                              |Acknowledgement                                   |*any notification*            |
|`b7`|BUSY                |                              |Sent a command and waiting for response           |*any notification*            |

## Data

### Boolean

A boolean take 1 byte : `00` means false and any other value means true, although it is usually `ff`.

### String

A string has a variable length (with a maximum of 65535 bytes, ).
The two first bytes indicate the length of the string (hence the limit) followed by the string encoded in utf-8.

### ClientId

A ClientId is 5 bytes long : 1 byte for the type, 4 bytes for additional data.
- connection for debugging : `ff xxxxxxxx` where *xxxxxxxx* is the debug key as a 32-bit integer.
- connection as a generic application with PID : `00 xxxxxxxx` where *xxxxxxxx* is the PID of the application as a 32-bit integer.
- connection as IUTLauncher (reserved) : `aa xxxxxxxx` where *xxxxxxxx* is the authentication key.
- connection as a game launched by IUTLauncher : `ab xxxxxxxx` where *xxxxxxxx* is the id of the game. 

### ControllerEvents & KeyCode

Input event code, will be specified in a later revision. For now, MAP-CUSTOM should be unimplemented.