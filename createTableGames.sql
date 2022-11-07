CREATE TABLE table_games (
    id serial NOT NULL,
    gamename varchar(255) NOT NULL,
    price integer NOT NULL,
    playeramount integer NOT NULL,
	genre varchar(255) NOT NULL,
    PRIMARY KEY (ID)
); 