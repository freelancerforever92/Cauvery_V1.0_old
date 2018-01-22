<%@page import="com.pos.updated.reports.UpdatedDcsrAbstract"%>
<%@page import="com.Action.CashBill.DcsrAbstract"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.to.CashHeaderDCSR"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.to.CashHeaderDTO"%>
<%@page import="com.Action.CashBill.CashDailySummaryView" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            function exportToexcel(e)
            {
                window.open('data:application/vnd.ms-excel,' + encodeURIComponent($('#shwDCSRAbstract').html()));
                e.preventDefault();
            }
            $(document).ready(function() {
                $("#userProgress").hide();
                $("#shwDCSRAbstract").width(($(window).width() - ($(window).width() * 0.24)));
                $("#shwDCSRAbstract").height($(window).height() - ($(window).height() * 0.30));
            });
        </script>
        <style>
            .dcsrareport #shwDCSRAbstract{min-width: 1050px!important;max-height:530px!important; }
        </style>
    </head>
    <%
        String dcsrReportAbsFromDate = "";
        String dcsrReportAbsToDate = "";
        Date todaydate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("TDate", ft.format(todaydate));
        SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        request.setAttribute("saveFileName", saveFt.format(todaydate));
        String toDate = request.getParameter("cbToDate").trim();
        String fromDate = request.getParameter("cbFromDate").trim();
        //String vendorIds = request.getParameter("vendorIds").trim();
        // String vendortype = request.getParameter("vendorType").trim();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tempFromDate = simpleDateFormat.parse(fromDate);
            Date tempToDate = simpleDateFormat.parse(toDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            dcsrReportAbsFromDate = outputDateFormat.format(tempFromDate);
            dcsrReportAbsToDate = outputDateFormat.format(tempToDate);
        } catch (Exception ex) {
            System.out.println("Parse Exception");
        }
        String paymentType = request.getParameter("reportPaymentType").trim();
        String sessionId = request.getParameter("loggedSessionId").trim();
        String craftGroups = request.getParameter("craftGroups").trim();
        request.setAttribute("fromDate", toDate);
        if (!((fromDate.equals("")) || (fromDate.equalsIgnoreCase(null)))) {
            if (!((toDate.equals("")) || (toDate.equalsIgnoreCase(null)))) {
                if (!((paymentType.equals("")) || (paymentType.equalsIgnoreCase(null)))) {
                    if (!((sessionId.equals("")) || (sessionId.equalsIgnoreCase(null)))) {
                        //request.setAttribute("header", DcsrAbstract.displayCashDailySummaryAbstractReportDCSRA(fromDate, toDate, paymentType, sessionId, vendorIds, vendortype));
                        request.setAttribute("header", UpdatedDcsrAbstract.UpdatedDcsrAbstractReport(fromDate, toDate, paymentType, sessionId, craftGroups));
                    }
                }
            }
        }
        int dcsrAbsSNO = 1;
    %>
    <body>
        <div class="clearfix dcsrareport">
            <input type="button" id="dcsraBck" name="dcsraBck" class="fa fa-reply btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
            <div id="shwDCSRAbstract" style="overflow: scroll;">

                <display:table name="requestScope.header" class="table table-striped display-table" export="true" sort="list" id="head"  >

                    <display:setProperty name="export.excel" value="true" />
                    <display:setProperty name="export.excel.filename" value="DCSR-Abstract-${param.reportPaymentType}-${saveFileName}.xls"/>
                    <display:setProperty name="export.pdf" value="true" />
                    <display:setProperty name="export.pdf.filename" value="DCSR-Abstract-${param.reportPaymentType}-${saveFileName}.pdf"/>
                    <display:setProperty name="export.rtf" value="false" />
                    <display:setProperty name="export.rtf.filename" value="DCSR-Abstract-${param.reportPaymentType}-${saveFileName}.rtf"/>
                    <display:setProperty name="export.csv" value="false" />
                    <display:setProperty name="export.csv.filename" value="DCSR-Abstract-${param.reportPaymentType}-${saveFileName}.csv"/>
                    <display:caption media="html">CAUVERY <br>Karnataka State Arts & Crafts Emporium,No 49,MG Road,Bengaluru <br> DCSR Abstract FROM <b> <%=dcsrReportAbsFromDate%> to <%=dcsrReportAbsToDate%></b> <br> Pay-Type :<b>${param.reportPaymentType}</b>   Generated Date  : <b><c:out value="${TDate}"  /></b> </display:caption>
                    <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road,Bengaluru                                   DCSR Abstract FROM  <%=dcsrReportAbsFromDate%> to <%=dcsrReportAbsToDate%>                                                                            Pay-Type : ${param.reportPaymentType}  Generated Date  : <c:out value="${TDate}"  /> </display:caption>
                    <c:if test="${head.dcsrDate !='Grand- Total' }">
                        <display:column value="<%=dcsrAbsSNO++%>" title="S.No" />    
                    </c:if>

                    <display:column property="craftGroup" title="Counter-Names" group="1"/>
                    <display:column property="dcsrDate" title=" Date "/>
                    <display:column property="grossAmountFloat" title="Gross Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="discAmountFloat" title="Dis Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="netAmountFloat" title="Net Amt"  format="{0,number,0.00} "  total="true"/>

                    <c:choose>
                        <c:when test="${head.fromDD le 30 && head.fromYY le 2017 &&( head.fromMM le 6 || head.fromMM le 06 )}">
                            <display:column property="vatAmountFloat" title="VAT Amt"  format="{0,number,0.00} "  total="true"/>
                        </c:when>
                        <c:otherwise>
                            <display:column property="sgstAmountFloat" title="SGST Amt"  format="{0,number,0.00} "  />
                            <display:column property="cgstAmountFloat" title="CGST Amt"  format="{0,number,0.00} "  />
                        </c:otherwise>
                    </c:choose>

                    <display:column property="packAmountFloat" title="Pkg Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:column property="cashBillAmountFloat" title="Total Amt"  format="{0,number,0.00} "  total="true"/>
                    <display:footer media="html">
                        <td colspan="8">COUNTER SUMMARY FOR <%=dcsrAbsSNO - 1%> INVOICES</td>
                    </display:footer>
                    <display:footer media="excel pdf rtf">
                        COUNTER SUMMARY FOR <%=dcsrAbsSNO - 1%> INVOICES 
                    </display:footer>
                </display:table>
                <input class="btnPrintDisplay" type="button" onclick="printDiv('shwDCSRAbstract')" value="PRINT" />
            </div>
        </div>
    </body>
</html>
