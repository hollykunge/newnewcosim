<br>
<table style="border:1px solid #366092;border-collapse :collapse;width:100%;text-align:center;font-size: 12px;">
    <tr style="height: 32px;background-color: #366092;">
    	<td colspan="${fields?size +1}" style="border:1px solid #366092;text-align: left;padding-left: 4px;"><a class="link add" href="javascript:void(0)" >添加</a></td>
    </tr>
	<tr style="height: 25px;">
		<#list fields as field>
			<#if field.valueFrom != 2 && field.isHidden == 0>
			<th style="border:1px solid #366092;text-align: center;	height: 25px;font-size:12px;background-color:#F1F6FF;font-weight:bold;">${field.fieldDesc } </th>
			</#if>
		</#list>
        <th style="background-color:#F1F6FF;font-weight:bold;">管理</th>
	</tr>
	<tr class="listRow" formType="edit">
		<#list fields as field>
			<#if field.valueFrom != 2 && field.isHidden == 0>
			<td style="border:1px solid #366092;text-align: center;padding-top: 2px;padding-bottom: 2px;">
				<@input field=field/>
			</td>
			</#if>
		</#list>
        <td ><a class="link del" href="javascript:void(0)" title="删除">删除</a></td>
	</tr>
</table>
