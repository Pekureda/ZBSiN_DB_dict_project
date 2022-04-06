package put.projdbdict.dbdictproj;

import javafx.beans.property.*;

import java.sql.Date;

public class AdditionalNotes {
    private StringProperty kanji_character;
    private IntegerProperty note_id;
    private StringProperty type;
    private StringProperty note;
    private SimpleObjectProperty<Date> date_added;

    public AdditionalNotes() {
        this.kanji_character = new SimpleStringProperty();
        this.note_id = new SimpleIntegerProperty();
        this.type = new SimpleStringProperty();
        this.note = new SimpleStringProperty();
        this.date_added = new SimpleObjectProperty<>();
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

    // Note id
    public int getNoteId(){
        return note_id.get();
    }

    public void setNoteId(int nid){
        this.note_id.set(nid);
    }

    public IntegerProperty noteIdProperty(){
        return note_id;
    }

    // Note note
    public String getNote(){
        return note.get();
    }

    public void setNote(String str){
        this.note.set(str);
    }

    public StringProperty noteProperty(){
        return note;
    }

    // Note date_added
    public Date getNoteDate_added(){
        return date_added.get();
    }

    public void setNoteDate_added(Date date){
        this.date_added.set(date);
    }

    public SimpleObjectProperty<Date> noteDate_addedProperty() {
        return date_added;
    }

    // Note type
    public String getNoteType(){
        return type.get();
    }

    public void setNoteType(String str){
        this.type.set(str);
    }

    public StringProperty noteTypeProperty(){
        return type;
    }
}