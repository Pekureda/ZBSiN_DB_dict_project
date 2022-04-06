package put.projdbdict.dbdictproj;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Meaning {
    private StringProperty kanji_character;
    private IntegerProperty meaning_id;
    private StringProperty meaning;

    public Meaning() {
        this.kanji_character = new SimpleStringProperty();
        this.meaning_id = new SimpleIntegerProperty();
        this.meaning = new SimpleStringProperty();
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

    // Meaning id
    public int getMeaningId(){
        return meaning_id.get();
    }

    public void setMeaningId(int nid){
        this.meaning_id.set(nid);
    }

    public IntegerProperty meaningIdProperty(){
        return meaning_id;
    }

    // Meaning meaning
    public String getMeaningMeaning(){
        return meaning.get();
    }

    public void setMeaningMeaning(String str){
        this.meaning.set(str);
    }

    public StringProperty meaningMeaningProperty(){
        return meaning;
    }
}
