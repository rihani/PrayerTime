/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import static javafx.scene.paint.Color.RED;
import javafx.util.Duration;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import eu.hansolo.enzo.clock.Clock;
import eu.hansolo.enzo.clock.ClockBuilder;
import eu.hansolo.enzo.imgsplitflap.SplitFlap;
import eu.hansolo.enzo.imgsplitflap.SplitFlapBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.Pane;
import javax.sound.sampled.AudioFormat;




//import org.joda.time.chrono.JulianChronology;

/**
 *
 * @author ossama
 */
   
    public class JavaFXApplication4 extends Application {
    private Process p;
    private Date fullMoon= null;
    private Date newMoon= null;
    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
    private Clock clock;
    private ObservableList data;
    private Label Phase_Label, Moon_Date_Label, Moon_Image_Label;
    private Integer moonPhase;
    private Boolean isWaning;
    private Boolean isStarting = true;
    private Boolean new_day = true;
    private Boolean fajr_athan_enable, duha_athan_enable, zuhr_athan_enable, asr_athan_enable, maghrib_athan_enable, isha_athan_enable = true ;
    private Boolean update_prayer_labels, update_moon_image, fajr_jamaat_update_enable  = false;
    private int id;
    int olddayofweek_int;
    private Date prayer_date;
    private Calendar fajr_cal, duha_cal, zuhr_cal, asr_cal, maghrib_cal, isha_cal,old_today;
    private Calendar fajr_jamaat_update_cal, duha_jamaat_update_cal, zuhr_jamaat_update_cal, asr_jamaat_update_cal, maghrib_jamaat_update_cal, isha_jamaat_update_cal;
    String fajr_jamaat ,zuhr_jamaat ,asr_jamaat ,maghrib_jamaat ,isha_jamaat ;
    private Date fajr_begins_time,fajr_jamaat_time, sunrise_time, zuhr_begins_time, zuhr_jamaat_time, asr_begins_time, asr_jamaat_time, maghrib_begins_time, maghrib_jamaat_time,isha_begins_time, isha_jamaat_time;
    private SplitFlap fajr_hourLeft, fajr_hourRight, fajr_minLeft, fajr_minRight, fajr_jamma_hourLeft, fajr_jamma_hourRight, fajr_jamma_minLeft, fajr_jamma_minRight;
    private SplitFlap sunrise_hourLeft, sunrise_hourRight, sunrise_minLeft, sunrise_minRight;
    private SplitFlap time_Separator1, time_Separator2, time_Separator3, time_Separator4, time_Separator5, time_Separator6, time_jamma_Separator1, time_jamma_Separator2, time_jamma_Separator3, time_jamma_Separator4 ,time_jamma_Separator5; 
    private SplitFlap zuhr_hourLeft, zuhr_hourRight, zuhr_minLeft, zuhr_minRight, zuhr_jamma_hourLeft, zuhr_jamma_hourRight, zuhr_jamma_minLeft, zuhr_jamma_minRight;
    private SplitFlap asr_hourLeft, asr_hourRight, asr_minLeft, asr_minRight, asr_jamma_hourLeft, asr_jamma_hourRight, asr_jamma_minLeft, asr_jamma_minRight;
    private SplitFlap maghrib_hourLeft, maghrib_hourRight, maghrib_minLeft, maghrib_minRight, maghrib_jamma_hourLeft, maghrib_jamma_hourRight, maghrib_jamma_minLeft, maghrib_jamma_minRight;
    private SplitFlap isha_hourLeft, isha_hourRight, isha_minLeft, isha_minRight, isha_jamma_hourLeft, isha_jamma_hourRight, isha_jamma_minLeft, isha_jamma_minRight;
    private long moonPhase_lastTimerCall,translate_lastTimerCall;
    private AnimationTimer moonPhase_timer, translate_timer;
    boolean arabic = true;
    boolean english = false;
    GridPane Mainpane;
    GridPane Moonpane;
    char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
    
    Connection c ;
           ObservableList<String> names = FXCollections.observableArrayList();
     
           
           private AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;
        int sampleInbits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
    }

    @Override public void init() {
        
// load the font.
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/PTBLARC.TTF").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BMajidSh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Oldoutsh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BJadidBd.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/wlm_carton.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Arial_Black.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Arial_Bold.ttf").toExternalForm(),30);
        data = FXCollections.observableArrayList();
        GridPane Mainpane = new GridPane();
        Moon_Image_Label = new Label();
        Phase_Label = new Label();
        Moon_Date_Label = new Label();
        clock = new Clock();
        clock = ClockBuilder.create()
                             .prefSize(200, 200)
                             .minHeight(200)
                             .minWidth(200)
                             .design(Clock.Design.IOS6)
                             .discreteSecond(true)
                             .secondPointerVisible(true)
                             .build();
        clock.setCache(true);

        time_Separator1 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator2 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator3 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator4 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator5 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator6 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        
        time_jamma_Separator1 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator2 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator3 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator4 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator5 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        
        
        time_jamma_Separator1 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator2 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator3 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator4 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator5 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        
        fajr_hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        
        fajr_jamma_hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_jamma_hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_jamma_minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_jamma_minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();

        
        sunrise_hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        sunrise_hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        sunrise_minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        sunrise_minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();

        
        zuhr_hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        zuhr_hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        zuhr_minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        zuhr_minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        
        zuhr_jamma_hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        zuhr_jamma_hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        zuhr_jamma_minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        zuhr_jamma_minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();

        asr_hourLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        asr_hourRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        asr_minLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        asr_minRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();

        asr_jamma_hourLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        asr_jamma_hourRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        asr_jamma_minLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        asr_jamma_minRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        
        maghrib_hourLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.RED).build();
        maghrib_hourRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.RED).build();
        maghrib_minLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.RED).build();
        maghrib_minRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.RED).build();
        
        maghrib_minRight = SplitFlapBuilder.create().textColor(Color.WHITE).build();
        
        maghrib_jamma_hourLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        maghrib_jamma_hourRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        maghrib_jamma_minLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        maghrib_jamma_minRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();

        isha_hourLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        isha_hourRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        isha_minLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        isha_minRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        
        isha_jamma_hourLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        isha_jamma_hourRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        isha_jamma_minLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        isha_jamma_minRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        
        
        Timer moonCalTimer = new Timer();
        moonCalTimer.scheduleAtFixedRate(new TimerTask() 
        {
            @Override
            public void run() 
            {
                try {
                        Locale.setDefault(new Locale("en", "AU"));
                        Date now = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(now);
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
                        getprayertime.setCalcMethod(3);
                        getprayertime.setAsrJuristic(0);
                        getprayertime.setAdjustHighLats(0);
                        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
                        getprayertime.tune(offsets);

                        ArrayList<String> prayerTimes = getprayertime.getPrayerTimes(cal, latitude, longitude, timezone);
                        ArrayList<String> prayerNames = getprayertime.getTimeNames();

                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                        fajr_begins_time = new Time(formatter.parse(prayerTimes.get(0)).getTime());
                        sunrise_time = new Time(formatter.parse(prayerTimes.get(1)).getTime());
                        zuhr_begins_time = new Time(formatter.parse(prayerTimes.get(2)).getTime());
                        asr_begins_time = new Time(formatter.parse(prayerTimes.get(3)).getTime());
                        maghrib_begins_time = new Time(formatter.parse(prayerTimes.get(5)).getTime());
                        isha_begins_time = new Time(formatter.parse(prayerTimes.get(6)).getTime());
                        update_prayer_labels = true;
                        
//                        System.out.println(" fajr time " + fajr_begins);
//                        System.out.println(" Sunrise time " + sunrise);
//                        System.out.println(" Zuhr time " + zuhr_begins);
//                        System.out.println(" Asr time " + asr_begins);
//                        System.out.println(" Maghrib time " + maghrib_begins);
//                        System.out.println(" Isha time " + isha_begins);

                        if (dayofweek_int != olddayofweek_int)
                        {    
//                            old_today = Calendar.getInstance();
                            olddayofweek_int = dayofweek_int;                         
                            System.out.println("current day of the week " + dayofweek_int ); 
                            System.out.println("old day of the week " + olddayofweek_int ); 
                            System.out.println(" cal:  " + cal ); 
                            fajr_athan_enable = true;
                            duha_athan_enable = true;
                            zuhr_athan_enable = true;
                            asr_athan_enable = true;
                            maghrib_athan_enable = true;
                            isha_athan_enable = true;

                            

                            Date Fajr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + fajr_begins_time);
                            cal.setTime(Fajr_temp);
                            Date fajr = cal.getTime();
                            fajr_cal = Calendar.getInstance();
                            fajr_cal.setTime(fajr);
                            fajr_cal.set(Calendar.MILLISECOND, 0);
                            fajr_cal.set(Calendar.SECOND, 0);

                            Date Duha_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + sunrise_time);
                            cal.setTime(Duha_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date duha = cal.getTime();
//                            System.out.println(duha);
                            duha_cal = Calendar.getInstance();
                            duha_cal.setTime(duha);
                            duha_cal.set(Calendar.MILLISECOND, 0);
                            duha_cal.set(Calendar.SECOND, 0);
                            
                            Date zuhr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + zuhr_begins_time);
                            cal.setTime(zuhr_temp);
                            Date zuhr = cal.getTime();
                            zuhr_cal = Calendar.getInstance();
                            zuhr_cal.setTime(zuhr);
                            zuhr_cal.set(Calendar.MILLISECOND, 0);
                            zuhr_cal.set(Calendar.SECOND, 0);
                            
                            Date asr_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + asr_begins_time);
                            cal.setTime(asr_temp);
                            Date asr = cal.getTime();
                            asr_cal = Calendar.getInstance();
                            asr_cal.setTime(asr);
                            asr_cal.set(Calendar.MILLISECOND, 0);
                            asr_cal.set(Calendar.SECOND, 0);
                            
                            Date maghrib_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + maghrib_begins_time);
                            cal.setTime(maghrib_temp);
                            Date maghrib = cal.getTime();
                            maghrib_cal = Calendar.getInstance();
                            maghrib_cal.setTime(maghrib);
                            maghrib_cal.set(Calendar.MILLISECOND, 0);
                            maghrib_cal.set(Calendar.SECOND, 0);
                            
                            Date isha_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + isha_begins_time);
                            cal.setTime(isha_temp);
                            Date isha = cal.getTime();
                            isha_cal = Calendar.getInstance();
                            isha_cal.setTime(isha);
                            isha_cal.set(Calendar.MILLISECOND, 0);
                            isha_cal.set(Calendar.SECOND, 0);
        
                        }
                        
                        if (isStarting)
                        {

                            c = DBConnect.connect();
                            System.out.println("connected");
                            //SQL FOR SELECTING NATIONALITY OF CUSTOMER
                            String SQL = "select * from jos_prayertimes where DATE(date) = DATE(NOW())";

                            ResultSet rs = c.createStatement().executeQuery(SQL);
                            System.out.println("query");
                            while (rs.next()) 
                            {
                                id =                rs.getInt("id");
                                prayer_date =       rs.getDate("date");
    //                            fajr_begins =       rs.getTime("fajr_begins");
                                fajr_jamaat_time =       rs.getTime("fajr_jamaat");
    //                            sunrise =           rs.getTime("sunrise");
    //                            zuhr_begins =       rs.getTime("zuhr_begins");
                                zuhr_jamaat_time =       rs.getTime("zuhr_jamaat");
    //                            asr_begins =        rs.getTime("asr_begins");
                                asr_jamaat_time =        rs.getTime("asr_jamaat");
                                maghrib_jamaat_time =    rs.getTime("maghrib_jamaat");
    //                            isha_begins =       rs.getTime("isha_begins");
                                isha_jamaat_time =       rs.getTime("isha_jamaat");
    //                            String lastName = rs.getString("last_name");
    //                            boolean  isAdmin = rs.getBoolean("is_admin");             
                            }
                            c.close();
                            System.out.println("disconnected");

                            fajr_jamaat = fajr_jamaat_time.toString();
                            zuhr_jamaat = zuhr_jamaat_time.toString();
                            asr_jamaat = asr_jamaat_time.toString();
                            maghrib_jamaat = maghrib_jamaat_time.toString();
                            isha_jamaat = isha_jamaat_time.toString();
                            // print the results
                            System.out.format("%s,%s,%s,%s,%s,%s,%s \n", id, prayer_date, fajr_jamaat, zuhr_jamaat, asr_jamaat, maghrib_jamaat, isha_jamaat );
                        
                        
                            
                            Date fajr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + fajr_jamaat);
                            cal.setTime(fajr_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date fajr_jamaat = cal.getTime();
                            fajr_jamaat_update_cal = Calendar.getInstance();
                            fajr_jamaat_update_cal.setTime(fajr_jamaat);
                            fajr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            fajr_jamaat_update_cal.set(Calendar.SECOND, 0);
//                            System.out.println(fajr_jamaat_update_cal.getTime());

                            Date zuhr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + zuhr_jamaat);
                            cal.setTime(zuhr_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date zuhr_jamaat = cal.getTime();
                            zuhr_jamaat_update_cal = Calendar.getInstance();
                            zuhr_jamaat_update_cal.setTime(zuhr_jamaat);
                            zuhr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            zuhr_jamaat_update_cal.set(Calendar.SECOND, 0);
                            
                            Date asr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + asr_jamaat);
                            cal.setTime(asr_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date asr_jamaat = cal.getTime();
                            asr_jamaat_update_cal = Calendar.getInstance();
                            asr_jamaat_update_cal.setTime(asr_jamaat);
                            asr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            asr_jamaat_update_cal.set(Calendar.SECOND, 0);
                            
                            Date maghrib_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + maghrib_jamaat);
                            cal.setTime(maghrib_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date maghrib_jamaat = cal.getTime();
                            maghrib_jamaat_update_cal = Calendar.getInstance();
                            maghrib_jamaat_update_cal.setTime(maghrib_jamaat);
                            maghrib_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            maghrib_jamaat_update_cal.set(Calendar.SECOND, 0);
                            
                            Date isha_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + isha_jamaat);
                            cal.setTime(isha_jamaat_temp);
                            cal.add(Calendar.MINUTE, 15);
                            Date isha_jamaat = cal.getTime();
                            isha_jamaat_update_cal = Calendar.getInstance();
                            isha_jamaat_update_cal.setTime(isha_jamaat);
                            isha_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                            isha_jamaat_update_cal.set(Calendar.SECOND, 0);
                        
                        
                        
                        
                        }
                        
                        
                        
                        
                        
                        Moon m = new Moon();
                        moonPhase = m.illuminatedPercentage();
                        isWaning = m.isWaning();
                        update_moon_image = true;
                        System.out.println("The moon is " + moonPhase + "% full and " + (isWaning ? "waning" : "waxing"));
 
                       fullMoon = MoonPhaseFinder.findFullMoonFollowing(Calendar.getInstance());
                       newMoon = MoonPhaseFinder.findNewMoonFollowing(Calendar.getInstance());
                        
                     } 

                catch(SQLException e)
                {
                    System.out.println("Error on Database connection");
                    Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, e);
                }
                catch (ParseException ex) 
                {
                    Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                } 
                catch (Exception ex) 
                {
                    Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                }
                
             
            }
        }, 0, 3600000);   
        
               
