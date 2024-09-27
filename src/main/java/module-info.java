module jp.udonbei.udonpasuta {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires lombok;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires org.slf4j;
    requires java.desktop;

    opens jp.udonbei.udonpasuta.controller to javafx.fxml;
    exports jp.udonbei.udonpasuta;
    exports jp.udonbei.udonpasuta.logging.filter;
    exports jp.udonbei.udonpasuta.control;
}