module group3.mindfactory_administration {
    requires javafx.controls;
    requires javafx.fxml;


    opens group3.mindfactory_administration to javafx.fxml;
    exports group3.mindfactory_administration;
}