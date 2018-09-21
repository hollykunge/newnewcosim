<br>
<table cellpadding="2" cellspacing="0" border="0" style="border:1px solid #366092;border-collapse :collapse;width:100%;text-align:center;font-size: 12px;">
    <tr style="height: 32px;background-color: #366092;">
    	<td  colspan="${fields?size +1}"><a class="link add" href="javascript:void(0)" >添加</a></td>
    </tr>
	<tr >
		<#list fields as field>
			<#if field.valueFrom != 2 && field.isHidden == 0>
			<th >${field.fieldDesc } </th>
			</#if>
		</#list>
	</tr>
	<tr  formType="form">
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<td fieldName="${field.fieldName }" ></td>
		</#if>
	</#list>
	</tr>
</table>

<table formType="window">
	<#list fields as field>
		<#if field.valueFrom != 2 && field.isHidden == 0>
		<tr>
			<td width="30%" style="padding:2px;">${field.fieldDesc }<#if field.isRequired == 1><span class="required">*</span></#if>:</td>
			<td width="70%" style="padding:2px;"><@input field=field/></td>
		</tr>
		</#if>
	</#list>
</table>