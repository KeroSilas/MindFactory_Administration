module group3.mindfactory_administration {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;


    opens group3.mindfactory_administration to javafx.fxml;
    exports group3.mindfactory_administration;
    exports group3.mindfactory_administration.controllers;
    opens group3.mindfactory_administration.controllers to javafx.fxml;
}