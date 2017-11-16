<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<head>
    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#couponType").focus();
            $("#div-customerInfo").hide();
            couponTypesDropDown();
        });
        function showCustomerInfodiv()
        {
            $("#div-customerInfo").show();
        }
    </script>

    <style type="text/css">
        .black_overlay{
            display: none;
            position: fixed;
            top: 0%;
            left: 0%;
            width: auto;
            height: auto;
            background-color:#b0c4de;
            z-index:1001;
            -moz-opacity: 0.8;
            opacity:.80;
            filter: alpha(opacity=80);
        }
        .white_content {
            display: none;
            position: fixed;
            top: 30%;
            left: 40%;
            width: 16%;
            height: 20;
            padding: 16px;
            border: 2px solid #00FF00;
            background-color: white;
            z-index:1002;
            overflow: auto;
        }
    </style>
    <style type="text/css">

        .white_contentcustomerInfo {
            display: none;
            position: fixed;
            top: 30%;
            left: 35%;
            width: 28%;
            height: 30%;
            padding: 16px;
            border: 2px solid #ffff00; 
            background-color: #F5F5F5;
            z-index:1002;
            overflow: auto;
        }


    </style>



</head>
<form id="couponSalesForm" name="couponSalesForm" method="post" >
    <legend>Coupon Sales
        <input type="button" id="couponSalesClearbutton" name="couponSalesClearbutton" class="btn btn-danger" onclick="clearAllValues();" value="Clear" style="position: relative;float: right;"/>
        <input type="button" id="couponSalesCustomerInfo" name="couponSalesCustomerInfo" class="btn btn-success" onclick="showCustomerInfodiv();" value="Customer Info" style="position: relative;float: right;margin-right: 5px;"/>
        <input type="button" id="couponSalesBackButton" name="couponSalesBackButton" class="btn btn-warning" onclick="load_CashBillSalesForm();" value="Back" style="position: relative;float: right;margin-right: 5px;"/>
    </legend>

    <div class="control-group">


        <table>
            <tr>
                <td>
                    <label class="control-label" for="couponType">Coupon Type</label>
                </td>
                <td>
                    <select id="couponType" name="couponType"  style="width: 164px;height: 33px;">
                        <option value="0" selected="0">Select</option>
                    </select>
                <td>
                    <label class="control-label" for="couponFrom">From</label>
                </td>
                <td>
                    <input type="text" class="input-medium" id="couponFrom" name="couponFrom" style="margin-left: 50px;" title="Please Enter CouponForm">
                </td>

                <td>
                    <label class="control-label" for="couponTo">To</label>
                </td>
                <td>
                    <input type="text" class="input-medium" id="couponTo" name="couponTo" style="margin-left: 50px;" title="Please Enter CouponTo">
                    <input type="button" id="couponSalesEnter" name="couponSalesEnter" class="btn btn-success"  value="Enter" onclick="listCoupons();" style="margin-left: 10px;margin-bottom: 5px;"/>
                </td>
            </tr>
        </table>

        <div id="couponDetails_table" style="width: 70%; float: left; height: 300px; overflow: scroll; vertical-align: top;">
            <table id="cashbill_PruchasedProductList" class="table" name="cashbill_PruchasedProductList" border="2">
                <thead>
                    <tr>
                        <th style=""></th>
                        <th style="text-align: center;width: 90px;">Coupon Type</th>
                        <th style="text-align: center;width: 20px;">Coupon</th>
                        <th style="text-align: center">Description</th>
                        <th style="text-align: center;width: 50px;">Rate</th>
                        <th style="width: 50px;"></th>
                    </tr>
                </thead>
                <tbody id="coupon-sales-table-body"></tbody>

            </table>
        </div>

        <div style="float: right;margin-right: 50px;" id="div-customerInfo">
            <table>
                <tr>
                    <td>
                        First Name
                    </td>
                    <td>
                        <input  type="text" id="fname" name="fname" class="input-medium" style="width: 150px;margin-left: 10px;"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Last Name
                    </td>
                    <td>
                        <input  type="text" id="lname" name="lname" class="input-medium" style="width: 150px;margin-left: 10px;"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Address
                    </td>
                    <td>
                        <textarea id="addrss" name="addrss"></textarea>
                    </td>
                </tr>

                <%--
                <tr>
                    <td>
                        Contact Number
                    </td>
                    <td>
                        <input  type="text" id="" name="" class="input-medium" style="width: 150px;margin-left: 10px;"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Street 1
                    </td>
                    <td>
                        <input  type="text" id="" name="" class="input-medium" style="width: 150px;margin-left: 10px;"/>
                    </td>
                </tr>
                <tr>
                    <td >
                        Street 2
                    </td>
                    <td>
                        <input  type="text" id="" name="" class="input-medium"style="width: 150px;margin-left: 10px;"/>
                    </td>
                </tr>
                --%>
            </table>
        </div>
        <div style="float: left;margin-top: 20px;margin-right: 175px;margin-bottom: 15px;">
            <input type="button" id="couponSalesSave" name="couponSalesSave" onclick="sellCoupons();" class="btn btn-success"  value="Save / Print"  />
            <input type="button" id="couponSalesReprint" name="couponSalesReprint" class="btn btn-primary" value="Re-Print" /> 
            <input type="reset" id="couponSalesCancel" name="couponSalesCancel" class="btn btn-danger"  value="Cancel" />

        </div>
        <div style="float: right; margin-top: 20px;margin-right: 175px;margin-bottom: 15px;">
            <label >Total Amount</label>
            <input type="text" id="couponTotalAmt" name="couponTotalAmt" readonly="true" style="width: 100px;"/>

            <label style="float: right;margin-left: 250px;">Total No.Of Coupons </label>
            <input type="text" id="couponTotalNos" name="couponTotalNos" readonly="true" style="width: 100px;"/>

        </div>
    </div>
</form>