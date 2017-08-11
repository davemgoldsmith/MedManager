

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
of medications.  An Alarm service utilizes the Android notification service to enact
as the notification/alarm when a medication is due to be taken.

Known Bugs: the code for the alarm is deprecated, and only apears to work appropriately on
some older APIs.  

Further Development: Improvement in UI to ensure text:background contrast is in line 
with government regulations.  Also, expanding the alarm to be accessible to arm/disarm/snooze
directly from the primary page in addition to the phone/device notification.  Fially, improving
the UI with the time picker function.

Further Improvement: potentially add user selectable 'themes' that may be more appropriate for 
handedness, and look at different color schemes that may be more comfortable for viewers.




