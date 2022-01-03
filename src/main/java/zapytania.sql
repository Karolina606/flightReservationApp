INSERT INTO address (apartment_nr, building_nr, city, country, postcode, street)
VALUES(NULL, 5, 'Trzebnica', 'Polska', '55-100', 'Polna');

INSERT INTO address (apartment_nr, building_nr, city, country, postcode, street)
VALUES(20, 5, 'Wrocław', 'Polska', '45-100', 'Rynek');

INSERT INTO address (apartment_nr, building_nr, city, country, postcode, street)
VALUES(15, 10, 'Legnica', 'Polska', '30-110', 'Jagielna');

INSERT INTO address (apartment_nr, building_nr, city, country, postcode, street)
VALUES(NULL, 190, 'Wrocław', 'Polska', '54-530', 'Graniczna');

INSERT INTO address (apartment_nr, building_nr, city, country, postcode, street)
VALUES(NULL, 13, 'Bergamo', 'Włochy', '24050', 'Orio al Serio BG');

INSERT INTO address (apartment_nr, building_nr, city, country, postcode, street)
VALUES(NULL, 36, 'Barcelona', 'Hiszpania', '08002', 'Carrer dEstuc');


INSERT INTO airport(name, address_id)
VALUES ('Port lotniczy Wrocław', 4);

INSERT INTO airport(name, address_id)
VALUES ('Bergamo airport', 5);

INSERT INTO airport(name, address_id)
VALUES ('Locker Barcelona', 6);


INSERT INTO personal_data(pesel, date_of_birth, first_name, last_name, phone_number, address_id)
VALUES (12345678901, '1970-12-12', 'Andrzej', 'Kowalski', 123456789, 1);

INSERT INTO personal_data(pesel, date_of_birth, first_name, last_name, phone_number, address_id)
VALUES (12345678902, '1985-04-01', 'Sasha', 'Ivanova', 123456798, 2);

INSERT INTO personal_data(pesel, date_of_birth, first_name, last_name, phone_number, address_id)
VALUES (12345678903, '1990-01-01', 'Vanessa', 'Lopez', 213456789, 3);



INSERT INTO employee(empolyee_role, salary, pesel)
VALUES (0, 10000.00, 12345678901);

INSERT INTO employee(empolyee_role, salary, pesel)
VALUES (1, 5000.00, 12345678902);

INSERT INTO employee(empolyee_role, salary, pesel)
VALUES (1, 4000.00, 12345678903);


INSERT INTO plane_model(brand, model_name, number_of_flight_attendants, number_of_pilots, number_of_seats, tank_capacity)
VALUES ('Boeing', 737, 10, 2, 230, 25940);

INSERT INTO plane(airlines, inspection_date, model_id)
VALUES ('QuatarAirlines', '2022-03-01', 1);

INSERT INTO plane(airlines, inspection_date, model_id)
VALUES ('Ryanair', '2021-12-28', 1);


INSERT INTO flight(arrival_date, departure_date, price, arrival_place_id, departure_place_id, plane_id)
VALUES ('2021-12-31 16:00:00', '2021-12-31 14:00:00', 200.00, 2, 1, 2);

INSERT INTO flight(arrival_date, departure_date, price, arrival_place_id, departure_place_id, plane_id)
VALUES ('2022-01-10 08:30:00', '2022-01-10 10:30:00', 300.00, 3, 1, 1);

SELECT * FROM airport;
SELECT * FROM address;
SELECT * FROM personal_data;
SELECT * FROM employee;

DELETE FROM airport WHERE id > 0;
DELETE FROM address WHERE id > 0;
DELETE FROM personal_data WHERE pesel = 12345678903;
DELETE FROM employee WHERE pesel = 12345678903;
DELETE FROM plane_model WHERE id = 1;

ALTER TABLE address AUTO_INCREMENT = 1;
ALTER TABLE personal_data AUTO_INCREMENT = 1;
ALTER TABLE plane_model AUTO_INCREMENT = 1;
ALTER TABLE plane AUTO_INCREMENT = 1;
ALTER TABLE flight AUTO_INCREMENT = 1;
ALTER TABLE airport AUTO_INCREMENT = 1;
ALTER TABLE flight_crew AUTO_INCREMENT = 1;
ALTER TABLE reservation AUTO_INCREMENT = 1;
ALTER TABLE employee AUTO_INCREMENT = 1;
ALTER TABLE user AUTO_INCREMENT = 1;

UPDATE employee SET employee_role = 1 WHERE employee_role = 2;

UPDATE flight SET departure_date =  FROM_UNIXTIME(
            UNIX_TIMESTAMP('2022-01-01 00:00:00') + FLOOR(0 + (RAND() * 31531500))
    ), arrival_date = FROM_UNIXTIME(UNIX_TIMESTAMP(departure_date) + FLOOR(0 + (RAND() * 43000))) WHERE true;

UPDATE plane SET inspection_date =  FROM_UNIXTIME(
            UNIX_TIMESTAMP('2022-01-01 00:00:00') + FLOOR(0 + (RAND() * 31531500))) WHERE true;

UPDATE employee SET employee_role = employee_role where true;

UPDATE user SET role = 0 WHERE  role = 1;
UPDATE user SET role = 1 WHERE  role = 2;
UPDATE user SET role = 2 WHERE  role = 3;

DELETE FROM employee WHERE employee.id = 41;
