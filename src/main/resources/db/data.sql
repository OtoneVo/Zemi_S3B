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
