/**
 * main.htmlのJavaスクリプト

書き方
実行対象のボタン又はリンクのタグに return ○○○○（メソッド名）を書く。
false：実行されない]
true：実行される


例題：zipcodeの
zipcodeの入力チェック
function zip_check(){
	if (zip.zipcode.value == ""){
		//条件に一致する場合(メールアドレスが空の場合)
		alert("郵便番号が未入力です。");    //エラーメッセージを出力
		return false;    //送信ボタン本来の動作をキャンセルします
	}else{
		//条件に一致しない場合(メールアドレスが入力されている場合)
		return true;    //送信ボタン本来の動作を実行します
	}
	*/