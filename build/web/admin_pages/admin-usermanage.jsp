<%-- 
    Document   : admin-usermanage
    Created on : Aug 22, 2014, 4:42:16 PM
    Author     : Pranesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            function formactive(cellvalue, options, row) {
                var updateStatusTo = 0;
                var btnClassValue;
                var sts = "Activate";
                if (row.employeeStatus == "1") {
                    updateStatusTo = 0;
                    btnClassValue = "btn btn-danger";
                    sts = "De-Activate";
                } else if (row.employeeStatus == "0") {
                    updateStatusTo = 1;
                    btnClassValue = "btn btn-success";
                    sts = "Activate";
                }
                return "<button width='80' style='margin-left: 20px' class='" + btnClassValue + "' onclick='activeEmp(" + row.employeePk + "," + updateStatusTo + ")'>" + sts + "</button>";
            }

            function pwdReset(cellvalue, options, row) {
                if (row.securityQuestion_fk !== 1)
                {
                    return "<button width='80' style='margin-left: 20px' class='btn btn-success' onclick='userPwdReset(" + row.employeePk + ")'>Reset</button>";
                }
                else {
                    return "";
                }
            }
            function userPwdReset(empId) {
                $.getJSON('resetPwd.action?empPk=' + empId, function(data)
                {
                    if (data.updatePwdvalue >= 1)
                    {
                        alert("Password Reset completed");
                         location.reload();
                    }
                    else {
                        alert("Reset Password Process Fail");
                    }
                });
            }

            function activeEmp(empId, updatingStatus) {
                if (updatingStatus !== null && empId !== "") {
                    $("#usersList").jqGrid('setGridParam', {
                        postData: {
                            actEmpid: empId,
                            stus: updatingStatus,
                        }
                    }).trigger("reloadGrid");
                    $("#usersList").jqGrid('setGridParam', {datatype: 'json', postData: {searchString: "", searchField: "", searchOper: ""}}).trigger('reloadGrid');
                    $("#usersList").jqGrid('setGridParam', {datatype: 'Local'});
                }
                else {
                    alert("Try Later");
                }
            }
        </script>
    </head>
    <body>
        <div id="CourseGrid" >
            <s:url id="userDetailUrl" action="userDetailaction"></s:url>
            <sjg:grid
                id="usersList"
                dataType="json"
                href="%{userDetailUrl}"
                loadonce="true"
                pager="true"
                gridModel="usersList"
                rowList="13,50"
                rowNum="13"
                rownumbers="true"
                navigator="true"
                navigatorSearch="false"
                onPagingTopics="grdcourseopt"
                navigatorSearchOptions="{sopt:['eq']}"
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
                height="380"
                autowidth="true"                
                >
                <sjg:gridColumn name="employeePk" width="10" title="PK"  key="true" search="false" hidden="true"/>
                <sjg:gridColumn name="securityQuestion_fk" width="10" title="securityQuestion_fk"  key="true" search="false"  hidden="true"/>

                <sjg:gridColumn name="employeeId" width="90" index="employeeId" align="center" title="EmployeeId" />
                <sjg:gridColumn name="plantId" width="85" index="plantId" align="center" title="PlantId" />
                <sjg:gridColumn name="employeeName" width="210" index="employeeName" title="Employee Name" />
                <sjg:gridColumn name="employeeType" width="150" title="Employee Type" index="employeeType"/>
                <sjg:gridColumn name="employeeCreatedDate" width="190" align="center" title="CreatedDate" index="employeeCreatedDate"/>
                <sjg:gridColumn name="updatedDate" width="193" align="center" title="Last UpdatedDate" index="updatedDate"/>

                <sjg:gridColumn name="employeeStatus" width="10" title="Status" hidden="true" index="employeeStatus"/>
                <sjg:gridColumn name="employeePk" width="135" index="employeePk" search="false" title="Action"  key="true" formatter="formactive" editable="false" sortable="false" />

                <sjg:gridColumn name="employeePwdReset" width="10" title="Status" hidden="true" index="employeePwdReset"/>
                <sjg:gridColumn name="employeePk" width="135" index="employeePk" search="false" title=""  key="true" formatter="pwdReset" editable="false" sortable="false" />
            </sjg:grid>
        </div>
    </body>
</html>
