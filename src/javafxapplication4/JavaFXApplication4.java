/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
sudo cp  SimpleAstronomyLib-0.1.0.jar  /opt/jdk1.8.0/jre/lib/ext
sudo service samba restart
scp -P 2221 JavaFXApplication4.jar  pi@ibnabbas.dyndns.org:/home/pi/prayertime

26/11/13 from windows XP: Uncomment Lines 227, 214 - 221, 891 - 892 to work on raspberry pi

*/

//TODO change vpn setting to dns instead of ip, and setup my home to ossama.org for example

package javafxapplication4;
import com.bradsbrain.simpleastronomy.MoonPhaseFinder;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.json.JsonObject;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.*;
import java.io.IOException;
import static java.lang.Math.abs;
import static java.lang.System.out;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraintsBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import me.shanked.nicatronTg.jPushover.Pushover;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.joda.time.chrono.JulianChronology;

/**
 *
 * @author ossama
 */
   
    public class JavaFXApplication4 extends Application {
    
    private final Boolean debug    = true;  //  <<========================== Debuger  
    private final Logger logger = Logger.getLogger(JavaFXApplication4.class.getName());
    private Date fullMoon= null; //  <<========================== might fix errors at startup
    private Date newMoon= null; //  <<========================== might fix errors at startup
    private Date date_now;
    private long diff;
            
    private Process p;
    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
    
    // Bankstown NSW Location
//    double latitude = -33.9172891;
//    double longitude = 151.035882;
//    double timezone = 10;

    
    
    double latitude;
    double longitude;
    double timezone;
    
    private ObservableList data;
    
    private Integer moonPhase;
    private Boolean isWaning;
    private Boolean sensorLow = false;
    private Boolean hdmiOn = false;
    private Boolean isStarting = true;
    private Boolean new_day = true;
    private Boolean fajr_athan_enable = true ;
    private Boolean duha_athan_enable = true ;
    private Boolean zuhr_athan_enable  = true ;
    private Boolean asr_athan_enable  = true ;
    private Boolean maghrib_athan_enable  = true ;
    private Boolean isha_athan_enable = true ;
    private Boolean fajr_jamma_time_change = false;
    private Boolean zuhr_jamma_time_change = false;
    private Boolean asr_jamma_time_change = false;
    private Boolean maghrib_jamma_time_change = false;
    private Boolean isha_jamma_time_change = false;
    private Boolean notification = false;
    private Boolean notification_Bis = false;
    private Boolean athan_Change_Label_visible = false;
    private Boolean update_prayer_labels  = false;
    private Boolean update_moon_image  = false;
    private Boolean getHadith = true;
    private Boolean getFacebook = true;
    private Boolean fajr_jamaat_update_enable = true;
    private Boolean zuhr_jamaat_update_enable = true;
    private Boolean asr_jamaat_update_enable = true;
    private Boolean maghrib_jamaat_update_enable = true;
    private Boolean isha_jamaat_update_enable = true;
    private boolean  notification_Sent;
    private boolean  facebook_Receive = false;
    private boolean  facebook_notification_enable = false;
    private boolean  pir_sensor;
    private boolean  pir_disactive_startup = true;
    private boolean arabic = true;
    private boolean english = false;
    private boolean moon_hadith_Label_visible = false;
    private boolean hadith_Label_visible = false;
    private boolean facebook_Label_visible = false;
    private boolean facebook_Text_Post = false;
    private boolean facebook_Picture_Post = false;
    private boolean facebook_Label_visible_set_once = false;
    private boolean prayer_In_Progress = false;
    private boolean startup = true;
    private boolean fajr_prayer_In_Progress_notification = false;
    private boolean zuhr_prayer_In_Progress_notification = false;
    private boolean asr_prayer_In_Progress_notification = false;
    private boolean maghrib_prayer_In_Progress_notification = false;
    private boolean isha_prayer_In_Progress_notification = false;
    private boolean count_down = false;
    private boolean count_down_disable  = false;
            
    private String hadith, translated_hadith, ar_full_moon_hadith, en_full_moon_hadith, ar_moon_notification, en_moon_notification, announcement, en_notification_Msg, ar_notification_Msg, device_name, device_location;
    private String ar_notification_Msg_Lines[], en_notification_Msg_Lines[], notification_Msg, facebook_moon_notification_Msg;    
    private String fajr_jamaat ,zuhr_jamaat ,asr_jamaat ,maghrib_jamaat ,isha_jamaat ;
    private String labeconv;
    private String friday_jamaat, future_zuhr_jamaat_time;
    private String future_fajr_jamaat ,future_zuhr_jamaat ,future_asr_jamaat ,future_maghrib_jamaat ,future_isha_jamaat ;
    private String en_message_String, ar_message_String; 
    private String facebook_post, facebook_post_visibility, facebook_hadith, facebook_Fan_Count, facebook_Post_Url,old_facebook_Post_Url;
    private String fb_Access_token, platform; 
    private String page_ID;
    String timeZone_ID ; // = timeZone_ID
    String SQL;
    private String hour_in_hour_Label, minute_in_minute_Label, date;
    private String formattedDateTime;
    
    ResultSet rs;
    
    private int id, maghrib_adj;
    private int AsrJuristic,calcMethod;
    private int max_ar_hadith_len, max_en_hadith_len;
    int olddayofweek_int;
    int clock_minute, old_clock_minute ;
    int fajr_diffsec, maghrib_diffsec;
    int fajr_diffsec_dec ,fajr_diffsec_sin, maghrib_diffsec_dec ,maghrib_diffsec_sin;
    private Date prayer_date,future_prayer_date;
    private Calendar fajr_cal, sunrise_cal, duha_cal, zuhr_cal, asr_cal, maghrib_cal, isha_cal,old_today;
    private Calendar fajr_jamaat_cal, duha_jamaat_cal, zuhr_jamaat_cal, asr_jamaat_cal, maghrib_jamaat_cal, isha_jamaat_cal;
    private Calendar fajr_jamaat_update_cal, duha_jamaat_update_cal, zuhr_jamaat_update_cal, asr_jamaat_update_cal, maghrib_jamaat_update_cal, isha_jamaat_update_cal;
    private Calendar future_fajr_jamaat_cal, future_zuhr_jamaat_cal, future_asr_jamaat_cal, future_maghrib_jamaat_cal, future_isha_jamaat_cal, maghrib_plus15_cal, zuhr_plus15_cal;
    private Calendar notification_Date_cal, hadith_notification_Date_cal;
    
    private Date fajr_begins_time,fajr_jamaat_time, sunrise_time, duha_time, zuhr_begins_time, zuhr_jamaat_time, asr_begins_time, asr_jamaat_time, maghrib_begins_time, maghrib_jamaat_time,isha_begins_time, isha_jamaat_time;
    private Date future_fajr_jamaat_time, future_asr_jamaat_time, future_maghrib_jamaat_time,future_isha_jamaat_time;
    private Date notification_Date, hadith_notification_Date;   
    private Date fullMoon_plus1;
    Date ishadate, maghribdate, asrdate, zuhrdate, fajrdate; 
    Date fajrjamaatdate, zuhrjamaatdate, asrjamaatdate, maghribjamaatdate, ishajamaatdate;
    
    DateTimeZone tzSAUDI_ARABIA;
    DateTime dtIslamic;
    DateTime DateTime_now;    
    Calendar Calendar_now;
    
    private Label fajr_hourLeft, fajr_hourRight, fajr_minLeft, fajr_minRight, fajr_jamma_hourLeft, fajr_jamma_hourRight, fajr_jamma_minLeft, fajr_jamma_minRight, footer_Label, like_Label;
    private Label sunrise_hourLeft, sunrise_hourRight, sunrise_minLeft, sunrise_minRight;
    private Label time_Separator1, time_Separator2, time_Separator3, time_Separator4, time_Separator5, time_Separator6,time_Separator8, time_jamma_Separator1, time_jamma_Separator2, time_jamma_Separator3, time_jamma_Separator4 ,time_jamma_Separator5; 
    private Label zuhr_hourLeft, zuhr_hourRight, zuhr_minLeft, zuhr_minRight, zuhr_jamma_hourLeft, zuhr_jamma_hourRight, zuhr_jamma_minLeft, zuhr_jamma_minRight;
    private Label asr_hourLeft, asr_hourRight, asr_minLeft, asr_minRight, asr_jamma_hourLeft, asr_jamma_hourRight, asr_jamma_minLeft, asr_jamma_minRight;
    private Label maghrib_hourLeft, maghrib_hourRight, maghrib_minLeft, maghrib_minRight, maghrib_jamma_hourLeft, maghrib_jamma_hourRight, maghrib_jamma_minLeft, maghrib_jamma_minRight;
    private Label isha_hourLeft, isha_hourRight, isha_minLeft, isha_minRight, isha_jamma_hourLeft, isha_jamma_hourRight, isha_jamma_minLeft, isha_jamma_minRight;
    private Label friday_hourLeft, friday_hourRight, friday_minLeft, friday_minRight;
    private Label Phase_Label, Moon_Date_Label, Moon_Image_Label, friday_Label_eng,friday_Label_ar,sunrise_Label_ar,sunrise_Label_eng, fajr_Label_ar, fajr_Label_eng, zuhr_Label_ar, zuhr_Label_eng, asr_Label_ar, asr_Label_eng, maghrib_Label_ar, maghrib_Label_eng, isha_Label_ar, isha_Label_eng, jamaat_Label_eng,jamaat_Label_ar, athan_Label_eng,athan_Label_ar, hadith_Label, announcement_Label,athan_Change_Label_L1, athan_Change_Label_L2, hour_Label, minute_Label, date_Label, divider1_Label, divider2_Label, ar_moon_hadith_Label_L1, ar_moon_hadith_Label_L2, en_moon_hadith_Label_L1, en_moon_hadith_Label_L2, facebook_Label;
    
    private List<String> images;
    private File directory;
    private File[] files;
    private String rand_Image_Path;
    private int countImages;
    private int imageNumber;
    int dayofweek_int;
    
    
    private long moonPhase_lastTimerCall,translate_lastTimerCall, clock_update_lastTimerCall ,sensor_lastTimerCall, debug_lastTimerCall, proximity_lastTimerCall;
    public long delay_turnOnTV_after_Prayers = 135000000000L; // 2.25 minute
//    public long delay_turnOnTV_after_Prayers = 60000000000L; // 1 minute
    public long delay_turnOnTV_after_Prayers_nightmode = 420000000000L; // 7 minutes
    
    public long delay_turnOffTV_after_inactivity = 1500000000000L; // 25minutes
//    public long delay_turnOffTV_after_inactivity = 280000000000L; // 1minutes
    private AnimationTimer moonPhase_timer, translate_timer, clock_update_timer ,debug_timer ;
        
    DateFormat dateFormat = new SimpleDateFormat("hh:mm");
    
    
    GridPane Mainpane, Moonpane, prayertime_pane, clockPane, hadithPane;
    char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
    static String[] suffixes =
    //    0     1     2     3     4     5     6     7     8     9
       { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
    //    10    11    12    13    14    15    16    17    18    19
         "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
    //    20    21    22    23    24    25    26    27    28    29
         "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
    //    30    31
         "th", "st" };
    Connection c,c2 ;
    ObservableList<String> names = FXCollections.observableArrayList();
    private String fromClient;
    private String toClient;
    private ServerSocket server;
    
    DatagramSocket socket, socket1;
    boolean send_Broadcast_msg = false;
    String broadcast_msg;
    byte[] buf1 = new byte[256];
    InetAddress group;
    DatagramPacket packet1;

    Scene scene;
    File file = new File("/home/pi/prayertime/Images/");
    
    @Override public void init() throws IOException {
        
        
// Twitter ==============================
    
//        String Test = "This is a test message"; 
//        Twitter twitter = TwitterFactory.getSingleton();
//        Status status = null;
//        try {
//             status = twitter.updateStatus(Test);
//        } catch (TwitterException ex) {
//            logger.warn("Unexpected error", e);
//        }
//        System.out.println("Successfully updated the status to [" + status.getText() + "].");

 
        
        logger.info("Starting application....");
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() 
        {            
            @Override
            public void run() 
            {
                logger.info("Exiting application....");
                ProcessBuilder processBuilder2 = new ProcessBuilder("bash", "-c", "echo \"standby 0000\" | cec-client -d 1 -s \"standby 0\" RPI");
                try {Process process2 = processBuilder2.start();}
                catch (IOException e) {logger.warn("Unexpected error", e);}
            
            }
        }));
        
        moonPhase = 200;
     
// Get Parameter from database==========================================================
        
        try
            {
                c = DBConnect.connect();
                SQL = "Select * from settings";
                rs = c.createStatement().executeQuery(SQL);
                while (rs.next()) 
                {
                    id =                            rs.getInt("id");
                    platform =                      rs.getString("platform");
                    facebook_notification_enable =  rs.getBoolean("facebook_notification_enable");  
                    facebook_Receive             =  rs.getBoolean("facebook_Receive"); 
                    latitude =                      rs.getDouble("latitude");
                    longitude =                     rs.getDouble("longitude");
                    timezone =                      rs.getInt("timezone");
                    timeZone_ID =                   rs.getString("timeZone_ID");
                    device_name =                   rs.getString("device_name");
                    device_location =               rs.getString("device_location");
                    pir_sensor =                    rs.getBoolean("pir_sensor");
                    calcMethod =                    rs.getInt("calcMethod");
                    AsrJuristic =                   rs.getInt("AsrJuristic");
                    fb_Access_token =               rs.getString("fb_Access_token");
                    page_ID =                       rs.getString("page_ID");
                    maghrib_adj =                   rs.getInt("maghrib_adj");
                    max_ar_hadith_len =             rs.getInt("max_ar_hadith_len");
                    max_en_hadith_len =             rs.getInt("max_en_hadith_len");
                    
                    
                }
                c.close();
                System.out.format("Prayertime server running on %s platform\n", platform);
                System.out.format(" Face Book Notification Enabled: %s \n Face Book Receive posts: %s \n Facebook page ID: %s \n Latitude: %s \n Longitude: %s \n Time Zone: %s \n Calculation Method: %s  \n Asr Juristic: %s \n", facebook_notification_enable, facebook_Receive, page_ID, latitude, longitude, timezone,calcMethod, AsrJuristic );
                System.out.format("Device Name is:%s at %s \n", device_name, device_location);
                System.out.format("Time Zone ID is:%s \n", timeZone_ID);
            }
        catch (Exception e){logger.warn("Unexpected error", e);}

        if (facebook_notification_enable){System.out.println("facebook notification is enabled" );}
        if (!facebook_notification_enable){System.out.println("facebook notification is not enabled" );}
        if (pir_sensor){System.out.println("PIR sensor is enabled" );}
        
// facebook Client ==========================================================================        

//        FacebookClient facebookClient = new DefaultFacebookClient("CAAJRZCld8U30BAMmPyEHDW2tlR07At1vTmtHEmD8iHtiFWx7D2ZBroCVWQfdhxQ7h2Eohv8ZBPRk85vs2r7XC0K4ibGdFNMTkh0mJU8vui9PEnpvENOSAFD2q7CQ7NJXjlyK1yITmcrvZBAZByy4qV7whiAb2a2SN7s23nYvDgMMG3RhdPIakZBLV39pkksjYZD");
        FacebookClient facebookClient = new DefaultFacebookClient(fb_Access_token);        
        
// Pushover ==========================================================================        
        
        //https://github.com/nicatronTg/jPushover
        Pushover p = new Pushover("WHq3q48zEFpTqU47Wxygr3VMqoodxc", "skhELgtWRXslAUrYx9yp1s0Os89JTF");
        String temp_msg = device_name + " at "+ device_location + " is starting";
        try {p.sendMessage(temp_msg);} catch (IOException e){e.printStackTrace();}
        
        
//        test
//        try 
//                                            {
//                                                String pageID = page_ID +"/feed";
//                                                String temporary_msg = " This is an Automated test message";
//                                                facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", temporary_msg));
//                                                
//                                            }
//                                            catch (FacebookException e){logger.warn("Unexpected error", e);} 

        
//Load random Background image on strtup ===============================================        
        images = new ArrayList<String>();
        //change on osx
        if (platform.equals("osx"))
//        {directory = new File("/Users/ossama/Projects/Pi/javafx/prayertime/background/");} 
        {directory = new File("/Users/ossama/Dropbox/Projects/Pi/javafx/prayertime/background");}
//        {directory = new File("/Users/samia/NetBeansProjects/prayertime_files/background/");}
        //change on Pi
        if (platform.equals("pi"))
        {directory = new File("/home/pi/prayertime/Images/");}
        
        files = directory.listFiles();
        for(File f : files) 
        {
            images.add(f.getName());
        }   
        System.out.println(images);
        countImages = images.size();
        imageNumber = (int) (Math.random() * countImages);
        rand_Image_Path = directory + "/"+ images.get(imageNumber);
        System.out.println(rand_Image_Path);
        
        if (!platform.equals("osx"))
        {
            try
            {
                broadcast_msg = "Prayer Time Server Starting";
                socket1 = new DatagramSocket(null);
                socket1.setBroadcast(true);
                buf1 = broadcast_msg.getBytes();
                group = InetAddress.getByName("255.255.255.255");
                packet1 = new DatagramPacket(buf1, buf1.length, group, 8888);
                socket1.send(packet1);

            }
            catch(Exception e){System.err.println("Sending failed. " + e.getMessage());}
        }
        
        
        
//        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
//        Process process = processBuilder.start();
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//               System.out.println(line);
//            } 
       
