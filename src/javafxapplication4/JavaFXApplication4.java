/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
26/11/13 from windows XP: Uncomment Lines 227, 214 - 221, 891 - 892 to work on raspberry pi

*/



package javafxapplication4;

//import java.util.Locale;
//import org.joda.time.*;
//import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.RowConstraintsBuilder;
import java.util.Calendar;
import com.bradsbrain.simpleastronomy.MoonPhaseFinder;
import java.sql.Time;
import java.text.ParseException;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import me.shanked.nicatronTg.jPushover.Pushover;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.Format;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
//import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
//import org.joda.time.chrono.JulianChronology;

/**
 *
 * @author ossama
 */
   
    public class JavaFXApplication4 extends Application {
    
    private Boolean debug    = false;  //  <<========================== Debuger  
    
    private Date fullMoon= null; //  <<========================== might fix errors at startup
    private Date newMoon= null; //  <<========================== might fix errors at startup
    
    private Process p;
    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

    private ObservableList data;
    
    private Integer moonPhase;
    private Boolean isWaning;
    private Boolean sensorLow = false;
    private Boolean hdmiOn = true;
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
    private Boolean fajr_jamaat_update_enable = true;
    private Boolean zuhr_jamaat_update_enable = true;
    private Boolean asr_jamaat_update_enable = true;
    private Boolean maghrib_jamaat_update_enable = true;
    private Boolean isha_jamaat_update_enable = true;
    private boolean  notification_Sent;
    private boolean arabic = true;
    private boolean english = false;
    private boolean moon_hadith_Label_visible = false;
    private boolean hadith_Label_visible = false;
            
    private String hadith, ar_full_moon_hadith, en_full_moon_hadith, ar_moon_notification, en_moon_notification, announcement, en_notification_Msg, ar_notification_Msg;
    private String ar_notification_Msg_Lines[], en_notification_Msg_Lines[], notification_Msg, facebook_moon_notification_Msg;    
    private String fajr_jamaat ,zuhr_jamaat ,asr_jamaat ,maghrib_jamaat ,isha_jamaat ;
    private String labeconv;
    private String friday_jamaat, future_zuhr_jamaat_time;
    private String future_fajr_jamaat ,future_zuhr_jamaat ,future_asr_jamaat ,future_maghrib_jamaat ,future_isha_jamaat ;
    private String en_message_String, ar_message_String; 
    
    private int id;
    int olddayofweek_int;
    private Date prayer_date,future_prayer_date;
    private Calendar fajr_cal, sunrise_cal, duha_cal, zuhr_cal, asr_cal, maghrib_cal, isha_cal,old_today;
    private Calendar fajr_jamaat_update_cal, duha_jamaat_update_cal, zuhr_jamaat_update_cal, asr_jamaat_update_cal, maghrib_jamaat_update_cal, isha_jamaat_update_cal;
    private Calendar future_fajr_jamaat_cal, future_zuhr_jamaat_cal, future_asr_jamaat_cal, future_maghrib_jamaat_cal, future_isha_jamaat_cal;
    private Calendar notification_Date_cal;
    
    private Date fajr_begins_time,fajr_jamaat_time, sunrise_time, duha_time, zuhr_begins_time, zuhr_jamaat_time, asr_begins_time, asr_jamaat_time, maghrib_begins_time, maghrib_jamaat_time,isha_begins_time, isha_jamaat_time;
    private Date future_fajr_jamaat_time, future_asr_jamaat_time, future_maghrib_jamaat_time,future_isha_jamaat_time;
    private Date notification_Date;   

    private Label fajr_hourLeft, fajr_hourRight, fajr_minLeft, fajr_minRight, fajr_jamma_hourLeft, fajr_jamma_hourRight, fajr_jamma_minLeft, fajr_jamma_minRight;
    private Label sunrise_hourLeft, sunrise_hourRight, sunrise_minLeft, sunrise_minRight;
    private Label time_Separator1, time_Separator2, time_Separator3, time_Separator4, time_Separator5, time_Separator6,time_Separator8, time_jamma_Separator1, time_jamma_Separator2, time_jamma_Separator3, time_jamma_Separator4 ,time_jamma_Separator5; 
    private Label zuhr_hourLeft, zuhr_hourRight, zuhr_minLeft, zuhr_minRight, zuhr_jamma_hourLeft, zuhr_jamma_hourRight, zuhr_jamma_minLeft, zuhr_jamma_minRight;
    private Label asr_hourLeft, asr_hourRight, asr_minLeft, asr_minRight, asr_jamma_hourLeft, asr_jamma_hourRight, asr_jamma_minLeft, asr_jamma_minRight;
    private Label maghrib_hourLeft, maghrib_hourRight, maghrib_minLeft, maghrib_minRight, maghrib_jamma_hourLeft, maghrib_jamma_hourRight, maghrib_jamma_minLeft, maghrib_jamma_minRight;
    private Label isha_hourLeft, isha_hourRight, isha_minLeft, isha_minRight, isha_jamma_hourLeft, isha_jamma_hourRight, isha_jamma_minLeft, isha_jamma_minRight;
    private Label friday_hourLeft, friday_hourRight, friday_minLeft, friday_minRight;
    private Label Phase_Label, Moon_Date_Label, Moon_Image_Label, friday_Label_eng,friday_Label_ar,sunrise_Label_ar,sunrise_Label_eng, fajr_Label_ar, fajr_Label_eng, zuhr_Label_ar, zuhr_Label_eng, asr_Label_ar, asr_Label_eng, maghrib_Label_ar, maghrib_Label_eng, isha_Label_ar, isha_Label_eng, jamaat_Label_eng,jamaat_Label_ar, athan_Label_eng,athan_Label_ar, hadith_Label, announcement_Label,athan_Change_Label_L1, athan_Change_Label_L2, hour_Label, minute_Label, date_Label, divider1_Label, divider2_Label, ar_moon_hadith_Label_L1, ar_moon_hadith_Label_L2, en_moon_hadith_Label_L1, en_moon_hadith_Label_L2;
    
    private long moonPhase_lastTimerCall,translate_lastTimerCall,sensor_lastTimerCall, debug_lastTimerCall;
    private AnimationTimer moonPhase_timer, translate_timer ,debug_timer ;
        
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

    @Override public void init() throws IOException {
        
        
// Twitter ==============================
    
//        String Test = "This is a test message"; 
//        Twitter twitter = TwitterFactory.getSingleton();
//        Status status = null;
//        try {
//             status = twitter.updateStatus(Test);
//        } catch (TwitterException ex) {
//            Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("Successfully updated the status to [" + status.getText() + "].");
        moonPhase = 200;
        
        FacebookClient facebookClient = new DefaultFacebookClient("CAAJRZCld8U30BAMmPyEHDW2tlR07At1vTmtHEmD8iHtiFWx7D2ZBroCVWQfdhxQ7h2Eohv8ZBPRk85vs2r7XC0K4ibGdFNMTkh0mJU8vui9PEnpvENOSAFD2q7CQ7NJXjlyK1yITmcrvZBAZByy4qV7whiAb2a2SN7s23nYvDgMMG3RhdPIakZBLV39pkksjYZD");
        
        
// Pushover ==============================       
        
        //https://github.com/nicatronTg/jPushover
        Pushover p = new Pushover("WHq3q48zEFpTqU47Wxygr3VMqoodxc", "skhELgtWRXslAUrYx9yp1s0Os89JTF");
        try 
        {
            p.sendMessage("Prayer Time Ibn Abass starting");
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
                            

        
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
        Process process = processBuilder.start();
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//               System.out.println(line);
//            } 
       
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
        GridPane Mainpane = new GridPane();
        
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
        
    
        athan_Label_ar.setId("prayer-label-arabic");
        athan_Label_ar.setText("الأذان");
        prayertime_pane.setHalignment(athan_Label_ar,HPos.CENTER) ;
        athan_Label_eng.setId("prayer-label-english");
        athan_Label_eng.setText("Athan");
        prayertime_pane.setHalignment(athan_Label_eng,HPos.CENTER);
        
        jamaat_Label_ar.setId("prayer-label-arabic");
        jamaat_Label_ar.setText("إقامة");
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
                        
                        Moon m = new Moon();
                        moonPhase = m.illuminatedPercentage();
                        isWaning = m.isWaning();
                        update_moon_image = true;
                        System.out.println("The moon is " + moonPhase + "% full and " + (isWaning ? "waning" : "waxing"));
                    
                    
                        Locale.setDefault(new Locale("en", "AU"));
                        Date now = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(now);
//                        cal.add(Calendar.DAY_OF_MONTH, -3);
//                        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        cal.setFirstDayOfWeek(Calendar.MONDAY);
                        int dayofweek_int = cal.get(Calendar.DAY_OF_WEEK);
                        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        PrayTime getprayertime = new PrayTime();

                        // Bankstown NSW Location
                        double latitude = -33.9172891;
                        double longitude = 151.035882;
                        double timezone = 10;
                        

                        getprayertime.setTimeFormat(0);
                        getprayertime.setCalcMethod(4);
                        getprayertime.setAsrJuristic(1);
                        getprayertime.setAdjustHighLats(0);
                        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
                        getprayertime.tune(offsets);
                        
                        
                        Date time = cal.getTime();
                        System.out.println(" daylight saving? " + TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time ));
                        
//                        The following calculate the next daylight saving date
                        DateTimeZone zone = DateTimeZone.forID("Australia/Sydney");        
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
                        if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date fajr = cal.getTime();
                        fajr_cal = Calendar.getInstance();
                        fajr_cal.setTime(fajr);
                        fajr_begins_time = fajr_cal.getTime();
                        System.out.println(" fajr time " + fajr_begins_time);
                        
                        Date sunrise_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(1)).getTime()));
                        cal.setTime(sunrise_temp);
                        if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
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
                        if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date zuhr = cal.getTime();
                        zuhr_cal = Calendar.getInstance();
                        zuhr_cal.setTime(zuhr);
                        zuhr_begins_time = zuhr_cal.getTime();
                        System.out.println(" Zuhr time " + zuhr_begins_time);
                        
                        Date asr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(3)).getTime()));
                        cal.setTime(asr_temp);
                        if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date asr = cal.getTime();
                        asr_cal = Calendar.getInstance();
                        asr_cal.setTime(asr);
                        asr_begins_time = asr_cal.getTime();
                        System.out.println(" Asr time " + asr_begins_time);
                        
                        Date maghrib_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(5)).getTime()));
                        cal.setTime(maghrib_temp);
                        if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date maghrib = cal.getTime();
                        maghrib_cal = Calendar.getInstance();
                        maghrib_cal.setTime(maghrib);
                        maghrib_begins_time = maghrib_cal.getTime();
                        System.out.println(" maghrib time " + maghrib_begins_time);
                        
                        Date isha_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + new Time(formatter.parse(prayerTimes.get(6)).getTime()));
                        cal.setTime(isha_temp);
                        if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){cal.add(Calendar.MINUTE, 60);}
                        Date isha = cal.getTime();
                        isha_cal = Calendar.getInstance();
                        isha_cal.setTime(isha);
                        isha_begins_time = isha_cal.getTime();
                        System.out.println(" isha time " + isha_begins_time);
                        
