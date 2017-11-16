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
                $("#manageReason").addClass("active");
            });
            function editSecQuestion(cellvalue, options, row) {
                return "<button class='btn btn-success'  onclick='editQuestion(" + row.reasonId + ");'>Edit</button>";
            }
            function editQuestion(reasonId)
            {
                $.getJSON('getCancelReason.action?reasonId=' + reasonId, function(data)
                {
                    $("#cReasonDesc").val(data.reasonDesc);
                    $("#reasonId").val(reasonId);
                    $("#myModal1").show();
                });
            }
            function closepopup() {
                $("#myModal1").hide();
            }
            function updateReasons() {
                if ($("#cReasonDesc").val() !== "") {
                    $.getJSON("UpdateCancelReason.action?reasonId=" + $("#reasonId").val().trim(), '?&reasonDesc=' + $("#cReasonDesc").val().trim(), function(data)
                    {
                        if (data.countExixt <= 0) {
                            if (data.updateValueCancelResion >= 1) {
                                alert("Updated Sucesfully..!");
                                $("#myModal1").hide();
                                location.reload();
                            } else {
                                alert("Record Not Updated..!");
                                $("#myModal1").hide();
                                location.reload();
                            }
                        } else {
                            alert("Record All Ready Exist..!");
                            $("#myModal1").hide();
                            location.reload();
                        }
                    });
                } else {
                    alert("Canceling Reason Cannot Be Empty..!");
                    $("#cReasonDesc").focus();
                }
            }
        </script>

    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li class="active"><a href="canclingReason.jsp">Reasons List</a></li>
                    <li><a href="createCancellingReason.jsp">Create Reasons</a></li>

                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">

                <s:url id="countDetailUrl" action="cancelReasonAction"></s:url>
                <sjg:grid
                    id="cancellingReasonTos"
                    dataType="json"
                    href="%{countDetailUrl}"
                    loadonce="true"
                    pager="true"
                    gridModel="cancellingReasonTos"
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
                    autowidth="true"                
                    >
                    <sjg:gridColumn name="reasonId" width="300" index="reasonId" title="reason_id" hidden="true" />
                    <sjg:gridColumn name="reasonDesc" width="380" index="reasonDesc"  title="Reason Description" />
                    <sjg:gridColumn name="reasonNo" width="200" index="reasonNo" align="center" title="Reason No" hidden="true"  />
                    <sjg:gridColumn name="createdDate" width="200" index="createdDate"  title="Created Date"   />
                    <sjg:gridColumn name="updatedDate" width="200" index="updatedDate" title="Updated Date"/>
                    <sjg:gridColumn name="reasonId" index="reasonId" search="false" title="Action" align="center"  formatter="editSecQuestion" />
                </sjg:grid>
            </div>
        </div>
        <div id="myModal1" class="clearfix dis-n popupbox" tabindex="1" role="dialog">
            <div class="popupcontentbox">
                <div class="modal-header">
                    <button type="button" class="close" onclick="closepopup();">×</button>
                    <h3>Update Reasons</h3>
                </div>
                <div class="modal-body">
                    <input id="reasonId" type="hidden" class="input-block-level" >
                    <input id="cReasonDesc" type="text" class="input-block-level" >
                </div>
                <div class="modal-footer">
                    <button class="btn" onclick="closepopup();">Close</button>
                    <button class="btn btn-primary" onclick="updateReasons();">Update</button>
                </div>
            </div>
        </div>
    </body>
</html>