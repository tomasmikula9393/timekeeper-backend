CREATE SEQUENCE id_transaction_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE transaction (
                              id_transaction BIGINT DEFAULT nextval('id_transaction_seq') PRIMARY KEY,
                              type VARCHAR(30) NOT NULL, -- "income", "expense", "monthly_fee"
                              category VARCHAR(50) NOT NULL, -- např. "Nájem", "Plat", "Potraviny"
                              description VARCHAR(50),
                              amount NUMERIC(10, 2) NOT NULL,
                              transaction_date DATE,
                              user_id BIGINT,
                              CONSTRAINT fk_item_user FOREIGN KEY (user_id) REFERENCES "user" (id_user) ON DELETE SET NULL

);

CREATE INDEX pk_id_transaction ON "transaction" (id_transaction);
