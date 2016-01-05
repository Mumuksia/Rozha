var StatBox = React.createClass({
  loadUsersFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
      success: function(data) {
        this.setState({data: data.Users});
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
    this.loadUsersFromServer();
    setInterval(this.loadUsersFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="notesBox">   
        <UserList data={this.state.data} />
      </div>
    );
  }
});

var UserList = React.createClass({
  render: function() {
    var userNodes = this.props.data.map(function (user) {
      return (
        <User key={user.id} name={user.Name} status={user.Status} clanId={user.ClanId} number={user.Number}>          
        </User>    
      );
    });

    return (
      <div className="container">
        {userNodes}
            </div>
    );
  }
});


var User = React.createClass({
  render: function() {
    return (
            <div className="row">
                <div className="col-md-2"><b>{this.props.name}</b></div>     
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