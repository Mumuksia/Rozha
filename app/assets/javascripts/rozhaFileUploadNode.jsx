var FormUpload = React.createClass({
uploadFile: function (e) {

    var fd = new FormData();    
    fd.append( 'file', this.refs.file.getDOMNode().files[0] );

    $.ajax({
        url: '/uploadFileSync',
        data: fd,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function(data){
            alert(data);
        }
    });
    e.preventDefault()
},
render: function() {
    return (
      <div>                
         <form ref="uploadForm" className="uploader" encType="multipart/form-data" >
            <input ref="file" type="file" name="file" className="upload-file"/>
            <input type="button" ref="button" value="Upload" onClick={this.uploadFile} />
        </form>                
      </div>
  );
}
});



React.render(
  <FormUpload />,
  document.getElementById('fileUploadNode'));