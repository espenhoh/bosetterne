-- Kodeverk
-- insert into bosetterne.K_TYPE (k_type, dekode) values ('BOSETTERNE','Bosetterne');
-- insert into bosetterne.K_TYPE (k_type, dekode) values ('BYERRIDDER','Byer og riddere');
--  insert into bosetterne.K_TYPE (k_type, dekode) values ('SJOFARER','Sjøfarer');
--  insert into bosetterne.K_TYPE (k_type, dekode) values ('SJOOGLAND','Sjøfarer med byer og riddere');
-- insert into bosetterne.K_TYPE (k_type, dekode) values ('HANDELBARB','Traders and barbarians ikke planlagt implementert');
-- insert into bosetterne.K_TYPE (k_type, dekode) values ('EXPLPIRAT','Explorers & Pirates ikke planlagt implementert');

-- Testdata,'SJØFARER','SJØOGLAND','HANDELBARB','EXPLPIRAT'
INSERT INTO bosetterne.SPILLER (brukernavn, kallenavn, farge, epost, passord, dato_registrert) 
	VALUES ('testbruker', 'testbruker', '#000000', 'test@test.com', 'test_passord', CURDATE());
INSERT INTO bosetterne.SPILLER (brukernavn, kallenavn, farge, epost, passord, dato_registrert) 
	VALUES ('testbruker2', 'testbruker', '#0000A0', 'test@test2.com', 'test_passord', CURDATE());
    
insert into bosetterne.SPILL (navn, leder, type_spill, dato_fom, dato_tom)
	values ('testspill', 'testbruker', 'BOSETTERNE', '2014-10-12 21:14:07', '2014-10-13 01:14:07');
insert into bosetterne.SPILL (navn, leder, type_spill, dato_fom, dato_tom)
	values ('testspill', 'testbruker', 'BOSETTERNE', '2014-10-12 21:14:07', null);
insert into bosetterne.SPILL (navn, leder, type_spill, dato_fom, dato_tom)
	values ('testspill', 'testbruker', 'BOSETTERNE', null , null);
    
    
insert into bosetterne.SPILLER_I_SPILL (brukernavn, spill_id, plassering)
	values ('testbruker', 1, 1);
insert into bosetterne.SPILLER_I_SPILL (brukernavn, spill_id, plassering)
	values ('testbruker2', 1, 2);
insert into bosetterne.SPILLER_I_SPILL (brukernavn, spill_id, plassering)
	values ('testbruker', 2, null);
insert into bosetterne.SPILLER_I_SPILL (brukernavn, spill_id, plassering)
	values ('testbruker2', 2, null);
insert into bosetterne.SPILLER_I_SPILL (brukernavn, spill_id, plassering)
	values ('testbruker', 3, null);
insert into bosetterne.SPILLER_I_SPILL (brukernavn, spill_id, plassering)
	values ('testbruker2', 3, null);