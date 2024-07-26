module AtomicBomber {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires jdk.xml.dom;


    exports view;
    exports model;
    opens view to javafx.fxml, com.google.gson, java.compiler;
    opens model to javafx.fxml, com.google.gson;
    exports controller;
    opens controller to javafx.fxml, com.google.gson, java.compiler;
    exports view.animations;
    opens view.animations to javafx.fxml, com.google.gson;
    exports model.components;
    opens model.components to javafx.fxml, com.google.gson;
    exports model.bombs;
    opens model.bombs to com.google.gson, javafx.fxml;


}