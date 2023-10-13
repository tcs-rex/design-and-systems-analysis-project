# Insert into theatre
INSERT INTO theatre (theatre_name)
VALUES 
("The Globe Assembly Theatre")
;

# Insert into movie
INSERT INTO movie (title, genre, release_date, theatre_id)
VALUES 
("Black Adam", "Action", "2022-10-21", 1),
("Black Panther: Wakanda Forever", "Science Fiction", "2022-10-21", 1),
("Smile", "Horror", "2022-09-30", 1),
("Fall", "Thriller", DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY), 1),
("The Lair", "Horror", DATE_SUB(CURRENT_DATE(), INTERVAL 2 DAY), 1)
;

# Insert into showtime
INSERT INTO showtime (st_date, st_time, movie_id)
VALUES 
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "15:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "17:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "19:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "15:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "17:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "19:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "15:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "17:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "19:30:00", 1),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "15:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "17:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "19:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "15:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "17:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "19:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "15:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "17:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "19:30:00", 2),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "15:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "17:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "19:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "15:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "17:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "19:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "15:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "17:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "19:30:00", 3),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "15:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "17:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "19:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "15:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "17:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "19:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "15:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "17:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "19:30:00", 4),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "15:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "17:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), "19:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "15:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "17:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), "19:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "15:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "17:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 3 DAY), "19:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY), "15:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY), "17:30:00", 5),
(DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY), "19:30:00", 5)
;

# Insert into seat
INSERT INTO seat (seat_row, seat_column, showtime_id)
SELECT *
  FROM (
		 SELECT 1 AS seat_row
         UNION ALL
         SELECT 2 AS seat_row
         UNION ALL
         SELECT 3 AS seat_row
	   ) s_row
 CROSS JOIN (
				SELECT 1 AS seat_column
                UNION ALL
                SELECT 2 AS seat_column
                UNION ALL
                SELECT 3 AS seat_column
			) s_col
 CROSS JOIN (
				SELECT showtime_id
                  FROM showtime
			) st_id
;


# Insert into users
INSERT INTO users (first_name, last_name, email, user_password, is_reg_user, registration_date)
VALUES
('John', 'Kai', 'gameremailling@gmail.com', 'fakepassword', true, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY)),
('Olivia', 'Charlotte', 'OliviaCharlotte@gmail.com', 'fakepassword', true, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY)),
('Emma', 'Amelia', 'EmmaAmelia@gmail.com', 'fakepassword', true, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY)),
('Ava', 'Sophia', 'AvaSophia@gmail.com', 'fakepassword', true, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY)),
('Isabella', 'Mia', 'IsabellaMia@gmail.com', 'fakepassword', true, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY)),
('Evelyn', 'Harper', 'EvelynHarper@gmail.com', null, false,  null),
('Luna', 'Camila', 'LunaCamila@gmail.com', null, false, null),
('Gianna', 'Elizabeth', 'GiannaElizabeth@gmail.com', null, false, null),
('Eleanor', 'Ella', 'EleanorElla@gmail.com', null, false, null),
('Abigal', 'Sofia', 'AbigalSofia@gmail.com', null, false, null)
;

# Insert into financial information
INSERT INTO financial_information (user_id, card_number, cvc, expiry_date)
VALUES
(1, 1234567891234567, 123, DATE_ADD(CURRENT_DATE(), INTERVAL 1 YEAR)),
(2, 2345678912345678, 123, DATE_ADD(CURRENT_DATE(), INTERVAL 1 YEAR)),
(3, 3456789123456789, 123, DATE_ADD(CURRENT_DATE(), INTERVAL 1 YEAR)),
(4, 5456789123456789, 123, DATE_ADD(CURRENT_DATE(), INTERVAL 1 YEAR)),
(5, 4456789123456789, 123, DATE_ADD(CURRENT_DATE(), INTERVAL 1 YEAR))
;

# Insert into ticket
INSERT INTO ticket (user_id, movie_id, theatre_id, showtime_id, seat_id)
VALUES
(1, 1, 1, 1, 1),
(2, 2, 1, 10, 82),
(3, 3, 1, 19, 163),
(4, 4, 1, 28, 244),
(5, 5, 1, 37, 325),
(1, 1, 1, 1, 2),
(2, 2, 1, 10, 83),
(3, 3, 1, 19, 164),
(4, 4, 1, 28, 245),
(5, 5, 1, 37, 326),
(6, 1, 1, 1, 3),
(7, 2, 1, 10, 84),
(8, 3, 1, 19, 165),
(9, 4, 1, 28, 246),
(10, 5, 1, 37, 327),
(6, 1, 1, 1, 4),
(7, 2, 1, 10, 85),
(8, 3, 1, 19, 166),
(9, 4, 1, 28, 247),
(10, 5, 1, 37, 328),
(2, 5, 1, 46, 412)
;

# Insert into annual_payment
INSERT INTO annual_payment (payment_type, user_id, payment_date, amount_paid, payment_status, credit_amount, credit_expiry_date)
VALUES
("Annual", 1, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 20, "Paid", 0, NULL),
("Annual", 2, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 20, "Paid", 0, NULL),
("Annual", 3, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 20, "Paid", 0, NULL),
("Annual", 4, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 20, "Paid", 0, NULL),
("Annual", 5, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 20, "Paid", 0, NULL)
;

# Insert into ticket_payment
INSERT INTO ticket_payment (payment_type, ticket_id, payment_date, amount_paid, payment_status, credit_amount, credit_expiry_date)
VALUES
("Ticket", 1, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 2, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 3, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 4, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 5, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 6, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 15, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 7, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 15, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 8, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 15, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 9, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 15, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 10, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 15, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 11, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 12, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 13, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 14, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 15, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, NULL),
("Ticket", 16, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 12.75, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 17, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 12.75, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 18, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 12.75, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 19, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 12.75, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 20, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Refunded", 12.75, DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), INTERVAL 1 YEAR)),
("Ticket", 21, DATE_SUB(CURRENT_DATE(), INTERVAL 10 DAY), 15, "Paid", 0, null)
;

UPDATE SEAT
   SET is_taken = true
 WHERE seat_id IN (
		SELECT t.seat_id
		  FROM ticket t
		 INNER JOIN ticket_payment tp
			ON t.ticket_id = tp.ticket_id
		 WHERE payment_status = "Paid"
		)
 ;