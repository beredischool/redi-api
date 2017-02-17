$("#register").submit(function(event){
    event.preventDefault(); //prevent default action
    var post_url = $(this).attr("action"); //get form action url
    var request_method = $(this).attr("method"); //get form GET/POST method
    var form_data = $(this).serialize(); //Encode form elements for submission

    $.ajax({
        url : post_url,
        type: request_method,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        data : form_data
    }).done(function(data) {
        listUsers();
        //$("#server-results").html(response);
    }).fail(function(error) {
        alert("Password confirmation don't match!");
    });
});

function listUsers() {
    $.ajax({
        url : "api/user/AllUsers?page=0&size=20",
        type: "get"
    }).done(function(data) {
        var tmpl = $.templates("#userListTemplate");
        $("#main").html(tmpl.render(data));
        //$("#server-results").html(response);
    })
}