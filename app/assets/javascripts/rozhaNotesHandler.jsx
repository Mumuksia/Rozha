var NotesBox = React.createClass({
  loadNotesFromServer: function() {
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
    this.loadNotesFromServer();
    setInterval(this.loadNotesFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="notesBox">
        <h1>Reservations</h1>        
        <NoteList data={this.state.data} />
        <NoteForm />
      </div>
    );
  }
});

var NoteList = React.createClass({
  render: function() {
    var noteNodes = this.props.data.map(function (note) {
      return (
              <div>
        <Note key={note.id} noteBy={note.NoteBy} note={note.Note} name={note.Name}>          
        </Note>    
        </div>
      );
    });

    return (
      <div className="container">
        {noteNodes}
      </div>
    );
  }
});

var NoteForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var noteBy = React.findDOMNode(this.refs.noteBy).value.trim();
    var name = React.findDOMNode(this.refs.name).value.trim();
    var note = React.findDOMNode(this.refs.note).value.trim();
    if (!name || !noteBy) {
      return;
    }

    var noteUrl = "http://localhost:9000/addNote?noteBy=" + noteBy + "&name=" + name + "&note=" + note;
    $.ajax({
          url: noteUrl,
          method: 'POST',
          dataType: 'json',
          cache: false,
          success: function(data) {
            console.log(data)
          }.bind(this),
          error: function(xhr, status, err) {
            console.error(noteUrl, status, err);
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
      <form className="noteForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Your name" ref="noteBy" />
        <input type="text" placeholder="Clan Name" ref="name" />
        <input type="text" placeholder="Note description" ref="note" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});

var Note = React.createClass({
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