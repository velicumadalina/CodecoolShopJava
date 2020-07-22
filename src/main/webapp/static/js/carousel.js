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


// When the user scrolls the page, execute myFunction
window.onscroll = function() {myFunction()};

// Get the header
const header = document.getElementById("myHeader");

// Get the offset position of the navbar
const sticky = header.offsetTop;

// Add the sticky class to the header when you reach its scroll position. Remove "sticky" when you leave the scroll position
function myFunction() {
    if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
    }
}