//        ProcessBuilder processBuilder_Athan = new ProcessBuilder("bash", "-c", "mpg123 /home/pi/prayertime/Audio/athan1.mp3");
//        try {Process process3 = processBuilder_Athan.start();} 
//            catch (IOException e) {logger.warn("Unexpected error", e);}
        
        
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/PTBLARC.TTF").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BMajidSh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Oldoutsh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BJadidBd.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/wlm_carton.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Arial_Black.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Arial_Bold.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/timeburner_regular.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Pinstripe_Limo.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/LateefRegOT.ttf").toExternalForm(),30);
        
        data = FXCollections.observableArrayList();
        Mainpane = new GridPane();
        
        footer_Label = new Label();
        like_Label = new Label();
        Moon_Image_Label = new Label();
        Phase_Label = new Label();
        Moon_Date_Label = new Label();
        jamaat_Label_eng = new Label();
        jamaat_Label_ar = new Label();
        athan_Label_eng = new Label();
        athan_Label_ar = new Label();
        friday_Label_eng = new Label();
        friday_Label_ar = new Label();
        sunrise_Label_eng = new Label();
        sunrise_Label_ar = new Label();
        fajr_Label_ar = new Label();
        fajr_Label_eng = new Label();
        zuhr_Label_ar = new Label();
        zuhr_Label_eng = new Label();
        asr_Label_ar = new Label();
        asr_Label_eng = new Label();
        maghrib_Label_ar = new Label();
        maghrib_Label_eng = new Label();
        isha_Label_ar = new Label();
        isha_Label_eng = new Label();
        hadith_Label = new Label();
        ar_moon_hadith_Label_L1 = new Label();
        ar_moon_hadith_Label_L2 = new Label();
        en_moon_hadith_Label_L1 = new Label();
        en_moon_hadith_Label_L2 = new Label();
        announcement_Label = new Label();
        athan_Change_Label_L1 = new Label();
        athan_Change_Label_L2 = new Label();
        hour_Label = new Label();
        minute_Label = new Label();
        date_Label = new Label();
        divider1_Label = new Label();
        divider2_Label = new Label();
        fajr_hourLeft = new Label();
        fajr_hourRight = new Label();
        time_Separator1 = new Label();
        fajr_minLeft = new Label();
        fajr_minRight = new Label();
        fajr_jamma_hourLeft = new Label();
        fajr_jamma_hourRight = new Label();
        time_jamma_Separator1 = new Label();
        fajr_jamma_minLeft = new Label();
        fajr_jamma_minRight = new Label();
        sunrise_hourLeft = new Label();
        sunrise_hourRight = new Label();
        time_Separator2 = new Label();
        sunrise_minLeft = new Label();
        sunrise_minRight = new Label();
        zuhr_hourLeft = new Label();
        zuhr_hourRight = new Label();
        time_Separator3 = new Label();
        zuhr_minLeft = new Label();
        zuhr_minRight = new Label();
        zuhr_jamma_hourLeft = new Label();
        zuhr_jamma_hourRight = new Label();
        time_jamma_Separator2 = new Label();
        zuhr_jamma_minLeft = new Label();
        zuhr_jamma_minRight = new Label();
        asr_hourLeft = new Label();
        asr_hourRight = new Label();
        time_Separator4 = new Label();
        asr_minLeft = new Label();
        asr_minRight = new Label();
        asr_jamma_hourLeft = new Label();
        asr_jamma_hourRight = new Label();
        time_jamma_Separator3 = new Label();
        asr_jamma_minLeft = new Label();
        asr_jamma_minRight = new Label();
        maghrib_hourLeft = new Label();
        maghrib_hourRight = new Label();
        time_Separator5 = new Label();
        maghrib_minLeft = new Label();
        maghrib_minRight = new Label();
        maghrib_jamma_hourLeft = new Label();
        maghrib_jamma_hourRight = new Label();
        time_jamma_Separator4 = new Label();
        maghrib_jamma_minLeft = new Label();
        maghrib_jamma_minRight = new Label();
        isha_hourLeft = new Label();
        isha_hourRight = new Label();
        time_Separator6 = new Label();
        isha_minLeft = new Label();
        isha_minRight = new Label();
        isha_jamma_hourLeft = new Label();
        isha_jamma_hourRight = new Label();
        time_jamma_Separator5 = new Label();
        isha_jamma_minLeft = new Label();
        isha_jamma_minRight = new Label();
        friday_hourLeft = new Label();
        friday_hourRight = new Label();
        time_Separator8 = new Label();
        friday_minLeft = new Label();
        friday_minRight = new Label();
        facebook_Label = new Label();
        
    
        athan_Label_ar.setId("prayer-label-arabic");
        athan_Label_ar.setText("الأذان");
        prayertime_pane.setHalignment(athan_Label_ar,HPos.CENTER) ;
        athan_Label_eng.setId("prayer-label-english");
        athan_Label_eng.setText("Athan");
        prayertime_pane.setHalignment(athan_Label_eng,HPos.CENTER);
        
        jamaat_Label_ar.setId("prayer-label-arabic");
        jamaat_Label_ar.setText("الإقامة");
        prayertime_pane.setHalignment(jamaat_Label_ar,HPos.CENTER) ;
        jamaat_Label_eng.setId("prayer-label-english");
        jamaat_Label_eng.setText("Congregation");
        prayertime_pane.setHalignment(jamaat_Label_eng,HPos.CENTER);
        
        
        sunrise_Label_ar.setId("prayer-label-arabic");
        sunrise_Label_ar.setText("الشروق");
        prayertime_pane.setHalignment(sunrise_Label_ar,HPos.CENTER) ;
        sunrise_Label_eng.setId("prayer-label-english");
        sunrise_Label_eng.setText("Sunrise");
        prayertime_pane.setHalignment(sunrise_Label_eng,HPos.CENTER);
        
        friday_Label_ar.setId("prayer-label-arabic");
        friday_Label_ar.setText("الجمعة");
        prayertime_pane.setHalignment(friday_Label_ar,HPos.CENTER) ;
        friday_Label_eng.setId("prayer-label-english");
        friday_Label_eng.setText("Friday");
        prayertime_pane.setHalignment(friday_Label_eng,HPos.CENTER);
        
        isha_Label_ar.setId("prayer-label-arabic");
        isha_Label_ar.setText("العشاء");
        prayertime_pane.setHalignment(isha_Label_ar,HPos.CENTER) ;
        isha_Label_eng.setId("prayer-label-english");
        isha_Label_eng.setText("Isha");
        prayertime_pane.setHalignment(isha_Label_eng,HPos.CENTER);        
        
        maghrib_Label_ar.setId("prayer-label-arabic");
        maghrib_Label_ar.setText("المغرب");
        prayertime_pane.setHalignment(maghrib_Label_ar,HPos.CENTER) ;
        maghrib_Label_eng.setId("prayer-label-english");
        maghrib_Label_eng.setText("Maghrib");
        prayertime_pane.setHalignment(maghrib_Label_eng,HPos.CENTER);
        
        asr_Label_ar.setId("prayer-label-arabic");
        asr_Label_ar.setText("العصر");
        GridPane.setHalignment(asr_Label_ar,HPos.CENTER) ;
        asr_Label_eng.setId("prayer-label-english");
        asr_Label_eng.setText("Asr");
        GridPane.setHalignment(asr_Label_eng,HPos.CENTER);
        
        zuhr_Label_ar.setId("prayer-label-arabic");
        zuhr_Label_ar.setText("الظهر");
        GridPane.setHalignment(zuhr_Label_ar,HPos.CENTER) ;
        zuhr_Label_eng.setId("prayer-label-english");
        zuhr_Label_eng.setText("Duhr");
        GridPane.setHalignment(zuhr_Label_eng,HPos.CENTER);
        
        fajr_Label_ar.setId("prayer-label-arabic");
        fajr_Label_ar.setText("الفجر");
        GridPane.setHalignment(fajr_Label_ar,HPos.CENTER) ;
        fajr_Label_eng.setId("prayer-label-english");
        fajr_Label_eng.setText("Fajr");
        GridPane.setHalignment(fajr_Label_eng,HPos.CENTER);


       
        Timer prayerCalcTimer = new Timer();
        prayerCalcTimer.scheduleAtFixedRate(new TimerTask() 
        {
            @Override
            public void run() 
            {
                try {
                        
//                        Moon m = new Moon();
//                        moonPhase = m.illuminatedPercentage();
//                        isWaning = m.isWaning();
//                        update_moon_image = true;
//                        System.out.println("The moon is " + moonPhase + "% full and " + (isWaning ? "waning" : "waxing"));
//                    
                    
                        Locale.setDefault(new Locale("en", "AU"));
                        Date now = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(now);
//                        cal.add(Calendar.DAY_OF_MONTH, -3);
//                        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        cal.setFirstDayOfWeek(Calendar.MONDAY);
                        dayofweek_int = cal.get(Calendar.DAY_OF_WEEK);
                        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        PrayTime getprayertime = new PrayTime();
                        
                        getprayertime.setTimeFormat(0);
                        getprayertime.setCalcMethod(calcMethod);
                        getprayertime.setAsrJuristic(AsrJuristic);
                        getprayertime.setAdjustHighLats(0);
                        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
                        getprayertime.tune(offsets);
                        
                        Date time = cal.getTime();
                        System.out.println(" daylight saving? " + TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time ));
                        
//                        The following calculate the next daylight saving date
                        DateTimeZone zone = DateTimeZone.forID(timeZone_ID);        
                        DateTimeFormatter format = DateTimeFormat.mediumDateTime();

//                        long current = System.currentTimeMillis();
//                        for (int i=0; i < 1; i++)
//                        {
//                            long next = zone.nextTransition(current);
//                            if (current == next)
//                            {
//                                break;
//                            }
//                            System.out.println ("Next Daylight saving Change: " + format.print(next) + " Into DST? " 
//                                                + !zone.isStandardOffset(next));
//                            current = next;
//                        }
                        
                        long next = zone.nextTransition(System.currentTimeMillis());
                        
                        Date nextTransitionDate = new Date(next);
                        Format format1 = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
                        format1.format(nextTransitionDate).toString();
                        Calendar nextTransitionCal = Calendar.getInstance();
                        nextTransitionCal.setTime(nextTransitionDate);
//                        Date fajr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(next);
                        nextTransitionCal.add(Calendar.DAY_OF_MONTH, -7);
                        nextTransitionCal.set(Calendar.HOUR_OF_DAY, 0);
                        System.out.println ("Next Daylight saving Check (-7 days): " + nextTransitionCal.getTime());
                        
                        
                        
                        ArrayList<String> prayerTimes = getprayertime.getPrayerTimes(cal, latitude, longitude, timezone);
                        ArrayList<String> prayerNames = getprayertime.getTimeNames();

                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                        
                        Date fajr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(0)).getTime()));
                        cal.setTime(fajr_temp);
                        if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date fajr = cal.getTime();
                        fajr_cal = Calendar.getInstance();
                        fajr_cal.setTime(fajr);
                        fajr_begins_time = fajr_cal.getTime();
                        System.out.println(" fajr time " + fajr_begins_time);
                        
                        Date sunrise_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(1)).getTime()));
                        cal.setTime(sunrise_temp);
                        if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date sunrise = cal.getTime();
                        sunrise_cal = Calendar.getInstance();
                        sunrise_cal.setTime(sunrise);
                        sunrise_time = sunrise_cal.getTime();
                        System.out.println(" sunrise time " + sunrise_time);
                        
                        cal.add(Calendar.MINUTE, 15);
                        Date duha = cal.getTime();
//                            System.out.println(duha);
                        duha_cal = Calendar.getInstance();
                        duha_cal.setTime(duha);
                        duha_cal.set(Calendar.MILLISECOND, 0);
                        duha_cal.set(Calendar.SECOND, 0);
                        System.out.println(" Duha alarm time " + duha_cal.getTime());
                                                
                        
                        Date zuhr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(2)).getTime()));
                        cal.setTime(zuhr_temp);
                        if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date zuhr = cal.getTime();
                        zuhr_cal = Calendar.getInstance();
                        zuhr_cal.setTime(zuhr);
                        zuhr_begins_time = zuhr_cal.getTime();
                        System.out.println(" Zuhr time " + zuhr_begins_time);
                        
                        Date asr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(3)).getTime()));
                        cal.setTime(asr_temp);
                        if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date asr = cal.getTime();
                        asr_cal = Calendar.getInstance();
                        asr_cal.setTime(asr);
                        asr_begins_time = asr_cal.getTime();
                        System.out.println(" Asr time " + asr_begins_time);
                        
                        Date maghrib_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(5)).getTime()));
                        cal.setTime(maghrib_temp);
                        if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date maghrib = cal.getTime();
                        maghrib_cal = Calendar.getInstance();
                        maghrib_cal.setTime(maghrib);
                        maghrib_begins_time = maghrib_cal.getTime();
                        System.out.println(" maghrib time " + maghrib_begins_time);

                        maghrib_plus15_cal = (Calendar)maghrib_cal.clone();
                        maghrib_plus15_cal.add(Calendar.MINUTE, +15);
                        
                        Date isha_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(6)).getTime()));
                        cal.setTime(isha_temp);
                        if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date isha = cal.getTime();
                        isha_cal = Calendar.getInstance();
                        isha_cal.setTime(isha);
                        isha_begins_time = isha_cal.getTime();
                        System.out.println(" isha time " + isha_begins_time);
                        
//                        set friday prayer here
                        if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){friday_jamaat = "01:30";}
                        else{friday_jamaat = "12:30";}
           
                        update_prayer_labels = true;
                        getFacebook = true;
                        
                        DateTime_now = new DateTime();    
                        Calendar_now = Calendar.getInstance();
                        Calendar_now.setTime(new Date());
                        Calendar_now.set(Calendar.MILLISECOND, 0);
                        Calendar_now.set(Calendar.SECOND, 0);
                        Calendar_now.set(Calendar.MINUTE, 0);
                        Calendar_now.set(Calendar.HOUR_OF_DAY, 0);

                        tzSAUDI_ARABIA = DateTimeZone.forID("Asia/Riyadh");
                        dtIslamic = DateTime_now.withChronology(IslamicChronology.getInstance(tzSAUDI_ARABIA, IslamicChronology.LEAP_YEAR_15_BASED));
                        System.out.print("Arabic Date:  ");
                        System.out.print(dtIslamic.getMonthOfYear());
                        System.out.print("/");
                        System.out.println(dtIslamic.getDayOfMonth());

                        if (dtIslamic.getMonthOfYear()==9){System.out.println("==========Ramadan Moubarik==========");}
        
                        //enable athan play time
                        if (dayofweek_int != olddayofweek_int)
                        {   
                            Moon m = new Moon();
                            moonPhase = m.illuminatedPercentage();
                            isWaning = m.isWaning();
                            update_moon_image = true;
                            System.out.println("The moon is " + moonPhase + "% full and " + (isWaning ? "waning" : "waxing"));
                    
//                            old_today = Calendar.getInstance();
                            olddayofweek_int = dayofweek_int;                         
//                            System.out.println("current day of the week " + dayofweek_int ); 
//                            System.out.println("old day of the week " + olddayofweek_int ); 
//                            System.out.println(" cal:  " + cal ); 
                            fajr_athan_enable = true;
                            duha_athan_enable = true;
                            zuhr_athan_enable = true;
                            asr_athan_enable = true;
                            maghrib_athan_enable = true;
                            isha_athan_enable = true;
                            getHadith = true;
                            
//==============JAMAA Prayer time 

                            try
                            {
                                c = DBConnect.connect();
    //                            System.out.println("connected");
                                SQL = "select * from prayertimes where DATE(date) = DATE(NOW())";
                                rs = c.createStatement().executeQuery(SQL);
                                while (rs.next()) 
                                {
                                    id =                rs.getInt("id");
                                    prayer_date =       rs.getDate("date");
                                    fajr_jamaat_time =       rs.getTime("fajr_jamaat");
                                    asr_jamaat_time =        rs.getTime("asr_jamaat");
                                    isha_jamaat_time =       rs.getTime("isha_jamaat");           
                                }
                                c.close();
                                fajr_jamaat = fajr_jamaat_time.toString();
                                if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){zuhr_jamaat = "01:30";} else{zuhr_jamaat = "12:30";}
                                asr_jamaat = asr_jamaat_time.toString();
                                isha_jamaat = isha_jamaat_time.toString();
                                // print the results
//                                System.out.format("%s,%s,%s,%s,%s \n", id, prayer_date, fajr_jamaat, asr_jamaat, isha_jamaat );
                            }
                            catch (Exception e){ logger.warn("Unexpected error", e); }
                                                
                            
                            Date fajr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + fajr_jamaat);
                            cal.setTime(fajr_jamaat_temp);
                            cal.add(Calendar.MINUTE, 5);
                            Date fajr_jamaat = cal.getTime();
                            fajr_jamaat_update_cal = Calendar.getInstance();
                            fajr_jamaat_update_cal.setTime(fajr_jamaat);
                            fajr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            fajr_jamaat_update_cal.set(Calendar.SECOND, 0);
                            
                            fajr_jamaat_cal = (Calendar)fajr_jamaat_update_cal.clone();
                            fajr_jamaat_cal.add(Calendar.MINUTE, -5);
//                            System.out.println("fajr Jamaat update scheduled at:" + fajr_jamaat_update_cal.getTime());
                            
                            Date zuhr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + zuhr_jamaat);
                            cal.setTime(zuhr_jamaat_temp);
                            Date zuhr_jamaat_Date = cal.getTime();
                            zuhr_jamaat_cal = Calendar.getInstance();
                            zuhr_jamaat_cal.setTime(zuhr_jamaat_Date);
                            zuhr_jamaat_cal.set(Calendar.MILLISECOND, 0);
                            zuhr_jamaat_cal.set(Calendar.SECOND, 0);
                            
                            zuhr_plus15_cal = (Calendar)zuhr_jamaat_cal.clone();
                            zuhr_plus15_cal.add(Calendar.MINUTE, +15);
                            
                            Date asr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + asr_jamaat);
                            cal.setTime(asr_jamaat_temp);
                            cal.add(Calendar.MINUTE, 5);
                            Date asr_jamaat = cal.getTime();
                            asr_jamaat_update_cal = Calendar.getInstance();
                            asr_jamaat_update_cal.setTime(asr_jamaat);
                            asr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            asr_jamaat_update_cal.set(Calendar.SECOND, 0);
//                            System.out.println("asr Jamaat update scheduled at:" + asr_jamaat_update_cal.getTime());
                            asr_jamaat_cal = (Calendar)asr_jamaat_update_cal.clone();
                            asr_jamaat_cal.add(Calendar.MINUTE, -5);


                            maghrib_jamaat_cal = (Calendar)maghrib_cal.clone();
                            maghrib_jamaat_cal.add(Calendar.MINUTE, maghrib_adj);
                            

                            Date isha_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + isha_jamaat);
                            cal.setTime(isha_jamaat_temp);
                            cal.add(Calendar.MINUTE, 5);
                            Date isha_jamaat = cal.getTime();
                            isha_jamaat_update_cal = Calendar.getInstance();
                            isha_jamaat_update_cal.setTime(isha_jamaat);
                            isha_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            isha_jamaat_update_cal.set(Calendar.SECOND, 0);
//                            System.out.println("Isha Jamaat update scheduled at:" + isha_jamaat_update_cal.getTime());
                            isha_jamaat_cal = (Calendar)isha_jamaat_update_cal.clone();
                            isha_jamaat_cal.add(Calendar.MINUTE, -5);

                            
//==============Prayer time change notification logic + 7days
// check excel file in documentation folder for a flow chart                            
                            
                            // check if a notification has already been sent, to avoid flooding users with notifications, i.e during a system restart
                            ar_notification_Msg_Lines = null;
                            
                            try
                            {
                                c = DBConnect.connect();
                                SQL = "Select * from notification where id = (select max(id) from notification)";
                                rs = c.createStatement().executeQuery(SQL);
                                while (rs.next()) 
                                {
                                    id =                rs.getInt("id");
                                    notification_Date = rs.getDate("notification_Date");
                                    en_message_String = rs.getString("en_message_String");
                                    ar_message_String = rs.getString("ar_message_String");
                                    notification_Sent = rs.getBoolean("notification_Sent");             
                                }
                                c.close();
                                System.out.format("%s,%s,%s,%s \n", notification_Date, en_message_String, ar_message_String, notification_Sent );
                            }
                            catch (Exception e){logger.warn("Unexpected error", e);}
                            notification_Date_cal = Calendar.getInstance();
                            notification_Date_cal.setTime(notification_Date);
                            notification_Date_cal.set(Calendar.MILLISECOND, 0);
                            notification_Date_cal.set(Calendar.SECOND, 0);
                            
//                            System.out.println("notification_Date_cal:" + notification_Date_cal.getTime());
//                            System.out.println("Calendar_now:         " + Calendar_now.getTime());
                            
                            if (Calendar_now.compareTo(notification_Date_cal) <0 )  //&& !notification_Sent
                            {
                                en_notification_Msg = en_message_String;
                                ar_notification_Msg = ar_message_String;                                
                                ar_notification_Msg_Lines = ar_notification_Msg.split("\\r?\\n");
                                en_notification_Msg_Lines = en_notification_Msg.split("\\r?\\n");
                                athan_Change_Label_visible = true;
                                getFacebook = false;
                            }
                            
                            if (Calendar_now.compareTo(notification_Date_cal) >=0 )  //&& !notification_Sent
                            {
                                athan_Change_Label_visible = false;
                                try
                                {
                                        c = DBConnect.connect();
                                        SQL = "select * from prayertimes where DATE(date) = DATE(NOW() ) + INTERVAL 7 DAY ";
                                        rs = c.createStatement().executeQuery(SQL);
                                        while (rs.next()) 
                                        {
                                            future_prayer_date =       rs.getDate("date");
                                            future_fajr_jamaat_time =       rs.getTime("fajr_jamaat");
                                            future_asr_jamaat_time =        rs.getTime("asr_jamaat");
                                            future_isha_jamaat_time =       rs.getTime("isha_jamaat");             
                                        }
                                        c.close();

                                        // print the results
                                        System.out.format("%s,%s,%s,%s \n", future_prayer_date, future_fajr_jamaat_time, future_asr_jamaat_time, future_isha_jamaat_time );


                                    if (!fajr_jamaat_time.equals(future_fajr_jamaat_time))
                                    {

                                        System.out.println("Fajr Prayer Time Difference" );
                                        fajr_jamma_time_change =true;
                                        notification = true;

                                        java.sql.Date sqlDate = new java.sql.Date(future_prayer_date.getTime());
                                        c = DBConnect.connect();
                                        PreparedStatement ps = c.prepareStatement("INSERT INTO prayertime.notification (notification_Date) VALUE (?)");  
                                        ps.setDate(1, sqlDate);  
                                        ps.executeUpdate(); 
                                        c.close();
                                    }


                                    if (!asr_jamaat_time.equals(future_asr_jamaat_time) )
                                    {
                                        System.out.println("asr Prayer Time Difference" );
                                        asr_jamma_time_change =true;
                                        if(!notification)
                                        {
                                            java.sql.Date sqlDate = new java.sql.Date(future_prayer_date.getTime());
                                            c = DBConnect.connect();
                                            PreparedStatement ps = c.prepareStatement("INSERT INTO prayertime.notification (notification_Date) VALUE (?)");  
                                            ps.setDate(1, sqlDate);  
                                            ps.executeUpdate(); 
                                            c.close();
                                        }
                                        notification = true;
                                    }


                                    if (!isha_jamaat_time.equals(future_isha_jamaat_time) )
                                    {
                                        System.out.println("isha Prayer Time Difference" );
                                        isha_jamma_time_change =true;
                                        if(!notification)
                                        {
                                            java.sql.Date sqlDate = new java.sql.Date(future_prayer_date.getTime());
                                            c = DBConnect.connect();
                                            PreparedStatement ps = c.prepareStatement("INSERT INTO prayertime.notification (notification_Date) VALUE (?)");  
                                            ps.setDate(1, sqlDate);  
                                            ps.executeUpdate(); 
                                            c.close();
                                        }
                                        notification = true;
                                    }
                                }
                                catch (Exception e){logger.warn("Unexpected error", e);}
                                
                            }
                            
                            
                            fullMoon = MoonPhaseFinder.findFullMoonFollowing(Calendar.getInstance());
                            newMoon = MoonPhaseFinder.findNewMoonFollowing(Calendar.getInstance());
                            System.out.println("The moon is full on " + fullMoon );
                            System.out.println("The moon is new on " + newMoon );                       
                            if(newMoon.before(fullMoon)){System.out.println("New moon is before full moon");}
                            else {System.out.println("Full moon is before new moon" );}
                            pir_disactive_startup = false;
                            
// ======= Notification for ashura and full moon 5 days earlier, 2 days before fasting period

                            
//TODO Use moonsighting.info to get moon sighting observations
                            
                            Days d = Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon));
                            int days_Between_Now_Fullmoon = d.getDays();
                            System.out.format("Days left to full moon: %s\n", days_Between_Now_Fullmoon );
                            
                            fullMoon_plus1 = (Date)fullMoon.clone();
                            Date ashura = (Date)fullMoon.clone();
                            DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();
