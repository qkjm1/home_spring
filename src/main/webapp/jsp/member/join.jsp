<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<h2>회원가입</h2>

	<a href="../home/main">메인화면 돌아가기</a>

	<script type="text/javascript">
		function joinForm__submit(form){
			let loginId = form.loginId.value.trim();
			let loginPw = form.loginPw.value.trim();
			let loginPwConfirm = form.loginPwConfirm.value.trim();
			
			if (loginId.length == 0) {
				alert('아이디를 기입해주세요');

				return;
			}
			if (loginPw.length == 0) {
				alert('비밀번호를 기입해주세요');
				form.loginPw.focus();
				return;
			}
			if (loginPwConfirm.length == 0 || loginPw != loginPwConfirm) {
				alert('제대로 입력');
				form.loginPwConfirm.focus();
				return;
			}
			if (form.name.value.trim().length == 0) {
				alert('이름을 기입해주세요');
				form.name.focus();
				return;
			}
			
			from.submit();
		}
	</script>

	<form onsubmit="joinForm__submit(this); return false;" action="doJoin"
		method="POST">
		<div>
			아이디 : <input autocomplete="off" type="text" placeholder="아이디 입력" name="loginId" />
		</div>
		<div>
			비밀번호 : <input autocomplete="off" type="text" placeholder="비밀번호 입력" name="loginPw" />
		</div>
		<div>
			비밀번호 확인 : <input autocomplete="off" type="text" placeholder="비밀번호 확인" name="loginPwConfirm" />
		</div>
		<div>
			이름 : <input autocomplete="off" type="text" placeholder="이름 입력" name="name" />
		</div>
		
		<button type="submit">가입</button>
	</form>
</body>
</html>