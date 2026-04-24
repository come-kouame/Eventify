-- =====================================
-- Messages (test)
-- =====================================

CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    content VARCHAR(255) NOT NULL
);

INSERT INTO messages (content)
VALUES ('Hello World');

-- =====================================
-- Utilisateur
-- =====================================

CREATE TABLE IF NOT EXISTS utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    date_naissance DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    ville VARCHAR(100) NOT NULL,
    rue VARCHAR(150) NOT NULL,
    code_postal VARCHAR(10) NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    mdp VARCHAR(100) NOT NULL
);

-- =====================================
-- Organisateur
-- =====================================

CREATE TABLE IF NOT EXISTS organisateur (
    id_orga SERIAL PRIMARY KEY,
    utilisateur_id INT NOT NULL UNIQUE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE
);

-- =====================================
-- Administrateur
-- =====================================

CREATE TABLE IF NOT EXISTS administrateur (
    id_admin SERIAL PRIMARY KEY,
    organisateur_id INT NOT NULL UNIQUE,
    utilisateur_id INT NOT NULL,
    FOREIGN KEY (organisateur_id) REFERENCES organisateur(id_orga) ON DELETE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE
);

-- =====================================
-- Evenement
-- =====================================

CREATE TABLE IF NOT EXISTS evenement (
    id_evenement SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    description TEXT,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    lieu VARCHAR(150) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    poster VARCHAR(255),
    prix DECIMAL(6,2) NOT NULL,
    nb_places INT NOT NULL,
    nb_places_vendues INT DEFAULT 0,
    valide BOOLEAN DEFAULT FALSE,
    orga_id INT NOT NULL,
    admin_id INT,
    FOREIGN KEY (orga_id) REFERENCES organisateur(id_orga) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES administrateur(id_admin)
);

-- =====================================
-- Participation
-- =====================================

CREATE TABLE IF NOT EXISTS participation (
    id_participation SERIAL PRIMARY KEY,
    utilisateur_id INT NOT NULL,
    evenement_id INT NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (evenement_id) REFERENCES evenement(id_evenement) ON DELETE CASCADE,
    CONSTRAINT uniq_participation UNIQUE(utilisateur_id, evenement_id)
);

-- =====================================
-- Benevolat
-- =====================================

CREATE TABLE IF NOT EXISTS benevolat (
    id_benevolat SERIAL PRIMARY KEY,
    orga_id INT NOT NULL,
    evenement_id INT NOT NULL,
    FOREIGN KEY (orga_id) REFERENCES organisateur(id_orga) ON DELETE CASCADE,
    FOREIGN KEY (evenement_id) REFERENCES evenement(id_evenement) ON DELETE CASCADE,
    CONSTRAINT uniq_benevolat UNIQUE(orga_id, evenement_id)
);