//                        set friday prayer here
                        if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){friday_jamaat = "13:30";}
                        else{friday_jamaat = "12:30";}
           
                        update_prayer_labels = true;
                        
                        DateTime DateTime_now = new DateTime();    
                        Calendar Calendar_now = Calendar.getInstance();
                        Calendar_now.setTime(new Date());
                        Calendar_now.set(Calendar.MILLISECOND, 0);
                        Calendar_now.set(Calendar.SECOND, 0);
                        Calendar_now.set(Calendar.MINUTE, 0);
                        Calendar_now.set(Calendar.HOUR_OF_DAY, 0);

                        
                        //enable athan play time
                        if (dayofweek_int != olddayofweek_int)
                        {    
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
                            
                            c = DBConnect.connect();
//                            System.out.println("connected");
                            String SQL = "select * from prayertimes where DATE(date) = DATE(NOW())";
                            ResultSet rs = c.createStatement().executeQuery(SQL);
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
                            if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){zuhr_jamaat = "13:30";} else{zuhr_jamaat = "12:30";}
                            asr_jamaat = asr_jamaat_time.toString();
                            isha_jamaat = isha_jamaat_time.toString();
                            // print the results
                            System.out.format("%s,%s,%s,%s,%s \n", id, prayer_date, fajr_jamaat, asr_jamaat, isha_jamaat );
                        
                        
                            
                            Date fajr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + fajr_jamaat);
                            cal.setTime(fajr_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date fajr_jamaat = cal.getTime();
                            fajr_jamaat_update_cal = Calendar.getInstance();
                            fajr_jamaat_update_cal.setTime(fajr_jamaat);
                            fajr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            fajr_jamaat_update_cal.set(Calendar.SECOND, 0);
                            System.out.println("fajr Jamaat update scheduled at:" + fajr_jamaat_update_cal.getTime());


                            
                            Date asr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + asr_jamaat);
                            cal.setTime(asr_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date asr_jamaat = cal.getTime();
                            asr_jamaat_update_cal = Calendar.getInstance();
                            asr_jamaat_update_cal.setTime(asr_jamaat);
                            asr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            asr_jamaat_update_cal.set(Calendar.SECOND, 0);
                            System.out.println("asr Jamaat update scheduled at:" + asr_jamaat_update_cal.getTime());

                            
                            Date isha_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + isha_jamaat);
                            cal.setTime(isha_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date isha_jamaat = cal.getTime();
                            isha_jamaat_update_cal = Calendar.getInstance();
                            isha_jamaat_update_cal.setTime(isha_jamaat);
                            isha_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            isha_jamaat_update_cal.set(Calendar.SECOND, 0);
                            System.out.println("Isha Jamaat update scheduled at:" + isha_jamaat_update_cal.getTime());

                            
//==============Prayer time change notification logic + 7days
// check excel file in documentation folder for a flow chart                            
                            
                            // check if a notification has already been sent, to avoid flooding users with notifications, i.e during a system restart
                            ar_notification_Msg_Lines = null;
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
                            
                            notification_Date_cal = Calendar.getInstance();
                            notification_Date_cal.setTime(notification_Date);
                            notification_Date_cal.set(Calendar.MILLISECOND, 0);
                            notification_Date_cal.set(Calendar.SECOND, 0);
                            
                            System.out.println("notification_Date_cal:" + notification_Date_cal.getTime());
                            System.out.println("Calendar_now:         " + Calendar_now.getTime());
                            
                            if (Calendar_now.compareTo(notification_Date_cal) <0 )  //&& !notification_Sent
                            {
                                en_notification_Msg = en_message_String;
                                ar_notification_Msg = ar_message_String;                                
                                ar_notification_Msg_Lines = ar_notification_Msg.split("\\r?\\n");
                                en_notification_Msg_Lines = en_notification_Msg.split("\\r?\\n");
                                athan_Change_Label_visible = true;
                            }
                            
                            if (Calendar_now.compareTo(notification_Date_cal) >=0 )  //&& !notification_Sent
                            {
                                athan_Change_Label_visible = false;

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

                            fullMoon = MoonPhaseFinder.findFullMoonFollowing(Calendar.getInstance());
                            newMoon = MoonPhaseFinder.findNewMoonFollowing(Calendar.getInstance());
                            System.out.println("The moon is full on " + fullMoon );
                            System.out.println("The moon is new on " + newMoon );                       
                            if(newMoon.before(fullMoon)){System.out.println("New moon is before full moon");}
                            else {System.out.println("Full moon is before new moon" );}
                            
// ======= Notification for full moon 5 days earlier, 2 days before fasting period
                            
                            if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <= 5 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() > 3)
                            {
                                System.out.println("=================  x days left to full moon==================== " );
                                
                                //hide hadith label boolean
//                                getHadith = false;
                                hadith_Label_visible = false;
                                //show moon notification label boolean
                                moon_hadith_Label_visible = true;

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
                                
                                if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() == 5)
                                {

                                    if (fullMoon.getHours()>maghrib_cal.getTime().getHours()){ Moon_Date_Label.setText("سيكون القمر بدرا ليلة الغذٍٍُِِِ" );}
                                    else{Moon_Date_Label.setText("سيكون القمر بدرا غدآ" );}
                                    
                                    
                                    
                                    
                                    ar_moon_notification = "إخوتي وأخواتي في الله ، نذكّركم وأنفسنا بفضل صيام الايام البيض من كل شهر, التي تبدأ من بعد غد ان شاء الله. إن أستطعت الصيام فصم وذكر أحبابك ";
                                    en_moon_notification = "We would like to remind our dear brothers & sisters that this month's \"White days\" will start after tomorrow, it is recommended to fast these days";
                                    facebook_moon_notification_Msg = ar_moon_notification + "\n\n" + en_moon_notification;
//                                    try {facebookClient.publish("187050104663230/feed", FacebookType.class, Parameter.with("message", facebook_moon_notification_Msg));}
//                                    catch (FacebookException e){Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, e);}                           
                                    System.out.println("Full Moon Notification Sent to Facebook:" );
                                    System.out.println(facebook_moon_notification_Msg);
                                    
                                }
                                
                                if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() == 4)
                                {
                                    ar_moon_notification = "إخوتي وأخواتي في الله ، نذكّركم وأنفسنا بفضل صيام الايام البيض من كل شهر, التي تبدأ غدا ان شاء الله. إن أستطعت الصيام فصم وذكر أحبابك";
                                    en_moon_notification = "We would like to remind our dear brothers & sisters that this month's 'White days' will start tomorrow, it is recommended to fast these days";
                                }
                                
                                
                            }
                            
                            else 
                            {
                                getHadith = true;                                
                                //hide moon notification label boolean
                                moon_hadith_Label_visible = false;
                                //show hadith label boolean
                                hadith_Label_visible = true;
                            }
                            
                        }                      
                        
