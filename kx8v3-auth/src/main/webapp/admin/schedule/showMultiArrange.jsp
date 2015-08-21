<div id="divDetail">
</div>
<script type="text/javascript">
var _schId= '${param["schId"]}';
var classIds = '${param["ids"]}';
var arrIds = classIds.split(",");
$(function(){
	for(var i=0;i<arrIds.length;i++){
		var id = "div"+arrIds[i];
		var temp = "<div id='"+id+"'></div>";
		$("#divDetail").append(temp);
		$("#"+id).load(path + "/admin/schedule/toArrange?classId="+arrIds[i]+"&schId="+_schId+"&flag=1");
	}
})

</script>