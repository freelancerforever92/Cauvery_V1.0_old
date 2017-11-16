<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cauvery - Super Admin</title>
        <link rel="icon" href="../images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>

        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/custom_styles.css" />   
        <script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
        <script src="../js/jquery-admin-index.js" type="text/javascript"></script>
        <sj:head jquerytheme="start"/>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#manageReportType").addClass("active");
            });
            function editReportType(cellvalue, options, row) {
                return "<button class='btn btn-success'  onclick='editType(" + row.reportTypePk + ");'>Edit</button>";
            }
            function editType(reportTypePk)
            {
                $.getJSON('getreportType.action?reportTypePk=' + reportTypePk, function(data)
                {
                    $("#reportTypeName").val(data.rptName);
                    $("#reportTypePk").val(reportTypePk);
                    $("#myModal1").show();
                });
            }
            function closepopup() {
                $("#myModal1").hide();
            }
            function updateRType() {
                if ($("#reportTypeName").val() !== "") {
                    $.getJSON("UpdateRTypeAction.action?reportTypePk=" + $("#reportTypePk").val().trim(), '?&rptName=' + $("#reportTypeName").val().trim(), function(data)
                    {
                        if (data.existTypeVal <= 0) {
                            if (data.updateRTypeVal >= 1) {
                                alert("Updated Sucesfully..!");
                                $("#myModal1").hide();
                                location.reload();
                            } else {
                                alert("Record Not Updated..!");
                                $("#myModal1").hide();
                                location.reload();
                            }
                        } else {
                            alert("Record already Exist..!");
                            $("#myModal1").hide();
                            location.reload();
                        }
                    });
                } else {
                    alert("ReportType Cannot Be Empty..!");
                    $("#reportTypeName").focus();
                }
            }
        </script>

    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">
            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li class="active"><a href="admin_reporttype.jsp">Report Types</a></li>                   
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">               
                <s:url id="reportTypesUrl" action="reportTypesAction"></s:url>
                <sjg:grid
                    id="reportTypeList"
                    dataType="json"
                    href="%{reportTypesUrl}"
                    loadonce="true"
                    pager="true"
                    gridModel="reportTypeList"
                    rowList="10,100"                   
                    rownumbers="true"
                    navigator="true"
                    navigatorSearch="false"                    
                    cssStyle="font-size:12px;"
                    draggable="false" 
                    hoverrows="false"
                    navigatorAdd="false"
                    navigatorDelete="false"
                    navigatorEdit="false"
                    navigatorRefresh="false"
                    sortable="true"
                    viewrecords="true"
                    shrinkToFit="false"
                    height="155"
                    autowidth="true"                
                    >

                    <sjg:gridColumn name="reportTypePk" width="200" index="reportTypePk" hidden="true" align="center" title="reportTypePk" />
                    <sjg:gridColumn name="rptName" width="200" index="rptName" title="Report Types" />
                    <sjg:gridColumn name="updatedDate" width="200" index="updatedDate" align="center" title="Last UpdatedOn" />
                    <sjg:gridColumn name="reportTypePk" index="reportTypePk" width="165" align="center"  search="false" title="#"  formatter="editReportType" />
                </sjg:grid>

            </div>
        </div>
        <div id="myModal1" class="clearfix dis-n popupbox" tabindex="1" role="dialog">
            <div class="popupcontentbox">
                <div class="modal-header">
                    <button type="button" class="close" onclick="closepopup();">×</button>
                    <h3>Update Report Type</h3>
                </div>
                <div class="modal-body">
                    <input id="reportTypePk" type="hidden" class="input-block-level" >
                    <input id="reportTypeName" type="text" class="input-block-level" >
                </div>
                <div class="modal-footer">
                    <button class="btn" onclick="closepopup();">Close</button>
                    <button class="btn btn-primary" onclick="updateRType();">Update</button>
                </div>
            </div>
        </div>
    </body>
</html>