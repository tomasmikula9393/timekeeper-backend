CREATE SEQUENCE id_body_measurement_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE body_measurement
(
    id_body_measurement BIGINT DEFAULT nextval('id_body_measurement_seq') PRIMARY KEY,
    weight              DOUBLE PRECISION,
    body_fat            DOUBLE PRECISION,
    week                INTEGER NOT NULL,
    day                 INTEGER NOT NULL,
    training_diary_id   BIGINT  NOT NULL,
    CONSTRAINT fk_training_training_diary FOREIGN KEY (training_diary_id) REFERENCES "training_diary" (id_training_diary) ON DELETE SET NULL

);