// Prayer time change notification/////////////////////put this in a thread, so error does not stop code further down
// creates message to send to facebook
// creates labels for notification
                        
                        if (notification)
                        {
                            ar_notification_Msg_Lines = null;
                            Calendar_now.setTime(future_prayer_date);
                            int day = Calendar_now.get(Calendar.DAY_OF_MONTH);
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
                                if (TimeZone.getTimeZone( "Australia/Sydney").inDaylightTime( time )){future_zuhr_jamaat_time = "13:30";}
                                else{future_zuhr_jamaat_time = "12:30";}  
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
                            
                            c = DBConnect.connect();
                            Statement st = (Statement) c.createStatement(); 
                            st.executeUpdate("UPDATE prayertime.notification SET en_message_String='" + en_notification_Msg + "' ORDER BY id DESC LIMIT 1");
                            st.executeUpdate("UPDATE prayertime.notification SET ar_message_String= '" + ar_notification_Msg + "' ORDER BY id DESC LIMIT 1");
                            c.close();
                            ar_notification_Msg_Lines = ar_notification_Msg.split("\\r?\\n");
                            en_notification_Msg_Lines = en_notification_Msg.split("\\r?\\n");
                            
                            
                            notification = false;
                            athan_Change_Label_visible = true;
                            
                            notification_Msg = ar_notification_Msg_Lines[0] + "\n" + ar_notification_Msg_Lines[1] + "\n\n" + en_notification_Msg_Lines[0] + "\n" + en_notification_Msg_Lines[1];
                            System.out.println(notification_Msg );
                            
//                            Twitter twitter = TwitterFactory.getSingleton();
//                            Status status = null;
//                            try {status = twitter.updateStatus(notification_Msg);} 
//                            catch (TwitterException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
//                            System.out.println("Successfully updated the status to [" + status.getText() + "].");
                            System.out.println("Notification Sent to Facebook" );
                            
                            try {facebookClient.publish("187050104663230/feed", FacebookType.class, Parameter.with("message", notification_Msg));}
                            catch (FacebookException e){Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, e);}
                            
                            try {p.sendMessage(en_notification_Msg);} 
                            catch (IOException e){e.printStackTrace();}
                        }        
                                
                        if (isStarting){isStarting = false;}

// Get Daily Hadith =================================================================================
                        if (getHadith)
                        {
                            getHadith = false;
                            c = DBConnect.connect();
                            //SQL FOR SELECTING NATIONALITY OF CUSTOMER
                            String SQL = "select * from hadith WHERE day IS NULL order by rand() limit 1";
                            ResultSet rs = c.createStatement().executeQuery(SQL);
                            while (rs.next()) 
                            {
                                hadith = rs.getString("hadith");
                            }
                            c.close();
                            System.out.format("hadith: %s\n", hadith );

                        }
                     } 

                catch(SQLException e)
                {
                    System.out.println("Error on Database connection");
                    Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, e);
                }
                catch (ParseException ex){Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
                catch (Exception ex){Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
                
             
            }
        }, 0, 3600000);   
//        }, 0, 240000);        
        
// Timer to traslate labels from arabic to english on the screen====================================================
        
//        translate_lastTimerCall = System.nanoTime();
        translate_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > translate_lastTimerCall + 15000_000_000l) 
                {
                    try {update_labels();} 
                    catch (Exception ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
                    translate_lastTimerCall = now;
                }
            }
        };
 
       

