insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (1,'2005-02-01'::date,'KYRGYZSTAN','+996700928410','mirbek kadze','mitokadze','mitomito','facebook Miki','XXL','THIRTY_SIX','Read Book','https://ca.slack-edge.com/T023L1WBFLH-U04JFFUHKA4-771974cc4ed4-512','Не курю');
insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (2,'1998-02-02'::date,'UZBEKISTAN','+996778000202','Nurik Telegram','Nurik Instagram','Nurik Vkontakte','Nurik Facebook','S','FORTY','Play Football','https://ca.slack-edge.com/T023L1WBFLH-U051DPXE2F7-0760f6c6fbb2-512','Не курю');
insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (3,'1977-03-08'::date,'RUSSIA','+996501278650','Aikezhan Telegram','Aikezhan Instagram','Aikezhan Vkontakte','Aikezhan Facebook','L','FORTY_ONE','Sing Song','https://ca.slack-edge.com/T023L1WBFLH-U04H64X7YTD-3e41ab25f5fb-512','Не курю');
insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (4,'2006-11-08'::date,'AZERBAIJAN','+996777112233','Nuraiym telegram','Nuraiym Instagram','Nuraiym Vkontakte','Nuraiym Facebook','S','THIRTY_FIVE','Coding','https://ca.slack-edge.com/T023L1WBFLH-U03M5BDM7N2-95125b9ffdbf-512','Не курю');
insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (5,'1999-03-08'::date,'KAZAKHSTAN','+996550004433','Zhanuzak Telegram','Zhanuzak Instagram','Zhanuzak Vkontakte','Zhanuzak Facebook','L','FORTY_TWO','Play Football','https://ca.slack-edge.com/T023L1WBFLH-U051SKU3FRA-71197334aa83-512','Не курю');
insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (6,'2006-08-08'::date,'TAJIKISTAN','+996500500500','Kasymbek Telegram','Kasymbek Instagram','Kasymbek Vkontakte','Kasymbek Facebook','L','THIRTY_SIX','Write book','https://image.spreadshirtmedia.com/image-server/v1/mp/products/T812A2PA4267PT17X55Y45D1010802245W23498H25000/views/1,width=800,height=800,appearanceId=2,backgroundColor=F2F2F2,modelId=115,crop=detail/i-love-javascript-mug-unisex-premium-t-shirt.jpg','Не курю');
insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (7,'1995-03-08'::date,'KYRGYZSTAN','+996777777777','Ulanbek Telegram','Ulanbek Instagram','Ulanbek Vkontakte','Ulanbek Facebook','L','FORTY_ONE','Охотиться','https://ca.slack-edge.com/T023L1WBFLH-U052CR1H9CY-fa25d3ec82e4-512','Не курю');
insert into user_info(id,date_of_birth,country,phone_number,link_telegram,link_instagram,link_vkontakte,link_face_book,clothing_size,shoe_size,hobby,image,important)
values (8,'1990-12-08'::date,'KYRGYZSTAN','+996777777700','Mukhammed Telegram','Mukhammed Instagram','Mukhammed Vkontakte','Mukhammed Facebook','L','FORTY_ONE','Охотиться','https://ca.slack-edge.com/T023L1WBFLH-U02RZCE9R9C-8a82f69ee97e-512','Не курю');

insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (2, 'Mirbek', 'Nazhmidinov', 'mitokadze@gmail.com', '$2a$12$NEXH3UjGqGpq43k.K4Tn3.9QFk2emGuxwr1DR.mkHEE4X0xwWhDBW',false,true, 'USER',1);
insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (3, 'Nurik', 'Alymbai uulu', 'nalymbaide@gmail.com', '$2a$12$rVag3KopRuzNBo7MDPFvIeqYWbg/SS6l4/zzrvLo5QHtjofNtRBai', false,false,'USER',2);
insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (4, 'Aikezhan', 'Akhmatova', 'alinaakhmatova77@gmail.com', '$2a$12$jYw27FJsq0BnfDgHIGnJW.Umbfq8QmRGqdFJrMa5LDLdu3tX32Y/S',false,true, 'USER',3);
insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (5, 'Nuraiym', 'Mamatova', 'mnurajym9@gmail.com', '$2a$12$PRxps52ufb8drekkKGRE6.biN/ZpYdejdYeQbQOlEA2Xkmw1BXrVK', false,true,'USER',4);
insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (6, 'Kasymbek', 'Ulanbek uulu', 'kasymbek@gmail.com', '$$2a$12$xfKwxyrvcGUR5yGo6NBwfeXpCA6wi7AAzwCRE1ywpEXPS3bZ3VXy2', false,false,'USER',6);
insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (7, 'Zhanuzak', 'Anashov', 'anashov17@gmail.com', '$2a$12$Ba/TQQm6K6aYobseyAfoGODlke1zTpHVevIL5ToGwMvyifcSGpcGq', false,true,'USER',5);
insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (8, 'Abubakir', 'Kubanychbekov', 'kubanycbekovabubakir@gmail.com', '$2a$12$mS9jhoImsXDfEDKw3MvK3uEgE.fLcbjbKp78YcNDdz.GUqEOztnCy', false,true,'USER',7);
insert into users(id, first_name, last_name, email, password,is_block ,is_agree,role,user_info_id)
values (9, 'Muhammed', 'Asantegin', 'muhammed@gmail.com', '$2a$12$qQp6US9yfiAf7A7NS7/zS.zFbNtt1NqPiqrHM9NEwUsvw/aHy6Fbm', false,false,'USER',8);

INSERT INTO holidays(id, name_holiday, image, date_of_holiday, user_id)
VALUES (1, 'Кадыр тун', 'https://24.kg/files/media/291/291593.jpg', '2023-12-12'::date, 7);
INSERT INTO holidays(id, name_holiday, image, date_of_holiday, user_id)
VALUES (2, 'Курбан айт', 'https://assembly.kz/upload/iblock/7a9/7a957d5ad8251498ba80da9070e4be1f.jpg', '2023-12-12'::date, 3);
INSERT INTO holidays(id, name_holiday, image, date_of_holiday, user_id)
VALUES (3, 'День матери', 'https://www.stomatolog9.by/images/mama_i_doch06d0.jpg', '2024-01-02'::date, 4);
INSERT INTO holidays(id, name_holiday, image, date_of_holiday, user_id)
VALUES (4, 'Женский день', 'https://gshra.ru/netcat_files/multifile/1529/8_Marta.jpg', '2024-03-08'::date, 5);
INSERT INTO holidays(id, name_holiday, image, date_of_holiday, user_id)
VALUES (5, 'День защиты отечества', 'https://24.kg/files/media/60/60540.png', '2024-02-23'::date, 6);
INSERT INTO holidays(id, name_holiday, image, date_of_holiday, user_id)
VALUES (6, 'День защиты детей', 'https://rdp1.medgis.ru/uploads/9f/fd/4f/5d/9ffd4f5d13af94ec384ecc7885b4aec36499f178.jpg', '2024-06-01'::date, 2);
INSERT INTO holidays(id, name_holiday, image, date_of_holiday, user_id)
VALUES (7, 'Жума мубарак', 'https://sun9-29.userapi.com/impg/7cHKDLRBMqaWtFw62pjzzOv2P79wbDR9eIWeJw/aElaH_Z4GCM.jpg?size=750x750&quality=96&sign=3ba3aa514ac8f5d508182bfb62db65c6&c_uniq_tag=adqGz4J5NOWJ-IhJO2S4OUN_TYoIJcZiiTQeuOG0cPI&type=album', '2023-11-11'::date, 8);

