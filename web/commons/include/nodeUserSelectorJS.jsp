<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
		 // 移动行
		 function move(tableId, direct, idx){
			var objTr=$("#"+tableId+">tbody.data>tr");
			if(objTr.length==0) return;
			
			var nodeId = tableId.split('_')[1];
			var curObj = $('#'+nodeId+'_'+idx);
			if(direct=='up'){
				var prevObj=curObj.prev();
				if(prevObj!=null){
					curObj.insertBefore(prevObj);	
				}
			}
			else{
				var nextObj=curObj.next();
				if(nextObj!=null){
					curObj.insertAfter(nextObj);
				}
			}
		};
		
		function selectScript(){
			var objConditionCode=$("#txtScriptData")[0];
			ScriptDialog({callback:function(script){
				jQuery.insertText(objConditionCode,script);
			}});
		}
	
		function addUserSetRow(rowId,userSetTypeSelectOpt){
			var tbody=$("#table_"+rowId + " tbody.data");
			var rowLen=tbody.children('tr').length;
			var idx = rowLen+1;
			var str="<tr id='"+rowId+"_"+idx+"'>";
				str+="<td nowrap='nowrap' height='28'>";
					str+="<input type='checkbox' name='nodeUserCk'/>&nbsp;";
					str+=idx;
					str+="<input type='hidden' name='nodeUserId'/>";
					str+="<input type='hidden' name='nodeId' value='" + rowId + "'/>";
					str+="<select name='assignUseType'>";
					str+="<option value='0' selected='selected'>参与流程</option>";
					str+="<option value='1'>接收通知</option></select>";
				str+="</td>";
			
				str+="<td>";
					str+="<select name='assignType' class='select' onchange='assignTypeChange(this);'>";
					str+=userSetTypeSelectOpt;
					str+="</select>";
				str+="</td>";
				
				str+="<td>";
					str+="<input type='hidden' name='cmpIds'/><textarea name='cmpNames' readonly='readonly' style='width:80%' rows='2' class='textarea'></textarea>";
					str+="&nbsp;<a class='button' onclick='selectCmp(this);'><span>选择...</span></a>";
				str+="</td>";
				
				str+="<td>";
				str+="<a id='moveupField' class='link moveup' onclick='move(\"table_"+rowId+"\",\"up\",\""+idx+"\")'></a>";
				str+="<a id='movedownField' class='link movedown' onclick='move(\"table_"+rowId+"\",\"down\",\""+idx+"\")'></a>";
				str+="</td>";
				
				str+="<td>";
				str+="<select name='compType'>";
				str+="<option value='0'>或运算</option>";
				str+="<option value='1'>与运算</option>";
				str+="<option value='2'>排除运算</option>";
				str+"</select>";
				str+="</td>";
				
			str+="</tr>";
			tbody.last().append(str);
		};
		
		function addRow(rowId){
			
			var tbody=$("#table_"+rowId + " tbody.data");
			var rowLen=tbody.children('tr').length;
			var idx = rowLen+1;
			var str="<tr id='"+rowId+"_"+idx+"'>";
				str+="<td nowrap='nowrap' height='28'>";
					str+="<input type='checkbox' name='nodeUserCk'/>&nbsp;";
					str+=idx;
					str+="<input type='hidden' name='nodeUserId'/>";
					str+="<input type='hidden' name='nodeId' value='" + rowId + "'/>";
					str+="<select name='assignUseType'>";
					str+="<option value='0' selected='selected'>参与流程</option>";
					str+="<option value='1'>接收通知</option></select>";
				str+="</td>";
			
				str+="<td>";
					str+="<select name='assignType' class='select' onchange='assignTypeChange(this);'>";
					str+="<option value='0'>发起人</option>";
					str+="<option value='1' selected='selected'>用户</option>";
					str+="<option value='2'>角色</option>";
					str+="<option value='3'>组织</option>";
					str+="<option value='4'>组织负责人</option>";
					str+="<option value='5'>岗位</option>";
					str+="<option value='6'>上下级</option>";
					str+="<option value='7'>用户属性</option>";
					str+="<option value='8'>组织属性</option>";
					str+="<option value='9'>与发起人相同部门</option>";
					str+="<option value='10'>与其他节点相同执行人</option>";
					str+="<option value='11'>发起人的直属领导(组织)</option>";
					str+="<option value='13'>上个任务执行人的直属领导(组织)</option>";
					str+="<option value='14'>发起人的领导</option>";
					str+="<option value='15'>上个任务执行人的领导</option>";
					str+="<option value='16'>部门的上级类型部门的负责人</option>";
					str+="<option value='12'>脚本</option>";
					str+="</select>";
				str+="</td>";
				
				str+="<td>";
					str+="<input type='hidden' name='cmpIds'/><textarea name='cmpNames' readonly='readonly' style='width:80%' rows='2' class='textarea'></textarea>";
					str+="&nbsp;<a class='button' onclick='selectCmp(this);'><span>选择...</span></a>";
				str+="</td>";
				
				str+="<td>";
				str+="<a id='moveupField' class='link moveup' onclick='move(\"table_"+rowId+"\",\"up\",\""+idx+"\")'></a>";
				str+="<a id='movedownField' class='link movedown' onclick='move(\"table_"+rowId+"\",\"down\",\""+idx+"\")'></a>";
				str+="</td>";
				
				str+="<td>";
				str+="<select name='compType'>";
				str+="<option value='0'>或运算</option>";
				str+="<option value='1'>与运算</option>";
				str+="<option value='2'>排除运算</option>";
				str+"</select>";
				str+="</td>";
				
			str+="</tr>";
			tbody.last().append(str);
		}
		
		function delRows(rowId){
			var cks=$("#table_"+rowId + " input[name=nodeUserCk]:checked");
			if(cks.length==0){
				$.ligerMsg.info('请选择要删除的行!');
				return ;
			}
			for(var i=cks.length-1;i>=0;i--){
				var nodeUserId=$(cks[i]).siblings("input[name='nodeUserId']").val();
				if(nodeUserId!='' && nodeUserId!=undefined){
					$.post('${ctx}/platform/bpm/bpmDefinition/delBpmNodeUser.ht',{nodeUserId:nodeUserId});
				}
				$(cks[i]).parent().parent().remove();
			}
		}
		
		function selectCmp(obj){
			var cmpIds=$(obj).siblings("input[name='cmpIds']");
			var cmpNames=$(obj).siblings("textarea[name='cmpNames']");
			var assignType=$(obj).parent().prev('td').children('[name="assignType"]').val();

			var nodeUserId=$(obj).parent().prev('td').prev('td').children('[name="nodeUserId"]').val();
			
			var nodeId=$(obj).parent().prev('td').prev('td').children('[name="nodeId"]').val();
			
			var callback=function(ids,names){				
				cmpIds.val(ids);
				cmpNames.val(names);
			};
			var type = parseInt(assignType);
			switch(type){
			case 1:
				var selectIds=cmpIds.val();
				var selectNames=cmpNames.val();
				 UserDialog({callback:callback,
					 selectUserIds:selectIds,
					 selectUserNames:selectNames
				 });
				break;
			case 2:
				RoleDialog({callback:callback});
				break;
			case 3:
			case 4:
				OrgDialog({callback:callback});	
				break;
			case 5:
				PosDialog({callback:callback});
				break;
			case 6:
				UplowDialog({callback:callback});
				break;
			case 7:
				UserParamDialog({callback:callback,nodeUserId:nodeUserId,cmpIds:cmpIds.val(),cmpNames:cmpNames.val()});
				break;
			case 8:
				OrgParamDialog({callback:callback,nodeUserId:nodeUserId,cmpIds:cmpIds.val(),cmpNames:cmpNames.val()});
				break;
			case 9:
				break;
			case 10:
				showOtherNodeDlg({callback:callback,nodeId:nodeId});
				break;
			case 12:
				showScript(cmpNames);
				break;
			case 16:
				typeSetDialog({callback:callback,nodeUserId:nodeUserId,cmpIds:cmpIds.val(),cmpNames:cmpNames.val()});
				break;
			case 17:
				showOtherNodeDlg({callback:callback,nodeId:nodeId});
			}
		}
		
		function assignTypeChange(obj){
			var cmpIds=$(obj).parent().next('td').children('input[name="cmpIds"]');
			var cmpdNames=$(obj).parent().next('td').children('textarea[name="cmpNames"]');
			var selButtons=$(obj).parent().next('td').children('.button');
			if(obj.value==0||obj.value==11||obj.value==9 || obj.value==13 ||obj.value==14 || obj.value==15 ){
				selButtons.hide();
				cmpdNames.hide();
			}else{
				selButtons.show();
				cmpdNames.show();
			}
			cmpIds.val('');
			cmpdNames.val('');
		}
		
		var win;
		function showScript(obj){
			$("#txtScriptData").val(obj.val());
			
			var divObj=$("#divScriptData");
			win= $.ligerDialog.open({ target:divObj , height: 350,width:500, modal :true,
				buttons: [ { text: '确定', onclick: function (item, dialog) { 
						obj.val($("#txtScriptData").val());
						dialog.hide();
					} 
				}, 
				{ text: '取消', onclick: function (item, dialog) { dialog.hide(); } } ] }); 
			
		}
		
		function changeVar(obj){
			var val=$(obj).val();
			var objScript=$("#txtScriptData")[0];
			jQuery.insertText(objScript,val);
		}
		
		//显示其他节点的对话框
		function showOtherNodeDlg(conf){
			var winArgs="dialogWidth=650px;dialogHeight=500px;help=0;status=0;scroll=1;center=1";
			url=__ctx + "/platform/bpm/bpmDefinition/taskNodes.ht?actDefId=${bpmDefinition.actDefId}&nodeId="+conf.nodeId;
			url=url.getNewUrl();
			var rtn=window.open(url,"",winArgs);
			if(conf.callback){
				if(rtn!=undefined){
					conf.callback.call(this,rtn.nodeId,rtn.nodeName);
				}	
			}
		}
		$(function(){
			$("a.del").unbind("click");
		});
	</script>