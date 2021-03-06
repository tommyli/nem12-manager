-- Tommy Li (tommy.li@firefire.co), 2017-02-09

INSERT INTO login (username, email, name, picture_url, locale, family_name, given_name)
VALUES ('tommy.li@gmail.com', 'tommy.yli@gmail.com', 'Tom Li', NULL, NULL, 'Li', 'Tom');
INSERT INTO login_nmi (login, nmi, label)
VALUES (currval('login_id_seq'), '6123456789', 'Work');
INSERT INTO nmi_meter_register (login_nmi, meter_serial, register_id, nmi_suffix, nmi_config, mdm_data_stream_id, uom, interval_length, next_scheduled_read_date)
VALUES (currval('login_nmi_id_seq'), 'ABCD-12345', 'E1', 'E1', 'E1E1', NULL, 'KWH', 30, DATE '2017-02-21');
INSERT INTO interval_day (nmi_meter_register, interval_date, quality, method, reason_code, reason_description, update_date_time, msats_load_date_time)
VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-01', 'A', NULL, NULL, NULL, TIMESTAMP '2016-12-30 20:28:28', TIMESTAMP '2016-12-31 20:28:28');

INSERT INTO login (username, email, name, picture_url, locale, family_name, given_name)
VALUES ('tommy.li@firefire.co', 'tommy.li@firefire.co', 'Tommy Li', NULL, NULL, 'Li', 'Tommy');
INSERT INTO login_nmi (login, nmi, label)
VALUES (currval('login_id_seq'), '6123456789', 'Home');
INSERT INTO nmi_meter_register (login_nmi, meter_serial, register_id, nmi_suffix, nmi_config, mdm_data_stream_id, uom, interval_length, next_scheduled_read_date)
VALUES (currval('login_id_seq'), 'ABCD-12345', 'E1', 'E1', 'E1E1', NULL, 'KWH', 30, DATE '2017-02-23');
INSERT INTO interval_day (nmi_meter_register, interval_date, quality, method, reason_code, reason_description, update_date_time, msats_load_date_time)
VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-01', 'A', NULL, NULL, NULL, TIMESTAMP '2016-12-30 20:28:28', TIMESTAMP '2016-12-31 20:28:28');
INSERT INTO interval_day (nmi_meter_register, interval_date, quality, method, reason_code, reason_description, update_date_time, msats_load_date_time)
VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-02', 'V', '50', '501', NULL, TIMESTAMP '2016-12-31 20:28:28', TIMESTAMP '2017-01-01 20:28:28');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 1, 0.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 2, -1.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 3, 2.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 4, 3.6, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 5, 4.8, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 6, 6.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 7, 7.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 8, 8.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 9, 9.6, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 10, 10.8, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 11, 12.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 12, 13.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 13, 14.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 14, 15.6, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 15, 16.8, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 16, 18.0, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 17, 19.2, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 18, 20.4, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 19, 21.6, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 20, 22.8, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 21, 24.0, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 22, 25.2, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 23, 26.4, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 24, 27.6, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 25, 28.8, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 26, 30.0, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 27, 31.2, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 28, 32.4, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 29, 33.6, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 30, 34.8, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 31, 36.0, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 32, 37.2, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 33, 38.4, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 34, 39.6, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 35, 40.8, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 36, 42.0, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 37, 43.2, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 38, 44.4, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 39, 45.6, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 40, 46.8, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 41, 48.0, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 42, 49.2, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 43, 50.4, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 44, 51.6, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 45, 52.8, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 46, 54.0, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 47, 55.2, 'E', '51', NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 48, -56.4, 'A', NULL, NULL, NULL);

INSERT INTO interval_day (nmi_meter_register, interval_date, quality, method, reason_code, reason_description, update_date_time, msats_load_date_time)
VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-03', 'A', '70', NULL, NULL, TIMESTAMP '2017-01-01 20:28:28', TIMESTAMP '2017-01-02 20:28:28');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 1, 0.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 2, -1.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 3, -2.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 4, 3.6, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 5, 4.8, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 6, 6.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 7, 7.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 8, 8.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 9, 9.6, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 10, 10.8, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 11, 12.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 12, 13.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 13, 14.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 14, 15.6, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 15, 16.8, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 16, 18.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 17, 19.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 18, 20.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 19, 21.6, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 20, 22.8, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 21, 24.0, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 22, 25.2, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 23, 26.4, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 24, 27.6, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 25, 28.8, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 26, 30.0, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 27, 31.2, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 28, 32.4, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 29, 33.6, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 30, 34.8, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 31, 36.0, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 32, 37.2, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 33, 38.4, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 34, 39.6, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 35, 40.8, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 36, 42.0, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 37, 43.2, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 38, 44.4, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 39, 45.6, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 40, 46.8, 'E', '79', '69', 'broken meter');
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 41, 48.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 42, 49.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 43, 50.4, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 44, 51.6, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 45, 52.8, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 46, 54.0, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 47, 55.2, 'A', NULL, NULL, NULL);
INSERT INTO interval_value (interval_day, interval, value, quality, method, reason_code, reason_description) VALUES (currval('interval_day_id_seq'), 48, -56.4, 'A', NULL, NULL, NULL);
