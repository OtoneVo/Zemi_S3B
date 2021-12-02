/* 開発用にデータ削除を追加　:　リリース時は消す*/
--DROP TABLE m_user;

/**
 * ユーザマスタテーブル
 *
 * user_id				ユーザID(メールアドレス形式)
 * encrypted_password	暗号化パスワード
 * user_name			ユーザ名
 * user_permission		ユーザ権限(管理者：ADMIN、一般：GENERAL、病院：HOSPITAL)
 * gender				身体性別
 * age					年齢
 * birth_date			生年月日
 * address				住所
 * phone_number			電話番号
 */
CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(254) PRIMARY KEY,
    encrypted_password varchar(100) NOT NULL,
    user_name varchar(50) NOT NULL,
    user_permission varchar(8),
    gender char(1) NOT NULL,
    age Integer(3) NOT NULL,
    birth_date timestamp NOT NULL,
    address varchar(254) NOT NULL,
    phone_number varchar(20) NOT NULL,
    PRIMARY KEY (user_id)
);

/**
 * 病院マスタテーブル
 *
 * hospital_id				病院ID
 * hospital_name			病院名
 * encrypted_password		暗号化パスワード
 * address					住所
 * phone_number				電話番号
 * number_of_reservations	予約可能人数
 * reservations_count		予約済み人数
 */
CREATE TABLE IF NOT EXISTS hospital_list (
	hospital_id varchar(254),
	hospital_name varchar(50) NOT NULL,
	encrypted_password varchar(254) NOT NULL,
	address varchar(254) NOT NULL,
	phone_number varchar(20) NOT NULL,
	number_of_reservations varchar(3) NOT NULL,
	reservations_count int NOT NULL DEFAULT 0,
	PRIMARY KEY (hospital_id)
);

/**
 * 予約マスタテーブル
 *
 * hospital_id			病院ID
 * user_id				ユーザID
 * diagnosis_id			診療科ID
 * reservation_date		予約日付
 * reservation_time		予約時刻
 */
CREATE TABLE IF NOT EXISTS reservation_list (
	hospital_id varchar(254),
	user_id varchar(254),
	diagnosis_id varchar(254),
	reservation_date timestamp,
	reservation_time time,
	PRIMARY KEY (hospital_id, user_id, diagnosis_id)
);

/**
 * 診療科マスタテーブル
 *
 * diagnosis_id		診療科ID
 * diagnosis_name	診療科名
 */
CREATE TABLE IF NOT EXISTS diagnosis_list (
	diagnosis_id varchar(254),
	diagnosis_name varchar(50) NOT NULL,
	PRIMARY KEY (diagnosis_id)
);

/**
 * 病院診療科テーブル
 *
 * hospital_id		病院ID
 * diagnosis_id		診療科ID
 */
CREATE TABLE IF NOT EXISTS hospital_diagnosis_list (
	hospital_id varchar(254),
	diagnosis_id varchar(254),
	PRIMARY KEY (hospital_id, diagnosis_id),
	FOREIGN KEY (hospital_id) REFERENCES hospital_diagnosis_list (hospital_id) ON DELETE CASCADE,
	FOREIGN KEY (diagnosis_id) REFERENCES hospital_diagnosis_list (diagnosis_id) ON DELETE CASCADE
);
