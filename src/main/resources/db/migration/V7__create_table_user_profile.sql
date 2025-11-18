CREATE SEQUENCE id_user_profile_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Table creation
CREATE TABLE user_profile (
                                          id_user_profile BIGINT PRIMARY KEY DEFAULT nextval('id_user_profile_seq'),
                                          weight          INTEGER,
                                          height          INTEGER,
                                          age             INTEGER,
                                          description     VARCHAR(255),
                                          goal            VARCHAR(255),

                                          user_id BIGINT UNIQUE
);

-- Index for ID (although PK already has one, you requested index in JPA)
CREATE INDEX pk_id_user_profile
    ON user_profile (id_user_profile);

-- Index for user FK
CREATE INDEX idx_user_profile_user
    ON user_profile (user_id);

-- Foreign key (update table name if your User entity uses a different table)
ALTER TABLE user_profile
    ADD CONSTRAINT fk_user_profile_user
        FOREIGN KEY (user_id)
            REFERENCES "user"(id_user);

