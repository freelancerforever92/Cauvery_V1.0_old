

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <%@taglib prefix="s" uri="/struts-tags" %>
        <%@taglib prefix="sj" uri="/struts-jquery-tags"%>
        <%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Cauvery - Super Admin</title>
        <link rel="icon" href="../images/cauvery.jpg" rel="Cauvery" type="image/x-icon"/>

        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/custom_styles.css" />   
        <script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>
        <script src="../js/jquery-admin-index.js" type="text/javascript"></script>
        <script>
            $(document).ready(function()
            {
                 $("#manageCounter").addClass("active");
                $("#counterRes").hide();
                $("#counterInfoProgress").hide();
            });
        </script>

        <sj:head jquerytheme="start"/>
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li><a href="admin_counter.jsp">Counter List</a></li>
                    <li><a href="admin_create_counter.jsp">Create Counter</a></li>
                    <li class="active"><a href="admin_edit_counter.jsp">Change Counter Info</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <div class="clearfix search_sec">
                    <div class="row-fluid">
                        <div class="span4">
                            <label class="pull-right">Counter Id :</label>
                        </div>
                        <div class="span4">
                            <input type="text" id="counId" name="counId" class="input-block-level"/>
                        </div>
                        <div class="span4">
                            <div onclick="getCounterInfo();" class="search pull-left">
                                <span class="icon-search icon-white"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="counterInfoProgress" class="t-c"> 
                    <p>Wait for searching...</p>
                    <img alt="uploadingMaterial" src="bootstrap/img/progress_bar.gif"/>
                </div>
                <div class="clearfix" id="counterRes">
                    <div class="clearfix create-counter">
                        <form id="counterUpdationForm" name="counterUpdationForm">
                            <div class="row-fluid dis-n">                        
                                <input type="text" name="counterId" id="counterId" class="input-block-level">                       
                            </div>
                            <div class="row-fluid">
                                <div class="span4">
                                    <label>Counter Name :</label>
                                </div>
                                <div class="span8">
                                    <input type="text" name="counterName" id="counterName" class="input-block-level">
                                </div>
                            </div>

                            <div class="row-fluid">
                                <div class="span4">
                                    <label>Counter Legacy No :</label>
                                </div>
                                <div class="span8">
                                    <input type="text" name="counterLegacyNo" id="counterLegacyNo" class="input-block-level">
                                </div>
                            </div>             
                            <div class="row-fluid" style="padding-top: 10px;">
                                <div class="span4"></div> 
                                <div class="span4">
                                    <input type="button" style="width: 100px;" class="btn btn-warning" value="Clear" onclick="clearCounterInfo();">
                                </div> 
                                <div class="span4">
                                    <input type="button" style="width: 100px;" class="btn btn-primary pull-right" value="Update" onclick="editCounter();">
                                </div>                  
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </body>
</html>