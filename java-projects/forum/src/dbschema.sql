create table t_user(id integer primary key auto_increment,login_name varchar(50) not null,nick_name varchar(50) not null,password varchar(50) not null,email varchar(50) not null,homepage varchar(100),qq varchar(15),mark integer,rank integer,unique(login_name,nick_name))
create table t_board(id integer primary key auto_increment,name varchar(50) not null,description varchar(200),unique(name))
create table t_topic(id integer primary key auto_increment,board_id integer not null,user_id integer not null,title varchar(200) not null,content text not null,post_time timestamp not null,foreign key(board_id) references t_board(id),foreign key(user_id) references t_user(id))
create table t_post(id integer primary key auto_increment,topic_id integer,user_id integer,content text not null,post_time timestamp not null,foreign key(topic_id) references t_topic(id),foreign key(user_id) references t_user(id))