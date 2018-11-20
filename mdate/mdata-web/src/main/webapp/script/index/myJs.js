// JavaScript Document

$(document).ready(function(){
    $(".ce > li.nav_li > span").click(function () {
        if ($(this).hasClass("bg1")) {
            $(this).removeClass("bg1").addClass("bg2");
        }
        else {
            $(this).removeClass("bg2").addClass("bg1");
        }
        if ($(this).parent().find(".er").hasClass("er1")) {
            $(this).parent().find(".er").removeClass("er1").addClass("er2");
        }
        else {
            $(this).parent().find(".er").removeClass("er2").addClass("er1");
        }
    })
});