// PIR sensor thread to turn on/Off TV screen to save energy ===============================================================        
        new Thread(() -> 
        {
             final GpioController gpioSensor = GpioFactory.getInstance();
             sensor_lastTimerCall = System.currentTimeMillis();
             final GpioPinDigitalInput sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);
             
             sensor.addListener(new GpioPinListenerDigital() 
             {
                 @Override
                 public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) 
                 {

                     if (event.getState().isHigh()) 
                     {
                         sensor_lastTimerCall = System.currentTimeMillis();
                         if(!hdmiOn)
                         {
                            ProcessBuilder processBuilder1 = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
                            hdmiOn = true;
                            System.out.println("Tv turned on");
                            try {Process process1 = processBuilder1.start();}
                            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}   
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
                     long now = System.currentTimeMillis();
                     if (System.currentTimeMillis() > sensor_lastTimerCall + 360000 && sensorLow) 
                     {
                         System.out.println("All is quiet...");
                         ProcessBuilder processBuilder2 = new ProcessBuilder("bash", "-c", "echo \"standby 0000\" | cec-client -d 1 -s \"standby 0\" RPI");
                         hdmiOn = false;
                         try {Process process2 = processBuilder2.start();}
                         catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
                         sensor_lastTimerCall = now;
                         sensorLow = false;
                     }
                     Thread.sleep(1000);
                 }
                 catch(InterruptedException ex) 
                 {
                     gpioSensor.shutdown();
                     Thread.currentThread().interrupt();
                 }
             }

         }).start();

    }
//===============================================================================================================================================
    
    @Override public void start(Stage stage) {
        
        Pane root = new Pane();
        // rotate tv screen to portrait mode
        // edit the /boot/config.txt file Copy stored in documentation folder (i.e. ramebuffer_width=1080   framebuffer_height=1920  display_rotate=1...)
        Scene scene = new Scene(root, 1080, 1920);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
                
        Mainpane = new GridPane();        
        String image = JavaFXApplication4.class.getResource("/Images/green-wallpaper.jpg").toExternalForm();
//        Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: repeat; ");  
        Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-image-repeat: repeat; -fx-background-size: 1080 1920;-fx-background-position: bottom left;");        
        
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
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/22.0).build()
        );
//        Mainpane.setGridLinesVisible(true);
        Mainpane.setId("Mainpane");
        GridPane prayertime_pane = prayertime_pane();    
        GridPane Moonpane =   moonpane();
        GridPane hadithPane = hadithPane();
        GridPane clockPane =   clockPane();
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
        Mainpane.add(prayertime_pane, 1, 4,11,8);  
        Mainpane.add(hadithPane, 1, 13,11,8);
//        Mainpane.add(footerPane, 1, 20,11,2);
//        Mainpane.setCache(true);
        scene.setRoot(Mainpane);
        stage.show();
        translate_timer.start(); 
        
        
                new Thread()
        {
            public void run() 
            {
                for (;;) 
                {
                    try 
                    {
                       Thread.sleep(360000);
                        moon_hadith_Label_visible = false;
                                //show hadith label boolean
                                hadith_Label_visible = true;
                       
                    }
                    catch (Exception ex) 
                    {
                        Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

public void update_labels() throws Exception{
    
        DateTime DateTime_now = new DateTime();    
        Calendar Calendar_now = Calendar.getInstance();
        Calendar_now.setTime(new Date());
        Calendar_now.set(Calendar.MILLISECOND, 0);
        Calendar_now.set(Calendar.SECOND, 0);
        
        
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
                hadith_Label.setMinHeight(0);
            }
            
            if (moon_hadith_Label_visible)
            {
                
                hadith_Label.setVisible(false);
                hadith_Label.setMinHeight(0);
                hadith_Label.setText("");
                
                ar_moon_hadith_Label_L1.setVisible(false);
                en_moon_hadith_Label_L1.setVisible(true);
                en_moon_hadith_Label_L1.setText(en_full_moon_hadith);
                en_moon_hadith_Label_L1.setId("hadith-text-english");
                hadithPane.setHalignment(en_moon_hadith_Label_L1,HPos.LEFT);
                
                ar_moon_hadith_Label_L2.setVisible(false);
                en_moon_hadith_Label_L2.setVisible(true);
                en_moon_hadith_Label_L2.setText(en_moon_notification);
                en_moon_hadith_Label_L2.setId("en_moon-notification-text");
                hadithPane.setHalignment(en_moon_hadith_Label_L2,HPos.LEFT);

  
            }
            
            if (!athan_Change_Label_visible)
            {
                athan_Change_Label_L1.setVisible(false);
                athan_Change_Label_L2.setVisible(false);
                divider1_Label.setVisible(false);
            }
            
            if (athan_Change_Label_visible)
            {
                athan_Change_Label_L1.setVisible(true);
                athan_Change_Label_L2.setVisible(true);
                divider1_Label.setVisible(true);
                athan_Change_Label_L1.setId("en_athan-change-text");
                hadithPane.setHalignment(athan_Change_Label_L1,HPos.LEFT);
                athan_Change_Label_L1.setText(en_notification_Msg_Lines[0]);
                athan_Change_Label_L2.setId("en_athan-change-textL2");
                hadithPane.setHalignment(athan_Change_Label_L2,HPos.LEFT);
                athan_Change_Label_L2.setText(en_notification_Msg_Lines[1]);
            }
                        
            String hour = new SimpleDateFormat("kk").format(Calendar_now.getTime());
            hour_Label.setText(hour);
            String minute = new SimpleDateFormat(":mm").format(Calendar_now.getTime());
            minute_Label.setText(minute);
            String date = new SimpleDateFormat("EEEE, d MMMM").format(Calendar_now.getTime());
            date_Label.setText(date);
            
            Calendar_now.setTime(newMoon);
            int day = Calendar_now.get(Calendar.DAY_OF_MONTH);
            String dayStr = day + suffixes[day];

            if (newMoon.before(fullMoon))
            {
                String FullMoon_date_en = new SimpleDateFormat("' of ' MMMM").format(newMoon);
                Moon_Date_Label.setId("moon-text-english");
                Moon_Date_Label.setText("Next New Moon is on\n" + " the "+ dayStr + FullMoon_date_en);
                Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                english = true;
                arabic = false;   
            }
            
            else
            {
                if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <= 7 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() >1)
                {
                    String FullMoon_date_en = new SimpleDateFormat("EEEE").format(fullMoon);
                    String FullMoon_date_en1 = new SimpleDateFormat("' of ' MMMM").format(fullMoon);

                    Moon_Date_Label.setId("moon-text-english");

                    if (fullMoon.getHours()>maghrib_cal.getTime().getHours()){Moon_Date_Label.setText("Full moon is on next\n" + FullMoon_date_en + " night "  + dayStr + FullMoon_date_en1);}
                    else{Moon_Date_Label.setText("Full moon is on next\n" + FullMoon_date_en + " the "  + dayStr  + FullMoon_date_en1);}

                    Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                    english = true;
                    arabic = false;
                }

                else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() == 0)
                {
                    Moon_Date_Label.setId("moon-text-english");

                    if (fullMoon.getHours()>maghrib_cal.getTime().getHours()){Moon_Date_Label.setText("The moon is full tonight" );}
                    else{Moon_Date_Label.setText("The moon is full today" );}

                    Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                    english = true;
                    arabic = false;
                }

                else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() >0 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <=1 )
                {
                    Moon_Date_Label.setId("moon-text-english");
                    if (fullMoon.getHours()>maghrib_cal.getTime().getHours()){Moon_Date_Label.setText("Full moon is on\n"+ "tomorrow night");}
                    else{Moon_Date_Label.setText("Full moon is on\n"+ "tomorrow");}
                    Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                    english = true;
                    arabic = false;
                }

                else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <10 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() > 7)
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
                hadith_Label.setVisible(false);
                hadith_Label.setMinHeight(0);
                hadith_Label.setText("");
                en_moon_hadith_Label_L1.setVisible(false);
                ar_moon_hadith_Label_L1.setVisible(true);
                hadithPane.setHalignment(ar_moon_hadith_Label_L1,HPos.RIGHT);
                ar_moon_hadith_Label_L1.setText(ar_full_moon_hadith);
                ar_moon_hadith_Label_L1.setId("hadith-text-arabic");
                
                en_moon_hadith_Label_L2.setVisible(false);
                ar_moon_hadith_Label_L2.setVisible(true);
                hadithPane.setHalignment(ar_moon_hadith_Label_L2,HPos.RIGHT);
                ar_moon_hadith_Label_L2.setText(ar_moon_notification);
                ar_moon_hadith_Label_L2.setId("ar_moon-notification-text");

                
            }
            
            
            if (!athan_Change_Label_visible)
            {
                athan_Change_Label_L1.setVisible(false);
                athan_Change_Label_L2.setVisible(false);
                divider1_Label.setVisible(false);
            }
            
            if (athan_Change_Label_visible)
            {
                athan_Change_Label_L1.setVisible(true);
                athan_Change_Label_L2.setVisible(true);
                divider1_Label.setVisible(true);
                athan_Change_Label_L1.setText(ar_notification_Msg_Lines[0]);
                athan_Change_Label_L1.setId("ar_athan-change-text");
                hadithPane.setHalignment(athan_Change_Label_L2,HPos.RIGHT);
                athan_Change_Label_L2.setText(ar_notification_Msg_Lines[1]);
                athan_Change_Label_L2.setId("ar_athan-change-textL2");
            }
            
            if (newMoon.before(fullMoon))
            {
                String FullMoon_date_ar = new SimpleDateFormat(" EEEE d MMMM", new Locale("ar")).format(newMoon);               
                    labeconv = "سيظهر الهلال يوم\n" + FullMoon_date_ar;
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
                if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <= 7 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() >1)
                {
                    String FullMoon_date_ar = new SimpleDateFormat("' 'EEEE", new Locale("ar")).format(fullMoon);
                    String FullMoon_date_ar1 = new SimpleDateFormat("d MMMM", new Locale("ar")).format(fullMoon);


                    if (fullMoon.getHours()>maghrib_cal.getTime().getHours()){ labeconv = "سيكون القمر بدرا ليلة\n" + FullMoon_date_ar + " القادم" +""+ FullMoon_date_ar1;}
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

                else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() >0 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <=1 )
                {
                    Moon_Date_Label.setId("moon-text-arabic");
                    if (fullMoon.getHours()>maghrib_cal.getTime().getHours()){ Moon_Date_Label.setText("سيكون القمر بدرا ليلة الغذٍٍُِِِ" );}
                    else{Moon_Date_Label.setText("سيكون القمر بدرا غدآ" );}
                    Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                    english = false;
                    arabic = true;
                }

                else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() == 0)
                {
                    Moon_Date_Label.setId("moon-text-arabic");
                    if (fullMoon.getHours()>maghrib_cal.getTime().getHours()){Moon_Date_Label.setText("القمر بدر ليلة اليوم" );}
                    else{Moon_Date_Label.setText("القمر بدر اليوم " );}
                    Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                    english = false;
                    arabic = true;
                }

                else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <10 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() > 7)
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
        