//        translate_lastTimerCall = System.nanoTime();
        translate_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > translate_lastTimerCall + 10000_000_000l) {
                    
                    try {
//                      buildData_database();
//                        buildData_calculate();
                        update_labels();
                    } catch (Exception ex) {
                        Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    translate_lastTimerCall = now;
                }
            }
        };
        
        moonPhase_lastTimerCall = System.nanoTime();
        moonPhase_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now >moonPhase_lastTimerCall + 1000000_000_000l) {
                    
                    try {
//                      buildData_database();
//                        buildData_calculate();
                    } catch (Exception ex) {
                        Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    moonPhase_lastTimerCall = now;
                }
            }
        };
        
        

             


    }

    @Override public void start(Stage stage) {
        
        Pane root = new Pane();
        Scene scene = new Scene(root, 650, 1180); //1180, 650
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Prayer Time Display");
                
        Mainpane = new GridPane();        
        String image = JavaFXApplication4.class.getResource("/Images/wallpaper7.jpg").toExternalForm();
        Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: stretch; -fx-background-size: 650 1180;-fx-background-position: bottom left;");        
        Mainpane.getColumnConstraints().setAll(
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/10.0).build()       
        );
        Mainpane.getRowConstraints().setAll(
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build(),
                RowConstraintsBuilder.create().percentHeight(100/15.0).build()
        );
