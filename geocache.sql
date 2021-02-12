create database la2-geocache;
use la2-geocache;
-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : jeu. 11 fév. 2021 à 10:17
-- Version du serveur :  5.7.30
-- Version de PHP : 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données : `la2-geocache`
--

-- --------------------------------------------------------

--
-- Structure de la table `cache`
--

CREATE TABLE `cache` (
  `id` int(11) NOT NULL,
  `latitude` decimal(10,0) DEFAULT NULL,
  `longitude` decimal(10,0) DEFAULT NULL,
  `description` text,
  `nature` varchar(10) NOT NULL,
  `type_cache` varchar(25) NOT NULL DEFAULT 'TRADITIONNELLE',
  `statut` varchar(25) NOT NULL DEFAULT 'INACTIVE',
  `code_secret` varchar(50) NOT NULL,
  `lieu_id` int(11) DEFAULT NULL,
  `proprietaire_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `cache`
--

INSERT INTO `cache` (`id`, `latitude`, `longitude`, `description`, `nature`, `type_cache`, `statut`, `code_secret`, `lieu_id`, `proprietaire_id`) VALUES
(1, '45', '1', 'Je suis sous un arbre majestueux ', 'PHYSIQUE', 'TRADITIONNELLE', 'INACTIVE', 'AZERTY', 2, 4),
(2, '53', '180', 'Mon code secret est le nom des serveur cloud de Amazon ', 'VIRTUELLE', 'TRADITIONNELLE', 'INACTIVE', 'AWS', 4, 1),
(3, '40', '20', 'VENERE', '12345', 'TRADITIONNELLE', 'ACTIVE', '1234', 1, 3),
(4, NULL, NULL, NULL, 'PHYSIQUE', 'JEU DE PISTE', 'ACTIVE', 'EGDGE', 1, 2),
(5, '76', NULL, 'Je suis dans le centre ville prêt de d\'une boulangerie', 'PHYSIQUE', 'TRADITIONNELLE', 'INACTIVE', 'boulangerie', 5, 3);

-- --------------------------------------------------------

--
-- Structure de la table `lieu`
--

CREATE TABLE `lieu` (
  `libelle` varchar(100) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `lieu`
--

INSERT INTO `lieu` (`libelle`, `id`) VALUES
('LENS', 1),
('LE MEIX ST EPOING', 2),
('PARIS', 3),
('TOUR', 4),
('METZ', 5);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `pseudo` varchar(50) NOT NULL,
  `description` text,
  `avatar` varchar(255) NOT NULL DEFAULT 'default.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `pseudo`, `description`, `avatar`) VALUES
(1, 'ReplicanReturn', 'le retour de Replican', 'PhotoReplican.png'),
(2, 'aslavia', 'Aime planquer des caches', 'default.png'),
(3, 'Replican', 'j\'adore j\'addore', 'replican.png'),
(4, 'WorldChampion', 'Je suis champion du monde pour trouver des caches', 'default.png'),
(5, 'NoobyDu51', 'Je ne suis pas vraiment doué', 'default.png');

-- --------------------------------------------------------

--
-- Structure de la table `visite`
--

CREATE TABLE `visite` (
  `id` int(11) NOT NULL,
  `date_visite` datetime DEFAULT NULL,
  `utilisateur_id` int(11) NOT NULL,
  `cache_id` int(11) NOT NULL,
  `commentaire` text,
  `statut` varchar(25) NOT NULL DEFAULT 'EN COURS'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `visite`
--

INSERT INTO `visite` (`id`, `date_visite`, `utilisateur_id`, `cache_id`, `commentaire`, `statut`) VALUES
(2, '2021-02-17 14:37:26', 3, 1, 'Je suis en recherche active', 'EN COURS'),
(3, '2001-01-01 12:02:03', 5, 5, 'Super j\'ai passé un bon moment ', 'TERMINEE'),
(4, '2021-02-17 15:58:32', 2, 3, 'Pas facile à trouver la cache est-elle toujours active ? ', 'EN COURS'),
(5, '2021-02-11 10:22:51', 3, 3, 'je suis passe par ici et je repasserais par là', 'TERMINEE');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `cache`
--
ALTER TABLE `cache`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cache_lieu_id_fk` (`lieu_id`),
  ADD KEY `cache_utilisateur_id_fk` (`proprietaire_id`);

--
-- Index pour la table `lieu`
--
ALTER TABLE `lieu`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `utilisateur_pseudo_uindex` (`pseudo`);

--
-- Index pour la table `visite`
--
ALTER TABLE `visite`
  ADD PRIMARY KEY (`id`),
  ADD KEY `visite_cache_id_fk` (`cache_id`),
  ADD KEY `visite_utilisateur_id_fk` (`utilisateur_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `cache`
--
ALTER TABLE `cache`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `lieu`
--
ALTER TABLE `lieu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `visite`
--
ALTER TABLE `visite`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
