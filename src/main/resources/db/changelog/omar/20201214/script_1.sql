alter table video add column video_id varchar(255) not null default 'qwerty';
alter table video add column finished int(1) not null default 0;
alter table video modify duration double(10,2);
alter table video add column progress int(3) not null default 0;
alter table video add column created_at date;