//==Days left to full moon============================================================        
 

//==prayer alarms =================================================================================
       
//        URL url = this.getClass().getClassLoader().getResource("Audio/athan1.wav");
//        AudioFormat adFormat = getAudioFormat();
//        Clip clip = AudioSystem.getClip();
//        AudioInputStream ais = AudioSystem.getAudioInputStream( url );
        
        
        ProcessBuilder processBuilder_Athan = new ProcessBuilder("bash", "-c", "aplay /home/pi/javafx/examples/PrayerTime/src/Audio/athan1.wav");
        ProcessBuilder processBuilder_Duha = new ProcessBuilder("bash", "-c", "aplay /home/pi/javafx/examples/PrayerTime/src/Audio/duha.wav");
//                            try {
//                                Process process = processBuilder.start();                                
//                            } catch (IOException ex) {
//                                Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                            
        
        
//        URL url = this.getClass().getClassLoader().getResource("Audio/athan1.wav");
//        AudioInputStream ais = AudioSystem.getAudioInputStream(url); 
//        AudioFormat littleEndianFormat = getAudioFormat();
//        AudioInputStream converted = AudioSystem.getAudioInputStream(littleEndianFormat, ais); 
//        Clip clip = AudioSystem.getClip();
        
        ProcessBuilder processBuilder_Tvon = new ProcessBuilder("bash", "-c", "echo \"as\" | cec-client -d 1 -s \"standby 0\" RPI");
