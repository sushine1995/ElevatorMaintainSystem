<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
<title>养殖场温度监测系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/pages/common/default_header.jsp"%>
<%@ include file="/WEB-INF/pages/common/default_css.jsp"%>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<!--
  BODY TAG OPTIONS:
  =================
  Apply one or more of the following classes to get the
  desired effect
  |---------------------------------------------------------|
  | SKINS         | skin-blue                               |
  |               | skin-black                              |
  |               | skin-purple                             |
  |               | skin-yellow                             |
  |               | skin-red                                |
  |               | skin-green                              |
  |---------------------------------------------------------|
  |LAYOUT OPTIONS | fixed                                   |
  |               | layout-boxed                            |
  |               | layout-top-nav                          |
  |               | sidebar-collapse                        |
  |               | sidebar-mini                            |
  |---------------------------------------------------------|
  -->
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Main Header -->
		<%@include file="/WEB-INF/pages/common/main_header.jsp"%>
		<!-- Left side column. contains the logo and sidebar -->
		<%@include file="/WEB-INF/pages/common/left_sidebar.jsp"%>

		<!-- 内容区域 -->
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper" id="content-wrapper">
			<%@include file="/WEB-INF/pages/common/main_content.jsp"%><!-- default content -->
			<%-- <%@include file="/WEB-INF/pages/example/simpleTable.jsp"%> --%>
		</div>
		<!-- /.content-wrapper -->

		<!-- Main Footer -->
		<%@include file="/WEB-INF/pages/common/main_footer.jsp"%>

		<!-- Control Sidebar -->
		<%-- 		<%@include file="/WEB-INF/pages/common/control_sidebar.jsp"%> --%>
		<!-- /.control-sidebar -->
		<!-- control-sidebar的背景设置  Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
		<!-- <div class="control-sidebar-bg"></div> -->
	</div>
	<!-- ./wrapper -->

	<!-- REQUIRED JS SCRIPTS -->
	<%@ include file="/WEB-INF/pages/common/default_js.jsp"%>

	<!-- Optionally, you can add Slimscroll and FastClick plugins.
         Both of these plugins are recommended to enhance the
         user experience. Slimscroll is required when using the
         fixed layout. -->
	<script type="text/javascript">
	  var stompClient = null;      
          
      function connect() {
          var socket = new SockJS('/ElevatorMaintainSystem/ws');//建立一个websocket连接，服务器要有对应的处理方法
          stompClient = Stomp.over(socket);//在websocket上建立stomp客户端            
          stompClient.connect({}, function(frame) {//连接stomp服务端
              
              console.log('Connected: ' + frame);
              //订阅即用于接收数据
              //var mysubid = '...';默认系统会生成一个唯一的id
			 //var subscription = client.subscribe(destination, callback, { id: mysubid });
              //这个订阅的url,再后台转发给broker的时候也要这样发，@subscribeMapping则只需要、topic/connect
              stompClient.subscribe('/app/notice/count', function(frame){//订阅服务端的“/topic/greetings”
              var result=JSON.parse(frame.body);             
              var count=result.noReadCount;
              var notices=result.noReadNotices;
              $(".noReadNoticeCount").text(count);
              $("#noReadNoticeHeader").text("你有"+count+"条通知");
              for(var notice in notices){
            	  $("#noReadNoticeListMenu").append('<li><a href="#"><i class="fa fa-warning text-red"></i>'
            			  +notices[notice].content+'</a></li>');
            	            	  
              }
             
              });
              stompClient.subscribe('/notice/new',function(frame){
            	  alert("您有一条新的通知");
              });
              stompClient.subscribe('/notice/count',function(frame){
            	  var result=JSON.parse(frame.body);             
                  var count=result.noReadCount;
                  var notices=result.noReadNotices;
            	  $("#noReadNoticeListMenu").empty();
            	  if(count==0){
            		  $(".noReadNoticeCount").text("");
            		  $("#noReadNoticeHeader").text("没有通知");
            	  }else{
                	//  $("#noticeCount").text(count);
                	  $(".noReadNoticeCount").text(count);
                	  $("#noReadNoticeHeader").text("你有"+count+"条通知");
                	  for(var notice in notices){
                    	  $("#noReadNoticeListMenu").append('<li><a href="#"><i class="fa fa-warning text-red"></i>'
                    			  +notices[notice].content+'</a></li>');
                    	            	  
                      } 
            	  }
            	  
              });
          });
         
      }
      
      function disconnect() {
          if (stompClient != null) {
              stompClient.disconnect();
          }        
          console.log("Disconnected");
      }
    /*   
      function sendName() {
          var name = document.getElementById('name').value;
          //客户端向服务端发送消息,第一个参数是url,第二个参数是headers(JSON格式),第三个是body(String类型)
          //client.send("/queue/test", {priority: 9}, "Hello, STOMP");
          //If you want to send a message with a body, you must also pass the headers argument. 
          //If you have no headers to pass, use an empty JavaScript literal {}:
          stompClient.send("/ElevatorMaintainSystem/hello", {}, JSON.stringify({ 'name': name }));
      } */
           	
         $(document).ready(function(){
        	 connect();
        	/*   setTimeout(function(){
            	  stompClient.send("/app/notice/content", {}, "name:vino");
              },3000); */
        	 $(".sidebarMenuHref").click(function(){
        		 
        		 $.ajax({
                     async : false,
                     cache:false,
                     type: 'GET',
                     //    contentType : 'application/json',  //发送信息至服务器时内容编码类型
                     //dataType : "json",
                     url: this.href,//请求的action路径
                     error: function () {//请求失败处理函数
                         alert('请求失败');
                     },
                     success:function(data){ //请求成功后处理函数。
                        // alert(data);
                     $("#content-wrapper").html(data);
                     }
        	 });
        	 return false;       		
          	});
          	});
         
         $(document).ready(function(){
        	 $("#profile,#noticeDetail").click(function(){
        		 
        		 $.ajax({
                     async : false,
                     cache:false,
                     type: 'GET',
                     //    contentType : 'application/json',  //发送信息至服务器时内容编码类型
                     //dataType : "json",
                     url: this.href,//请求的action路径
                     error: function () {//请求失败处理函数
                         alert('请求失败');
                     },
                     success:function(data){ //请求成功后处理函数。
                        // alert(data);
                     $("#content-wrapper").html(data);
                     }
        	 });
        	 return false;       		
          	});
          	});
        
         
          	
         </script>
</body>
</html>
