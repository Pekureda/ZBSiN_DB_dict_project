package put.projdbdict.dbdictproj;

import javafx.beans.property.*;

import java.sql.Date;

public class Kanji {
    private StringProperty character;
    private SimpleObjectProperty<Date> date_added;
    private IntegerProperty proficiency;
    private SimpleObjectProperty<Date> last_tested;
    private IntegerProperty JLPT_classification;

    public Kanji() {
        this.character = new SimpleStringProperty();
        this.date_added = new SimpleObjectProperty<>();
        this.proficiency = new SimpleIntegerProperty();
        this.last_tested = new SimpleObjectProperty<>();
        this.JLPT_classification = new SimpleIntegerProperty();
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

    // Kanji date_added
    public Date getKanjiDate_added(){
        return date_added.get();
    }

    public void setKanjiDate_added(Date date){
        this.date_added.set(date);
    }

    public SimpleObjectProperty<Date> kanjiDate_addedProperty() {
        return date_added;
    }

    // Kanji proficiency
    public int getKanjiProficiency(){
        return proficiency.get();
    }

    public void setKanjiProficiency(int nid){
        this.proficiency.set(nid);
    }

    public IntegerProperty kanjiProficiencyProperty(){
        return proficiency;
    }

    // Kanji last_tested
    public Date getKanjiLast_tested(){
        return last_tested.get();
    }

    public void setKanjiLast_tested(Date date){
        this.last_tested.set(date);
    }

    public SimpleObjectProperty<Date> kanjiLast_testedProperty() {
        return last_tested;
    }

    // Kanji JLPT_classification
    public int getKanjiJLPT_classification(){
        return JLPT_classification.get();
    }

    public void setKanjiJLPT_classification(int nid){
        this.JLPT_classification.set(nid);
    }

    public IntegerProperty kanjiJLPT_classificationProperty(){
        return JLPT_classification;
    }
}
