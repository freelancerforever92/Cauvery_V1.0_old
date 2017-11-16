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
        <script>
            $(document).ready(function() {
                $("#manageSalesOrderReport").addClass("active");
            });

            function editSIRAlias(cellvalue, options, row) {
                //alert(row.siReportrNmePk);
                return "<button class='btn btn-success' onclick='editSIRA(" + row.siReportrNmePk + ");'>Edit</button>";

            }
            function editSIRA(siReportrNmePk) {
                //alert(siReportrNmePk);
                $.getJSON('getreportTypeAction.action?siReportrNmePk=' + siReportrNmePk, function(data)
                {
                    $("#reportSIReName").val(data.siReportNme);
                    $("#reportSIRAliasName").val(data.siReportAliasNme);
                    $("#reportSIRPk").val(siReportrNmePk);
                    $("#myModal1").show();
                });


            }
            function closepopup() {
                $("#myModal1").hide();
            }
            function updateSIRT() {
                if ($("#reportSIRAliasName").val() !== "") {
                    if (confirm("Click ok to Update Sales Intimation Report Info")) {
                        $.getJSON('updateSIRAction.action?siReportrNmePk=' + $("#reportSIRPk").val().trim(), "&siReportAliasNme=" + $("#reportSIRAliasName").val().trim()+ "&siReportNme=" + $("#reportSIReName").val().trim(), function(data)
                        {
                            // alert(data.checkAliasNameVal);
                            if (data.checkAliasNameVal === 1) {
                                //alert(data.updateSIRAliasVal);
                                if (data.updateSIRAliasVal > 0) {
                                    alert("Succefully updated");
                                    $("#myModal1").hide();
                                    location.reload();
                                }
                                else {
                                    alert("Fail");
                                    $("#myModal1").hide();
                                    location.reload();
                                }
                            }
                            else {
                                alert("Alias name already exist");
                                $("#myModal1").hide();

                            }


                        });
                    }
                }
                else {
                    alert("Oop's Alias Name Missing");
                }

            }

        </script>
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li class="active"><a href="admin_salesIntimationReport.jsp">List</a></li>
                    <li><a href="admin_create_salesIntimation.jsp">Create</a></li>

                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">

                <s:url id="salesIntimationReportUrl" action="salesIntimationReportAction"></s:url>
                <sjg:grid
                    id="getListArrayList"
                    dataType="json"
                    href="%{salesIntimationReportUrl}"
                    loadonce="true"
                    pager="true"
                    gridModel="getListArrayList"
                    rowList="10,50,100,1000,4000"
                    rowNum="16"
                    rownumbers="true"
                    navigator="true"
                    navigatorSearch="true"
                    onPagingTopics="grdcourseopt"
                    navigatorSearchOptions="{sopt:['eq']}"
                    cssStyle="font-size:12px;"
                    draggable="false" 
                    hoverrows="false"
                    navigatorAdd="false"
                    navigatorDelete="false"
                    navigatorEdit="false"
                    navigatorRefresh="false"
                    sortable="false"
                    viewrecords="true"
                    shrinkToFit="false"
                    height="400"
                    autowidth="true"                
                    >
                    <sjg:gridColumn name="siReportrNmePk" width="160" index="siReportrNmePk" title="Pk" hidden="true"/>
                    <sjg:gridColumn name="siReportNme" width="160" index="siReportNme" title="Name" />
                    <sjg:gridColumn name="siReportAliasNme" width="160" index="siReportAliasNme" title="Alias Name" />
                    <sjg:gridColumn name="createDate" width="160" index="createDate" title="Created Date" />
                    <sjg:gridColumn name="updatedDate" width="160" index="updatedDate" title="Updated Date" />
                    <sjg:gridColumn name="siReportrNmePk" index="siReportrNmePk" width="165" align="center"  search="false" title="#"  formatter="editSIRAlias" />
                </sjg:grid>
            </div>
        </div>
        <div id="myModal1" class="clearfix dis-n popupbox" tabindex="1" role="dialog">
            <div class="popupcontentbox">
                <div class="modal-header">
                    <button type="button" class="close" onclick="closepopup();">×</button>
                    <h3>Update Sales Intimation Report Type</h3>
                </div>
                <div class="modal-body">
                    <input id="reportSIRPk" type="hidden" class="input-block-level" >
                    <div class="row-fluid">
                        <div class="span4">Report Name :</div>
                        <div class="span8"><input id="reportSIReName" name="reportSIReName" type="text" class="input-block-level" disabled></div>
                    </div>
                    <div class="row-fluid">
                        <div class="span4">Report Alias Name :</div>
                        <div class="span8"> <input id="reportSIRAliasName" name="reportSIRAliasName" type="text" class="input-block-level" ></div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button class="btn" onclick="closepopup();">Close</button>
                    <button class="btn btn-primary" onclick="updateSIRT();">Update</button>
                </div>
            </div>
        </div>
    </body>
</html>