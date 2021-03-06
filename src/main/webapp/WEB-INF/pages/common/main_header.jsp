<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <header class="main-header">

        <!-- Logo -->
        <a href="" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>E</b>MS</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>养殖场温度监测系统</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <!-- Navbar Right Menu -->
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
            
               <!-- Notifications: style can be found in dropdown.less -->
              <li class="dropdown notifications-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-bell-o"></i>
                  <span class="label label-warning noReadNoticeCount" ></span><!-- 未读通知数 -->
                </a>
                <ul class="dropdown-menu">
                  <li class="header" id="noReadNoticeHeader">没有通知</li>
                  <li>
                    <!-- inner menu: contains the actual data -->
                    <ul class="menu" id="noReadNoticeListMenu">
                                       
                    </ul>
                  </li>
                  <li class="footer"><a id="noticeDetail" href="<%=request.getContextPath()%>/notice/noRead">查看详情</a></li>
                </ul>
              </li>
              
              <!-- User Account Menu -->
              <li class="dropdown user user-menu">
                <!-- Menu Toggle Button -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <!-- The user image in the navbar-->
                  <img src="<%=request.getContextPath()%>/resources/AdminLTE/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                  <!-- hidden-xs hides the username on small devices so only the image appears. -->
                  <span class="hidden-xs">${currentUser.userAlias}</span>
                </a>
                <ul class="dropdown-menu">
                  <!-- The user image in the menu -->
                  <li class="user-header">
                    <img src="<%=request.getContextPath()%>/resources/AdminLTE/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                    <p>
                     <strong>${currentUser.userAlias}</strong> 
                      <small>上次登录时间:<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${currentUser.lastLoginTime}"></fmt:formatDate></small>
                    </p>
                  </li>
                  <!-- Menu Body -->
                  
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a id="profile" href="user/profile" class="btn btn-default btn-flat">个人资料</a>
                    </div>
                    <div class="pull-right">
                      <a href="logout" class="btn btn-default btn-flat">注销</a>
                    </div>
                  </li>
                </ul>
              </li>
              <!-- Control Sidebar Toggle Button -->
          <!--     <li>
                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
              </li> -->
            </ul>
          </div>
        </nav>
      </header>