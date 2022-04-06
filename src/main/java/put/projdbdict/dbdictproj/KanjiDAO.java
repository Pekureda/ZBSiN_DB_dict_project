package put.projdbdict.dbdictproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KanjiDAO {
    private static Kanji getKanjiFromResultSet(ResultSet rs) throws SQLException {
        Kanji kanji = null;
        if (rs.next()) {
            kanji = new Kanji();
            kanji.setKanjiDate_added(rs.getDate("date_added"));
            kanji.setKanjiCharacter(rs.getString("character"));
            kanji.setKanjiProficiency(rs.getInt("proficiency"));
            kanji.setKanjiLast_tested(rs.getDate("last_tested"));
            kanji.setKanjiJLPT_classification(rs.getInt("jlpt_classification"));
        }
        return kanji;
    }

    private static ObservableList<Kanji> getKanjiList(ResultSet rs) throws SQLException {
        ObservableList<Kanji> kanjiList = FXCollections.observableArrayList();

        while (rs.next()) {
            Kanji kanji = new Kanji();
            kanji.setKanjiDate_added(rs.getDate("date_added"));
            kanji.setKanjiCharacter(rs.getString("character"));
            kanji.setKanjiProficiency(rs.getInt("proficiency"));
            kanji.setKanjiLast_tested(rs.getDate("last_tested"));
            kanji.setKanjiJLPT_classification(rs.getInt("jlpt_classification"));

            kanjiList.add(kanji);
        }

        return kanjiList;
    }

    public static ObservableList<Kanji> getAllKanji() throws SQLException {
        String selectStmt = "select * from kanji";

        ResultSet rs = DBUtil.obj.executeQuery(selectStmt);
        System.out.println("Good");
        ObservableList<Kanji> kanjiList = getKanjiList(rs);


        return kanjiList;
    }
}
