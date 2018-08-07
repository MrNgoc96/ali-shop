$(document).ready(function () {
    $('#selectPage').change(function () {
        let page = $(this).val();
        window.location.href = "?page="+page;
    })
})