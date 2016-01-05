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
        <UserList data={this.state.data} expandUser={this.expandUser}/>
        <br/>
        <PenaltyList penalties={this.state.penalties} />
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

React.render(
  <StatBox url="/loadUsers" pollInterval={20000} />,
  document.getElementById('statisticsNode'));