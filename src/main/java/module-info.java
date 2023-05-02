module group3.mindfactory_administration {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;


    opens group3.mindfactory_administration to javafx.fxml;
    exports group3.mindfactory_administration;
    exports group3.mindfactory_administration.controllers;
    opens group3.mindfactory_administration.controllers to javafx.fxml;
}