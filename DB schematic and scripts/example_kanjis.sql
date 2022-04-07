insert into kanji values('一', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'ichi', '一');
insert into reading values(reading_reading_id_seq.nextval, 'hito', '一');

insert into kanji values('二', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'ni', '二');
insert into reading values(reading_reading_id_seq.nextval, 'futa', '二');

insert into kanji values('三', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'san', '三');
insert into reading values(reading_reading_id_seq.nextval, 'mittsu', '三');

insert into kanji values('四', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'shi', '四');
insert into reading values(reading_reading_id_seq.nextval, 'yottsu', '四');

insert into kanji values('五', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'go', '五');
insert into reading values(reading_reading_id_seq.nextval, 'ittsu', '五');

insert into kanji values('六', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'roku', '六');
insert into reading values(reading_reading_id_seq.nextval, 'muttsu', '六');

insert into kanji values('七', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'nana', '七');
insert into reading values(reading_reading_id_seq.nextval, 'shichi', '七');

insert into kanji values('八', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'hachi', '八');
insert into reading values(reading_reading_id_seq.nextval, 'ystsu', '八');

insert into kanji values('九', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'kyuu', '九');
insert into reading values(reading_reading_id_seq.nextval, 'kokono', '九');

insert into kanji values('十', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'jyuu', '十');
insert into reading values(reading_reading_id_seq.nextval, 'do', '十');

insert into kanji values('百', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'hyaku', '百');
insert into reading values(reading_reading_id_seq.nextval, 'byaku', '百');
insert into reading values(reading_reading_id_seq.nextval, 'pyaku', '百');

insert into kanji values('万', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'man', '万');

insert into kanji values('円', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'en', '円');
insert into reading values(reading_reading_id_seq.nextval, 'maru', '円');

insert into kanji values('目', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'me', '目');
insert into reading values(reading_reading_id_seq.nextval, 'moku', '目');

insert into kanji values('口', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'kuchi', '口');
insert into reading values(reading_reading_id_seq.nextval, 'kou', '口');

insert into kanji values('千', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'sen', '千');
insert into reading values(reading_reading_id_seq.nextval, 'chi', '千');

insert into kanji values('日', current_date, 0, current_date, null);
insert into reading values(reading_reading_id_seq.nextval, 'nichi', '日');
insert into reading values(reading_reading_id_seq.nextval, 'bi', '日');

--select * from kanji;
--delete from kanji;

--select * from reading;
--delete from reading;

insert into meaning values(meaning_meaning_id_seq.nextval, 'one', '一');
insert into meaning values(meaning_meaning_id_seq.nextval, 'two', '二');
insert into meaning values(meaning_meaning_id_seq.nextval, 'three', '三');
insert into meaning values(meaning_meaning_id_seq.nextval, 'four', '四');
insert into meaning values(meaning_meaning_id_seq.nextval, 'five', '五');
insert into meaning values(meaning_meaning_id_seq.nextval, 'six', '六');
insert into meaning values(meaning_meaning_id_seq.nextval, 'seven', '七');
insert into meaning values(meaning_meaning_id_seq.nextval, 'eight', '八');

--select * from meaning;

insert into usage values (usage_example_id_seq.nextval, '日曜日 - sunday', '日');
insert into usage values (usage_example_id_seq.nextval, '日 - day', '日');
insert into usage values (usage_example_id_seq.nextval, '日本 - Japan', '日');
insert into usage values (usage_example_id_seq.nextval, '日時 - date and time', '日');

insert into additional_notes values (additional_notes_note_id_seq.nextval, '日', 'Trivia', '日 in the word meaning Japan means sun and 本 the base.Together it\'s the place where the sun "starts" (sunrise) wow', current_date);