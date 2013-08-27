/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

//import java.util.Locale;
//import org.joda.time.DateTime;
//import org.joda.time.Instant;
import eu.hansolo.enzo.clock.Clock;
import eu.hansolo.enzo.clock.ClockBuilder;
import eu.hansolo.enzo.imgsplitflap.SplitFlap;
import eu.hansolo.enzo.imgsplitflap.SplitFlapBuilder;
import eu.hansolo.enzo.lcdclock.LcdClock;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
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
//import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.RowConstraintsBuilder;
import java.util.Calendar;
import com.bradsbrain.simpleastronomy.MoonPhaseFinder;
import org.joda.time.chrono.JulianChronology;

/**
 *
 * @author ossama
 */
   
    public class JavaFXApplication4 extends Application {
    
//    private SimpleIndicator control;
//    private boolean toggle;    

    private Clock clock;
    private Clock clock5;
    private LcdClock clock3;
    private ObservableList data;
 
    private static final String[] WEEK_DAYS = {
        "SUN","MON","TUE","WED","THU","FRI","SAT"
    };
    private static final String[] MONTHS = {
        "JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"
    };

    private int id;
    private Date prayer_date;
    private Time fajr_begins,fajr_jamaat, sunrise, zuhr_begins, zuhr_jamaat, asr_begins, asr_jamaat, maghrib_begins, maghrib_jamaat,isha_begins, isha_jamaat;

    private SplitFlap dayLeft;
    private SplitFlap dayMid;
    private SplitFlap dayRight;

    private SplitFlap dateLeft;
    private SplitFlap dateRight;

    private SplitFlap monthLeft;
    private SplitFlap monthMid;
    private SplitFlap monthRight;

    private SplitFlap hourLeft;
    private SplitFlap hourRight;
    private SplitFlap minLeft;
    private SplitFlap minRight;
    private SplitFlap secLeft;
    private SplitFlap secRight;
    
    private SplitFlap fajr_hourLeft, fajr_hourRight, fajr_minLeft, fajr_minRight, fajr_jamma_hourLeft, fajr_jamma_hourRight, fajr_jamma_minLeft, fajr_jamma_minRight;
    private SplitFlap time_Separator1, time_Separator2, time_Separator3, time_Separator4 ,time_Separator5, time_jamma_Separator1, time_jamma_Separator2, time_jamma_Separator3, time_jamma_Separator4 ,time_jamma_Separator5; 
    private SplitFlap zuhr_hourLeft, zuhr_hourRight, zuhr_minLeft, zuhr_minRight, zuhr_jamma_hourLeft, zuhr_jamma_hourRight, zuhr_jamma_minLeft, zuhr_jamma_minRight;
    private SplitFlap asr_hourLeft, asr_hourRight, asr_minLeft, asr_minRight, asr_jamma_hourLeft, asr_jamma_hourRight, asr_jamma_minLeft, asr_jamma_minRight;
    private SplitFlap maghrib_hourLeft, maghrib_hourRight, maghrib_minLeft, maghrib_minRight, maghrib_jamma_hourLeft, maghrib_jamma_hourRight, maghrib_jamma_minLeft, maghrib_jamma_minRight;
    private SplitFlap isha_hourLeft, isha_hourRight, isha_minLeft, isha_minRight, isha_jamma_hourLeft, isha_jamma_hourRight, isha_jamma_minLeft, isha_jamma_minRight;

    private int date;
    private int hours;
    private int minutes;
    private int seconds;
    private long lastTimerCall,buildData_lastTimerCall;
    private AnimationTimer timer, buildData_timer;
    

    @Override public void init() {
        
// load the font.
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/PTBLARC.TTF").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BMajidSh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Oldoutsh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BJadidBd.ttf").toExternalForm(),30);
        
        clock = new Clock();
//        clock5 = new Clock();
        clock = ClockBuilder.create()
                             .prefSize(200, 200)
                             .design(Clock.Design.IOS6)
                             .discreteSecond(true)
                             .build();
        
        
