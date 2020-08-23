update file_types
set name_kz='үй тапсырмасын жүктеу(тесттер)',
    name_ru='для скачивания домашнего задания(тестов)',
    name_en='for download homework(tests)'
where id = 2;

insert into file_types (name_kz, name_ru, name_en)
values ('үй тапсырмасын жіберу(тесттер)', 'для загрузки домашнего задания(тестов)', 'for upload homework(tests)');