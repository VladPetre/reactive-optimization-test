CREATE
    EXTENSION IF NOT EXISTS "pgcrypto";


--####### TABLES ##########
create table execution_steps
(
    id                    bigserial not null
        constraint execution_steps_pkey
            primary key,
    active                boolean,
    entries_number        integer,
    last_active           timestamp,
    method                varchar(255),
    receiver_nr_instances integer,
    seconds_offset        bigint,
    status                integer,
    threads_number        integer
);

alter table execution_steps
    owner to postgres;

---------

create table execution_steps
(
    id                    bigserial not null
        constraint execution_steps_pkey
            primary key,
    active                boolean,
    entries_number        integer,
    last_active           timestamp,
    method                varchar(255),
    receiver_nr_instances integer,
    seconds_offset        bigint,
    status                integer,
    threads_number        integer
);

alter table execution_steps
    owner to postgres;

-----------

create table sensors
(
    guid        uuid not null
        constraint sensors_pkey
            primary key,
    location    varchar(255),
    sensor_type varchar(255),
    status      integer
);

alter table sensors
    owner to postgres;

------------

create table sensors_data
(
    guid       uuid not null
        constraint sensors_data_pkey
            primary key,
    battery    double precision,
    updated_on timestamp,
    value      double precision
);

alter table sensors_data
    owner to postgres;

------------

-- ######## POPULATE ###########

INSERT INTO execution_steps (entries_number, method, status, threads_number, active,
                             receiver_nr_instances, seconds_offset)
VALUES (1000, 'GET', 1, 4, false, 1, 300),
       (1000, 'REACT_GET', 1, 4, false, 1, 300),
       (1000, 'GET_SAVE', 1, 4, false, 1, 300),
       (1000, 'REACT_SAVE', 1, 4, false, 1, 300),
       (1000, 'GET_SAVE', 1, 4, false, 1, 300),
       (1000, 'REACT_GET_SAVE', 1, 4, false, 1, 300),

       (1000, 'GET', 1, 4, false, 3, 300),
       (1000, 'REACT_GET', 1, 4, false, 3, 300),
       (1000, 'GET_SAVE', 1, 4, false, 3, 300),
       (1000, 'REACT_SAVE', 1, 4, false, 3, 300),
       (1000, 'GET_SAVE', 1, 4, false, 3, 300),
       (1000, 'REACT_GET_SAVE', 1, 4, false, 3, 300);

