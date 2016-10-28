USE Psych;

select * from fieldLookup;
#truncate table fieldLookup;
select * from admin;

select * from location;

#delete from location where location.locCode = 'ABCD12';

insert into fieldLookup (fieldLookup.groupName, fieldLookup.fieldName, fieldLookup.description) values ('Role', 'GlobalAdministrator', 'Global Administrator Description');
insert into fieldLookup (fieldLookup.groupName, fieldLookup.fieldName, fieldLookup.description) values ('Role', 'LocalAdministrator', 'Local Administrator Description');

insert into fieldLookup (fieldLookup.groupName, fieldLookup.fieldName, fieldLookup.description) values ('State', 'MA', 'Massachusetts');
insert into fieldLookup (fieldLookup.groupName, fieldLookup.fieldName, fieldLookup.description) values ('State', 'NY', 'New York');
insert into fieldLookup (fieldLookup.groupName, fieldLookup.fieldName, fieldLookup.description) values ('State', 'NJ', 'New Jersey');

insert into location (location.locCode, location.locName, location.description, location.keywords, location.addressLine1, location.addressLine2,  location.city, location.state, location.zipcode, location.phoneNumber, location.faxNumber, location.email)  values ('ABCD12', 'Northeastern University', 'Location Decription', 'Keywords1, Keywords2, Keyword3', '360 Huntington Ave', '', 'Boston', 6, 02120, 1234567891, 1234567891, 'northeastern@google.com');

insert into admin (admin.firstName, admin.lastName, admin.email, admin.password, admin.locationId,
admin.privilegeToReleaseFeedback, admin.privilegeToCustomizeTraining, role) values 
('Darshan', 'Patel', 'patel.dars@husky.neu.edu', 'abcd', 5, true, true, 4);

# Code  : ABCD12 - QUJDRDEy
# Name  : Northeastern University - Tm9ydGhlYXN0ZXJuIFVuaXZlcnNpdHk=
# description : Location Description - TG9jYXRpb24gRGVzY3JpcHRpb24=
# keywards  : Keywords1, Keywords 2, ABCD 123455 - S2V5d29yZHMxLCBLZXl3b3JkcyAyLCBBQkNEIDEyMzQ1NQ==
# addressLine1 :
# addressLine2 :
# city :
# state :
# zipcode : 02120 - MDIxMjA=
# phoneNumber : 2168016907 - MjE2ODAxNjkwNw==
# faxNumber : 3451234567 - MzQ1MTIzNDU2Nw==
# email : locaiton@email.com : bG9jYWl0b25AZW1haWwuY29t


insert into admin (admin.firstName, admin.lastName, admin.email, admin.password, admin.locationId, admin.privilegeToReleaseFeedback, admin.privilegeToCustomizeTraining, role)
values ('', '', '', '', locationId, true, true, 4);

