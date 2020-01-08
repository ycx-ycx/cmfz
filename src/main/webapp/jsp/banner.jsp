<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : '${pageContext.request.contextPath}/banner/showAllBanners',
                datatype : "json",
                // 时间格式的处理在后台进行
                colNames : [ 'ID', '标题', '图片', '超链接', '创建时间','描述', '状态' ],
                colModel : [
                    {name : 'id',align:"center",hidden:true},
                    {name : 'title',align:"center",editable: true,editrules:{required:true}},
                    {name : 'url',align:"center",formatter:function (data) {
                            return "<img style='width: 180px;height: 80px' src='"+data+"'>"
                        },editable: true,edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                    {name : 'href',align:"center",editable:true},
                    {name : 'createDate',align:"center",editable:true,editrules:{required:true},edittype: "date"},
                    {name : 'desc',align:"center",editable:true,editrules:{required:true}},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else return "冻结";
                        },editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}}
                ],
                rowNum : 2,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPage',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",
                caption : "轮播图信息",
                autowidth: true,
                multiselect:true,
                styleUI:"Bootstrap",
                height:"300px",
                editurl: "${pageContext.request.contextPath}/banner/editBanner"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerPage', {edit : true,add : true,del : true,edittext:"编辑",addtext:"添加",deltext:"删除"},
            // {} --> edit操作    {} --> 添加操作     {} --> 删除操作
            /*
                1. 写出三个{} 分别对应 修改,添加,删除操作
                2. 为每个{} 中加入 closeAfterXXX:true 表示 完成增删改后关闭提示框
                3. afterSubmit:function (response,postData) {} 方法编写
                4. response 指editurl的回执信息 通常使用 response.responseJSON.XXX 获取值
                5. 在afterSubmit方法中调用$.AjaxFileUpload 方法进行上传操作
                6.  data:{bannerId:bannerId},
                        fileElementId:"url"
             */
            {
                closeAfterEdit: true,
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        datatype: "json",
                        data:{bannerId:bannerId},
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterAdd:true,
                afterSubmit:function (response,postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/uploadBanner",
                        type:"post",
                        datatype: "json",
                        data:{bannerId:bannerId},
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    })
                    return postData;
                }
            },{
                closeAfterDel:true
            });
    })
</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>

</ul>
<div class="panel">
    <table id="bannerTable"></table>
    <div id="bannerPage" style="height: 50px"></div>
</div>

