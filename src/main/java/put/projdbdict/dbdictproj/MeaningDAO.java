package put.projdbdict.dbdictproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeaningDAO {
    private static Meaning getMeaningFromResultSet(ResultSet rs) throws SQLException {
        Meaning obj = new Meaning();
        if (rs.next()) {
            obj = new Meaning();
            obj.setKanjiCharacter(rs.getString("kanji_character"));
            obj.setMeaningId(rs.getInt("meaning_id"));
            obj.setMeaningMeaning(rs.getString("meaning"));
        }
        return obj;
    }

    private static ObservableList<Meaning> getMeaningList(ResultSet rs) throws SQLException {
        ObservableList<Meaning> objList = FXCollections.observableArrayList();

        while (rs.next()) {
            Meaning obj = new Meaning();
            obj.setKanjiCharacter(rs.getString("kanji_character"));
            obj.setMeaningId(rs.getInt("meaning_id"));
            obj.setMeaningMeaning(rs.getString("meaning"));

            objList.add(obj);
        }

        return objList;
    }

    public static ObservableList<Meaning> getAllMeanings() throws SQLException {
        String selectStmt = "select * from meaning";

        ResultSet rs = DBUtil.obj.executeQuery(selectStmt);

        ObservableList<Meaning> objList = getMeaningList(rs);

        return objList;
    }
}
