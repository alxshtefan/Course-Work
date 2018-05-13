delete from tests_questions;
delete from questions_answers;

delete from questions;
alter table questions auto_increment=1;
insert into questions value (default, 'stub');

delete from tests;
alter table tests auto_increment=1;
insert into tests value (default, 'stub', 1, 1, 1, 'stub');

delete from answers;
alter table answers auto_increment=1;
insert into answers value (default, 'stub');