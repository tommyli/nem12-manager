-- Tommy Li (tommy.li@firefire.co), 2017-05-13

-- Initial DB setup script for test and production, run as postgres user
DROP DATABASE IF EXISTS prod;
DROP DATABASE IF EXISTS test;
CREATE DATABASE prod;
CREATE DATABASE test;

DROP USER IF EXISTS n12m;
DROP USER IF EXISTS n12mread;
DROP USER IF EXISTS n12mtest;
DROP USER IF EXISTS n12mtestread;

CREATE USER n12m WITH ENCRYPTED PASSWORD 'n12m_password';
CREATE USER n12mread WITH ENCRYPTED PASSWORD 'n12mread_password';
CREATE USER n12mtest WITH ENCRYPTED PASSWORD 'n12mtest_password';
CREATE USER n12mtestread WITH ENCRYPTED PASSWORD 'n12mtestread_password';

GRANT CONNECT, CREATE, TEMPORARY ON DATABASE prod TO n12m;
GRANT CONNECT ON DATABASE prod TO n12mread;
GRANT CONNECT, CREATE, TEMPORARY ON DATABASE test TO n12mtest;
GRANT CONNECT ON DATABASE test TO n12mtestread;

COMMIT;
