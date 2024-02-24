CREATE TABLE report
(
    date_time   TIMESTAMP(6) WITH TIME ZONE,
    id          uuid NOT NULL,
    description VARCHAR(255),
    reporter_id uuid,
    PRIMARY KEY (id)
);