//clip.open(converted);
//            clip.start();
        
        if (duha_cal.equals(Calendar_now) && duha_athan_enable) 
        {
            duha_athan_enable = false;
            System.out.println("Duha Time");
//            String image = JavaFXApplication4.class.getResource("/Images/sunrise.png").toExternalForm();
//            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-image-repeat: repeat; -fx-background-size: 1080 1920;-fx-background-position: bottom left;");
            sensor_lastTimerCall = System.currentTimeMillis();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Duha.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}

        }

        else if (fajr_cal.equals(Calendar_now) && fajr_athan_enable) 
        {
            fajr_athan_enable = false;
            System.out.println("fajr Time");
//            clip.open(converted);
//            clip.start();
            sensorLow = true;
            sensor_lastTimerCall = System.currentTimeMillis();
            
            try {Process process = processBuilder_Tvon.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
  
        }
        
        else if (zuhr_cal.equals(Calendar_now) && zuhr_athan_enable) 
        {
            zuhr_athan_enable = false;
            System.out.println("zuhr Time");
//            clip.open(converted);
//            clip.start();
            sensor_lastTimerCall = System.currentTimeMillis();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
        }        

        else if (asr_cal.equals(Calendar_now) && asr_athan_enable) 
        {
            asr_athan_enable = false;
            System.out.println("asr Time");
            sensor_lastTimerCall = System.currentTimeMillis();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
        } 
        
        else if (maghrib_cal.equals(Calendar_now) && maghrib_athan_enable) 
        {
            maghrib_athan_enable = false;
            System.out.println("maghrib Time");
            sensor_lastTimerCall = System.currentTimeMillis();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
//            String image = JavaFXApplication4.class.getResource("/Images/wallpaper_sunset.jpg").toExternalForm();
//            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-image-repeat: repeat; -fx-background-size: 1080 1920;-fx-background-position: bottom left;");  
        } 
        
        else if (isha_cal.equals(Calendar_now) && isha_athan_enable) 
        {
            isha_athan_enable = false;
            System.out.println("isha Time");
            sensor_lastTimerCall = System.currentTimeMillis();
            sensorLow = true;
            try {Process process = processBuilder_Tvon.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
            TimeUnit.SECONDS.sleep(3);
            try {Process process = processBuilder_Athan.start();} 
            catch (IOException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
        }      
        
        
        
// Jammat update=========================================================== 
        
        Date now = new Date();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
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
                        String SQL = "select * from prayertimes where DATE(date) = DATE(NOW()) + 1";
                        ResultSet rs = c.createStatement().executeQuery(SQL);
                        while (rs.next())
                        {
                            fajr_jamaat_time =       rs.getTime("fajr_jamaat");
                        }
                        c.close();
                        fajr_jamaat = fajr_jamaat_time.toString();
                        System.out.println("fajr jamaat time updated:" + fajr_jamaat);
                        Date fajr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + fajr_jamaat);
                        cal.setTime(fajr_jamaat_temp);
                        cal.add(Calendar.MINUTE, 15);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        Date fajr_jamaat = cal.getTime();
                        fajr_jamaat_update_cal = Calendar.getInstance();
                        fajr_jamaat_update_cal.setTime(fajr_jamaat);
                        fajr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                        fajr_jamaat_update_cal.set(Calendar.SECOND, 0);
    //                            System.out.println(fajr_jamaat_update_cal.getTime());
                        System.out.println("next update is on:" + fajr_jamaat_update_cal.getTime());
                        TimeUnit.MINUTES.sleep(1);
                        fajr_jamaat_update_enable = true;
                        update_prayer_labels = true;

                    } 
                    catch (SQLException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
                    catch (ParseException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
                    catch (InterruptedException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
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
                        String SQL = "select * from prayertimes where DATE(date) = DATE(NOW()) + 1";
                        ResultSet rs = c.createStatement().executeQuery(SQL);
                        while (rs.next())
                        {
                            asr_jamaat_time =       rs.getTime("asr_jamaat");
                        }
                        c.close();
                        asr_jamaat = asr_jamaat_time.toString();
                        System.out.println("asr jamaat time updated:" + asr_jamaat);
                        Date asr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + asr_jamaat);
                        cal.setTime(asr_jamaat_temp);
                        cal.add(Calendar.MINUTE, 15);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        Date asr_jamaat = cal.getTime();
                        asr_jamaat_update_cal = Calendar.getInstance();
                        asr_jamaat_update_cal.setTime(asr_jamaat);
                        asr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                        asr_jamaat_update_cal.set(Calendar.SECOND, 0);
    //                            System.out.println(fajr_jamaat_update_cal.getTime());
                        System.out.println("next update is on:" + asr_jamaat_update_cal.getTime());
                        TimeUnit.MINUTES.sleep(1);
                        asr_jamaat_update_enable = true;
                        update_prayer_labels = true;

                    } 
                    catch (SQLException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
                    catch (ParseException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
                    catch (InterruptedException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
               }
            }).start();
        }
        
//        if (maghrib_jamaat_update_cal.equals(Calendar_now) && maghrib_jamaat_update_enable )         
//        {       
//            maghrib_jamaat_update_enable = false;
//            new Thread(new Runnable() 
//            {
//                public void run() 
//                {
//                    try {
//                        c = DBConnect.connect();
//                        String SQL = "select * from prayertimes where DATE(date) = DATE(NOW()) + 1";
//                        ResultSet rs = c.createStatement().executeQuery(SQL);
//                        while (rs.next())
//                        {
//                            maghrib_jamaat_time =       rs.getTime("maghrib_jamaat");
//                        }
//                        c.close();
//                        maghrib_jamaat = maghrib_jamaat_time.toString();
//                        System.out.println("maghrib jamaat time updated:" + maghrib_jamaat);
//                        Date maghrib_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + maghrib_jamaat);
//                        cal.setTime(maghrib_jamaat_temp);
//                        cal.add(Calendar.MINUTE, 15);
//                        cal.add(Calendar.DAY_OF_MONTH, 1);
//                        Date maghrib_jamaat = cal.getTime();
//                        maghrib_jamaat_update_cal = Calendar.getInstance();
//                        maghrib_jamaat_update_cal.setTime(maghrib_jamaat);
//                        maghrib_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
//                        maghrib_jamaat_update_cal.set(Calendar.SECOND, 0);
//    //                            System.out.println(fajr_jamaat_update_cal.getTime());
//                        System.out.println("next update is on:" + maghrib_jamaat_update_cal.getTime());
//                        TimeUnit.MINUTES.sleep(1);
//                        maghrib_jamaat_update_enable = true;
//                        update_prayer_labels = true;
//
//                    } 
//                    catch (SQLException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
//                    catch (ParseException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
//                    catch (InterruptedException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
//               }
//            }).start();
//        }
        
        if (isha_jamaat_update_cal.equals(Calendar_now) && isha_jamaat_update_enable )         
        {       
            isha_jamaat_update_enable = false;
            new Thread(new Runnable() 
            {
                public void run() 
                {
                    try {
                        c = DBConnect.connect();
                        String SQL = "select * from prayertimes where DATE(date) = DATE(NOW()) + 1";
                        ResultSet rs = c.createStatement().executeQuery(SQL);
                        while (rs.next())
                        {
                            isha_jamaat_time =       rs.getTime("isha_jamaat");
                        }
                        c.close();
                        isha_jamaat = isha_jamaat_time.toString();
                        System.out.println("isha jamaat time updated:" + isha_jamaat);
                        Date isha_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + isha_jamaat);
                        cal.setTime(isha_jamaat_temp);
                        cal.add(Calendar.MINUTE, 15);
                        cal.add(Calendar.DAY_OF_MONTH, 1);
                        Date isha_jamaat = cal.getTime();
                        isha_jamaat_update_cal = Calendar.getInstance();
                        isha_jamaat_update_cal.setTime(isha_jamaat);
                        isha_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                        isha_jamaat_update_cal.set(Calendar.SECOND, 0);
    //                            System.out.println(fajr_jamaat_update_cal.getTime());
                        System.out.println("next update is on:" + isha_jamaat_update_cal.getTime());
                        TimeUnit.MINUTES.sleep(1);
                        isha_jamaat_update_enable = true;
                        update_prayer_labels = true;

                    } 
                    catch (SQLException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
                    catch (ParseException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);} 
                    catch (InterruptedException ex) {Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);}
               }
            }).start();
        }
        
 
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
////        Date preDefineTime=formatter.parse("10:00");
//        long additionMin=15*60*1000;
//        System.out.println(formatter.format(sunrise));
//        System.out.println(formatter.format(sunrise.getTime()+additionMin));
       
