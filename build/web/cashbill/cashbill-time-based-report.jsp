<%@page import="com.Action.CashBill.CashBillTimeBased"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<script type="text/javascript">
    $(document).ready(function() {
        $("#userProgress").hide();
    });
</script>
<style>
    .TBRRport #shwTBR{min-width: 1050px!important;max-height:530px!important; }
</style>
<%
    String reportingSummaryFromDate = "";
    String reportingSummaryToDate = "";
    Date todaydate = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-YYYY");
    request.setAttribute("TDate", ft.format(todaydate));
    SimpleDateFormat saveFt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    request.setAttribute("saveFileName", saveFt.format(todaydate));
    String fromDate = request.getParameter("cbFromDate").trim();
    String toDate = request.getParameter("cbToDate").trim();

   String FromDate=fromDate;
   String ToDate=toDate;
    
    String paymentType = request.getParameter("reportPaymentType").trim();
    String sessionId = request.getParameter("loggedSessionId").trim();

    if (!((fromDate.equals("")) || (fromDate.equalsIgnoreCase(null)))) {
        if (!((toDate.equals("")) || (toDate.equalsIgnoreCase(null)))) {
            if (!((paymentType.equals("")) || (paymentType.equalsIgnoreCase(null)))) {
                if (!((sessionId.equals("")) || (sessionId.equalsIgnoreCase(null)))) {
                    request.setAttribute("header", CashBillTimeBased.CashBillTimeBasedReport(fromDate, toDate, paymentType, sessionId));

                }
            }
        }
    }
%>
<div class="clearfix TBRRport">
    <input type="button" id="dsctSISBackButton" name="dsctSISBackButton" class="btn btn-danger" value="Back" style="margin-bottom: 5px;" onclick="load_CashBillSalesForm();"/>
    <div id="shwTBR" style="overflow: scroll;">
        <%int i=1;%>
        <display:table name="requestScope.header" class="table table-striped display-table"  export="true" sort="list" id="head">
            <display:setProperty name="export.excel" value="true" />
            <display:setProperty name="export.excel.filename" value="${param.intimationName}-SUMMARY-REPORT-${param.reportPaymentType}-${saveFileName}.xls"/>
            <display:setProperty name="export.pdf" value="true" />
            <display:setProperty name="export.pdf.filename" value="${param.intimationName}-SUMMARY-REPORT-${param.reportPaymentType}-${saveFileName}.pdf"/>
            <display:setProperty name="export.rtf" value="false" />
            <display:setProperty name="export.rtf.filename" value="${param.intimationName}-SUMMARY-REPORT-${param.reportPaymentType}-${saveFileName}.rtf"/>
            <display:setProperty name="export.csv" value="false" />
            <display:setProperty name="export.csv.filename" value="${param.intimationName}-SUMMARY-REPORT-${param.reportPaymentType}-${saveFileName}.csv"/>
            <display:caption media="html">CAUVERY <br/> Karnataka State Arts & Crafts Emporium ,No 49,MG Road,Bengaluru<br>  ${param.intimationName} TIME BASED REPORT FOR  <br><b> <%=FromDate%> to <%=ToDate%></b>  Generated Date  :<b> <c:out value="${TDate}"  /></b> </display:caption>
            <display:caption media="excel pdf rtf">CAUVERY                                                                                                                              Karnataka State Arts & Crafts Emporium, No 49,MG Road, Bengaluru                                   ${param.intimationName} SUMMARY REPORT FOR  <%=FromDate%> to <%=ToDate%>                                                     Generated Date  : <c:out value="${TDate}"  /> </display:caption>

            <display:column  title="S.No" >
                <c:if test="${head.unitPrice !='Total' }">
                    <%=i%>
                    <%i++;%>
                </c:if>
            </display:column>
            <display:column property="invoiceNumber" title="Invoice Number" />
            <display:column property="item" title="Item"  />
            <display:column property="materialNumber" title="Material Number"  />
            <display:column property="materialDesc" title="Material Desc"  />
            <display:column property="craftGroup" title="Craft Group" />
            <display:column property="vendorNumber" title="Vendor Number" />
            <display:column property="unitPrice" title="Unit Price" />

            <display:column property="qty" title="Quantity" total="true" format="{0,number,0.000} "/>
            <display:column property="grossValue" title="Gross Amt" format="{0,number,0.00} " />
            <display:column property="dispPer" title="Disp Per" />
            <display:column property="disValue" title="Disc Amt" format="{0,number,0.00} " />
            <display:column property="vatPer" title="Vat Per" />
            <display:column property="vatValue" title="Vat Value" format="{0,number,0.00} " />
            <display:column property="calcValue" title="Calc Value" format="{0,number,0.00} " />
            <display:column property="packCharge" title="Pack Charge" format="{0,number,0.00} " />
            <display:column property="dateTime" title="DateTime" />
            <display:footer media="html">
                <td colspan="11"><br/><br/><br/>
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

                Certified that the items mentioned in the consignment sale intimation have actually been sold and the amount is fully realized 
                and the amount has been remitted to our bank Account

                Assistant                   Dy.G.M                  Internal Auditors


            </display:footer>
        </display:table>
        <input class="btnPrintDisplay" type="button" onclick="printDiv('shwTBR')" value="PRINT" />
    </div>
</div>