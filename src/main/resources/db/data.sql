/* 開発用にデータ削除を追加 : リリース時は消す */
--DELETE FROM m_user;

/* ユーザマスタのデータ（ADMIN権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_permission, gender, age, birth_date, address, phone_number)
VALUES('kumamoto@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '隈元悠翔', 'ADMIN', 1, 21, '2000-07-31', '札幌市東区北30条西2丁目9-12', '090-8254-9913');
/* ユーザマスタのデータ（一般権限） PASS:pasword */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_permission, gender, age, birth_date, address, phone_number)
VALUES('kashiwaya@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '柏谷弘輝', 'GENERAL', 1, 21, '2000-08-16', '札幌市北区南1条東6丁目11-4', '090-5204-0942');
/* ユーザマスタのデータ(病院権限) PASS:password */
INSERT INTO m_user (user_id, encrypted_password, user_name, user_permission, gender, age, birth_date, address, phone_number)
VALUES('ima@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '井馬渉太', 'HOSPITAL', 1, 21, '2000-07-29', '札幌市東区北4条東6丁目3-50', '090-7243-5143');


/* 病院マスタのデータ PASS:password */
INSERT INTO hospital_list (hospital_id, hospital_name, encrypted_password, address, phone_number, number_of_reservations)
VALUES('sapporo@hosp.ac.jp', '札幌市立病院', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '札幌市中央区北10条東5丁目', '090-2435-8204' , '30');

/* 病院マスタのデータ PASS:password */
INSERT INTO hospital_list (hospital_id, hospital_name, encrypted_password, address, phone_number, number_of_reservations)
VALUES('takikawa@hosp.ac.jp', '滝川市立病院', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '滝川市空知町2丁目2−34', '090-5475-8164' , '30');

/* 病院マスタのデータ PASS:password */
INSERT INTO hospital_list (hospital_id, hospital_name, encrypted_password, address, phone_number, number_of_reservations)
VALUES('sunagawa@hosp.ac.jp', '砂川市立病院', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '砂川市西7条北4', '090-2398-0214' , '30');


/* 予約マスタテーブル */
INSERT INTO reservation_list (hospital_id, user_id, medical_id, reservation_date, reservation_time)
VALUES('sapporo@hosp.ac.jp', 'kashiwaya@hcs.ac.jp', '1', '2021-12-22', '12:30:00');


/* 診療科マスタテーブル 1：外科 */
INSERT INTO diagnosis_list (medical_id, medical_name)
VALUES('1', '外科');

/* 診療科マスタテーブル 2：内科 */
INSERT INTO diagnosis_list (medical_id, medical_name)
VALUES('2', '内科');


/* 病院診療科テーブル */
INSERT INTO hospital_diagnosis_list (hospital_id, medical_id)
VALUES('sapporo@hosp.ac.jp', '1');
