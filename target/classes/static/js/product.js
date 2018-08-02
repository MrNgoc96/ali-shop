$(document).ready(function () {
    $('#scope-price').change(function () {
        let scopePrice = $(this).val();
        window.location.href = "./products?scopePrice=" + scopePrice;
    })
})

