# [Homework 6](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw6.docx): MarksEditor

- SQLite database access with modern [AndroidX Room](https://developer.android.com/training/data-storage/room/accessing-data)
  library.
- Context-awareness with view model and list adapter observing data changes.
- Cards in grid layout with different configurations for portrait and landscape
  orientations.

## Screenshots

### Main screen

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw6/screenshot1.png"><br><small>Screenshot 1 &mdash; Filling out form</small>

The main screen displays a form to input student's ID, name and marks. These
values are displayed in the front-end while being stored in the local database
for further consumption. Using Room that conforms to the POJO and DAO pattern,
the data is persisted and retrieved in a structured manner.

### Input validation

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw6/screenshot2.png"><br><small>Screenshot 2 &mdash; Incomplete form</small>

Text in the input fields are being watched for changes. In this instance, the
floating button is hidden until all fields are filled and do not violate each
field's regex pattern constraint. The text fields are cleared upon successful
submission.

### Interaction

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw6/screenshot3_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw6/screenshot3_2.png"><br><small>Screenshot 3.1 & 3.2 &mdash; Press and long press</small>

A quick tap on the card will display an information popup of current student. On
the other hand, a long press will prompt a confirmation dialog to delete the
student's record. Changes made to the layout are reflected in the real-time
database.

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw6/screenshot3_3.png"><br><small>Screenshot 3.3 &mdash; Reset table</small>

User may reset the table by selecting the **Reset** menu item. This action will
clear all records after a confirmation button in an indefinite snackbar is
pressed. When there are no records, a placeholder text is displayed to inform
the user that the list is empty.
