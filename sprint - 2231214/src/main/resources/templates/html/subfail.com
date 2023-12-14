<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="/css/subscribe.css" />
</head>

<body>
    <div class="div-logo">
        <a href="/" class="logo"><img src="images/newcar.png" ></a>
        <!-- <p id="logo">NEW CAR</p> -->
    </div>
    <div class="main">
        <h2>구독 신청 실패</h2>
        <p>현재 구독 신청이 되어있거나</p>
        <p>Premium에서 Basic으로 신청하는 경우</p>
        <p>구독 신청이 불가능합니다.</p>
        <div class="div-btnflex">
            <a href="/subpage" class="a-ok">확인</a>
            <a href="javascript:history.back();" class="a-cancel">취소</a>
        </div>
    </div>
</body>

</html>