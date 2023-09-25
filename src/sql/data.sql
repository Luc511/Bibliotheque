-- Supprimer les données existantes
truncate table borrow cascade;
truncate table book cascade;
truncate table user_ cascade;
truncate table library cascade;
truncate table author cascade;
truncate table address cascade;

-- Insérer les adresses
insert into address (addressid, streetnumber, streetname, zipcode, city)
values
    (1,'123','Baker Street','NW16XE','London'),
    (2,'456','Fifth Avenue','10011','New York'),
    (3,'789','Champs-Elysees','75008','Paris'),
    (4,'321','Burj Khalifa','12345','Dubai'),
    (5,'654','Space Needle','98109','Seattle'),
    (6,'987','Shibuya','150-0031','Tokyo'),
    (7,'147','Central Perk','10001','New York'),
    (8,'258','Lombard Street','94133','San Francisco'),
    (9,'369','Roundabout','SW1A 1AA','London'),
    (10,'741','Wall Street','10005','New York');

-- Insérer les auteurs
insert into author (authorid, name)
values
    (1,'John Smith'),
    (2,'Jane Doe'),
    (3,'Julia Donaldson'),
    (4,'Dr. Seuss'),
    (5,'Roald Dahl'),
    (6,'JK Rowling'),
    (7,'Stephen King'),
    (8,'Agatha Christie'),
    (9,'James Patterson'),
    (10,'Sidney Sheldon');

-- Insérer la librairie
insert into library values (1, 'Global Library', 1);

-- Insérer les utilisateurs
insert into user_ (userId, firstName, lastName, birthdate, login, password, userRole, addressId)
values
    (1, 'John', 'Doe', '1980-01-01', 'jdoe', '1234', 'Reader', 1),
    (2, 'Jane', 'Smith', '1990-01-01', 'jsmith', '1234', 'Reader', 2),
    (3, 'Robert', 'Jones', '2000-01-01', 'rjones', '1234', 'Reader', 3),
    (4, 'Julia', 'Johnson', '1970-01-01', 'jjohnson', '1234', 'Librarian', 4);

-- Insérer des livres avec leurs auteurs
insert into book (bookid, name, publishingyear, isbn, authorid, libraryid, available)
values
    (1,'To Kill a Mockingbird', 1960, '9780446310789',1,1, true),
    (2,'1984', 1950, '9780451524935',2,1, true),
    (3,'In Search of Lost Time', 1913, '9780141180311',3,1, true),
    (4,'War and Peace', 1869, '9780198813965',4,1, true),
    (5,'One Hundred Years of Solitude', 1967, '9780060883287',5,1, true),
    (6,'A Tale of Two Cities', 1859, '9780486406510',6,1, true),
    (7,'The Brothers Karamazov', 1880, '9780374528379',7,1, true),
    (8,'Pride and Prejudice', 1813, '9780141439518',7,1, true),
    (9,'The Lord of the Rings', 1954, '9780261103207',4,1, true),
    (10,'The Great Gatsby', 1925, '9780743273565',5,1, true),
    (11,'Crime and Punishment', 1866, '9780486415871',9,1, true),
    (12,'Lolita', 1955, '9780679723165',2,1, true),
    (13,'Anna Karenina', 1877, '9780553213461',10,1, true),
    (14,'Wuthering Heights', 1847, '9781853260018',1,1, true),
    (15,'Catch-22', 1961, '9780099477310',3,1, true),
    (16,'The Odyssey', -800, '9780393089059',4,1, true),
    (17,'The Divine Comedy', 1320, '9780142437223',5,1, true),
    (18,'The Iliad', -762, '9780140275360',6,1, true),
    (19,'Madame Bovary', 1856, '9781853260780',7,1, true),
    (20,'The Adventures of Huckleberry Finn', 1884, '9780486280615',8,1, true),
    (21,'Don Quixote', 1615, '9780142437230',9,1, true),
    (22,'Moby Dick', 1851, '9781503287276',10,1, true),
    (23,'Little Women', 1868, '9781503287276',6,1, true),
    (24,'Jane Eyre', 1847, '9781503287276',8,1, true),
    (25,'Gone with the Wind', 1936, '9781503287276',7,1, true);

-- Insérer les emprunts
insert into borrow (borrowid, borrowdate, returndate, userid, bookid)
select generated_series, current_date - interval '1 day' * generated_series,
       CASE
           WHEN (generated_series <= 10) THEN (current_date - interval '1 day' * generated_series + interval '14 days')
           END,
       1 + (generated_series - 1) % 4,
       2 + (generated_series - 1)
from generate_series(1, 15) as generated_series;