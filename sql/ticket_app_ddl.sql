CREATE SCHEMA IF NOT EXISTS movie_theatre_ticket_system;

# Create statement for theatre table
CREATE TABLE IF NOT EXISTS theatre (
	theatre_id int PRIMARY KEY AUTO_INCREMENT,
    theatre_name varchar(30) NOT NULL UNIQUE
);

# Create statement for movie table 
CREATE TABLE IF NOT EXISTS movie (
	movie_id int PRIMARY KEY AUTO_INCREMENT,
    title varchar(30) NOT NULL,
    genre varchar(30) NOT NULL,
    release_date date NOT NULL,
    theatre_id int NOT NULL,
    CONSTRAINT FOREIGN KEY (theatre_id) REFERENCES theatre(theatre_id)
);

# Create statement for showtime table
CREATE TABLE IF NOT EXISTS showtime (
	showtime_id int PRIMARY KEY AUTO_INCREMENT,
    st_date date NOT NULL,
    st_time time NOT NULL,
    movie_id int NOT NULL,
    CONSTRAINT FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    CONSTRAINT UNIQUE (st_date, st_time, movie_id)
);

# Create statement for seat table
CREATE TABLE IF NOT EXISTS seat (
	seat_id int PRIMARY KEY AUTO_INCREMENT,
    seat_row int NOT NULL,
    seat_column int NOT NULL,
    is_taken boolean NOT NULL DEFAULT false,
    showtime_id int NOT NULL,
    CONSTRAINT FOREIGN KEY (showtime_id) REFERENCES showtime(showtime_id),
    CONSTRAINT UNIQUE (showtime_id, seat_row, seat_column)
);

# Create statement for user table
CREATE TABLE IF NOT EXISTS users (
	user_id int PRIMARY KEY AUTO_INCREMENT,
    first_name varchar(30) NULL,
    last_name varchar(30) NULL,
    email varchar(30) NOT NULL UNIQUE,
    user_password varchar(30) NULL,
    is_reg_user boolean NOT NULL DEFAULT false,
    registration_date date NULL,
    CONSTRAINT UNIQUE (email),
    CONSTRAINT reg_user_chk CHECK (
		(
			is_reg_user = true AND 
			first_name IS NOT NULL AND 
			last_name IS NOT NULL AND 
			email IS NOT NULL AND
			user_password IS NOT NULL AND
			registration_date IS NOT NULL
        ) OR
        (
			is_reg_user = false AND
            registration_date IS NULL AND
            user_password IS NULL
        )
	)
);

# Create statement for financial information table
CREATE TABLE IF NOT EXISTS financial_information (
	user_id int NOT NULL,
    card_number bigint NOT NULL,
    cvc int NOT NULL,
    expiry_date date NOT NULL,
    CONSTRAINT UNIQUE (user_id),
    CONSTRAINT UNIQUE (card_number),
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(user_id)
);

# Create statement for ticket table
CREATE TABLE IF NOT EXISTS ticket (
	ticket_id int PRIMARY KEY AUTO_INCREMENT,
    user_id int NOT NULL,
    movie_id int NOT NULL,
    theatre_id int NOT NULL,
    showtime_id int NOT NULL,
    seat_id int NOT NULL,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FOREIGN KEY (movie_id) REFERENCES movie(movie_id),
    CONSTRAINT FOREIGN KEY (theatre_id) REFERENCES theatre(theatre_id),
    CONSTRAINT FOREIGN KEY (showtime_id) REFERENCES showtime(showtime_id),
    CONSTRAINT FOREIGN KEY (seat_id) REFERENCES seat(seat_id)
);

-- # Create payment type table
-- CREATE TABLE IF NOT EXISTS payment_type (
-- 	payment_type_id int PRIMARY KEY,
--     payment_type varchar(30) NOT NULL UNIQUE
-- );

-- # Create payment status table
-- CREATE TABLE IF NOT EXISTS payment_status (
-- 	payment_status_id int PRIMARY KEY,
--     payment_status varchar(30) NOT NULL UNIQUE
-- );

# Create statement for payment table
CREATE TABLE IF NOT EXISTS annual_payment (
	payment_id int PRIMARY KEY AUTO_INCREMENT, 
	user_id int NOT NULL,
    payment_type varchar(10) NOT NULL DEFAULT "Annual",
    payment_date date NOT NULL DEFAULT (CURRENT_DATE),
    amount_paid float NOT NULL DEFAULT 20,
    payment_status varchar(10) NOT NULL DEFAULT "Paid",
    credit_amount float NOT NULL DEFAULT 0,
    credit_expiry_date date NULL,
    CONSTRAINT FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT payment_type_chk CHECK (payment_type IN ("Annual")),
    CONSTRAINT payment_status_chk CHECK (payment_status IN ("Paid")),
    CONSTRAINT anuual_fee_chk CHECK (
		payment_type = "Annual" AND
        payment_status = "Paid" AND
        credit_amount = 0 AND
        credit_expiry_date IS NULL
	)
);

# Create statement for payment table
CREATE TABLE IF NOT EXISTS ticket_payment (
	payment_id int PRIMARY KEY AUTO_INCREMENT,
	ticket_id int NOT NULL,
    payment_type varchar(10) NOT NULL DEFAULT "Ticket",
    payment_date date NOT NULL DEFAULT (CURRENT_DATE),
    amount_paid float NOT NULL DEFAULT 15,
    payment_status varchar(10) NOT NULL DEFAULT ("Paid"),
    credit_amount float NULL DEFAULT 0,
    credit_expiry_date date NULL,
    CONSTRAINT FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id),
    CONSTRAINT payment_type_chk2 CHECK (payment_type IN ("Ticket")),
    CONSTRAINT payment_status_chk2 CHECK (payment_status IN ("Paid", "Refunded")),
    CONSTRAINT refunded_ticket_chk2 CHECK (
		(
			payment_status = "Refunded" AND
			credit_amount IS NOT NULL AND
			credit_expiry_date IS NOT NULL
		) OR
        (
			payment_status = "Paid" AND
            credit_amount = 0 AND
            credit_expiry_date IS NULL
		)
    ),
    CONSTRAINT credit_expiry_date_chk2 CHECK (
		credit_expiry_date IS NULL OR
		credit_expiry_date > payment_date
    ),
    CONSTRAINT credit_amount_chk2 CHECK (
		credit_amount <= amount_paid
    )
);

# Create statement for news VIEW
CREATE OR REPLACE VIEW news 
	AS
SELECT *
  FROM movie m
 WHERE m.release_date BETWEEN CURRENT_DATE() AND DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY)
 GROUP BY m.genre,
          m.release_date,
          m.title
;