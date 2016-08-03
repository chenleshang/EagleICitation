-- Create tables


-- Creates tables eaglei_resources
CREATE TABLE IF NOT EXISTS pre_table_name_eaglei_resources (
id int not null auto_increment primary key,
subject text,
predicate text, 
object text,
type varchar(10));
-- Creates table temp_eaglei_resources
CREATE TABLE IF NOT EXISTS pre_table_name_temp_eaglei_resources (
subject text,
predicate text, 
object text,
version_date varchar(8),
type varchar(10));
-- Creates table eaglei_validtime
CREATE TABLE IF NOT EXISTS pre_table_name_eaglei_validtime(
id int not null,
start_version varchar(8) not null,
end_version varchar(8)
);
-- Creates table eaglei_global_vars
CREATE TABLE IF NOT EXISTS pre_table_name_eaglei_global_vars(
id int not null primary key,
last_version varchar(8),
today varchar(8)
);


-- Creates table eaglei_changes_log_tripple
CREATE TABLE IF NOT EXISTS pre_table_name_eaglei_changes_log_triple(
date_log_generated varchar(8),
id int not null,
type varchar(15),
operation varchar(10)
);
-- Creates table eaglei_changes_log_resource
CREATE TABLE IF NOT EXISTS pre_table_name_eaglei_changes_log_resource(
date_log_generated varchar(8),
eaglei_id_subject text,
operation varchar(10)
);


