<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div style="padding:10px 10px 10px 10px">
	<form id="userAddForm" class="userForm" method="post">
	<input type="hidden" id="id" name="id"/>
	    <table>
	        <tr>
	            <td>用户昵称:</td>
	            <td class="usercontent">
	            	<input class="easyui-textbox" type="text" name="username" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	        	<td>用户密码:</td>
	            <td class="ordercontent">
	            	<input class="easyui-textbox" id="password" type="password" name="password" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	        	<td>确认密码:</td>
	            <td class="ordercontent">
	            	<input class="easyui-textbox" id="qpassword" type="password" name="password" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>用户邮箱:</td>
	            <td>
	            	<input class="easyui-textbox" id="email" type="text" name="email" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	        	<td>用户电话:</td>
	            <td>
	            	<input class="easyui-numberbox" id="phone" type="text" name="phone" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	            
	        </tr>
	        <tr>
	        	<td>用户状态:</td>
	            <td>
	       			<select id="status" name="status" class="easyui-combobox" style="width: 160px;">
	            		<option value="0" selected="selected">正常</option>
	            		<option value="1">删除</option>
	            	</select>
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

	

	function checkPassword(){
		//取得密码框的值
		var password = $("#password").val();
		// 取得确认密码狂的值
		var qpassword = $("#qpassword").val();
		if(password == qpassword){
			return true;
		}else{
			return false;
		}
	}

	//提交表单
	function submitForm(){
		// 获得手机号码
		var phone = $("#phone").val();
		// 获得邮箱
		var email = $("#email").val();
		//有效性验证
		if(!$('#userAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		if(!checkPassword()){
			$.messager.alert('提示','两次密码不一致!');
			return ;
		}
		
		if(!isValidEmail(email)){
			$.messager.alert('提示','邮箱格式错误!');
			return ;
		}
		if(!isValidMobile(phone)){
			$.messager.alert('提示','手机号码格式错误!');
			return ;
		}
		
		console.log($("#userAddForm").serialize());
		$.post("/user/updateUser",$("#userAddForm").serialize(), function(data){
			if(data.status == 200){
				$.messager.confirm('提示','修改用户成功!',function(result){
					if(result){
						// 刷新页面
						window.location.reload();
					}
				});
			}else{
				$.messager.confirm('提示','修改用户失败哦!',function(result){
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
	
	// 校验邮箱
	function isValidEmail(email){
		var regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if(regEmail.test(email))
			return true;
		return false;
	}
	
	// 校验手机
	function isValidMobile(b) {
		return isValidChinaMobileNum(b) || IsValidChinaUnicomMobileNum(b) || IsValidChinaTelecomMobileNum(b);
	}

	function isValidChinaMobileNum(b) {
		if(/(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\d{8}$)|(^1705\d{7})$/.test(b))
			return true;
		return false;
	}

	function IsValidChinaUnicomMobileNum(b) {
		if(/(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\d{8}$)|(^1709\d{7})$/.test(b))
			return true;
		return false;
	}

	function IsValidChinaTelecomMobileNum(b) {
		if(/(^1(33|53|77|8[019])\d{8}$)|(^1700\d{7})$/.test(b))
			return true;
		return false;
	}
</script>