//                                System.out.println(days_Between_Now_Fullmoon);
                            
                            if (dtIslamic.getMonthOfYear()==1 &&  days_Between_Now_Fullmoon ==9 && comparator.compare(fullMoon, maghrib_cal)>0)
                            {
                                //hide hadith label boolean
                                getHadith = false;
//                                getFacebook = false;
                                hadith_Label_visible = false;
                                //show moon notification label boolean
                                moon_hadith_Label_visible = true;
                                
                                // 15 - 9 = 6 Ashura = fullMoon_plus1.setTime(fullMoon.getTime() - 4 * 24 * 60 * 60 * 1000);
                                
                                                                
                                
                                
                                try
                                {
                                    c = DBConnect.connect();

                                    SQL = "select hadith, translated_hadith from hadith WHERE (translated_hadith LIKE '%Ashura%') and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";
                                    rs = c.createStatement().executeQuery(SQL);
                                    while (rs.next()) 
                                    {
                                        hadith = rs.getString("hadith");
                                        translated_hadith = rs.getString("translated_hadith");

                                    }
                                    c.close();
                                    System.out.println("Ashura arabic hadith length" + hadith.length());
                                    System.out.println("Ashura english hadith length" + translated_hadith.length());
                                
                                }  
                                catch (Exception e){logger.warn("Unexpected error", e);}
                                ashura.setTime(fullMoon.getTime() - 4 * 24 * 60 * 60 * 1000);
                                String Ashura_dow_ar = new SimpleDateFormat("' 'EEEE' '", new Locale("ar")).format(ashura);
                                String Ashura_dow_en = new SimpleDateFormat("EEEE").format(ashura);
                                String temp_ar_text1 = "نذكركم أيها الأحبة بصيام يوم عاشوراء يوم ";
                                String temp_ar_text2 = "إن شاء الله ويوم قبله اوبعده. إن استطعت الصيام فصم و ذكر أحبابك";
                                ar_moon_notification = temp_ar_text1 + Ashura_dow_ar + temp_ar_text2;
                                en_moon_notification = "A reminder that the 10th of Muharram \"Ashura\" will fall on " + Ashura_dow_en + ", It is recommended to fast either the 9th & 10th of Muharram or the 10th & 11th ";
                                facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;    
                                if (facebook_notification_enable)
                                {
                                    try 
                                    {
                                        String pageID = page_ID +"/feed";
                                        facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", facebook_moon_notification_Msg));
                                        System.out.println("Full Moon Notification Sent to Facebook:" );
                                        System.out.println(facebook_moon_notification_Msg);
                                    }
                                    catch (FacebookException e){logger.warn("Unexpected error", e);} 

                                }
                                
                                
                                
                            }
                            
                            else if (dtIslamic.getMonthOfYear()==1 &&  days_Between_Now_Fullmoon ==9 )
                            {
                                //hide hadith label boolean
                                getHadith = false;
//                                getFacebook = false;
                                hadith_Label_visible = false;
                                //show moon notification label boolean
                                moon_hadith_Label_visible = true;
                                
                                
                                // 15 - 9 = 6 Ashura = fullMoon_plus1.setTime(fullMoon.getTime() - 5 * 24 * 60 * 60 * 1000);
                                
                                
                            }
                            
                            
                            if (dtIslamic.getMonthOfYear()!=9 && days_Between_Now_Fullmoon <= 5 && days_Between_Now_Fullmoon >= 2)
                            {                                
                                //hide hadith label boolean
                                getHadith = false;
//                                getFacebook = false;
                                hadith_Label_visible = false;
                                //show moon notification label boolean
                                moon_hadith_Label_visible = true;
                                try
                                {
                                    c = DBConnect.connect();
                                    SQL = "select hadith, translated_hadith from hadith WHERE day = '15' ORDER BY RAND( ) LIMIT 1";
                                    rs = c.createStatement().executeQuery(SQL);
                                    while (rs.next()) 
                                    {
                                        ar_full_moon_hadith = rs.getString("hadith");
                                        en_full_moon_hadith = rs.getString("translated_hadith");
                                    }
                                    c.close();
                                    System.out.format("Full Moon arabic hadith: %s\n", ar_full_moon_hadith );
                                    System.out.format("Full Moon english hadith: %s\n", en_full_moon_hadith );
                                }
                                catch (Exception e){logger.warn("Unexpected error", e);}
                                
                                
                                
                                if ( days_Between_Now_Fullmoon == 5 && comparator.compare(fullMoon, maghrib_cal)<0)
                                {
                                    fullMoon_plus1.setTime(fullMoon.getTime() - 2 * 24 * 60 * 60 * 1000);
                                    String FullMoon_dow_ar = new SimpleDateFormat("' 'EEEE' '", new Locale("ar")).format(fullMoon_plus1);
                                    String FullMoon_dow_en = new SimpleDateFormat("EEEE").format(fullMoon_plus1);
                                    String temp_ar_text1 = "نذكركم و أنفسنا بفضل صيام الايام البيض من كل شهر التي تبدأ يوم";
                                    String temp_ar_text2 = " إن استطعت الصيام فصم و ذكر أحبابك. يرجى ملاحظة أن هذا يقوم على حسابات التقويم لاعلى ملاحظات رؤية الهلال";
                                    ar_moon_notification = temp_ar_text1 + FullMoon_dow_ar + temp_ar_text2;
                                    en_moon_notification = "We would like to remind our dear brothers & sisters that this month's \"White days\" will start this " + FullMoon_dow_en + ", it is recommended to fast these days. Pls note that this is based on calendar calculations not moon sighting observations";
                                    facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;
//                                    try
//                                    {
//                                        String pageID = page_ID +"/feed";
//                                        facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", facebook_moon_notification_Msg));
//                                    }
//                                    catch (FacebookException e){logger.warn("Unexpected error", e);}                           
//                                    System.out.println("Full Moon Notification Sent to Facebook:" );
//                                    System.out.println(facebook_moon_notification_Msg);
                                }
                                
                                else if ( days_Between_Now_Fullmoon == 5 )
                                {
                                    if (comparator.compare(fullMoon, maghrib_cal)>0)
                                    { 
                                        fullMoon_plus1.setTime(fullMoon.getTime() - 1 * 24 * 60 * 60 * 1000);
                                        String FullMoon_dow_ar = new SimpleDateFormat("' 'EEEE' '", new Locale("ar")).format(fullMoon_plus1);
                                        String FullMoon_dow_en = new SimpleDateFormat("EEEE").format(fullMoon_plus1);
                                        String temp_ar_text1 = "نذكركم و أنفسنا بفضل صيام الايام البيض من كل شهر التي تبدأ يوم";
                                        String temp_ar_text2 = "إن استطعت الصيام فصم و ذكر أحبابك. يرجى ملاحظة أن هذا يقوم على حسابات التقويم لاعلى ملاحظات رؤية الهلال";
                                        ar_moon_notification = temp_ar_text1 + FullMoon_dow_ar + temp_ar_text2;
                                        en_moon_notification = "We would like to remind our dear brothers & sisters that this month's \"White days\" will start this " + FullMoon_dow_en + ", it is recommended to fast these days. Pls note that this is based on calendar calculations not moon sighting observations";
                                        facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;    
                                        if (facebook_notification_enable)
                                        {
                                            try 
                                            {
                                                String pageID = page_ID +"/feed";
                                                facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", facebook_moon_notification_Msg));
                                                System.out.println("Full Moon Notification Sent to Facebook:" );
                                                System.out.println(facebook_moon_notification_Msg);
                                            }
                                            catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                            
                                        }
                                        
                                    }
                                    
                                    else
                                    {
                                        fullMoon_plus1.setTime(fullMoon.getTime() - 2 * 24 * 60 * 60 * 1000);
                                        String FullMoon_dow_ar = new SimpleDateFormat("' 'EEEE' '", new Locale("ar")).format(fullMoon_plus1);
                                        String FullMoon_dow_en = new SimpleDateFormat("EEEE").format(fullMoon_plus1);
                                        String temp_ar_text1 = "نذكركم و أنفسنا بفضل صيام الايام البيض من كل شهر التي تبدأ يوم";
                                        String temp_ar_text2 = "إن استطعت الصيام فصم و ذكر أحبابك. يرجى ملاحظة أن هذا يقوم على حسابات التقويم لاعلى ملاحظات رؤية الهلال";
                                        ar_moon_notification = temp_ar_text1 + FullMoon_dow_ar + temp_ar_text2;
                                        en_moon_notification = "We would like to remind our dear brothers & sisters that this month's \"White days\" will start this " + FullMoon_dow_en + ", it is recommended to fast these days. Pls note that this is based on calendar calculations not moon sighting observations";
                                        facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;                          
                                        System.out.println("Full Moon Notification:" );
                                        System.out.println(facebook_moon_notification_Msg);
                                    }
                                }
                                
                                else if ( days_Between_Now_Fullmoon == 3 )
                                {
                                    if (comparator.compare(fullMoon, maghrib_cal)>0)
                                    { 
                                        fullMoon_plus1.setTime(fullMoon.getTime() - 1 * 24 * 60 * 60 * 1000);
                                        String FullMoon_dow_ar = new SimpleDateFormat("' 'EEEE' '", new Locale("ar")).format(fullMoon_plus1);
                                        String FullMoon_dow_en = new SimpleDateFormat("EEEE").format(fullMoon_plus1);
                                        String temp_ar_text1 = "نذكركم و أنفسنا بفضل صيام الايام البيض من كل شهر التي تبدأ يوم";
                                        String temp_ar_text2 = "إن استطعت الصيام فصم و ذكر أحبابك. يرجى ملاحظة أن هذا يقوم على حسابات التقويم لاعلى ملاحظات رؤية الهلال";
                                        ar_moon_notification = temp_ar_text1 + FullMoon_dow_ar + temp_ar_text2;
                                        en_moon_notification = "We would like to remind our dear brothers & sisters that this month's \"White days\" will start this " + FullMoon_dow_en + ", it is recommended to fast these days. Pls note that this is based on calendar calculations not moon sighting observations";
                                        facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;    
                                    }
                                    
                                    else
                                    {
                                        String temp_ar_text1 = "نذكركم و أنفسنا بفضل صيام الايام البيض من كل شهر التي تبدأ غدا ";
                                        String temp_ar_text2 = "إن استطعت الصيام فصم و ذكر أحبابك. يرجى ملاحظة أن هذا يقوم على حسابات التقويم لاعلى ملاحظات رؤية الهلال";
                                        ar_moon_notification = temp_ar_text1 +  temp_ar_text2;
                                        en_moon_notification = "We would like to remind our dear brothers & sisters that this month's \"White days\" will start tomorrow, it is recommended to fast these days. Pls note that this is based on calendar calculations not moon sighting observations";
                                        facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;   
                                        if (facebook_notification_enable)
                                        {
                                            try 
                                            {
                                                String pageID = page_ID +"/feed";
                                                facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", facebook_moon_notification_Msg));
                                                System.out.println("Full Moon Notification Sent to Facebook:" );
                                                System.out.println(facebook_moon_notification_Msg);
                                            }
                                            catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                        }
                                        
                                    }
                                }

                                else if ( days_Between_Now_Fullmoon == 2 && comparator.compare(fullMoon, maghrib_cal)>0)
                                {
                                        String temp_ar_text1 = "نذكركم و أنفسنا بفضل صيام الايام البيض من كل شهر التي تبدأ غدا ";
                                        String temp_ar_text2 = "إن استطعت الصيام فصم و ذكر أحبابك. يرجى ملاحظة أن هذا يقوم على حسابات التقويم لاعلى ملاحظات رؤية الهلال";
                                        ar_moon_notification = temp_ar_text1 +  temp_ar_text2;
                                        System.out.println(ar_moon_notification);
                                        en_moon_notification = "We would like to remind our dear brothers & sisters that this month's \"White days\" will start tomorrow, it is recommended to fast these days. Pls note that this is based on calendar calculations not moon sighting observations";
                                        System.out.println(en_moon_notification);
                                        facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;    
                                        if (facebook_notification_enable)
                                        {
                                            try 
                                            {
                                                String pageID = page_ID +"/feed";
                                                facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", facebook_moon_notification_Msg));
                                                System.out.println("Full Moon Notification Sent to Facebook:" );
                                                System.out.println(facebook_moon_notification_Msg);
                                            }
                                            catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                        }
                                        
                                }
                                
                                else
                                {
                                    getHadith = true; 
                                    moon_hadith_Label_visible = false;
                                    hadith_Label_visible = true;
                                    System.out.println("moon else" );
                                }
                            }
                            
                            else if( days_Between_Now_Fullmoon > 5 || days_Between_Now_Fullmoon < 2 || dtIslamic.getMonthOfYear()!=1)
                            {
                                getHadith = true; 
//                                getFacebook = true;
                                //hide moon notification label boolean
                                moon_hadith_Label_visible = false;
                                //show hadith label boolean
                                hadith_Label_visible = true;
                            }
                            
                        }                      
                        
// Prayer time change notification/////////////////////put this in a thread, so error does not stop code further down ========================================================
// creates message to send to facebook
// creates labels for notification
                        
                        if (notification )
                        {
                            ar_notification_Msg_Lines = null;
//                            Calendar_now.setTime(future_prayer_date);
                            
                            Calendar prayertime_Change_Due_Date = null; 
                            prayertime_Change_Due_Date = Calendar.getInstance();
                            prayertime_Change_Due_Date.setTime(future_prayer_date);
                            
                            
                            
                            System.out.println ("Calendar_now: " + Calendar_now.getTime());
                            int day = prayertime_Change_Due_Date.get(Calendar.DAY_OF_MONTH);
                            String dayStr = day + suffixes[day];
                            String en_notification_date = new SimpleDateFormat("EEEE").format(future_prayer_date);
                            String en_notification_date1 = new SimpleDateFormat("' of ' MMMM").format(future_prayer_date);
                            
                            String ar_notification_date = new SimpleDateFormat(" EEEE d MMMM ", new Locale("ar")).format(future_prayer_date);               
                            labeconv = "إبتداءا من يوم " + ar_notification_date + "ستتغير اوقات الصلاة كالتالي  \n";
                            StringBuilder builder = new StringBuilder();
                            for(int i =0;i<labeconv.length();i++)
                            {
                                if(Character.isDigit(labeconv.charAt(i)))
                                {
                                    builder.append(arabicChars[(int)(labeconv.charAt(i))-48]);
                                }
                                else
                                {
                                    builder.append(labeconv.charAt(i));
                                }
                            }
                            ar_notification_Msg = builder.toString();
                    
                            en_notification_Msg = "Starting from " + en_notification_date + " the "  + dayStr  + en_notification_date1 + " the following prayer time(s) will change\n";
                            
                            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");
//                            en_notification_Msg = "Time change\nTime saving will be in effect as of *Sunday, November 03, 2013*\nAll prayer times will move back by one hour.\nJummah prayer will be at 1:00 PM";
                            if (fajr_jamma_time_change )
                            {
                                String future_fajr_jamaat_time_mod = DATE_FORMAT.format(future_fajr_jamaat_time);
//                                Date future_fajr_jamaat_time_mod = new SimpleDateFormat("HH:mm").parse("Fajr time: " + future_fajr_jamaat_time);
                                en_notification_Msg = en_notification_Msg + "Fajr: " + future_fajr_jamaat_time_mod +"    ";
                                ar_notification_Msg = ar_notification_Msg + "الفجر: " + future_fajr_jamaat_time_mod +"    ";
                                fajr_jamma_time_change = false;
                            }           
                            
                            
                            if(Calendar_now.compareTo(nextTransitionCal)==0 )
                            {
                                if (TimeZone.getTimeZone( timeZone_ID).inDaylightTime( time )){future_zuhr_jamaat_time = "12:30";}
                                else{future_zuhr_jamaat_time = "13:30";}  
                                en_notification_Msg = en_notification_Msg + "Duhr & Friday: " + future_zuhr_jamaat_time +"    ";
                                ar_notification_Msg = ar_notification_Msg + "الظهر و الجمعة: " + future_zuhr_jamaat_time +"    ";
                            }
                            
                            if (asr_jamma_time_change )
                            {
                                String future_asr_jamaat_time_mod = DATE_FORMAT.format(future_asr_jamaat_time);
//                                Date future_fajr_jamaat_time_mod = new SimpleDateFormat("HH:mm").parse("Fajr time: " + future_fajr_jamaat_time);
                                en_notification_Msg = en_notification_Msg + "Asr: " + future_asr_jamaat_time_mod +"    ";
                                ar_notification_Msg = ar_notification_Msg + "العصر: " + future_asr_jamaat_time_mod +"    ";
                                asr_jamma_time_change = false;
                            }
                            
                            if (isha_jamma_time_change )
                            {
                                String future_isha_jamaat_time_mod = DATE_FORMAT.format(future_isha_jamaat_time);
//                                Date future_fajr_jamaat_time_mod = new SimpleDateFormat("HH:mm").parse("Fajr time: " + future_fajr_jamaat_time);
                                en_notification_Msg = en_notification_Msg + "Isha: " + future_isha_jamaat_time_mod;
                                ar_notification_Msg = ar_notification_Msg + "العشاء: " + future_isha_jamaat_time_mod;
                                isha_jamma_time_change = false;
                            }
                            try
                            {
                                c = DBConnect.connect();
                                Statement st = (Statement) c.createStatement(); 
                                st.executeUpdate("UPDATE prayertime.notification SET en_message_String='" + en_notification_Msg + "' ORDER BY id DESC LIMIT 1");
                                st.executeUpdate("UPDATE prayertime.notification SET ar_message_String= '" + ar_notification_Msg + "' ORDER BY id DESC LIMIT 1");
                                c.close();
                                ar_notification_Msg_Lines = ar_notification_Msg.split("\\r?\\n");
                                en_notification_Msg_Lines = en_notification_Msg.split("\\r?\\n");
                            }
                            catch (Exception e){logger.warn("Unexpected error", e);}
                            
                            notification = false;
                            athan_Change_Label_visible = true;
                            getFacebook = false;
                            
                            notification_Msg = ar_notification_Msg_Lines[0] + "\n" + ar_notification_Msg_Lines[1] + "\n\n" + en_notification_Msg_Lines[0] + "\n" + en_notification_Msg_Lines[1];
                            System.out.println(notification_Msg );
                            
//                            Twitter twitter = TwitterFactory.getSingleton();
//                            Status status = null;
//                            try {status = twitter.updateStatus(notification_Msg);} 
//                            catch (TwitterException ex) {logger.warn("Unexpected error", e);}
//                            System.out.println("Successfully updated the status to [" + status.getText() + "].");
                            System.out.println("Notification Sent to Facebook" );
                            if (facebook_notification_enable)
                            {
                                try 
                                {
                                    String pageID = page_ID +"/feed";
                                    facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", notification_Msg));
                                }
                                catch (Exception e){logger.warn("Unexpected error", e);}
                            }
                            
                            try {p.sendMessage(en_notification_Msg);} 
                            catch (Exception e){{logger.warn("Unexpected error", e);}}
                        }        
                                
                        if (isStarting){isStarting = false;}

