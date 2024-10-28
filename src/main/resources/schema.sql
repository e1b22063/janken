/* userやgroupといった名前はSQLでは予約語で使えないため，userNameとしていることに注意 */
CREATE TABLE users (
    id IDENTITY,
    name VARCHAR
);

CREATE TABLE matches (
  id IDENTITY,
  user1 INT,
  user2 INT,
  user1Hand VARCHAR,
  user2Hand VARCHAR,
  isActive BOOLEAN DEFAULT FALSE
);

CREATE TABLE matchinfo (
  id IDENTITY,
  user1 INT,
  user2 INT,
  user1Hand VARCHAR,
  isActive BOOLEAN DEFAULT FALSE
);
