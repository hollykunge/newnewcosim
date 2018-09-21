<#setting number_format="#">
<div class="desktop">
<ul>
<#list model as data>
<li>
<span align="right"><b>(${data_index+1})</b></span>
<a href="javascript:void(0)" onclick="javascript:jQuery.openFullWindow('${ctxPath}/platform/bpm/processRun/get.ht?runId=${data.runId}')" class='contenttw'>${data.subject}</a>
</li>
</#list>
</ul>
</div>