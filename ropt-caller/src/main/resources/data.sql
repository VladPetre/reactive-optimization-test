CREATE
EXTENSION IF NOT EXISTS   "pgcrypto";

truncate table execution_steps;

INSERT INTO execution_steps (id, active, entries_number, finished_at, instance_id,
                             instance_type, method, started_at, status, threads_number)
VALUES (gen_random_uuid(), true, 10, null, 'test-instance-2', 'test', 'GET', null, 0, 16);