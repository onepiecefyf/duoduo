<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table class="easyui-datagrid" id="itemParamList" title="商品分类规格列表" 
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'/itemCat/param/list',method:'get',pageSize:30,toolbar:itemParamListToolbar">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:130">ID</th>
        	<th data-options="field:'itemCatId',width:80">商品类目ID</th>
        	<th data-options="field:'itemCatName',width:100">商品类目</th>
            <th data-options="field:'paramData',width:300,formatter:formatItemParamData">规格(只显示分组名称)</th>
            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
            <th data-options="field:'status',width:50,align:'center',formatter:TAOTAO.formatStatus">状态</th>
        </tr>
    </thead>
</table>
<div id="itemEditWindow" class="easyui-window" title="编辑商品" data-options="modal:true,closed:true,iconCls:'icon-save',href:'/item-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

	// 得到分组的名称并将他们以,号连接
	function formatItemParamData(value , index){
		var json = JSON.parse(value);
		var array = [];
		$.each(json,function(i,e){
			array.push(e.group);
		});
		return array.join(",");
	}

	// 获得所有选中的行数，并将他们以,号连接
    function getSelectionsIds(){
    	var itemList = $("#itemParamList");
    	var sels = itemList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var itemParamListToolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	TAOTAO.createWindow({
        		url : "/itemCat-param-add",
        	});
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	$.messager.alert('提示','该功能未实现!');
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品规格!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品规格吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
        	    	
                	$.post("/itemCat/param/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除商品规格成功!',undefined,function(){
            					$("#itemParamList").datagrid("reload");
            				});
            			}else{
            				$.messager.alert('提示','删除商品分类规格失败哦!',undefined,function(){
            					$("#itemParamList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    },{
	    text:'使用',
	    iconCls:'icon-add',
	    handler:function(){
	    	var ids = getSelectionsIds();
	    	if(ids.length == 0){
	    		$.messager.alert('提示','未选中商品规格!');
	    		return ;
	    	}
	    	$.messager.confirm('确认','确定还原ID为 '+ids+' 的商品规格吗？',function(r){
	    	    if (r){
	    	    	var params = {"ids":ids};
	    	    	
	            	$.post("/itemCat/param/useItemCatParam",params, function(data){
	        			if(data.status == 200){
	        				$.messager.alert('提示','还原商品规格成功!',undefined,function(){
	        					$("#itemParamList").datagrid("reload");
	        				});
	        			}else{
	        				$.messager.alert('提示','还原商品分类规格失败哦!',undefined,function(){
	        					$("#itemParamList").datagrid("reload");
	        				});
	        			}
	        		});
	    	    }
	    	});
	    }
	}];
</script>