

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
                $("#manageVendor").addClass("active");
                $("#vendorRes").hide();
                $("#vendorInfoProgress").hide();
            });
            function chanetitlefun() {
                if ($("#titleOption").find("option:selected").val() !== "select")
                {
                    $("#vendorTitle").val($("#titleOption").find("option:selected").val());
                }
                else {
                    $("#vendorTitle").val($("#vTitle").val());
                }
            }
        </script>

        <sj:head jquerytheme="start"/>
    </head>
    <body>
       <jsp:include page="commonJsp.jsp" /> 

        <div class="clearfix body-sec">
            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li><a href="admin_vendor.jsp">Vendor List</a></li>
                    <li><a href="admin_create_vendor.jsp">Upload Vendor</a></li>
                    <li class="active"><a href="admin_edit_vendor.jsp">Change Vendor</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <div class="clearfix search_sec">
                    <div class="row-fluid">
                        <div class="span4">
                            <label class="pull-right">Vendor Id :</label>
                        </div>
                        <div class="span4">
                            <input type="text" id="venId" name="venId" class="input-block-level"/>
                        </div>
                        <div class="span4">
                            <div onclick="getVendorInfo();" class="search pull-left">
                                <span class="icon-search icon-white"></span>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="vendorInfoProgress" class="t-c"> 
                    <p>Wait for searching...</p>
                    <img alt="uploadingMaterial" src="bootstrap/img/progress_bar.gif"/>
                </div>
                <div id="vendorRes">
                    <form id="vendorUpdationForm" name="vendorUpdationForm">
                        <div class="row-fluid dis-n">
                            <input type="text" id="vendorId" name="vendorId"/>
                        </div>
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Account Group : <span class="red_star"> *</span></label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorAccountGroup" name="vendorAccountGroup" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Vendor Name : <span class="red_star"> *</span></label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorName" name="vendorName" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Vendor Name3 :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorName3" name="vendorName3" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Address1 : <span class="red_star"> *</span></label>
                                    </div>
                                    <div class="span7">
                                        <textarea id="vendorAddress1" name="vendorAddress1" rows="1" class="input-block-level" ></textarea>
                                        <!--                                        <input type="text" id="vendorAddress1" name="vendorAddress1" autocomplete="off" class="input-block-level"/>    -->
                                    </div>
                                </div>                               
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Address3 :</label>
                                    </div>
                                    <div class="span7">
                                        <textarea id="vendorAddress3" name="vendorAddress3" rows="1" class="input-block-level" ></textarea>
                                        <!--                                        <input type="text" id="vendorAddress4" name="vendorAddress4" autocomplete="off" class="input-block-level"/>    -->
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Search Term:</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorSearchTerm" name="vendorSearchTerm" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">District :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorDistrict" name="vendorDistrict" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">PinCode :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorPinCode" name="vendorPinCode" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Mobile No :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorMobileNo" name="vendorMobileNo" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Email Id :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorEmailId" name="vendorEmailId" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                            </div>

                            <div class="span6">                               
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Title : <span class="red_star"> *</span></label>
                                    </div>
                                    <div class="span2">
                                        <input type="hidden" id="vTitle"/>
                                        <input type="text" id="vendorTitle" name="vendorTitle" autocomplete="off" class="input-block-level" readonly/>    
                                    </div>
                                    <div class="span5">
                                        <select onchange="chanetitlefun();" id="titleOption" class="input-block-level" name="titleOption">
                                            <option value="select">-- Select Title --</option>
                                            <option value="Mr.">Mr.</option>
                                            <option value="Ms.">Ms.</option>                                                                         
                                        </select> 
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Vendor Name2 :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorName2" name="vendorName2" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Vendor Name4 :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorName4" name="vendorName4" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Address2 :</label>
                                    </div>
                                    <div class="span7">
                                        <textarea id="vendorAddress2" name="vendorAddress2" rows="1" class="input-block-level" ></textarea>
                                        <!--                                        <input type="text" id="vendorAddress2" name="vendorAddress2" autocomplete="off" class="input-block-level"/>    -->
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Address4 :</label>
                                    </div>
                                    <div class="span7">
                                        <textarea id="vendorAddress4" name="vendorAddress4" rows="1" class="input-block-level" ></textarea>
                                        <!--                                        <input type="text" id="vendorAddress3" name="vendorAddress3" autocomplete="off" class="input-block-level"/>    -->
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">City :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorCity" name="vendorCity" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>                                
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">State :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorState" name="vendorState" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Telephone No :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorTelNo" name="vendorTelNo" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span4">
                                        <label class="">Fax No :</label>
                                    </div>
                                    <div class="span7">
                                        <input type="text" id="vendorFaxNo" name="vendorFaxNo" autocomplete="off" class="input-block-level"/>    
                                    </div>
                                </div>
                                <div class="row-fluid" style="margin-top: 5px;">
                                    <div class="span4"></div>
                                    <div class="span4"><input style="width: 110px;" type="button" id="user_update_button" name="user_create_button" class="btn btn-primary"  value="Update" onclick="updateVenInputs();"/></div>
                                    <div class="span4"> <input style="width: 110px;" type="button" id="user_update_cancel_button" name="user_create_cancel_button" class="btn btn-warning" value="Cancel" onclick="clearVendorDetails();" /></div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>