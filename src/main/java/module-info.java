module group3.mindfactory_administration {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.naming;
    requires java.mail;
    requires java.desktop;


    opens group3.mindfactory_administration to javafx.fxml;
    exports group3.mindfactory_administration;
    exports group3.mindfactory_administration.controllers;
    opens group3.mindfactory_administration.controllers to javafx.fxml;
    exports group3.mindfactory_administration.dao;
    opens group3.mindfactory_administration.dao to javafx.fxml;
    exports group3.mindfactory_administration.model;
    opens group3.mindfactory_administration.model to javafx.fxml;
    exports group3.mindfactory_administration.dao.singleton;
    opens group3.mindfactory_administration.dao.singleton to javafx.fxml;
    exports group3.mindfactory_administration.model.nodes;
    opens group3.mindfactory_administration.model.nodes to javafx.fxml;
}