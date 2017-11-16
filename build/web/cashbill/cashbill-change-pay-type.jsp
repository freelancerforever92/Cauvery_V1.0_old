<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<head>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#invoiceNo").focus();
        });
    </script>
</head>
<div class="clearfix changePayType">
    <legend> <h2>Update Payment Type <span class="pull-right"><input type="button" class="btn btn-warning" value="Back" onclick="load_CashBillSalesForm();"></span></h2>
    </legend><br>
    <div class="row-fluid">
        <div class="span1"><label class="control-label">Invoice No :</label></div>
        <div class="span2">
            <input type="text" id="invoiceNo" placeholder="Invoice No" class="input-block-level">
        </div>
        <div class="span2"> <input type="button" value="Search" class="btn btn-info" onclick="changeCashToCard();"/></div>

    </div>
    <br/>
    <div class="clearfix displayInfoDiv dis-n">
        <table id="displayInfotTable" class="table table-bordered hide" border="1">
            <thead>
                <tr>
                    <th>Invoice No</th>                   
                    <th>Amount</th>
                    <th>Payment Type</th>
                    <th>Cashier Name</th>
                    <th>Date </th>
                    <th>Last UpDated On </th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <tr >
                    <td id="invoice"></td>
                    <td id="amount" style="font-size: medium;"></td>
                    <td id="payType" style="font-size: large;"></td>
                    <td id="name"></td>
                    <td id="cDate"></td>
                    <td id="updatedDate"></td>
                    <td id="changeBtn">
                        <input type="button" id="chgPayTypeBtn" name="chgPayTypeBtn" onclick="changeCashToCardAction();" class="btn-danger btn-large"/>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
