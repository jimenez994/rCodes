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
                       // alert('Object with ID ' + id + ' was deleted successfully!');
                        // Remove the deleted object from the UI
                        $('[data-id="' + id + '"]').closest('div').remove();
                    },
                    error: function(xhr, status, error) {
                        alert('An error occurred: ' + error);
                    }
                });
            });
        });
   