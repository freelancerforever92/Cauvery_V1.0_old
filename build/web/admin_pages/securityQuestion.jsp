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

        <script type="text/javascript" src="../js/jquery-1.10.1.min.js"></script>


        <script src="../js/jquery-admin-index.js" type="text/javascript"></script>
        <sj:head jquerytheme="start"/>

        <script type="text/javascript">
            $(document).ready(function() {
                $("#manageQuestion").addClass("active");
            });
            function editSecQuestion(cellvalue, options, row) {
                return "<button class='btn btn-success'  onclick='editQuestion(" + row.securityPk + ");'>Edit</button>";
            }
            function editQuestion(secPk)
            {
                if (secPk !== 0) {
                    $.getJSON('getQuestionAction.action?securityPk=' + secPk, function(data)
                    {
                        $("#sPk").val(secPk);
                        $("#sQuestion").val(data.securityQuestion);
                        $("#myModal1").show();
                    });
                }
                else {
                    alert("Oop's Primary key value is not getting");
                }

            }

            function closepopup() {
                $("#myModal1").hide();
            }

            function updateQuestion() {
                if ($("#sQuestion").val() !== "") {
                    $.getJSON('updateQuestionAction.action?securityPk=' + $("#sPk").val().trim(), '?&securityQuestion=' + $("#sQuestion").val().trim(), function(data)
                    {
                        if (data.questionsCount <= 0) {
                            if (data.updateQuestionValue >= 1)
                            {
                                alert("Successfully Updated");
                                $("#myModal1").hide();
                                location.reload();
                            }
                            else {
                                alert("Updating Failed");
                            }
                        }
                        else {
                            alert("Question allready exist");
                        }
                    });
                }
                else {
                    alert("Oop's Security Question is Missing");
                    $("#sQuestion").focus();
                }
            }

        </script>
       
    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li class="active"><a href="securityQuestion.jsp">Question List</a></li>
                    <li><a href="createsecurityQuestion.jsp">Create Question</a></li>                   
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">

                <s:url id="securityQuestionUrl" action="securityQuestionAction"></s:url>
                <sjg:grid
                    id="questionsList"
                    dataType="json"
                    href="%{securityQuestionUrl}"
                    loadonce="true"
                    pager="true"
                    gridModel="questionsList"
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
                    <sjg:gridColumn name="securityPk" index="securityPk" title="" hidden="true" />
                    <sjg:gridColumn name="securityQuestion" width="380" index="securityQuestion" title="Security Question" />
                    <sjg:gridColumn name="securityCreateDate" width="240" index="securityCreateDate" title="Security CreateDate" />
                    <sjg:gridColumn name="securityUpdatedDate" width="240" index="securityUpdatedDate" title="Security UpdatedDate" />                    
                    <sjg:gridColumn name="securityPk" index="securityPk" width="165" align="center"  search="false" title="#"  formatter="editSecQuestion" />
                </sjg:grid>
            </div>

                

        </div>
    </div>
    <div id="myModal1" class="clearfix dis-n popupbox" tabindex="1" role="dialog">
                    <div class="popupcontentbox">
                <div class="modal-header">
                    <button type="button" class="close" onclick="closepopup();">×</button>
                    <h3>Update Security Question</h3>
                </div>
                <div class="modal-body">
                    <input id="sPk" type="hidden" class="input-block-level">
                    <input id="sQuestion" type="text" class="input-block-level" >
                </div>
                <div class="modal-footer">
                    <button class="btn" onclick="closepopup();">Close</button>
                    <button class="btn btn-primary" onclick="updateQuestion();">Update</button>
                </div>
                    </div>
            </div>
</body>
</html>