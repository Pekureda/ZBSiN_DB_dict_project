package put.projdbdict.dbdictproj;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class Flashcard {
    private StringProperty character;
    private ListProperty<String> meanings;
    private ListProperty<String> readings;
    private ListProperty<String> examples;

    public Flashcard() {
        this.character = new SimpleStringProperty();
        this.meanings = new SimpleListProperty<>();
        this.readings = new SimpleListProperty<>();
        this.examples = new SimpleListProperty<>();
    }

    // Kanji character
    public String getKanjiCharacter(){
        return character.get();
    }

    public void setKanjiCharacter(String str){
        this.character.set(str);
    }

    public StringProperty kanjiCharacterProperty(){
        return character;
    }

    // Meanings
    public List<String> getMeanings(){
        return meanings.get();
    }

    public void setMeanings(ObservableList<String> lst){
        this.meanings.set(lst);
    }

    public ListProperty<String> meaningsProperty() {
        return meanings;
    }

    // Readings
    public List<String> getReadings(){
        return readings.get();
    }

    public void setReadings(ObservableList<String> lst){
        this.readings.set(lst);
    }

    public ListProperty<String> readingsProperty(){
        return readings;
    }

    // Examples
    public List<String> getExamples(){
        return examples.get();
    }

    public void setExamples(ObservableList<String> lst){
        this.examples.set(lst);
    }

    public ListProperty<String> examplesProperty() {
        return examples;
    }

}