// Get Facebook Latest Post =================================================================================
                        if (getFacebook && facebook_Receive)
                        {
                            getFacebook = false;
                            facebook_Text_Post = false;
                            facebook_Picture_Post = false;
                            facebook_post = "";
//                            facebook_Post_Url = "";
                            facebook_Fan_Count = "";
                            Calendar facebook_created_time_calendar = null;
                            Calendar facebook_photo_created_time_calendar = null;
                            Calendar facebook_check_post_date = Calendar.getInstance();
                            facebook_check_post_date.add(Calendar.DAY_OF_MONTH, -6);
                            long facebook_check_post_Unix_Time = facebook_check_post_date.getTimeInMillis() / 1000;
//                            out.println(facebook_check_post_Unix_Time);
                            String query = "SELECT message,timeline_visibility, created_time   FROM stream WHERE source_id = " + page_ID + " AND message AND strlen(attachment.fb_object_type) < 1 AND type != 56 AND type = 46  AND strpos(message, \"prayer time(s)\") < 0 AND strpos(message, \"White days\") < 0 AND strpos(message, \"Hadith of the Day:\") < 0 AND created_time > " + facebook_check_post_Unix_Time + " LIMIT 1";
//                            String query = "{\"messages\":\"SELECT message,timeline_visibility, created_time   FROM stream WHERE source_id = " + page_ID + " AND message AND strlen(attachment.fb_object_type) < 1 AND type != 56 AND type = 46  AND strpos(message, \'prayer time(s)\') < 0 AND strpos(message, \'White days\') < 0 AND strpos(message, \'Hadith of the Day:\') < 0 AND created_time > " + facebook_check_post_Unix_Time + " LIMIT 1\" ,  \"count\": \"SELECT fan_count FROM page WHERE page_id = " + page_ID + "\"}";
//                            out.println(query);
                            try 
                            {
                                List<JsonObject> queryResults = facebookClient.executeFqlQuery(query, JsonObject.class);
                                
                                if(!queryResults.isEmpty()) 
                                {
                                    JsonObject facebookPost_J = queryResults.get(0);
                                    facebook_post = facebookPost_J.getString("message");

//                                    facebook_post = "Asalamualaikum,\n" + "We have been given a large printer/copier for administration at Daar Ibn\n Abbas. Is there any brothers available to pick it up from Lakemba? ";
                                    
                                    String[] lines = facebook_post.split("\r\n|\r|\n");

                                    if(null != facebook_post && !"".equals(facebook_post)) 
                                    {
                                        if(facebook_post.contains("\n\n"))
                                        {
                                            out.println("'/n/n' detected");
                                            facebook_post =facebook_post.replace("\n\n", "\n");
                                            out.println(facebook_post);
                                        }   
                                        facebook_created_time_calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone_ID));    
                                        facebook_created_time_calendar.setTimeInMillis(queryResults.get(0).getLong("created_time")* 1000);
//                                        out.print("Comment posted on:"); out.println(facebook_created_time_calendar.getTime());
                                        if(facebook_post.contains("tonight") || facebook_post.contains("today") && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(facebook_created_time_calendar)).getDays() != 0)
                                        {
                                            out.println("Facebook post contains either  the word 'today' or 'tonight' and has not been posted today");
                                            facebook_post = "";
                                            facebook_Label_visible = false;
                                        }
                                        
                                        else if (facebook_post.length() > 390 || lines.length > 6)
                                        {
                                            System.out.println("Facebook post is too large, it will not be posted");
                                            System.out.println("Facebook lines: " +lines.length);
                                            System.out.println("Facebook string length: " + facebook_post.length());
                                            facebook_post = "";
                                            facebook_Label_visible = false;
                                        }
                                        
                                        else
                                        {
                                            facebook_Text_Post = true;
                                            facebook_Label_visible = true; 
                                            facebook_Label_visible_set_once = true;
                                        }
                                    }
                                }
                                else{out.println("Facebook post is empty");}
                            }
                            catch (FacebookException e){logger.warn("Unexpected error", e);} 
                            catch (Exception e){logger.warn("Unexpected error", e);} 

                            query = "SELECT fan_count FROM page WHERE page_id = " + page_ID ;
                            try 
                            {
                                List<JsonObject> queryResults = facebookClient.executeFqlQuery(query, JsonObject.class);
                                facebook_Fan_Count = queryResults.get(0).getString("fan_count");
                                out.println("Page Likes: " + facebook_Fan_Count);
                                
                            }
                            catch (FacebookException e){logger.warn("Unexpected error", e);} 
                            catch (Exception e){logger.warn("Unexpected error", e);}  
   
                            query = "SELECT attachment.media.photo.images.src, created_time   FROM stream WHERE source_id = " + page_ID + "  AND type = 247 AND created_time > " + facebook_check_post_Unix_Time + " LIMIT 1";
                            try 
                            {
                                List<JsonObject> queryResults = facebookClient.executeFqlQuery(query, JsonObject.class);
                                if(!queryResults.isEmpty()) 
                                {
                                    if(null != facebook_Post_Url && !"".equals(facebook_Post_Url)){ old_facebook_Post_Url = new String(facebook_Post_Url);}
//                                    out.println(old_facebook_Post_Url);
                                    
                                    try 
                                    {facebook_Post_Url = queryResults.get(0).getJsonObject("attachment").getJsonArray("media").getJsonObject(0).getJsonObject("photo").getJsonArray("images").getJsonObject(1).getString("src");}
                                    catch (Exception e){logger.warn("facebook post url exception", e);} 
                                    
                                    out.println(facebook_Post_Url);

                                    facebook_photo_created_time_calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone_ID));    
                                    facebook_photo_created_time_calendar.setTimeInMillis(queryResults.get(0).getLong("created_time")* 1000);
                                    out.print("Comment posted on:"); out.println(facebook_photo_created_time_calendar.getTime());
                                    
                                    if(null != facebook_Post_Url && !"".equals(facebook_Post_Url) )
                                    {
                                        if(null != old_facebook_Post_Url && !"".equals(old_facebook_Post_Url))
                                        {
                                            if(facebook_Post_Url.equals(old_facebook_Post_Url))
                                            {out.print("Facebook photo post has not changed from previous fetch, nothing has been set");}
                                            
                                            if(!facebook_Post_Url.equals(old_facebook_Post_Url))
                                            {
                                                facebook_Picture_Post = true;
                                                facebook_Label_visible = true;
                                                facebook_Label_visible_set_once = true;
                                            }
                                        }
                                        
                                        else
                                        {
                                            facebook_Picture_Post = true;
                                            facebook_Label_visible = true;
                                            facebook_Label_visible_set_once = true;
                                        }
                                    }   
                                }
                                
                            }
                            catch (FacebookException e){logger.warn("Unexpected error", e);} 
                            catch (Exception e){logger.warn("Unexpected error", e);} 
                            
                            //compare text and picture post dates, if facebook_Picture_Post && facebook_Text_Post are true, and dates are not null
                            // which ever was posted last, clear facebook_post = ""; or facebook_Post_Url = "";
                            if(facebook_Picture_Post && facebook_Text_Post)
                            {
                                if (facebook_photo_created_time_calendar.before(facebook_created_time_calendar))
                                {
                                    facebook_Post_Url = "";
                                    
                                }
                                
                                else
                                {
                                    facebook_post = "";
                                    
                                }
                            }  
                        }
                        
// Get Daily Hadith =================================================================================
                        if (getHadith)
                        {
                            getHadith = false;
                            try
                            {
                                c = DBConnect.connect();
                                
                                if (dtIslamic.getMonthOfYear()==9 && dtIslamic.getDayOfMonth()<19){SQL ="select hadith, translated_hadith from hadith WHERE topic = 'fasting' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
//                                if (dtIslamic.getMonthOfYear()==9){SQL ="select hadith, translated_hadith from hadith WHERE ID = 2872";}
                                else if (dtIslamic.getMonthOfYear()==9 && dtIslamic.getDayOfMonth()>19){SQL ="select hadith, translated_hadith from hadith WHERE topic = 'Virtues of the Night of Qadr' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                                else if (dtIslamic.getMonthOfYear()==9 && dtIslamic.getDayOfMonth()>28){SQL ="select hadith, translated_hadith from hadith WHERE translated_hadith LIKE '%fitr %' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                                else if (dtIslamic.getMonthOfYear()==10 && dtIslamic.getDayOfMonth()==1){SQL ="select hadith, translated_hadith from hadith WHERE translated_hadith LIKE '%fitr %' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                                //SELECT * FROM hadith WHERE translated_hadith LIKE '%fitr %'
                                else if(dtIslamic.getMonthOfYear()==12){SQL ="select hadith, translated_hadith from hadith WHERE topic = 'Hajj (Pilgrimage)' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                                else if (dayofweek_int == 6){SQL = "select hadith, translated_hadith from hadith WHERE day = '5' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                                
                                else if (dtIslamic.getMonthOfYear()==1 && dtIslamic.getDayOfMonth()>7 && dtIslamic.getDayOfMonth()<12 ){SQL = "select hadith, translated_hadith from hadith WHERE (translated_hadith LIKE '%Ashura%') and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                          
                                
                                else 
                                {
                                    SQL = "select * from hadith WHERE day = '0' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";
//                                    SQL = "select * from hadith where  length(translated_hadith)>527"; // the bigest Hadith
                                }
                                rs = c.createStatement().executeQuery(SQL);
                                while (rs.next()) 
                                {
                                    hadith = rs.getString("hadith");
                                    translated_hadith = rs.getString("translated_hadith");

                                }
                                c.close();
                                System.out.println("arabic hadith length" + hadith.length());
                                System.out.println(" english hadith length" + translated_hadith.length());
                                // 528 length should be the max allowed for the hadith in english, generally arabic hadith  is smaller than english translation
                                facebook_hadith = "Hadith of the Day:\n\n"+ hadith +"\n\n" + translated_hadith;
                            
                                // check if a notification has already been sent, to avoid flooding users with notifications, i.e during system restarts
                                c = DBConnect.connect();
                                SQL = "Select * from facebook_hadith_notification where id = (select max(id) from facebook_hadith_notification)";
                                rs = c.createStatement().executeQuery(SQL);
                                while (rs.next()) 
                                {
                                    id =                rs.getInt("id");
                                    hadith_notification_Date = rs.getDate("notification_date");
                                }
                                c.close();
                                hadith_notification_Date_cal = Calendar.getInstance();
                                hadith_notification_Date_cal.setTime(hadith_notification_Date);
                                hadith_notification_Date_cal.set(Calendar.MILLISECOND, 0);
                                hadith_notification_Date_cal.set(Calendar.SECOND, 0);

//                                System.out.println(hadith_notification_Date_cal.getTime());
                                System.out.println(Calendar_now.getTime());
                                if (Calendar_now.compareTo(hadith_notification_Date_cal) == 0 )  
                                {
                                    System.out.println("hadith has already been posted today to Facebook");
                                }

                                if (Calendar_now.compareTo(hadith_notification_Date_cal) != 0 )  
                                {
                                    try 
                                    {

                                        if (facebook_notification_enable)
                                        {
                                            try 
                                            {
                                                String pageID = page_ID +"/feed";
                                                facebookClient.publish(pageID, FacebookType.class, Parameter.with("message", facebook_hadith));
                                            }
                                            catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                        }

                                        c = DBConnect.connect();
                                        PreparedStatement ps = c.prepareStatement("INSERT INTO prayertime.facebook_hadith_notification (notification_date) VALUE (?)");                                      
                                        java.sql.Timestamp mysqldate = new java.sql.Timestamp(new java.util.Date().getTime());
                                        ps.setTimestamp(1, mysqldate);   
                                        ps.executeUpdate(); 
                                        c.close();
                                        System.out.println("hadith posted to Facebook: \n" + facebook_hadith );
                                    }
                                    catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                    catch (Exception e){logger.warn("Unexpected error", e);}
                                }
                            }
                            catch (Exception e){logger.warn("Unexpected error", e);}

                        }
                     } 

                catch(SQLException e)
                {
                    System.out.println("Error on Database connection");
                    logger.warn("Unexpected error", e);
                }
                catch (ParseException e){logger.warn("Unexpected error", e);} 
                catch (Exception e){logger.warn("Unexpected error", e);}
                
             
            }
        }, 0, 3600000);   
//        }, 0, 120000);        
        
// Timer to traslate labels from arabic to english on the screen====================================================
        
//        translate_lastTimerCall = System.nanoTime();
        translate_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > translate_lastTimerCall + 40000_000_000l) 
                {
                    try {update_labels();} 
                    catch (Exception e) {logger.warn("Unexpected error", e);}
                    translate_lastTimerCall = now;
                }
            }
        };
 
// Timer to update clock====================================================
        
//        translate_lastTimerCall = System.nanoTime();
        clock_update_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > clock_update_lastTimerCall + 1000_000_000l) 
                {
                    try {update_clock();} 
                    catch (Exception e) {logger.warn("Unexpected error", e);}
                    clock_update_lastTimerCall = now;
                }
            }
        };       

// PIR sensor thread to turn on/Off TV screen to save energy ===============================================================        
        new Thread(() -> 
        {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                java.util.logging.Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
//            }
            final GpioController gpioSensor = GpioFactory.getInstance();
             sensor_lastTimerCall =  System.nanoTime(); 
             final GpioPinDigitalInput sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
             
             sensor.addListener(new GpioPinListenerDigital() 
             {
                 @Override
                 public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) 
                 {

                     if (event.getState().isHigh()) 
                     {
                         sensor_lastTimerCall = System.nanoTime(); 
                         if(!hdmiOn && !pir_disactive_startup)
                         {
                             if (!prayer_In_Progress)
                             {
                                ProcessBuilder processBuilder1 = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
                                hdmiOn = true;
                                startup = false;
                                System.out.println("Tv turned on");
                                try {Thread.sleep(2500);} catch (InterruptedException e) {logger.warn("Unexpected error", e);}
                                try {Process process1 = processBuilder1.start();}
                                catch (IOException e) {logger.warn("Unexpected error", e);} 

                             }

                             if (prayer_In_Progress)
                             {
                                
                                Calendar cal = Calendar.getInstance();
                                int hour_Now_int = cal.get(Calendar.HOUR_OF_DAY); 
                                int hourbefore_fajr_int = fajr_cal.get(Calendar.HOUR_OF_DAY) -1; 
                                hourbefore_fajr_int = hourbefore_fajr_int -1; 
//                                System.out.println("hour now is" + hour_Now_int);
//                                System.out.println("fajr hour -1 hour is" + hourbefore_fajr_int);
                                
                                
                                
                                if(hour_Now_int >=0 && hour_Now_int<=hourbefore_fajr_int)
                                {
                                    
                                    
                                    System.out.println("prayer detected in after hours");
                                    if (System.nanoTime() > proximity_lastTimerCall + delay_turnOnTV_after_Prayers_nightmode )
                                    {
    //                                    System.out.println(proximity_lastTimerCall + delay_turnOnTV_after_Prayers_nightmode);
    //                                    System.out.println(System.nanoTime());
                                        ProcessBuilder processBuilder1 = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
                                        hdmiOn = true;
                                        prayer_In_Progress = false;
                                        System.out.println("Tv turned on");
                                        try {Thread.sleep(2500);} catch (InterruptedException e) {logger.warn("Unexpected error", e);}
                                        try {Process process1 = processBuilder1.start();}
                                        catch (IOException e) {logger.warn("Unexpected error", e);} 
                                    }
                                }
                                
                                else
                                {
                                    System.out.println("prayer detected during normal hours");
                                    
                                    if (fajr_prayer_In_Progress_notification && cal.after(fajr_jamaat_cal) && cal.before(fajr_jamaat_update_cal))
                                    {
                                        fajr_prayer_In_Progress_notification = false;
                                        Pushover p = new Pushover("WHq3q48zEFpTqU47Wxygr3VMqoodxc", "skhELgtWRXslAUrYx9yp1s0Os89JTF");
                                        try {p.sendMessage("Fajr Jamaa at Daar Ibn Abass has just started"); System.out.println("Prayer in progress notification sent");} catch (IOException e){e.printStackTrace();}
                                        send_Broadcast_msg = true;
                                        broadcast_msg = "Fajr Jamaa at Daar Ibn Abass has just started";
                                    }
                                    if (zuhr_prayer_In_Progress_notification && cal.after(zuhr_jamaat_cal) && cal.before(zuhr_plus15_cal))
                                    {
                                        zuhr_prayer_In_Progress_notification = false;
                                        Pushover p = new Pushover("WHq3q48zEFpTqU47Wxygr3VMqoodxc", "skhELgtWRXslAUrYx9yp1s0Os89JTF");
                                        try {p.sendMessage("Zuhr Jamaa at Daar Ibn Abass has just started"); System.out.println("Prayer in progress notification sent");} catch (IOException e){e.printStackTrace();}
                                        send_Broadcast_msg = true;
                                        broadcast_msg = "Zuhr Jamaa at Daar Ibn Abass has just started";
                                    }
                                    if (asr_prayer_In_Progress_notification && cal.after(asr_jamaat_cal) && cal.before(asr_jamaat_update_cal))
                                    {
                                        asr_prayer_In_Progress_notification = false;
                                        Pushover p = new Pushover("WHq3q48zEFpTqU47Wxygr3VMqoodxc", "skhELgtWRXslAUrYx9yp1s0Os89JTF");
                                        try {p.sendMessage("Asr Jamaa at Daar Ibn Abass has just started"); System.out.println("Prayer in progress notification sent");} catch (IOException e){e.printStackTrace();}
                                        send_Broadcast_msg = true;
                                        broadcast_msg = "Asr Jamaa at Daar Ibn Abass has just started";
                                    }
                                    
                                    
                                    if (maghrib_prayer_In_Progress_notification && cal.after(maghrib_cal) && cal.before(maghrib_plus15_cal))
                                    {
                                        maghrib_prayer_In_Progress_notification = false;
                                        Pushover p = new Pushover("WHq3q48zEFpTqU47Wxygr3VMqoodxc", "skhELgtWRXslAUrYx9yp1s0Os89JTF");
                                        try {p.sendMessage("Maghrib Jamaa at Daar Ibn Abass has just started"); System.out.println("Prayer in progress notification sent");} catch (IOException e){e.printStackTrace();}
                                        send_Broadcast_msg = true;
                                        broadcast_msg = "Maghrib Jamaa at Daar Ibn Abass has just started";
                                    }
                                    
                                    if (isha_prayer_In_Progress_notification && cal.after(isha_jamaat_cal) && cal.before(isha_jamaat_update_cal))
                                    {
                                        isha_prayer_In_Progress_notification = false;
                                        Pushover p = new Pushover("WHq3q48zEFpTqU47Wxygr3VMqoodxc", "skhELgtWRXslAUrYx9yp1s0Os89JTF");
                                        try {p.sendMessage("Isha Jamaa at Daar Ibn Abass has just started"); System.out.println("Prayer in progress notification sent");} catch (IOException e){e.printStackTrace();}
                                        send_Broadcast_msg = true;
                                        broadcast_msg = "Isha Jamaa at Daar Ibn Abass has just started";
                                    }

                                    if (System.nanoTime() > proximity_lastTimerCall + delay_turnOnTV_after_Prayers )
                                    {
    //                                    System.out.println(proximity_lastTimerCall + delay_turnOnTV_after_Prayers);
    //                                    System.out.println(System.nanoTime());
                                        ProcessBuilder processBuilder1 = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
                                        hdmiOn = true;
                                        prayer_In_Progress = false;
                                        System.out.println("Tv turned on");
                                        try {Thread.sleep(2500);} catch (InterruptedException e) {logger.warn("Unexpected error", e);}
                                        try {Process process1 = processBuilder1.start();}catch (IOException e) {logger.warn("Unexpected error", e);}    
                                    }
                                    
                                    if(send_Broadcast_msg)
                                    {
                                        try
                                        {
                                            send_Broadcast_msg = false;
                                            socket1 = new DatagramSocket(null);
                                            socket1.setBroadcast(true);
                                            buf1 = broadcast_msg.getBytes();
                                            group = InetAddress.getByName("255.255.255.255");
                                            packet1 = new DatagramPacket(buf1, buf1.length, group, 8888);
                                            socket1.send(packet1);
                                        }
                                        catch(Exception e){logger.warn("Unexpected error", e);}
                                    }
                                
                                
                                }
 
                             }
                         }
                     }
                     
                     if(event.getState().isLow()){sensorLow = true;}
                 }
             });
             
             System.out.println(" ... Motion Detection Starting.....");

             for (;;) 
             {
                 try 
                 {
                     
                     if (System.nanoTime() > sensor_lastTimerCall + delay_turnOffTV_after_inactivity && sensorLow && hdmiOn || startup) 
                     {
                         startup = false;
                         System.out.println("All is quiet...");
                         ProcessBuilder processBuilder2 = new ProcessBuilder("bash", "-c", "echo \"standby 0000\" | cec-client -d 1 -s \"standby 0\" RPI");
                         hdmiOn = false;
                         try {Process process2 = processBuilder2.start();}
                         catch (IOException e) {logger.warn("Unexpected error", e);}
                         sensor_lastTimerCall = System.nanoTime();
                         sensorLow = false;
                     }
                     Thread.sleep(1000);
                 }
                 catch(InterruptedException e) 
                 {
                     gpioSensor.shutdown();
                     Thread.currentThread().interrupt();
                 }
             }

         }).start();

        
        
        
