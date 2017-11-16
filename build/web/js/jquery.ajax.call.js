
function forwardAction(url) {
    var divObj = $('#bodyTile');
    alert(divObj);
    if (divObj == null)
    {

        alert("div is not there");
    }
    else
    {
        alert("div is there");
    }

    $('#bodyTile').children().remove();
    $('#bodyTile').load(url);
}