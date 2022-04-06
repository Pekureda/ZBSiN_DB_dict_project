package put.projdbdict.dbdictproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsageDAO {
    private static Usage getUsageFromResultSet(ResultSet rs) throws SQLException {
        Usage obj = new Usage();
        if (rs.next()) {
            obj = new Usage();
            obj.setKanjiCharacter(rs.getString("kanji_character"));
            obj.setExampleId(rs.getInt("example_id"));
            obj.setUsageExample(rs.getString("example"));
        }
        return obj;
    }

    private static ObservableList<Usage> getUsageList(ResultSet rs) throws SQLException {
        ObservableList<Usage> objList = FXCollections.observableArrayList();

        while (rs.next()) {
            Usage obj = new Usage();
            obj.setKanjiCharacter(rs.getString("kanji_character"));
            obj.setExampleId(rs.getInt("example_id"));
            obj.setUsageExample(rs.getString("example"));

            objList.add(obj);
        }

        return objList;
    }

    public static ObservableList<Usage> getAllUsage() throws SQLException {
        String selectStmt = "select * from usage";

        ResultSet rs = DBUtil.obj.executeQuery(selectStmt);

        ObservableList<Usage> objList = getUsageList(rs);

        return objList;
    }
}
