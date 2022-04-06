package put.projdbdict.dbdictproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class FlashcardDAO {

    private static Flashcard getFlashcardFromResultSet(ResultSet rs) throws SQLException {
        Flashcard flashcard = null;
        if (rs.next()) {
            flashcard = new Flashcard();
            flashcard.setMeanings(rs.getObject("meanings", ObservableList.class));
            flashcard.setKanjiCharacter(rs.getString("kanji"));
            flashcard.setReadings(rs.getObject("readings", ObservableList.class));
            flashcard.setExamples(rs.getObject("examples", ObservableList.class));
        }
        return flashcard;
    }

    private static ObservableList<Flashcard> getFlashcardList(ResultSet rs) throws SQLException {
        ObservableList<Flashcard> flashcardList = FXCollections.observableArrayList();

        while (rs.next()) {
            Flashcard flashcard = new Flashcard();
            flashcard.setMeanings(rs.getObject("meanings", ObservableList.class));
            flashcard.setKanjiCharacter(rs.getString("kanji"));
            flashcard.setReadings(rs.getObject("readings", ObservableList.class));
            flashcard.setExamples(rs.getObject("examples", ObservableList.class));

            flashcardList.add(flashcard);
        }

        return flashcardList;
    }

    public static Flashcard getFlashcard(String character) throws SQLException {
        CallableStatement stmt = DBUtil.prepareCallFunction("getFlashKanjiInfo", 1);
        stmt.registerOutParameter(1,  Types.STRUCT, "flashcard");
        stmt.setString(2, character);
        stmt.executeQuery();
        Flashcard fl = stmt.getObject(1, Flashcard.class);
        //ObservableList<Flashcard> flashcardList = getFlashcardList(rs);


        return fl;
    }
}
