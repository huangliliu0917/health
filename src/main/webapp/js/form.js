$(document).ready(function() {
    $("#wizard").steps({
        onStepChanging: function(event, currentIndex, newIndex) {
            return true;

        },
        onStepChanged: function(event, currentIndex, priorIndex) {
        },
        onFinishing: function(event, currentIndex) {
            return true;
        },
        onFinished: function(event, currentIndex) {
            var form=$(".step-content:eq("+currentIndex+")");
            var id=$(".formId",form).text();
            var content=$(form).html();


            console.log(id);
            console.log(content);

        }

    });

});