INSERT INTO sensors(guid, sensor_type, location, status)
VALUES ('1be2e67f-f8bb-4995-a714-8311f6675df1', 'Powernet', 'Vanderveer Street', 2),
       ('37ed9f52-5863-4d43-bde8-3e34588638a0', 'Verbus', 'Montrose Avenue', 1),
       ('94af2dc9-2207-46d6-85cf-95778f744acb', 'Halap', 'Cameron Court', 2),
       ('2e6830cd-03b5-4bb4-8f15-005798e761db', 'Zanymax', 'Howard Place', 2),
       ('efb5af7c-c7ca-49e5-8a76-0d116871c203', 'Optyk', 'Montauk Avenue', 1),
       ('fff0b99d-2f77-4674-a762-cb6836f719df', 'Jamnation', 'Murdock Court', 1),
       ('eea62697-e526-4d98-8291-65eb05d32c90', 'Pulze', 'Will Place', 2),
       ('b12de559-b9bc-47fb-817d-126025cdd0be', 'Marketoid', 'Macdougal Street', 3),
       ('828e974e-0bcf-441a-897c-f790d716091f', 'Zillactic', 'Nautilus Avenue', 1),
       ('5e463146-b81b-445a-a846-11e776315a2e', 'Xleen', 'Gunther Place', 3),
       ('fad70a2d-9b35-45ab-87be-6dddc17efbe1', 'Opticom', 'Hemlock Street', 2),
       ('ecbab21d-5cff-44fa-9c87-ca8b76dfb433', 'Terascape', 'Central Avenue', 3),
       ('47db1a3d-4eca-4090-afe8-ac267d1bb93d', 'Bluegrain', 'Clark Street', 1),
       ('9b3fa91a-62f8-4e42-bfcd-39b259160a3e', 'Automon', 'Doughty Street', 3),
       ('405dfbec-8706-470b-9943-cfab4951a189', 'Atgen', 'Moore Street', 1),
       ('4162c7e2-cb1f-4494-a267-fec7718d2f3c', 'Lovepad', 'Lee Avenue', 1),
       ('adb6f1a0-da6d-4342-92c3-b115b2602d90', 'Rodeocean', 'Turnbull Avenue', 1),
       ('31b43974-9965-49e3-9a99-b384ebff06f9', 'Balooba', 'Vandalia Avenue', 3),
       ('92966e11-2e3c-4d7a-865b-0f0e24ee3d33', 'Comverges', 'Maple Avenue', 1),
       ('1491a1a6-1d02-40a2-9f2b-c077d27961f1', 'Telequiet', 'Huron Street', 3),
       ('95afd977-a26a-467c-b9d2-38fcef989ec6', 'Hotcakes', 'Visitation Place', 2),
       ('7329b36e-e27e-44a1-935d-ec69fd66d0ae', 'Strozen', 'Bassett Avenue', 2),
       ('6da92ee2-ebe8-4281-b8f1-f1a6afac0812', 'Zyple', 'Commercial Street', 2),
       ('75795bf3-6934-44a2-ad2c-cec51d8cacf6', 'Accuprint', 'Marconi Place', 2),
       ('8598f739-3c19-43e2-a047-2e7be607bc54', 'Xyqag', 'Seigel Court', 2),
       ('f8cd08f5-43ae-475a-bda4-399cdc848c75', 'Kongene', 'Alabama Avenue', 2),
       ('a689330d-b3b6-4a9b-81f3-20a9e46287eb', 'Primordia', 'Story Street', 2),
       ('7d831782-bd82-4f7c-b6b6-241df264bed1', 'Namebox', 'Fillmore Avenue', 1),
       ('942f718d-7d9d-4d9d-9ba3-171fd233826c', 'Magneato', 'Agate Court', 3),
       ('4923c55b-8c80-4c2d-9897-da1912f5b363', 'Quilk', 'Hunts Lane', 2),
       ('df646288-fd2e-495a-94ba-b5647d11033e', 'Franscene', 'Garnet Street', 1),
       ('c96515c9-6f27-447a-ae72-32d87c6969d2', 'Magmina', 'Monument Walk', 2),
       ('91e8f50b-d920-4a07-8760-1ac39e5c9840', 'Hometown', 'Hawthorne Street', 1),
       ('89011d3c-9f6f-4d1d-8bfc-09ee553bca54', 'Optique', 'Lincoln Road', 1),
       ('3520d95c-4c47-4d2f-96b3-79c099e9612b', 'Fibrodyne', 'Ridgecrest Terrace', 3),
       ('3bec72cf-e853-4edb-8fe5-855bb8825e56', 'Rotodyne', 'Fleet Place', 2),
       ('5025d355-c791-49a5-a0fc-3eefb9587865', 'Xixan', 'Cedar Street', 1),
       ('3bb651ac-2265-444f-97be-e3b530aad174', 'Terragen', 'Buffalo Avenue', 3),
       ('efbcba21-1887-4728-9a6d-9c16afb1481f', 'Metroz', 'Bedford Place', 3),
       ('29a45437-fc7f-4563-bed7-e54583309218', 'Pyramax', 'Clifton Place', 2),
       ('780973e9-a347-4f8f-82d0-41e8ca9b8940', 'Geekular', 'Bleecker Street', 3),
       ('f1469362-b733-4efd-aa4b-13c4d819fb27', 'Pigzart', 'Carroll Street', 2),
       ('08ad375e-c015-43d6-bd72-36981d7e663a', 'Zytrek', 'Bokee Court', 3),
       ('6f7b5491-1158-4269-8e49-7d2754f7f1bc', 'Luxuria', 'Kay Court', 1),
       ('046f449d-ee7e-4822-8237-8b6b3a232a80', 'Geekko', 'Lombardy Street', 3),
       ('f58bae6c-668c-4716-b4a7-b7f357ca153f', 'Splinx', 'Bogart Street', 2),
       ('881187c3-51b2-418d-abcb-63d1ba445f68', 'Junipoor', 'Monitor Street', 2),
       ('59ca217a-a139-410f-9731-89ca57c5f37e', 'Eplosion', 'Eldert Street', 2),
       ('7fc47f54-f70a-49dd-acaa-82e53168a8b8', 'Isoternia', 'Livingston Street', 3),
       ('1343c30f-cfe2-480e-8d2a-4d82498c7105', 'Comveyor', 'Prince Street', 1),
       ('e7c8f07d-9a02-49e7-9ed6-fe4be905f87b', 'Dognost', 'Drew Street', 1),
       ('dc3de775-2336-49d9-8c15-b2f8c33769c8', 'Pushcart', 'Adams Street', 2),
       ('55d98583-bc7e-4316-8cda-3e56dcabf8be', 'Zensure', 'Hoyts Lane', 3),
       ('19e33f98-da9a-4b6f-9b35-240b33efe4da', 'Acium', 'Malbone Street', 3),
       ('e662624a-67ea-4b5a-9ecc-c6a654c950df', 'Vortexaco', 'Diamond Street', 1),
       ('b6e8586b-d16a-4cf6-9394-0ae72c412bbe', 'Gorganic', 'Turner Place', 2),
       ('90974c66-30c4-4490-bf05-10dc0adecea8', 'Opticall', 'Kings Place', 1),
       ('eb637d0e-efbf-4f07-8ba5-1bff340026c6', 'Realysis', 'Harbor Lane', 2),
       ('f3ec7012-6054-4350-b2c0-49e5f1c1539f', 'Geekwagon', 'Laurel Avenue', 1),
       ('75ae2760-3fdf-41cb-b9ef-8e2de0d43d13', 'Omnigog', 'Front Street', 3),
       ('4ede2bf8-df7f-4c14-8130-07ad8da31092', 'Signidyne', 'Arion Place', 3),
       ('8eac6bb6-835e-45fd-a63e-cca19b263639', 'Inquala', 'Ocean Court', 3),
       ('6c8727a5-6bb1-481c-ab51-0e09c500933a', 'Austex', 'Lake Place', 2),
       ('a231ca43-5f62-4efd-918d-7799bce5feed', 'Zillacom', 'Suydam Street', 1),
       ('acbc4717-20fc-43ad-8482-b829eb3e8e8b', 'Pharmex', 'Rutland Road', 1),
       ('a3f630e3-202a-4f50-9818-8a10b48c7a3e', 'Cormoran', 'Crescent Street', 2),
       ('e6ea880c-a97d-4baf-8144-f6a6fc2b5498', 'Ovolo', 'Gilmore Court', 3),
       ('bed1f716-f5fb-4442-ab96-211e22cd5d1a', 'Neptide', 'Veranda Place', 1),
       ('22fa2ec7-41c9-4a13-a1ad-b6025ac9e963', 'Tersanki', 'Cass Place', 3),
       ('85b2d3b6-b23a-4854-9457-151db6f150de', 'Zenthall', 'Crawford Avenue', 2),
       ('1a4deb93-4cdf-4f01-a250-da5d004f96b3', 'Neocent', 'Prospect Street', 3),
       ('6e37cf02-4ba8-4cc0-8233-9d3225a1759b', 'Maineland', 'Rose Street', 2),
       ('ee04eb52-b9fd-421d-80ec-2179f0c59e99', 'Exosis', 'Oakland Place', 1),
       ('0b82f993-616e-497e-8643-9bf8c4af631e', 'Niquent', 'Moore Place', 1),
       ('652817d9-19e6-45f8-ba1c-8048a425dee3', 'Futuris', 'Tompkins Place', 3),
       ('69ef3810-7af9-406c-98f4-a15a943495f6', 'Letpro', 'Albee Square', 3),
       ('1c8715ca-00cc-456d-8831-ecaa67e32120', 'Premiant', 'Malta Street', 1),
       ('a34aeb1c-5966-408c-8633-2ac736e9c98c', 'Lyrichord', 'Merit Court', 3),
       ('1fa31952-a78d-4e0b-8bac-e0493c2b8048', 'Empirica', 'Nolans Lane', 3),
       ('83f7c9c5-8c9d-4ee4-8db5-3d941c133911', 'Geofarm', 'Barlow Drive', 1),
       ('fa28f6d3-72e9-40a9-b0f5-661ccc8541c7', 'Snowpoke', 'River Street', 1),
       ('9a0814f8-3a9b-4705-a039-96da8b483660', 'Comtest', 'Exeter Street', 2),
       ('3ea041df-a008-457f-9294-9e53a09db2c8', 'Lexicondo', 'Ashford Street', 3),
       ('5e6bb9a1-831c-4ba5-88d4-d12343aba034', 'Amril', 'Hinsdale Street', 2),
       ('0c084823-10b1-446a-afd7-43f6268e2277', 'Urbanshee', 'Legion Street', 2),
       ('8f808dc7-8630-4183-ae65-68eeb3066247', 'Quantasis', 'Caton Avenue', 1),
       ('afcd514a-1a78-47f4-ac59-b5f1f84e772e', 'Snorus', 'Hendrickson Place', 1),
       ('721ea111-25d6-42e9-8dd0-92340012c72d', 'Zolarex', 'Conselyea Street', 2),
       ('88c67b09-368f-4070-b6f8-0c13b8ba439e', 'Solgan', 'Beekman Place', 2),
       ('86a1117b-1ff3-4b0a-948c-ae10d8f2f8ef', 'Zensor', 'Seagate Avenue', 3),
       ('637572db-bca1-4bd6-bc57-df87ce337343', 'Noralex', 'Eldert Lane', 1),
       ('2ee128b8-aaa8-4e3b-b0ab-1bf30ef2a0fb', 'Spherix', 'Beaumont Street', 2),
       ('e85e6faf-6e21-46f7-91f8-b4ef87583e3b', 'Globoil', 'Voorhies Avenue', 2),
       ('8222669a-798b-4261-8b43-48defa0bff64', 'Goko', 'Lewis Place', 1),
       ('b3def714-ecd4-4c0d-b837-fb08fdf153dc', 'Zolar', 'Navy Street', 1),
       ('273eb0ae-be1d-4818-a8d9-aec287970d8c', 'Tingles', 'Willoughby Street', 1),
       ('e2f8aab9-c255-45cd-a3a5-83188e5ff1f0', 'Suretech', 'Seabring Street', 1),
       ('3925863f-09a4-4663-994a-c43a266fa8d2', 'Zaya', 'Putnam Avenue', 1),
       ('e60eb506-1590-484e-a6fe-163e840bd4b2', 'Apex', 'King Street', 2),
       ('f3892cdc-8be1-49d8-bdd2-c61634b5bd0d', 'Accufarm', 'Grove Place', 1);


