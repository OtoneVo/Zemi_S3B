/*
 * 病院変更画面の診療科チェックボックスのcheckedのON・OFFを制御するスクリプト
 */
function surgical(medicals, count) {
	if (medicals[count].value.indexOf("外科") != -1) {
		document.getElementById('surgical').checked = true;
	} else {
		document.getElementById('surgical').checked = false;
	}
}

function internal_medicine(medicals, count) {
	if (medicals[count].value.indexOf("内科") != -1) {
		document.getElementById('internal_medicine').checked = true;
	} else {
		document.getElementById('internal_medicine').checked = false;
	}
}

function skin(medicals, count) {
	if (medicals[count].value.indexOf("皮膚科") != -1) {
		document.getElementById('hihu').checked = true;
	} else {
		document.getElementById('hihu').checked = false;
	}
}

function mental(medicals, count) {
	if (medicals[count].value.indexOf("精神科") != -1) {
		document.getElementById('sei').checked = true;
	} else {
		document.getElementById('sei').checked = false;
	}
}

function pregnant(medicals, count) {
	if (medicals[count].value.indexOf("産婦人科") != -1) {
		document.getElementById('san').checked = true;
	} else {
		document.getElementById('san').checked = false;
	}
}

function brain(medicals, count) {
	if (medicals[count].value.indexOf("脳神経外科") != -1) {
		document.getElementById('nou').checked = true;
	} else {
		document.getElementById('nou').checked = false;
	}
}

/*
 *病院変更画面の診療科欄の変更後のチェックボックスにチェックをあらかじめつけるスクリプト
 */
$(function() {
	let medicals = [document.getElementById("medical_name")];
	let count = 0;

	for (count; count < medicals.length; count++) {
		surgical(medicals, count);
		internal_medicine(medicals, count);
		skin(medicals, count);
		mental(medicals, count);
		pregnant(medicals, count);
		brain(medicals, count);
	}
});