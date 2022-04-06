package put.projdbdict.dbdictproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadingDAO {
    private static Reading getReadingFromResultSet(ResultSet rs) throws SQLException {
        Reading reading = null;
        if (rs.next()) {
            reading = new Reading();
            reading.setKanjiCharacter(rs.getString("kanji_character"));
            reading.setReadingId(rs.getInt("reading_id"));
            reading.setReadingReading(rs.getString("reading"));
        }
        return reading;
    }

    private static ObservableList<Reading> getReadingList(ResultSet rs) throws SQLException {
        ObservableList<Reading> objList = FXCollections.observableArrayList();

        while (rs.next()) {
            Reading obj = new Reading();
            obj.setKanjiCharacter(rs.getString("kanji_character"));
            obj.setReadingId(rs.getInt("reading_id"));
            obj.setReadingReading(rs.getString("reading"));

            objList.add(obj);
        }
        return objList;
    }

    public static ObservableList<Reading> getAllReadings() throws SQLException {
        String selectStmt = "select * from reading";

        ResultSet rs = DBUtil.obj.executeQuery(selectStmt);

        ObservableList<Reading> objList = getReadingList(rs);


        return objList;
    }
}
