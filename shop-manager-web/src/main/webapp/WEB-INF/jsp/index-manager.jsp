<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	#importItems{width:auto; margin-top:12px; 
		      font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
		      font-size:11px; 
		      color:#999999;
		      line-height:25px;
		      letter-spacing:1px}
		      
	#deleteAllIndex{width:auto; margin-top:12px; 
			     font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
			      font-size:11px; 
			      color:#999999;
			      line-height:25px;
			      letter-spacing:1px}
</style>
<div>
	<a class="easyui-linkbutton" onclick="importItems()" >一键导入所有商品到索引库</a><br/>
	<div id="importItems">此功能会为所有的商品的<span style="color: red;">添加索引</span>,如果是第一次为大量商品添加索引，会占用较长，请耐心等待！，</div>
</div>
<div style="margin-top: 30px;">
	<a class="easyui-linkbutton" onclick="deleteAllIndex()" >一键删除所有商品到索引</a><br />
	<div id="deleteAllIndex">请慎重考虑！此功能可能会导致所有已经建立索引的数据丢失！但是，并不会丢失<span style="color: red;">本地数据</span></div>
</div>

<div style="padding:0px;">
	<img alt="淘淘" src="imag/index-manager.png">
</div>

<script type="text/javascript">
	function importItems(){
		$.post("/index/import",null, function(data){
			if(data.status == 200){
				$.messager.alert("提示","商品数据导入完成");
			}
		});
	}
	
	function deleteAllIndex(){
		
		$.post("/index/deleteAllIndex",null, function(data){
			if(data.status == 200){
				$.messager.alert("提示","商品数据删除完成");
			}
		});
	}
</script>