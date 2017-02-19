package in.vishnu.ayyappa.ayyappapp.util;

public interface Config {

   String SERVER_URL="http://eloksolutions.in/ayyappa/";
   // String SERVER_URL="http://192.168.0.2:8080/melzol/";
   // CONSTANTS
   String YOUR_SERVER_URL =  "http://eloksolutions.in/notification/register.php";
   // YOUR_SERVER_URL : Server url where you have placed your server files
    // Google project id
     String GOOGLE_SENDER_ID = "85557991429";  // Place here your Google project id
    //s3 image bucket name
    String BUCKET_NAME = "in-melzol-images";
    /**
     * Tag used on log messages.
     */
     String TAG = "Melzol";
    String MEMBER_TOPIC="MEM";
     String DISPLAY_MESSAGE_ACTION =
            "info.activities.services.myarea.DISPLAY_MESSAGE";

     String EXTRA_MESSAGE = "message";
   String Member_ID="Member_ID";
   String Member_Name="Member_Name";
    String Pincode="Pincode";
      
   
}