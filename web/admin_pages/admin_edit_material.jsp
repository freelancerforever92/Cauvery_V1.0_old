

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
                $("#manageMaterial").addClass("active");
                $("#materialRes").hide();
                $("#materialProgress").hide();
            });
            function chaneGroupfun() {
                if ($("#changeCraftGroup").find("option:selected").val() !== "select")
                {
                    $("#craftGroup").val($("#changeCraftGroup").find("option:selected").val());
                }
                else{
                    $("#craftGroup").val($("#cGroup").val());
                    
                }
            }
        </script>

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
                  <script src="<c:url value='../js/html5.js'/>"></script>
                <![endif]-->
        <sj:head jquerytheme="start"/>
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 

        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li><a href="admin_material.jsp">Material List</a></li>
                    <li><a href="admin_create_material.jsp">Upload Material</a></li>
                    <li class="active"><a href="admin_edit_material.jsp">Change Material</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <div class="row-fluid">
                <div class="span4">
                        <label class="pull-right">Material Id :</label>
                    </div>
                    <div class="span4">
                        <input type="text" id="materialId" name="materialId" class="input-block-level"/>
                    </div>
                    <div class="span4">
                        <div id="editUser" onclick="getMaterialInfo();" class="search pull-left">
                            <span class="icon-search icon-white"></span>
                        </div>
                    </div>
                </div>

                <div id="materialProgress"> 
                    <p>Please wait for searching...</p>
                    <img alt="uploadingMaterial" src="bootstrap/img/progress_bar.gif"/>
                </div>
                <div id="materialRes">
                    <form id="materialUpdationForm" name="materialUpdationForm">
                        <div class="row-fluid dis-n">
                            <input type="text" id="matId" name="matId"/>
                        </div>
                        <div class="row-fluid">
                            <div class="span4">
                                <label>Material Description :</label>
                            </div>
                            <div class="span8">
                                <input type="text" id="matDesc" name="matDesc" autocomplete="off" class="input-block-level"/>    
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span4">
                                <label>Craft Group :</label>
                            </div>
                            <div class="span3">
                                <input type="hidden" id="cGroup"/>
                                      
                                <input type="text" id="craftGroup" name="craftGroup" autocomplete="off" class="input-block-level" readonly/>    
                            </div>
                            <div class="span5">
                                <select onchange="chaneGroupfun();" id="changeCraftGroup" name="changeCraftGroup" class="input-block-level">                                 

                                </select>
                            </div>
                        </div>

                        <div class="row-fluid">
                            <div class="span4">
                                <label>Plant Id :</label>
                            </div>
                            <div class="span8">
                                <input type="text" id="plantNo" name="plantNo" autocomplete="off" class="input-block-level" readonly/>    
                            </div>                        
                        </div>

                        <div class="row-fluid">
                            <div class="span4">
                                <label>Storage Location :</label>
                            </div>
                            <div class="span8">
                                <input type="text" id="storageLoc" name="storageLoc" autocomplete="off" class="input-block-level"/>    
                            </div>                        
                        </div>
                        <div class="row-fluid">
                            <div class="span4">
                                <label>Distribution Channel No :</label>
                            </div>
                            <div class="span8">
                                <input type="text" id="distChanelNo" name="distChanelNo" autocomplete="off" class="input-block-level"/>    
                            </div>                        
                        </div>
                        <div class="row-fluid">
                            <div class="span4">
                                <label>Price :</label>
                            </div>
                            <div class="span8">
                                <input type="text" id="price" name="price" autocomplete="off" class="input-block-level"/>    
                            </div>                        
                        </div> 
                        <div class="row-fluid">
                            <div class="span4">
                                <label>Standard Price :</label>
                            </div>
                            <div class="span8">
                                <input type="text" id="sPrice" name="standardPrice" autocomplete="off" class="input-block-level"/>    
                            </div>                        
                        </div>     
                        <div class="row-fluid" style="padding-top: 10px;">  
                            <div class="span4"></div>
                            <div class="span4"> <input style="width: 100px;" type="button" id="user_update_cancel_button" name="user_create_cancel_button" class="btn btn-warning" value="Cancel" onclick="clearMatDetails();" /></div>
                            <div class="span4"><input style="width: 100px;" type="button" id="user_update_button" name="user_create_button" class="btn btn-primary pull-right"  value="Update" onclick="updateMatInputs();"/></div>
                        </div>  

                    </form>
                </div>

            </div>
        </div>
    </body>
</html>