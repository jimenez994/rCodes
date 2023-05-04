window.onload = function(){
    var files = document.querySelectorAll("input[type=file]");
    files[0].addEventListener("change",function(e){
        if(this.files && this.files[0]){
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById("preview").setAttribute("src", e.target.result);
            }
            reader.readAsDataURL(this.files[0]);
        }
    });
}
  $(document).ready(function() {
            $('.deleteButtonPost').click(function() {
                var id = $(this).data('id');
                $.ajax({
                    type: 'POST',
                    url: '/home/delete/' + id,
                    success: function(response) {
                        $('[data-id="' + id + '"]').closest('div').remove();
                        console.log(response)
                    },
                    error: function(xhr, status, error) {
                        console.log('An error occurred: ' + error);
                    }
                });
            });
        });
        
document.getElementById("postForm").addEventListener("submit", function(event) {
  event.preventDefault(); // prevent default form submission behavior
  submitForm();
});


  function submitForm() {
    var form = document.getElementById("postForm");
    var formData = new FormData(form);
    var xhttp = new XMLHttpRequest();
    var submitButton = document.getElementById("submitPostButton");
    var loadingIcon = document.getElementById("loadingIcon");
    var imagebButton = document.getElementById("imageButton");

    // Hide the submit button and show the loading icon
    submitButton.style.visibility = "hidden";
    loadingIcon.style.display = "inline-block";
    imagebButton.style.visibility = "hidden";

    xhttp.onreadystatechange = function() {
      if (this.readyState == 4) {
        // Show the submit button and hide the loading icon
        submitButton.style.visibility = "visible";
        loadingIcon.style.display = "none";
            imagebButton.style.visibility = "visible";

        if (this.status == 200) {
          form.reset();
          document.getElementById("files").value = "";
          document.getElementById("preview").src = ""; // Clear image preview
          console.log(this.responseText);
          // handle success response
        } else {
          console.log(this.status + " " + this.statusText);
          // handle error response
        }
      }
    };
    xhttp.open("POST", "/home/post", true);
    xhttp.send(formData);
  }







   