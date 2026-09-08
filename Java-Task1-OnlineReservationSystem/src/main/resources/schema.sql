PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS trains (
    train_number INTEGER PRIMARY KEY,
    train_name TEXT NOT NULL,
    source TEXT NOT NULL,
    destination TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS reservations (
    pnr TEXT PRIMARY KEY,
    passenger_name TEXT NOT NULL,
    train_number INTEGER NOT NULL,
    class_type TEXT NOT NULL,
    journey_date TEXT NOT NULL,
    source_station TEXT NOT NULL,
    destination_station TEXT NOT NULL,
    created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (train_number) REFERENCES trains(train_number)
);

CREATE INDEX IF NOT EXISTS idx_reservations_train_number ON reservations(train_number);
CREATE INDEX IF NOT EXISTS idx_reservations_journey_date ON reservations(journey_date);
