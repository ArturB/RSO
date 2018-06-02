------------------ BAZA RSO ------------------
-- tables are created in alphabetical order


DROP KEYSPACE IF EXISTS rso;
CREATE KEYSPACE rso WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 3 };
USE rso;

CREATE TABLE candidate (
    candidate_id uuid PRIMARY KEY,
    name text,
    surname text,
    age int,
    occupation text,
    commune uuid,
    turns list<uuid>,
    party uuid
);
CREATE INDEX candidate_commune ON candidate ( commune );
CREATE INDEX candidate_party ON candidate ( party );

CREATE TABLE commune (
    commune_id uuid PRIMARY KEY,
    commune_name text,
    position_name text
);
CREATE INDEX commune_commune_name ON commune ( commune_name );

CREATE TABLE electoral_period (
    period_id uuid PRIMARY KEY,
    period_name text,
    date_from date,
    date_to date
);
CREATE INDEX electoral_period_period_name ON electoral_period ( period_name );

CREATE TABLE party (
    party_id uuid PRIMARY KEY,
    party_abbreviation text,
    party_name text
);
CREATE INDEX party_party_abbreviation ON party ( party_abbreviation );
CREATE INDEX party_party_name ON party ( party_name );

CREATE TABLE turn (
    turn_id uuid PRIMARY KEY,
    no_turn int,
    date_from date,
    date_to date,
    commune uuid
);

CREATE TABLE user (
    user_id uuid PRIMARY KEY,
    username text,
    password_hash text,
    password_salt text,
    group text,
    commune uuid,
    ward uuid
);
CREATE INDEX user_username ON user ( username );
CREATE INDEX user_commune ON user (commune );
CREATE INDEX user_ward ON user ( ward );

CREATE TABLE ward (
    ward_id uuid PRIMARY KEY,
    ward_name text,
    commune uuid
);
CREATE INDEX ward_ward_name ON ward ( ward_name );
CREATE INDEX ward_commune ON ward ( commune );

CREATE TABLE votes_from_ward (
    votes_id uuid PRIMARY KEY,
    no_votes int,
    date date,
    type text,
    user uuid,
    voting_data uuid,
    candidate uuid
);
CREATE INDEX votes_from_ward_type ON votes_from_ward ( type );
CREATE INDEX votes_from_ward_user ON votes_from_ward ( user );
CREATE INDEX votes_from_ward_candidate ON votes_from_ward ( candidate );

CREATE TABLE voting_data (
    voting_id uuid PRIMARY KEY,
    no_can_vote int,
    no_cards_used int,
    is_voting_finished boolean,
    ward uuid,
    turn uuid
);
CREATE INDEX voting_data_ward ON voting_data ( ward );

CREATE TABLE voting_report (
    report_id uuid PRIMARY KEY,
    commune_name text,
    ward_name text,
    no_turn int,
    no_can_vote int,
    no_cards_used int,
    vote_type text,
    no_of_votes int,
    candidate_id uuid,
    candidate_name text,
    candidate_surname text,
    date_generated date,
    is_voting_finished boolean
);

CREATE TABLE voting_results (
    result_id uuid PRIMARY KEY,
    commune_name text,
    no_turn int,
    no_can_vote int,
    no_cards_used int,
    vote_type text,
    no_of_votes int,
    candidate_id uuid,
    candidate_name text,
    candidate_surname text,
    date_generated date,
    is_voting_finished boolean
);
