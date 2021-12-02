$(function() {
	$("#textarea").bind("blur", function() {
		var textarea = $(this).val();
		check_textarea(textarea);
		$('#report').prop("disabled", false);
	});
});

function check_textarea(str) {
	$("#err_textarea label").remove();
	$("#err_textarea br").remove();
	var _result = true;
	var textarea = $.trim(str);

	if (textarea.length == 0) {
		$("#err_textarea").append("<br><label>報告内容を入力してください。</label>");
		_result = false;
	} else if (textarea.length >= 1000) {
		$("#err_textarea").append("<br><label>報告内容は1000文字以内で入力してください。</label>");
		_result = false;
	}
	return _result;
}

function do_check() {
	var result = true;
	var check_result = true;

	// エラーメッセージ初期化
	$("#err_textarea label").remove();
	$("#err_textarea br").remove();

	// テキストエリア
	var textarea = $("#textarea").val();
	result = check_textarea(textarea);
	if(!result){
		check_result = result;
	}

	return check_result;
}

function do_submit() {
	var result = do_check();
	var check = true;

	if (result) {
		alert("就職活動申請（報告）を修正しました。");
		check = true;
	} else {
		alert("エラーがあります。\n画面を確認してください。");
		//新規作成ボタン非活性
		$('#report').prop("disabled", true);
		check = false;
	}

	return check;
}