<#setting number_format="0">
<br />
<table cellpadding="2" cellspacing="0" border="1" style="border:1px solid #366092;border-collapse :collapse;width:100%;text-align:center;font-size: 12px;">
	<tr>
		<td colspan="2" bgcolor="#366092"  style="border:1px solid #366092;text-align: center;font-size: 16px;font-weight: bold;height: 32px;color: #fff;" >${table.tableDesc }</td>
	</tr>
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
			<tr style="height: 23px;">
				<td  style="width:20%;padding:2px;border:1px solid #366092;text-align: right;" bgcolor="#EDF6FC"  >${field.fieldDesc}<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
				<td  style="width:80%;padding:2px;border:1px solid #366092;text-align: left;" >
					<@input field=field/>
				</td>
			</tr>
		</#if>
	</#list>
</table>
<br />
