var NotesBox = React.createClass({
  loadCommentsFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err);
      }.bind(this)
    });
  },
  getInitialState: function() {
    return {data: []};
  },
  componentDidMount: function() {
    this.loadCommentsFromServer();
    setInterval(this.loadCommentsFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="commentBox">
        <h1>Reservations</h1>        
        <CommentList data={this.state.data} />
        <CommentForm />
      </div>
    );
  }
});

var CommentList = React.createClass({
  render: function() {
    var commentNodes = this.props.data.map(function (comment) {
      return (
        <Comment key={comment.id} noteBy={comment.NoteBy} note={comment.Note} name={comment.Name}>          
        </Comment>        
      );
    });

    return (
      <div className="container">
        {commentNodes}
      </div>
    );
  }
});

var CommentForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var approvedBy = React.findDOMNode(this.refs.noteBy).value.trim();
    var name = React.findDOMNode(this.refs.name).value.trim();
    var number = React.findDOMNode(this.refs.note).value.trim();
    if (!name || !noteBy) {
      return;
    }

    var commentUrl = "http://localhost:9000/addNote?noteBy=" + noteBy + "&name=" + name + "&note=" + note;
    $.ajax({
          url: commentUrl,
          method: 'POST',
          dataType: 'json',
          cache: false,
          success: function(data) {
            console.log(data)
          }.bind(this),
          error: function(xhr, status, err) {
            console.error(commentUrl, status, err);
          }.bind(this)
        });

    // clears the form fields
    React.findDOMNode(this.refs.noteBy).value = '';
    React.findDOMNode(this.refs.name).value = '';
    React.findDOMNode(this.refs.note).value = '';
    return;
  },
  render: function() {
    return (
      <form className="commentForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Your name" ref="noteBy" />
        <input type="text" placeholder="Clan Name" ref="name" />
        <input type="text" placeholder="Note description" ref="note" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});

var Comment = React.createClass({
  render: function() {
    var rawMarkup = this.props.name;

    return (
            <div className="row">
                <div className="col-md-3">{this.props.name}</div>     
                <div className="col-md-2">{this.props.note}</div> 
                <div className="col-md-3">{this.props.noteBy}</div>
                <div className="col-md-4"></div>
            </div>
    );
  }
});

React.render(
  <NotesBox url="http://localhost:9000/loadNotes" pollInterval={20000} />,
  document.getElementById('notesNode'));