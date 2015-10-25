# --- Sample dataset

# --- !Ups

insert into reservation (id,name,number,status) values (1,'Anand Kumar Singh', 'Knoldus Software', 'Sr. Software Consultant');
insert into reservation (id,name,number,status) values (2,'Supriya', 'Knoldus Software','Sr. Software Consultant');
insert into reservation (id,name,number,status) values (3,'Jyoti', 'Knoldus Software','Software Consultant');
insert into reservation (id,name,number,status) values (4,'Mayank', 'Knoldus Software','Sr. Software Consultant');

# --- !Downs

delete from employee;
