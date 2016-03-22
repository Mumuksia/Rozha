var StatBox = React.createClass({
  loadUsersFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data.Users, penalties: data.Penalties});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err);
      }.bind(this)
    });
  },
  expandUser: function(userName) {
    var loadUsersWithUser = "/loadUsersWithUser?userName=" + userName;
    $.ajax({
      url: loadUsersWithUser,
      dataType: 'json',
      type: 'POST',
      data: userName,
      success: function(data) {
        this.setState({data: data.Users, penalties: data.Penalties});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
    handleNoteSubmit: function(note) {
    var noteUrl = "/addNote?noteBy=" + note.noteBy + "&name=" + note.name + "&description=" + note.description;

    $.ajax({
      url: noteUrl,
      dataType: 'json',
      type: 'POST',
      data: note,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  onUserSubmit: function(user) {
    var userUrl = "/addUser?clanId=" + user.clanId + "&name=" + user.name + "&status=" + user.status;

    $.ajax({
      url: userUrl,
      dataType: 'json',
      type: 'POST',
      data: user,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  getInitialState: function() {
    return {data: [], penalties: []};
  },
  componentDidMount: function() {
    this.loadUsersFromServer();
    setInterval(this.loadUsersFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="notesBox">   
        <h4>Додати гравця</h4>
        <UserForm onUserSubmit={this.onUserSubmit} />
        <br/>
        <UserList data={this.state.data} expandUser={this.expandUser}/>
        <br/>
        <PenaltyList penalties={this.state.penalties} />
        <br/>
        <h4>Додати порушення</h4>
        <NoteForm noNoteSubmit={this.handleNoteSubmit}/>
      </div>
    );
  }
});

var UserList = React.createClass({
  onExpandClick: function(userName){
    return this.props.expandUser(userName);
  },
  render: function() {
    var userNodes = this.props.data.map(function (user) {
      return (
        <User key={user.id} name={user.Name} status={user.Status} clanId={user.ClanId} number={user.Number} onExpandUser={this.onExpandClick}>          
        </User>    
      );
    }.bind(this));

    return (
      <div className="container">
        {userNodes}
            </div>
    );
  }
});

var PenaltyList = React.createClass({
  render: function() {
    var noteNodes = this.props.penalties.map(function (note) {
      return (
              <div>
        <Note key={note.id} noteBy={note.NoteBy} description={note.Description} name={note.Name}>          
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
                <div className="col-md-1"><b>{this.props.name}</b></div>     
                <div className="col-md-5">{this.props.description}</div> 
                <div className="col-md-2">{this.props.noteBy}</div>
                <div className="col-md-4"></div>
            </div>
            
    );
  }
});


var User = React.createClass({
  handleClick: function(e, name){        
    var userName = this.props.name;
    return this.props.onExpandUser(userName);
  },
  render: function() {
    return (
            <div className="row">
                <div className="col-md-2"><a onClick={this.handleClick.bind(null, this.props.name)}><b>{this.props.name}</b></a></div>     
                <div className="col-md-2">{this.props.status}</div> 
                <div className="col-md-2">{this.props.clanId}</div>
                <div className="col-md-2">{this.props.number}</div>
                <div className="col-md-4"></div>
            </div>
            
    );
  }
});

var NoteForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var noteBy = React.findDOMNode(this.refs.noteBy).value.trim();
    var name = React.findDOMNode(this.refs.name).value.trim();
    var description = React.findDOMNode(this.refs.description).value.trim();
    if (!name || !noteBy) {
      return;
    }

    this.props.noNoteSubmit({noteBy: noteBy, name: name, description: description});

    // clears the form fields
    React.findDOMNode(this.refs.noteBy).value = '';
    React.findDOMNode(this.refs.name).value = '';
    React.findDOMNode(this.refs.description).value = '';
    return;
  },
  render: function() {
    return (
      <form className="form-inline" onSubmit={this.handleSubmit}>
        <input type="text" className="form-control" placeholder="Your name" ref="noteBy" />
        <input type="text" className="form-control" placeholder="Clan Name" ref="name" />
        <input type="text" className="form-control" placeholder="Penalty description" ref="description" />
        <input type="submit" className="btn btn-primary" value="Post" />
      </form>
    );
  }
});

var UserForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var status = React.findDOMNode(this.refs.status).value.trim();
    var name = React.findDOMNode(this.refs.name).value.trim();
    var clanId = React.findDOMNode(this.refs.clanId).value.trim();
    if (!name || !clanId || !status) {
      return;
    }

    this.props.onUserSubmit({status: status, name: name, clanId: clanId});

    // clears the form fields
    React.findDOMNode(this.refs.status).value = '';
    React.findDOMNode(this.refs.name).value = '';
    React.findDOMNode(this.refs.clanId).value = '';
    return;
  },
  render: function() {
    return (
      <form className="form-inline" onSubmit={this.handleSubmit}>
        <input type="text" className="form-control" placeholder="Статус гравця" ref="status" />
        <input type="text" className="form-control" placeholder="Кланове ім'я" ref="name" />
        <input type="text" className="form-control" placeholder="Кланове ід" ref="clanId" />
        <input type="submit" className="btn btn-primary" value="Додати" />
      </form>
    );
  }
});

React.render(
  <StatBox url="/loadUsers" pollInterval={20000} />,
  document.getElementById('statNode'));