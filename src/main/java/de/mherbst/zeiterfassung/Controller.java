package de.mherbst.zeiterfassung;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Label ergebnisLblMaxGehen;

    @FXML
    private Label ergebnisLblAnwesenheit;

    @FXML
    private Label ergebnisLblPause;

    @FXML
    private Label ergebnisLblArbeitszeit;

    @FXML
    private Label ergebnisLblIndustrie;

    @FXML
    public TextField txtfldKommen;

    @FXML
    public TextField txtfldGehen;

    @FXML
    private Button btnExit;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize ( URL location, ResourceBundle resources ) {

        /**
         * Processing the TextField  txtfldKommen
         */
        txtfldKommen.setOnKeyPressed ( event -> {
            switch (event.getCode ( )) {
                case TAB, ENTER, DOWN -> {
                    // System.out.println ( event.getCode ( ) );
                    String zeitString = txtfldKommen.getText ( ).trim ( );
                    if ( CalcTime.checkTimePattern ( zeitString ) ) {
                        CalcTime.setKommenZeit ( CalcTime.convertStringToLocalTime ( zeitString ) );
                        LocalTime maxUhrzeit = CalcTime.addMinutesToLocalTime ( CalcTime.convertStringToLocalTime ( zeitString ), 645 );
                        ergebnisLblMaxGehen.setText ( CalcTime.convertLocalTimeToTimeString ( maxUhrzeit ) );

                        ergebnisLblAnwesenheit.setText ( CalcTime.convertMinutesToTimeString ( CalcTime.getTimeDiff ( ) ) );
                        ergebnisLblPause.setText ( CalcTime.convertMinutesToTimeString ( CalcTime.calcPause ( ) ) );
                        ergebnisLblArbeitszeit.setText ( CalcTime.convertMinutesToTimeString ( CalcTime.calcWorktime ( ) ) );
                        ergebnisLblIndustrie.setText ( CalcTime.calcIndustryTime () );

                        txtfldGehen.requestFocus ( );
                    } else {
                        txtfldKommen.setText ( CalcTime.convertLocalTimeToTimeString ( LocalTime.now ( ) ) );
                    }
                }
            }
        } );

        /**
         * Processing the TextField  txtfldGehen
         */
        txtfldGehen.setOnKeyPressed ( event -> {
            switch (event.getCode ( )) {
                case TAB, ENTER, UP -> {
                    String zeitString = txtfldGehen.getText ( ).trim ( );
                    if ( CalcTime.checkTimePattern ( zeitString ) ) {
                        CalcTime.setGehenZeit ( CalcTime.convertStringToLocalTime ( zeitString ) );

                        ergebnisLblAnwesenheit.setText ( CalcTime.convertMinutesToTimeString ( CalcTime.getTimeDiff ( ) ) );
                        ergebnisLblPause.setText ( CalcTime.convertMinutesToTimeString ( CalcTime.calcPause ( ) ) );
                        ergebnisLblArbeitszeit.setText ( CalcTime.convertMinutesToTimeString ( CalcTime.calcWorktime ( ) ) );
                        ergebnisLblIndustrie.setText ( CalcTime.calcIndustryTime () );

                        btnExit.requestFocus ( );
                    } else {
                        txtfldGehen.setText ( CalcTime.convertLocalTimeToTimeString ( LocalTime.now ( ) ) );
                    }
                }
            }
        } );

        /**
         * Processing the exit button
         */
        btnExit.setOnAction ( new EventHandler<ActionEvent> ( ) {
            @Override
            public void handle ( ActionEvent event ) {
                System.exit ( 0 );
            }
        } );

    }
}