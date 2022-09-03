CREATE TABLE IF NOT EXISTS EVENTS_LOG
(
Id BIGINT IDENTITY PRIMARY KEY,
Event_Id VARCHAR(500) NOT NULL,
Event_Duration BIGINT,
Type VARCHAR (50),
Host VARCHAR(50),
Alert BIT
);