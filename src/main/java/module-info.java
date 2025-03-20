module de.mherbst.zeiterfassung {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;

    opens de.mherbst.zeiterfassung to javafx.fxml;
    exports de.mherbst.zeiterfassung;
}