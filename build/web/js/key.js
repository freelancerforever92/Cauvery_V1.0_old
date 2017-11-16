/*
 del 46
 hom 36
 end 35
 spac 32
 - 173
 */
function text(event)
{
    var keyCode = event.which || event.keyCode;
    var englishAlphabetAndWhiteSpace = /[A-Za-z]/g;
    var key = String.fromCharCode(event.which);
    if (event.keyCode == 190 || event.keyCode == 173 || event.keyCode == 46 || event.keyCode == 36 || event.keyCode == 35 || event.keyCode == 8 || event.keyCode == 37 || event.keyCode == 103 || event.keyCode == 9 || event.keyCode == 32 || event.keyCode == 110 || event.keyCode == 39 || englishAlphabetAndWhiteSpace.test(key))
    {
        if (event.keyCode == 111 || event.keyCode == 109 || event.keyCode == 48 || event.keyCode == 55 || event.keyCode == 57 || event.keyCode == 189 || event.keyCode >= 96 && event.keyCode <= 106 || event.keyCode == 107 || event.keyCode == 61 || event.keyCode == 187)
        {
            event.preventDefault();
        }
        else
        {
            return true;
        }
    }
    else
    {
        event.preventDefault();
    }
}

function CmpnyNam(event)
{
    var englishAlphabetAndWhiteSpace = /[A-Za-z]/g;
    var key = String.fromCharCode(event.which);

    if (event.keyCode == 190 || event.keyCode == 61 || event.keyCode == 43 || event.keyCode == 173 || event.keyCode == 55 || event.keyCode == 57 || event.keyCode == 48 || event.keyCode == 46 || event.keyCode == 36 || event.keyCode == 35 || event.keyCode == 8 || event.keyCode == 37 || event.keyCode == 103 || event.keyCode == 9 || event.keyCode == 32 || event.keyCode == 110 || event.keyCode == 39 || englishAlphabetAndWhiteSpace.test(key) || (event.keyCode >= 49 && event.keyCode <= 57))
    {
        if (event.keyCode == 111 || event.keyCode == 109 || event.keyCode == 189 || event.keyCode >= 96 && event.keyCode <= 106 || event.keyCode == 187)
        {
            event.preventDefault();
        }
        else
        {
            return true;
        }
    }
    else
    {
        event.preventDefault();
    }
}



function dec_number(event)
{
    if (event.shiftKey == 1)
    {
        event.preventDefault();
    }
    else
    {
        var j = 0;
        if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 35 || event.keyCode == 32 || event.keyCode == 36 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 42 || event.keyCode == 43 || event.keyCode == 46 || event.keyCode == 107 || event.keyCode == 190 || (event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57))
        {
            if (j == 1 && (event.keyCode == 109) || (event.keyCode >= 124 && event.keyCode <= 126))
            {
                event.preventDefault();
            }
        }
        else
        {
            if (event.keyCode < 48 || event.keyCode > 57)
            {
                event.preventDefault();
            }
        }
    }
}

//function checkNumber(obj)
//{
//
//    var chkNumber = /([0-9])$/;
//    if (!chkNumber.test(obj.value))
//    {
//        obj.value = "";
//    }
//
//    return true;
//}

