<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/include-path.jsp"%>
<%@include file="/common/include-bace.jsp"%>
<title>大洋基础平台管理后台登陆</title>
</head>
<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="icon-leaf green"></i> <span class="red">大洋基础平台</span> <span
									class="white">管理后台</span>
							</h1>
							<h4 class="blue">&copy; 湖南大洋软件</h4>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box"
								class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="icon-coffee green"></i> 请输入登陆信息
										</h4>

										<div class="space-6"></div>

										<form action="<%=path %>/admin/main" method="post">
										<fieldset>
											<label class="block clearfix">
											<span class="block input-icon input-icon-right">
												<input type="text" name="username" class="form-control" placeholder="用户名" /> 
												<i class="icon-user"></i>
											</span>
											</label>
											<label class="block clearfix">
												<span class="block input-icon input-icon-right"> 
												<input type="password" class="form-control" placeholder="登陆密码" />
												<i class="icon-lock"></i>
											</span>
											</label>
											<div class="space"></div>

											<div class="clearfix">
												<label class="inline"> <input type="checkbox" class="ace"/> <span
													class="lbl"> 记录密码</span>
												</label>

												<button type="submit"
													class="width-35 pull-right btn btn-sm btn-primary">
													<i class="icon-key"></i> 登陆
												</button>
											</div>

											<div class="space-4"></div>
										</fieldset>
										</form>

										<!-- <div class="social-or-login center">
											<span class="bigger-110">Or Login Using</span>
										</div>

										<div class="social-login center">
											<a class="btn btn-primary"> <i class="icon-facebook"></i>
											</a> <a class="btn btn-info"> <i class="icon-twitter"></i>
											</a> <a class="btn btn-danger"> <i class="icon-google-plus"></i>
											</a>
										</div> -->
									</div>
									<!--/widget-main-->

									<div class="toolbar clearfix">
										<div>
											<a href="#" onclick="show_box('forgot-box'); return false;"
												class="forgot-password-link"> <i
												class="icon-arrow-left"></i> 忘记密码？
											</a>
										</div>

										<div>
											<a href="#" onclick="show_box('signup-box'); return false;"
												class="user-signup-link"> 用户注册 <i
												class="icon-arrow-right"></i>
											</a>
										</div>
									</div>
								</div>
								<!--/widget-body-->
							</div>
							<!--/login-box-->

							<div id="forgot-box" class="forgot-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header red lighter bigger">
											<i class="icon-key"></i> 找回密码
										</h4>

										<div class="space-6"></div>
										<p>请输入你的注册邮箱</p>

										<form />
										<fieldset>
											<label class="block clearfix"> 
												<span class="block input-icon input-icon-right"> <input
														type="email" class="form-control" placeholder="请输入邮箱地址" /> <i
														class="icon-envelope"></i>
												</span>
											</label>

											<div class="clearfix">
												<button onclick="return false;"
													class="width-35 pull-right btn btn-sm btn-danger">
													<i class="icon-lightbulb"></i> 发送
												</button>
											</div>
										</fieldset>
										</form>
									</div>
									<!--/widget-main-->

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> 返回登陆 <i
											class="icon-arrow-right"></i>
										</a>
									</div>
								</div>
								<!--/widget-body-->
							</div>
							<!--/forgot-box-->

							<div id="signup-box" class="signup-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="icon-group blue"></i> 新用户注册
										</h4>

										<div class="space-6"></div>
										<p>请输入注册详细信息:</p>

										<form />
										<fieldset>
											<label class="block clearfix"> 
												<span class="block input-icon input-icon-right"> <input
													type="email" class="form-control" placeholder="邮箱地址" /> <i
													class="icon-envelope"></i>
												</span>
											</label> 
											<label class="block clearfix"> 
												<span class="block input-icon input-icon-right"> <input
													type="text" class="form-control" placeholder="用户名" /> <i
													class="icon-user"></i>
											</span>
											</label  class="block clearfix"> 
											<label  class="block clearfix"> 
												<span class="block input-icon input-icon-right"> <input
													type="password" class="form-control" placeholder="登陆密码" />
													<i class="icon-lock"></i>
											</span>
											</label> 
											<label  class="block clearfix"> 
												<span class="block input-icon input-icon-right"> <input
													type="password" class="form-control"
													placeholder="再次输入密码" /> <i class="icon-retweet"></i>
											</span>
											</label> 
											<label  class="block clearfix"> <input type="checkbox" class="ace" /> <span
												class="lbl"> 我接受 <a href="#">用户协议</a>
											</span>
											</label>

											<div class="space-24"></div>

											<div class="clearfix">
												<button type="reset"
													class="width-30 pull-left btn btn-sm">
													<i class="icon-refresh"></i> 重置
												</button>

												<button onclick="return false;"
													class="width-65 pull-right btn btn-sm btn-success">
													注册 <i class="icon-arrow-right icon-on-right"></i>
												</button>
											</div>
										</fieldset>
										</form>
									</div>

									<div class="toolbar center">
										<a href="#" onclick="show_box('login-box'); return false;"
											class="back-to-login-link"> <i class="icon-arrow-left"></i>
											返回登陆
										</a>
									</div>
								</div>
								<!--/widget-body-->
							</div>
							<!--/signup-box-->
						</div>
						<!--/position-relative-->
					</div>
				</div>
				<!--/.span-->
			</div>
			<!--/.row-fluid-->
		</div>
	</div>
	<script type="text/javascript">
		function show_box(id) {
			$('.widget-box.visible').removeClass('visible');
			$('#'+id).addClass('visible');
		}
	</script>
</html>