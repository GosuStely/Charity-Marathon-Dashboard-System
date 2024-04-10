module org.example.mysystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens org.example.mysystem to javafx.fxml;
    exports org.example.mysystem;
}