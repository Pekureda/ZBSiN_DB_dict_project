module put.projdbdict.dbdictproj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    requires com.oracle.database.jdbc;


    opens put.projdbdict.dbdictproj to javafx.fxml;
    exports put.projdbdict.dbdictproj;
}