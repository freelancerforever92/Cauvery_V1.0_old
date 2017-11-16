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
                $("#manageQuestion").addClass("active");
            }); 
        </script>

    </head>
    <body>
        <jsp:include page="commonJsp.jsp" /> 
        <div class="clearfix body-sec">

            <div class="clearfix sublist">
                <ul class="clearfix list-s">
                    <li><a href="securityQuestion.jsp">Question List</a></li>
                    <li class="active"><a href="createsecurityQuestion.jsp">Create Question</a></li>                   
                </ul>
            </div>
            <div class="content_sec" style="background: #fff;">                
                <div class="clearfix create-counter">
                        <form id="counterUpdationForm" name="counterUpdationForm">
                            
                            <div class="row-fluid">
                                <div class="span4">
                                    <label>Security Question :</label>
                                </div>
                                <div class="span8">
                                    <textarea name="securityQuestion" id="securityQuestion" class="input-block-level" rows="5"></textarea> 
                                </div>
                            </div>                                        
                            <div class="row-fluid" style="padding-top: 10px;">
                                <div class="span4"></div> 
                                <div class="span4">
                                    <input type="button" style="width: 100px;" class="btn btn-warning" value="Clear" onclick="clearQuestionInfo();">
                                </div> 
                                <div class="span4">
                                    <input type="button" style="width: 100px;" class="btn btn-primary pull-right" value="Create" onclick="createQuestion();">
                                </div>                  
                            </div>
                        </form>
                    </div>                
            </div>
        </div>
    </div>
</body>
</html>