/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

//import java.util.Locale;
//import org.joda.time.*;
import eu.hansolo.enzo.clock.Clock;
import eu.hansolo.enzo.clock.ClockBuilder;
import eu.hansolo.enzo.imgsplitflap.SplitFlap;
import eu.hansolo.enzo.imgsplitflap.SplitFlapBuilder;
import java.sql.Connection;
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
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.RowConstraintsBuilder;
import java.util.Calendar;
import com.bradsbrain.simpleastronomy.MoonPhaseFinder;
//import java.text.SimpleDateFormat;
import java.util.Locale;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.time.LocalTime;
import java.util.Date;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

//import org.joda.time.chrono.JulianChronology;

/**
 *
 * @author ossama
 */
   
    public class JavaFXApplication4 extends Application {

    private Clock clock;
    private ObservableList data;
    private Label Phase_Label, Moon_Date_Label, Moon_Image_Label;
    private Integer moonPhase;
    private static final String[] WEEK_DAYS = {
        "SUN","MON","TUE","WED","THU","FRI","SAT"
    };
    private static final String[] MONTHS = {
        "JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"
    };
    private int id;
    private Date prayer_date;
    private Time fajr_begins,fajr_jamaat, sunrise, zuhr_begins, zuhr_jamaat, asr_begins, asr_jamaat, maghrib_begins, maghrib_jamaat,isha_begins, isha_jamaat;
    private SplitFlap fajr_hourLeft, fajr_hourRight, fajr_minLeft, fajr_minRight, fajr_jamma_hourLeft, fajr_jamma_hourRight, fajr_jamma_minLeft, fajr_jamma_minRight;
    private SplitFlap time_Separator1, time_Separator2, time_Separator3, time_Separator4 ,time_Separator5, time_jamma_Separator1, time_jamma_Separator2, time_jamma_Separator3, time_jamma_Separator4 ,time_jamma_Separator5; 
    private SplitFlap zuhr_hourLeft, zuhr_hourRight, zuhr_minLeft, zuhr_minRight, zuhr_jamma_hourLeft, zuhr_jamma_hourRight, zuhr_jamma_minLeft, zuhr_jamma_minRight;
    private SplitFlap asr_hourLeft, asr_hourRight, asr_minLeft, asr_minRight, asr_jamma_hourLeft, asr_jamma_hourRight, asr_jamma_minLeft, asr_jamma_minRight;
    private SplitFlap maghrib_hourLeft, maghrib_hourRight, maghrib_minLeft, maghrib_minRight, maghrib_jamma_hourLeft, maghrib_jamma_hourRight, maghrib_jamma_minLeft, maghrib_jamma_minRight;
    private SplitFlap isha_hourLeft, isha_hourRight, isha_minLeft, isha_minRight, isha_jamma_hourLeft, isha_jamma_hourRight, isha_jamma_minLeft, isha_jamma_minRight;
    private long moonPhase_lastTimerCall,prayerTime_lastTimerCall;
    private AnimationTimer moonPhase_timer, prayerTime_timer;
    

    @Override public void init() {
        
// load the font.
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/PTBLARC.TTF").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BMajidSh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Oldoutsh.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/BJadidBd.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/wlm_carton.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Arial_Black.ttf").toExternalForm(),30);
        Font.loadFont(JavaFXApplication4.class.getResource("Fonts/Arial_Bold.ttf").toExternalForm(),30);
        
        Moon_Image_Label = new Label();
        Phase_Label = new Label();
        Moon_Date_Label = new Label();
        clock = new Clock();
        clock = ClockBuilder.create()
                             .prefSize(200, 200)
                             .design(Clock.Design.IOS6)
                             .discreteSecond(true)
                             .secondPointerVisible(true)
                             .build();
        clock.setCache(true);

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

        moonPhase_lastTimerCall = System.nanoTime();
