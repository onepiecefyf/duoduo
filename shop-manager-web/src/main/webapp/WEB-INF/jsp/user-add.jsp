<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
	<form id="userAddForm" class="userForm" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>用户昵称:</td>
	            <td>
	            	<input class="easyui-textbox" type="text" name="username" style="width: 280px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>用户密码:</td>
	            <td>
	            	<input class="easyui-textbox" type="password" name="password" style="width: 280px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>用户电话:</td>
	            <td>
	            	<input class="easyui-numberbox" type="text" name="phone" style="width: 280px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>用户邮箱:</td>
	            <td>
	            	<input class="easyui-numberbox" type="text" name="email" style="width: 280px;"></input>
	            </td>
	        </tr>
	    </table>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#userAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}

		$.post("/user/save",$("#userAddForm").serialize(), function(data){
			if(data.status == 200){
				$.messager.confirm('提示','新增用户成功!',function(result){
					if(result){
						// 刷新页面
						window.location.reload();
					}
				});
			}else{
				$.messager.confirm('提示','新增用户失败!',function(result){
					if(result){
						// 刷新页面
						window.location.reload();
					}
				});
			}
		});
	}
	
	function clearForm(){
		$('#userAddForm').form('reset');
	}
</script>
