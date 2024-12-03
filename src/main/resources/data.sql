-- H2 database initialization
insert into user_details (id, name, birth_date)
values (10001, 'Tristan', current_date());
insert into user_details (id, name, birth_date)
values (10002, 'Gareth', dateadd('year', -3, current_date()));
insert into user_details (id, name, birth_date)
values (10003, 'Owen', '1990-01-02');
