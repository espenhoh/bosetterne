-- Testpersoner
insert into bosetterne.SPILLER (brukernavn, passord, kallenavn, epost, dato_registrert) VALUES ('testbruker','test_passord','kalle', 'test@test.com', CURDATE());

-- Kodeverk
insert into bosetterne.K_TYPE (k_type, dekode) values ('BOSETTERNE','Bosetterne basisspillet');
insert into bosetterne.K_TYPE (k_type, dekode) values ('BYERRIDDER','Basisspillet med byer og riddere');
insert into bosetterne.K_TYPE (k_type, dekode) values ('SJØFARER','Sjøfarerutvidelsen');
insert into bosetterne.K_TYPE (k_type, dekode) values ('SJØOGLAND','Sjøfarerutvidelsen med byer og riddere');
insert into bosetterne.K_TYPE (k_type, dekode) values ('HANDELBARB','Traders and barbarians ikke planlagt implementert');
insert into bosetterne.K_TYPE (k_type, dekode) values ('EXPLPIRAT','Explorers & Pirates ikke planlagt implementert');