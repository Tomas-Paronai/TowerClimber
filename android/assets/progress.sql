CREATE DATABASE progress;
use progress;

CREATE TABLE levels_passed(
	name varchar(10),
	position int(11)
);

CREATE TABLE player_status(
	score int(11),
	lives int(5)
);