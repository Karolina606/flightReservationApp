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
