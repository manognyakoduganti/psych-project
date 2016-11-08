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

# 50 USA States
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Alabama', 'Alabama');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Alaska', 'Alaska');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Arizona', 'Arizona');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Arkansas', 'Arkansas');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'California', 'California');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Colorado', 'Colorado');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Connecticut', 'Connecticut');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Delaware', 'Delaware');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Florida', 'Florida');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Georgia', 'Georgia');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Hawaii', 'Hawaii');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Idaho', 'Idaho');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Illinois', 'Illinois');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Indiana', 'Indiana');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Iowa', 'Iowa');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Kansas', 'Kansas');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Kentucky', 'Kentucky');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Louisiana', 'Louisiana');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Maine', 'Maine');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Maryland', 'Maryland');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Massachusetts', 'Massachusetts');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Michigan', 'Michigan');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Minnesota', 'Minnesota');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Mississippi', 'Mississippi');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Missouri', 'Missouri');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Montana', 'Montana');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Nebraska', 'Nebraska');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Nevada', 'Nevada');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'New Hampshire', 'New Hampshire');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'New Jersey', 'New Jersey');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'New Mexico', 'New Mexico');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'New York', 'New York');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'North Carolina', 'North Carolina');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'North Dakota', 'North Dakota');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Ohio', 'Ohio');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Oklahoma', 'Oklahoma');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Oregon', 'Oregon');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Pennsylvania', 'Pennsylvania');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Rhode Island', 'Rhode Island');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'South Carolina', 'South Carolina');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'South Dakota', 'South Dakota');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Tennessee', 'Tennessee');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Texas', 'Texas');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Utah', 'Utah');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Vermont', 'Vermont');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Virginia', 'Virginia');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Washington', 'Washington');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'West Virginia', 'West Virginia');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Wisconsin', 'Wisconsin');
insert into fieldlookup (fieldlookup.groupName, fieldlookup.fieldName, fieldlookup.description) values ('State', 'Wyoming', 'Wyoming');

