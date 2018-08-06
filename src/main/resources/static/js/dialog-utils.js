function Confirm(title, msg, $true, $false, $link) {
    let $content = "<div class='dialog-ovelay'>"
        + "<div class='dialog'><div class='.head'>" + "<h3> " + title + " </h3> " + "</div>"
        + "<div class='dialog-msg'>" + " <p> " + msg + " </p> " + "</div>"
        + "<div class='footer'>" + "<div class='controls'>"
        + " <button class='btn btn-success button-danger doAction'><i class='fa fa-check'></i> " + $true
        + "</button> "
        + " <button class='btn btn-danger button button-default cancelAction'><i class='fa fa-ban'></i> " + $false
        + "</button> " + "</div>" + "</div>" + "</div>" + "</div>";
    $('body').prepend($content);
    $('.doAction').click(function () {
        window.location.href = $link;
        $(this).parents('.dialog-ovelay').fadeOut(500, function () {
            $(this).remove();
        });
    });
    $('.cancelAction, .fa-close').click(function () {
        $(this).parents('.dialog-ovelay').fadeOut(500, function () {
            $(this).remove();
        });
    });

}

function showPopup(title, message) {
    let content = '<div id="popup">\n' +
        '\t\t\t<div id="popup-message">\n' +
        '\t\t\t\t<div id="popup-title">\n' +
        '\t\t\t\t\t<h3>' + title + '</h3>\n' +
        '\t\t\t\t</div>\n' +
        '\t\t\t\t<div id="popup-me">\n' +
        '\t\t\t\t\t<p>' + message + '</p>\n' +
        '\t\t\t\t</div>\n' +
        '\t\t\t\t<img alt="Icon Inform" src="http://www.franklinlakes.org/vertical/Sites/%7B02E9C1B5-59B4-4B82-8487-CE42C675CF8A%7D/uploads/Information.png">\n' +
        '\t\t\t\t<button class="btn btn-danger" id="close-popup">OK</button>\n' +
        '\t\t\t</div>\n' +
        '\t\t</div></div>'
    $('body').prepend(content);
    $("#popup").fadeIn(500);
    $('#close-popup').click(function () {
        $("#popup").fadeOut(500);
    })
}
