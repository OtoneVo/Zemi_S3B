function surgical(medicals, foo) {
	if (medicals[foo].value.indexOf("外科") != -1) {
		document.getElementById('surgical').checked = true;
	} else {
		document.getElementById('surgical').checked = false;
	}
}

function internal_medicine(medicals, foo) {
	if (medicals[foo].value.indexOf("内科") != -1) {
		document.getElementById('internal_medicine').checked = true;
	} else {
		document.getElementById('internal_medicine').checked = false;
	}
}

function skin(medicals, foo) {
	if (medicals[foo].value.indexOf("皮膚科") != -1) {
		document.getElementById('hihu').checked = true;
	} else {
		document.getElementById('hihu').checked = false;
	}
}

function mental(medicals, foo) {
	if (medicals[foo].value.indexOf("精神科") != -1) {
		document.getElementById('sei').checked = true;
	} else {
		document.getElementById('sei').checked = false;
	}
}

function pregnant(medicals, foo) {
	if (medicals[foo].value.indexOf("産婦人科") != -1) {
		document.getElementById('san').checked = true;
	} else {
		document.getElementById('san').checked = false;
	}
}

function brain(medicals, foo) {
	if (medicals[foo].value.indexOf("脳神経外科") != -1) {
		document.getElementById('nou').checked = true;
	} else {
		document.getElementById('nou').checked = false;
	}
}

/*
 *病院変更画面の診療科欄の変更後のチェックボックスにチェックをあらかじめつける機能
 */
$(function() {
	let medicals = [document.getElementById("medical_name")];
	let foo = 0;

	for (foo; foo < medicals.length; foo++) {
		surgical(medicals, foo);
		internal_medicine(medicals, foo);
		skin(medicals, foo);
		mental(medicals, foo);
		pregnant(medicals, foo);
		brain(medicals, foo);
	}
});