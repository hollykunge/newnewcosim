<#setting number_format="0">
<#macro input field table >
<#if table.isMain==1>
<#if field.valueFrom == 0>
		<#if field.fieldType == "varchar">
				<#if field.controlType == 1>
						<input  type="text"  name="${field.fieldName}" class="inputText" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}" />
				<#elseif    field.controlType == 2>
						<textarea name="${field.fieldName}" class="l-textarea" rows="5" cols="40" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
				<#elseif    field.controlType == 3>
						<input class="dicComboTree" nodeKey="${field.dictType}" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" name="${field.fieldName}" height="200" width="125"/>
				<#elseif    field.controlType == 4>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value="">
						<input name="${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly/>
						<a href="javascript:;" class="link user" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 5>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link role" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 6>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link org" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 7>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link position" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 8>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly>
						<a href="javascript:;" class="link users" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 10>
						<textarea class="ckeditor"  name="${field.fieldName}" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
				<#elseif  field.controlType == 9>
					<div name="div_attachment_container" right="w" >
						<div  class="attachement" ></div>
						<textarea style="display:none" controltype="attachment"  name="${field.fieldName}" ><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
						<a href="javascript:;" field="${field.fieldName}" class="link selectFile" onclick="AttachMent.addFile(this);">选择</a>
					</div>
				<#elseif  field.controlType == 11>
					<select name="${field.fieldName}" >
						<#list field.aryOptions as tmp>
							<option value="${tmp}" <c:if test="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}=='${tmp}'}">selected="selected"</c:if >> ${tmp}</option>
						</#list>
					</select>
				<#elseif  field.controlType == 13>
					<#list field.aryOptions as tmp>
						<label><input type="checkbox" name="${field.fieldName}" value="${tmp}" data="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" />${tmp}</label>
					</#list>
				<#elseif  field.controlType == 14>
					<#list field.aryOptions as tmp>
						<label><input type="radio" name="${field.fieldName}" value="${tmp}" <c:if test="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}=='${tmp}'}">checked="checked"</c:if >/>${tmp}</label>
					</#list>
				<#elseif  field.controlType == 12>
						<input type="hidden" class="hidden" name="${field.fieldName}" controltype="office"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" />
						<div id="div_${field.fieldName?replace(":","_")}" class="office-div"></div>
				<#elseif  field.controlType == 15>
						<input name="${field.fieldName}" type="text" class="Wdate" displayDate="<#if (field.getPropertyMap().displayDate==null)>0<#else>${field.getPropertyMap().displayDate}</#if>" dateFmt="<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{date:true,<#if field.isRequired == 1>required:true</#if>}">
				<#elseif  field.controlType == 16>
						<input name="${field.fieldName}" type="hidden"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}">
				</#if>
		<#elseif field.fieldType == "number">
			<#if  field.controlType == 16>
				<input name="${field.fieldName}" type="hidden"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}">
			<#else>
				<input name="${field.fieldName}" type="text" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{number:true,maxIntLen:${field.intLen},maxDecimalLen:${field.decimalLen}<#if field.isRequired == 1>,required:true</#if>}">
			</#if>
		<#elseif field.fieldType == "date">
			<input name="${field.fieldName}" type="text" class="Wdate" displayDate="<#if (field.getPropertyMap().displayDate==null)>0<#else>${field.getPropertyMap().displayDate}</#if>"  dateFmt="<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>"  value="<fmt:formatDate value='<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}' pattern='<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>'/>" validate="{<#if field.isRequired == 1>required:true</#if>}">
		<#else>
			<#if  field.controlType == 16>
				<input name="${field.fieldName}" type="hidden"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}">
			<#elseif field.controlType == 10>
				<textarea class="ckeditor"  name="${field.fieldName}" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
			<#else>
				<textarea  name="${field.fieldName}" validate="{<#if field.isRequired == 1>required:true</#if><#if field.validRule != ""><#if field.isRequired == 1>,</#if>${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
			</#if>
		</#if>
