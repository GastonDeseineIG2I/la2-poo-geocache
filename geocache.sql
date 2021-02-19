create schema `la2-geocache` collate latin1_swedish_ci;
use `la2-geocache`;
create table cache
(
	id varchar(100) not null
		primary key,
	latitude decimal(12,10) null,
	longitude decimal(13,10) null,
	description text null,
	nature varchar(10) not null,
	type_cache varchar(25) default 'traditionnelle' not null,
	statut varchar(25) default 'inactive' not null,
	code_secret varchar(50) not null,
	lieu_id varchar(100) null,
	proprietaire_id varchar(100) not null
);

create index cache_lieu_id_fk
	on cache (lieu_id);

create index cache_utilisateur_id_fk
	on cache (proprietaire_id);

create table lieu
(
	libelle varchar(100) not null,
	id varchar(100) not null
		primary key
);

create table utilisateur
(
	id varchar(100) not null
		primary key,
	pseudo varchar(50) not null,
	description text null,
	avatar varchar(255) default 'default.png' not null,
	constraint utilisateur_pseudo_uindex
		unique (pseudo)
);

create table visite
(
	id varchar(100) not null
		primary key,
	date_visite datetime null,
	utilisateur_id varchar(100) not null,
	cache_id varchar(100) not null,
	commentaire text null,
	statut varchar(25) default 'En cours' not null
);

create index visite_cache_id_fk
	on visite (cache_id);

create index visite_utilisateur_id_fk
	on visite (utilisateur_id);



-- INSERTS

INSERT INTO `la2-geocache`.lieu (libelle, id) VALUES ('LENS', '512f372a-0aec-4d38-8b28-2c0dab591e25');
INSERT INTO `la2-geocache`.lieu (libelle, id) VALUES ('PARIS', 'd2a41c70-0647-4342-9f2b-906518d962f6');
INSERT INTO `la2-geocache`.lieu (libelle, id) VALUES ('TOUR', '34d86790-ddf3-4912-979b-20689d97aaed');
INSERT INTO `la2-geocache`.lieu (libelle, id) VALUES ('METZ', '86102dc4-3b95-42a5-850a-b47f9112b946');

INSERT INTO `la2-geocache`.utilisateur (id, pseudo, description, avatar) VALUES ('f4f16eb4-d9b6-42c0-b232-fb2beb8b7bcd', 'Aslavia', 'Aime planquer des caches', 'avatar.png');
INSERT INTO `la2-geocache`.utilisateur (id, pseudo, description, avatar) VALUES ('bcac8890-72b9-11eb-9439-0242ac130002', 'Replican', 'j''adore j''adore', 'replican.png');

INSERT INTO `la2-geocache`.cache (id, latitude, longitude, description, nature, type_cache, statut, code_secret, lieu_id, proprietaire_id) VALUES ('b2f7aa4d-9c77-4341-b9e8-206dd71651c7', 53.0000000000, 180.0000000000, 'Mon code secret est le nom des serveur cloud de Amazon', 'VIRTUELLE', 'TRADITIONNELLE', 'INACTIVE', 'AWS', 'd2a41c70-0647-4342-9f2b-906518d962f6', 'f4f16eb4-d9b6-42c0-b232-fb2beb8b7bcd');

INSERT INTO `la2-geocache`.visite (id, date_visite, utilisateur_id, cache_id, commentaire, statut) VALUES ('0b11078e-d35c-4eb7-8ed5-9082fb46068d', '2021-02-19 12:57:03', 'f4f16eb4-d9b6-42c0-b232-fb2beb8b7bcd', 'b2f7aa4d-9c77-4341-b9e8-206dd71651c7', 'Je suis en recherche active', 'EN COURS');