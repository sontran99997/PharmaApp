create database QLQT
go
use QLQT
go
create table Suppliers(
	ID varchar(10) primary key,
	SupName Nvarchar(30),
	SupAddress Nvarchar(50),
);
Create Table Drug_Info(
	ID varchar(10) primary key,
	DrugName Nvarchar(30),
	Price int,
	ProductionDate date NOT NULL,
	ExpirationDate date NOT NULL,
	DrugType Nvarchar(30),
	DrugUnit varchar(10),
	SupplierID varchar(10),
	Quantity int,
	Constraint FR_SP_DI Foreign key(SupplierID) references Suppliers(ID),
);
Create table Customers(
	ID varchar(10) primary key,
	CusName Nvarchar(30),
	PhoneNum int NOT NULL,
	CusAddress Nvarchar(50),
	MedicalRecord Nvarchar(50),
);
create table Staffs(
	ID varchar(10) primary key,
	StaName Nvarchar(30),
	DoB date,
	PhoneNum int NOT NULL,
	StaAddress Nvarchar(50),
);
create table Users(
	ID varchar(10) primary key,
	UserName varchar(20) NOT NULL,
	UserPass varchar(20) NOT NULL,
	LoginDate timestamp,
	Active bit,
	Constraint FR_ST_US Foreign key(ID) references Staffs(ID),
);
create table ODStatus(
	ID int primary key,
	OdStatus varchar(20) NOT NULL UNIQUE,
);
insert into ODStatus(ID,OdStatus)
values
	(1,'None'),
	(2,'Prescription');
create table Orders(
	ID varchar(10) primary key,
	OdDate date NOT NULL,
	OdStatus integer NOT NULL Default 1,
	CusID varchar(10),
	StaID varchar(10) NOT NULL,
	TotalPrice int,
	constraint FK_OS_OD foreign key(OdStatus) references ODStatus(ID),
	constraint FK_Cus_OD foreign key(CusID) references Customers(ID),
	constraint FK_Sta_OD foreign key(StaID) references Staffs(ID),
);
create table OrderDetails(
	ID varchar(10) primary key,
	OdID varchar(10) NOT NULL,
	DruID varchar(10) NOT NULL,
	Quantity int,
	PriceEach int,
	constraint FK_OD_OT foreign key(OdID) references Orders(ID),
	constraint FK_Dru_OT foreign key(DruID) references Drug_Info(ID),
	
);
create table Statistic(
	
);
