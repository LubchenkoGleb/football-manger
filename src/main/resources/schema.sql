drop table if exists team;
drop table if exists player;

create table player(id bigint, first_name varchar(255) not null, last_name varchar(255) not null, position varchar(255) not null, birthday date not null, team_id bigint,constraint player_PK primary key (id));

create table team(id bigint, name varchar(255) not null, captain_id bigint not null, constraint team_PK primary key (id), constraint team_player_captain_id_FK foreign key (captain_id) references player (id), constraint team_captain_id_UQ unique (captain_id));

alter table player add constraint player_team_team_id_FK foreign key (team_id) references team (id) on delete cascade;