//==Update Prayer time Labels==========================================================        
        
        if (update_prayer_labels) 
        {
            update_prayer_labels = false;
            fajr_hourLeft.setText(fajr_begins_time.toString().substring(11, 12));
            fajr_hourRight.setText(fajr_begins_time.toString().substring(12, 13));
            fajr_minLeft.setText(fajr_begins_time.toString().substring(14, 15));
            fajr_minRight.setText(fajr_begins_time.toString().substring(15, 16));
            
//            sunrise_hourLeft.setText(sunrise_time.toString().substring(11, 12));
//            sunrise_hourRight.setText(sunrise_time.toString().substring(12, 13));
//            sunrise_minLeft.setText(sunrise_time.toString().substring(14, 15));
//            sunrise_minRight.setText(sunrise_time.toString().substring(15, 16));
            

            zuhr_hourLeft.setText(zuhr_begins_time.toString().substring(11, 12));
            zuhr_hourRight.setText(zuhr_begins_time.toString().substring(12, 13));
            zuhr_minLeft.setText(zuhr_begins_time.toString().substring(14, 15));
            zuhr_minRight.setText(zuhr_begins_time.toString().substring(15, 16));
             
            asr_hourLeft.setText(asr_begins_time.toString().substring(11, 12));
            asr_hourRight.setText(asr_begins_time.toString().substring(12, 13));
            asr_minLeft.setText(asr_begins_time.toString().substring(14, 15));
            asr_minRight.setText(asr_begins_time.toString().substring(15, 16));
             
            maghrib_hourLeft.setText(maghrib_begins_time.toString().substring(11, 12));
            maghrib_hourRight.setText(maghrib_begins_time.toString().substring(12, 13));
            maghrib_minLeft.setText(maghrib_begins_time.toString().substring(14, 15));
            maghrib_minRight.setText(maghrib_begins_time.toString().substring(15, 16));
             
            isha_hourLeft.setText(isha_begins_time.toString().substring(11, 12));
            isha_hourRight.setText(isha_begins_time.toString().substring(12, 13));
            isha_minLeft.setText(isha_begins_time.toString().substring(14, 15));
            isha_minRight.setText(isha_begins_time.toString().substring(15, 16));
            
            time_Separator1.setText(":");
            time_Separator2.setText(":");
            time_Separator3.setText(":");
            time_Separator4.setText(":");
            time_Separator5.setText(":");
            time_Separator6.setText(":");
            time_Separator8.setText(":");
            
            fajr_jamma_hourLeft.setText(fajr_jamaat.substring(0, 1));
            fajr_jamma_hourRight.setText(fajr_jamaat.substring(1, 2));
            fajr_jamma_minLeft.setText(fajr_jamaat.substring(3, 4));
            fajr_jamma_minRight.setText(fajr_jamaat.substring(4, 5));
             
            zuhr_jamma_hourLeft.setText(zuhr_jamaat.substring(0, 1));
            zuhr_jamma_hourRight.setText(zuhr_jamaat.substring(1, 2));
            zuhr_jamma_minLeft.setText(zuhr_jamaat.substring(3, 4));
            zuhr_jamma_minRight.setText(zuhr_jamaat.substring(4, 5));
             
            asr_jamma_hourLeft.setText(asr_jamaat.substring(0, 1));
            asr_jamma_hourRight.setText(asr_jamaat.substring(1, 2));
            asr_jamma_minLeft.setText(asr_jamaat.substring(3, 4));
            asr_jamma_minRight.setText(asr_jamaat.substring(4, 5));
             
            maghrib_jamma_hourLeft.setText(maghrib_begins_time.toString().substring(11, 12));
            maghrib_jamma_hourRight.setText(maghrib_begins_time.toString().substring(12, 13));
            maghrib_jamma_minLeft.setText(maghrib_begins_time.toString().substring(14, 15));
            maghrib_jamma_minRight.setText(maghrib_begins_time.toString().substring(15, 16));
             
            isha_jamma_hourLeft.setText(isha_jamaat.substring(0, 1));
            isha_jamma_hourRight.setText(isha_jamaat.substring(1, 2));
            isha_jamma_minLeft.setText(isha_jamaat.substring(3, 4));
            isha_jamma_minRight.setText(isha_jamaat.substring(4, 5));
            
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
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/0%.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
            
            if (moonPhase <= 2 && moonPhase >= 0 )
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/0%.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
        
            else if (moonPhase>2 && moonPhase<=10 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/3%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>10 && moonPhase<=17 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/12%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>17 && moonPhase<=32 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/21%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
            else if (moonPhase>32 && moonPhase<=43 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/38%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>43 && moonPhase<=52 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/47%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>52 && moonPhase<=61 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>61 && moonPhase<=70 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/65%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>70 && moonPhase<=78 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/74%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>78 && moonPhase<=87 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/82%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>87 && moonPhase<=99 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/91%WA.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase== 100)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/100%.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>2 && moonPhase<=12 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/8%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>12 && moonPhase<=20 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/16%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>20 && moonPhase<=28 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/24%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>28 && moonPhase<=36 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/32%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>36 && moonPhase<=44 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/40%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>44 && moonPhase<=52 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/48%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>52 && moonPhase<=59 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>59 && moonPhase<=67 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/63%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>67 && moonPhase<=74 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/71%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>74 && moonPhase<=82 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/78%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>82 && moonPhase<=90 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/86%WX.png")));      
                Moon_img.setFitWidth(160);
                Moon_img.setFitHeight(160);
                Moon_img.setPreserveRatio(false);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>90 && moonPhase<=99 && !isWaning)
            {
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
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return false;
//            }
//            catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return false;
//            }
//            return true;
//        }

    
    public GridPane prayertime_pane() {
        
   GridPane prayertime_pane = new GridPane();
        prayertime_pane.setId("prayertime_pane");
        prayertime_pane.setCache(false);       
//        prayertime_pane.setGridLinesVisible(true);
        prayertime_pane.setPadding(new Insets(20, 0, 20, 20));
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
        fajrBox.setMaxSize(200,70);
        fajrBox.setMinSize(200,70);
        fajrBox.setPrefSize(200,70);
        
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
        

//============================= 
        HBox zuhrBox = new HBox();
        zuhrBox.setSpacing(0);
        zuhrBox.setMaxSize(200,70);
        zuhrBox.setMinSize(200,70);
        zuhrBox.setPrefSize(200,70);
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

//============================= 
        HBox asrBox = new HBox();
        asrBox.setSpacing(0);
        asrBox.setMaxSize(200,70);
        asrBox.setMinSize(200,70);
        asrBox.setPrefSize(200,70);
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
        
//============================= 
        
        HBox maghribBox = new HBox();
        maghribBox.setSpacing(0);
        maghribBox.setMaxSize(200,70);
        maghribBox.setMinSize(200,70);
        maghribBox.setPrefSize(200,70);
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

//============================= 
        
        HBox ishaBox = new HBox();
        ishaBox.setSpacing(0);
        ishaBox.setMaxSize(200,70);
        ishaBox.setMinSize(200,70);
        ishaBox.setPrefSize(200,70);
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

 //=============================  
//        HBox gapBox = new HBox();
//        gapBox.setSpacing(0);
//        gapBox.setMaxSize(200,70);
//        gapBox.setMinSize(200,70);
//        gapBox.getChildren().addAll();
//        prayertime_pane.setConstraints(gapBox, 0, 12);
//        prayertime_pane.getChildren().add(gapBox);       
        
//=============================  
//        HBox sunriseBox = new HBox();
//        sunriseBox.setSpacing(0);
//        sunriseBox.setMaxSize(200,70);
//        sunriseBox.setMinSize(200,70);
//        sunriseBox.setPrefSize(200,70);
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
        fridayBox.setMaxSize(200,70);
        fridayBox.setMinSize(200,70);
        fridayBox.setPrefSize(200,70);
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
        fajr_jamma_Box.setMaxSize(200,70);
        fajr_jamma_hourLeft.setId("hourLeft");
        fajr_jamma_hourRight.setId("hourLeft");
        time_jamma_Separator1.setId("hourLeft");
        fajr_jamma_minLeft.setId("hourLeft");
        fajr_jamma_minRight.setId("hourLeft");
        fajr_jamma_Box.getChildren().addAll(fajr_jamma_hourLeft, fajr_jamma_hourRight, time_jamma_Separator1, fajr_jamma_minLeft, fajr_jamma_minRight);
        prayertime_pane.setConstraints(fajr_jamma_Box, 0, 2);
        prayertime_pane.getChildren().add(fajr_jamma_Box);
        
//============================= 
        HBox zuhr_jamma_Box = new HBox();
        zuhr_jamma_Box.setSpacing(0);
        zuhr_jamma_Box.setMaxSize(200,70);
        zuhr_jamma_hourLeft.setId("hourLeft");
        zuhr_jamma_hourRight.setId("hourLeft");
        time_jamma_Separator2.setId("hourLeft");
        zuhr_jamma_minLeft.setId("hourLeft");
        zuhr_jamma_minRight.setId("hourLeft");
        zuhr_jamma_Box.getChildren().addAll(zuhr_jamma_hourLeft, zuhr_jamma_hourRight, time_jamma_Separator2, zuhr_jamma_minLeft, zuhr_jamma_minRight);
        prayertime_pane.setConstraints(zuhr_jamma_Box, 0, 4);
        prayertime_pane.getChildren().add(zuhr_jamma_Box);

//============================= 
        HBox asr_jamma_Box = new HBox();
        asr_jamma_Box.setSpacing(0);
        asr_jamma_Box.setMaxSize(200,70);
        asr_jamma_minRight.setId("hourLeft");
        asr_jamma_minLeft.setId("hourLeft");
        time_jamma_Separator3.setId("hourLeft");
        asr_jamma_hourRight.setId("hourLeft");
        asr_jamma_hourLeft.setId("hourLeft");
        asr_jamma_Box.getChildren().addAll(asr_jamma_hourLeft, asr_jamma_hourRight, time_jamma_Separator3, asr_jamma_minLeft, asr_jamma_minRight);
        prayertime_pane.setConstraints(asr_jamma_Box, 0, 6);
        prayertime_pane.getChildren().add(asr_jamma_Box);
        
//============================= 
        
        HBox maghrib_jamma_Box = new HBox();
        maghrib_jamma_Box.setSpacing(0);
        maghrib_jamma_Box.setMaxSize(200,70);
        maghrib_jamma_minRight.setId("hourLeft");
        maghrib_jamma_minLeft.setId("hourLeft");
        time_jamma_Separator4.setId("hourLeft");
        maghrib_jamma_hourRight.setId("hourLeft");
        maghrib_jamma_hourLeft.setId("hourLeft");
        maghrib_jamma_Box.getChildren().addAll(maghrib_jamma_hourLeft, maghrib_jamma_hourRight, time_jamma_Separator4, maghrib_jamma_minLeft, maghrib_jamma_minRight);
        prayertime_pane.setConstraints(maghrib_jamma_Box, 0, 8);
        prayertime_pane.getChildren().add(maghrib_jamma_Box);
        
//============================= 
        
        HBox isha_jamma_Box = new HBox();
        isha_jamma_Box.setSpacing(0);
        isha_jamma_Box.setMaxSize(200,70);
        isha_jamma_hourLeft.setId("hourLeft");
        isha_jamma_hourRight.setId("hourLeft");
        time_jamma_Separator5.setId("hourLeft");
        isha_jamma_minLeft.setId("hourLeft");
        isha_jamma_minRight.setId("hourLeft");
        isha_jamma_Box.getChildren().addAll(isha_jamma_hourLeft, isha_jamma_hourRight, time_jamma_Separator5, isha_jamma_minLeft, isha_jamma_minRight);
        prayertime_pane.setConstraints(isha_jamma_Box, 0, 10);
        prayertime_pane.getChildren().add(isha_jamma_Box);

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
//       Moonpane.setGridLinesVisible(true);

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
        Moonpane.setConstraints(Moon_Date_Label, 0, 0);
        Moonpane.getChildren().add(Moon_Date_Label);

        return Moonpane;
    }
    
    //===MOON PANE==========================  
    public GridPane hadithPane() {
      
        GridPane hadithPane = new GridPane();
//        hadithPane.setGridLinesVisible(true);
        hadithPane.setId("hadithpane");
        hadithPane.setVgap(20);

        hadith_Label.setId("hadith-text-arabic");
        hadith_Label.setWrapText(true);
        hadith_Label.setMinHeight(0);
        hadithPane.setConstraints(hadith_Label, 0, 0);
        hadithPane.getChildren().add(hadith_Label);
        
        
        en_moon_hadith_Label_L1.setId("hadith-text-english");
        en_moon_hadith_Label_L1.setWrapText(true);
        en_moon_hadith_Label_L1.setMinHeight(0);
        hadithPane.setConstraints(en_moon_hadith_Label_L1, 0, 0);
        hadithPane.getChildren().add(en_moon_hadith_Label_L1);
        
        en_moon_hadith_Label_L2.setWrapText(true);
        en_moon_hadith_Label_L2.setMinHeight(0);
//        en_moon_hadith_Label_L2.setPrefHeight(130);
        hadithPane.setConstraints(en_moon_hadith_Label_L2, 0, 1);
        hadithPane.getChildren().add(en_moon_hadith_Label_L2);
        
        ar_moon_hadith_Label_L1.setId("hadith-text-arabic");
        ar_moon_hadith_Label_L1.setWrapText(true);
        ar_moon_hadith_Label_L1.setMinHeight(0);
        hadithPane.setConstraints(ar_moon_hadith_Label_L1, 0, 0);
        hadithPane.getChildren().add(ar_moon_hadith_Label_L1);
        
        ar_moon_hadith_Label_L2.setWrapText(true);
//        ar_moon_hadith_Label_L2.setPrefHeight(130);
        ar_moon_hadith_Label_L2.setMinHeight(0);
        hadithPane.setConstraints(ar_moon_hadith_Label_L2, 0, 1);
        hadithPane.getChildren().add(ar_moon_hadith_Label_L2);
        
                
        ImageView divider_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/divider.png")));      
        divider1_Label.setGraphic(divider_img);
        hadithPane.setHalignment(divider1_Label,HPos.CENTER);
        hadithPane.setConstraints(divider1_Label, 0, 2);
        hadithPane.getChildren().add(divider1_Label); 
        
        
        athan_Change_Label_L1.setWrapText(true);
        athan_Change_Label_L1.setMinHeight(0);
        hadithPane.setConstraints(athan_Change_Label_L1, 0, 3);
        hadithPane.getChildren().add(athan_Change_Label_L1);
        
        athan_Change_Label_L2.setWrapText(true);
        athan_Change_Label_L2.setMinHeight(0);
        hadithPane.setConstraints(athan_Change_Label_L2, 0, 4);
        hadithPane.getChildren().add(athan_Change_Label_L2);
        
        ImageView twitter_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/QR_CODE_Twitter.png")));      
        twitter_img.setFitWidth(110);
        twitter_img.setPreserveRatio(true);
        twitter_img.setTranslateY(485);
        hadithPane.getChildren().add(twitter_img); 

        return hadithPane;
    }    

    public GridPane footerPane() {
      
        GridPane footerPane = new GridPane();
//        hadithPane.setGridLinesVisible(true);
        footerPane.setId("hadithpane");
        footerPane.setVgap(15);
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
//        clockPane.setGridLinesVisible(true);
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
//sudo cp  SimpleAstronomyLib-0.1.0.jar  /opt/jdk1.8.0/jre/lib/ext

                        
//                        
//                       
//
//                        Locale locale = Locale.getDefault();
//        TimeZone localTimeZone = TimeZone.getDefault(); 
//        //TimeZone localTimeZone = TimeZone.getTimeZone("Australia/Sydney");
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
//                                Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
//                            }
