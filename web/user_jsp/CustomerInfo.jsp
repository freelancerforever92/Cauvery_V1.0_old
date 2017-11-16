<%-- 
    Document   : CustomerInfo
    Created on : Mar 2, 2014, 1:36:59 PM
    Author     : pranesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/menu.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/key.js"></script>
        <title>Customer Info</title>
        <script type="text/javascript">

            $(document).ready(function()
            {
                Fill_State();
                Fill_City();
            });

            function sendToParent()
            {
                var fname = "", lname = "", con = "", str1 = "", str2 = "", cuntry = "", stat = "", city = "", zip = "";
                if (window.opener) {
                    fname = document.getElementById('txtFname');
                    lname = document.getElementById('txtLname');
                    con = document.getElementById('txtConNo');
                    str1 = document.getElementById('txtStr1');
                    str2 = document.getElementById('txtStr2');
                    /*cuntry= $("#OptCuntry").find("option:selected").text();
                     alert("cuntry : "+cuntry);
                     stat= $("#OptState").find("option:selected").text();
                     alert("stat : "+stat);
                     city= $("#OptCity").find("option:selected").text();
                     alert("city : "+city);*/
                    zip = document.getElementById('txtZipcode');
                    var pta = window.opener.document.getElementById('addrs');
                    pta.value = fname.value + ('\n') + lname.value + ('\n') + lname.value + ('\n') + con.value + ('\n') + str1.value + ('\n') + str1.value + ('\n') + str2.value + ('\n') + cuntry.value + ('\n') + stat.value + ('\n') + city.value + ('\n') + zip.value + ('\n');
                    window.opener.focus();
                    window.close();
                }
            }

            function Fill_State()
            {
                $.getJSON('FillState.action', function(data)
                {
                    $("#OptState").empty();
                    $.each(data.stateList, function(i, item)
                    {
                        $("#OptState").append($("<option></option>").attr("value", i).text(item));
                    });
                });
            }

            function Fill_City()
            {
                $.getJSON('FillCity.action', function(data)
                {
                    $("#OptCity").empty();
                    $.each(data.cityList, function(i, item)
                    {
                        $("#OptCity").append($("<option></option>").attr("value", i).text(item));
                    });
                });
            }

            function loadStateCity()
            {
                var selectedState = "";
                selectedState = $("#OptState").find("option:selected").text();
                if (selectedState !== "Select")
                {
                    $.getJSON('StateCity?StateVal=' + selectedState, function(data)
                    {
                        $("#OptCity").empty();
                        $.each(data.cityList, function(i, item)
                        {
                            $("#OptCity").append($("<option></option>").attr("value", i).text(item));
                        });
                    });
                }
            }

            function insCustomerInfo()
            {
                var selState = "", selCity = "";
                selState = $("#OptState").find("option:selected").text();
                selCity = $("#OptCity").find("option:selected").text();
                if (selCity !== "Select" && selState !== "Select")
                {
                    $.getJSON('InsCustomerInfo?custState=' + selState + '&custCity=' + selCity, $("#customerInfo").serialize(), function(data)
                    {
                        alert(data.instCustInfo_Status);
                        if (data.instCustInfo_Status === 1)
                        {
                            alert("Information Save Successfully");
                            //this.window.close();
                        }
                        else if (data.instCustInfo_Status === -1)
                        {
                            alert("Process Failed,Try again..");
                        }
                    });
                }
            }

        </script>
    </head>
    <body>
        <form id="customerInfo" name="customerInfo" method="post">
            <table cellpadding="2" width="120%" cellspacing="5" border="0" style="margin-top: 15px; margin-left: 13px">
                <tr>
                    <td style="font-size: 16px;margin-left: 5px;font-family: Times New Roman">
                        First Name
                    </td>
                    <td>
                        <input  type="text" id="txtFname" name="txtFname" class="input-text"/>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman">
                        Last Name
                    </td>
                    <td>
                        <input  type="text" id="txtLname" name="txtLname" class="input-text"/>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman">
                        Contact Number
                    </td>
                    <td>
                        <input  type="text" id="txtConNo" name="txtConNo" class="input-text"/>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman">
                        Street 1
                    </td>
                    <td>
                        <input  type="text" id="txtStr1" name="txtStr1" class="input-text"/>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman">
                        Street 2
                    </td>
                    <td>
                        <input  type="text" id="txtStr2" name="txtStr2" class="input-text"/>
                    </td>
                </tr>
                <%--
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman">
                        Country
                    </td>
                    <td>
                        <select id="OptCuntry" name="OptCuntry" style="width: 164px;height: 33px;">
                            <option>Select</option>
                            <option>India</option>
                            <option>U.S.A</option>
                            <option>ABC</option>
                        </select>
                    </td>
                </tr>
                --%>
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman;">
                        State
                    </td>
                    <td>
                        <select id="OptState" name="OptState" style="width: 164px;height: 33px;" onchange="loadStateCity()">
                            <option value="0" selected="0">Select</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman">
                        City
                    </td>
                    <td>
                        <select id="OptCity" name="OptCity" style="width: 164px;height: 33px;">
                            <option value="0" selected="0">Select</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 16px;font-family: Times New Roman">
                        Zip code
                    </td>
                    <td>
                        <input type="text" id="txtZipcode" name="txtZipcode" class="input-text"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" name="" id="" class="button" value="Submit" onclick="insCustomerInfo();" style="float: right;margin-top: 13px;"/>
                    </td>
                    <td>
                        <input type="submit" name="" id="" class="button" value="Cancel" style="margin-top: 13px; margin-left: 20px;"/>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
