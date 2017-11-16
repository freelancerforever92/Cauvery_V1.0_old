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
        <script type="text/javascript">
            $(document).ready(function()
            {
                loadCounterReportGrid();
                $("#CounterExport").hide();
                $("#reportmenu").height(($(window).height() - ($(window).height() * 0.31)));
                $("#CounterContentArea").height(($(window).height() - ($(window).height() * 0.99)));
                $("#CounterContentArea").width(($(window).width() - ($(window).width() * 0.10)));
                setSalesHomeDefaultDate();
            });
            function setSalesHomeDefaultDate() {
                var dt = new Date();
                var todayDate = dt.getDate();
                var todayMonth = dt.getMonth() + 1;
                var todayYear = dt.getFullYear();
                var salesDefaultDate = todayYear + "-" + (todayMonth < 10 ? '0' : '') + todayMonth + "-" + (todayDate < 10 ? '0' : '') + todayDate;
                $("#counterFromDate").val(salesDefaultDate.trim());
                $("#counterToDate").val(salesDefaultDate.trim());
            }
        </script>
        <link type="text/css" rel="stylesheet" href="../css/css/bootstrap.css"/>
        <link type="text/css" rel="stylesheet" href="../css/bootstrap-responsive.css"/>
        <link type="text/css" rel="stylesheet" href="../css/style.css"/>
        <style>
            .dataIpWidth{width: 105px;}
            #reportSec{background-color: #f3f3f3;border: #e6e6e6 1px solid;height: 65vh; padding: 15px; border-radius:10px;}
        </style>
        <sj:head jquerytheme="start"/>
    </head>
    <body>
        <div class="row-fluid">
            <div class="span3">
                <div id="reportSec" class="clearfix reportSec">
                    <form id="CounterrwiseAccordionForm" name="CounterrwiseAccordionForm" method="post">
                        <h4><span>Counter Reports</span></h4>
                        <ul class="clearfix">
                            <li><div class="row-fluid"><div class="span5">From Date</div><div class="span7"><sj:datepicker id="counterFromDate" name="counterFromDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-medium dataIpWidth"/></div></div></li>
                            <li><div class="row-fluid"><div class="span5">To Date</div><div class="span7"><sj:datepicker id="counterToDate" name="counterToDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-medium dataIpWidth"/></div></div></li>
                            <li><div class="row-fluid"><div class="span5">Selection Type</div>
                                    <div class="span7">
                                        <select id="cancelledOptionParameter" name="cancelledOptionParameter" class="input-medium" style="width: 115px;">
                                            <option value="Select">Select</option>
                                            <option value="N">Not-Cancelled</option>
                                            <option value="X">Cancelled</option>
                                        </select>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="row-fluid">
                                    <div class="span5">
                                        <input type="button" id="counterReportSearchButton" name="counterReportSearchButton" class="btn btn-primary"  value="Detailed-View" onclick="filterCounterBillRecord();"/>
                                    </div> &nbsp; &nbsp;<input id="CounterExport" name="CounterExport" style="margin-right: 5px;" class="btn btn-success" type="button" onclick="printDiv('gview_gridCounterSummaryView')" value="Detailed Print" />
                                    <div class="span5">
                                        <input type="button" id="counterViewInGrid" name="counterViewInGrid" class="btn btn-warning" value="Abstract-View" onclick="craftwiseCounterReport();"/>
                                    </div> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<input id="CounterExport" name="CounterExport" style="margin-right: 5px;" class="btn btn-success" type="button" onclick="printDiv('gview_counterSummaryGridView1')" value="Abstract Print" />

                                    <div class="span5">
                                        <!--<input type="button" id="CounterExport" name="CounterExport" style="margin-right: 5px;" class="btn btn-success" value="Export" onclick="CounterExportDailyReport();"/>-->
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="row-fluid">
                                    <div class="span5"><label style="font-size: 18px;font-weight: bold;">Rs : </label></div>
                                    <div class="span7"><label id="totalSumValue" style="font-size: 20px;font-weight: bold;"></label></div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span5"><label style="font-size: 18px;font-weight: bold;">Invoices : </label></div>
                                    <div class="span7"><label id="counterBillTotal" style="font-size: 20px;font-weight: bold;"></label></div>
                                </div>
                            </li>
                        </ul>
                    </form>
                </div>
                <%--
                <div id="reportmenu" style="width: 275px;">
                    <sj:accordion id="CounterSummaryRightAccording" autoHeight="true">
                        <sj:accordionItem title="Counterwise Report" id="rghtAcc1">
                            <form id="CounterrwiseAccordionForm" name="CounterrwiseAccordionForm" method="post">
                                <div style="height: 200px;">
                                    <table cellspacing="9px" cellpadding="3px">
                                        <tr>
                                            <td>
                                                <label><b>From Date</b></label>
                                            </td>
                                            <td>
                                                <sj:datepicker id="counterFromDate" name="counterFromDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-medium dataIpWidth"/>
                                            </td>
                                        </tr>

                                    <tr>
                                        <td>
                                            <label><b>To Date</b></label>
                                        </td>
                                        <td>
                                            <sj:datepicker id="counterToDate" name="counterToDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" maxDate="true" cssClass="input-medium dataIpWidth"/>
                                        </td>
                                    </tr>

                                    <tr>
                                        <td>
                                            <label><b>Option </b></label>
                                        </td>
                                        <td>
                                            <select id="cancelledOptionParameter" name="cancelledOptionParameter" class="input-medium" style="width: 115px;">
                                                <option value="Select">Select</option>
                                                <option value="N">Not-Cancelled</option>
                                                <option value="X">Cancelled</option>
                                            </select>
                                        </td>
                                    </tr>
                <%--
                <tr>
                    <td>
                        <label><b>To Date</b></label>
                    </td>
                    <td>
                        <sj:datepicker id="counterToDate" name="counterToDate" changeMonth="true" changeYear="true" showOn="focus" displayFormat="yy-mm-dd" cssClass="input-medium dataIpWidth"/>
                    </td>
                </tr>
               
                <tr style="padding-left: -50px;">
                    <td>
                        <input type="button" id="counterReportSearchButton" name="counterReportSearchButton" class="btn btn-primary"  value="Search" onclick="filterCounterBillRecord();" style="margin-top: 8px;" />
                    </td>
                    <td>
                        <input type="button" id="counterViewInGrid" name="counterViewInGrid" class="btn btn-warning" value="View" onclick="craftwiseCounterReport();" style="margin-top: 8px;margin-left:-1px;"/>
                    </td>
                <%--
                <td>
                    <input type="button" id="cashBillReportPrintButton" name="cashBillReportPrintButton" class="btn btn-danger"  value="Print" onclick="printReport();" style="margin-top: 8px;margin-left: 10px;"/>
                </td>
               
            </tr>
            <tr>
                <td>
                    <input type="button" id="CounterExport" name="CounterExport" class="btn btn-success" value="Export" onclick="CounterExportDailyReport();" style="margin-top: 8px;margin-left:-1px;"/>
                </td>
            </tr>
        </table>
    </div>
    </form>
    </sj:accordionItem>

                <%--
                <sj:accordionItem title="Casherwise Summary" id="rghtAcc2" onclick="#">
                    <div>
                        <sj:datepicker id="" changeMonth="true" changeYear="true" showOn="focus"/>
                        <input type="button" id="" name="" class="btn btn-success"  value="Click"/>
                    </div>
                </sj:accordionItem>
                
            </sj:accordion>

                <div>
                    <table>
                        <tr>
                            <td><b><label style="font-size: 18px;font-weight: bold;  margin-left: 6px;margin-top: 20px;">Rs : </label></b></td>
                            <td><b><label id="totalSumValue" style="font-size: 20px;font-weight: bold;  margin-left: 6px;margin-top: 20px;"></label></b></td>
                        </tr>
                        <tr>
                            <td><b><label style="margin-top: 20px;font-size: 18px;font-weight: bold;">Total bills : </label></b></td>
                            <td><b><label id="counterBillTotal" style="font-size: 20px;font-weight: bold;  margin-left: 6px;margin-top: 20px;"></label></b></td>
                        </tr>
                    </table>
                </div>
            </div>
                --%>
            </div>
        </div>
        <div class="span9">
            <div id="CounterContentArea" name="CounterContentArea"style="float:left;margin-left: 320px;"></div>
        </div>
    </body>
</html>
