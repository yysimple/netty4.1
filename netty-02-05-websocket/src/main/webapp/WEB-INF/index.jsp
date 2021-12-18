<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>wechat-01</title>

<script type="text/javascript" src="res/js/jquery.min.js"></script>
<script type="text/javascript" src="res/js/jquery.serialize-object.min.js"></script>
<script type="text/javascript" src="res/js/index.js"></script>

<style>
body{
	background-image:url(res/img/bg.jpg);
	background-repeat:no-repeat;
	background-size:cover;
	font-family:”微软雅黑”;
}


#chatDiv{
	position: relative;
	margin:0 auto;
	margin-top:150px;
	width:839px;
	height:667px;
	background-color:#CCCCCC;
	border-radius:3px;-moz-border-radius:3px;
}

</style>

</head>

<body>

<div id="chatDiv">
	<!-- left -->
	<div style="width:60px; height:667px; background-color:#2D2B2A; float:left;">
		<!-- 头像 -->
		<div style="width:35px; height:35px; margin:0 auto; margin-top:19px; margin-left:12px; float:left; border:1px solid #666666;border-radius:3px;-moz-border-radius:3px;">
			<img src="res/img/bugstack.png" width="35px" height="35px"/>
		</div>

		<!-- 聊天 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:25px; margin-left:16px; float:left;">
			<img src="res/img/chat.png" width="28px" height="28px"/>
		</div>

		<!-- 好友 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:20px; margin-left:16px; float:left;">
			<img src="res/img/friend.png" width="28px" height="28px"/>
		</div>

		<!-- 收藏 -->
		<div style="width:28px; height:28px; margin:0 auto; margin-top:20px; margin-left:16px; float:left;">
			<img src="res/img/collection.png" width="28px" height="28px"/>
		</div>

		<!-- 设置 -->
		<div style="width:20px; height:20px; margin:0 auto; margin-left:20px; float:left; position:absolute;bottom:0; margin-bottom:12px;">
			<img src="res/img/set.png" width="20px" height="20px"/>
		</div>

	</div>

	<!-- center -->
	<div style="width:250px; height:667px; background-color:#EBE9E8; float:left;">
		<div style=" background-image:url(res/img/search.png); background-repeat:no-repeat;margin:0 auto; margin-top:25px; padding-top:5px; padding-bottom:5px; width:210px; background-color:#DBD9D8;border-radius:3px;-moz-border-radius:3px; float:left; margin-left:20px; font-size:12px; color:#333333;text-indent:27px;">
			王力宏
		</div>

		<!-- friendList -->
		<div id="friendList" style="float:left; margin-top:5px;">
			<div style="width:250px; height:65px;">
				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/cuicui.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">崔猛男</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">崔梦涵：[图片]</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#C9C7C6;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/junbing.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">古天乐</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">meimei：咖喱给给</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/shaoqi.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">逝水</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">流年：[微信红包]</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/ycl.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">圆圈亮</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">ycg：开下灯</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head5.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">库克</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">库克：你的MacBookPro请接收</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head7.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">bill</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">bill：我的micro还是很好用的..</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head8.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">马云</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">马云：你什么时候来做做..</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head9.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">王健林</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">王健林：明年的小目标。10个亿</td>
					</tr>
				</table>

				<table style="width:240px; height:60px; margin:0 auto; margin-top:2px; background-color:#E5E5E5;">
					<tr>
						<td rowspan="2" width="50"><img src="res/img/head10.jpg" width="50px" height="50px" style="border-radius:3px;-moz-border-radius:3px;"/></td>
						<td style="color:#333333; text-indent:5px; font-size:12px; vertical-align:bottom; font-weight:bolder;">马化腾</td>
					</tr>
					<tr>
						<td style="color:#999999; text-indent:5px; font-size:10px;">马化腾：给你全套lol皮肤..</td>
					</tr>
				</table>
			</div>
		</div>

	</div>


	<!-- chat -->
	<div id="chat" style="width:529px; height:667px; background-color:#F5F5F5; float:right;">
		<div style="width:16px; height:16px; background-image:url(res/img/exit.png); background-repeat:no-repeat; float:right; margin-top:10px; margin-right:30px;"></div>
		<div style="width:16px; height:16px; background-image:url(res/img/min.png); background-repeat:no-repeat; float:right; margin-top:10px; margin-right:12px;"></div>
		<div style="border-bottom:1px #E7E7E7 solid;width:509px; padding-top:0px; padding-left:20px; padding-bottom:10px; font-size:18px; font-weight:bolder;float:left;">
			杭州负翁群（999）
		</div>

		<!-- 会话区域 begin -->
		<div id="show" style="width:529px; height:450px; float:left;overflow-y:scroll;">

			<!-- 消息块；系统 -->
			<div class="msgBlockSystem" style="margin-left:30px; margin-top:15px; width:340px; height:auto; margin-bottom:15px; float:left;">
				<div class="msgBlock_userHeadImg" style="float:left; width:35px; height:35px;border-radius:3px;-moz-border-radius:3px; background-color:#FFFFFF;">
					<img class="point" src="res/img/head0.jpg" width="35px" height="35px" style="border-radius:3px;-moz-border-radius:3px;"/>
				</div>

				<div class="msgBlock_channelId" style="float:left; width:100px; margin-top:-5px; margin-left:10px; padding-bottom:2px; font-size:10px;">
					欠债王
				</div>

				<div class="msgBlock_msgInfo" style="height:auto;width:280px;float:left;margin-left:12px; margin-top:4px;border-radius:3px;-moz-border-radius:3px; ">
					<div class="point" style="float:left;width:260px; padding:7px; background-color:#FFFFFF; border-radius:3px;-moz-border-radius:3px; height:auto; font-size:12px;display:block;word-break: break-all;word-wrap: break-word;">
						出来玩玩，牛牛、金花、跑得快
					</div>
				</div>
			</div>

			<!-- 消息块；好友 -->
			<div class="msgBlockFriendClone" style=" display:none; margin-left:30px; margin-top:15px; width:340px; height:auto; margin-bottom:15px; float:left;">
				<div class="msgBlock_userHeadImg" style="float:left; width:35px; height:35px;border-radius:3px;-moz-border-radius:3px; background-color:#FFFFFF;">
					<img class="headPoint" src="res/img/head5.jpg" width="35px" height="35px" style="border-radius:3px;-moz-border-radius:3px;"/>
				</div>

				<div class="msgBlock_channelId" style="float:left; width:100px; margin-top:-5px; margin-left:10px; padding-bottom:2px; font-size:10px;">
					<!-- 名称 -->
				</div>

				<div class="msgBlock_msgInfo" style="height:auto;width:280px;float:left;margin-left:12px; margin-top:4px;border-radius:3px;-moz-border-radius:3px; ">
					<div style="width:4px; height:20px; background-color:#CC0000; float:left;border-radius:3px;-moz-border-radius:3px;"></div>
					<div class="msgPoint" style="float:left;width:260px; padding:7px; background-color:#FFFFFF; border-radius:3px;-moz-border-radius:3px; height:auto; font-size:12px;display:block;word-break: break-all;word-wrap: break-word;">
						<!-- 信息 -->
					</div>
				</div>
			</div>

			<!-- 消息块；自己 -->
			<div class="msgBlockOwnerClone" style=" display:none; margin-right:30px; margin-top:15px; width:340px; height:auto; margin-bottom:15px; float:right;">

				<div style="float:right; width:35px; height:35px;border-radius:3px;-moz-border-radius:3px; background-color:#FFFFFF;">
					<img class="headPoint" src="res/img/head3.jpg" width="35px" height="35px" style="border-radius:3px;-moz-border-radius:3px;"/>
				</div>

				<div class="msgBlock_msgInfo" style="height:auto;width:280px;float:left;margin-left:12px; margin-top:4px;border-radius:3px;-moz-border-radius:3px; ">
					<div class="msgPoint" style="float:left;width:260px; padding:7px; background-color:#FFFFFF; border-radius:3px;-moz-border-radius:3px; height:auto; font-size:12px;display:block;word-break: break-all;word-wrap: break-word;">
						<!-- 信息 -->
					</div>
					<div style="width:4px; height:20px; background-color:#CC0000; float:right;border-radius:3px;-moz-border-radius:3px;"></div>
				</div>

			</div>

			<span id="msgPoint"></span>

		</div>
		<!-- 会话区域 end -->
		<div style="width:100%; height:2px; float:left; background-color:#CCCCCC;"></div>
		<div style="margin:0 auto; width:100%; height:149px; margin-top:5px;  background-color:#FFFFFF; float:left;">
			<textarea id="sendBox" style="font-size:14px; border:0; width:499px; height:80px; outline:none; padding:15px;font-family:”微软雅黑”;resize: none;"></textarea>

			<div style="margin-top:20px; float:right; margin-right:35px; padding:5px; padding-left:15px; padding-right:15px; font-size:12px; background-color:#F5F5F5;border-radius:3px;-moz-border-radius:3px; cursor:pointer;" onclick="javascript:util.send();">发送(S)</div>
		</div>
	</div>

</div>


</body>
</html>