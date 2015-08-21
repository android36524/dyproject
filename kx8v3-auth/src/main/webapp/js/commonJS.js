/**
 * 塔尖的公共JS
 */
var commonJs = (function(){
	return {
		btn_modifycode:"modify",
		btn_delcode:"del",
		btn_addcode:"add",
		btn_name:{"import":"import","export":"export","download":"download","showlist":"showlist","enable":"enable","disable":"disable","manage":"manage","set":"set","arrange":"arrange","graduate":"graduate",
				 "approve":"approve","revoke":"revoke"
		},
		btn_defaultClass:{"modify":["btn btn-xs btn-info tooltip-info","icon-edit bigger-120"],
		                  "del":["btn btn-xs btn-danger tooltip-danger","icon-trash bigger-120"],
		                  "import":["btn btn-xs btn-warning tooltip-warning","icon-import bigger-120"],
		                  "export":["btn btn-xs btn-warning tooltip-warning","icon-export bigger-120"],
		                  "showlist":["btn btn-xs btn-info tooltip-info","icon-list bigger-120"],
		                  "enable":["btn btn-xs btn-success tooltip-success","icon-ok bigger-120"],
		                  "disable":["btn btn-xs btn-danger tooltip-danger","icon-lock bigger-120"],
		                  "manage":["btn btn-xs btn-primary tooltip-primary","icon-cog bigger-120"],
		                  "set":["btn btn-xs btn-primary tooltip-primary","icon-cog bigger-120"],
		                  "graduate":["btn btn-xs btn-primary tooltip-primary","icon-flag bigger-120"],
		                  "arrange":["btn btn-xs btn-primary tooltip-primary","icon-time bigger-120"],
		                  "approve":["btn btn-xs btn-info tooltip-info","icon-edit bigger-120"],
		                  "revoke":["btn btn-xs btn-info tooltip-info","icon-edit bigger-120"]
		
		},
		                  
		/**
		 * 打开图标选择窗体
		 */
		openIcon : function(callback){
			$.get(path + "/common/ico.jsp",function(ret){
				bootbox.dialog({
					title : "选择图标",
					message : ret,
					buttons : {
						success : {
							label: "选择",
	                        className: "btn-success",
	                        callback :  function(result) {
								if(result) {
									if($.isFunction(callback)){
										callback($("#div_selected_ico").attr("icon"));
									}
								}
							}
						},
						cancel : {
							"label" : "取消",
							"class" : "btn",
							"callback" :  function(result) {
								if(result) {
									
								}
							}
						}
					}
				});
			});	
		},
		
		/**
		 * Echart初始化
		 */
		initEchart : function(){
			require.config({
				paths: {
	                echarts: rootPath + '/public/echarts'
	            }
			});
		},
		
		/**
		 * 生成柱状图报表
		 * @param domId 需要展现的元素ID
		 * @param reportData 报表数据
		 * @param dataTitle 标题数组
		 */
		generateBarReport : function(domId,reportData,dataTitle){
			this.initEchart();
			
			dataTitle = dataTitle || [""];
			reportData = reportData || {};
			require([
				'echarts',
				'echarts/chart/bar'
	         ],function(ec){
				//创建短信发送报表
				var barChart = ec.init($("#"+domId)[0]);
				var option = {
					    title : {
					        text: (reportData.title || ""),
					        subtext: (reportData.subTitle || "")
					    },
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:dataTitle,
					        show : true,
					        itemGap : 5
					    },
					    toolbox: {
					        show : false
					    },
					    calculable : false,
					    xAxis : [
					        {
					            type : 'category',
					            data : (reportData.xaxis || []),
					            axisLabel : {
					            	rotate : -90,
					            	margin : 4
					            }
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:dataTitle[0] || "",
					            type:'bar',
					            data:(reportData.data || []),
					            markLine : {
					                data : [
					                    {type : 'average', name: '平均值'}
					                ]
					            }
					        }
					    ]
					};
				
				barChart.setOption(option);
			});
		},
		
		/**
		 * 时间轴样式处理
		 */
		timelineStyle : function(){
			$(".dy-timeline").on("click","a",function(){
				$(this).parent().find("a").removeClass("ding");
				$(this).addClass("ding");
			});
		},
		
		/**
		 * 调用get方法后显示弹出框
		 */
		toUrl:function(title,btlabel,url,callback){
			$.get(url,function(ret){
				if(ret.code=="-1"){
					$.gritter.add({
						title: title,
						text: ret.msg,
						 
						sticky: false,
						time: '',
						class_name: "gritter-light"
					});
					return;
				}
				bootbox.dialog({
					title : title,
					message : ret,
					buttons : {
						success : {
							label: btlabel,
				            className: "btn-success",
				            callback :  callback
						},
						cancel : {
							"label" : "取消",
							"class" : "btn",
							"callback" :  function(result) {
								if(result) {	
								}
							}
						}
					}
				});
			});
		},
		/**
		 * 调用post方法后显示弹出框
		 */
		toPostUrl:function(title,btlabel,url,postData,callback){
			$.post(url,postData,function(ret){
				if(ret.code=="-1"){
					$.gritter.add({
						title: title,
						text: ret.msg,
						sticky: false,
						time: '',
						class_name: "gritter-light"
					});
					return;
				}
				bootbox.dialog({
					title : title,
					message : ret,
					buttons : {
						success : {
							label: btlabel,
				            className: "btn-success",
				            callback :  callback
						},
						cancel : {
							"label" : "取消",
							"class" : "btn",
							"callback" :  function(result) {
								if(result) {	
								}
							}
						}
					}
				});
			});
		},
		
		/**
		 * 打开组织机构树选择框
		 * @param params 需要传入的参数,JS对象参数
		 * @param callback 回调方法（选择组织机构后需要处理的逻辑）
		 */
		showDeptDialog : function(params,callback){
			$.layer({
	            type : 1,
	            shadeClose: false,
	            title: "部门选择",
	            closeBtn: [0, true],
	            btn : ['选择','取消'],
	            shade: [0.8, '#000'],
	            shift : 'top',
	            btns : 2,
	            border: [0],
	            offset: ['20px',''],
	            area: ['400px', '530px'],
	            page : {
	            	url : path + "/common/common-deptchoose.jsp?"+$.param(params || {})
	            },
	            yes : function(index){
	            	var _ret = commondept.getSelected(callback);
	            	if(_ret == 1){
		                layer.close(index);
	            	}
	            }
	        });
		},
		/**
		 * 关闭 bootbox.dialog 窗口
		 * @param Id  表格ID
		 */
		closedialog : function(Id){
			$("#"+Id).parents(".bootbox").prev().remove();
			$("#"+Id).parents(".bootbox").remove();
		},
		
		/**
		 * code  按钮的code
		 * dyid  按钮的dyid
		 * title 按钮title
		 * btnclass 按钮的样式
		 * labelclass label的样式
		 * attr  json格式,可以拼自定义属性 如{gradeId:1,dyname:''}
		 */
		createAuthBtn:function(code,dyid,title,btnclass,labelclass,attr){
			var hasAuth = $("#hasauth_"+code).val();			
			if(hasAuth=="1"){				
				var tempAttr=" ";
				for(var k in attr){				
					tempAttr+=k+'="'+attr[k]+'" ';
				};				
				var s= '<button class="'+btnclass+'" dyid="'+dyid+'" '+tempAttr+' data-rel="tooltip" data-placement="top" title="'+title+'">';
				s+='<i class="'+labelclass+'"></i></button>';
				return s;
			}
			return '';
			btn_defaultClass
		},
		/**
		 * code  按钮的code
		 * dyid  按钮的dyid
		 * title 按钮title
		 * attr  json格式,可以拼自定义属性 如{gradeId:1,dyname:''}
		 */
		createDefaultAuthBtn:function(code,dyid,title,attr){
			var hasAuth = $("#hasauth_"+code).val();			
			if(hasAuth=="1"){				
				var tempAttr=" ";				
				for(var k in attr){					
					tempAttr+=k+'="'+attr[k]+'" ';
				};
				var btnclass= commonJs.btn_defaultClass[code]?commonJs.btn_defaultClass[code]["0"]:"";
				var labelclass= commonJs.btn_defaultClass[code]?commonJs.btn_defaultClass[code]["1"]:"";				
				var s= '<button class="'+btnclass+'" dyid="'+dyid+'" '+tempAttr+' data-rel="tooltip" data-placement="top" title="'+title+'">';
				s+='<i class="'+labelclass+'"></i></button>';
				return s;
			}
			return '';
		}
	};
})();

$(function(){
	commonJs.timelineStyle();
	window.console = window.console || (function(){ 
		var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile 
		= c.clear = c.exception = c.trace = c.assert = function(){}; 
		return c; 
	})();
});