function number(event)
{
    //alert(event.keyCode);
    if (event.shiftKey == 1)
    {
        event.preventDefault();
    }
    else
    {
        var j = 0;
        if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 35 || event.keyCode == 32 || event.keyCode == 36 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 42 || event.keyCode == 43 || event.keyCode == 46 || event.keyCode == 107/*||event.keyCode==173*/ || (event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57))
        {
            if (j == 1 && (event.keyCode == 110) || (event.keyCode >= 124 && event.keyCode <= 126))
            {
                event.preventDefault();
            }
        }
        else
        {
            if (event.keyCode < 48 || event.keyCode > 57)
            {
                event.preventDefault();
            }
        }
    }
}
function number_sec(event)
{
    //alert(event.keyCode);
    if (event.shiftKey == 1)
    {
        event.preventDefault();
    }
    else
    {
        var j = 0;
        if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 35 || event.keyCode == 36 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 42 || event.keyCode == 46 || event.keyCode == 107/*||event.keyCode==173*/ || (event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57))
        {
            if (j == 1 && (event.keyCode == 110 && event.keyCode == 173) || (event.keyCode >= 124 && event.keyCode <= 126))
            {
                event.preventDefault();
            }
        }
        else
        {
            if (event.keyCode < 48 || event.keyCode > 57)
            {
                event.preventDefault();
            }
        }
    }
}
function count(event)
{
    if (event.shiftKey == 1)
    {
        event.preventDefault();
    }
    else
    {
        var j = 0;
        if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 35 || event.keyCode == 32 || event.keyCode == 36 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 42 || event.keyCode == 43 || event.keyCode == 46 || (event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57))
        {
            if (j == 1 && (event.keyCode == 110 || event.keyCode == 190) || (event.keyCode >= 124 && event.keyCode <= 126))
            {
                event.preventDefault();
            }
        }
        else
        {
            if (event.keyCode < 48 || event.keyCode > 57 || event.keyCode == 107)
            {
                event.preventDefault();
            }
        }
    }
}
function zipco(event)
{
    if (event.shiftKey == 1)
    {
        event.preventDefault();
    }
    else
    {
        var j = 0;
        if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 35 || event.keyCode == 32 || event.keyCode == 36 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 42 || event.keyCode == 43 || event.keyCode == 46 || (event.keyCode >= 96 && event.keyCode <= 105) || (event.keyCode >= 48 && event.keyCode <= 57))
        {
            if (j == 1 && (event.keyCode == 110 || event.keyCode == 107 || event.keyCode == 190) || (event.keyCode >= 124 && event.keyCode <= 126))
                event.preventDefault();
        }
        else
        {
            if (event.keyCode < 48 || event.keyCode > 57)
            {
                event.preventDefault();
            }
        }
    }
}

function text_mob(txtid)
{
    var a = $('#' + txtid);

    $(a).keypress(function(e)
    {
        // alert("AAAAA :   "+e.keycode);
        var temp = $(a).val().length;
        if ((temp >= 0 && temp <= 16) || (e.keyCode == 8 || e.keyCode == 32 || e.keyCode == 36 || e.keyCode == 37 || e.keyCode == 39 || e.keyCode == 35 || e.keyCode == 46 || e.keyCode == 173 || e.keyCode == 107) || (e.keyCode >= 33 && e.keyCode <= 38))
        {
            return true;
        }
        else
        {
            e.preventDefault();
            return false;
        }
    });
}

function phn_lgh(txtid)
{
    var a = $('#' + txtid);
    var temp;
    $(a).keypress(function(e)
    {
        temp = $(a).val().length;
        if ((temp >= 0 && temp <= 16) || (e.keyCode == 8 || e.keyCode == 32 || e.keyCode == 36 || e.keyCode == 37 || e.keyCode == 39 || e.keyCode == 35 || e.keyCode == 46) || (e.keyCode >= 33 && e.keyCode <= 38))
        {
            return true;
        }
        else
        {
            e.preventDefault();
            return false;
        }
    });
}

function text_phn(event, othrid)
{
    var othr = $('#' + othrid);
    /*if (event.shiftKey==1)
     {
     event.preventDefault(); 
     }
     else
     {*/
    var j = 0;
    if (event.keyCode == 173 || event.keyCode == 61 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 35 || event.keyCode == 32 || event.keyCode == 36 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 109 || event.keyCode == 42 || event.keyCode == 43 || event.keyCode == 46 || event.keyCode == 105 || event.keyCode == 107 || (event.keyCode >= 96 && event.keyCode <= 109) || (event.keyCode >= 48 && event.keyCode <= 57))
    {
        if (j == 1 && (event.keyCode >= 124 && event.keyCode <= 126) || event.keyCode == 110 || event.keyCode == 190)
        {
            $(othr).text("Invalid Phone Number (eg : 0422-0000000)");
            event.preventDefault();
        }
    }
    else
    {
        if (event.keyCode < 48 || event.keyCode > 57)
        {
            event.preventDefault();
        }
    }
//}
}


