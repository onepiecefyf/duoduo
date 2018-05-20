<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
	<form id="orderAddForm" class="orderForm" method="post">
	<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
	    <table>
	        <tr>
	            <td>买家昵称:</td>
	            <td class="ordercontent">
	            	<input class="easyui-textbox" id="buyerNick" type="text" name="buyerNick" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	        	<td>物流名称:</td>
	            <td class="ordercontent">
	            	<input class="easyui-textbox" id="shippingName" type="text" name="shippingName" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>实付金额:</td>
	            <td>
	            	<input class="easyui-numberbox" id="payment" type="text" name="payment" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	        	<td>邮费:</td>
	            <td>
	            	<input class="easyui-numberbox" id="postFee" type="text" name="postFee" style="width: 240px;"></input>
	            </td>
	        </tr>
	        <tr>
	            
	        </tr>
	        <tr>
	        	<td>支付类型:</td>
	            <td>
	       			<select id="paymentType" name="paymentType" class="easyui-combobox" style="width: 160px;">
	            		<option value="1" selected="selected">在线支付</option>
	            		<option value="2">货到付款</option>
	            	</select>
	            </td>
	        </tr>
	        <tr>
	            <td>订单状态:</td>
	            <td>
	          	    <select id="status" name="status" class="easyui-combobox" style="width: 160px;">
	            		<option value="1" selected="selected">未付款</option>
	            		<option value="2">已付款</option>
	            		<option value="3">未发货</option>
	            		<option value="4">已发货</option>
	            		<option value="5">交易成功</option>
	            		<option value="6">交易关闭</option>
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
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#orderAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//获取邮费
		var postFee = $("#postFee").val();
		// 获取实付金额
		var payment = $("#payment").val();
		// 进行校验
		if(isNullOrEmpty(postFee)){
			$.messager.alert('提示','邮费不能为空，最少为0!');
			return ;
		}
		if(isNullOrEmpty(payment)){
			$.messager.alert('提示','实付金额不能为空，最少为0!');
			return ;
		}
		if(!isNullOrEmpty(postFee)){
			if(!isDigit(postFee)){
				$.messager.alert('提示','邮费只能是数字!');
				return ;
			}
		}
		if(!isNullOrEmpty(payment)){
			if(!isDigit(payment)){
				$.messager.alert('提示','实付金额只能是数字!');
				return ;
			}
		}
		$.post("/order/updateOrder",$('#orderAddForm').serialize(), function(data){
			if(data.status == 200){
				$.messager.confirm('提示','修改订单成功!',function(result){
					if(result){
						// 刷新页面
						window.location.reload();
					}
				});
			}else{
				$.messager.confirm('提示','修改订单成功!',function(result){
					if(result){
						// 刷新页面
						window.location.reload();
					}
				});
			}
		});
	}
	function isNullOrEmpty(val) {
		return (val == null || val == undefined || val == "" || val == "undefined");
	}
	function isDigit(digit){
		var regDigit = /^[0-9]*$/;
		if(regDigit.test(digit))
			return true;
		return false;
	}
	function clearForm(){
		$('#orderAddForm').form('reset');
	}
</script>