//        prayerTime_lastTimerCall = System.nanoTime();
        
        
        prayerTime_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now > prayerTime_lastTimerCall + 1000000_000_000l) {
                    
                    try {
//                      buildData_database();
                        buildData_calculate();
                    } catch (Exception ex) {
                        Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    prayerTime_lastTimerCall = now;
                }
            }
        };
        
        moonPhase_timer = new AnimationTimer() {
            @Override public void handle(long now) {
                if (now >moonPhase_lastTimerCall + 1000000_000_000l) {
                    
                    try {
//                      buildData_database();
                        buildData_calculate();
                    } catch (Exception ex) {
                        Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                    }
 
                    moonPhase_lastTimerCall = now;
                }
            }
        };
        
    }

    @Override public void start(Stage stage) {
        
        Group root = new Group();
        Scene scene = new Scene(root, 720, 1230); //1180, 650
        stage.setScene(scene);
        stage.setTitle("Prayer Time Display");
                
        GridPane Mainpane = new GridPane();
        scene.setRoot(Mainpane);
        
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
        
        GridPane prayertime_pane = new GridPane();
        prayertime_pane.setId("prayertime_pane");
        prayertime_pane.setCache(true);       
//        prayertime_pane.setGridLinesVisible(true);
        prayertime_pane.setPadding(new Insets(20, 20, 20, 20));
        prayertime_pane.setAlignment(Pos.BASELINE_CENTER);
        prayertime_pane.setVgap(7);
        prayertime_pane.setHgap(35);

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
        
 //===MOON PANE==========================         
        
        BorderPane Moonpane = new BorderPane();
        Moonpane.setId("moonpane");
        Moonpane.setPadding(new Insets(10, 10, 10, 10));
        Moonpane.setMaxSize(380,70);
        Moonpane.setMinSize(300,70);
        Phase_Label.setId("moon-text-english");
        Moonpane.setRight(Phase_Label);
//        myLabel.textProperty().bind(valueProperty);
        ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/0%.png")));      
        Moon_img.setFitWidth(100);
        Moon_img.setFitHeight(100);
        Moon_img.setPreserveRatio(true);
        Moon_img.setSmooth(true);
        Moon_Image_Label.setGraphic(Moon_img);
        Moonpane.setCenter(Moon_Image_Label);   
        Moon_Date_Label.setId("moon-text-english");
        Moonpane.setLeft(Moon_Date_Label);
        Reflection r = new Reflection();
        r.setFraction(0.15f);
        Moonpane.setEffect(r);
        
  //============================================
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(10.0);
        ds.setOffsetX(10.0);
        ds.setColor(Color.BLACK);
        prayertime_pane.setEffect(ds);
        
        Mainpane.add(Moonpane, 4, 1,3,3);                       
        Mainpane.add(clock, 1, 1,2,2);    
        Mainpane.add(prayertime_pane, 1, 5,8,5);                     
        Mainpane.setCache(true);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        stage.show();
        prayerTime_timer.start();
        

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
        
        System.out.println("The moon is " + m.illuminatedPercentage() + "% full and " + (m.isWaning() ? "waning" : "waxing"));
        System.out.println("The next full moon is on: " + MoonPhaseFinder.findFullMoonFollowing(Calendar.getInstance()));
        System.out.println("The next new moon is on: " + MoonPhaseFinder.findNewMoonFollowing(Calendar.getInstance()));
                
//        String FullMoon_date_en = new SimpleDateFormat("EEEE dd'th' MMM").format(MoonPhaseFinder.findFullMoonFollowing(Calendar.getInstance()));
//        Moon_Date_Label.setId("moon-text-english");
//        Moon_Date_Label.setText("Next Full Moon is on\n" + FullMoon_date_en);
        
        
        String FullMoon_date_ar = new SimpleDateFormat(" EEEE' '  dd  MMMM", new Locale("ar")).format(MoonPhaseFinder.findFullMoonFollowing(Calendar.getInstance()));
        Moon_Date_Label.setId("moon-text-arabic");
        Moon_Date_Label.setText("سيكون القمر كاملا يوم\n" + FullMoon_date_ar);
        
        
        FadeTransition ft = new FadeTransition(Duration.millis(16000), Moon_Date_Label);
     ft.setFromValue(0.3);
     ft.setToValue(1);
     ft.setCycleCount(1);
     ft.setAutoReverse(false);
 
     ft.play();
        
        
        if (m.illuminatedPercentage()== 0)
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/0%.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>3 && m.illuminatedPercentage()<=10 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/3%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>10 && m.illuminatedPercentage()<=17 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/12%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>17 && m.illuminatedPercentage()<=32 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/21%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        if (m.illuminatedPercentage()>32 && m.illuminatedPercentage()<=43 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/38%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>43 && m.illuminatedPercentage()<=52 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/47%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>52 && m.illuminatedPercentage()<=61 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>61 && m.illuminatedPercentage()<=70 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/65%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>70 && m.illuminatedPercentage()<=78 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/74%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>78 && m.illuminatedPercentage()<=87 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/82%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>87 && m.illuminatedPercentage()<=96 && m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/91%WA.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()== 100)
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/100%.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>4 && m.illuminatedPercentage()<=12 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/8%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>12 && m.illuminatedPercentage()<=20 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/16%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>20 && m.illuminatedPercentage()<=28 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/24%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>28 && m.illuminatedPercentage()<=36 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/32%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>36 && m.illuminatedPercentage()<=44 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/40%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>44 && m.illuminatedPercentage()<=52 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/48%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>52 && m.illuminatedPercentage()<=59 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/56%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>59 && m.illuminatedPercentage()<=67 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/63%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>67 && m.illuminatedPercentage()<=74 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/71%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>74 && m.illuminatedPercentage()<=82 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/78%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>82 && m.illuminatedPercentage()<=90 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/86%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
        
        if (m.illuminatedPercentage()>90 && m.illuminatedPercentage()<=98 && !m.isWaning())
        {
            ImageView Moon_img = new ImageView(new Image(getClass().getResourceAsStream("/Images/Moon/94%WX.png")));      
            Moon_img.setFitWidth(100);
            Moon_img.setFitHeight(100);
            Moon_img.setPreserveRatio(true);
            Moon_img.setSmooth(true);        
            Moon_Image_Label.setGraphic(Moon_img);
        }
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