<%-- 
    Document   : cashbill-summary-report-home
    Created on : Nov 4, 2014, 10:17:39 AM
    Author     : Administrator
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="../css/style.css"/>
        <link type="text/css" rel="stylesheet" href="../css/bootstrap.css"/>
        <link type="text/css" rel="stylesheet" href="../css/bootstrap-responsive.css"/>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#discPer").hide();
                $("#rptCrfGrpLst").hide();
                $("#craftGroups").hide();
                $("#vendorsIdNumber").hide();
                $("#vendorNumbers").hide();
                $("#userProgress").show();
                loadReportTypes();
                loadCashBillReport();
                loadingRptCraftGroup();
                loadSalesIntimationTypes();
                //clearLoadedCraftGroups();
                $("#export").hide();
                /*TIME_BASED_REPORT */
                //$("#tbrtodate").hide();
                //$("#tbrfromdate").hide();
                /*TIME_BASED_REPORT */
                $("#displaySalesIntimation").hide();
                $("#cashSummaryRightAccording").height(($(window).height() - ($(window).height() * 0.31)));
                //$("#ReportContentArea").height(($(window).height() - ($(window).height() * 0.92)));
                setCashHomeDefaultDate();
            });

            function setCashHomeDefaultDate() {
                var dt = new Date();
                var todayDate = dt.getDate();
                var todayMonth = dt.getMonth() + 1;
                var todayYear = dt.getFullYear();
                var defaultDate = todayYear + "-" + (todayMonth < 10 ? '0' : '') + todayMonth + "-" + (todayDate < 10 ? '0' : '') + todayDate;
                $("#cbFromDate").val(defaultDate.trim());
                $("#cbToDate").val(defaultDate.trim());

            }
        </script>
        <script>
            var expanded = false;
            function showCheckboxes() {
                var checkboxes = document.getElementById("rptCrfGrpLst");
                if (!expanded) {
                    checkboxes.style.display = "block";
                    expanded = true;
                } else {
                    checkboxes.style.display = "none";
                    expanded = false;
                }
            }

            function showVendorCheckboxes() {
                var checkboxes = document.getElementById("vendorNumbers");
                if (!expanded) {
                    checkboxes.style.display = "block";
                    expanded = true;
                } else {
                    checkboxes.style.display = "none";
                    expanded = false;
                }
            }

        </script>
        <style>
            .cashDatePickerWth{ width: 105px;}
            #userProgress{margin-top: 80px;  margin-left: 100px;}
            .exportlinks a{font-weight: 600; color:#ffffff; background-color: #da4f49; padding:6px 10px; border:#cc0000 solid 1px; border-radius: 4px;}
            .exportlinks a:hover{text-decoration: none; background-color: #990000;color:#ffffff;}
            #reportSec{background-color: #f3f3f3;border: #e6e6e6 1px solid;height: 65vh; padding: 15px; border-radius:10px;}
            .multiselect {    }
            .selectBox {
                position: relative;
            }
            .selectBox select {
                width: 100%;
                font-weight: bold;
            }
            .overSelect {
                position: absolute;
                left: 0; right: 0; top: 0; bottom: 0;
            }
            #rptCrfGrpLst,#vendorNumbers {
                z-index: 1001;
                display: none;
                position: absolute;
                margin-top: -10px;
                padding: 10px;
                width: 148px;
                height:150px;
                overflow-y: auto;
                background-color: #fff;
                border: 1px #dadada solid;
            }
            #rptCrfGrpLst,#vendorNumbers label,#vendorNumbers,#vendorNumbers label {
                display: block;
            }
            #rptCrfGrpLst,#vendorNumbers label:hover,#vendorNumbers,#vendorNumbers label:hover {
                background-color: #1e90ff;
            }

        </style>
        <sj:head jquerytheme="start"/>
    </head>
    <body>
        <div class="row-fluid" style="margin-bottom: 10px;">
            <div class="span3">
                <div id="reportSec" class="clearfix reportSec">
                    <form id="CasherwiseAccordionForm" name="CasherwiseAccordionForm" method="post">
                        <input type="hidden" id="hiddenLoginSessionId" name="hiddenLoginSessionId" style="display: none;"/>
                        <h4><span>Daily Sales Report</span></h4>
                        <ul class="clearfix">
                            <li id="normalfromdate"><div class="row-fluid"><div class="span5">From Date</div><div class="span7"><sj:datepicker id="cbFromDate" name="cbFromDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-block-level cashDatePickerWth"/></div></div></li>
                            <li id="normaltodate"><div class="row-fluid"><div class="span5">To Date</div><div class="span7"><sj:datepicker id="cbToDate" name="cbToDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-block-level cashDatePickerWth"/></div></div></li>
                                    <%--TIME_BASED_REPORT --%>
                                    <%--<li id="tbrfromdate"><div class="row-fluid"><div class="span5">From Date</div><div class="span7"><sj:datepicker id="cbFromDateTimeMin" name="cbFromDateTimeMin" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" timepicker="true" timepickerShowSecond="false" timepickerFormat="hh:mm:ss" maxDate="true" cssClass="input-block-level cashDatePickerWth"/></div></div></li>--%>
                                    <%--<li id="tbrtodate"><div class="row-fluid"><div class="span5">To Date</div><div class="span7"><sj:datepicker id="cbToDateTimeMin" name="cbToDateTimeMin" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" timepicker="true" timepickerShowSecond="false" timepickerFormat="hh:mm:ss" maxDate="true" cssClass="input-block-level cashDatePickerWth"/></div></div></li>--%>
                                    <%--TIME_BASED_REPORT --%>

                            <li>
                                <div class="row-fluid">
                                    <div class="span5">Report Type</div>
                                    <div class="span7">
                                        <select id="reportType" name="reportType" class="input-block-level" onchange="enableDisablePaymentType();">
                                            <option value="Select">Select</option>
                                        </select>
                                    </div>
                                </div>
                            </li>

                            <li id="craftGroups">
                                <div class="row-fluid">
                                    <div class="span5">Craft Group</div>
                                    <div class="span7">
                                        <div class="multiselect">
                                            <div class="selectBox" onclick="showCheckboxes();">
                                                <select class="input-block-level">
                                                    <option id="displayCraftGroup">Select</option>
                                                </select>
                                                <div class="overSelect"></div>
                                            </div>
                                            <div id="rptCrfGrpLst"></div>
                                        </div>
                                    </div>
                                </div>
                            </li>

                            <li id="displaySalesIntimation">
                                <div class="row-fluid">
                                    <div class="span5">Sales Type</div>
                                    <div class="span7">
                                        <select  name="selSalesIntimationType" id="selSalesIntimationType" class="input-block-level" onchange="showHideVendorNos();">
                                            <option value="Select">Select</option>
                                        </select>
                                    </div>
                                </div>
                            </li> 

                            <li id="vendorsIdNumber">
                                <div class="row-fluid">
                                    <div class="span5">Vendor</div>
                                    <div class="span7">
                                        <div class="multiselect">
                                            <div class="selectBox" id="vendorCategory" onclick="showVendorCheckboxes();">
                                                <select class="input-block-level">
                                                    <option id="displayVendorList">Select</option>
                                                </select>
                                                <div class="overSelect"></div>
                                            </div>
                                            <div id="vendorNumbers"></div>
                                        </div>
                                    </div>
                                </div>
                            </li>

                            <li>
                                <div class="row-fluid">
                                    <div class="span5">Payment Type</div>
                                    <div class="span7">
                                        <select id="reportPaymentType" name="reportPaymentType" class="input-block-level">
                                            <option value="Select">Select</option>
                                            <option value="CASH">Cash</option>
                                            <option value="CRC">Card</option>
                                            <option value="CASH/CARD">Both( Cash & Card )</option>
                                        </select>
                                    </div>
                                </div>
                            </li>
                            <li id="discPer">
                                <div class="row-fluid">
                                    <div class="span5">Disc %</div>
                                    <div class="span7">
                                        <input type="text" class="input-block-level cashDatePickerWth hasDatepicker" id="discountPer" name="discountPer"/>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="row-fluid">
                                    <div class="span6"><input type="button" id="export" name="export" class="btn btn-success input-block-level" value="Export"  style="display: none;"/></div>
                                    <div class="span6"><input type="button" id="viewOly" name="viewOly" class="btn btn-warning pull-right" value="View" onclick="viewCBDR();"/></div>
                                </div>
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
            <div class="span9">
                <div id="userProgress"> 
                    <p>Loading...</p>
                    <img width="200" alt="uploadingMaterial" src="images/progress_bar.gif"/></div>

                <div id="ReportContentArea"></div>
            </div>
        </div>
        <%--
        float:left;margin-left: 290px;
        
        --%>


    </body>
</html>
