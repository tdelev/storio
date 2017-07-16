DROP TABLE IF EXISTS stores;
CREATE TABLE stories
(
  id          TEXT PRIMARY KEY NOT NULL,
  author      TEXT             NOT NULL,
  text        TEXT             NOT NULL,
  likes       INTEGER          NOT NULL DEFAULT 0,
  time_posted TIMESTAMP WITHOUT TIME ZONE
);
