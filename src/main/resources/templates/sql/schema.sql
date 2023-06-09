create type role as enum ('USER', 'ADMIN');
create type account_status as enum ('ACTIVE', 'BANNED');

create table account
(
    id             bigserial primary key,
    username       varchar(35),
    password       varchar,
    first_name     varchar,
    last_name      varchar,
    phone_number   varchar(12),
    role           role,
    rating         float,
    car_brand      varchar,
    car_model      varchar,
    avatar_ref     varchar,
    account_status account_status
);

create table drive
(
    id                   bigserial primary key,
    source               varchar,
    destination          varchar,
    date                 date,
    time                 time,
    price                int,
    number_of_passengers int,
    driver_id            bigint references account (id)
);

create table account_drive
(
    id         bigserial primary key,
    account_id bigint references account (id),
    drive_id   bigint references drive (id)
);

create table warn
(
    id           bigserial primary key,
    account_id   bigint references account (id),
    warn_message varchar
);

create table review
(
    id          bigserial primary key,
    author_id   bigint references account (id),
    account_id  bigint references account (id),
    rate        float,
    review_text text
);

create table preference
(
    id              bigserial primary key,
    account_id      bigint references account (id),
    preference_unit varchar
);

create table bookmark
(
    id         bigserial primary key,
    account_id bigint references account (id),
    drive_id   bigint references drive (id)
);

create table city
(
    id        bigserial primary key,
    city_name varchar
);

