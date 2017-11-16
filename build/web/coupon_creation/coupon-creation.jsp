<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<head>
    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#couponType").focus();
        });
    </script>
</head>
<form id="couponCreationForm" name="couponCreationForm" method="post" >
    <legend>CASH BILL-Coupon Creation</legend>

    <div class="control-group">
        <table>
            <tr>
                <td>
                    <label class="control-label" for="input01">Coupon Type</label>
                </td>
                <td>
                    <input type="text" class="input-medium" id="couponType" name="couponType" style="margin-left: 50px;" title="Please Enter Material ID">
                </td>
            </tr>
            
            <tr>
                <td>
                    <label class="control-label" for="input01">Coupon Description</label>
                </td>
                <td>
                    <textarea id="couponDescription" name="couponDescription" style="margin-left: 50px;"></textarea>
                </td>
            </tr>

            
            <tr>
                <td>
                    <label class="control-label" for="input01">Coupon Rate</label>
                </td>
                <td>
                    <input type="text" class="input-medium" id="couponRate" name="couponRate" style="margin-left: 50px;" title="Please Enter Material ID" onblur="checkSalesOrderCancelStatus();" >
                </td>
            </tr>
            
            <tr>
                <td>
                    <label class="control-label" for="input01">Coupon From</label>
                </td>
                <td>
                    <input type="text" class="input-medium" id="couponFrom" name="couponFrom" style="margin-left: 50px;" title="Please Enter Material ID"  >
                </td>
            </tr>
             <tr>
                <td>
                    <label class="control-label" for="input01">Coupon To</label>
                </td>
                <td>
                    <input type="text" class="input-medium" id="couponTo" name="couponTo" style="margin-left: 50px;" title="Please Enter Material ID"  >
                </td>
            </tr>
            
            
            
            
            <tr>
                <td style="float: right;">
                    <input type="button" id="cashbillCancel_EnterButton" name="cashbillCancel_EnterButton" class="btn btn-info" onclick="createCoupon();" value="Enter" style="margin-bottom: 10px;"/>
                </td>
            </tr>
        </table>
    </div>
</form>