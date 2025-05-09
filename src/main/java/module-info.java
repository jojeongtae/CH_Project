module com.example.ch_project_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.ch_project_fx to javafx.fxml;
    exports com.example.ch_project_fx;
}