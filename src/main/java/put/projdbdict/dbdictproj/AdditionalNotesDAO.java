package put.projdbdict.dbdictproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdditionalNotesDAO {
    private static AdditionalNotes getAdditionalNotesFromResultSet(ResultSet rs) throws SQLException {
        AdditionalNotes obj = new AdditionalNotes();
        if (rs.next()) {
            obj = new AdditionalNotes();
            obj.setKanjiCharacter(rs.getString("kanji_character"));
            obj.setNote(rs.getString("note"));
            obj.setNoteType(rs.getString("type"));
            obj.setNoteId(rs.getInt("note_id"));
            obj.setNoteDate_added(rs.getDate("date_added"));
        }
        return obj;
    }

    private static ObservableList<AdditionalNotes> getAdditionalNotesList(ResultSet rs) throws SQLException {
        ObservableList<AdditionalNotes> objList = FXCollections.observableArrayList();

        while (rs.next()) {
            AdditionalNotes obj = new AdditionalNotes();
            obj.setKanjiCharacter(rs.getString("kanji_character"));
            obj.setNote(rs.getString("note"));
            obj.setNoteType(rs.getString("type"));
            obj.setNoteId(rs.getInt("note_id"));
            obj.setNoteDate_added(rs.getDate("date_added"));

            objList.add(obj);
        }

        return objList;
    }

    public static ObservableList<AdditionalNotes> getAllAdditionalNotes() throws SQLException {
        String selectStmt = "select * from additional_notes";

        ResultSet rs = DBUtil.obj.executeQuery(selectStmt);

        ObservableList<AdditionalNotes> objList = getAdditionalNotesList(rs);

        return objList;
    }
}
