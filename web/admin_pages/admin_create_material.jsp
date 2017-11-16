

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
        <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
        <script src="../js/jquery-admin-index.js" type="text/javascript"></script>


        <script>
            $(document).ready(function (data)
            {
                $("#manageMaterial").addClass("active");
                $(".res").hide();
                $("#progress").hide();
                var idjS = '<%=request.getParameter("id")%>';
                if (idjS === '1') {
                    $(".res").show();
                    $("#progress").hide();
                }
            });
            function showprogress(evt)
            {
                if ($("#userImage").val() !== "") {
                    $(".res").hide();
                    $("#progress").show();
                } else {
                    alert("Please Select File To UpLoad...!");
                    evt.preventDefault();
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
                    <li class="active"><a href="admin_create_material.jsp">Upload Material</a></li>
                    <li><a href="admin_edit_material.jsp">Update Material</a></li>
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">
                <div class="clearfix downloadPosition"><a href="../admin_pages/sample_doc/POS-Sample-Material-Format.xls" class="btn btn-primary">Download Sample Format</a></div>

                <form style="width:30%; margin: 0 auto;" action="materialUploading" name="materialUploading" method="post" enctype="multipart/form-data">
                    <div class="row-fluid">
                        <div class="span8"><input class="file" type="file" id="userImage" name="userImage"></div>
                        <div class="span4"><input onclick="showprogress(event);" type="submit" value="Upload" class="btn btn-success"></div>

                    </div>
                </form>
                <div id="progress" class="t-c"> 
                    <p>Please wait uploading...</p>
                    <img alt="uploadingMaterial" src="bootstrap/img/progress_bar.gif"/>
                </div>
                <div class="res"> 
                    <s:set name="val" value="fileTypeVal" />
                    <s:if test="%{#val==1}">
                        <div class="clearfix"> 
                            <div class="pull-right" style="width: 100%; margin-top: 160px;"> 
                                <%int CountSucess = 0;%>
                                <%int totalCount = 0;%>
                                <s:iterator value="meterialUploadingAL">

                                   
                                    <s:if test="resField=='Success'">
                                        <%++CountSucess;%>
                                    </s:if>
                                </s:iterator>
                                <div class="row-fluid">
                                    <div class="span6">
                                        <h4 class="t-c">Duplicate Material List</h4>
                                        <div class="dtable">
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>S.No</th>
                                                        <th>MATERIAL ID</th>
                                                        <th>DESCRIPTION</th>
                                                    </tr>
                                                </thead>
                                                <%int CountDup = 0;%>
                                                <tbody>
                                                    <s:iterator value="meterialUploadingAL">
                                                        <s:if test="resField=='Duplicate'">
                                                            <tr>
                                                                <td><%=++CountDup%>
                                                                <td> <s:property value="meterialNo"/></td>
                                                                <td> <s:property value="meterialDesc"/></td>
                                                                
                                                            </tr>
                                                        </s:if>

                                                    </s:iterator>
                                                </tbody> 
                                            </table> 
                                        </div>                                    
                                    </div>
                                    <div class="span6">
                                        <h4 class="t-c">Failed to Upload List</h4>
                                        <div class="dtable">    
                                            <table class="table table-bordered">
                                                <%int CountFail = 0;%>

                                                <tr>
                                                    <th>S.No</th>
                                                    <th>MATERIAL ID</th>
                                                    <th>DESCRIPTION</th>
                                                </tr>

                                                <tbody>
                                                    <s:iterator value="meterialUploadingAL">
                                                        <s:if test="resField=='Fail'">
                                                            <tr>
                                                                <td> <%=++CountFail%></td>
                                                                <td> <s:property value="meterialNo"/></td>
                                                                <td> <s:property value="meterialDesc"/></td>
                                                               
                                                            </tr>
                                                        </s:if>
                                                    </s:iterator>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div style="width: 60%; margin: 0 auto;">
                                <table class="table table-bordered">
                                    <tr>
                                        <th>Total Material Count</th>
                                            <%totalCount = CountSucess + CountDup + CountFail;%>
                                        <td><%=totalCount%></td>
                                    </tr>
                                    <tr>
                                        <th>Uploaded Material Count</th>
                                        <td><%=CountSucess%></td>
                                    </tr>

                                    <tr>
                                        <th>Duplicate Material Count</th>
                                        <td><%=CountDup%></td>
                                    </tr>

                                    <tr>
                                        <th>Failed to Material Count</th>
                                        <td><%=CountFail%></td>
                                    </tr>

                                </table>
                            </div>
                        </div>
                                    
                                    <br><br>
                                    <div class="clearfix row-fluid">
                                        
                                        <div class="span6">
                                             <h4 class="t-c">Success  to MatVen Upload List</h4>
                                            <div class="dtable">
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>S.No</th>
                                                        <th>MATERIAL ID</th>
                                                        <th>Vendor ID</th>
                                                        <th>Craft Group</th>
                                                    </tr>
                                                </thead>
                                                <%int metVenCountVal = 0;%>
                                                <tbody>
                                                    <s:iterator value="meterialVenUploadingAL">
                                                        <s:if test="resVendorField=='SUCCESS'">
                                                            <tr>
                                                                <td><%=++metVenCountVal%>
                                                                <td> <s:property value="meterialNo"/></td>
                                                                <td> <s:property value="VendorId"/></td>
                                                                <td> <s:property value="craftGroup"/></td>
                                                                
                                                              
                                                            </tr>
                                                        </s:if>

                                                    </s:iterator>
                                                </tbody> 
                                            </table> 
                                        </div> 
                                            
                                        </div>
                                                <div class="span6">
                                                     <h4 class="t-c">Fail  to MatVen Upload List</h4>
                                                    <div class="dtable">
                                            <table class="table table-bordered">
                                                <thead>
                                                    <tr>
                                                        <th>S.No</th>
                                                        <th>MATERIAL ID</th>
                                                        <th>Vendor ID</th>
                                                        <th>Craft Group</th>
                                                    </tr>
                                                </thead>
                                                <%int metVenFailVal = 0;%>
                                                <tbody>
                                                    <s:iterator value="meterialVenUploadingAL">
                                                        <s:if test="resVendorField=='FAIL'">
                                                           <tr>
                                                                <td><%=++metVenCountVal%>
                                                                <td> <s:property value="meterialNo"/></td>
                                                                <td> <s:property value="VendorId"/></td>
                                                                <td> <s:property value="craftGroup"/></td>
                                                                
                                                                
                                                            </tr>
                                                        </s:if>

                                                    </s:iterator>
                                                </tbody> 
                                            </table> 
                                        </div> 
                                                </div>
                                        
                                        
                                        
                                    </div>
                    </s:if>
                    <s:else>
                        <div class="clearfix t-c" style="width: 20%; margin: 0 auto; margin-top: 100px;"><div class="alert-error" style="padding: 10px; font-weight: 600; font-size:15px; border-radius: 10px; margin-right: 50px;">Invalid File Format<br> <span style="font-size: 10px;">Please Select Excel File Only</span></div></div>
                            </s:else>
                </div>

            </div>



        </div>
    </body>
</html>