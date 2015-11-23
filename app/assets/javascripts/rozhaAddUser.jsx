var StatBox = React.createClass({
  loadUsersFromServer: function() {
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
        <UserForm onUserSubmit={this.onUserSubmit} />
      </div>
    );
  }
});

var UserList = React.createClass({
  render: function() {
    var userNodes = this.props.data.map(function (user) {
      return (
        <User key={user.id} name={user.Name} status={user.Status} clanId={user.ClanId} remoteAddress={user.RemoteAddress}>          
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
                <div className="col-md-2">{this.props.remoteAddress}</div>
                <div className="col-md-4"></div>
            </div>
            
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
  document.getElementById('statisticsNode'));