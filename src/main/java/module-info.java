module sae.saezelda {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens sae.saezelda to javafx.fxml;
    exports sae.saezelda;

    exports sae.saezelda.controleur;
    opens sae.saezelda.controleur to javafx.fxml;

    exports sae.saezelda.modele;
    opens sae.saezelda.modele to javafx.fxml;

    exports sae.saezelda.vue;
    opens sae.saezelda.vue to javafx.fxml;
}

