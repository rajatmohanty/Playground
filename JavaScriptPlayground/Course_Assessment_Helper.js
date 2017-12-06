/* 打开课程评估后，首先点开第一个课的”未评”，让右方出现列表 */
/* index 是你想给老师统一打的分，0 = 很满意，以此类推，取值范围为 [0, 3] */
var index = 0;
var table = document.getElementById("tbCourseList");

evlFaile = function() {
			alert("Fail");
		}

updateEval = function() {
	return true;
}

check = function() {
	var m = document.all.question.value;
	var n = document.all.mulItem.value;		

	for(var i = 1; i <= m; i++){
	   	var temp = document.getElementsByName('question' + i);
	   	temp[index].checked = true;
	}

	for(var j = 1; j <= n; j++){
	   	var temp = document.getElementsByName('mulItem' + j);
	   	temp[index].checked = true;
	}
	var button = document.getElementsByName('sub')[0];
	button.click();
	return true;
}

for(var c = 0; c < table.rows.length; c++){
	var course = table.rows[c];
	course.click();

	alert(c + " " + course.id);

	try{
		var button = document.getElementsByName('sub')[0];
		button.click();
	}catch(err){
		alert("No button");
	}
}
alert("Done!");