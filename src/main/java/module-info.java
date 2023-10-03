module com.valeriygulin.ssemultichatjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp.eventsource;
    requires com.fasterxml.jackson.databind;
    requires java.prefs;
    requires static lombok;


    opens com.valeriygulin.ssemultichatjavafx to javafx.fxml;
    exports com.valeriygulin.ssemultichatjavafx;
    exports com.valeriygulin.ssemultichatjavafx.controllers;
    opens com.valeriygulin.ssemultichatjavafx.controllers to javafx.fxml;
    exports com.valeriygulin.ssemultichatjavafx.dto;
    exports com.valeriygulin.ssemultichatjavafx.model;
}