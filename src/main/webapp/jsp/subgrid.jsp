<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        $("#ftable").jqGrid(
            {
                url : '${pageContext.request.contextPath}/json/f.json',
                datatype : "json",
                height : 500,
                colNames : [ 'ID', 'clazz'],
                colModel : [
                    {name : 'id'},
                    {name : 'clazz'},

                ],
                rowNum : 8,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#fpage',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                mtype:"get",
                multiselect : false,
                autowidth:true,
                styleUI:"Bootstrap",
                // 开启子表格支持
                subGrid : true,
                caption : "Grid as Subgrid",
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded : function(subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id,row_id);
                },
                // 删除表格的方法
                subGridRowColapsed : function(subgrid_id, row_id) {
                }
            });
        $("#ftable").jqGrid('navGrid', '#pagersg11', {
            add : false,
            edit : false,
            del : false
        });
    });
    // subgrid_id 父行级id
    function addSubgrid(subgrid_id,row_id) {
        // 声明子表格Id
        var sid = subgrid_id + "table";
        // 声明子表格工具栏id
        var spage = subgrid_id + "page";
        $("#"+subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='"+ spage +"' style='height: 50px'></div>")
        $("#" + sid).jqGrid(
            {
                // 指定的json文件
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url : "${pageContext.request.contextPath}/json/s.json",
                datatype : "json",
                colNames : [ 'id', 'stu'],
                colModel : [
                    {name : "id",  index : "num",width : 80,key : true},
                    {name : "stu",index : "item",  width : 130},
                ],
                rowNum : 20,
                pager : spage,
                sortname : 'num',
                sortorder : "asc",
                height : '100%',
                autowidth: true,
                styleUI:"Bootstrap",
            });
        $("#" + sid).jqGrid('navGrid',
            "#" + spage, {
                edit : false,
                add : false,
                del : false
            });
    };
</script>
<body>
<table id="ftable"></table>
<div id="fpage" style="height: 50px"></div>
</body>