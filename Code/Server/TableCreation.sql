CREATE TABLE problem_instance
(
    id              SERIAL PRIMARY KEY,
    number_of_nodes INTEGER,
    name            VARCHAR(255),
    points          JSON
);

CREATE TABLE solutions
(
    id          SERIAL PRIMARY KEY,
    instance_id INTEGER,
    user_name   VARCHAR(255),
    algorithm_name VARCHAR(255),
    value       BIGINT
);

ALTER TABLE solutions
    ADD CONSTRAINT fk_solutions_instance
        FOREIGN KEY (instance_id)
            REFERENCES problem_instance (id)
            ON DELETE CASCADE;

