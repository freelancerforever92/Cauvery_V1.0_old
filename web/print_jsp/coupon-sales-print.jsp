<%@page import="com.to.CouponSalesHeaderTo"%>
<%@page import="com.to.CouponSalesPrintTo"%>
<%@page import="com.model.GenericModule"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">
<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/style.css'/>"
      type="text/css" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <style>
            @font-face { font-family: Free 3 of 9 Extended; src: url('freefont1.TTF'); } 
        </style> 
        <script language="javascript" type="text/javascript">
            function printPurchaseOrder() {
                var printContent = document.getElementById("printDivID");

                var Win4Print = window.open('', '', 'left=0,top=0,width=640,height=480,top=301,left=365,resizable=1,status=0,toolbar=0');
                Win4Print.document.write(printContent.innerHTML);
                Win4Print.document.close();
                Win4Print.focus();
                Win4Print.print();
                Win4Print.close();
            }
        </script>
    </head>

    <%
        String couponSalesSlrNo = request.getParameter("couponSalesNo");
        CouponSalesHeaderTo couponHeaderTo = GenericModule.getCouponOrderPrint(couponSalesSlrNo);
        request.setAttribute("header", couponHeaderTo);

    %>
    <title>Coupon Sales - ${requestScope.header.salesOrderNo}</title>
    <body>
        <div id="printDivID">
            <table border="0px" style="width: 100%; border: 1px;"cellspacing="0px" cellpadding="0px">
                <thead>
                    <tr>
                        <td width="25%"><img src="../images/cauvery.jpg" height="80"
                                             width="60" style="float: left;" /></td>
                        <td style="text-align: center;position: inherit;margin-left: 850px;">
                            <h4 style="font-size:130%;margin-top: 8px;">CAUVERY</h4>
                            <address style="font-style: normal;margin-top: 8px;">
                                <b> Karnataka State Arts and Craft Emporium</b><br>
                                ( Unit of Karnataka State Handicrafts Development Corpn.LTD )<br>
                                ( A Government of Karnataka Enterprise )<br>
                                #49,Mahatma Gandhi Road,Bangalore-560001
                            </address>
                            <h4>TAX INVOICE - CASH</h4>
                        </td>
                        <td width="25%" style="display: none">Grams:<br> Phone:<br> Fax:<br>
                            Email:<br>
                        </td>
                    </tr>
                    <%--
                    <tr>
                        <td colspan="3" align="center"><h4>TAX INVOICE - CASH</h4></td>
                    </tr>
                    --%>
                    <tr>
                        <td>Counter Bill : <b></b></td>
                        <td>${requestScope.header.salesOrderNo}</td>
                        <td>Bar Code : </td>
                    </tr>
                    <tr>

                        <td colspan="2">
                            Name & Address : <%--${requestScope.header.customeName}  ${requestScope.header.streetOne}
                            ${requestScope.header.streetTwo} ${requestScope.header.country} ${requestScope.header.state} ${requestScope.header.city} ${requestScope.header.zipCode}
                            --%>
                        </td>

                        <td>Date : ${requestScope.header.dateTime}</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="3">
                            <table border="1px" style="width: 100%" cellspacing="0px" cellpadding="0px">
                                <tr>
                                    <th style="font-size:80%;width: 5%;">S.No</th>
                                    <th style="font-size:80%;width: 8%;">Item No</th>
                                    <th style="font-size:80%;width: 5%;">Coupon No</th>
                                    <th style="font-size:80%;width: 8%;">Description</th>
                                    <th style="font-size:80%;width: 5%;">Rate</th>
                                </tr>
                                <c:forEach var="line" items="${requestScope.header.couponSalesPrintTos}">
                                    <tr>
                                        <td style="text-align: center;font-size:72%;">${line.slNo}</td>
                                        <td style="text-align: left;font-size:72%;">${line.couponType}</td>
                                        <td style="text-align: center;font-size:72%;">${line.couponNo}</td>
                                        <td style="text-align: left;font-size:72%;">${line.couponDesc}</td>
                                        <td style="text-align: right;font-size:72%;">${line.couponRate}</td>
                                    </tr>
                                </c:forEach>
                                <td colspan="4" style="text-align: right;font-size:68%;">
                                    Amount in Words : ${requestScope.header.totalValueInText}   ${ "Rupees Only" }
                                </td>
                                <td style="text-align: right;width: 60px;">${requestScope.header.totalValue}</td>
                            </table>
                    </tr>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="2"></td>
                        <!--                    
                              <td colspan="2">E & OE Time : 16:15:19 <br> Goods once
                                                sold cannot be taken back or exchanged.<br> 
                                                TIN No.29520088675 <br>
                                                CST NO.00551697[06/06/90]
                                            </td>-->
                        <td style="text-align: right">
                            Billed By <br><br>${requestScope.header.empName}

                        </td>
                    </tr>
                    <tr>
                        <td colspan="3"><br><br></td>
                    </tr>
                    <tr>
                        <td colspan="3">For Online Shopping,Visit:
                            www.cauveryhandicrafts.net</td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <div style="text-align: center">
            <input type="button" class="btn btn-success" value="Print Page" onclick="printPurchaseOrder()"/>
        </div>
    </body>
</html>