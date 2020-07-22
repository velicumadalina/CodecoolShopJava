$(document).ready(function()
{
    $('.hero').css('height', ($(window).height() - $('header').outerHeight()) + 'px'); // Set hero to fill page height

    $('#scroll-hero').click(function()
    {
        $('html,body').animate({scrollTop: $("#hero-bloc").height()}, 'slow');
    });
});


// Window resize
$(window).resize(function()
{
    $('.hero').css('height', ($(window).height() - $('header').outerHeight()) + 'px'); // Refresh hero height
});

function submitForm() {
    document.getElementById('form')[0].submit()
    console.log("AAAAAAAAAAAAAAAAA")
}

