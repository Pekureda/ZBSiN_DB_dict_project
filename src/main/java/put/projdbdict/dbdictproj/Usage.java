package put.projdbdict.dbdictproj;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usage {
    private StringProperty kanji_character;
    private IntegerProperty example_id;
    private StringProperty example;

    public Usage() {
        this.kanji_character = new SimpleStringProperty();
        this.example_id = new SimpleIntegerProperty();
        this.example = new SimpleStringProperty();
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

    // Usage example_id
    public int getExampleId(){
        return example_id.get();
    }

    public void setExampleId(int nid){
        this.example_id.set(nid);
    }

    public IntegerProperty exampleIdProperty(){
        return example_id;
    }

    // Usage example
    public String getUsageExample(){
        return example.get();
    }

    public void setUsageExample(String str){
        this.example.set(str);
    }

    public StringProperty usageExampleProperty(){
        return example;
    }
}