insert into city (city_name)
values ('Moscow'),
       ('Saint Petersburg'),
       ('Novosibirsk'),
       ('Yekaterinburg'),
       ('Nizhniy Novgorod'),
       ('Kazan'),
       ('Chelyabinsk'),
       ('Omsk'),
       ('Samara'),
       ('Rostov'),
       ('Ufa'),
       ('Krasnoyarsk'),
       ('Voronezh'),
       ('Perm'),
       ('Volgograd'),
       ('Krasnodar'),
       ('Saratov'),
       ('Tyumen'),
       ('Tolyatti'),
       ('Izhevsk'),
       ('Barnaul'),
       ('Ulyanovsk'),
       ('Irkutsk'),
       ('Khabarovsk'),
       ('Yaroslavl'),
       ('Vladivostok'),
       ('Makhachkala'),
       ('Tomsk'),
       ('Orenburg'),
       ('Kemerovo'),
       ('Novokuznetsk'),
       ('Ryazan'),
       ('Naberezhnyye Chelny'),
       ('Astrakhan'),
       ('Penza'),
       ('Lipetsk'),
       ('Kirov'),
       ('Cheboksary'),
       ('Tula'),
       ('Kaliningrad'),
       ('Balashikha'),
       ('Kursk'),
       ('Stavropol'),
       ('Ulan-Ude'),
       ('Tver'),
       ('Magnitogorsk'),
       ('Sochi'),
       ('Ivanovo'),
       ('Bryansk'),
       ('Surgut'),
       ('Belgorod'),
       ('Vladimir'),
       ('Nizhniy Tagil'),
       ('Arkhangelsk'),
       ('Chita'),
       ('Kaluga'),
       ('Smolensk'),
       ('Volzhskiy'),
       ('Cherepovets'),
       ('Saransk'),
       ('Vologda'),
       ('Yakutsk'),
       ('Orël'),
       ('Kurgan'),
       ('Vladikavkaz'),
       ('Podolsk'),
       ('Murmansk'),
       ('Groznyy'),
       ('Tambov'),
       ('Sterlitamak'),
       ('Nizhnevartovsk'),
       ('Petrozavodsk'),
       ('Kostroma'),
       ('Novorossiysk'),
       ('Yoshkar-Ola'),
       ('Nalchik'),
       ('Engels'),
       ('Khimki'),
       ('Taganrog'),
       ('Komsomol’sk-na-Amure'),
       ('Syktyvkar'),
       ('Nizhnekamsk'),
       ('Shakhty'),
       ('Dzerzhinsk'),
       ('Bratsk'),
       ('Orsk'),
       ('Noginsk'),
       ('Angarsk'),
       ('Kolpino'),
       ('Blagoveshchensk'),
       ('Staryy Oskol'),
       ('Velikiy Novgorod'),
       ('Korolëv'),
       ('Mytishchi'),
       ('Pskov'),
       ('Lyubertsy'),
       ('Biysk'),
       ('Prokopyevsk'),
       ('Yuzhno-Sakhalinsk'),
       ('Balakovo'),
       ('Armavir'),
       ('Rybinsk'),
       ('Severodvinsk'),
       ('Abakan'),
       ('Petropavlovsk-Kamchatskiy'),
       ('Norilsk'),
       ('Krasnogorsk'),
       ('Syzran'),
       ('Volgodonsk'),
       ('Ussuriysk'),
       ('Kamensk-Ural’skiy'),
       ('Novocherkassk'),
       ('Zlatoust'),
       ('Elektrostal'),
       ('Almetyevsk'),
       ('Salavat'),
       ('Miass'),
       ('Nakhodka'),
       ('Orekhovo-Borisovo Yuzhnoye'),
       ('Kopeysk'),
       ('Odintsovo'),
       ('Pyatigorsk'),
       ('Rubtsovsk'),
       ('Berezniki'),
       ('Kolomna'),
       ('Maykop'),
       ('Khasavyurt'),
       ('Kovrov'),
       ('Kislovodsk'),
       ('Nefteyugansk'),
       ('Domodedovo'),
       ('Neftekamsk'),
       ('Bataysk'),
       ('Novocheboksarsk'),
       ('Serpukhov'),
       ('Novomoskovsk'),
       ('Shchelkovo'),
       ('Pervouralsk'),
       ('Kaspiysk'),
       ('Derbent'),
       ('Cherkessk'),
       ('Orekhovo-Zuyevo'),
       ('Nazran'),
       ('Nevinnomyssk'),
       ('Ramenskoye'),
       ('Dimitrovgrad'),
       ('Kyzyl'),
       ('Obninsk'),
       ('Oktyabr’skiy'),
       ('Novyy Urengoy'),
       ('Yessentuki'),
       ('Kamyshin'),
       ('Murom'),
       ('Dolgoprudnyy'),
       ('Novoshakhtinsk'),
       ('Zhukovskiy'),
       ('Seversk'),
       ('Reutov'),
       ('Noyabrsk'),
       ('Artëm'),
       ('Khanty-Mansiysk'),
       ('Pushkino'),
       ('Achinsk'),
       ('Sergiyev Posad'),
       ('Arzamas'),
       ('Yelets'),
       ('Berdsk'),
       ('Elista'),
       ('Noginsk'),
       ('Novokuybyshevsk'),
       ('Zheleznogorsk'),
       ('Zelënodol’sk'),
       ('Magadan'),
       ('Velikiye Luki'),
       ('Lobnya'),
       ('Buzuluk'),
       ('Kineshma'),
       ('Kuznetsk'),
       ('Yurga'),
       ('Ivanteyevka'),
       ('Chernogorsk'),
       ('Birobidzhan'),
       ('Kirovo-Chepetsk'),
       ('Georgiyevsk'),
       ('Ishim'),
       ('Buynaksk'),
       ('Gukovo'),
       ('Gorno-Altaysk'),
       ('Fryazino'),
       ('Lytkarino'),
       ('Shuya'),
       ('Prokhladnyy'),
       ('Iskitim'),
       ('Klimovsk'),
       ('Dzerzhinskiy'),
       ('Volzhsk'),
       ('Salekhard'),
       ('Moskovskiy'),
       ('Novovyatsk'),
       ('Mozhga'),
       ('Kizlyar'),
       ('Kotelniki'),
       ('Kanash'),
       ('Krasnoznamensk'),
       ('Morshansk'),
       ('Pereslavl’-Zalesskiy'),
       ('Mtsensk'),
       ('Baksan'),
       ('Protvino'),
       ('Kasimov'),
       ('Kokhma'),
       ('Kotovsk'),
       ('Dagestanskiye Ogni'),
       ('Shumerlya'),
       ('Udomlya'),
       ('Desnogorsk'),
       ('Losino-Petrovskiy'),
       ('Nar’yan-Mar'),
       ('Kraskovo'),
       ('Karachayevsk'),
       ('Kozmodemyansk'),
       ('Kirsanov'),
       ('Anadyr'),
       ('Udelnaya'),
       ('Kudrovo'),
       ('Pionerskiy'),
       ('Khasanya'),
       ('Magas'),
       ('Kenzhe'),
       ('Molochnoye'),
       ('Solyanka'),
       ('Timofeyevka'),
       ('Belaya Rechka'),
       ('Dzerzhinskoye'),
       ('Oktyabr’skiy')