// //Infrared sensor thread to turn on/Off TV screen when Prayer starts ===============================================================        
        new Thread(() -> 
        {

             System.out.println(" ... Prayer Detection Starting.....");            
             for (;;) 
             {                 
                try 
                {
                    DatagramSocket socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
                    socket.setBroadcast(true);
                    byte[] buf = new byte[512];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    while (true) 
                    {
                        
                        socket.receive(packet);
                        String received = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("UDP Packet received: " + received);
                        proximity_lastTimerCall = System.nanoTime();
                        
                        if(received.equals("Prayer in progress") && pir_sensor) 
                        {
                            ProcessBuilder processBuilder2 = new ProcessBuilder("bash", "-c", "echo \"standby 0000\" | cec-client -d 1 -s \"standby 0\" RPI");
                            try 
                            {
                                if (hdmiOn)
                                {
                                    Process process2 = processBuilder2.start(); 
                                    System.out.println("Prayer in Progress...Turning Off TV(s)");
                                    Thread.sleep(1000);
                                    hdmiOn = false;
                                    prayer_In_Progress = true;
                                    proximity_lastTimerCall = System.nanoTime();
                                    
                                }
                            }
                            catch (IOException e) {logger.warn("Unexpected error", e);}
                            
                        }
                        
                        else if(received.equals("refresh hadith")) 
                        {
                            
                            System.out.println("Getting Hadith...");
                            
                            try
                            {
                                c = DBConnect.connect();
                                
                                if (dtIslamic.getMonthOfYear()==9){SQL ="select hadith, translated_hadith from hadith WHERE topic = 'fasting' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
//                                if (dtIslamic.getMonthOfYear()==9){SQL ="select hadith, translated_hadith from hadith WHERE ID = 2872";}
                                
                                else if(dtIslamic.getMonthOfYear()==12){SQL ="select hadith, translated_hadith from hadith WHERE topic = 'Hajj (Pilgrimage)' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                                else if (dayofweek_int == 5 || dayofweek_int == 6){SQL = "select hadith, translated_hadith from hadith WHERE day = '5' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";}
                                else 
                                {
                                    SQL = "select * from hadith WHERE day = '0' and length(translated_hadith)<"+ max_en_hadith_len + " and length(hadith)<" + max_ar_hadith_len + " ORDER BY RAND( ) LIMIT 1";
//                                    SQL = "select * from hadith where  length(translated_hadith)>527"; // the bigest Hadith
                                }
                                rs = c.createStatement().executeQuery(SQL);
                                while (rs.next()) 
                                {
                                    hadith = rs.getString("hadith");
                                    translated_hadith = rs.getString("translated_hadith");

                                }
                                c.close();
                                System.out.println("arabic hadith length" + hadith.length());
                                System.out.println(" english hadith length" + translated_hadith.length());
                                
                            }
                            catch (Exception e){logger.warn("Unexpected error", e);}
                            
                        }
                        
                        
                        
                        if(received.equals("prayer call")) 
                        {
                            ProcessBuilder processBuilder_Athan = new ProcessBuilder("bash", "-c", "mpg123 /home/pi/prayertime/Audio/athan1.mp3");

                            try {Process process = processBuilder_Athan.start();} 
                            catch (IOException e) {logger.warn("Unexpected error", e);}
                        }
                        
                        if(received.equals("snapshot")) 
                        {
                            System.out.println("saving...");
//                            WritableImage snapshot_prayertime_pane = prayertime_pane.getScene().snapshot(null);
//                            WritableImage snapshot_full = scene.snapshot(null);
//                            ImageIO.write(SwingFXUtils.fromFXImage(snapshot_full, null), "png", file);
                            
                            WritableImage image = new WritableImage(400, 400);
                            scene.snapshot(image);
                                File outputfile = new File("saved.png");
                                ImageIO.write((RenderedImage) image, "png", outputfile);
                            

                            
                            
                            System.out.println("saved...");
                        }
                        
                        if(received.equals("refresh facebook")) 
                        {
                            if (facebook_Receive)
                            {
                                getFacebook = false;
                                facebook_Text_Post = false;
                                facebook_Picture_Post = false;
                                facebook_post = "";
    //                            facebook_Post_Url = "";
                                facebook_Fan_Count = "";
                                Calendar facebook_created_time_calendar = null;
                                Calendar facebook_photo_created_time_calendar = null;
                                Calendar facebook_check_post_date = Calendar.getInstance();
                                facebook_check_post_date.add(Calendar.DAY_OF_MONTH, -6);
                                long facebook_check_post_Unix_Time = facebook_check_post_date.getTimeInMillis() / 1000;
    //                            out.println(facebook_check_post_Unix_Time);
                                String query = "SELECT message,timeline_visibility, created_time   FROM stream WHERE source_id = " + page_ID + " AND message AND strlen(attachment.fb_object_type) < 1 AND type != 56 AND type = 46  AND strpos(message, \"prayer time(s)\") < 0 AND strpos(message, \"White days\") < 0 AND strpos(message, \"Hadith of the Day:\") < 0 AND created_time > " + facebook_check_post_Unix_Time + " LIMIT 1";
    //                            String query = "{\"messages\":\"SELECT message,timeline_visibility, created_time   FROM stream WHERE source_id = " + page_ID + " AND message AND strlen(attachment.fb_object_type) < 1 AND type != 56 AND type = 46  AND strpos(message, \'prayer time(s)\') < 0 AND strpos(message, \'White days\') < 0 AND strpos(message, \'Hadith of the Day:\') < 0 AND created_time > " + facebook_check_post_Unix_Time + " LIMIT 1\" ,  \"count\": \"SELECT fan_count FROM page WHERE page_id = " + page_ID + "\"}";
    //                            out.println(query);
                                try 
                                {
                                    List<JsonObject> queryResults = facebookClient.executeFqlQuery(query, JsonObject.class);

                                    if(!queryResults.isEmpty()) 
                                    {
                                        JsonObject facebookPost_J = queryResults.get(0);
                                        facebook_post = facebookPost_J.getString("message");

    //                                    facebook_post = "Asalamualaikum,\n" + "We have been given a large printer/copier for administration at Daar Ibn\n Abbas. Is there any brothers available to pick it up from Lakemba? ";

                                        String[] lines = facebook_post.split("\r\n|\r|\n");

                                        if(null != facebook_post && !"".equals(facebook_post)) 
                                        {
                                            if(facebook_post.contains("\n\n"))
                                            {
                                                out.println("'/n/n' detected");
                                                facebook_post =facebook_post.replace("\n\n", "\n");
                                                out.println(facebook_post);
                                            }   
                                            facebook_created_time_calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone_ID));    
                                            facebook_created_time_calendar.setTimeInMillis(queryResults.get(0).getLong("created_time")* 1000);
    //                                        out.print("Comment posted on:"); out.println(facebook_created_time_calendar.getTime());
                                            if(facebook_post.contains("tonight") || facebook_post.contains("today") && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(facebook_created_time_calendar)).getDays() != 0)
                                            {
                                                out.println("Facebook post contains either  the word 'today' or 'tonight' and has not been posted today");
                                                facebook_post = "";
                                                facebook_Label_visible = false;
                                            }

                                            else if (facebook_post.length() > 390 || lines.length > 6)
                                            {
                                                System.out.println("Facebook post is too large, it will not be posted");
                                                System.out.println("Facebook lines: " +lines.length);
                                                System.out.println("Facebook string length: " + facebook_post.length());
                                                facebook_post = "";
                                                facebook_Label_visible = false;
                                            }

                                            else
                                            {
                                                facebook_Text_Post = true;
                                                facebook_Label_visible = true; 
                                                facebook_Label_visible_set_once = true;
                                            }
                                        }
                                    }
                                    else{out.println("Facebook post is empty");}
                                }
                                catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                catch (Exception e){logger.warn("Unexpected error", e);} 

                                query = "SELECT fan_count FROM page WHERE page_id = " + page_ID ;
                                try 
                                {
                                    List<JsonObject> queryResults = facebookClient.executeFqlQuery(query, JsonObject.class);
                                    facebook_Fan_Count = queryResults.get(0).getString("fan_count");
                                    out.println("Page Likes: " + facebook_Fan_Count);

                                }
                                catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                catch (Exception e){logger.warn("Unexpected error", e);}  

                                query = "SELECT attachment.media.photo.images.src, created_time   FROM stream WHERE source_id = " + page_ID + "  AND type = 247 AND created_time > " + facebook_check_post_Unix_Time + " LIMIT 1";
                                try 
                                {
                                    List<JsonObject> queryResults = facebookClient.executeFqlQuery(query, JsonObject.class);
                                    if(!queryResults.isEmpty()) 
                                    {
                                        if(null != facebook_Post_Url && !"".equals(facebook_Post_Url)){ old_facebook_Post_Url = new String(facebook_Post_Url);}
    //                                    out.println(old_facebook_Post_Url);

                                        facebook_Post_Url = queryResults.get(0).getJsonObject("attachment").getJsonArray("media").getJsonObject(0).getJsonObject("photo").getJsonArray("images").getJsonObject(1).getString("src");
    //                                    out.println(facebook_Post_Url);

                                        facebook_photo_created_time_calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone_ID));    
                                        facebook_photo_created_time_calendar.setTimeInMillis(queryResults.get(0).getLong("created_time")* 1000);
                                        out.print("Comment posted on:"); out.println(facebook_photo_created_time_calendar.getTime());

                                        if(null != facebook_Post_Url && !"".equals(facebook_Post_Url) )
                                        {
                                            if(null != old_facebook_Post_Url && !"".equals(old_facebook_Post_Url))
                                            {
                                                if(facebook_Post_Url.equals(old_facebook_Post_Url))
                                                {out.print("Facebook photo post has not changed from previous fetch, nothing has been set");}

                                                if(!facebook_Post_Url.equals(old_facebook_Post_Url))
                                                {
                                                    facebook_Picture_Post = true;
                                                    facebook_Label_visible = true;
                                                    facebook_Label_visible_set_once = true;
                                                }
                                            }

                                            else
                                            {
                                                facebook_Picture_Post = true;
                                                facebook_Label_visible = true;
                                                facebook_Label_visible_set_once = true;
                                            }
                                        }   
                                    }

                                }
                                catch (FacebookException e){logger.warn("Unexpected error", e);} 
                                catch (Exception e){logger.warn("Unexpected error", e);} 

                                //compare text and picture post dates, if facebook_Picture_Post && facebook_Text_Post are true, and dates are not null
                                // which ever was posted last, clear facebook_post = ""; or facebook_Post_Url = "";
                                if(facebook_Picture_Post && facebook_Text_Post)
                                {
                                    if (facebook_photo_created_time_calendar.before(facebook_created_time_calendar))
                                    {
                                        facebook_Post_Url = "";

                                    }

                                    else
                                    {
                                        facebook_post = "";

                                    }
                                }  
                            }
                        }
                        
                    }
                }

//                 catch(InterruptedException e){Thread.currentThread().interrupt();}
                 catch (Exception e){logger.warn("Unexpected error", e); Thread.currentThread().interrupt();}
                 
             }

         }).start();
        
        
        
        
    }
//===============================================================================================================================================
    
    @Override public void start(Stage stage) {
        
        Pane root = new Pane();
        // rotate tv screen to portrait mode
        // edit the /boot/config.txt file Copy stored in documentation folder (i.e. ramebuffer_width=1080   framebuffer_height=1920  display_rotate=1...)
        scene = new Scene(root, 1080, 1920);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
                
        Mainpane = new GridPane();
        try
        {
        String image = new File(rand_Image_Path).toURI().toURL().toString();
        System.out.println("image string: " + image);
//        String image = JavaFXApplication4.class.getResource(rand_Image_Path).toExternalForm();
//        Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: repeat; ");  
        Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-image-repeat: repeat; -fx-background-size: 1080 1920;-fx-background-position: bottom left;");  
        
        }
        catch (IOException e) {logger.warn("Unexpected error", e);}
        
        
        
     
        
        
        Mainpane.getColumnConstraints().setAll(
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/13.0).build()       
        );
        Mainpane.getRowConstraints().setAll(
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/24.0).build()
        );
//        Mainpane.setGridLinesVisible(true);
        Mainpane.setId("Mainpane");
        prayertime_pane = prayertime_pane();    
        Moonpane =   moonpane();
        hadithPane = hadithPane();
        clockPane =   clockPane();
        GridPane footerPane =   footerPane();
         
  //============================================
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(10.0);
        ds.setOffsetX(10.0);
        ds.setColor(Color.BLACK);

//        clock.setEffect(ds);
        Moonpane.setEffect(ds);
        prayertime_pane.setEffect(ds);
        hadithPane.setEffect(ds);
        clockPane.setEffect(ds);
        footerPane.setEffect(ds);
  //============================================
        Mainpane.add(clockPane, 1, 1);
        Mainpane.add(Moonpane, 7, 1);
        Mainpane.add(prayertime_pane, 1, 5,11,7);  
        Mainpane.add(hadithPane, 1, 15,11,13);
        prayertime_pane.setTranslateY(30);
//        hadithPane.setTranslateY(0);
        Mainpane.add(footerPane, 1, 20,11,1);
        footerPane.setTranslateY(514);
//        Mainpane.setCache(true);
        scene.setRoot(Mainpane);
        stage.show();
        translate_timer.start(); 
        clock_update_timer.start();
        
//        For debuuging purposes only
//                new Thread()
//        {
//            @Override
//            public void run() 
//            {
//                for (;;) 
//                {
//                    try 
//                    {
//                       Thread.sleep(360000);
//                        moon_hadith_Label_visible = false;
//                                //show hadith label boolean
//                                hadith_Label_visible = true;
//                       
//                    }
//                    catch (InterruptedException ex) 
//                    {
//                        logger.warn("Unexpected error", e);
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            }
//        }.start();
    }

public static void main(String[] args) {
    launch(args);
    System.exit(0);
}

public void update_clock() throws Exception{  
    
    
        DateTime_now = new DateTime();    
        Calendar_now = Calendar.getInstance();
        Calendar_now.setTime(new Date());
        date_now = new Date();
//        Calendar_now.set(Calendar.MILLISECOND, 0);
//        Calendar_now.set(Calendar.SECOND, 0);
        
        hour_in_hour_Label = new SimpleDateFormat("hh").format(Calendar_now.getTime());
        minute_in_minute_Label = new SimpleDateFormat(":mm").format(Calendar_now.getTime());
        clock_minute = Calendar_now.get(Calendar.MINUTE);
        
        if(clock_minute != old_clock_minute)
        {
            old_clock_minute = clock_minute;
            hour_Label.setText(hour_in_hour_Label);
            minute_Label.setText(minute_in_minute_Label);
            date = new SimpleDateFormat("EEEE, d MMMM").format(Calendar_now.getTime());
            date_Label.setText(date);
            
        }
        
//       SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//	String dateInString = "21-07-2014 22:05:56";
//	Date date2 = sdf.parse(dateInString);
//        fajr_diffsec = (int) ((date2.getTime() - date_now.getTime() ) / (1000));
        
        
        fajr_diffsec = (int) ((fajr_begins_time.getTime() - date_now.getTime() ) / (1000));
        
//        System.out.println("difference between seconds: " + fajr_diffsec); 

        if(abs(fajr_diffsec) < 61) //fajr_begins_time
        {
            count_down = true;
            fajr_diffsec_dec = fajr_diffsec/10;
            fajr_diffsec_sin = fajr_diffsec - fajr_diffsec_dec*10;
            
            fajr_hourLeft.setId("hourLeft-countdown");
            fajr_hourRight.setId("hourLeft-countdown");
            fajr_minLeft.setId("hourLeft-countdown");
            fajr_minRight.setId("hourLeft-countdown");
            
            fajr_hourLeft.setText("-");
            fajr_hourRight.setText("0");
            fajr_minLeft.setText(Integer.toString(fajr_diffsec_dec));
            fajr_minRight.setText(Integer.toString(fajr_diffsec_sin));
        }
        if(fajr_diffsec < 0 && count_down) //fajr_begins_time
        {
            count_down = false;
//            dateFormat = new SimpleDateFormat("hh:mm");
            fajrdate = fajr_cal.getTime();
            formattedDateTime = dateFormat.format(fajrdate);
            
            fajr_hourLeft.setId("hourLeft");
            fajr_hourRight.setId("hourLeft");
            fajr_minLeft.setId("hourLeft");
            fajr_minRight.setId("hourLeft");
            
            fajr_hourLeft.setText(formattedDateTime.substring(0, 1));
            fajr_hourRight.setText(formattedDateTime.substring(1, 2));
            fajr_minLeft.setText(formattedDateTime.substring(3, 4));
            fajr_minRight.setText(formattedDateTime.substring(4, 5));
        }
        
//        maghrib_diffsec = (int) ((date2.getTime() - date_now.getTime() ) / (1000));
        
        maghrib_diffsec = (int) ((maghrib_begins_time.getTime() - date_now.getTime() ) / (1000));
//        System.out.println("difference between seconds: " + maghrib_diffsec); 

        if(abs(maghrib_diffsec) < 61) //fajr_begins_time
        {
            count_down = true;
            maghrib_diffsec_dec = maghrib_diffsec/10;
            maghrib_diffsec_sin = maghrib_diffsec - maghrib_diffsec_dec*10;

            maghrib_hourLeft.setId("hourLeft-countdown");
            maghrib_hourRight.setId("hourLeft-countdown");
            maghrib_minLeft.setId("hourLeft-countdown");
            maghrib_minRight.setId("hourLeft-countdown");
            
            maghrib_hourLeft.setText("-");
            maghrib_hourRight.setText("0");
            maghrib_minLeft.setText(Integer.toString(maghrib_diffsec_dec));
            maghrib_minRight.setText(Integer.toString(maghrib_diffsec_sin));
        }
        
        if(maghrib_diffsec < 0 && count_down) //fajr_begins_time
        {
            count_down = false;

            maghribdate = maghrib_cal.getTime();
            formattedDateTime = dateFormat.format(maghribdate);
            
            maghrib_hourLeft.setId("hourLeft");
            maghrib_hourRight.setId("hourLeft");
            maghrib_minLeft.setId("hourLeft");
            maghrib_minRight.setId("hourLeft");
                
            maghrib_hourLeft.setText(formattedDateTime.substring(0, 1));
            maghrib_hourRight.setText(formattedDateTime.substring(1, 2));
            maghrib_minLeft.setText(formattedDateTime.substring(3, 4));
            maghrib_minRight.setText(formattedDateTime.substring(4, 5));
        }
        
//        System.out.println("abs difference between seconds: " + abs(maghrib_diffsec));
        
        //==prayer alarms =================================================================================
       
//        URL url = this.getClass().getClassLoader().getResource("Audio/athan1.wav");
//        AudioFormat adFormat = getAudioFormat();
//        Clip clip = AudioSystem.getClip();
//        AudioInputStream ais = AudioSystem.getAudioInputStream( url );
        
        
        ProcessBuilder processBuilder_Athan = new ProcessBuilder("bash", "-c", "mpg123 /home/pi/prayertime/Audio/athan1.mp3");
        ProcessBuilder processBuilder_Duha = new ProcessBuilder("bash", "-c", "mpg123 /home/pi/prayertime/Audio/duha.mp3");

        
//                            try {
//                                Process process = processBuilder.start();                                
//                            } catch (IOException ex) {
//                                logger.warn("Unexpected error", e);
//                            }
                            
        
        
//        URL url = this.getClass().getClassLoader().getResource("Audio/athan1.wav");
//        AudioInputStream ais = AudioSystem.getAudioInputStream(url); 
//        AudioFormat littleEndianFormat = getAudioFormat();
//        AudioInputStream converted = AudioSystem.getAudioInputStream(littleEndianFormat, ais); 
//        Clip clip = AudioSystem.getClip();
        
        ProcessBuilder processBuilder_Tvon = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
//clip.open(converted);
//            clip.start();
        Calendar_now.set(Calendar.MILLISECOND, 0);
        Calendar_now.set(Calendar.SECOND, 0);
        
        if (duha_cal.equals(Calendar_now) && duha_athan_enable) 
        {
            duha_athan_enable = false;
            System.out.println("Duha Time");
//            String image = JavaFXApplication4.class.getResource("/Images/sunrise.png").toExternalForm();
//            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-image-repeat: repeat; -fx-background-size: 1080 1920;-fx-background-position: bottom left;");
//            sensor_lastTimerCall = System.nanoTime();
//            sensorLow = true;
            try {Process process = processBuilder_Tvon.start(); hdmiOn = true;} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Duha.start();} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
        }

        else if (fajr_cal.equals(Calendar_now) && fajr_athan_enable) 
        {
            fajr_prayer_In_Progress_notification = true;
            fajr_athan_enable = false;
            System.out.println("fajr Time");
//            clip.open(converted);
//            clip.start();
            
            sensor_lastTimerCall = System.nanoTime();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start(); hdmiOn = true;} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
        }
        
        else if (zuhr_cal.equals(Calendar_now) && zuhr_athan_enable) 
        {
            zuhr_prayer_In_Progress_notification = true;
            zuhr_athan_enable = false;
            System.out.println("zuhr Time");
//            clip.open(converted);
//            clip.start();
            sensor_lastTimerCall = System.nanoTime();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start(); hdmiOn = true;} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
        }        

        else if (asr_cal.equals(Calendar_now) && asr_athan_enable) 
        {
            asr_prayer_In_Progress_notification = true;
            asr_athan_enable = false;
            System.out.println("asr Time");
            sensor_lastTimerCall = System.nanoTime();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start(); hdmiOn = true;} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
        } 
        
        else if (maghrib_cal.equals(Calendar_now) && maghrib_athan_enable) 
        {
            maghrib_prayer_In_Progress_notification = true;
            maghrib_athan_enable = false;
            System.out.println("maghrib Time");
            sensor_lastTimerCall = System.nanoTime();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start(); hdmiOn = true;} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
//            String image = JavaFXApplication4.class.getResource("/Images/wallpaper_sunset.jpg").toExternalForm();
//            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-image-repeat: repeat; -fx-background-size: 1080 1920;-fx-background-position: bottom left;");  
        } 
        
        else if (isha_cal.equals(Calendar_now) && isha_athan_enable) 
        {
            isha_prayer_In_Progress_notification = true;
            isha_athan_enable = false;
            System.out.println("isha Time");
            sensor_lastTimerCall = System.nanoTime();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start(); hdmiOn = true;} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException e) {logger.warn("Unexpected error", e);}
        }      
        
        
//move here athan player********************************************
    
    
 }   
    
