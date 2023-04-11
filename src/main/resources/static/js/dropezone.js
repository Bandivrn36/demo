<script>
  Dropzone.autoDiscover = false;

  $(document).ready(function() {
      var myDropzone = new Dropzone(".dropzone", {
          url: "/upload",
          paramName: "file",
          maxFilesize: 2, // MB
          acceptedFiles: ".jpeg,.jpg,.png,.gif",
          addRemoveLinks: true,
          dictRemoveFile: "Remove",
          dictDefaultMessage: "Drop files here or click to upload",
          init: function() {
              this.on("success", function(file, response) {
                  console.log("File uploaded: ", response);
              });
              this.on("removedfile", function(file) {
                  console.log("File removed: ", file);
              });
          }
      });
  });
</script>