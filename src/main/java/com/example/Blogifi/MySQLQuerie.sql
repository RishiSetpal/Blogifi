-- abc123
show databases;

-- Step 1:
-- CREATE DATABASE DataBaseName
-- CREATE DATABASE BlogifiDB

-- Step 2: 
USE blogifidb;

SHOW TABLES;

DESC posts;
DESC tags;
DESC posts_tags;
DESC user;

SELECT * FROM posts;
SELECT * FROM tags;
SELECT * FROM posts_tags;
SELECT * FROM user;


-- INSERT INTO tags(name) value("it"); -- Dont add it if the table Column is Unique

-- SELECT * FROM posts_tags JOIN tags ON posts_tags.tag_id WHERE posts_tags.post_id = 1;
-- SELECT * FROM posts_tags JOIN tags ON posts_tags.tag_id WHERE posts_tags.post_id = 2;
-- SELECT * FROM posts_tags JOIN tags ON posts_tags.tag_id WHERE posts_tags.post_id = 3;
-- SELECT * FROM posts_tags JOIN tags ON posts_tags.tag_id WHERE posts_tags.post_id = 4;

SELECT * FROM posts JOIN posts_tags ON posts.id = posts_tags.post_id JOIN tags on posts_tags.tag_id = tags.id;