function text_email(txtid, othrid)
{
    var othr = $('#' + othrid);
    var rcv_othr = $(othr).val();

    var emailAddress = $('#' + txtid);
    var Rcv_Email = $(emailAddress).val();
    if (Rcv_Email != "")
    {
        //var pattern =  new RegExp(/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);
        var pattern = new RegExp(/^((([a-z]|\d|[!#\$%&\*\+\-\/=\?\^_{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i);
        if (pattern.test(Rcv_Email))
        {
            $(othr).text("");
            return true;
        }
        else
        {
            $(othr).text("Invalid Email-Address (eg : abc@gmail.com)");
            $(emailAddress).val("");
            return false;
        }
    }
    else
    {
        //$(othr).text("Enter Email-Address");
    }
}

function text_nam(txtid)
{
    var a = $('#' + txtid);
    $(a).keypress(function(e)
    {
        var temp = $(a).val().length;
        if ((temp >= 0 && temp < 64) || (e.keyCode == 46 || e.keyCode == 8))
        {
            return true;
        }
        else
        {
            e.preventDefault();
            return false;
        }
    });
}

function text_strt(txtid)
{
    var a = $('#' + txtid);
    $(a).keypress(function(e)
    {
        var temp = $(a).val().length;
        if ((temp >= 0 && temp < 128) || (e.keyCode == 46 || e.keyCode == 8))
        {
            return true;
        }
        else
        {
            e.preventDefault();
            return false;
        }
    });
}

function text_othrdes(txtid)
{
    var a = $('#' + txtid);
    $(a).keypress(function(e)
    {
        var temp = $(a).val().length;
        if ((temp >= 0 && temp < 200) || (e.keyCode == 46 || e.keyCode == 8))
        {
            return true;
        }
        else
        {
            e.preventDefault();
            return false;
        }
    });
}

function text_desc(txtid)
{
    var a = $('#' + txtid);
    $(a).keypress(function(e)
    {
        var temp = $(a).val().length;
        if ((temp >= 0 && temp < 2000) || (e.keyCode == 46 || e.keyCode == 8))
        {
            return true;
        }
        else
        {
            e.preventDefault();
            return false;
        }
    });
}
function text_zip(txtid)
{
    var a = $('#' + txtid);
    $(a).keypress(function(e)
    {
        var temp = $(a).val().length;
        if ((temp >= 0 && temp < 8) || (e.keyCode == 46 || e.keyCode == 8))
        {
            return true;
        }
        else
        {
            e.preventDefault();
            return false;
        }
    });
}
function textprice(event)
{
    if (event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 36 || event.keyCode == 46 || event.keyCode == 190 || event.keyCode == 191 || event.keyCode == 53 || event.keyCode == 110 || event.keyCode == 111 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 35)
    {
        if (j == 1 && (event.keyCode == 110 || event.keyCode == 190))
        {
            event.preventDefault();
        }
        if (j < 1 && (event.keyCode == 110 || event.keyCode == 190))
        {
            j++;
            this.j = j;
        }
        /*if(j==1&&event.keyCode==8)
         {
         j=0;
         this.j=j;
         }*/
    }
    else
    {
        if (event.keyCode < 48 || event.keyCode > 57)
        {
            event.preventDefault();
        }
    }

}

function isstreet(strt, othrid) {
    var othr = $('#' + othrid);
    var pattern = new RegExp(/^[a-zA-Z0-9\s\#\-\,\.\&]*$/);
    var address = $('#' + strt);
    var rc_address = $(address).val();
    if (pattern.test(rc_address)) {
        $(othr).text("");
        return true;
    }
    else
    {
        $(othr).text("Only Limited Symbols are Allowed");
        return false;
    }
}

function isemail(emailAddress)
{
    var pattern = new RegExp(/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);
    // alert( pattern.test(emailAddress) );
    return pattern.test(emailAddress);
}
