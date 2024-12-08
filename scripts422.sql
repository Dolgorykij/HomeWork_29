CREATE TABLE Automobile (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(50),
    model VARCHAR(50),
    price DECIMAL(10, 2)
);
CREATE TABLE Person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    has_license BOOLEAN,
    Automobile_id INT,
    CONSTRAINT a_Automobile FOREIGN KEY (Automobile_id) REFERENCES Automobile (id)
);