SELECT *
  FROM theatre;
  
SELECT *
  FROM movie;
  
SELECT *
  FROM showtime;

SELECT *
  FROM seat;
  
SELECT *
  FROM users;
  
SELECT *
  FROM ticket;

SELECT *
  FROM ticket_payment;
  
SELECT *
  FROM annual_payment;

SELECT *
  FROM financial_information;

SELECT *
  FROM users u
 INNER JOIN annual_payment a
    ON u.user_id = a.user_id
;

SELECT *
  FROM users u
 INNER JOIN ticket t
    ON t.user_id = u.user_id
 INNER JOIN ticket_payment tp
    ON tp.ticket_id = t.ticket_id
 INNER JOIN seat s
    ON s.seat_id = t.seat_id
;

SELECT *
  FROM ticket t
 INNER JOIN movie m
    ON t.movie_id = m.movie_id
 INNER JOIN showtime st
    ON st.showtime_id = t.showtime_id
 INNER JOIN seat s
    ON s.seat_id = t.seat_id;