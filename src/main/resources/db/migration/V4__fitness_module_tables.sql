CREATE SEQUENCE id_training_diary_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE training_diary
(
    id_training_diary BIGINT DEFAULT nextval('id_training_diary_seq') PRIMARY KEY,
    name              VARCHAR(50) NOT NULL,
    week              INTEGER     NOT NULL, -- Týden v roce (1–53)
    year              INTEGER     NOT NULL, -- Rok
    user_id           BIGINT      NOT NULL, -- Vlastník deníku
    CONSTRAINT fk_training_diary_user FOREIGN KEY (user_id) REFERENCES "user" (id_user) ON DELETE SET NULL
);

CREATE INDEX pk_id_training_diary ON "training_diary" (id_training_diary);

CREATE SEQUENCE id_training_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE training
(
    id_training       BIGINT DEFAULT nextval('id_training_seq') PRIMARY KEY,
    type              VARCHAR(10),
    day               VARCHAR(10),
    note              VARCHAR(100),
    training_diary_id BIGINT NOT NULL,
    CONSTRAINT fk_training_training_diary FOREIGN KEY (training_diary_id) REFERENCES "training_diary" (id_training_diary) ON DELETE SET NULL

);

CREATE INDEX pk_id_training ON "training" (id_training);

--
CREATE SEQUENCE id_exercise_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE exercise
(
    id_exercise BIGINT DEFAULT nextval('id_exercise_seq') PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    set         INTEGER,
    rep         INTEGER,
    weight      DOUBLE PRECISION,
    duration    INTEGER,
    distance    DOUBLE PRECISION,
    speed       DOUBLE PRECISION,
    training_id BIGINT      NOT NULL,
    CONSTRAINT fk_exercise_training FOREIGN KEY (training_id) REFERENCES "training" (id_training) ON DELETE SET NULL

);

CREATE INDEX pk_id_exercise ON "exercise" (id_exercise);