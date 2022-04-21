module tuvarna.bg.warehouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens tuvarna.bg.warehouse to javafx.fxml;
    exports tuvarna.bg.warehouse;
}