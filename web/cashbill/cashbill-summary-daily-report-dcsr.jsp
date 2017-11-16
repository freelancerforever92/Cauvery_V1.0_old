<%@page import="com.Action.CashBill.DcsrReport"%>
<%@page import="com.pos.updated.reports.UpdatedDcsrReports"%>
<%@page import="java.util.Calendar"%>
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
            $(document).ready(function() {
                $("#userProgress").hide();
            });
        </script>
    </head>
    <%
        String dcsrReportFromDate = "";
        String dcsrReportToDate = "";
        Date todaydate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String todate = day + "-" + month + "-" + year;
        //request.setAttribute("TDate", ft.format(todate2));
        request.setAttribute("TDate", todate);
        SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        //request.setAttribute("saveFileName", saveFt.format(todate2));
        request.setAttribute("saveFileName", todate);
        String toDate = request.getParameter("cbToDate").trim();
        String fromDate = request.getParameter("cbFromDate").trim();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tempFromDate = simpleDateFormat.parse(fromDate);
            Date tempToDate = simpleDateFormat.parse(toDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-YYYY");
            dcsrReportFromDate = outputDateFormat.format(tempFromDate);
            dcsrReportToDate = outputDateFormat.format(tempToDate);
        } catch (Exception ex) {
            System.out.println("Parse Exception :: " + ex.getMessage());
        }

        String paymentType = request.getParameter("reportPaymentType").trim();
        String sessionId = request.getParameter("loggedSessionId").trim();
        //String craftGroups = request.getParameter("craftGroups").trim();
        /*BLOCKED DUE TO PERFORMANCE DELAY.REVERTING TO OLD PROCESS */
        //String vendorIds = request.getParameter("vendorIds").trim();
        /*BLOCKED DUE TO PERFORMANCE DELAY.REVERTING TO OLD PROCESS */
        //String vendorType = request.getParameter("vendorType").trim();
        request.setAttribute("fromDate", toDate);
        if (!((fromDate.equals("")) || (fromDate.equalsIgnoreCase(null)))) {
            if (!((toDate.equals("")) || (toDate.equalsIgnoreCase(null)))) {
                if (!((paymentType.equals("")) || (paymentType.equalsIgnoreCase(null)))) {
                    if (!((sessionId.equals("")) || (sessionId.equalsIgnoreCase(null)))) {
                        //List list = DcsrReport.displayCashDailySummaryReportDCSR(fromDate, toDate, paymentType, sessionId);
                        List list = UpdatedDcsrReports.newUpdatedDcsrReportPos(fromDate, toDate, paymentType, sessionId);
                        request.setAttribute("header", list);
                    }
                }
            }
        }
        int dcsrSNum = 1;
    %>
    <style>
        .dcsrreport #shwDCSR{min-width: 1050px!important;max-height:500px!important; }
    </style>
    <body>
        <input type="hidden" id="dcsrPdfFDate" name="dcsrPdfFDate" style="display: none;" value="<%=request.getParameter("cbFromDate").trim()%>"/>
        <input type="hidden" id="dcsrPdfTDate" name="dcsrPdfTDate" style="display: none;" value="<%=request.getParameter("cbToDate").trim()%>"/>
        <input type="hidden" id="dcsrPdfRptTyp" name="dcsrPdfRptTyp" style="display: none;" value="<%=request.getParameter("reportPaymentType").trim()%>"/> 
        <input type="hidden" id="dcsrPdfSessionId" name="dcsrPdfSessionId" style="display: none;" value="<%=request.getParameter("loggedSessionId").trim()%>"/>
        <div class="clearfix dcsrreport">
            <input type="button" id="dcsrBck" name="dcsrBck" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
            <div id="shwDCSR" style="overflow: scroll;">
                <display:table name="requestScope.header"  style="margin:10px;margin-top:15px"  class="table table-striped display-table" export="true" sort="list" id="head"  >
                    <display:setProperty name="export.excel" value="true"/>
                    <display:setProperty name="export.excel.filename" value="DCSR-${param.reportPaymentType}-${saveFileName}.xls"/>
                    <display:setProperty name="export.pdf" value="true" />
                    <display:setProperty name="export.pdf.filename" value="DCSR-${param.reportPaymentType}-${saveFileName}.pdf"/>
                    <display:setProperty name="export.rtf" value="false" />
                    <display:setProperty name="export.rtf.filename" value="DCSR-${param.reportPaymentType}-${saveFileName}.rtf"/>
                    <display:setProperty name="export.csv" value="false" />
                    <display:setProperty name="export.csv.filename" value="DCSR-${param.reportPaymentType}-${saveFileName}.csv"/>
                    <display:caption media="html">CAUVERY -0 <br>Karnataka State Arts & Crafts Emporium,No 49,MG Road,Bengaluru <br> DCSR FROM <b> <%=dcsrReportFromDate%> to <%=dcsrReportToDate%></b> <br> Pay-Type : <b>${param.reportPaymentType}</b>  Run Date  : <b><c:out value="${TDate}"  /></b> </display:caption>
                    <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road,Bengaluru                                   DCSR FROM  <%=dcsrReportFromDate%> to <%=dcsrReportToDate%>                                                                                              Pay-Type : ${param.reportPaymentType}  Generated Date : <c:out value="${TDate}"  /> </display:caption>
                    <%-- <c:if test="${head.count !=0}">--%>
                   DATE  : <c:out value="${head.fromDD}"></c:out>
                   MONTH : <c:out value="${head.fromMM}"></c:out>
                   YEAR  : <c:out value="${head.fromYY}"></c:out>
                    <c:if test="${head.dcsrDate !='Cashier Sub-Total'}">
                        <c:if test="${head.dcsrDate !='Sub-Total'}">
                            <c:if test="${head.dcsrDate !='Grand- Total'}">
                                <c:if test="${head.dcsrDate !='Cashier Grand-Total'}">
                                    <display:column value="<%=dcsrSNum++%>" title="S.No" /> 
                                </c:if>   
                            </c:if>
                        </c:if>
                    </c:if>
                    
                    
                    
                    <%-- </c:if>--%>
                    <display:column property="dcsrDate" title="            " group="1"/>
                    <display:column property="cashInvoiceNumber" title="INVOICE-NO - PaymentType" />
                    <display:column property="manualBillNo" title="Manual Bill No"/>
                    <display:column property="grossAmountFloat" title="Gross Amt" format="{0,number,0.00} "   />
                    <display:column property="discAmountFloat" title="Dis Amt"  format="{0,number,0.00} " />
                    <display:column property="netAmountFloat" title="Net Amt"  format="{0,number,0.00} "  />
                    
                    <c:choose>
                       <c:when test="${head.fromDD le 30 && head.fromMM le 6 || head.fromMM le 06 && head.fromYY le 2017 }">
                            <display:column property="vatAmountFloat" title="VAT Amt"  format="{0,number,0.00} "  />
                      </c:when>
                       <c:otherwise>
                       <%--<display:column property="gstPercentage" title="GST Per"  format="{0,number,0.00}"/>--%>
                           <display:column property="sgstAmountFloat" title="SGST Amt"  format="{0,number,0.00} "  />
                           <display:column property="cgstAmountFloat" title="CGST Amt"  format="{0,number,0.00} "  />
                       </c:otherwise>
                   </c:choose>
                                      
                    <display:column property="packAmountFloat" title="Pkg Amt"  format="{0,number,0.00} " />
                 <%--  <display:column property="cashBillAmountWROFloat" title="Total-Amt WROF"  format="{0,number,0.00} " /> --%>
                    <display:column property="cashBillAmountFloat" title="Total Amt"  format="{0,number,0.00} " />
                    <display:column property="empName" title="CASHIER-NAME"/>
                    <display:footer media="html">
                        <td colspan="11">COUNTER SUMMARY FOR <%=dcsrSNum - 1%> INVOICES</td> 
                    </display:footer>
                    <display:footer media="excel pdf rtf">
                        COUNTER SUMMARY FOR <%=dcsrSNum - 1%> INVOICES
                    </display:footer>
                </display:table>
                <input class="btnPrintDisplay" type="button" onclick="printDiv('shwDCSR')" value="PRINT" />
            </div>
        </div>
    </body>
</html>
