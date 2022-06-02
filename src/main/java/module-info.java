module tuvarna.bg.warehouse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires itextpdf;

    opens tuvarna.bg.warehouse to javafx.fxml;
    opens tuvarna.bg.warehouse.controllers to javafx.fxml;
    opens tuvarna.bg.warehouse.models to javafx.base;
    exports tuvarna.bg.warehouse;
}