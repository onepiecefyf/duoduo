<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.title{font-size: 1em;font-weight: bold;color: blue;}
	.condition{height: 15px;}
	.textbox{height: 20px;margin-bottom: 10px;}
	#status{border: 1px solid #95B8E7;background-color: #fff;font-size: 12px;font-weight: normal;}
	#btn{text-decoration: none;width: 300px;height: 25px;margin-top:30px;
	font-size: 1.2em;border: 1px solid #95B8E7;background-color:yellow;}
</style>
<table class="easyui-datagrid" id="orderList" title="订单列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/order/list',method:'get',pageSize:30,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'orderId',width:60">订单ID</th>
            <th data-options="field:'payment',width:60">实付金额</th>
            <th data-options="field:'paymentType',width:80,formatter:TAOTAO.formatPayType">支付类型</th>
            <th data-options="field:'shippingName',width:100,align:'center'">物流名称</th>
            <th data-options="field:'buyerNick',width:100,align:'center'">买家昵称</th>
            <th data-options="field:'postFee',width:60,formatter:TAOTAO.formatPostFee">邮费(元)</th>
            <th data-options="field:'status',width:50,align:'right',formatter:TAOTAO.formatOrderStatus">状态</th>
            <th data-options="field:'createTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updateTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
            <th data-options="field:'paymentTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">支付日期</th>
            <th data-options="field:'consignTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">发货日期</th>
            <th data-options="field:'endTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">交易完成日期</th>
            <th data-options="field:'closeTime',width:130,align:'center',formatter:TAOTAO.formatDateTime">交易关闭日期</th>
        </tr>
    </thead>
</table>
<div id="orderEditWindow" class="easyui-window" title="编辑订单" 
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/order/page/order-edit'" 
	style="width:80%;height:80%;padding:10px;">
</div>
<!-- 查询定区 -->
<div class="easyui-window" title="查询订单窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
	<div style="overflow:auto;padding:5px;" border="false">
		<form id="searchForm">
			<table class="table-edit" width="80%" align="center">
				<tr class="title">
					<td colspan="2">查询条件</td>
				</tr>
				<tr class="condition">
					<td>订单ID</td>
					<td>
						<input class="textbox" type="text" name="orderId" class="easyui-validatebox" required="true" />
					</td>
				</tr>
				<tr  class="condition">
					<td>支付类型</td>
					<td>
						<input class="textbox" type="text" name="paymentType" class="easyui-validatebox" required="true" />
					</td>
				</tr>
				<tr>
					<td>物流名称</td>
					<td>
						<input class="textbox" type="text" name="shippingName" class="easyui-validatebox" required="true" />
					</td>
				</tr>
				<tr>
					<td>买家昵称</td>
					<td>
						<input class="textbox" type="text" name="buyerNick" class="easyui-validatebox" required="true" />
					</td>
				</tr>
				<tr>
					<td>状态</td>
					<td>
						<select id="status" name="status" class="combobox-group" style="width: 160px;">
							<option value="-1" selected="selected">--全部--</option>
		            		<option value="1">未付款</option>
		            		<option value="2">已付款</option>
		            		<option value="3">未发货</option>
		            		<option value="4">已发货</option>
		            		<option value="5">交易成功</option>
		            		<option value="6">交易关闭</option>
		            	</select>
					</td>
				</tr>
				<tr>
					<td colspan="2"><button id="btn" href="#" class="easyui-linkbutton">查       询</button> </td>
				</tr>
			</table>
		</form>
	</div>
</div>
<script>
    function getSelectionsIds(){
    	// 获得table对象
    	var orderList = $("#orderList");
    	// 获取选中的行数
    	var sels = orderList.datagrid("getSelections");
    	// 定义数组，存储选中的商品的id
    	var ids = [];
    	for(var i in sels){
    		// 将选中的订单的id，存入到数组
    		ids.push(sels[i].orderId);
    	}
    	// 将商品的id以,拼接
    	ids = ids.join(",");
    	return ids;
    };
    
 	// 查询订单
	$('#searchWindow').window({
        title: '查询订单',
        width: 400,
        modal: true,
        shadow: true,
        closed: true,
        height: 400,
        resizable:false
    });
 	
	// 点击查询按钮，将form数据转换为json 绑定datagrid 
	$("#btn").click(function(){
		// 转换form对象 到 json
		var params = $("#searchForm").serialize();
		// 绑定datagrid 
		$("#orderList").datagrid('load',params);
		// 关闭查询窗口 
		$("#searchWindow").window('close');
	});
    
    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	TAOTAO.createWindow({
        		url : "/order-add",
        	});
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	// 获得选中的订单的ids数组
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个订单才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个订单!');
        		return ;
        	}
        	
        	$("#orderEditWindow").window({
        		onLoad :function(){
        			//获得当前选中的行 的   回显数据
        			var data = $("#orderList").datagrid("getSelections")[0];
        			
        			$("#orderEditWindow").form("load",data);
        		}
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	// 获得选中的订单的ids数组
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中订单!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的订单吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/rest/order/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除订单成功!',undefined,function(){
            					$("#orderList").datagrid("reload");
            				});
            			}else{
            				$.messager.alert('提示','删除订单失败!',undefined,function(){
            					$("#orderList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>