//        clock5 = ClockBuilder.create()
//                             .prefSize(200, 200)
//                             .design(Clock.Design.BRAUN)
//                             .discreteSecond(false)
//                             .secondPointerVisible(false)
//                
//                             .build();
//        
//        
//        clock3 = LcdClockBuilder.create()
//                               //.color(Color.CYAN)
//                               //.hourColor(Color.LIME)
//                               .minuteColor(Color.AQUA)
//                               //.secondColor(Color.MAGENTA)
//                               //.textColor(Color.DARKOLIVEGREEN)
//                               .alarmVisible(true)
//                               .alarmOn(true)
//                               .alarm(LocalTime.now().plusSeconds(20))
//                               .dateVisible(true)
//                               .build();
     
        time_Separator1 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator2 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator3 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator4 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_Separator5 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        
        time_jamma_Separator1 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator2 = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator3 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator4 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        time_jamma_Separator5 = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).textColor(Color.WHITESMOKE).build();
        
        dayLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.ALPHA).textColor(Color.WHITESMOKE).build();
        dayMid = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.ALPHA).textColor(Color.WHITESMOKE).build();
        dayRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.ALPHA).textColor(Color.WHITESMOKE).build();

        dateLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        dateRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();

        monthLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.ALPHA).textColor(Color.WHITESMOKE).build();
        monthMid = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.ALPHA).textColor(Color.WHITESMOKE).build();
        monthRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(0).selection(SplitFlap.ALPHA).textColor(Color.WHITESMOKE).build();

        hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(60).selection(SplitFlap.TIME_0_TO_5).textColor(Color.WHITESMOKE).build();
        hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(60).selection(SplitFlap.TIME_0_TO_9).textColor(Color.WHITESMOKE).build();
        minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(60).selection(SplitFlap.TIME_0_TO_5).textColor(Color.WHITESMOKE).build();
        minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(60).selection(SplitFlap.TIME_0_TO_9).textColor(Color.WHITESMOKE).build();
        secLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(60).selection(SplitFlap.TIME_0_TO_5).textColor(Color.ORANGERED).build();
        secRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(60).flipTime(60).selection(SplitFlap.TIME_0_TO_9).textColor(Color.ORANGERED).build();
        
            
        fajr_hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        
        fajr_jamma_hourLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_jamma_hourRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_jamma_minLeft = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        fajr_jamma_minRight = SplitFlapBuilder.create().prefWidth(32).prefHeight(62).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();

        
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
        
        maghrib_hourLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        maghrib_hourRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        maghrib_minLeft = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        maghrib_minRight = SplitFlapBuilder.create().scaleX(1).scaleY(1).flipTime(0).selection(SplitFlap.NUMERIC).textColor(Color.WHITESMOKE).build();
        
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

        lastTimerCall = System.nanoTime();
        
        timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > lastTimerCall + 500_000_000l) {
                    String day = WEEK_DAYS[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1];
                    if (day.equals("SAT") || day.equals("SUN")) {
                        if (!dayLeft.getTextColor().equals(Color.CRIMSON)) {
                            dayLeft.setTextColor(Color.CRIMSON);
                            dayMid.setTextColor(Color.CRIMSON);
                            dayRight.setTextColor(Color.CRIMSON);
                        }
                    } else {
                        if (!dayLeft.getText().equals(Color.WHITESMOKE)) {
                            dayLeft.setTextColor(Color.WHITESMOKE);
                            dayMid.setTextColor(Color.WHITESMOKE);
                            dayRight.setTextColor(Color.WHITESMOKE);
                        }
                    }
                    dayLeft.setText(day.substring(0, 1));
                    dayMid.setText(day.substring(1, 2));
                    dayRight.setText(day.substring(2));

                    date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    String dateString = Integer.toString(date);
                    if (date < 10) {
                        dateLeft.setText("0");
                        dateRight.setText(dateString.substring(0, 1));
                    } else {
                        dateLeft.setText(dateString.substring(0, 1));
                        dateRight.setText(dateString.substring(1));
                    }

                    String month = MONTHS[Calendar.getInstance().get(Calendar.MONTH)];
                    monthLeft.setText(month.substring(0, 1));
                    monthMid.setText(month.substring(1, 2));
                    monthRight.setText(month.substring(2));

                    hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    String hourString = Integer.toString(hours);
                    if (hours < 10) {
                        hourLeft.setText("0");
                        hourRight.setText(hourString.substring(0, 1));
                    } else {
                        hourLeft.setText(hourString.substring(0, 1));
                        hourRight.setText(hourString.substring(1));
                    }

                    minutes = Calendar.getInstance().get(Calendar.MINUTE);
                    String minutesString = Integer.toString(minutes);
                    if (minutes < 10) {
                        minLeft.setText("0");
                        minRight.setText(minutesString.substring(0, 1));
                    } else {
                        minLeft.setText(minutesString.substring(0, 1));
                        minRight.setText(minutesString.substring(1));
                    }

                    seconds = Calendar.getInstance().get(Calendar.SECOND);
                    String secondsString = Integer.toString(seconds);
                    if (seconds < 10) {
                        secLeft.setText("0");
                        secRight.setText(secondsString.substring(0, 1));
                    } else {
                        secLeft.setText(secondsString.substring(0, 1));
                        secRight.setText(secondsString.substring(1));
                    }
                    lastTimerCall = now;
                }
            }
        };
        
        buildData_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > buildData_lastTimerCall + 1000000_000_000l) {
                    
                    try {
//                      buildData_database();
                        buildData_calculate();
                    } catch (Exception ex) {
                        Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    buildData_lastTimerCall = now;
                }
            }
        };
        
    }

    @Override public void start(Stage stage) {
        
        timer.start();
        buildData_timer.start();
        
        Group root = new Group();
        Scene scene = new Scene(root, 1180, 650);
        stage.setScene(scene);
        stage.setTitle("Prayer Time Display");
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
        GridPane Mainpane = new GridPane();
        scene.setRoot(Mainpane);
        Mainpane.getColumnConstraints().setAll(
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build(),
                ColumnConstraintsBuilder.create().percentWidth(100/15.0).build()
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
        
        GridPane prayertime_pane = new GridPane();
        prayertime_pane.setId("prayertime_pane");
//        prayertime_pane.setGridLinesVisible(true);
        prayertime_pane.setPadding(new Insets(20, 20, 20, 20));
        prayertime_pane.setAlignment(Pos.BASELINE_CENTER);
        prayertime_pane.setVgap(7);
        prayertime_pane.setHgap(35);

//        HBox dayBox = new HBox();
//        dayBox.setSpacing(0);
//        dayBox.getChildren().addAll(dayLeft, dayMid, dayRight);
//        dayBox.setLayoutX(12);
//        dayBox.setLayoutY(76);
//
//        HBox dateBox = new HBox();
//        dateBox.setSpacing(0);
//        dateBox.getChildren().addAll(dateLeft, dateRight);
//        dateBox.setLayoutX(134);
//        dateBox.setLayoutY(76);
//
//        HBox monthBox = new HBox();
//        monthBox.setSpacing(0);
//        monthBox.getChildren().addAll(monthLeft, monthMid, monthRight);
//        monthBox.setLayoutX(217);
//        monthBox.setLayoutY(76);
//
//        HBox clockBox = new HBox();
//        clockBox.setSpacing(0);
//        HBox.setMargin(hourRight, new Insets(0, 40, 0, 0));
//        HBox.setMargin(minRight, new Insets(0, 40, 0, 0));
//        clockBox.getChildren().addAll(hourLeft, hourRight, minLeft, minRight, secLeft, secRight);
//        clockBox.setLayoutY(175);
//        
        HBox clock1Box = new HBox();
        clock1Box.setSpacing(0);
        clock1Box.getChildren().addAll(clock);
               
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
        HBox zuhrBox = new HBox();
        zuhrBox.setSpacing(0);
        zuhrBox.setMaxSize(155, 54);
        zuhrBox.getChildren().addAll(zuhr_hourLeft, zuhr_hourRight, time_Separator2, zuhr_minLeft, zuhr_minRight);
        prayertime_pane.setConstraints(zuhrBox, 1, 3);
        prayertime_pane.getChildren().add(zuhrBox);
        
        TextFlow duhrtextFlow = new TextFlow();
        Text text2 = new Text("الظهر\n");
        text2.setId("prayer-text-arabic");
        Text text20 = new Text("Duhr");
        text20.setId("prayer-text-english");
        prayertime_pane.setHalignment(text2,HPos.RIGHT);
        prayertime_pane.setValignment(text2,VPos.TOP);
        prayertime_pane.setConstraints(text2, 2, 3);
        prayertime_pane.getChildren().add(text2);
        prayertime_pane.setHalignment(text20,HPos.LEFT);
        prayertime_pane.setValignment(text20,VPos.BOTTOM);
        prayertime_pane.setConstraints(text20, 2, 3);
        prayertime_pane.getChildren().add(text20);

//============================= 
        HBox asrBox = new HBox();
        asrBox.setSpacing(0);
        asrBox.setMaxSize(155, 54);
        asrBox.getChildren().addAll(asr_hourLeft, asr_hourRight, time_Separator3, asr_minLeft, asr_minRight);
        prayertime_pane.setConstraints(asrBox, 1, 5);
        prayertime_pane.getChildren().add(asrBox);
        
        TextFlow asrFlow = new TextFlow();
        Text text3 = new Text("العصر\n");
        text3.setId("prayer-text-arabic");
        Text text30 = new Text("Asr");
        text30.setId("prayer-text-english");
        prayertime_pane.setHalignment(text3,HPos.RIGHT);
        prayertime_pane.setValignment(text3,VPos.TOP);
        prayertime_pane.setConstraints(text3, 2, 5);
        prayertime_pane.getChildren().add(text3);
        prayertime_pane.setHalignment(text30,HPos.LEFT);
        prayertime_pane.setValignment(text30,VPos.BOTTOM);
        prayertime_pane.setConstraints(text30, 2, 5);
        prayertime_pane.getChildren().add(text30);
        
//============================= 
        
        HBox maghribBox = new HBox();
        maghribBox.setSpacing(0);
        maghribBox.setMaxSize(155, 54);
        maghribBox.getChildren().addAll(maghrib_hourLeft, maghrib_hourRight, time_Separator4, maghrib_minLeft, maghrib_minRight);
        prayertime_pane.setConstraints(maghribBox, 1, 7);
        prayertime_pane.getChildren().add(maghribBox);
        
        Text text4 = new Text("المغرب\n");
        text4.setId("prayer-text-arabic");
        Text text40 = new Text("\nMaghrib");
        text40.setId("prayer-text-english");
        prayertime_pane.setHalignment(text4,HPos.RIGHT);
        prayertime_pane.setValignment(text4,VPos.TOP);
        prayertime_pane.setConstraints(text4, 2, 7);
        prayertime_pane.getChildren().add(text4);
        prayertime_pane.setHalignment(text40,HPos.LEFT);
        prayertime_pane.setValignment(text40,VPos.BOTTOM);
        prayertime_pane.setConstraints(text40, 2, 7);
        prayertime_pane.getChildren().add(text40);
        
//============================= 
        
        HBox ishaBox = new HBox();
        ishaBox.setSpacing(0);
        ishaBox.setMaxSize(155, 54);
        ishaBox.getChildren().addAll(isha_hourLeft, isha_hourRight, time_Separator5, isha_minLeft, isha_minRight);
        prayertime_pane.setConstraints(ishaBox, 1, 9);
        prayertime_pane.getChildren().add(ishaBox);
        
        Text text5 = new Text("العشاء\n");
        text5.setId("prayer-text-arabic");
        Text text50 = new Text("Isha");
        text50.setId("prayer-text-english");
        prayertime_pane.setHalignment(text5,HPos.RIGHT);
        prayertime_pane.setValignment(text5,VPos.TOP);
        prayertime_pane.setConstraints(text5, 2, 9);
        prayertime_pane.getChildren().add(text5);
        prayertime_pane.setHalignment(text50,HPos.LEFT);
        prayertime_pane.setValignment(text50,VPos.BOTTOM);
        prayertime_pane.setConstraints(text50, 2, 9);
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
        prayertime_pane.setConstraints(zuhr_jamma_Box, 0, 3);
        prayertime_pane.getChildren().add(zuhr_jamma_Box);

//============================= 
        HBox asr_jamma_Box = new HBox();
        asr_jamma_Box.setSpacing(0);
        asr_jamma_Box.setMaxSize(155, 54);
        asr_jamma_Box.getChildren().addAll(asr_jamma_hourLeft, asr_jamma_hourRight, time_jamma_Separator3, asr_jamma_minLeft, asr_jamma_minRight);
        prayertime_pane.setConstraints(asr_jamma_Box, 0, 5);
        prayertime_pane.getChildren().add(asr_jamma_Box);
        
//============================= 
        
        HBox maghrib_jamma_Box = new HBox();
        maghrib_jamma_Box.setSpacing(0);
        maghrib_jamma_Box.setMaxSize(155, 54);
        maghrib_jamma_Box.getChildren().addAll(maghrib_jamma_hourLeft, maghrib_jamma_hourRight, time_jamma_Separator4, maghrib_jamma_minLeft, maghrib_jamma_minRight);
        prayertime_pane.setConstraints(maghrib_jamma_Box, 0, 7);
        prayertime_pane.getChildren().add(maghrib_jamma_Box);
        
//============================= 
        
        HBox isha_jamma_Box = new HBox();
        isha_jamma_Box.setSpacing(0);
        isha_jamma_Box.setMaxSize(155, 54);
        isha_jamma_Box.getChildren().addAll(isha_jamma_hourLeft, isha_jamma_hourRight, time_jamma_Separator5, isha_jamma_minLeft, isha_jamma_minRight);
        prayertime_pane.setConstraints(isha_jamma_Box, 0, 9);
        prayertime_pane.getChildren().add(isha_jamma_Box);
        
 //============================= 
              
       
        Mainpane.add(clock1Box, 12, 1,4,4);
//        Mainpane.setConstraints(clock1Box, 5, 1);
//        Mainpane.getChildren().add(clock1Box);
        
//        Mainpane.setConstraints(prayertime_pane, 2, 3);
        Mainpane.add(prayertime_pane, 1, 6,7,7);
//        Mainpane.getChildren().add(prayertime_pane);
        
        
     
        stage.show();
        

//        stage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch(args);
        

    }

public void buildData_calculate() throws Exception{
              
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

             java.util.Date now = new java.util.Date();
             Calendar cal = Calendar.getInstance();
             cal.setTime(now);

             ArrayList<String> prayerTimes = getprayertime.getPrayerTimes(cal, latitude, longitude, timezone);
             ArrayList<String> prayerNames = getprayertime.getTimeNames();

             for (int i = 0; i < prayerTimes.size(); i++) {
                System.out.println(prayerNames.get(i) + " - " + prayerTimes.get(i));
             }
            
             String fajr_prayertime  =       prayerTimes.get(0);
//                sunrise =           prayerTimes.get(1);
             String zuhr_prayertime =       prayerTimes.get(2);
             String asr_prayertime =        prayerTimes.get(3);
             String maghrib_prayertime =    prayerTimes.get(5);
             String isha_prayertime =       prayerTimes.get(6);
              
             fajr_hourLeft.setText(fajr_prayertime.substring(0, 1));
             fajr_hourRight.setText(fajr_prayertime.substring(1, 2));
             fajr_minLeft.setText(fajr_prayertime.substring(3, 4));
             fajr_minRight.setText(fajr_prayertime.substring(4, 5));
             
             zuhr_hourLeft.setText(zuhr_prayertime.substring(0, 1));
             zuhr_hourRight.setText(zuhr_prayertime.substring(1, 2));
             zuhr_minLeft.setText(zuhr_prayertime.substring(3, 4));
             zuhr_minRight.setText(zuhr_prayertime.substring(4, 5));
             
             asr_hourLeft.setText(asr_prayertime.substring(0, 1));
             asr_hourRight.setText(asr_prayertime.substring(1, 2));
             asr_minLeft.setText(asr_prayertime.substring(3, 4));
             asr_minRight.setText(asr_prayertime.substring(4, 5));
             
             maghrib_hourLeft.setText(maghrib_prayertime.substring(0, 1));
             maghrib_hourRight.setText(maghrib_prayertime.substring(1, 2));
             maghrib_minLeft.setText(maghrib_prayertime.substring(3, 4));
             maghrib_minRight.setText(maghrib_prayertime.substring(4, 5));
             
             isha_hourLeft.setText(isha_prayertime.substring(0, 1));
             isha_hourRight.setText(isha_prayertime.substring(1, 2));
             isha_minLeft.setText(isha_prayertime.substring(3, 4));
             isha_minRight.setText(isha_prayertime.substring(4, 5));
             
             time_Separator1.setText(":");
             time_Separator2.setText(":");
             time_Separator3.setText(":");
             time_Separator4.setText(":");
             time_Separator5.setText(":");

//             if (isInternetReachable()){ System.out.println("connected"); control.setIndicatorStyle(SimpleIndicator.IndicatorStyle.GREEN);} else {System.out.println("not connected");control.setIndicatorStyle(SimpleIndicator.IndicatorStyle.RED);}
     Moon m = new Moon();
        int moonPhase =  m.illuminatedPercentage();
        System.out.println("The moon is " + moonPhase + "% full");
        System.out.println("The next full moon is on: " + MoonPhaseFinder.findFullMoonFollowing(Calendar.getInstance()));
        System.out.println("The next new moon is on: " + MoonPhaseFinder.findNewMoonFollowing(Calendar.getInstance()));
        
        }    
    
    
    
        //CONNECTION DATABASE SAVING DATA
    public void buildData_database(){
        
          Connection c ;
           ObservableList<String> names = FXCollections.observableArrayList();
          data = FXCollections.observableArrayList();
          try{
            c = DBConnect.connect();
            System.out.println("connected");
            //SQL FOR SELECTING NATIONALITY OF CUSTOMER
            String SQL = "select * from jos_prayertimes where DATE(date) = DATE(NOW())";

            ResultSet rs = c.createStatement().executeQuery(SQL);
            System.out.println("query");
             while (rs.next()) {

                id =                rs.getInt("id");
                prayer_date =       rs.getDate("date");
                fajr_begins =       rs.getTime("fajr_begins");
                fajr_jamaat =       rs.getTime("fajr_jamaat");
                sunrise =           rs.getTime("sunrise");
                zuhr_begins =       rs.getTime("zuhr_begins");
                zuhr_jamaat =       rs.getTime("zuhr_jamaat");
                asr_begins =        rs.getTime("asr_begins");
                asr_jamaat =        rs.getTime("asr_jamaat");
                maghrib_jamaat =    rs.getTime("maghrib_jamaat");
                isha_begins =       rs.getTime("isha_begins");
                isha_jamaat =       rs.getTime("isha_jamaat");
                
//        String lastName = rs.getString("last_name");
//        boolean  isAdmin = rs.getBoolean("is_admin");             
             }
             
             c.close();
             System.out.println("disconnected");
             
             String fajr_prayertime = fajr_begins.toString();
             String zuhr_prayertime = zuhr_begins.toString();
             String asr_prayertime = asr_begins.toString();
             String maghrib_prayertime = maghrib_jamaat.toString();
             String isha_prayertime = isha_begins.toString();
             
           // print the results
             System.out.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s \n", id, prayer_date, fajr_begins, fajr_jamaat, sunrise, zuhr_begins, zuhr_jamaat, asr_begins, asr_jamaat, maghrib_jamaat, isha_begins, isha_jamaat );
             
             fajr_hourLeft.setText(fajr_prayertime.substring(0, 1));
             fajr_hourRight.setText(fajr_prayertime.substring(1, 2));
             fajr_minLeft.setText(fajr_prayertime.substring(3, 4));
             fajr_minRight.setText(fajr_prayertime.substring(4, 5));
             
             zuhr_hourLeft.setText(zuhr_prayertime.substring(0, 1));
             zuhr_hourRight.setText(zuhr_prayertime.substring(1, 2));
             zuhr_minLeft.setText(zuhr_prayertime.substring(3, 4));
             zuhr_minRight.setText(zuhr_prayertime.substring(4, 5));
             
             asr_hourLeft.setText(asr_prayertime.substring(0, 1));
             asr_hourRight.setText(asr_prayertime.substring(1, 2));
             asr_minLeft.setText(asr_prayertime.substring(3, 4));
             asr_minRight.setText(asr_prayertime.substring(4, 5));
             
             maghrib_hourLeft.setText(maghrib_prayertime.substring(0, 1));
             maghrib_hourRight.setText(maghrib_prayertime.substring(1, 2));
             maghrib_minLeft.setText(maghrib_prayertime.substring(3, 4));
             maghrib_minRight.setText(maghrib_prayertime.substring(4, 5));
             
             isha_hourLeft.setText(isha_prayertime.substring(0, 1));
             isha_hourRight.setText(isha_prayertime.substring(1, 2));
             isha_minLeft.setText(isha_prayertime.substring(3, 4));
             isha_minRight.setText(isha_prayertime.substring(4, 5));
             
             time_Separator1.setText(":");
             time_Separator2.setText(":");
             time_Separator3.setText(":");
             time_Separator4.setText(":");
             time_Separator5.setText(":");
 
            }
          
          catch(SQLException e){
              System.out.println("Error on Database connection");
          }
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

}