public void update_labels() throws Exception{
    
        DateTime_now = new DateTime();    
        Calendar_now = Calendar.getInstance();
        Calendar_now.setTime(new Date());
        Calendar_now.set(Calendar.MILLISECOND, 0);
        Calendar_now.set(Calendar.SECOND, 0);
        
//==Update Clock============================================================        
        
//        hour_in_hour_Label = new SimpleDateFormat("hh").format(Calendar_now.getTime());
//        hour_Label.setText(hour_in_hour_Label);
//        minute_in_minute_Label = new SimpleDateFormat(":mm").format(Calendar_now.getTime());
//        minute_Label.setText(minute_in_minute_Label);
//        date = new SimpleDateFormat("EEEE, d MMMM").format(Calendar_now.getTime());
//        date_Label.setText(date);

//==Translate labels============================================================  
        
        if (arabic)
        {
            
            athan_Label_ar.setVisible(false);
            athan_Label_eng.setVisible(true);
            jamaat_Label_ar.setVisible(false);
            jamaat_Label_eng.setVisible(true);
            friday_Label_ar.setVisible(false);
            friday_Label_eng.setVisible(true);
            fajr_Label_ar.setVisible(false);
            fajr_Label_eng.setVisible(true);
            zuhr_Label_ar.setVisible(false);
            zuhr_Label_eng.setVisible(true);
            asr_Label_ar.setVisible(false);
            asr_Label_eng.setVisible(true);
            maghrib_Label_ar.setVisible(false);
            maghrib_Label_eng.setVisible(true);
            isha_Label_ar.setVisible(false);
            isha_Label_eng.setVisible(true);
                                
            if (hadith_Label_visible)
            {
                hadith_Label.setVisible(true);
                hadith_Label.setText(translated_hadith);
                hadith_Label.setId("hadith-text-english");
//                hadithPane.setValignment(hadith_Label,VPos.TOP);
                ar_moon_hadith_Label_L1.setVisible(false);
                ar_moon_hadith_Label_L2.setVisible(false);
                en_moon_hadith_Label_L1.setVisible(false);
                en_moon_hadith_Label_L2.setVisible(false);
                
                en_moon_hadith_Label_L2.setText("");
                ar_moon_hadith_Label_L2.setText("");
                ar_moon_hadith_Label_L2.setMinHeight(0);
                en_moon_hadith_Label_L2.setMinHeight(0);
                hadith_Label.setMinHeight(0);
 
            }
            
            if (facebook_Label_visible)
            {
                if (facebook_Label_visible_set_once)
                {    
                    if(null != facebook_post && !"".equals(facebook_post))
                    {    
                        System.out.println("facebook_post label set");
                        facebook_Label.setGraphic(null);
                        facebook_Label.setText(facebook_post);
                        facebook_Label.setId("facebook-text");
                        divider1_Label.setVisible(true);
                        facebook_Label.setVisible(true);
                        facebook_Label_visible_set_once = false;
                    }

                    if(null != facebook_Post_Url && !"".equals(facebook_Post_Url))
                    {
                        System.out.println("facebook_post picture label set");
                        facebook_Label.setText("");
                        ImageView imageView = ImageViewBuilder.create().image(new Image(facebook_Post_Url)).build();  
                        imageView.setFitHeight(350);
                        imageView.setPreserveRatio(true);
                        facebook_Label.setGraphic(imageView);
                        facebook_Label.setAlignment(Pos.CENTER);
                        facebook_Label.setVisible(true);
                        hadithPane.setHalignment(facebook_Label,HPos.CENTER);
                        facebook_Label.setVisible(true);
                        facebook_Label_visible_set_once = false;  
                    }
                    
                    
                }
                
            }
            
            if (!facebook_Label_visible)
            {
                facebook_Label.setVisible(false);
                facebook_Label.setText("");
                divider1_Label.setVisible(false);
                
            }                     
            
            if (moon_hadith_Label_visible)
            {

                en_moon_hadith_Label_L1.setVisible(true);
                en_moon_hadith_Label_L1.setText(en_full_moon_hadith);
                en_moon_hadith_Label_L1.setId("hadith-text-english");
                
                en_moon_hadith_Label_L2.setVisible(true);
                en_moon_hadith_Label_L2.setText(en_moon_notification);
                en_moon_hadith_Label_L2.setId("en_moon-notification-text");
                hadithPane.setHalignment(en_moon_hadith_Label_L2,HPos.LEFT);
//                facebook_Label.setVisible(false);
//                facebook_Label.setText("");
//                facebook_Label.setGraphic(null);
//                facebook_Label.setMinHeight(0);
                
                ar_moon_hadith_Label_L1.setVisible(false);
                ar_moon_hadith_Label_L1.setText("");
                ar_moon_hadith_Label_L1.setMinHeight(0);
                hadith_Label.setVisible(false);
                hadith_Label.setMinHeight(0);
                hadith_Label.setText("");
                ar_moon_hadith_Label_L2.setVisible(false);
                divider1_Label.setMinHeight(50);
            }
            
            if (!athan_Change_Label_visible)
            {
                athan_Change_Label_L1.setVisible(false);
                athan_Change_Label_L1.setMaxHeight(0);
                athan_Change_Label_L2.setVisible(false);
                athan_Change_Label_L2.setMaxHeight(0);
                divider1_Label.setVisible(false);
                divider1_Label.setMaxHeight(0);
            }
            
            if (athan_Change_Label_visible)
            {
                facebook_Label.setVisible(false);
                facebook_Label.setText("");
                facebook_Label.setGraphic(null);
                facebook_Label.setMinHeight(0);
                athan_Change_Label_L1.setVisible(true);
                athan_Change_Label_L1.setMaxHeight(200);
                athan_Change_Label_L2.setVisible(true);
                athan_Change_Label_L2.setMaxHeight(200);
                divider1_Label.setVisible(true);
                divider1_Label.setMaxHeight(40);
                
                athan_Change_Label_L1.setId("en_athan-change-text");
                athan_Change_Label_L1.setText(en_notification_Msg_Lines[0]);
                athan_Change_Label_L2.setId("en_athan-change-textL2");
                athan_Change_Label_L2.setText(en_notification_Msg_Lines[1]);
                hadithPane.setHalignment(athan_Change_Label_L2,HPos.LEFT);
            }
                        
            
            
            if(newMoon != null && fullMoon != null)
            {
                if (newMoon.before(fullMoon))
                {
                    Calendar_now.setTime(newMoon);
                    int day = Calendar_now.get(Calendar.DAY_OF_MONTH);
                    String dayStr = day + suffixes[day];
                    DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();

                    Days d = Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(newMoon));
                    int days_Between_Now_Newmoon = d.getDays();

                    if ( days_Between_Now_Newmoon <= 7 && days_Between_Now_Newmoon >1)
                    {
                        String newMoon_date_en = new SimpleDateFormat("EEEE").format(newMoon);
                        String newMoon_date_en1 = new SimpleDateFormat("' of ' MMMM").format(newMoon);

                        Moon_Date_Label.setId("moon-text-english");

                        if (comparator.compare(newMoon, maghrib_cal)>0){Moon_Date_Label.setText("New moon is on next\n" + newMoon_date_en + " night "  + dayStr + newMoon_date_en1);}
                        else{Moon_Date_Label.setText("New moon is on next\n" + newMoon_date_en + " the "  + dayStr  + newMoon_date_en1);}

                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else if ( days_Between_Now_Newmoon == 0)
                    {
                        Moon_Date_Label.setId("moon-text-english");

                        if (comparator.compare(newMoon, maghrib_cal)>0){Moon_Date_Label.setText("The moon is new tonight" );}
                        else{Moon_Date_Label.setText("The moon is new today" );}

                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else if ( days_Between_Now_Newmoon >0 && days_Between_Now_Newmoon <=1 )
                    {
                        Moon_Date_Label.setId("moon-text-english");
                        if (comparator.compare(newMoon, maghrib_cal)>0){Moon_Date_Label.setText("New moon is on\n"+ "tomorrow night");}
                        else{Moon_Date_Label.setText("New moon is on\n"+ "tomorrow");}
                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else if ( days_Between_Now_Newmoon <10 && days_Between_Now_Newmoon > 7)
                    {
                        String newMoon_date_en = new SimpleDateFormat("EEEE").format(newMoon);
                        String newMoon_date_en1 = new SimpleDateFormat("' of ' MMMM").format(newMoon);
                        Moon_Date_Label.setId("moon-text-english");
                        Moon_Date_Label.setText("New moon is on\n" + newMoon_date_en + " the "  + dayStr  + newMoon_date_en1);
                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else 
                    {            
                        String newMoon_date_en = new SimpleDateFormat("EEEE").format(newMoon);
                        String newMoon_date_en1 = new SimpleDateFormat("' of ' MMMM").format(newMoon);
                        Moon_Date_Label.setId("moon-text-english");
                        Moon_Date_Label.setText("Next new Moon is on\n" + newMoon_date_en  + " the "  + dayStr  + newMoon_date_en1);
                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;

        //            String image = JavaFXApplication4.class.getResource("wallpaper4.jpg").toExternalForm();
        //            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: stretch; -fx-background-size: 650 1180;-fx-background-position: top left;");
                    }


                }

                else
                {
                    Calendar_now.setTime(fullMoon);
                    int day = Calendar_now.get(Calendar.DAY_OF_MONTH);
                    String dayStr = day + suffixes[day];
                    DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();
                    Days d = Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon));
                    int days_Between_Now_Fullmoon = d.getDays();

                    if ( days_Between_Now_Fullmoon <= 7 && days_Between_Now_Fullmoon >1)
                    {
                        String FullMoon_date_en = new SimpleDateFormat("EEEE").format(fullMoon);
                        String FullMoon_date_en1 = new SimpleDateFormat("' of ' MMMM").format(fullMoon);

                        Moon_Date_Label.setId("moon-text-english");

                        if (comparator.compare(fullMoon, maghrib_cal)>0){Moon_Date_Label.setText("Full moon is on next\n" + FullMoon_date_en + " night "  + dayStr + FullMoon_date_en1);}
                        else{Moon_Date_Label.setText("Full moon is on next\n" + FullMoon_date_en + " the "  + dayStr  + FullMoon_date_en1);}

                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else if ( days_Between_Now_Fullmoon == 0)
                    {
                        Moon_Date_Label.setId("moon-text-english");

                        if (comparator.compare(fullMoon, maghrib_cal)>0){Moon_Date_Label.setText("The moon is full tonight" );}
                        else{Moon_Date_Label.setText("The moon is full today" );}

                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else if ( days_Between_Now_Fullmoon >0 && days_Between_Now_Fullmoon <=1 )
                    {
                        Moon_Date_Label.setId("moon-text-english");
                        if (comparator.compare(fullMoon, maghrib_cal)>0){Moon_Date_Label.setText("Full moon is on\n"+ "tomorrow night");}
                        else{Moon_Date_Label.setText("Full moon is on\n"+ "tomorrow");}
                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else if ( days_Between_Now_Fullmoon <10 && days_Between_Now_Fullmoon > 7)
                    {
                        String FullMoon_date_en = new SimpleDateFormat("EEEE").format(fullMoon);
                        String FullMoon_date_en1 = new SimpleDateFormat("' of ' MMMM").format(fullMoon);
                        Moon_Date_Label.setId("moon-text-english");
                        Moon_Date_Label.setText("Full moon is on\n" + FullMoon_date_en + " the "  + dayStr  + FullMoon_date_en1);
                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;
                    }

                    else 
                    {            
                        String FullMoon_date_en = new SimpleDateFormat("EEEE").format(fullMoon);
                        String FullMoon_date_en1 = new SimpleDateFormat("' of ' MMMM").format(fullMoon);
                        Moon_Date_Label.setId("moon-text-english");
                        Moon_Date_Label.setText("Next Full Moon is on\n" + FullMoon_date_en  + " the "  + dayStr  + FullMoon_date_en1);
                        Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                        english = true;
                        arabic = false;

        //            String image = JavaFXApplication4.class.getResource("wallpaper4.jpg").toExternalForm();
        //            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: stretch; -fx-background-size: 650 1180;-fx-background-position: top left;");
                    }
                }
            }
        }

        else
        { 
            
            athan_Label_eng.setVisible(false);
            athan_Label_ar.setVisible(true);
            jamaat_Label_eng.setVisible(false);
            jamaat_Label_ar.setVisible(true);
            friday_Label_eng.setVisible(false);
            friday_Label_ar.setVisible(true);
            fajr_Label_ar.setVisible(true);
            fajr_Label_eng.setVisible(false);
            zuhr_Label_ar.setVisible(true);
            zuhr_Label_eng.setVisible(false);
            asr_Label_ar.setVisible(true);
            asr_Label_eng.setVisible(false);
            maghrib_Label_ar.setVisible(true);
            maghrib_Label_eng.setVisible(false);
            isha_Label_ar.setVisible(true);
            isha_Label_eng.setVisible(false);
            
            hadithPane.setHalignment(athan_Change_Label_L1,HPos.RIGHT);
            
            if (hadith_Label_visible)
            {
                hadith_Label.setVisible(true);
                hadith_Label.setMinHeight(0);
                hadith_Label.setText(hadith);
                hadith_Label.setId("hadith-text-arabic");
                ar_moon_hadith_Label_L1.setVisible(false);
                ar_moon_hadith_Label_L2.setVisible(false);
                en_moon_hadith_Label_L1.setVisible(false);
                en_moon_hadith_Label_L2.setVisible(false);
                en_moon_hadith_Label_L2.setText("");
                ar_moon_hadith_Label_L2.setText("");
                ar_moon_hadith_Label_L2.setMinHeight(0);
                en_moon_hadith_Label_L2.setMinHeight(0);
                
            }
            
            if (moon_hadith_Label_visible)
            {
                ar_moon_hadith_Label_L1.setVisible(true);
                ar_moon_hadith_Label_L1.setText(ar_full_moon_hadith);
                ar_moon_hadith_Label_L1.setId("hadith-text-arabic");
                
                ar_moon_hadith_Label_L2.setVisible(true);
                ar_moon_hadith_Label_L2.setText(ar_moon_notification);
                ar_moon_hadith_Label_L2.setId("ar_moon-notification-text");
                hadithPane.setHalignment(ar_moon_hadith_Label_L2,HPos.RIGHT);
                
//                facebook_Label.setVisible(false);
//                facebook_Label.setText("");
//                facebook_Label.setGraphic(null);
//                facebook_Label.setMinHeight(0);
                en_moon_hadith_Label_L1.setVisible(false);
                en_moon_hadith_Label_L1.setText("");
                en_moon_hadith_Label_L1.setMinHeight(0);
                hadith_Label.setVisible(false);
                hadith_Label.setMinHeight(0);
                hadith_Label.setText("");
                en_moon_hadith_Label_L2.setVisible(false);
                divider1_Label.setMinHeight(50);
            }
            
            
            if (!athan_Change_Label_visible)
            {
                athan_Change_Label_L1.setVisible(false);
                athan_Change_Label_L2.setVisible(false);
                athan_Change_Label_L1.setMaxHeight(0);
                athan_Change_Label_L2.setMaxHeight(0);
                divider1_Label.setVisible(false);
                divider1_Label.setMaxHeight(0);
            }
            
            if (athan_Change_Label_visible)
            {
                athan_Change_Label_L1.setVisible(true);
                athan_Change_Label_L2.setVisible(true);
                athan_Change_Label_L1.setMaxHeight(200);
                athan_Change_Label_L2.setMaxHeight(200);
                divider1_Label.setVisible(true);
                divider1_Label.setMaxHeight(50);
                athan_Change_Label_L1.setText(ar_notification_Msg_Lines[0]);
                athan_Change_Label_L1.setId("ar_athan-change-text");
                athan_Change_Label_L2.setText(ar_notification_Msg_Lines[1]);
                athan_Change_Label_L2.setId("ar_athan-change-textL2");
                hadithPane.setHalignment(athan_Change_Label_L2,HPos.RIGHT);

                facebook_Label.setVisible(false);
                facebook_Label.setText("");
                facebook_Label.setGraphic(null);
                facebook_Label.setMinHeight(0);
            }
            
            
            if(newMoon != null && fullMoon != null)
            {
                if (newMoon.before(fullMoon))
                {
                        Days d = Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(newMoon));
                        int days_Between_Now_Newmoon = d.getDays();
                        DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();

                        if ( days_Between_Now_Newmoon <= 7 && days_Between_Now_Newmoon >1)
                        {
                            String newMoon_date_ar = new SimpleDateFormat("' 'EEEE", new Locale("ar")).format(newMoon);
                            String newMoon_date_ar1 = new SimpleDateFormat("d MMMM", new Locale("ar")).format(newMoon);

                            if (comparator.compare(newMoon, maghrib_cal)>0){ labeconv = "سيظهر الهلال  ليلة\n" + newMoon_date_ar + " القادم" +""+ newMoon_date_ar1;}
                            else{ labeconv = "سيظهر الهلال يوم\n" + newMoon_date_ar + " القادم" +""+ newMoon_date_ar1;}

                            StringBuilder builder = new StringBuilder();
                            for(int i =0;i<labeconv.length();i++)
                            {
                                if(Character.isDigit(labeconv.charAt(i)))
                                {
                                    builder.append(arabicChars[(int)(labeconv.charAt(i))-48]);
                                }
                                else
                                {
                                    builder.append(labeconv.charAt(i));
                                }
                            }

                            Moon_Date_Label.setId("moon-text-arabic");
                            Moon_Date_Label.setText(builder.toString());
                            Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                            english = false;
                            arabic = true;

                        }

                        else if ( days_Between_Now_Newmoon >0 && days_Between_Now_Newmoon <=1 )
                        {
                            Moon_Date_Label.setId("moon-text-arabic");
                            if (comparator.compare(newMoon, maghrib_cal)>0){ Moon_Date_Label.setText("سيظهر الهلال ليلة الغذٍٍُِِِ" );}
                            else{Moon_Date_Label.setText("سيظهر الهلال غدآ" );}
                            Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                            english = false;
                            arabic = true;
                        }

                        else if ( days_Between_Now_Newmoon == 0)
                        {
                            Moon_Date_Label.setId("moon-text-arabic");
                            if (comparator.compare(newMoon, maghrib_cal)>0){Moon_Date_Label.setText("سيظهر الهلال ليلة اليوم" );}
                            else{Moon_Date_Label.setText("سيظهر الهلال اليوم " );}
                            Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                            english = false;
                            arabic = true;
                        }

                        else if ( days_Between_Now_Newmoon <10 && days_Between_Now_Newmoon > 7)
                        {
                            String newMoon_date_ar = new SimpleDateFormat("' 'EEEE", new Locale("ar")).format(newMoon);
                            String newMoon_date_ar1 = new SimpleDateFormat("d MMMM", new Locale("ar")).format(newMoon);
                            labeconv = "سيظهر الهلال " + newMoon_date_ar + ", " + newMoon_date_ar1;
                            StringBuilder builder = new StringBuilder();
                            for(int i =0;i<labeconv.length();i++)
                            {
                                if(Character.isDigit(labeconv.charAt(i)))
                                {
                                    builder.append(arabicChars[(int)(labeconv.charAt(i))-48]);
                                }
                                else
                                {
                                    builder.append(labeconv.charAt(i));
                                }
                            }

                            Moon_Date_Label.setId("moon-text-arabic");
                            Moon_Date_Label.setText(builder.toString());
                            Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                            english = false;
                            arabic = true;
                        }

                        else 
                        {            
                            String newMoon_date_ar = new SimpleDateFormat(" EEEE d MMMM", new Locale("ar")).format(newMoon);               
                            labeconv = "سيظهر الهلال يوم\n" + newMoon_date_ar;
                            StringBuilder builder = new StringBuilder();
                            for(int i =0;i<labeconv.length();i++)
                            {
                                if(Character.isDigit(labeconv.charAt(i)))
                                {
                                    builder.append(arabicChars[(int)(labeconv.charAt(i))-48]);
                                }
                                else
                                {
                                    builder.append(labeconv.charAt(i));
                                }
                            }

                            Moon_Date_Label.setId("moon-text-arabic");
                            Moon_Date_Label.setText(builder.toString());
                            Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                            english = false;
                            arabic = true;
                        }

                }

                else
                {
                    DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();
                    Days d = Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon));
                    int days_Between_Now_Fullmoon = d.getDays();

                    if ( days_Between_Now_Fullmoon <= 7 && days_Between_Now_Fullmoon >1)
                    {
                        String FullMoon_date_ar = new SimpleDateFormat("' 'EEEE", new Locale("ar")).format(fullMoon);
                        String FullMoon_date_ar1 = new SimpleDateFormat("d MMMM", new Locale("ar")).format(fullMoon);


                        if (comparator.compare(fullMoon, maghrib_cal)>0){ labeconv = "سيكون القمر بدرا ليلة\n" + FullMoon_date_ar + " القادم" +""+ FullMoon_date_ar1;}
                        else{ labeconv = "سيكون القمر بدرا\n" + FullMoon_date_ar + " القادم" +""+ FullMoon_date_ar1;}

                        StringBuilder builder = new StringBuilder();
                        for(int i =0;i<labeconv.length();i++)
                        {
                            if(Character.isDigit(labeconv.charAt(i)))
                            {
                                builder.append(arabicChars[(int)(labeconv.charAt(i))-48]);
                            }
                            else
                            {
                                builder.append(labeconv.charAt(i));
                            }
                        }

                        Moon_Date_Label.setId("moon-text-arabic");
                        Moon_Date_Label.setText(builder.toString());
                        Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                        english = false;
                        arabic = true;

                    }

                    else if ( days_Between_Now_Fullmoon >0 && days_Between_Now_Fullmoon <=1 )
                    {
                        Moon_Date_Label.setId("moon-text-arabic");
                        if (comparator.compare(fullMoon, maghrib_cal)>0){ Moon_Date_Label.setText("سيكون القمر بدرا ليلة الغذٍٍُِِِ" );}
                        else{Moon_Date_Label.setText("سيكون القمر بدرا غدآ" );}
                        Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                        english = false;
                        arabic = true;
                    }

                    else if ( days_Between_Now_Fullmoon == 0)
                    {
                        Moon_Date_Label.setId("moon-text-arabic");
                        if (comparator.compare(fullMoon, maghrib_cal)>0){Moon_Date_Label.setText("القمر بدر ليلة اليوم" );}
                        else{Moon_Date_Label.setText("القمر بدر اليوم " );}
                        Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                        english = false;
                        arabic = true;
                    }

                    else if ( days_Between_Now_Fullmoon <10 && days_Between_Now_Fullmoon > 7)
                    {
                        String FullMoon_date_ar = new SimpleDateFormat("' 'EEEE", new Locale("ar")).format(fullMoon);
                        String FullMoon_date_ar1 = new SimpleDateFormat("d MMMM", new Locale("ar")).format(fullMoon);
                        labeconv = "سيكون القمر بدرا " + FullMoon_date_ar + ", " + FullMoon_date_ar1;
                        StringBuilder builder = new StringBuilder();
                        for(int i =0;i<labeconv.length();i++)
                        {
                            if(Character.isDigit(labeconv.charAt(i)))
                            {
                                builder.append(arabicChars[(int)(labeconv.charAt(i))-48]);
                            }
                            else
                            {
                                builder.append(labeconv.charAt(i));
                            }
                        }

                        Moon_Date_Label.setId("moon-text-arabic");
                        Moon_Date_Label.setText(builder.toString());
                        Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                        english = false;
                        arabic = true;
                    }

                    else 
                    {            
                        String FullMoon_date_ar = new SimpleDateFormat(" EEEE d MMMM", new Locale("ar")).format(fullMoon);               
                        labeconv = "سيكون القمر بدرا يوم\n" + FullMoon_date_ar;
                        StringBuilder builder = new StringBuilder();
                        for(int i =0;i<labeconv.length();i++)
                        {
                            if(Character.isDigit(labeconv.charAt(i)))
                            {
                                builder.append(arabicChars[(int)(labeconv.charAt(i))-48]);
                            }
                            else
                            {
                                builder.append(labeconv.charAt(i));
                            }
                        }

                        Moon_Date_Label.setId("moon-text-arabic");
                        Moon_Date_Label.setText(builder.toString());
                        Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                        english = false;
                        arabic = true;
                    }
                }
            }
        }
        
//==Days left to full moon============================================================        
 

                
        
// Jammat update=========================================================== 
        
        Date now = new Date();
//        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
//        System.out.println(fajr_jamaat_update_cal.getTime());
        
        if (fajr_jamaat_update_cal.equals(Calendar_now) && fajr_jamaat_update_enable )         
        {       
            fajr_jamaat_update_enable = false;
            new Thread(new Runnable() 
            {
                public void run() 
                {
                    try {
                        c = DBConnect.connect();
                        SQL = "select * from prayertimes where DATE(date) = DATE(NOW()) + 1";
                        rs = c.createStatement().executeQuery(SQL);
                        while (rs.next())
                        {
                            fajr_jamaat_time =       rs.getTime("fajr_jamaat");
                        }
                        c.close();
                        fajr_jamaat = fajr_jamaat_time.toString();
                        System.out.println("fajr jamaat time updated:" + fajr_jamaat);
                        
                        
                        Date fajr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " " + fajr_jamaat);
                        cal.setTime(fajr_jamaat_temp);
                        cal.add(Calendar.MINUTE, 15);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        Date fajr_jamaat = cal.getTime();
                        fajr_jamaat_update_cal = Calendar.getInstance();
                        fajr_jamaat_update_cal.setTime(fajr_jamaat);
                        fajr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                        fajr_jamaat_update_cal.set(Calendar.SECOND, 0);
    //                            System.out.println(fajr_jamaat_update_cal.getTime());
                        
                        fajr_jamaat_cal = (Calendar)fajr_jamaat_update_cal.clone();
                        fajr_jamaat_cal.add(Calendar.MINUTE, -15);
                        
                        System.out.println("next update is on:" + fajr_jamaat_update_cal.getTime());
                        TimeUnit.MINUTES.sleep(1);
                        fajr_jamaat_update_enable = true;
                        update_prayer_labels = true;
                        
                        
                        
                        

                    } 
                    catch (SQLException e) {logger.warn("Unexpected error", e);} 
                    catch (ParseException e) {logger.warn("Unexpected error", e);} 
                    catch (InterruptedException e) {logger.warn("Unexpected error", e);}
               }
            }).start();
        }   
        
        if (asr_jamaat_update_cal.equals(Calendar_now) && asr_jamaat_update_enable )         
        {       
            
            asr_jamaat_update_enable = false;
            new Thread(new Runnable() 
            {
                public void run() 
                {
                    try {
                        c = DBConnect.connect();
                        SQL = "select * from prayertimes where DATE(date) = DATE(NOW()) + 1";
                        rs = c.createStatement().executeQuery(SQL);
                        while (rs.next())
                        {
                            asr_jamaat_time =       rs.getTime("asr_jamaat");
                        }
                        c.close();
                        asr_jamaat = asr_jamaat_time.toString();
                        System.out.println("asr jamaat time updated:" + asr_jamaat);
                        Date asr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " " + asr_jamaat);
                        cal.setTime(asr_jamaat_temp);
                        cal.add(Calendar.MINUTE, 15);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        Date asr_jamaat = cal.getTime();
                        asr_jamaat_update_cal = Calendar.getInstance();
                        asr_jamaat_update_cal.setTime(asr_jamaat);
                        asr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                        asr_jamaat_update_cal.set(Calendar.SECOND, 0);
    //                            System.out.println(fajr_jamaat_update_cal.getTime());
                        
                        asr_jamaat_cal = (Calendar)asr_jamaat_update_cal.clone();
                        asr_jamaat_cal.add(Calendar.MINUTE, -15);
                        
                        System.out.println("next update is on:" + asr_jamaat_update_cal.getTime());
                        TimeUnit.MINUTES.sleep(1);
                        asr_jamaat_update_enable = true;
                        update_prayer_labels = true;

                    } 
                    catch (SQLException e) {logger.warn("Unexpected error", e);} 
                    catch (ParseException e) {logger.warn("Unexpected error", e);} 
                    catch (InterruptedException e) {logger.warn("Unexpected error", e);}
               }
            }).start();
        }
        
        if (isha_jamaat_update_cal.equals(Calendar_now) && isha_jamaat_update_enable )         
        {       
            isha_jamaat_update_enable = false;
            new Thread(new Runnable() 
            {
                public void run() 
                {
                    try {
                        c = DBConnect.connect();
                        SQL = "select * from prayertimes where DATE(date) = DATE(NOW()) + 1";
                        rs = c.createStatement().executeQuery(SQL);
                        while (rs.next())
                        {
                            isha_jamaat_time =       rs.getTime("isha_jamaat");
                        }
                        c.close();
                        isha_jamaat = isha_jamaat_time.toString();
                        System.out.println("isha jamaat time updated:" + isha_jamaat);
                        Date isha_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " " + isha_jamaat);
                        cal.setTime(isha_jamaat_temp);
                        cal.add(Calendar.MINUTE, 15);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        Date isha_jamaat = cal.getTime();
                        isha_jamaat_update_cal = Calendar.getInstance();
                        isha_jamaat_update_cal.setTime(isha_jamaat);
                        isha_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                        isha_jamaat_update_cal.set(Calendar.SECOND, 0);
    //                            System.out.println(fajr_jamaat_update_cal.getTime());
                        
                        isha_jamaat_cal = (Calendar)isha_jamaat_update_cal.clone();
                        isha_jamaat_cal.add(Calendar.MINUTE, -15);
                        
                        System.out.println("next update is on:" + isha_jamaat_update_cal.getTime());
                        TimeUnit.MINUTES.sleep(1);
                        isha_jamaat_update_enable = true;
                        update_prayer_labels = true;

                    } 
                    catch (SQLException e) {logger.warn("Unexpected error", e);} 
                    catch (ParseException e) {logger.warn("Unexpected error", e);} 
                    catch (InterruptedException e) {logger.warn("Unexpected error", e);}
               }
            }).start();
        }
 
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
////        Date preDefineTime=formatter.parse("10:00");
//        long additionMin=15*60*1000;
//        System.out.println(formatter.format(sunrise));
//        System.out.println(formatter.format(sunrise.getTime()+additionMin));
       
