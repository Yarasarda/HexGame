module hexstrategy.group51.demo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens hexstrategy.group51 to javafx.fxml;
    exports hexstrategy.group51;
}