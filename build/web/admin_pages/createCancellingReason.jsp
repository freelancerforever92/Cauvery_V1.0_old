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
                $("#manageReason").addClass("active");
            });
        </script>
        
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li><a href="canclingReason.jsp">Reasons List</a></li>
                    <li class="active"><a href="createCancellingReason.jsp">Create Reasons</a></li>

                </ul>
            </div>

            <div class="content_sec" style="background: #fff;">
                <div id="newUserCreationDiv">                   
                    <div class="clearfix create-user">
                        <form id="userCreationForm" name="cancelReasonCreationForm">                           
                            <div class="row-fluid">
                                <div class="span4">
                                    <label>Enter Reason :</label>
                                </div>
                                <div class="span8">
                                    <textarea id="reasonDesc" name="reasonDesc" rows="5" autocomplete="off" class="input-block-level"></textarea>    
                                </div>
                            </div>
                            <div class="row-fluid" style="padding-top: 10px;">
                                <div class="span4"></div>
                                <div class="span4"> <input style="width: 100px;" type="button" id="reason_create_cancel_button" name="reason__create_cancel_button" class="btn btn-warning " value="Cancel" onclick="clearAllValues();" /></div>

                                <div class="span4"><input style="width: 100px;" type="button" id="reason_create_button" name="reason_create_button" class="btn btn-info pull-right"  value="Create" onclick="validNewReasonInputs();"/></div>
                            </div> 
                        </form>
                    </div>                
                </div>

            </div>
        </div>
    </body>
</html>