//==Update Prayer time Labels==========================================================        
        
        if (update_prayer_labels && !count_down) 
        {
            update_prayer_labels = false;

            fajrdate = fajr_cal.getTime();
            formattedDateTime = dateFormat.format(fajrdate);
            
            fajr_hourLeft.setText(formattedDateTime.substring(0, 1));
            fajr_hourRight.setText(formattedDateTime.substring(1, 2));
            fajr_minLeft.setText(formattedDateTime.substring(3, 4));
            fajr_minRight.setText(formattedDateTime.substring(4, 5));
            
//            sunrise_hourLeft.setText(sunrise_time.toString().substring(11, 12));
//            sunrise_hourRight.setText(sunrise_time.toString().substring(12, 13));
//            sunrise_minLeft.setText(sunrise_time.toString().substring(14, 15));
//            sunrise_minRight.setText(sunrise_time.toString().substring(15, 16));

            zuhrdate = zuhr_cal.getTime();
            formattedDateTime = dateFormat.format(zuhrdate);
            
            zuhr_hourLeft.setText(formattedDateTime.substring(0, 1));
            zuhr_hourRight.setText(formattedDateTime.substring(1, 2));
            zuhr_minLeft.setText(formattedDateTime.substring(3, 4));
            zuhr_minRight.setText(formattedDateTime.substring(4, 5));
             
            asrdate = asr_cal.getTime();
            formattedDateTime = dateFormat.format(asrdate);
            
            asr_hourLeft.setText(formattedDateTime.substring(0, 1));
            asr_hourRight.setText(formattedDateTime.substring(1, 2));
            asr_minLeft.setText(formattedDateTime.substring(3, 4));
            asr_minRight.setText(formattedDateTime.substring(4, 5));
            
            maghribdate = maghrib_cal.getTime();
            formattedDateTime = dateFormat.format(maghribdate);
            
            maghrib_hourLeft.setText(formattedDateTime.substring(0, 1));
            maghrib_hourRight.setText(formattedDateTime.substring(1, 2));
            maghrib_minLeft.setText(formattedDateTime.substring(3, 4));
            maghrib_minRight.setText(formattedDateTime.substring(4, 5));
            
            ishadate = isha_cal.getTime();
            formattedDateTime = dateFormat.format(ishadate);
            
            isha_hourLeft.setText(formattedDateTime.substring(0, 1));
            isha_hourRight.setText(formattedDateTime.substring(1, 2));
            isha_minLeft.setText(formattedDateTime.substring(3, 4));
            isha_minRight.setText(formattedDateTime.substring(4, 5));
            
            time_Separator1.setText(":");
            time_Separator2.setText(":");
            time_Separator3.setText(":");
            time_Separator4.setText(":");
            time_Separator5.setText(":");
            time_Separator6.setText(":");
            time_Separator8.setText(":");
            
            
            fajrjamaatdate = fajr_jamaat_cal.getTime();
            formattedDateTime = dateFormat.format(fajrjamaatdate);
            
            fajr_jamma_hourLeft.setText(formattedDateTime.substring(0, 1));
            fajr_jamma_hourRight.setText(formattedDateTime.substring(1, 2));
            fajr_jamma_minLeft.setText(formattedDateTime.substring(3, 4));
            fajr_jamma_minRight.setText(formattedDateTime.substring(4, 5));
            

            
            zuhr_jamma_hourLeft.setText(zuhr_jamaat.substring(0, 1));
            zuhr_jamma_hourRight.setText(zuhr_jamaat.substring(1, 2));
            zuhr_jamma_minLeft.setText(zuhr_jamaat.substring(3, 4));
            zuhr_jamma_minRight.setText(zuhr_jamaat.substring(4, 5));
             
            
            asrjamaatdate = asr_jamaat_cal.getTime();
            formattedDateTime = dateFormat.format(asrjamaatdate);
            
            asr_jamma_hourLeft.setText(formattedDateTime.substring(0, 1));
            asr_jamma_hourRight.setText(formattedDateTime.substring(1, 2));
            asr_jamma_minLeft.setText(formattedDateTime.substring(3, 4));
            asr_jamma_minRight.setText(formattedDateTime.substring(4, 5));
            
//            maghribjamaatdate = maghrib_cal.getTime();
            maghribjamaatdate = maghrib_jamaat_cal.getTime();
            formattedDateTime = dateFormat.format(maghribjamaatdate);
            maghrib_jamma_hourLeft.setText(formattedDateTime.substring(0, 1));
            maghrib_jamma_hourRight.setText(formattedDateTime.substring(1, 2));
            maghrib_jamma_minLeft.setText(formattedDateTime.substring(3, 4));
            maghrib_jamma_minRight.setText(formattedDateTime.substring(4, 5));
            
            ishajamaatdate = isha_jamaat_cal.getTime();
            formattedDateTime = dateFormat.format(ishajamaatdate);
            
            isha_jamma_hourLeft.setText(formattedDateTime.substring(0, 1));
            isha_jamma_hourRight.setText(formattedDateTime.substring(1, 2));
            isha_jamma_minLeft.setText(formattedDateTime.substring(3, 4));
            isha_jamma_minRight.setText(formattedDateTime.substring(4, 5));
            
            friday_hourLeft.setText(friday_jamaat.substring(0, 1));
            friday_hourRight.setText(friday_jamaat.substring(1, 2));
            friday_minLeft.setText(friday_jamaat.substring(3, 4));
            friday_minRight.setText(friday_jamaat.substring(4, 5));
             
            time_jamma_Separator1.setText(":");
            time_jamma_Separator2.setText(":");
            time_jamma_Separator3.setText(":");
            time_jamma_Separator4.setText(":");
            time_jamma_Separator5.setText(":");   
        }
        
//==Update Moon Images============================================================  
        
        if (update_moon_image)
        {   
            update_moon_image = false;
            
            if (moonPhase == 200 )
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/0%.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
            
            if (moonPhase <= 2 && moonPhase >= 0 )
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/0%.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
        
            else if (moonPhase>2 && moonPhase<=10 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/3%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>10 && moonPhase<=17 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/12%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>17 && moonPhase<=32 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/21%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
            else if (moonPhase>32 && moonPhase<=43 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/38%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>43 && moonPhase<=52 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/47%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>52 && moonPhase<=61 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>61 && moonPhase<=70 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/65%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>70 && moonPhase<=78 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/74%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>78 && moonPhase<=87 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/82%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>87 && moonPhase<=99 && isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/91%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase== 100)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/100%.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>2 && moonPhase<=12 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/8%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>12 && moonPhase<=20 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/16%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>20 && moonPhase<=28 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/24%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>28 && moonPhase<=36 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/32%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>36 && moonPhase<=44 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/40%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>44 && moonPhase<=52 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/48%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>52 && moonPhase<=59 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>59 && moonPhase<=67 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/63%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>67 && moonPhase<=74 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/71%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>74 && moonPhase<=82 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/78%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>82 && moonPhase<=90 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/86%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>90 && moonPhase<=99 && !isWaning)
            {
                Moon_Image_Label.setGraphic(null);
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/94%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

        }

//             if (isInternetReachable()){ System.out.println("connected"); control.setIndicatorStyle(SimpleIndicator.IndicatorStyle.GREEN);} else {System.out.println("not connected");control.setIndicatorStyle(SimpleIndicator.IndicatorStyle.RED);}

        }    
    

    //checks for connection to the internet through dummy request
