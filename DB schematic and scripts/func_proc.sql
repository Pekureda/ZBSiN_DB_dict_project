create or replace type meaningArr is table of varchar2(512);
/
create or replace type exampleArr is table of varchar2(256);
/
create or replace type readingArr is table of varchar2(50);
/
create or replace type flashcard as object (kanji varchar2(3), meanings meaningArr, readings readingArr, examples exampleArr);
/


create or replace function getFlashKanjiInfo(pChar kanji.character%type)
    return flashcard
    is
        vFlashcard flashcard;
    begin
        vFlashcard.kanji := pChar;
        select meaning bulk collect into vFlashcard.meanings from meaning where kanji_character = pChar;
        select reading bulk collect into vFlashcard.readings from reading where kanji_character = pChar;
        select example bulk collect into vFlashcard.examples from usage where kanji_character = pChar;
        
        return vFlashcard;
    end;
    
create or replace function getKanjiStats(pDateFrom date, pDateTo date)
    return varchar2
    is
        vResult varchar2(32767);
    begin
        for vKanji in (select character from kanji where date_added >= pDateFrom and date_added <= pDateTo) loop
            vResult := vResult || vKanji.character || ',';
        end loop;
        
        return vResult;
    end;
    
create or replace package IntKanji is

    procedure deleteByCharacter(pChar kanji.character%type);
    
    procedure addKanji(pChar kanji.character%type, pJLPT kanji.character%type default NULL);

    procedure updateProficiency(pChar kanji.character%type, pProf kanji.proficiency%type);
    
    procedure updateLastTested(pChar kanji.character%type);
    
    procedure updateJLPT(pChar kanji.character%type, pJLPT kanji.JLPT_classification%type);
    
end IntKanji;

create or replace package body IntKanji is

    procedure deleteByCharacter(pChar kanji.character%type) is
    begin
        delete from kanji where character = pChar;
    end;
    
    procedure addKanji(pChar kanji.character%type, pJLPT kanji.character%type default NULL) is
    begin
        insert into kanji values (pChar, CURRENT_DATE, 0, CURRENT_DATE, NULL);
    end;
    
    procedure updateProficiency(pChar kanji.character%type, pProf kanji.proficiency%type) is
    begin
        update kanji set proficiency = pProf where character = pChar;
    end;
    
    procedure updateLastTested(pChar kanji.character%type) is
    begin
        update kanji set last_tested = CURRENT_DATE where character = pChar;
    end;
    
    procedure updateJLPT(pChar kanji.character%type, pJLPT kanji.JLPT_classification%type) is
    begin
        update kanji set JLPT_classification = pJLPT where character = pChar;
    end;
    
end IntKanji;

-- ###############

create or replace package IntReading is

    procedure deleteByCharacter(pChar reading.kanji_character%type);
    
    procedure deleteById(pId reading.reading_id%type);
    
    procedure addReading(pChar reading.kanji_character%type, pReading reading.reading%type default NULL);

    procedure updateReading(pId reading.reading_id%type, pReading reading.reading%type);
    
    procedure updateCharacter(pId reading.reading_id%type, pChar reading.kanji_character%type);
    
end IntReading;

create or replace package body IntReading is

    procedure deleteByCharacter(pChar reading.kanji_character%type) is
    begin
        delete from reading where kanji_character = pChar;
    end;
    
    procedure deleteById(pId reading.reading_id%type) is
    begin
        delete from reading where reading_id = pId;
    end;
    
    procedure addReading(pChar reading.kanji_character%type, pReading reading.reading%type) is
    begin
        insert into reading values (reading_reading_id_seq.nextval, pReading, pChar);
    end;

    procedure updateReading(pId reading.reading_id%type, pReading reading.reading%type) is
    begin
        update reading set reading = pReading where pId = reading_id;
    end;
    
    procedure updateCharacter(pId reading.reading_id%type, pChar reading.kanji_character%type) is
    begin
        update reading set kanji_character = pChar where reading_id = pId;
    end;
    
end IntReading;

-- ################

create or replace package IntMeaning is

    procedure deleteByCharacter(pChar meaning.kanji_character%type);
    
    procedure deleteById(pId meaning.meaning_id%type);
    
    procedure addMeaning(pChar meaning.kanji_character%type, pMeaning meaning.meaning%type);

    procedure updateMeaning(pId meaning.meaning_id%type, pMeaning meaning.meaning%type);
    
    procedure updateCharacter(pId meaning.meaning_id%type, pChar meaning.kanji_character%type);
    
