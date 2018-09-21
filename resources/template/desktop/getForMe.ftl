<div  class="desktop">
<ul>
<#list model as data>
<li>
<span align="right"><b></b></span>
<a href="javascript:void(0)" onclick="javascript:jQuery.openFullWindow('${ctxPath}/platform/bpm/taskInfo/toStart.ht?taskId=${data.id}')" class='contenttw'>${data.subject}</a>
</li>
</#list>
</ul>
</div>
