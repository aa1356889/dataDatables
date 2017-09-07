(function() {
	var member = {
		render : function(data, type, full, meta) {
			return "<span title='" + data + "'>" + data + "</span>"
		},
	};
	$.fn.initAsynTable = function(options) {
		var th=this;
		options.columns.forEach(function(c) {
			if (!c.render) {
				c.render = member.render;

			}

		});
		var $loadingContainer=$(this).parent();
		var table = $(this).DataTable({
			scrollY : false,
			"scrollX" : true,
			autoWidth : false, // 禁用自动调整列宽
			"dom" : 'rt<"bottom"ilp<"clear">>',
			"pagingType" : "full_numbers",
			"orderClasses" : true,
			"ordering" : false,
			"serverSide" : true,
			"columns" : options.columns,
			language : {
				"sProcessing" : "处理中...",
				"sLengthMenu" : "显示 _MENU_ 项结果",
				"sZeroRecords" : "没有匹配结果",
				"sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				"sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
				"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
				"sInfoPostFix" : "",
				"sSearch" : "搜索:",
				"sUrl" : "",
				"sEmptyTable" : "表中数据为空",
				"sLoadingRecords" : "载入中...",
				"sInfoThousands" : ",",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "上页",
					"sNext" : "下页",
					"sLast" : "末页",
					"go" : "跳转"

				},
				"oAria" : {
					"sSortAscending" : ": 以升序排列此列",
					"sSortDescending" : ": 以降序排列此列"
				}
			},
			/*"ajax" : {
				"url" : options.url,
				"type" : "POST",
				"data" : options.getAPostParameter || function(d) {
					return d;
				}

			}*/
			ajax:function(data, callback) {//ajax配置为function,手动调用异步查询
				//手动控制遮罩
		     
				
				//封装请求参数
				var param =  (options.getAjaxParameterCallback&&options.getAjaxParameterCallback(data))||data;
				$loadingContainer.spinModal();
				$.ajax({
			            type: "POST",
			            url: options.url,
			            cache : false,	//禁用缓存
			            dataType: "json",
			            data: param,	//传入已封装的参数
			            success: function(result) {
			            	
			            		//封装返回数据，这里仅演示了修改属性名
			            		var returnData = {};
				            	returnData=result;
				            	//关闭遮罩
				            	$loadingContainer.spinModal(false);
				            	//调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
				            	//此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
				                
				               
				                table.context=[{json:returnData}];

				            	callback(returnData);
			            
			            },
			            error: function(XMLHttpRequest, textStatus, errorThrown) {
			               
			            	$(th).parent().spinModal(false);
			            }
			        });
			}

		});
		return table;
	}
})();