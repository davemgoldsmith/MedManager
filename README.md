

# Medication Manager Application.


Intent of application was to create an easy to use medication reminder 
that employs a database and alarm system to store medication 
for a user and notifies her/him when it is time to take a medication.
Further, the original intent for this application was to be easy to use
particularly for the visually impaired.  For this reason, drawers, drop-down
menus and any other small items requiring high visual acuity were ommited 
from the initial design.

The application utilizes a single entity "Schedule" as the database.
A simple layout with a primary screen (activity) is used as the entry point,
with only the need for one other screen (activity) to use for addition or adjustments
of medications.  An Alarm service utilizes the Android notification service to act
as the notification/alarm when a medication is due to be taken.

Known Bugs: 
The code for the alarm is deprecated, and only appears to work appropriately on
some older APIs.  
In current format, the alarm(s) would not reset upon device reboot.  
The application is defaulted to to repeat every 24 hours to assume daily consumption of medication.
At this time, daylight savings time changes would impact the time that the alarm sounds. 
The alarm system may still have some unknown bugs as testing has yielded varying results.
The alarm did work properly with last two testing sessions.

Further Development: 
Improvement in UI to ensure text:background contrast is in line with government regulations.  
Expansion of the alarm function to be accessible to arm/disarm/snooze directly from the primary 
page in addition to the phone/device notification.  Finally, improvement of the UI with a more 
user-friendly time selection mechanism.

Further Improvement: 
Add user selectable 'themes' that may be more appropriate for 
handedness, and look at different color schemes that may be more comfortable for viewers.




