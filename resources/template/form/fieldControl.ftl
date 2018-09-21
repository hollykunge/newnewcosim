<#setting number_format="0">
<#macro input field>
<#if field.valueFrom == 0>
		<#if field.fieldType == "varchar">
				<#if field.controlType == 1>
						<input  type="text"  name="${field.fieldName}" class="inputText" value="" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}" />
				<#elseif    field.controlType == 2>
						<textarea name="${field.fieldName}" class="l-textarea" rows="5" cols="40" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"></textarea>
				<#elseif    field.controlType == 3>
						<input class="dicComboTree" nodeKey="${field.dictType}" value="" validate="{<#if field.isRequired == 1>required:true</#if>}" name="${field.fieldName}" height="200" width="125"/>
				<#elseif    field.controlType == 4>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value="">
						<input name="${field.fieldName}" type="text"   value="" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly/>
						<a href="javascript:;" class="link user" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 5>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value=""><input name="${field.fieldName}" type="text"   value="" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link role" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 6>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value=""><input name="${field.fieldName}" type="text"   value="" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link org" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 7>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value=""><input name="${field.fieldName}" type="text"   value="" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly />
						<a href="javascript:;" class="link position" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 8>
						<div><input name="${field.fieldName}ID" type="hidden" class="hidden" value=""><input name="${field.fieldName}" type="text"   value="" validate="{<#if field.isRequired == 1>required:true</#if>}" readonly>
						<a href="javascript:;" class="link users" name="${field.fieldName}" >选择</a></div>
				<#elseif    field.controlType == 10>
						<textarea class="ckeditor"  name="${field.fieldName}" validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"></textarea>
				<#elseif  field.controlType == 9>
					<div name="div_attachment_container" >
						<div  class="attachement" ></div>
						<textarea style="display:none" controltype="attachment"  name="${field.fieldName}" ></textarea>
						<a href="javascript:;" field="${field.fieldName}" class="link selectFile" onclick="AttachMent.addFile(this);">选择</a>
					</div>
				<#elseif  field.controlType == 11>
					<select name="${field.fieldName}" >
						<#list field.aryOptions as tmp>
							<option value="${tmp}">${tmp}</option>
						</#list>
					</select>
				<#elseif  field.controlType == 13>
					<#list field.aryOptions as tmp>
						<label><input type="checkbox" name="${field.fieldName}" value="${tmp}" />${tmp}</label>
					</#list>
				<#elseif  field.controlType == 14>
					<#list field.aryOptions as tmp>
						<label><input type="radio" name="${field.fieldName}" value="${tmp}" />${tmp}</label>
					</#list>
				<#elseif  field.controlType == 12>
						<input type="hidden" class="hidden" name="${field.fieldName}" controltype="office"  value="" />
						<div id="div_${field.fieldName?replace(":","_")}" class="office-div"></div>
				<#elseif  field.controlType == 15>
						<input name="${field.fieldName}" type="text" class="Wdate" displayDate="<#if (field.getPropertyMap().displayDate==null)>0<#else>${field.getPropertyMap().displayDate}</#if>" dateFmt="<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>"  value="" validate="{<#if field.isRequired == 1>required:true</#if>}">
				<#elseif  field.controlType == 16>
						<input name="${field.fieldName}" type="hidden"  value="" validate="{<#if field.isRequired == 1>required:true</#if>}">
				</#if>
		<#elseif field.fieldType == "number">
			<#if  field.controlType == 16>
				<input name="${field.fieldName}" type="hidden"  value="" validate="{<#if field.isRequired == 1>required:true</#if>}">
			<#else>
				<input name="${field.fieldName}" type="text" value="" validate="{number:true,maxIntLen:${field.intLen},maxDecimalLen:${field.decimalLen}<#if field.isRequired == 1>,required:true</#if>}">
			</#if>
		<#elseif field.fieldType == "date">
			<input name="${field.fieldName}" type="text" class="Wdate" displayDate="<#if (field.getPropertyMap().displayDate==null)>0<#else>${field.getPropertyMap().displayDate}</#if>"  dateFmt="<#if (field.getPropertyMap().format==null)>yyyy-MM-dd<#else>${field.getPropertyMap().format}</#if>"  value="" validate="{<#if field.isRequired == 1>required:true</#if>}">
		<#else>
			<#if  field.controlType == 16>
				<input name="${field.fieldName}" type="hidden"  value="" validate="{<#if field.isRequired == 1>required:true</#if>}">
			<#elseif field.controlType == 10>
				<textarea class="ckeditor"  name="${field.fieldName}" validate="{<#if field.isRequired == 1>required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}"></textarea>
			<#else>
				<textarea  name="${field.fieldName}" validate="{<#if field.isRequired == 1>required:true</#if><#if field.validRule != ""><#if field.isRequired == 1>,</#if>${field.validRule}:true</#if>}"></textarea>
			</#if>
		</#if>
<#elseif field.valueFrom == 1 || field.valueFrom == 3>
		<#if field.fieldType == "varchar">
			<input name="${field.fieldName}" type="text"  value="" <#if field.valueFrom == 3>readonly</#if> validate="{maxlength:${field.charLen}<#if field.isRequired == 1>,required:true</#if><#if field.validRule != "">,${field.validRule}:true</#if>}">
		<#elseif field.fieldType == "number">
			<input name="${field.fieldName}" type="text"  value="" <#if field.valueFrom == 3>readonly</#if> validate="{number:true,maxIntLen:${field.intLen},maxDecimalLen:${field.decimalLen}<#if field.isRequired == 1>,required:true</#if>}">
		<#elseif field.fieldType == "date">
			<input name="${field.fieldName}" type="text"  value="" <#if field.valueFrom == 3>readonly</#if> validate="{<#if field.isRequired == 1>required:true</#if>}">
		</#if>
</#if>
</#macro>