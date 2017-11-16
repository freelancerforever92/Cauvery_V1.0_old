$.validator.setDefaults(
        {
        });
$(document).ready(function()
{
    load_BranchCodes();
    $("#userLogin").validate(
            {
                rules:
                        {
                            txtUname:
                                    {
                                        required: true
                                    },
                            txtPassword:
                                    {
                                        required: true
                                    }
                        },
                messages:
                        {
                            txtUname:
                                    {
                                        required: "Please Enter Username"
                                    },
                            txtPassword:
                                    {
                                        required: "Please Enter Password"
                                    }
                        }
            });

});

function load_BranchCodes()
{
    $.getJSON('BranchCode', function(data)
    {
        $("#counterNos").empty();
        $.each(data.counterCodes, function(i, item)
        {
            i = i + 1;
            $("#counterNos").append($("<option></option>").attr("value", i).text(item));
        });
    });
}