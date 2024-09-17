CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE USER_NT (
    user_id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    surname VARCHAR(150) NOT NULL,
    email VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE NOTE (
    note_id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES USER_NT(user_id)
);

CREATE TABLE TAG (
    tag_id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    color VARCHAR(10),
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES USER_NT(user_id)
);

CREATE TABLE NOTE_TAG (
    note_id INTEGER,
    tag_id INTEGER,
    PRIMARY KEY (note_id, tag_id),
    FOREIGN KEY (note_id) REFERENCES NOTE(note_id),
    FOREIGN KEY (tag_id) REFERENCES TAG(tag_id)
);


CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_update
    BEFORE UPDATE ON USER_NT
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER note_update
    BEFORE UPDATE ON NOTE
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
