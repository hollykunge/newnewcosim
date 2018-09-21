<#setting number_format="0">
<br />
<table cellpadding="2" cellspacing="0" border="1" style="border:1px solid #366092;border-collapse :collapse;width:100%;text-align:center;font-size: 12px;">
	<tr>
		<td colspan="4" bgcolor="#366092" style="border:1px solid #366092;text-align: center;font-size: 16px;font-weight: bold;height: 32px;color: #fff;" >${table.tableDesc }</td>
	</tr>
	<#assign index=0>
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<#if index % 2 == 0>
		<tr style="height: 23px;">
		</#if>
			<td  bgcolor="#EDF6FC" style="width:15%;padding:2px;border:1px solid #366092;text-align: right;" >${field.fieldDesc}<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
			<td style="width:35%;padding:2px;border:1px solid #366092;text-align: left;">
				<@input field=field/>
			</td>
			<#if index % 2 == 0 && !field_has_next>
			<td bgcolor="#EDF6FC" style="width:15%;padding:2px;border:1px solid #366092;text-align: right;"></td>
			<td style="width:35%;padding:2px;border:1px solid #366092;text-align: left;"></td>
			</#if>
		<#if index % 2 == 1 || !field_has_next>
		</tr>
		</#if>
		<#assign index=index+1>
		</#if>
	</#list>
</table>
<br />