end IntMeaning;

create or replace package body IntMeaning is

    procedure deleteByCharacter(pChar meaning.kanji_character%type) is
    begin
        delete from meaning where pChar = kanji_character;
    end;
    
    procedure deleteById(pId meaning.meaning_id%type) is
    begin
        delete from meaning where pId = meaning_id;
    end;
    
    procedure addMeaning(pChar meaning.kanji_character%type, pMeaning meaning.meaning%type) is
    begin
        insert into meaning values (meaning_meaning_id_seq.nextval, pMeaning, pChar);
    end;

    procedure updateMeaning(pId meaning.meaning_id%type, pMeaning meaning.meaning%type) is
    begin
        update meaning set meaning = pMeaning where pId = meaning_id;
    end;
    
    procedure updateCharacter(pId meaning.meaning_id%type, pChar meaning.kanji_character%type) is
    begin
        update meaning set kanji_character = pChar where pId = meaning_id;
    end;
    
end IntMeaning;

-- ################

create or replace package IntUsage is

    procedure deleteByCharacter(pChar usage.kanji_character%type);
    
    procedure deleteById(pId usage.example_id%type);
    
    procedure addExample(pChar usage.kanji_character%type, pExample usage.example%type);

    procedure updateExample(pId usage.example_id%type, pExample usage.example%type);
    
    procedure updateCharacter(pId usage.example_id%type, pChar usage.kanji_character%type);
    
end IntUsage;

create or replace package body IntUsage is

    procedure deleteByCharacter(pChar usage.kanji_character%type) is
    begin
        delete from usage where kanji_character = pChar;
    end;
    
    procedure deleteById(pId usage.example_id%type) is
    begin
        delete from usage where example_id = pId;
    end;
    
    procedure addExample(pChar usage.kanji_character%type, pExample usage.example%type) is
    begin
        insert into usage values (usage_example_id_seq.nextval, pExample, pChar);
    end;

    procedure updateExample(pId usage.example_id%type, pExample usage.example%type) is
    begin
        update usage set example = pExample where pId = example_id;
    end;
    
    procedure updateCharacter(pId usage.example_id%type, pChar usage.kanji_character%type) is
    begin
        update usage set kanji_character = pChar where pId = example_id;
    end;
    
end IntUsage;

-- ##############

create or replace package IntAdditionalNotes is

    procedure deleteByCharacter(pChar additional_notes.kanji_character%type);
    
    procedure deleteById(pId additional_notes.note_id%type);
    
    procedure addNote(pChar additional_notes.kanji_character%type, pType additional_notes.type%type, pNote additional_notes.note%type);

    procedure updateNote(pId additional_notes.note_id%type, pNote additional_notes.note%type);
    
    procedure updateNoteType(pId additional_notes.note_id%type, pType additional_notes.type%type);
    
    procedure updateCharacter(pId additional_notes.note_id%type, pChar additional_notes.kanji_character%type);
    
end IntAdditionalNotes;

create or replace package body IntAdditionalNotes is

    procedure deleteByCharacter(pChar additional_notes.kanji_character%type) is
    begin
        delete from additional_notes where pChar = kanji_character;
    end;
    
    procedure deleteById(pId additional_notes.note_id%type) is
    begin
        delete from additional_notes where pId = note_id;
    end;
    
    procedure addNote(pChar additional_notes.kanji_character%type, pType additional_notes.type%type, pNote additional_notes.note%type) is
    begin
        insert into additional_notes values (additional_notes_note_id_seq.nextval, pChar, pType, pNote, CURRENT_DATE);
    end;

    procedure updateNote(pId additional_notes.note_id%type, pNote additional_notes.note%type) is
    begin
        update additional_notes set note = pNote, date_added = CURRENT_DATE where pId = note_id;
    end;
    
    procedure updateNoteType(pId additional_notes.note_id%type, pType additional_notes.type%type) is
    begin
        update additional_notes set type = pType where pId = note_id;
    end;
    
    procedure updateCharacter(pId additional_notes.note_id%type, pChar additional_notes.kanji_character%type) is
    begin
        update additional_notes set kanji_character = pChar where pId = note_id;
    end;
    
end IntAdditionalNotes;


--drop table additional_notes;
--drop table usage;
--drop table reading;
--drop table meaning;
--drop table kanji cascade constraints;
commit;