//        Mainpane.setGridLinesVisible(true);
        Mainpane.setId("Mainpane");
        GridPane prayertime_pane = prayertime_pane();    
        GridPane Moonpane =   moonpane();
       
        
  //============================================
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(10.0);
        ds.setOffsetX(10.0);
        ds.setColor(Color.BLACK);

        clock.setEffect(ds);
        Moonpane.setEffect(ds);
        prayertime_pane.setEffect(ds);
  //============================================
        
        Mainpane.add(Moonpane, 5, 1,2,1);
        Mainpane.add(clock, 1, 1,1,1);    
        Mainpane.add(prayertime_pane, 1, 4,8,5);                     
        Mainpane.setCache(true);
        scene.setRoot(Mainpane);
        stage.show();
        translate_timer.start();       

//        stage.setFullScreen(true);
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
            
                   
            if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <= 7 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() >1)
            {
                String FullMoon_date_en = new SimpleDateFormat("EEEE").format(fullMoon);
                String FullMoon_date_en1 = new SimpleDateFormat("dd'th' MMM").format(fullMoon);
                
                Moon_Date_Label.setId("moon-text-english");
                Moon_Date_Label.setText("Full moon is on next\n" + FullMoon_date_en + " " + FullMoon_date_en1);
                Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                english = true;
                arabic = false;
            }
            
            
            else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() == 0)
            {

                Moon_Date_Label.setId("moon-text-english");
                Moon_Date_Label.setText("The moon is full today" );
                Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                english = true;
                arabic = false;

            }
            
            else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() >0 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <=1 )
            {

                String FullMoon_date_en1 = new SimpleDateFormat("dd'th' MMM").format(fullMoon);
                
                Moon_Date_Label.setId("moon-text-english");
                Moon_Date_Label.setText("Full moon is on\n"+ "tomorrow the "  + FullMoon_date_en1 );
                Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                english = true;
                arabic = false;

            }

            else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <10 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() > 7)
            {

                String FullMoon_date_en = new SimpleDateFormat("EEE").format(fullMoon);
                String FullMoon_date_en1 = new SimpleDateFormat("dd'th' MMM").format(fullMoon);
                
                Moon_Date_Label.setId("moon-text-english");
                Moon_Date_Label.setText("Full moon is on " + FullMoon_date_en + "\nnext week " + FullMoon_date_en1);
                Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                english = true;
                arabic = false;
            }
            
            else 
            {            
                String FullMoon_date_en = new SimpleDateFormat("EEEE dd'th' MMM").format(fullMoon);
                Moon_Date_Label.setId("moon-text-english");
                Moon_Date_Label.setText("Next Full Moon is on\n" + FullMoon_date_en);
                Moonpane.setHalignment(Moon_Date_Label,HPos.LEFT);
                english = true;
                arabic = false;
            
//            String image = JavaFXApplication4.class.getResource("wallpaper4.jpg").toExternalForm();
//            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: stretch; -fx-background-size: 650 1180;-fx-background-position: top left;");
            }

            
        }

        else
        { 
            if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <= 7 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() >1)
            {
                String FullMoon_date_ar = new SimpleDateFormat("' 'EEEE", new Locale("ar")).format(fullMoon);
                String FullMoon_date_ar1 = new SimpleDateFormat("dd MMMM", new Locale("ar")).format(fullMoon);
                String labeconv = "سيكون القمر بدرا\n" + FullMoon_date_ar + " القادم" +""+ FullMoon_date_ar1;
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
                Moon_Date_Label.setText("سيكون القمر بدرا غدآ" );
                Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                english = false;
                arabic = true;

            }
            
            else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() == 0)
            {

                Moon_Date_Label.setId("moon-text-arabic");
                Moon_Date_Label.setText("القمر بدر اليوم " );
                Moonpane.setHalignment(Moon_Date_Label,HPos.RIGHT);
                english = false;
                arabic = true;

            }
            
            else if ( Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() <10 && Days.daysBetween(new DateMidnight(DateTime_now), new DateMidnight(fullMoon)).getDays() > 7)
            {
                String FullMoon_date_ar = new SimpleDateFormat("' 'EEEE", new Locale("ar")).format(fullMoon);
                String FullMoon_date_ar1 = new SimpleDateFormat("dd MMMM", new Locale("ar")).format(fullMoon);
                                
                
                String labeconv = "سيكون القمر بدرا " + FullMoon_date_ar + "\n الأسبوع المقبل" + FullMoon_date_ar1;
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
                String FullMoon_date_ar = new SimpleDateFormat(" EEEE dd MMMM", new Locale("ar")).format(fullMoon);               
                String labeconv = "سيكون القمر بدرا يوم\n" + FullMoon_date_ar;
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
            
//            String image = JavaFXApplication4.class.getResource("wallpaper4.jpg").toExternalForm();
//            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: stretch; -fx-background-size: 650 1180;-fx-background-position: top left;");
            }
  
            
            
