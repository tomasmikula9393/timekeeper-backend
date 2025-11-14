CREATE SEQUENCE id_transaction_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE transactions (
                              id_transaction BIGINT DEFAULT nextval('id_transaction_seq') PRIMARY KEY,
                              type VARCHAR(30) NOT NULL,           -- "income", "expense", "monthly_fee"
                              category VARCHAR(50) NOT NULL,       -- např. "Nájem", "Plat", "Potraviny"
                              description VARCHAR(100),
                              amount NUMERIC(10, 2) NOT NULL,
                              transaction_date DATE,
                              user_id BIGINT NULL,

                              CONSTRAINT fk_transactions_user
                                  FOREIGN KEY (user_id) REFERENCES "user" (id_user) ON DELETE SET NULL
);

CREATE INDEX pk_id_transaction ON transactions (id_transaction);