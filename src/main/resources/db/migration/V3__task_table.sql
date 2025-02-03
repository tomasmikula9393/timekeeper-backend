CREATE SEQUENCE id_task_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE task (
                             id_task BIGINT DEFAULT nextval('id_task_seq') PRIMARY KEY,
                             name VARCHAR(50) NOT NULL,
                             category VARCHAR(50) NOT NULL,
                             description VARCHAR(50),
                             deadline DATE,
                             user_id BIGINT NOT NULL,
                             CONSTRAINT fk_item_user FOREIGN KEY (user_id) REFERENCES "user" (id_user) ON DELETE SET NULL

);

CREATE INDEX pk_id_task ON "task" (id_task);