//            String image = JavaFXApplication4.class.getResource("wallpaper3.jpg").toExternalForm();
//            Mainpane.setStyle("-fx-background-image: url('" + image + "'); -fx-background-repeat: stretch; -fx-background-size: 650 1180;-fx-background-position: top left;");

        }
        
//==Days left to full moon============================================================        
 

//==prayer alarms =================================================================================
       
//        URL url = this.getClass().getClassLoader().getResource("Audio/athan1.wav");
//        AudioFormat adFormat = getAudioFormat();
//        Clip clip = AudioSystem.getClip();
//        AudioInputStream ais = AudioSystem.getAudioInputStream( url );
        
        
        
        URL url = this.getClass().getClassLoader().getResource("Audio/athan1.wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream(url); 
        AudioFormat littleEndianFormat = getAudioFormat();
        AudioInputStream converted = AudioSystem.getAudioInputStream(littleEndianFormat, ais); 
        Clip clip = AudioSystem.getClip();
//clip.open(converted);
//            clip.start();
        
        if (duha_cal.equals(Calendar_now) && duha_athan_enable) 
        {
            duha_athan_enable = false;
            System.out.println("Duha Time");
            URL url1 = this.getClass().getClassLoader().getResource("Audio/duha.wav");
//                      URL url = new URL("http://pscode.org/media/leftright.wav");
            Clip clip1 = AudioSystem.getClip();
            AudioInputStream ais1 = AudioSystem.getAudioInputStream( url1 );
            clip1.open(ais1);
            clip1.start();
        }

        else if (fajr_cal.equals(Calendar_now) && fajr_athan_enable) 
        {
            fajr_athan_enable = false;
            System.out.println("fajr Time");
            clip.open(converted);
            clip.start();
        }
        
        else if (zuhr_cal.equals(Calendar_now) && zuhr_athan_enable) 
        {
            zuhr_athan_enable = false;
            System.out.println("zuhr Time");
            clip.open(converted);
            clip.start();
        }        

        else if (asr_cal.equals(Calendar_now) && asr_athan_enable) 
        {
            asr_athan_enable = false;
            System.out.println("asr Time");
            clip.open(converted);
            clip.start();
        } 
        
        else if (maghrib_cal.equals(Calendar_now) && maghrib_athan_enable) 
        {
            maghrib_athan_enable = false;
            System.out.println("maghrib Time");
            clip.open(converted);
            clip.start();
        } 
        
        else if (isha_cal.equals(Calendar_now) && isha_athan_enable) 
        {
            isha_athan_enable = false;
            System.out.println("isha Time");
            clip.open(converted);
            clip.start();
        }      
        
        
        
// Jammat update=========================================================== 
        
        Date now = new Date();
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
//        System.out.println(fajr_jamaat_update_cal.getTime());
        
        if (fajr_jamaat_update_cal.equals(Calendar_now)  )         
        //insert a bool here to enable update if initial fetch timin is pin the past, or elsejammat timing will not change and will wait indefinitly. 

        {
        
        fajr_jamaat_update_enable = false;
        new Thread(new Runnable() {
            public void run() 
            {
                try {
                    System.out.println("fajr jamaat time updated");
                    c = DBConnect.connect();
                    System.out.println("connected");
                    //SQL FOR SELECTING NATIONALITY OF CUSTOMER
                    String SQL = "select * from jos_prayertimes where DATE(date) = DATE(NOW()) + 1";
                    
                    ResultSet rs = c.createStatement().executeQuery(SQL);
                    System.out.println("query");
                    while (rs.next())
                    {
                        fajr_jamaat_time =       rs.getTime("fajr_jamaat");
                    }
                    c.close();
                    System.out.println("disconnected");
                    
                    fajr_jamaat = fajr_jamaat_time.toString();
                    
                    Date fajr_jamaat_temp = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + fajr_jamaat);
                    cal.setTime(fajr_jamaat_temp);
                    cal.add(Calendar.MINUTE, 15);
                    Date fajr_jamaat = cal.getTime();
                    fajr_jamaat_update_cal = Calendar.getInstance();
                    fajr_jamaat_update_cal.setTime(fajr_jamaat);
                    fajr_jamaat_update_cal.set(Calendar.MILLISECOND, 0);
                    fajr_jamaat_update_cal.set(Calendar.SECOND, 0);
//                            System.out.println(fajr_jamaat_update_cal.getTime());
                    update_prayer_labels = true;
                } catch (SQLException ex) {
                    Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                }
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
            fajr_hourLeft.setText(fajr_begins_time.toString().substring(0, 1));
            fajr_hourRight.setText(fajr_begins_time.toString().substring(1, 2));
            fajr_minLeft.setText(fajr_begins_time.toString().substring(3, 4));
            fajr_minRight.setText(fajr_begins_time.toString().substring(4, 5));
            
            sunrise_hourLeft.setText(sunrise_time.toString().substring(0, 1));
            sunrise_hourRight.setText(sunrise_time.toString().substring(1, 2));
            sunrise_minLeft.setText(sunrise_time.toString().substring(3, 4));
            sunrise_minRight.setText(sunrise_time.toString().substring(4, 5));
            
            
            zuhr_hourLeft.setText(zuhr_begins_time.toString().substring(0, 1));
            zuhr_hourRight.setText(zuhr_begins_time.toString().substring(1, 2));
            zuhr_minLeft.setText(zuhr_begins_time.toString().substring(3, 4));
            zuhr_minRight.setText(zuhr_begins_time.toString().substring(4, 5));
             
            asr_hourLeft.setText(asr_begins_time.toString().substring(0, 1));
            asr_hourRight.setText(asr_begins_time.toString().substring(1, 2));
            asr_minLeft.setText(asr_begins_time.toString().substring(3, 4));
            asr_minRight.setText(asr_begins_time.toString().substring(4, 5));
             
            maghrib_hourLeft.setText(maghrib_begins_time.toString().substring(0, 1));
            maghrib_hourRight.setText(maghrib_begins_time.toString().substring(1, 2));
            maghrib_minLeft.setText(maghrib_begins_time.toString().substring(3, 4));
            maghrib_minRight.setText(maghrib_begins_time.toString().substring(4, 5));
             
            isha_hourLeft.setText(isha_begins_time.toString().substring(0, 1));
            isha_hourRight.setText(isha_begins_time.toString().substring(1, 2));
            isha_minLeft.setText(isha_begins_time.toString().substring(3, 4));
            isha_minRight.setText(isha_begins_time.toString().substring(4, 5));
            
            time_Separator1.setText(":");
            time_Separator2.setText(":");
            time_Separator3.setText(":");
            time_Separator4.setText(":");
            time_Separator5.setText(":");
            time_Separator6.setText(":");
            
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
             
            maghrib_jamma_hourLeft.setText(maghrib_jamaat.substring(0, 1));
            maghrib_jamma_hourRight.setText(maghrib_jamaat.substring(1, 2));
            maghrib_jamma_minLeft.setText(maghrib_jamaat.substring(3, 4));
            maghrib_jamma_minRight.setText(maghrib_jamaat.substring(4, 5));
             
            isha_jamma_hourLeft.setText(isha_jamaat.substring(0, 1));
            isha_jamma_hourRight.setText(isha_jamaat.substring(1, 2));
            isha_jamma_minLeft.setText(isha_jamaat.substring(3, 4));
            isha_jamma_minRight.setText(isha_jamaat.substring(4, 5));
             
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
            if (moonPhase == 0)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/0%.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
        
            else if (moonPhase>3 && moonPhase<=10 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/3%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>10 && moonPhase<=17 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/12%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>17 && moonPhase<=32 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/21%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }
            else if (moonPhase>32 && moonPhase<=43 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/38%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>43 && moonPhase<=52 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/47%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>52 && moonPhase<=61 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>61 && moonPhase<=70 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/65%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>70 && moonPhase<=78 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/74%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>78 && moonPhase<=87 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/82%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>87 && moonPhase<=96 && isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/91%WA.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase== 100)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/100%.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>4 && moonPhase<=12 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/8%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>12 && moonPhase<=20 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/16%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>20 && moonPhase<=28 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/24%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>28 && moonPhase<=36 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/32%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>36 && moonPhase<=44 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/40%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>44 && moonPhase<=52 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/48%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>52 && moonPhase<=59 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>59 && moonPhase<=67 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/63%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>67 && moonPhase<=74 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/71%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>74 && moonPhase<=82 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/78%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>82 && moonPhase<=90 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/86%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
                Moon_img.setSmooth(true);        
                Moon_Image_Label.setGraphic(Moon_img);
            }

            else if (moonPhase>90 && moonPhase<=98 && !isWaning)
            {
                ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/94%WX.png")));      
                Moon_img.setFitWidth(100);
                Moon_img.setFitHeight(100);
                Moon_img.setPreserveRatio(true);
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
        prayertime_pane.setCache(true);       
//        prayertime_pane.setGridLinesVisible(true);
        prayertime_pane.setPadding(new Insets(20, 20, 20, 20));
        prayertime_pane.setAlignment(Pos.BASELINE_CENTER);
        prayertime_pane.setVgap(7);
        prayertime_pane.setHgap(35);
               
//=============================  
        HBox fajrBox = new HBox();
        fajrBox.setSpacing(0);
        fajrBox.setMaxSize(155, 54);
        fajrBox.getChildren().addAll(fajr_hourLeft, fajr_hourRight, time_Separator1, fajr_minLeft, fajr_minRight);
        prayertime_pane.setConstraints(fajrBox, 1, 1);
        prayertime_pane.getChildren().add(fajrBox);
        
        TextFlow fajrtextFlow = new TextFlow();
        Text text1 = new Text("الفجر\n");
        text1.setId("prayer-text-arabic");
        Text text10 = new Text("Fajr");
        text10.setId("prayer-text-english");       
        prayertime_pane.setHalignment(text1,HPos.RIGHT);
        prayertime_pane.setValignment(text1,VPos.TOP);
        prayertime_pane.setConstraints(text1, 2, 1);
        prayertime_pane.getChildren().add(text1);
        prayertime_pane.setHalignment(text10,HPos.LEFT);
        prayertime_pane.setValignment(text10,VPos.BOTTOM);
        prayertime_pane.setConstraints(text10, 2, 1);
        prayertime_pane.getChildren().add(text10);




//=============================  
        HBox sunriseBox = new HBox();
        sunriseBox.setSpacing(0);
        sunriseBox.setMaxSize(155, 54);
        sunriseBox.getChildren().addAll(sunrise_hourLeft, sunrise_hourRight, time_Separator2, sunrise_minLeft, sunrise_minRight);
        prayertime_pane.setConstraints(sunriseBox, 1, 3);
        prayertime_pane.getChildren().add(sunriseBox);
        
        TextFlow sunrisetextFlow = new TextFlow();
        Text text6 = new Text("الشروق\n");
        text6.setId("prayer-text-arabic");
        Text text60 = new Text("Sunrise");
        text60.setId("prayer-text-english");       
        prayertime_pane.setHalignment(text6,HPos.RIGHT);
        prayertime_pane.setValignment(text6,VPos.TOP);
        prayertime_pane.setConstraints(text6, 2, 3);
        prayertime_pane.getChildren().add(text6);
        prayertime_pane.setHalignment(text60,HPos.LEFT);
        prayertime_pane.setValignment(text60,VPos.BOTTOM);
        prayertime_pane.setConstraints(text60, 2, 3);
        prayertime_pane.getChildren().add(text60);


//============================= 
        HBox zuhrBox = new HBox();
        zuhrBox.setSpacing(0);
        zuhrBox.setMaxSize(155, 54);
        zuhrBox.getChildren().addAll(zuhr_hourLeft, zuhr_hourRight, time_Separator3, zuhr_minLeft, zuhr_minRight);
        prayertime_pane.setConstraints(zuhrBox, 1, 5);
        prayertime_pane.getChildren().add(zuhrBox);
        
        TextFlow duhrtextFlow = new TextFlow();
        Text text2 = new Text("الظهر\n");
        text2.setId("prayer-text-arabic");
        Text text20 = new Text("Duhr");
        text20.setId("prayer-text-english");
        prayertime_pane.setHalignment(text2,HPos.RIGHT);
        prayertime_pane.setValignment(text2,VPos.TOP);
        prayertime_pane.setConstraints(text2, 2, 5);
        prayertime_pane.getChildren().add(text2);
        prayertime_pane.setHalignment(text20,HPos.LEFT);
        prayertime_pane.setValignment(text20,VPos.BOTTOM);
        prayertime_pane.setConstraints(text20, 2, 5);
        prayertime_pane.getChildren().add(text20);

//============================= 
        HBox asrBox = new HBox();
        asrBox.setSpacing(0);
        asrBox.setMaxSize(155, 54);
        asrBox.getChildren().addAll(asr_hourLeft, asr_hourRight, time_Separator4, asr_minLeft, asr_minRight);
        prayertime_pane.setConstraints(asrBox, 1, 7);
        prayertime_pane.getChildren().add(asrBox);
        
        TextFlow asrFlow = new TextFlow();
        Text text3 = new Text("العصر\n");
        text3.setId("prayer-text-arabic");
        Text text30 = new Text("Asr");
        text30.setId("prayer-text-english");
        prayertime_pane.setHalignment(text3,HPos.RIGHT);
        prayertime_pane.setValignment(text3,VPos.TOP);
        prayertime_pane.setConstraints(text3, 2, 7);
        prayertime_pane.getChildren().add(text3);
        prayertime_pane.setHalignment(text30,HPos.LEFT);
        prayertime_pane.setValignment(text30,VPos.BOTTOM);
        prayertime_pane.setConstraints(text30, 2, 7);
        prayertime_pane.getChildren().add(text30);
        
//============================= 
        
        HBox maghribBox = new HBox();
        maghribBox.setSpacing(0);
        maghribBox.setMaxSize(155, 54);
        maghribBox.getChildren().addAll(maghrib_hourLeft, maghrib_hourRight, time_Separator5, maghrib_minLeft, maghrib_minRight);
        prayertime_pane.setConstraints(maghribBox, 1, 9);
        prayertime_pane.getChildren().add(maghribBox);
        
        Text text4 = new Text("المغرب\n");
        text4.setId("prayer-text-arabic");
        Text text40 = new Text("\nMaghrib");
        text40.setId("prayer-text-english");
        prayertime_pane.setHalignment(text4,HPos.RIGHT);
        prayertime_pane.setValignment(text4,VPos.TOP);
        prayertime_pane.setConstraints(text4, 2, 9);
        prayertime_pane.getChildren().add(text4);
        prayertime_pane.setHalignment(text40,HPos.LEFT);
        prayertime_pane.setValignment(text40,VPos.BOTTOM);
        prayertime_pane.setConstraints(text40, 2, 9);
        prayertime_pane.getChildren().add(text40);
        
//============================= 
        
        HBox ishaBox = new HBox();
        ishaBox.setSpacing(0);
        ishaBox.setMaxSize(155, 54);
        ishaBox.getChildren().addAll(isha_hourLeft, isha_hourRight, time_Separator6, isha_minLeft, isha_minRight);
        prayertime_pane.setConstraints(ishaBox, 1, 11);
        prayertime_pane.getChildren().add(ishaBox);
        
        Text text5 = new Text("العشاء\n");
        text5.setId("prayer-text-arabic");
        Text text50 = new Text("Isha");
        text50.setId("prayer-text-english");
        prayertime_pane.setHalignment(text5,HPos.RIGHT);
        prayertime_pane.setValignment(text5,VPos.TOP);
        prayertime_pane.setConstraints(text5, 2, 11);
        prayertime_pane.getChildren().add(text5);
        prayertime_pane.setHalignment(text50,HPos.LEFT);
        prayertime_pane.setValignment(text50,VPos.BOTTOM);
        prayertime_pane.setConstraints(text50, 2, 11);
        prayertime_pane.getChildren().add(text50);
        
 //============================= 
        
        final Separator sepHor1 = new Separator();
        prayertime_pane.setValignment(sepHor1,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor1, 0, 2);
        prayertime_pane.setColumnSpan(sepHor1, 3);
        prayertime_pane.getChildren().add(sepHor1);   
        
        final Separator sepHor2 = new Separator();
        prayertime_pane.setValignment(sepHor2,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor2, 0, 4);
        prayertime_pane.setColumnSpan(sepHor2, 3);
        prayertime_pane.getChildren().add(sepHor2);
        
        final Separator sepHor3 = new Separator();
        prayertime_pane.setValignment(sepHor3,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor3, 0, 6);
        prayertime_pane.setColumnSpan(sepHor3, 3);
        prayertime_pane.getChildren().add(sepHor3);
        
        final Separator sepHor4 = new Separator();
        prayertime_pane.setValignment(sepHor4,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor4, 0, 8);
        prayertime_pane.setColumnSpan(sepHor4, 3);
        prayertime_pane.getChildren().add(sepHor4);
        
        final Separator sepHor5 = new Separator();
        prayertime_pane.setValignment(sepHor5,VPos.CENTER);
        prayertime_pane.setConstraints(sepHor5, 0, 10);
        prayertime_pane.setColumnSpan(sepHor5, 3);
        prayertime_pane.getChildren().add(sepHor5);

//========= Jamama=======
        
        
//=============================  
        HBox fajr_jamma_Box = new HBox();
        fajr_jamma_Box.setSpacing(0);
        fajr_jamma_Box.setMaxSize(155, 54);
        fajr_jamma_Box.getChildren().addAll(fajr_jamma_hourLeft, fajr_jamma_hourRight, time_jamma_Separator1, fajr_jamma_minLeft, fajr_jamma_minRight);
        prayertime_pane.setConstraints(fajr_jamma_Box, 0, 1);
        prayertime_pane.getChildren().add(fajr_jamma_Box);
        
//============================= 
        HBox zuhr_jamma_Box = new HBox();
        zuhr_jamma_Box.setSpacing(0);
        zuhr_jamma_Box.setMaxSize(155, 54);
        zuhr_jamma_Box.getChildren().addAll(zuhr_jamma_hourLeft, zuhr_jamma_hourRight, time_jamma_Separator2, zuhr_jamma_minLeft, zuhr_jamma_minRight);
        prayertime_pane.setConstraints(zuhr_jamma_Box, 0, 5);
        prayertime_pane.getChildren().add(zuhr_jamma_Box);

//============================= 
        HBox asr_jamma_Box = new HBox();
        asr_jamma_Box.setSpacing(0);
        asr_jamma_Box.setMaxSize(155, 54);
        asr_jamma_Box.getChildren().addAll(asr_jamma_hourLeft, asr_jamma_hourRight, time_jamma_Separator3, asr_jamma_minLeft, asr_jamma_minRight);
        prayertime_pane.setConstraints(asr_jamma_Box, 0, 7);
        prayertime_pane.getChildren().add(asr_jamma_Box);
        
//============================= 
        
        HBox maghrib_jamma_Box = new HBox();
        maghrib_jamma_Box.setSpacing(0);
        maghrib_jamma_Box.setMaxSize(155, 54);
        maghrib_jamma_Box.getChildren().addAll(maghrib_jamma_hourLeft, maghrib_jamma_hourRight, time_jamma_Separator4, maghrib_jamma_minLeft, maghrib_jamma_minRight);
        prayertime_pane.setConstraints(maghrib_jamma_Box, 0, 9);
        prayertime_pane.getChildren().add(maghrib_jamma_Box);
        
//============================= 
        
        HBox isha_jamma_Box = new HBox();
        isha_jamma_Box.setSpacing(0);
        isha_jamma_Box.setMaxSize(155, 54);
        isha_jamma_Box.getChildren().addAll(isha_jamma_hourLeft, isha_jamma_hourRight, time_jamma_Separator5, isha_jamma_minLeft, isha_jamma_minRight);
        prayertime_pane.setConstraints(isha_jamma_Box, 0, 11);
        prayertime_pane.getChildren().add(isha_jamma_Box);

    return prayertime_pane;
}
    
  
    
    
    //===MOON PANE==========================  
    public GridPane moonpane() {
      
        GridPane Moonpane = new GridPane();
        Moonpane.setId("moonpane");
        Moonpane.getColumnConstraints().setAll(
                ColumnConstraintsBuilder.create().prefWidth(175).minWidth(175).build(),
                ColumnConstraintsBuilder.create().prefWidth(100).minWidth(100).build()     
        );
        Moonpane.setHgap(10);
        Moonpane.setMaxHeight(50);
//       Moonpane.setGridLinesVisible(true);

//        Phase_Label.setId("moon-text-english");
//        Moonpane.setRight(Phase_Label);
//        myLabel.textProperty().bind(valueProperty);
        ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/100%.png")));      
        Moon_img.setFitWidth(100);
        Moon_img.setFitHeight(100);
        Moon_img.setPreserveRatio(true);
        Moon_img.setSmooth(true);
        Moon_Image_Label.setGraphic(Moon_img);
        Moonpane.setConstraints(Moon_Image_Label, 1, 0);
        Moonpane.getChildren().add(Moon_Image_Label); 
        Moon_Date_Label.setId("moon-text-english");
        Moonpane.setConstraints(Moon_Date_Label, 0, 0);
        Moonpane.getChildren().add(Moon_Date_Label);
        Reflection r = new Reflection();
        r.setFraction(0.15f);
        Moonpane.setEffect(r);
        return Moonpane;
    }
    
    
}



                        
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

