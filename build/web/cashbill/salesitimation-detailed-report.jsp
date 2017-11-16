<%@page import="com.pos.updated.reports.UpdatedSalesIntimationDetail"%>
<%@page import="com.Action.CashBill.SalesIntimationDetail"%>
<%@page import="com.Action.CashBill.CashDailySummaryView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>


<script type="text/javascript">
    $(document).ready(function() {
        $("#userProgress").hide();
        $("#shwDCSRAbstract").width(($(window).width() - ($(window).width() * 0.24)));
        $("#shwDCSRAbstract").height($(window).height() - ($(window).height() * 0.30));
    });
</script>
<style>
    .SIRport #shwSIR{min-width: 1050px!important;max-height:445px!important; }
</style>

<%
    String reportingFromDate = "";
    String reportingToDate = "";
    Date todaydate = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-YYYY");
    request.setAttribute("TDate", ft.format(todaydate));
    SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    request.setAttribute("saveFileName", saveFt.format(todaydate));
    String toDate = request.getParameter("cbToDate").trim();
    String fromDate = request.getParameter("cbFromDate").trim();

    try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tempFromDate = simpleDateFormat.parse(fromDate);
        Date tempToDate = simpleDateFormat.parse(toDate);

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        reportingFromDate = outputDateFormat.format(tempFromDate);
        reportingToDate = outputDateFormat.format(tempToDate);
    } catch (Exception ex) {
        System.out.println("Parse Exception");
    }

    String paymentType = request.getParameter("reportPaymentType").trim();
    String sessionId = request.getParameter("loggedSessionId").trim();
    String typeValue = request.getParameter("typeValue").trim();
    String craftGroups = request.getParameter("craftGroups").trim();
    String vendorIds = request.getParameter("vendorIds").trim();
    String intimationName = request.getParameter("intimationName").trim().toUpperCase();

    if (!((fromDate.equals(
            "")) || (fromDate.equalsIgnoreCase(null)))) {
        if (!((toDate.equals("")) || (toDate.equalsIgnoreCase(null)))) {
            if (!((paymentType.equals("")) || (paymentType.equalsIgnoreCase(null)))) {
                if (!((sessionId.equals("")) || (sessionId.equalsIgnoreCase(null)))) {
                    if (!((typeValue.equals("")) || (typeValue.equalsIgnoreCase(null)))) {
                        //request.setAttribute("header", CashDailySummaryView.SalesIntimationDetailReport(fromDate, toDate, paymentType, sessionId, typeValue));
                       
                    // request.setAttribute("header", SalesIntimationDetail.SalesIntimationDetailReport(fromDate, toDate, paymentType, sessionId, typeValue, vendorIds, craftGroups));
                       request.setAttribute("header", UpdatedSalesIntimationDetail.UpdatedSalesIntimationDetailReport(fromDate, toDate, paymentType, sessionId, typeValue, vendorIds, craftGroups));
                    
                    
                    }
                }
            }
        }
    }
%>

<div class="clearfix SIRport">
    <input type="button" id="dcsraeBck" name="dcsraeBck" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
    <div id="shwSIR" style="overflow: scroll;">
        <c:set var="tempVendorId" scope="session" value="0"/>
        <display:table name="requestScope.header" class="table table-striped display-table"  export="true" sort="list" id="head">
            <display:setProperty name="export.excel" value="true" />
            <display:setProperty name="export.excel.filename" value="${param.intimationName}-DETAIL-REPORT-${param.reportPaymentType}-${saveFileName}.xls"/>
            <display:setProperty name="export.pdf" value="true" />
            <display:setProperty name="export.pdf.filename" value="${param.intimationName}-DETAIL-REPORT-${param.reportPaymentType}-${saveFileName}.pdf"/>
            <display:setProperty name="export.rtf" value="false" />
            <display:setProperty name="export.rtf.filename" value="${param.intimationName}-DETAIL-REPORT-${param.reportPaymentType}-${saveFileName}.rtf"/>
            <display:setProperty name="export.csv" value="false" />
            <display:setProperty name="export.csv.filename" value="${param.intimationName}-DETAIL-REPORT-${param.reportPaymentType}-${saveFileName}.csv"/>
            <display:caption media="html">CAUVERY<br>Karnataka State Arts & Crafts Emporium,No 49,MG Road,Bengaluru  <br>  ${param.intimationName} DETAILED REPORT FROM <b> <%=reportingFromDate%> to <%=reportingToDate%></b> <br> Pay-Type :<b> ${param.reportPaymentType} </b> Generated Date  :<b> <c:out value="${TDate}"  /></b> </display:caption>
            <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road,Bengaluru                                   ${param.intimationName} DETAILED REPORT FROM  <%=reportingFromDate%> to <%=reportingToDate%>                                                    Pay-Type  : ${param.reportPaymentType}  Generated Date  : <c:out value="${TDate}"  /> </display:caption>
            <display:column property="craftGroupName" title=" Craft Group " class="myrow" group="1"/>
            <display:column property="vendorNumber" title=" Vendor Id  " total="true"/>
            <c:set var="vendorId" scope="session" value="${head.vendorNumber}"/>
            <%--
            <display:column  title=" Vendor Name " media="html">
                <c:if test="${head.vendorNumber != vendorId}">
                    <p class="footnotes">-: The End :-</p>
                </c:if>
            </display:column>
            --%>
            <display:column property="vendorName" title=" Vendor Name " total="true"/>
            <display:column property="dateTime" title="  Date/Paytype  "   total="true"/>
            <display:column property="billNo" title=" BillNumber " class="myrow"/>
            <display:column property="manualBilNo" title=" Manual-BillNo "/>
            <display:column property="description" title=" Mat-Id/MatDes "   total="true"/>
            <display:column property="qty" title="  Qty  "   total="true" style="text-align:right"/>
            <display:column  title="  Rate  "   media="html excel pdf rtf">
                <c:if test="${head.rate != 0.0}">
                    <fmt:formatNumber pattern="#0.00"   value="${head.rate}"/>
                </c:if>
            </display:column>
            <display:column property="grossAmt" title=" Gross-Sales "  total="true" style="text-align:right"/>
            <display:column property="diskPer" title=" Disc % " total="true" />
            <display:column property="discAmt" title=" Disc-Amt "  total="true" style="text-align:right"/>
            <%-- <display:column property="packAmt" title="Pack Amt" format="{0,number,0.00} "  total="true"/>--%>
            <display:column property="netAmt" title=" Net-Sales "  total="true" style="text-align:right"/>
            <display:footer media="html">
                <td colspan="13"><br/><br/><br/>
                    <b>Certified that the items mentioned in the consignment sale<br/>
                        intimation have actually been sold and the amount is fully<br/>
                        realized and the amount has been remitted to our bank Account<br/><br/>
                        Assistant&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dy.G.M&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Internal Auditors</b>
                    &nbsp;<br>
                    &nbsp;<br>
                    &nbsp;<br>
                    &nbsp;<br>
                    &nbsp;<br>
                </td>
            </display:footer>
            <display:footer media="excel pdf rtf">

                Certified that the items mentioned in the consignment sale intimation have actually been sold 
                and the amount is fully realized and the amount has been remitted to our bank Account

                Assistant                   Dy.G.M                  Internal Auditors


            </display:footer>
        </display:table>
        <input class="btnPrintDisplay" type="button" onclick="printDiv('shwSIR')" value="PRINT" />
    </div>
</div>
