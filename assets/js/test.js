function main() {
    $('.card').hide();
    $('.card').fadeIn(1000);
    $('.card').on("mouseenter", function() {
        $(this).children('.tri-cover').animate({ bottom: "-=20" }, 200);
    }).on("mouseleave", function() {
        $(this).children('.tri-cover').animate({ bottom: "0" }, 200);
    });
}

$(document).ready(main());