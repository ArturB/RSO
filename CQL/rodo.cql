------------------ BAZA RODO ------------------

DROP KEYSPACE IF EXISTS rodo;
CREATE KEYSPACE rodo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 3 };
USE rodo;

CREATE TABLE user(
    user_id uuid PRIMARY KEY,
    name text,
    surname text,
    document_type text,
    document_no text,
    email text,
    birthdate date,
    PESEL int
);

