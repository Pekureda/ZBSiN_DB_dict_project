package put.projdbdict.dbdictproj;

import javafx.beans.property.*;

import java.sql.Date;

public class Reading {
    private StringProperty kanji_character;
    private IntegerProperty reading_id;
    private StringProperty reading;

    public Reading() {
        this.kanji_character = new SimpleStringProperty();
        this.reading_id = new SimpleIntegerProperty();
        this.reading = new SimpleStringProperty();
    }

    // Kanji character
    public String getKanjiCharacter(){
        return kanji_character.get();
    }

    public void setKanjiCharacter(String str){
        this.kanji_character.set(str);
    }

    public StringProperty kanjiCharacterProperty(){
        return kanji_character;
    }

    // Reading id
    public int getReadingId(){
        return reading_id.get();
    }

    public void setReadingId(int nid){
        this.reading_id.set(nid);
    }

    public IntegerProperty readingIdProperty(){
        return reading_id;
    }

    // Reading reading
    public String getReadingReading(){
        return reading.get();
    }

    public void setReadingReading(String str){
        this.reading.set(str);
    }

    public StringProperty readingReadingProperty(){
        return reading;
    }

}
