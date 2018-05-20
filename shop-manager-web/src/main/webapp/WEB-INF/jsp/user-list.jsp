<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.title{font-size: 1em;font-weight: bold;color: blue;}
	.condition{height: 15px;}
	.textbox{height: 20px;margin-bottom: 10px;}
	#status{border: 1px solid #95B8E7;background-color: #fff;font-size: 12px;font-weight: normal;}
	#btn{text-decoration: none;width: 300px;height: 25px;margin-top:30px;
	font-size: 1.2em;border: 1px solid #95B8E7;background-color:yellow;}
</style>
<table class="easyui-datagrid" id="userList" title="用户列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/user/list',method:'get',pageSize:30,toolbar:toolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:60">用户ID</th>
            <th data-options="field:'username',width:100">用户昵称</th>
            <th data-options="field:'phone',width:100,align:'center'">用户手机</th>
            <th data-options="field:'status',width:100,align:'center',formatter:TAOTAO.formatUserStatus">用户状态</th>
            <th data-options="field:'email',width:130,align:'center'">用户邮箱</th>
            <th data-options="field:'created',width:130,formatter:TAOTAO.formatDateTime">创建时间</th>
            <th data-options="field:'updated',width:130,align:'right',formatter:TAOTAO.formatDateTime">更新时间</th>
        </tr>
    </thead>
</table>
<div id="userEditWindow" class="easyui-window" title="编辑用户" 
	data-options="modal:true,closed:true,iconCls:'icon-save',href:'/user/page/user-edit'" 
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
    	var userList = $("#userList");
    	// 获取选中的行数
    	var sels = userList.datagrid("getSelections");
    	// 定义数组，存储选中的商品的id
    	var ids = [];
    	for(var i in sels){
    		// 将选中的订单的id，存入到数组
    		ids.push(sels[i].id);
    	}
    	// 将商品的id以,拼接
    	ids = ids.join(",");
    	return ids;
    };
    
 	// 查询订单
	$('#searchWindow').window({
        title: '查询用户',
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
        		url : "/user-add",
        	});
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	// 获得选中的订单的ids数组
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个用户才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个用户!');
        		return ;
        	}
        	
        	$("#userEditWindow").window({
        		onLoad :function(){
        			//获得当前选中的行 的   回显数据
        			var data = $("#userList").datagrid("getSelections")[0];
        			
        			console.log(data);
        			
        			$("#userEditWindow").form("load",data);
        		}
        	}).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	// 获得选中的用户的ids数组
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中用户!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的用户吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/rest/user/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除用户成功!',undefined,function(){
            					$("#userList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    },{
        text:'还原',
        iconCls:'icon-back',
        handler:function(){
        	// 获得选中的用户的ids数组
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中用户!');
        		return ;
        	}
        	$.messager.confirm('确认','确定还原ID为 '+ids+' 的用户吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/rest/user/useUser",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','还原用户成功!',undefined,function(){
            					$("#userList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
</script>