//        public static boolean isInternetReachable()
//        {
//            try {
//                //make a URL to a known source
//                URL url = new URL("http://www.google.com");
//
//                //open a connection to that source
//                HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
//
//                //trying to retrieve data from the source. If there
//                //is no connection, this line will fail
//                Object objData = urlConnect.getContent();
//
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//                return false;
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//            return true;
//        }

    
    public GridPane prayertime_pane() {
        
   GridPane prayertime_pane = new GridPane();
        prayertime_pane.setId("prayertime_pane");
        prayertime_pane.setCache(false);       
        prayertime_pane.setGridLinesVisible(false);
        prayertime_pane.setPadding(new Insets(0, 0, 20, 20));
        prayertime_pane.setAlignment(Pos.BASELINE_CENTER);
//        prayertime_pane.setVgap(20);
        prayertime_pane.setHgap(80);
        
        prayertime_pane.setConstraints(jamaat_Label_eng, 0, 1);
        prayertime_pane.getChildren().add(jamaat_Label_eng);      
        prayertime_pane.setConstraints(jamaat_Label_ar, 0, 1);
        prayertime_pane.getChildren().add(jamaat_Label_ar);
        
        prayertime_pane.setConstraints(athan_Label_eng, 1, 1);
        prayertime_pane.getChildren().add(athan_Label_eng);      
        prayertime_pane.setConstraints(athan_Label_ar, 1, 1);
        prayertime_pane.getChildren().add(athan_Label_ar);
//=============================  
        HBox fajrBox = new HBox();
        fajrBox.setSpacing(0);
        fajrBox.setMaxSize(180,60);
        fajrBox.setMinSize(180,60);
        fajrBox.setPrefSize(180,60);
        
        fajr_hourLeft.setId("hourLeft");
        fajr_hourRight.setId("hourLeft");
        time_Separator1.setId("hourLeft");
        fajr_minLeft.setId("hourLeft");
        fajr_minRight.setId("hourLeft");
        
        fajrBox.getChildren().addAll(fajr_hourLeft, fajr_hourRight, time_Separator1, fajr_minLeft, fajr_minRight);
        prayertime_pane.setConstraints(fajrBox, 1, 2);
        prayertime_pane.getChildren().add(fajrBox);
        
        prayertime_pane.setConstraints(fajr_Label_eng, 2, 2);
        prayertime_pane.getChildren().add(fajr_Label_eng);      
        prayertime_pane.setConstraints(fajr_Label_ar, 2, 2);
        prayertime_pane.getChildren().add(fajr_Label_ar);
        
        fajr_hourLeft.setText("-");
        fajr_hourRight.setText("-");
        fajr_minLeft.setText("-");
        fajr_minRight.setText("-");
        time_Separator1.setText(":");
        

//============================= 
        HBox zuhrBox = new HBox();
        zuhrBox.setSpacing(0);
        zuhrBox.setMaxSize(180,60);
        zuhrBox.setMinSize(180,60);
        zuhrBox.setPrefSize(180,60);
        zuhr_hourLeft.setId("hourLeft");
        zuhr_hourRight.setId("hourLeft");
        time_Separator3.setId("hourLeft");
        zuhr_minLeft.setId("hourLeft");
        zuhr_minRight.setId("hourLeft");
        zuhrBox.getChildren().addAll(zuhr_hourLeft, zuhr_hourRight, time_Separator3, zuhr_minLeft, zuhr_minRight);
        prayertime_pane.setConstraints(zuhrBox, 1, 4);
        prayertime_pane.getChildren().add(zuhrBox);
        
        prayertime_pane.setConstraints(zuhr_Label_eng, 2, 4);
        prayertime_pane.getChildren().add(zuhr_Label_eng);      
        prayertime_pane.setConstraints(zuhr_Label_ar, 2, 4);
        prayertime_pane.getChildren().add(zuhr_Label_ar);
        
        zuhr_hourLeft.setText("-");
        zuhr_hourRight.setText("-");
        zuhr_minLeft.setText("-");
        zuhr_minRight.setText("-");
        time_Separator3.setText(":");

//============================= 
        HBox asrBox = new HBox();
        asrBox.setSpacing(0);
        asrBox.setMaxSize(180,60);
        asrBox.setMinSize(180,60);
        asrBox.setPrefSize(180,60);
        asr_hourLeft.setId("hourLeft");
        asr_hourRight.setId("hourLeft");
        time_Separator4.setId("hourLeft");
        asr_minLeft.setId("hourLeft");
        asr_minRight.setId("hourLeft");
        asrBox.getChildren().addAll(asr_hourLeft, asr_hourRight, time_Separator4, asr_minLeft, asr_minRight);
        prayertime_pane.setConstraints(asrBox, 1, 6);
        prayertime_pane.getChildren().add(asrBox);
        
        prayertime_pane.setConstraints(asr_Label_eng, 2, 6);
        prayertime_pane.getChildren().add(asr_Label_eng);      
        prayertime_pane.setConstraints(asr_Label_ar, 2, 6);
        prayertime_pane.getChildren().add(asr_Label_ar);
        
        asr_hourLeft.setText("-");
        asr_hourRight.setText("-");
        asr_minLeft.setText("-");
        asr_minRight.setText("-");
        time_Separator4.setText(":");
        
//============================= 
        
        HBox maghribBox = new HBox();
        maghribBox.setSpacing(0);
        maghribBox.setMaxSize(180,60);
        maghribBox.setMinSize(180,60);
        maghribBox.setPrefSize(180,60);
        maghrib_hourLeft.setId("hourLeft");
        maghrib_hourRight.setId("hourLeft");
        time_Separator5.setId("hourLeft");
        maghrib_minLeft.setId("hourLeft");
        maghrib_minRight.setId("hourLeft");
        maghribBox.getChildren().addAll(maghrib_hourLeft, maghrib_hourRight, time_Separator5, maghrib_minLeft, maghrib_minRight);
        prayertime_pane.setConstraints(maghribBox, 1, 8);
        prayertime_pane.getChildren().add(maghribBox);
        
        prayertime_pane.setConstraints(maghrib_Label_eng, 2, 8);
        prayertime_pane.getChildren().add(maghrib_Label_eng);      
        prayertime_pane.setConstraints(maghrib_Label_ar, 2, 8);
        prayertime_pane.getChildren().add(maghrib_Label_ar);
        
        maghrib_hourLeft.setText("-");
        maghrib_hourRight.setText("-");
        maghrib_minLeft.setText("-");
        maghrib_minRight.setText("-");
        time_Separator5.setText(":");

//============================= 
        
        HBox ishaBox = new HBox();
        ishaBox.setSpacing(0);
        ishaBox.setMaxSize(180,60);
        ishaBox.setMinSize(180,60);
        ishaBox.setPrefSize(180,60);
        isha_hourLeft.setId("hourLeft");
        isha_hourRight.setId("hourLeft");
        time_Separator6.setId("hourLeft");
        isha_minLeft.setId("hourLeft");
        isha_minRight.setId("hourLeft");
        ishaBox.getChildren().addAll(isha_hourLeft, isha_hourRight, time_Separator6, isha_minLeft, isha_minRight);
        prayertime_pane.setConstraints(ishaBox, 1, 10);
        prayertime_pane.getChildren().add(ishaBox);
        
        prayertime_pane.setConstraints(isha_Label_eng, 2, 10);
        prayertime_pane.getChildren().add(isha_Label_eng);      
        prayertime_pane.setConstraints(isha_Label_ar, 2, 10);
        prayertime_pane.getChildren().add(isha_Label_ar);
        
        isha_hourLeft.setText("-");
        isha_hourRight.setText("-");
        isha_minLeft.setText("-");
        isha_minRight.setText("-");
        time_Separator6.setText(":");

 //=============================  
//        HBox gapBox = new HBox();
//        gapBox.setSpacing(0);
//        gapBox.setMaxSize(180,60);
//        gapBox.setMinSize(180,60);
//        gapBox.getChildren().addAll();
//        prayertime_pane.setConstraints(gapBox, 0, 12);
//        prayertime_pane.getChildren().add(gapBox);       
        
//=============================  
//        HBox sunriseBox = new HBox();
//        sunriseBox.setSpacing(0);
//        sunriseBox.setMaxSize(180,60);
//        sunriseBox.setMinSize(180,60);
//        sunriseBox.setPrefSize(180,60);
//        sunrise_hourLeft.setId("hourLeft");
//        sunrise_hourRight.setId("hourLeft");
//        time_Separator2.setId("hourLeft");
//        sunrise_minLeft.setId("hourLeft");
//        sunrise_minRight.setId("hourLeft");
//        sunriseBox.getChildren().addAll(sunrise_hourLeft, sunrise_hourRight, time_Separator2, sunrise_minLeft, sunrise_minRight);
//        prayertime_pane.setConstraints(sunriseBox, 1, 13);
//        prayertime_pane.getChildren().add(sunriseBox);
//        
//
//        prayertime_pane.setConstraints(sunrise_Label_eng, 2, 13);
//        prayertime_pane.getChildren().add(sunrise_Label_eng);
//       
//        prayertime_pane.setConstraints(sunrise_Label_ar, 2, 13);
//        prayertime_pane.getChildren().add(sunrise_Label_ar);


                      
//=============================  
         
        HBox fridayBox = new HBox();
        fridayBox.setSpacing(0);
        fridayBox.setMaxSize(180,60);
        fridayBox.setMinSize(180,60);
        fridayBox.setPrefSize(180,60);
        friday_hourLeft.setId("hourLeft");
        friday_hourRight.setId("hourLeft");
        time_Separator8.setId("hourLeft");
        friday_minLeft.setId("hourLeft");
        friday_minRight.setId("hourLeft");
        fridayBox.getChildren().addAll(friday_hourLeft, friday_hourRight, time_Separator8, friday_minLeft, friday_minRight);
        prayertime_pane.setConstraints(fridayBox, 1, 13);
        prayertime_pane.getChildren().add(fridayBox);
        
        
        prayertime_pane.setConstraints(friday_Label_eng, 2, 13);
        prayertime_pane.getChildren().add(friday_Label_eng);
        prayertime_pane.setConstraints(friday_Label_ar, 2, 13);
        prayertime_pane.getChildren().add(friday_Label_ar);
        
        friday_hourLeft.setText("-");
        friday_hourRight.setText("-");
        friday_minLeft.setText("-");
        friday_minRight.setText("-");
        time_Separator8.setText(":");
 //============================= 
        
        final Separator sepHor1 = new Separator();
        prayertime_pane.setValignment(sepHor1,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor1, 0, 3);
        prayertime_pane.setColumnSpan(sepHor1, 3);
        prayertime_pane.getChildren().add(sepHor1);   
        
        final Separator sepHor2 = new Separator();
        prayertime_pane.setValignment(sepHor2,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor2, 0, 5);
        prayertime_pane.setColumnSpan(sepHor2, 3);
        prayertime_pane.getChildren().add(sepHor2);
        
        final Separator sepHor3 = new Separator();
        prayertime_pane.setValignment(sepHor3,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor3, 0, 7);
        prayertime_pane.setColumnSpan(sepHor3, 3);
        prayertime_pane.getChildren().add(sepHor3);
        
        final Separator sepHor4 = new Separator();
        prayertime_pane.setValignment(sepHor4,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor4, 0, 9);
        prayertime_pane.setColumnSpan(sepHor4, 3);
        prayertime_pane.getChildren().add(sepHor4);
        
        final Separator sepHor5 = new Separator();
        prayertime_pane.setValignment(sepHor5,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor5, 1, 12);
        prayertime_pane.setColumnSpan(sepHor5, 2);
        prayertime_pane.getChildren().add(sepHor5);
        
//        final Separator sepHor6 = new Separator();
//        prayertime_pane.setValignment(sepHor6,VPos.CENTER);
//        prayertime_pane.setConstraints(sepHor6, 1, 14);
//        prayertime_pane.setColumnSpan(sepHor6, 2);
//        prayertime_pane.getChildren().add(sepHor6);

//========= Jamama=======
        
        
//=============================  
        HBox fajr_jamma_Box = new HBox();
        fajr_jamma_Box.setSpacing(0);
        fajr_jamma_Box.setMaxSize(180,60);
        fajr_jamma_hourLeft.setId("hourLeft");
        fajr_jamma_hourRight.setId("hourLeft");
        time_jamma_Separator1.setId("hourLeft");
        fajr_jamma_minLeft.setId("hourLeft");
        fajr_jamma_minRight.setId("hourLeft");
        fajr_jamma_Box.getChildren().addAll(fajr_jamma_hourLeft, fajr_jamma_hourRight, time_jamma_Separator1, fajr_jamma_minLeft, fajr_jamma_minRight);
        prayertime_pane.setConstraints(fajr_jamma_Box, 0, 2);
        prayertime_pane.getChildren().add(fajr_jamma_Box);
        
        fajr_jamma_hourLeft.setText("-");
        fajr_jamma_hourRight.setText("-");
        fajr_jamma_minLeft.setText("-");
        fajr_jamma_minRight.setText("-");
        time_jamma_Separator1.setText(":");
        
//============================= 
        HBox zuhr_jamma_Box = new HBox();
        zuhr_jamma_Box.setSpacing(0);
        zuhr_jamma_Box.setMaxSize(180,60);
        zuhr_jamma_hourLeft.setId("hourLeft");
        zuhr_jamma_hourRight.setId("hourLeft");
        time_jamma_Separator2.setId("hourLeft");
        zuhr_jamma_minLeft.setId("hourLeft");
        zuhr_jamma_minRight.setId("hourLeft");
        zuhr_jamma_Box.getChildren().addAll(zuhr_jamma_hourLeft, zuhr_jamma_hourRight, time_jamma_Separator2, zuhr_jamma_minLeft, zuhr_jamma_minRight);
        prayertime_pane.setConstraints(zuhr_jamma_Box, 0, 4);
        prayertime_pane.getChildren().add(zuhr_jamma_Box);
        
        zuhr_jamma_hourLeft.setText("-");
        zuhr_jamma_hourRight.setText("-");
        zuhr_jamma_minLeft.setText("-");
        zuhr_jamma_minRight.setText("-");
        time_jamma_Separator2.setText(":");

//============================= 
        HBox asr_jamma_Box = new HBox();
        asr_jamma_Box.setSpacing(0);
        asr_jamma_Box.setMaxSize(180,60);
        asr_jamma_minRight.setId("hourLeft");
        asr_jamma_minLeft.setId("hourLeft");
        time_jamma_Separator3.setId("hourLeft");
        asr_jamma_hourRight.setId("hourLeft");
        asr_jamma_hourLeft.setId("hourLeft");
        asr_jamma_Box.getChildren().addAll(asr_jamma_hourLeft, asr_jamma_hourRight, time_jamma_Separator3, asr_jamma_minLeft, asr_jamma_minRight);
        prayertime_pane.setConstraints(asr_jamma_Box, 0, 6);
        prayertime_pane.getChildren().add(asr_jamma_Box);
        
        asr_jamma_hourLeft.setText("-");
        asr_jamma_hourRight.setText("-");
        asr_jamma_minLeft.setText("-");
        asr_jamma_minRight.setText("-");
        time_jamma_Separator3.setText(":");
        
//============================= 
        
        HBox maghrib_jamma_Box = new HBox();
        maghrib_jamma_Box.setSpacing(0);
        maghrib_jamma_Box.setMaxSize(180,60);
        maghrib_jamma_minRight.setId("hourLeft");
        maghrib_jamma_minLeft.setId("hourLeft");
        time_jamma_Separator4.setId("hourLeft");
        maghrib_jamma_hourRight.setId("hourLeft");
        maghrib_jamma_hourLeft.setId("hourLeft");
        maghrib_jamma_Box.getChildren().addAll(maghrib_jamma_hourLeft, maghrib_jamma_hourRight, time_jamma_Separator4, maghrib_jamma_minLeft, maghrib_jamma_minRight);
        prayertime_pane.setConstraints(maghrib_jamma_Box, 0, 8);
        prayertime_pane.getChildren().add(maghrib_jamma_Box);
        
        maghrib_jamma_hourLeft.setText("-");
        maghrib_jamma_hourRight.setText("-");
        maghrib_jamma_minLeft.setText("-");
        maghrib_jamma_minRight.setText("-");
        time_jamma_Separator4.setText(":");
//============================= 
        
        HBox isha_jamma_Box = new HBox();
        isha_jamma_Box.setSpacing(0);
        isha_jamma_Box.setMaxSize(180,60);
        isha_jamma_hourLeft.setId("hourLeft");
        isha_jamma_hourRight.setId("hourLeft");
        time_jamma_Separator5.setId("hourLeft");
        isha_jamma_minLeft.setId("hourLeft");
        isha_jamma_minRight.setId("hourLeft");
        isha_jamma_Box.getChildren().addAll(isha_jamma_hourLeft, isha_jamma_hourRight, time_jamma_Separator5, isha_jamma_minLeft, isha_jamma_minRight);
        prayertime_pane.setConstraints(isha_jamma_Box, 0, 10);
        prayertime_pane.getChildren().add(isha_jamma_Box);
        
        isha_jamma_hourLeft.setText("-");
        isha_jamma_hourRight.setText("-");
        isha_jamma_minLeft.setText("-");
        isha_jamma_minRight.setText("-");
        time_jamma_Separator5.setText(":");

    return prayertime_pane;
}
    
  
    
    
    //===MOON PANE==========================  
    public GridPane moonpane() {
      
        GridPane Moonpane = new GridPane();
        Moonpane.setId("moonpane");
        Moonpane.getColumnConstraints().setAll(
                ColumnConstraintsBuilder.create().prefWidth(220).minWidth(220).build(),
                ColumnConstraintsBuilder.create().prefWidth(100).minWidth(100).build()     
        );
        Moonpane.setHgap(40);
        Moonpane.setMaxHeight(50);
//       Moonpane.setGridLinesVisible(false);

        ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/100%.png")));      
        Moon_img.setFitWidth(160);
        Moon_img.setFitHeight(160);
//        Moon_img.setPreserveRatio(false);
        Moon_img.setSmooth(true);
        Moon_Image_Label.setGraphic(Moon_img);
        Moonpane.setConstraints(Moon_Image_Label, 1, 0);
        Moonpane.getChildren().add(Moon_Image_Label); 
        Moon_Date_Label.setId("moon-text-english");
        Moon_Date_Label.setWrapText(true);
        Moon_Date_Label.setText("Loading......");
        Moonpane.setConstraints(Moon_Date_Label, 0, 0);
        Moonpane.getChildren().add(Moon_Date_Label);

        return Moonpane;
    }
    
    //===MOON PANE==========================  
    public GridPane hadithPane() {
      
        GridPane hadithPane = new GridPane();
//        hadithPane.setGridLinesVisible(true);
        hadithPane.setId("hadithpane");
        hadithPane.setVgap(0);

        hadith_Label.setId("hadith-text-arabic");
        hadith_Label.setWrapText(true);
        hadith_Label.setMinHeight(0);
        hadithPane.setConstraints(hadith_Label, 0, 0);
        hadithPane.getChildren().add(hadith_Label);
        
        
        en_moon_hadith_Label_L1.setId("hadith-text-english");
        en_moon_hadith_Label_L1.setWrapText(true);
        en_moon_hadith_Label_L1.setText("Loading.....");
        en_moon_hadith_Label_L1.setMinHeight(0);
        hadithPane.setConstraints(en_moon_hadith_Label_L1, 0, 0);
        hadithPane.getChildren().add(en_moon_hadith_Label_L1);
        
        en_moon_hadith_Label_L2.setWrapText(true);
        en_moon_hadith_Label_L2.setMinHeight(0);
        hadithPane.setConstraints(en_moon_hadith_Label_L2, 0, 1);
        hadithPane.getChildren().add(en_moon_hadith_Label_L2);
        
        ar_moon_hadith_Label_L1.setId("hadith-text-arabic");
        ar_moon_hadith_Label_L1.setWrapText(true);
        ar_moon_hadith_Label_L1.setMinHeight(0);
        hadithPane.setConstraints(ar_moon_hadith_Label_L1, 0, 0);
        hadithPane.getChildren().add(ar_moon_hadith_Label_L1);
        
        ar_moon_hadith_Label_L2.setWrapText(true);
        ar_moon_hadith_Label_L2.setMinHeight(0);
        hadithPane.setConstraints(ar_moon_hadith_Label_L2, 0, 1);
        hadithPane.getChildren().add(ar_moon_hadith_Label_L2);
        
        facebook_Label.setWrapText(true);
        facebook_Label.setMinHeight(0);
        hadithPane.setConstraints(facebook_Label, 0, 3);
        hadithPane.getChildren().add(facebook_Label);
                
        ImageView divider_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/divider.png")));      
        divider1_Label.setGraphic(divider_img);
        hadithPane.setHalignment(divider1_Label,HPos.CENTER);
        hadithPane.setConstraints(divider1_Label, 0, 2);
        hadithPane.getChildren().add(divider1_Label); 
        
//        divider2_Label.setGraphic(divider_img);
//        hadithPane.setHalignment(divider2_Label,HPos.CENTER);
//        hadithPane.setConstraints(divider2_Label, 0, 2);
//        hadithPane.getChildren().add(divider2_Label); 
        
        athan_Change_Label_L1.setWrapText(true);
        athan_Change_Label_L1.setMinHeight(0);
        hadithPane.setConstraints(athan_Change_Label_L1, 0, 3);
        hadithPane.getChildren().add(athan_Change_Label_L1);
        
        athan_Change_Label_L2.setWrapText(true);
        athan_Change_Label_L2.setMinHeight(0);
        hadithPane.setConstraints(athan_Change_Label_L2, 0, 4);
        hadithPane.getChildren().add(athan_Change_Label_L2);
        


        return hadithPane;
    }    

    public GridPane footerPane() {
      
        GridPane footerPane = new GridPane();
//        hadithPane.setGridLinesVisible(false);  
        double size = 10;
        TextFlow textFlow1 = new TextFlow();
        TextFlow textFlow2 = new TextFlow();
        TextFlow textFlow3 = new TextFlow();
        Text text1 = new Text("                                           Get prayer time notifications and daily hadith on your mobile by following us on ");
        text1.setFont(Font.font("Tahoma", size));
        text1.setFill(Color.WHITE);
        ImageView facebook_image = new ImageView(new Image(getClass().getResourceAsStream("/Images/facebook.png")));
        facebook_image.setTranslateY(15);
        Text text5 = new Text("  or  ");
        text5.setFont(Font.font("Tahoma", size));
        text5.setFill(Color.WHITE);
        ImageView twitter_image = new ImageView(new Image(getClass().getResourceAsStream("/Images/twitter.png")));
        Text text6 = new Text("                                                                 Twitter: ");
        text6.setFont(Font.font("Tahoma", size));
        text6.setFill(Color.WHITE);
        Text text7 = new Text("@Daar_Ibn_Abbas");
        text7.setFont(Font.font("Tahoma", FontWeight.BOLD, size));
        text7.setFill(Color.WHITE);
        Text text8 = new Text("           Facebook Page: ");
        text8.setFont(Font.font("Tahoma", size));
        text8.setFill(Color.WHITE);
        Text text9 = new Text("Daar-Ibn-Abbas");
        text9.setFont(Font.font("Tahoma" , FontWeight.BOLD, size));
        text9.setFill(Color.WHITE);
        Text text10 = new Text("لا حول ولا قوة الا بالله العلي العظيم                                                                                             ");
        text10.setFont(Font.font("Tahoma", size));
        text10.setFill(Color.GOLDENROD);
//        
//        
//        ImageView twitter_code = new ImageView(new Image(getClass().getResourceAsStream("/Images/QR_CODE_Twitter.png"))); 
//        twitter_code.setFitHeight(70);
//        twitter_code.setTranslateY(20);
//        twitter_code.setPreserveRatio(true);
//        ImageView facebook_code = new ImageView(new Image(getClass().getResourceAsStream("/Images/QR_CODE_Facebook.png")));
//        facebook_code.setFitHeight(70);
//        facebook_code.setTranslateY(20);
//        facebook_code.setPreserveRatio(true);
        textFlow1.getChildren().addAll(text1, twitter_image, text5, facebook_image);
        footerPane.setHalignment(textFlow1,HPos.CENTER);
        footerPane.setConstraints(textFlow1, 0, 0);
        footerPane.getChildren().add(textFlow1);
        
        textFlow2.getChildren().addAll(text6,text7,text8, text9);
        footerPane.setHalignment(textFlow2,HPos.CENTER);
        footerPane.setConstraints(textFlow2, 0, 1);
        footerPane.getChildren().add(textFlow2);
        
        textFlow3.getChildren().addAll(text10);
        footerPane.setHalignment(textFlow3,HPos.CENTER);
        footerPane.setConstraints(textFlow3, 0, 2);
        footerPane.getChildren().add(textFlow3);
        
        
        


        return footerPane;
    }    

    public GridPane clockPane() {
      
        GridPane clockPane = new GridPane();
        clockPane.setId("clockPane");
//        clockPane.setVgap(30);

        
        clockPane.getColumnConstraints().setAll(
                ColumnConstraintsBuilder.create().minWidth(150).build(),
                ColumnConstraintsBuilder.create().minWidth(150).build()     
        );
        
        clockPane.setHgap(0);
        clockPane.setGridLinesVisible(false);
//        clockPane.setMaxHeight(50);
        
        
        hour_Label.setId("hour");  
        clockPane.setHalignment(hour_Label,HPos.CENTER);
        clockPane.setConstraints(hour_Label, 0, 0);
        clockPane.getChildren().add(hour_Label);
        
        minute_Label.setId("minute");  
        clockPane.setHalignment(minute_Label,HPos.LEFT);
        clockPane.setValignment(minute_Label,VPos.CENTER);
        clockPane.setConstraints(minute_Label, 1, 0);
        clockPane.getChildren().add(minute_Label);
        
        date_Label.setId("date");
        clockPane.setHalignment(date_Label,HPos.CENTER);
        clockPane.setConstraints(date_Label, 0, 1,2,1);
        clockPane.getChildren().add(date_Label);
        return clockPane;
    }
    
    
    
    
 }



                        
//                        
//                       
//
//                        Locale locale = Locale.getDefault();
//        TimeZone localTimeZone = TimeZone.getDefault(); 
//        //TimeZone localTimeZone = TimeZone.getTimeZone(timeZone_ID);
//        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
//        dateFormat.setTimeZone(localTimeZone);
//        Date rightNow = new Date();
//        System.out.println(locale.toString() + ": " + dateFormat.format(rightNow));
//                        
//                        
// 

//ProcessBuilder processBuilder = 
//                            new ProcessBuilder("bash", "-c", "aplay /home/pi/javafx/examples/PrayerTime/src/Audio/athan1.wav");
//                            try {
//                                Process process = processBuilder.start();
//                                InputStream stderr = process.getErrorStream();
//                                InputStreamReader isr = new InputStreamReader(stderr);
//                                BufferedReader br = new BufferedReader(isr);
//                                String line = null;
//                                while ((line = br.readLine()) != null) {
//                                    System.out.println(line);
//                                }
//                            } catch (IOException ex) {
//                                logger.warn("Unexpected error", e);
//                            }
