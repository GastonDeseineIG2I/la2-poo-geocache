create database la2-geocache;
use la2-geocache;

create table cache
(
	id int auto_increment
		primary key,
	latitude decimal null,
	longitude decimal null,
	description text null,
	nature varchar(10) not null,
	type_cache varchar(25) default 'traditionnelle' not null,
	statut varchar(25) default 'inactive' not null,
	code_secret varchar(50) not null,
	lieu_id int null,
	proprietaire_id int not null
);

create index cache_lieu_id_fk
	on cache (lieu_id);

create index cache_utilisateur_id_fk
	on cache (proprietaire_id);

create table lieu
(
	libelle varchar(100) not null,
	id int auto_increment
		primary key
);

create table utilisateur
(
	id int auto_increment
		primary key,
	pseudo varchar(50) not null,
	description text null,
	avatar varchar(255) default 'default.png' not null,
	constraint utilisateur_pseudo_uindex
		unique (pseudo)
);

create table visite
(
	id int auto_increment
		primary key,
	date_visite datetime null,
	utilisateur_id int not null,
	cache_id int not null,
	commentaire text null,
	statut varchar(25) default 'En cours' not null
);

create index visite_cache_id_fk
	on visite (cache_id);

create index visite_utilisateur_id_fk
	on visite (utilisateur_id);