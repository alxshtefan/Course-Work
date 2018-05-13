delete from tests_questions;
delete from questions_answers;

delete from questions;
alter table questions auto_increment=1;

delete from tests;
alter table tests auto_increment=1;

delete from answers;
alter table answers auto_increment=1;
