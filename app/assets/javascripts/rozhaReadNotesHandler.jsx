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
        <h1>Notes</h1>        
        <NoteList data={this.state.data} />
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
  <NotesBox url="/loadNotes" pollInterval={20000} />,
  document.getElementById('notesNode'));