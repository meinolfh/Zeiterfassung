package de.mherbst.zeiterfassung;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;

public class Application extends javafx.application.Application {

    @Override
    public void init () throws Exception {
        // System.out.println ( "Init Method" );
    }

    @Override
    public void start ( Stage stage ) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader ( ZeiterfassungApplication.class.getResource ( "MainView.fxml" ) );
        final FXMLLoader fxmlLoader = new FXMLLoader ( this.getClass ( ).getResource ( "MainView.fxml" ) );
        final Parent root = ( Parent ) fxmlLoader.load ( );
        final Controller controller = ( Controller ) fxmlLoader.getController ( );
        final Scene scene = new Scene ( root, 400, 520 );

        scene.getStylesheets ( ).add ( Objects.requireNonNull ( this.getClass ( ).getResource ( "MainView.css" ) ).toExternalForm ( ) );

        final LocalTime startZeit = CalcTime.getStartzeit ( );

        // 10 h <==> 600 min + 45 min Pause = 645 min
        final LocalTime maxUhrzeit = CalcTime.addMinutesToLocalTime ( startZeit, 645 );

        controller.txtfldGehen.setText ( "00:00" );
        controller.txtfldKommen.setText ( CalcTime.convertLocalTimeToTimeString ( startZeit ) );
        controller.ergebnisLblMaxGehen.setText ( CalcTime.convertLocalTimeToTimeString ( maxUhrzeit ) );

        stage.getIcons ().add ( new Image ( Objects.requireNonNull ( this.getClass ( ).getResourceAsStream ( "uhr.png" ) ) ));
        stage.setTitle ( "Arbeitszeitprüfung" );
        stage.setResizable ( false );
        stage.setScene ( scene );
        stage.show ( );
    }

    /**
     *
     * @param args von außen übergebene Argumente
     */
    public static void main ( String[] args ) {
        launch ( );
    }
}

