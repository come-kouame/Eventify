CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    content VARCHAR(255) NOT NULL
);

INSERT INTO messages (content)
VALUES ('Hello World');

-- =====================================
-- Création des tables
-- =====================================

-- Table utilisateur
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

-- Table organisateur (lié à utilisateur)
CREATE TABLE IF NOT EXISTS organisateur (
    id_orga SERIAL PRIMARY KEY,
    utilisateur_id INT NOT NULL UNIQUE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE
);

-- Table administrateur (lié à organisateur)
CREATE TABLE IF NOT EXISTS administrateur (
    id_admin SERIAL PRIMARY KEY,
    organisateur_id INT NOT NULL UNIQUE,
    -- utilisateur_id INT NOT NULL,
    FOREIGN KEY (organisateur_id) REFERENCES organisateur(id_orga) ON DELETE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE
);

-- Table evenement
CREATE TABLE evenement (
    id_evenement SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    description TEXT,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    lieu VARCHAR(150) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    poster VARCHAR(255),
    prix DOUBLE PRECISION NOT NULL,
    nb_places INT NOT NULL,
    nb_places_vendues INT DEFAULT 0,
    valide BOOLEAN DEFAULT FALSE,
    orga_id INT NOT NULL,
    admin_id INT,
    FOREIGN KEY (orga_id) REFERENCES organisateur(id_orga) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES administrateur(id_admin)
);

-- Table participation des utilisateurs
CREATE TABLE IF NOT EXISTS participation (
    id_participation SERIAL PRIMARY KEY,
    utilisateur_id INT NOT NULL,
    evenement_id INT NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (evenement_id) REFERENCES evenement(id_evenement) ON DELETE CASCADE,
    CONSTRAINT uniq_participation UNIQUE(utilisateur_id, evenement_id)
);

-- Table bénévolat des organisateurs
CREATE TABLE IF NOT EXISTS benevolat (
    id_benevolat SERIAL PRIMARY KEY,
    orga_id INT NOT NULL,
    evenement_id INT NOT NULL,
    FOREIGN KEY (orga_id) REFERENCES organisateur(id_orga) ON DELETE CASCADE,
    FOREIGN KEY (evenement_id) REFERENCES evenement(id_evenement) ON DELETE CASCADE,
    CONSTRAINT uniq_benevolat UNIQUE(orga_id, evenement_id)
);

-- =====================================
-- Insertion des utilisateurs
-- =====================================
INSERT INTO utilisateur (nom, prenom, date_naissance, email, ville, rue, code_postal, login, mdp) VALUES
('Dupont', 'Alice', '2001-01-01', 'alice.dupont@example.com', 'Paris', '12 rue de Rivoli', '75001', 'alice25', 'mdp123'),
('Martin', 'Bob', '1996-06-15', 'bob.martin@example.com', 'Lyon', '34 avenue de la République', '69001', 'bob30', 'mdp456'),
('Durand', 'Claire', '1991-03-22', 'claire.durand@example.com', 'Marseille', '56 boulevard Longchamp', '13001', 'claire35', 'mdp789')
ON CONFLICT (login) DO NOTHING;

-- =====================================
-- Création des organisateurs
-- =====================================
-- Alice et Claire seront organisateurs
INSERT INTO organisateur (utilisateur_id) VALUES
((SELECT id FROM utilisateur WHERE login='alice25')),
((SELECT id FROM utilisateur WHERE login='claire35'))
ON CONFLICT (utilisateur_id) DO NOTHING;

-- =====================================
-- Création de l'administrateur
-- =====================================
-- Claire est admin
INSERT INTO administrateur (organisateur_id, utilisateur_id) VALUES
(
 (SELECT id_orga FROM organisateur 
  WHERE utilisateur_id = (SELECT id FROM utilisateur WHERE login='claire35')),
 (SELECT id FROM utilisateur WHERE login='claire35')
)
ON CONFLICT (organisateur_id) DO NOTHING;

-- =====================================
-- Insertion d'un événement
-- =====================================
-- INSERT INTO evenement (titre, description, date_event, prix, orga_id, admin_id) VALUES
-- ('Soirée Caritative', 'Collecte de fonds pour une asso locale', '2026-04-01', 20.00,
--  (SELECT id_orga FROM organisateur WHERE utilisateur_id = (SELECT id FROM utilisateur WHERE login='alice25')),
--  (SELECT id_admin FROM administrateur WHERE organisateur_id = (SELECT id_orga FROM organisateur WHERE utilisateur_id = (SELECT id FROM utilisateur WHERE login='claire35'))))
-- ON CONFLICT (id_evenement) DO NOTHING;
INSERT INTO evenement (
    titre, description, date_debut, date_fin, lieu, adresse, poster,
    prix, nb_places, nb_places_vendues, valide, orga_id, admin_id
) VALUES (
    'Soirée Caritative',
    'Collecte de fonds pour une asso locale',
    '2026-04-01',
    '2026-04-01',
    'Lyon',
    '34 avenue de la République',
    NULL,
    20.00,
    100,
    0,
    TRUE,
    (SELECT id_orga FROM organisateur WHERE utilisateur_id = (SELECT id FROM utilisateur WHERE login='alice25')),
    (SELECT id_admin FROM administrateur WHERE organisateur_id = (SELECT id_orga FROM organisateur WHERE utilisateur_id = (SELECT id FROM utilisateur WHERE login='claire35')))
)
ON CONFLICT (id_evenement) DO NOTHING;
-- =====================================
-- Participation et bénévolat
-- =====================================
-- Bob participe à l'événement
INSERT INTO participation (utilisateur_id, evenement_id) VALUES
((SELECT id FROM utilisateur WHERE login='bob30'),
 (SELECT id_evenement FROM evenement WHERE titre='Soirée Caritative'))
ON CONFLICT (utilisateur_id, evenement_id) DO NOTHING;

-- Exemple de bénévolat : Alice bénévole pour l'événement
INSERT INTO benevolat (orga_id, evenement_id) VALUES
((SELECT id_orga FROM organisateur WHERE utilisateur_id = (SELECT id FROM utilisateur WHERE login='alice25')),
 (SELECT id_evenement FROM evenement WHERE titre='Soirée Caritative'))
ON CONFLICT (orga_id, evenement_id) DO NOTHING;

-- Alice (l'organisatrice) se porte bénévole
INSERT INTO benevolat (orga_id, evenement_id) VALUES
(1, 1)
ON CONFLICT (orga_id, evenement_id) DO NOTHING;