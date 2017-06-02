var triFactor = 0.1;

function main() {
    $('.card').on("mouseenter", function() {
        $(this).children('.tri-cover').animate({ bottom: "-=20" }, 100);
    }).on("mouseleave", function() {
        $(this).children('.tri-cover').animate({ bottom: "0" }, 100);
        $(this).stop(true);
    });
}

$(document).ready(main());