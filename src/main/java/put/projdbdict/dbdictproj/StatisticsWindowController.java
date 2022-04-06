package put.projdbdict.dbdictproj;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;

public class StatisticsWindowController {
    public Text countText;
    public TextArea CharacterList;
    public DatePicker dateFrom;
    public DatePicker dateTo;

    @FXML
    private void initialize() {
        dateFrom.setValue(LocalDate.now());
        dateTo.setValue(LocalDate.now());
    }

    void updateStats() throws SQLException {
        CallableStatement stmt = DBUtil.prepareCallFunction("getKanjiStats", 2);
        stmt.registerOutParameter(1, Types.VARCHAR);
        stmt.setDate(2, Date.valueOf(dateFrom.getValue()));
        stmt.setDate(3, Date.valueOf(dateTo.getValue()));

        String[] resarr = new String[0];
        String finStr = "";

        stmt.execute();
        String res = stmt.getString(1);
        if (res != null) {
            resarr = res.split(",");

            for (String elem : resarr) {
                finStr += elem + ", ";
            }
            finStr = finStr.substring(0, finStr.length() - 2);
        }
        countText.setText("Character count: " + resarr.length);

        CharacterList.setText(finStr);
    }

    public void fromOnHiding(Event event) throws SQLException {
        updateStats();
    }

    public void toOnHiding(Event event) throws SQLException {
        updateStats();
    }
}