<#elseif field.valueFrom == 1 || field.valueFrom == 3>
		<#if field.fieldType == "varchar">
			<input name="${field.fieldName}" type="text"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" <#if field.valueFrom == 3>readonly</#if> validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}">
		<#elseif field.fieldType == "number">
			<input name="${field.fieldName}" type="text"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" <#if field.valueFrom == 3>readonly</#if> validate="{number:true,maxIntLen:${field.intLen},maxDecimalLen:${field.decimalLen}<#if field.isRequired == 1>,required:true</#if>}">
		<#elseif field.fieldType == "date">
			<input name="${field.fieldName}" type="text"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" <#if field.valueFrom == 3>readonly</#if> validate="{<#if field.isRequired == 1>required:true</#if>}">
		</#if>
</#if>
<#else>
	<#if field.fieldType == "varchar">
				<#if field.controlType == 1>
						<input  type="text"  name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" class="inputText" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}" />
				<#elseif    field.controlType == 2>
						<textarea name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" class="l-textarea" rows="5" cols="40" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
				<#elseif    field.controlType == 3>
						<input class="dicComboTree" nodeKey="${field.dictType}" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" height="200" width="125"/>
				<#elseif    field.controlType == 4>
						<div><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}ID" type="hidden" class="hidden" value="">
						<input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly/>
						<a href="javascript:;" class="link user" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 5>
						<div><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link role" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 6>
						<div><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link org" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 7>
						<div><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link position" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 8>
						<div><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}ID" type="hidden" class="hidden" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}"><input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text"   value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly>
						<a href="javascript:;" class="link users" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 10>
						<textarea class="ckeditor"  name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
				<#elseif  field.controlType == 9>
					<div name="div_attachment_container" right="w" >
						<div  class="attachement" ></div>
						<textarea style="display:none" controltype="attachment"  name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" ><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
						<a href="javascript:;" field="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldNamee}" class="link selectFile" onclick="AttachMent.addFile(this);">选择</a>
					</div>
				<#elseif  field.controlType == 11>
					<select name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" >
						<#list field.aryOptions as tmp>
							<option value="${tmp}" <c:if test="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}=='${tmp}'}">selected="selected"</c:if >> ${tmp}</option>
						</#list>
					</select>
				<#elseif  field.controlType == 13>
					<#list field.aryOptions as tmp>
						<label><input type="checkbox" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" value="${tmp}" data="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" />${tmp}</label>
					</#list>
				<#elseif  field.controlType == 14>
					<#list field.aryOptions as tmp>
						<label><input type="radio" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" value="${tmp}" <c:if test="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}=='${tmp}'}">checked="checked"</c:if >/>${tmp}</label>
					</#list>
				<#elseif  field.controlType == 12>
						<input type="hidden" class="hidden" name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" controltype="office"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" />
						<div id="div_${field.fieldName?replace(":","_")}" class="office-div"></div>
				<#elseif  field.controlType == 15>
						<input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text" class="Wdate" displayDate="<#if (field.getPropertyMap().displayDate==null)>0<#else>${field.getPropertyMap().displayDate}</#if>" dateFmt="<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{date:true,<#if field.isRequired == 1>required:true</#if>}">
				<#elseif  field.controlType == 16>
						<input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="hidden"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}">
				</#if>
		<#elseif field.fieldType == "number">
			<#if  field.controlType == 16>
				<input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="hidden"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}">
			<#else>
				<input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text" value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{number:true,maxIntLen:${field.intLen},maxDecimalLen:${field.decimalLen}<#if field.isRequired == 1>,required:true</#if>}">
			</#if>
		<#elseif field.fieldType == "date">
			<input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="text" class="Wdate" displayDate="<#if (field.getPropertyMap().displayDate==null)>0<#else>${field.getPropertyMap().displayDate}</#if>"  dateFmt="<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>"  value="<fmt:formatDate value='<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}' pattern='<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>'/>" validate="{<#if field.isRequired == 1>required:true</#if>}">
		<#else>
			<#if  field.controlType == 16>
				<input name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" type="hidden"  value="<#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}" validate="{<#if field.isRequired == 1>required:true</#if>}">
			<#elseif field.controlType == 10>
				<textarea class="ckeditor"  name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
			<#else>
				<textarea  name="${table.variable.classVar}List[<#noparse>${i.index}</#noparse>].${field.fieldName}" validate="{<#if field.isRequired == 1>required:true</#if><#if field.validRule != ""><#if field.isRequired == 1>,</#if>${field.validRule}:true</#if>}"><#noparse>${</#noparse>${table.variable.classVar}Item.${field.fieldName}}</textarea>
			</#if>
		</#if>
</#if>
</#macro>