insert into notifications(id,local_date,status,seen)
values (1,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (2,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (3,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (4,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (5,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (6,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (7,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (8,now(),'ADDED_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (9,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (10,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (11,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (12,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (13,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (14,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (15,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (16,now(),'ADDED_WISH',false);
insert into notifications(id,local_date,status,seen)
values (17,now(),'COMPLAINT_WISH',false);
insert into notifications(id,local_date,status,seen)
values (18,now(),'COMPLAINT_WISH',false);
insert into notifications(id,local_date,status,seen)
values (19,now(),'COMPLAINT_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (20,now(),'COMPLAINT_WISH',false);
insert into notifications(id,local_date,status,seen)
values (21,now(),'COMPLAINT_CHARITY',false);
insert into notifications(id,local_date,status,seen)
values (22,now(),'COMPLAINT_CHARITY',false);

insert into charities(id,name_charity,category,sub_category,description,image,created_at,is_block,condition,owner_id,status, notification_id,reservoir_id)
values (1,'Письма Элджертона','SCHOOL','PHOTO_AND_VIDEO_CAMERA','Мне бы очень хотелось получить этот книгу','https://img4.labirint.ru/rc/4647378ee26431af3ae3b25447fd9234/363x561q80/books86/850844/cover.jpg?1654010707',now(),false,'USED',2,'RESERVED', 1,5),
       (2,'Samsung S22','ELECTRONIC','SMARTPHONE','Мне очень нужен этот телефон','https://samsungstore.kg/files/media/10/10719.png',now(),false,'USED',6,'RESERVED', 2,3),
       (3,'Обувь','CLOTHING','COMPUTERS_AND_LAPTOP_TABLETS','Я бы хотел отдат даром этот бутсы','https://img5.lalafo.com/i/posters/original/2f/2c/52/d050b30f0b73552d307664874e.jpeg',now(),false,'NEW',4,'PENDING', 3,null),
       (4,'Платье','SHOE','CROSS','Отдам даром','https://s14.stc.all.kpcdn.net/woman/wp-content/uploads/2022/07/modnye-vechernie-platya-960-540-960x540.jpg',now(),true,'NEW',5,'RESERVED_ANONYMOUSLY',4,2),
       (5,'Laptop','ELECTRONIC','LAPTOP','Отдам даром','https://img5.lalafo.com/i/posters/original/57/ae/f2/8107d3348c6b5a371433d19ee0.jpeg',now(),true,'USED',4,'PENDING', 5,null),
       (6,'Honda Fit','TRANSPORT','CAR','Отдам даром игрушку','https://i.ytimg.com/vi/M4ccbkTuVPc/maxresdefault.jpg',now(),true,'USED',3,'RESERVED', 6,2),
       (7,'Cross','SHOE','CROSS','Отдам даром','https://i.redd.it/6rdnk4yck4r81.jpg',now(),true,'NEW',5,'PENDING', 7,null),
       (8,'Phone','ELECTRONIC','SMARTPHONE','Отдам даром','https://upload.wikimedia.org/wikipedia/commons/a/a9/Redmi_note_11_4G.jpg',now(),true,'NEW',6,'PENDING', 8,null);

insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (1,'Iphone 15','RESERVED','https://manas.news/obschestvo/chto-takoe-kadyr-tun-i-kak-ee-nuzhno-vstrechat-esli-vy-musulmanin/','https://www.cnet.com/a/img/resize/0f37c88c746b755a97f770500419522be6f1da43/hub/2023/09/18/c44256ef-e6c1-41bb-b77b-648792f47c6c/iphone15-pro-64.jpg?auto=webp&fit=crop&height=900&width=1200',false,'Я бы хотел получить такой подарок в кадыр тун','2023-10-12'::date,1,9,9,2);
insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (2,'Camry 70','RESERVED','https://www.mashina.kg/','https://turbo.kg/images/008/252/954/fbf36fc855d1186273808f5d1cd83a66.jpeg',true,'Хочу получить такое подарок от Отца','2023-12-12'::date,2,10,8,3);
insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (3,'Flowers','PENDING','https://crazylove.kg/','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkKYKogr3iKqyRXas-kQOE05L-qXUdYUdhLW3Q5cQCvB9Jp1tdVvaZSByq1_psBbzUfHs&usqp=CAU', false, 'Хочу получить букет цветов от сына','2024-01-02'::date,3,11,7,NULL);
insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (4,'Shocolade','RESERVED_ANONYMOUSLY','https://camelia.kg/shop/podarki/sladosti/shokolad-alpen-gold', 'https://globus-online.kg/upload/iblock/501/501101b1db8848fe196af37a59a8fb9a.png',false, 'Хочу получить шоколад от тебя','2023-12-02'::date,4,12,6,5);
insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (5,'Торт','PENDING','https://kulikov.kg/', 'https://globus-online.kg/upload/iblock/549/5495c874aaad964752893580bab4e2b3.png', false, 'Хочу получить торт от вас','2023-12-31'::date,5,13,5,NULL);
insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (6,'Ноутбук','RESERVED','https://ostore.kg/ru/', 'https://object.pscloud.io/cms/cms/Photo/img_0_62_2761_8_1.jpg',false, 'Хочу получить ноутбук от тебя','2024-01-02'::date,6,14,4,7);
insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (7,'Серьги','RESERVED','https://wanna-be.ru/catalog/sergi/14977/', 'https://wanna-be.ru/upload/iblock/6ed/zctxmcdlm6j3x9o9o7onti6c6wbt65x1/E332_01_Sergi_koltsa_ovalnye_bez_kamney_ser_2.jpg',false, 'Хочу получить золото','2024-03-02'::date,7,15,3,8);
insert into wishes(id,name_wish,status_wish,link_to_gift,image,is_block,description,date_of_holiday,holiday_id,notification_id,owner_id,reservoir_id)
values (8,'Кольцо','RESERVED','https://oniks.ua/categories/pomolvochnye-koltsa', 'https://oniks.ua/upload/resize_cache/iblock/d4f/379_379_2/mmxd2y16t21lvsr19cycc8gpbbb11wmk.jpg',false, 'Хочу получить кольцо','2024-05-02'::date,7,16,2,9);

insert into complains(id,text_complain,wish_id,charity_id,notification_id,select_complain,created_at)
values (1,'do not support',1,3,17,'CALLS_TO_VIOLENCE_DANGEROUS_ACTIONS', NOW());
insert into complains(id,text_complain,wish_id,charity_id,notification_id,select_complain,created_at)
values (2,'do not support',2,null,18,'SPAM', NOW());
insert into complains(id,text_complain,wish_id,charity_id,notification_id,select_complain,created_at)
values (3,'do not support',3,null,19,'ILLEGAL_ACTIVITIES_OR_REGULATED_PRODUCTS', NOW());
insert into complains(id,text_complain,wish_id,charity_id,notification_id,select_complain,created_at)
values (4,'do not support',4,null,20,'MANIFESTATION_OF_HATRED', NOW());
insert into complains(id,text_complain,wish_id,charity_id,notification_id,select_complain,created_at)
values (5,'do not support',5,4,21,'CRUELTY_AND_SHOCKING_CONTENT', NOW());
insert into complains(id,text_complain,wish_id,charity_id,notification_id,select_complain,created_at)
values (6,'do not support',6,2,22,'CRUELTY_AND_SHOCKING_CONTENT', NOW());

insert into complains_from_user(complains_id,from_user_id)
values (1,2),
       (2,3),
       (4,4),
       (3,5),
       (5,5),
       (6,5);

insert into notifications_from_user(from_user_id,notification_id)
values (2,3),
       (3,4),
       (4,2);
insert into notifications_to_user(to_user_id,notifications_id)
values (2,6),
       (3,5),
       (4,5),
       (3,6),
       (4,7),
       (3,8);

insert into notification_seen_user_id(notification_id,seen_user_id)
values (3,4),
       (3,6);

insert into mailing_lists(id,image,name_mailing,text,created_at)
values (1,'https://play-lh.googleusercontent.com/HGfb2ClmDEA6xydDMJRQtQ5JfQcHiOJT2PKCBlNTkgJZv6igmrHHFmz4010OCq2Q8cY','Hello','Welcome To Gift List',now());

insert into mailing_lists_users(mailing_list_id, users_id)
values (1,2),
       (1,4),
       (1,5),
       (1,7),
       (1,8);

insert into user_friends(friends,user_id)
values (2,3);
insert into user_friends(friends,user_id)
values (3,2);
insert into user_friends(friends,user_id)
values (4,3);
insert into user_friends(friends,user_id)
values (3,4);
insert into user_friends(friends,user_id)
values (5,3);
insert into user_friends(friends,user_id)
values (3,5);
insert into user_friends(friends,user_id)
values (7,3);
insert into user_friends(friends,user_id)
values (3,7);

insert into user_friends(friends,user_id)
values (7,2);
insert into user_friends(friends,user_id)
values (2,7);
insert into user_friends(friends,user_id)
values (5,2);
insert into user_friends(friends,user_id)
values (2,5);
insert into user_friends(friends,user_id)
values (5,7);
insert into user_friends(friends,user_id)
values (7,5);
insert into user_friends(friends,user_id)
values (5,4);
insert into user_friends(friends,user_id)
values (4,5);
insert into user_friends(friends,user_id)
values (3,6);
insert into user_friends(friends,user_id)
values (6,3);
insert into user_friends(friends,user_id)
values (2,6);
insert into user_friends(friends,user_id)
values (6,2);
