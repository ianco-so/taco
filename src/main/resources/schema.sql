CREATE TABLE IF NOT EXISTS Taco_Order (
  id            IDENTITY,
  client_name   VARCHAR(50) NOT NULL,
  client_street VARCHAR(50) NOT NULL,
  client_city   VARCHAR(50) NOT NULL,
  client_state  VARCHAR(2)  NOT NULL,
  zip           VARCHAR(10) NOT NULL,
  cc_number     VARCHAR(16) NOT NULL,
  cc_expiration VARCHAR(5)  NOT NULL,
  cc_cvv        VARCHAR(3)  NOT NULL,
  placed_at     TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS Taco (
  id              IDENTITY,
  taco_name       VARCHAR(50) NOT NULL,
  taco_order      BIGINT      NOT NULL,
  taco_order_key  BIGINT      NOT NULL,
  created_at      TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS Ingredient (
  id              VARCHAR(4)  NOT NULL,
  ingredient_name VARCHAR(25) NOT NULL,
  ingredient_type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS Ingredient_Ref (
  ingredient  VARCHAR(4)  NOT NULL,
  taco        BIGINT      NOT NULL,
  taco_key    BIGINT      NOT NULL
);

ALTER TABLE Ingredient      ADD PRIMARY KEY (id);
ALTER TABLE Ingredient_Ref  ADD FOREIGN KEY (ingredient) REFERENCES Ingredient(id);
ALTER TABLE Taco            ADD FOREIGN KEY (taco_order) REFERENCES Taco_Order(id);