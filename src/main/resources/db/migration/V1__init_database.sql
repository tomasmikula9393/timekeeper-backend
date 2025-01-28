CREATE SEQUENCE id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE "user" (
                      id_user BIGINT DEFAULT nextval('id_user_seq') PRIMARY KEY,
                      name VARCHAR(255),
                      surname VARCHAR(255),
                      email VARCHAR(255) UNIQUE NOT NULL,
                      username VARCHAR(255) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL
);

CREATE SEQUENCE id_item_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE "item" (
                      id_item BIGINT DEFAULT nextval('id_item_seq') PRIMARY KEY,
                      user_id BIGINT,
                      title VARCHAR(50),
                      description VARCHAR(100),
                      validity_from TIMESTAMP,
                      validity_to TIMESTAMP,
                      stav VARCHAR(20),
                      CONSTRAINT fk_item_user FOREIGN KEY (user_id) REFERENCES "user" (id_user) ON DELETE SET NULL
);

CREATE INDEX pk_id_item ON "item" (id_item);
CREATE INDEX pk_id_user ON "user" (id_user);

CREATE TABLE "user_roles" (
                            user_id BIGINT NOT NULL,
                            role VARCHAR(50) NOT NULL,
                            PRIMARY KEY (user_id, role),
                            CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES "user" (id_user) ON DELETE CASCADE
);
