drop table if exists Borrow;
drop table if exists Book;
drop table if exists Library;
drop table if exists Author;
drop table if exists User_;
drop table if exists Address;


create table Address (
    addressId int primary key,
    streetNumber varchar(50) not null,
    streetName varchar(50) not null,
    zipCode varchar(50) not null,
    city varchar(50) not null
);

create table User_ (
    userId bigint primary key,
    firstName varchar(50) not null,
    lastName varchar(50) not null,
    birthdate date,
    login varchar(50) not null,
    password varchar(50) not null,
    userRole varchar(50) not null,
    addressId int
        constraint FK_address_user
            references Address (addressId)
);

create table Author (
    authorId bigint primary key,
    name varchar(50) not null
);

create table Library (
    libraryId int primary key,
    name varchar(50) not null,
    addressId int
        constraint FK_address_library
            references Address (addressId)
);

create table Book (
    bookId bigint primary key,
    name varchar(50) not null ,
    publishingYear int,
    isbn varchar(50) not null ,
    available boolean,
    authorId int
        constraint FK_author_book
            references author (authorId),
    libraryId int
        constraint FK_library_book
            references Library (libraryId)
);

create table Borrow (
    borrowId bigint primary key,
    borrowDate date not null,
    returnDate date,
    userId int
        constraint FK_user_borrow
            references user_ (userId),
    bookId int
        constraint FK_book_borrow
            references book (bookId)
);

