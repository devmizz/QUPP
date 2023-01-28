set foreign_key_checks = 0;
truncate table category;
truncate table subcategory;
set foreign_key_checks = 1;

INSERT INTO category (id, college) values (1, 'Humanities');
INSERT INTO category (college) values ('SocialScience');
insert into category set id = 3, college='Business';
insert into category set college='NaturalScience';
insert into category set college='Engineering';
insert into category set college='Art';

INSERT INTO subcategory (id, dept, category_id) values (1, 'Humanities', 1);    -- 대분류 인문
INSERT INTO subcategory (id, dept, category_id) values (2, 'SocialScience', 2); -- 대분류 사회과학
INSERT INTO subcategory (id, dept, category_id) values (3, 'Business', 3);      -- 대분류 상경
INSERT INTO subcategory (id, dept, category_id) values (4, 'NaturalScience', 4);       -- 대분류 자연과학
INSERT INTO subcategory (id, dept, category_id) values (5, 'Engineering', 5);   -- 대분류 공학
INSERT INTO subcategory (id, dept, category_id) values (6, 'Art', 6);   -- 대분류 예술
