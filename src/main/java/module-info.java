module jp.u_donbei.udon_pasuta {
	requires javafx.controls;
	requires javafx.fxml;
	requires lombok;
	requires ch.qos.logback.classic;
	requires ch.qos.logback.core;
	requires org.slf4j;
	requires java.desktop;

	opens jp.u_donbei.udon_pasuta.controller to javafx.fxml;
	exports jp.u_donbei.udon_pasuta;
	exports jp.u_donbei.udon_pasuta.logging.filter;
	exports jp.u_donbei.udon_pasuta.control;
}