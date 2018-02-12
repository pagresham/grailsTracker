$(function() {
    if($(".welcome-success").length) {
        $(".welcome-success").show('slide');
        console.log($(".welcome-success"))
    }
    if($(".welcome-error").length) {
        $(".welcome-error").show('slide');
        console.log($(".welcome-error"))
    }
    if($(".add-recip-form").length) {
        $('.add-recip-btn').on('click', function(e) {
            e.preventDefault()
            $(".add-recip-form").toggle('slow')
        })
    }
    if($('#addRecipientForm')) {

        console.log("Form is true blah")

        var form = $('#addRecipientForm')

        console.log(form)

        form.submit(function( event ) {
            console.log("Inside submit handler")
            if(confirm("Are you sure you want to save this Recipient?")) {
                console.log("Submitting Form")
                return
            } else {
                console.log("Not Submitting Form")
                event.preventDefault()
            }
        })

    }

    // check if add gift to list form is available

    if($('#addGift')) {

        // on change, reformat the price
        console.log('found add gift form')
        $('#gift-price').on('change', function() {
            console.log("price changed")
            var price = $(this).val()
            console.log(parseFloat($(this).val()).toFixed(2))
            $(this).val(parseFloat(price).toFixed(2))
        })
    }

})