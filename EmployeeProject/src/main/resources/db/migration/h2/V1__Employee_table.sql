create sequence hibernate_sequence start with 1 increment by 1;
create table employee (
		id integer default NEXTVAL('hibernate_sequence') not null, 
		city VARCHAR(255), 
		name VARCHAR(255), 
		salary integer, 
		primary key (id));
		
insert into employee (city, name, salary) values ('Aakash', 'Sangli', 60000);
insert into employee (city, name, salary) values ('Sachin', 'pune', 50000);
insert into employee (city, name, salary) values ('Vishal', 'pune', 70000);
insert into employee (city, name, salary) values ('Tejaswini', 'Hydarabad', 80000);
insert into employee (city, name, salary) values ('Avdhut', 'Miraj', 20000);
insert into employee (city, name, salary) values ('Reavti', 'pune', 30000);
insert into employee (city, name, salary) values ('Rajesh', 'Sangli', 25000);
insert into employee (city, name, salary) values ('Gourav', 'Sangli', 25000);