------------------------------------------------


INSERT INTO sensors_data(guid, value, battery, updated_on)
VALUES ('1be2e67f-f8bb-4995-a714-8311f6675df1', 0, 0, current_timestamp)
     , ('37ed9f52-5863-4d43-bde8-3e34588638a0', 0, 0, current_timestamp)
     , ('94af2dc9-2207-46d6-85cf-95778f744acb', 0, 0, current_timestamp)
     , ('2e6830cd-03b5-4bb4-8f15-005798e761db', 0, 0, current_timestamp)
     , ('efb5af7c-c7ca-49e5-8a76-0d116871c203', 0, 0, current_timestamp)
     , ('fff0b99d-2f77-4674-a762-cb6836f719df', 0, 0, current_timestamp)
     , ('eea62697-e526-4d98-8291-65eb05d32c90', 0, 0, current_timestamp)
     , ('b12de559-b9bc-47fb-817d-126025cdd0be', 0, 0, current_timestamp)
     , ('828e974e-0bcf-441a-897c-f790d716091f', 0, 0, current_timestamp)
     , ('5e463146-b81b-445a-a846-11e776315a2e', 0, 0, current_timestamp)
     , ('fad70a2d-9b35-45ab-87be-6dddc17efbe1', 0, 0, current_timestamp)
     , ('ecbab21d-5cff-44fa-9c87-ca8b76dfb433', 0, 0, current_timestamp)
     , ('47db1a3d-4eca-4090-afe8-ac267d1bb93d', 0, 0, current_timestamp)
     , ('9b3fa91a-62f8-4e42-bfcd-39b259160a3e', 0, 0, current_timestamp)
     , ('405dfbec-8706-470b-9943-cfab4951a189', 0, 0, current_timestamp)
     , ('4162c7e2-cb1f-4494-a267-fec7718d2f3c', 0, 0, current_timestamp)
     , ('adb6f1a0-da6d-4342-92c3-b115b2602d90', 0, 0, current_timestamp)
     , ('31b43974-9965-49e3-9a99-b384ebff06f9', 0, 0, current_timestamp)
     , ('92966e11-2e3c-4d7a-865b-0f0e24ee3d33', 0, 0, current_timestamp)
     , ('1491a1a6-1d02-40a2-9f2b-c077d27961f1', 0, 0, current_timestamp)
     , ('95afd977-a26a-467c-b9d2-38fcef989ec6', 0, 0, current_timestamp)
     , ('7329b36e-e27e-44a1-935d-ec69fd66d0ae', 0, 0, current_timestamp)
     , ('6da92ee2-ebe8-4281-b8f1-f1a6afac0812', 0, 0, current_timestamp)
     , ('75795bf3-6934-44a2-ad2c-cec51d8cacf6', 0, 0, current_timestamp)
     , ('8598f739-3c19-43e2-a047-2e7be607bc54', 0, 0, current_timestamp)
     , ('f8cd08f5-43ae-475a-bda4-399cdc848c75', 0, 0, current_timestamp)
     , ('a689330d-b3b6-4a9b-81f3-20a9e46287eb', 0, 0, current_timestamp)
     , ('7d831782-bd82-4f7c-b6b6-241df264bed1', 0, 0, current_timestamp)
     , ('942f718d-7d9d-4d9d-9ba3-171fd233826c', 0, 0, current_timestamp)
     , ('4923c55b-8c80-4c2d-9897-da1912f5b363', 0, 0, current_timestamp)
     , ('df646288-fd2e-495a-94ba-b5647d11033e', 0, 0, current_timestamp)
     , ('c96515c9-6f27-447a-ae72-32d87c6969d2', 0, 0, current_timestamp)
     , ('91e8f50b-d920-4a07-8760-1ac39e5c9840', 0, 0, current_timestamp)
     , ('89011d3c-9f6f-4d1d-8bfc-09ee553bca54', 0, 0, current_timestamp)
     , ('3520d95c-4c47-4d2f-96b3-79c099e9612b', 0, 0, current_timestamp)
     , ('3bec72cf-e853-4edb-8fe5-855bb8825e56', 0, 0, current_timestamp)
     , ('5025d355-c791-49a5-a0fc-3eefb9587865', 0, 0, current_timestamp)
     , ('3bb651ac-2265-444f-97be-e3b530aad174', 0, 0, current_timestamp)
     , ('efbcba21-1887-4728-9a6d-9c16afb1481f', 0, 0, current_timestamp)
     , ('29a45437-fc7f-4563-bed7-e54583309218', 0, 0, current_timestamp)
     , ('780973e9-a347-4f8f-82d0-41e8ca9b8940', 0, 0, current_timestamp)
     , ('f1469362-b733-4efd-aa4b-13c4d819fb27', 0, 0, current_timestamp)
     , ('08ad375e-c015-43d6-bd72-36981d7e663a', 0, 0, current_timestamp)
     , ('6f7b5491-1158-4269-8e49-7d2754f7f1bc', 0, 0, current_timestamp)
     , ('046f449d-ee7e-4822-8237-8b6b3a232a80', 0, 0, current_timestamp)
     , ('f58bae6c-668c-4716-b4a7-b7f357ca153f', 0, 0, current_timestamp)
     , ('881187c3-51b2-418d-abcb-63d1ba445f68', 0, 0, current_timestamp)
     , ('59ca217a-a139-410f-9731-89ca57c5f37e', 0, 0, current_timestamp)
     , ('7fc47f54-f70a-49dd-acaa-82e53168a8b8', 0, 0, current_timestamp)
     , ('1343c30f-cfe2-480e-8d2a-4d82498c7105', 0, 0, current_timestamp)
     , ('e7c8f07d-9a02-49e7-9ed6-fe4be905f87b', 0, 0, current_timestamp)
     , ('dc3de775-2336-49d9-8c15-b2f8c33769c8', 0, 0, current_timestamp)
     , ('55d98583-bc7e-4316-8cda-3e56dcabf8be', 0, 0, current_timestamp)
     , ('19e33f98-da9a-4b6f-9b35-240b33efe4da', 0, 0, current_timestamp)
     , ('e662624a-67ea-4b5a-9ecc-c6a654c950df', 0, 0, current_timestamp)
     , ('b6e8586b-d16a-4cf6-9394-0ae72c412bbe', 0, 0, current_timestamp)
     , ('90974c66-30c4-4490-bf05-10dc0adecea8', 0, 0, current_timestamp)
     , ('eb637d0e-efbf-4f07-8ba5-1bff340026c6', 0, 0, current_timestamp)
     , ('f3ec7012-6054-4350-b2c0-49e5f1c1539f', 0, 0, current_timestamp)
     , ('75ae2760-3fdf-41cb-b9ef-8e2de0d43d13', 0, 0, current_timestamp)
     , ('4ede2bf8-df7f-4c14-8130-07ad8da31092', 0, 0, current_timestamp)
     , ('8eac6bb6-835e-45fd-a63e-cca19b263639', 0, 0, current_timestamp)
     , ('6c8727a5-6bb1-481c-ab51-0e09c500933a', 0, 0, current_timestamp)
     , ('a231ca43-5f62-4efd-918d-7799bce5feed', 0, 0, current_timestamp)
     , ('acbc4717-20fc-43ad-8482-b829eb3e8e8b', 0, 0, current_timestamp)
     , ('a3f630e3-202a-4f50-9818-8a10b48c7a3e', 0, 0, current_timestamp)
     , ('e6ea880c-a97d-4baf-8144-f6a6fc2b5498', 0, 0, current_timestamp)
     , ('bed1f716-f5fb-4442-ab96-211e22cd5d1a', 0, 0, current_timestamp)
     , ('22fa2ec7-41c9-4a13-a1ad-b6025ac9e963', 0, 0, current_timestamp)
     , ('85b2d3b6-b23a-4854-9457-151db6f150de', 0, 0, current_timestamp)
     , ('1a4deb93-4cdf-4f01-a250-da5d004f96b3', 0, 0, current_timestamp)
     , ('6e37cf02-4ba8-4cc0-8233-9d3225a1759b', 0, 0, current_timestamp)
     , ('ee04eb52-b9fd-421d-80ec-2179f0c59e99', 0, 0, current_timestamp)
     , ('0b82f993-616e-497e-8643-9bf8c4af631e', 0, 0, current_timestamp)
     , ('652817d9-19e6-45f8-ba1c-8048a425dee3', 0, 0, current_timestamp)
     , ('69ef3810-7af9-406c-98f4-a15a943495f6', 0, 0, current_timestamp)
     , ('1c8715ca-00cc-456d-8831-ecaa67e32120', 0, 0, current_timestamp)
     , ('a34aeb1c-5966-408c-8633-2ac736e9c98c', 0, 0, current_timestamp)
     , ('1fa31952-a78d-4e0b-8bac-e0493c2b8048', 0, 0, current_timestamp)
     , ('83f7c9c5-8c9d-4ee4-8db5-3d941c133911', 0, 0, current_timestamp)
     , ('fa28f6d3-72e9-40a9-b0f5-661ccc8541c7', 0, 0, current_timestamp)
     , ('9a0814f8-3a9b-4705-a039-96da8b483660', 0, 0, current_timestamp)
     , ('3ea041df-a008-457f-9294-9e53a09db2c8', 0, 0, current_timestamp)
     , ('5e6bb9a1-831c-4ba5-88d4-d12343aba034', 0, 0, current_timestamp)
     , ('0c084823-10b1-446a-afd7-43f6268e2277', 0, 0, current_timestamp)
     , ('8f808dc7-8630-4183-ae65-68eeb3066247', 0, 0, current_timestamp)
     , ('afcd514a-1a78-47f4-ac59-b5f1f84e772e', 0, 0, current_timestamp)
     , ('721ea111-25d6-42e9-8dd0-92340012c72d', 0, 0, current_timestamp)
     , ('88c67b09-368f-4070-b6f8-0c13b8ba439e', 0, 0, current_timestamp)
     , ('86a1117b-1ff3-4b0a-948c-ae10d8f2f8ef', 0, 0, current_timestamp)
     , ('637572db-bca1-4bd6-bc57-df87ce337343', 0, 0, current_timestamp)
     , ('2ee128b8-aaa8-4e3b-b0ab-1bf30ef2a0fb', 0, 0, current_timestamp)
     , ('e85e6faf-6e21-46f7-91f8-b4ef87583e3b', 0, 0, current_timestamp)
     , ('8222669a-798b-4261-8b43-48defa0bff64', 0, 0, current_timestamp)
     , ('b3def714-ecd4-4c0d-b837-fb08fdf153dc', 0, 0, current_timestamp)
     , ('273eb0ae-be1d-4818-a8d9-aec287970d8c', 0, 0, current_timestamp)
     , ('e2f8aab9-c255-45cd-a3a5-83188e5ff1f0', 0, 0, current_timestamp)
     , ('3925863f-09a4-4663-994a-c43a266fa8d2', 0, 0, current_timestamp)
     , ('e60eb506-1590-484e-a6fe-163e840bd4b2', 0, 0, current_timestamp)
     , ('f3892cdc-8be1-49d8-bdd2-c61634b5bd0d', 0, 0, current_timestamp);
