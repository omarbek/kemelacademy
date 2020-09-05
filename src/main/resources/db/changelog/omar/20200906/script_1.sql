update system_parameters
set value = '/home/files/'
where code = 'UPLOADED_FOLDER_REAL';

update feature_flag
set switch_on = 1
where code = 'GET_UPLOADED_FOLDER_REAL';