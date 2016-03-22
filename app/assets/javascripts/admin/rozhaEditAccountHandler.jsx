var AccountBox = React.createClass({
  loadAccountsFromServer: function() {
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
  handleWishSubmit: function(wish) {
    var wishUrl = "/addWish?name=" + wish.name + "&number=" + wish.number;

    $.ajax({
      url: wishUrl,
      dataType: 'json',
      type: 'POST',
      data: wish,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  changeRoleAccount: function(email) {
    var promoteUrl = "/editAccount?email=" + email;
    $.ajax({
      url: promoteUrl,
      dataType: 'json',
      type: 'POST',
      data: email,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  deleteAccount: function(email) {
    var deleteUrl = "/deleteAccount?email=" + email;
    $.ajax({
      url: deleteUrl,
      dataType: 'json',
      type: 'DELETE',
      data: email,
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
    this.loadAccountsFromServer();
    setInterval(this.loadAccountsFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="accountBox">
        <h1>Accounts</h1>    
        <AccountList data={this.state.data} deleteAccount={this.deleteAccount} changeRoleAccount={this.changeRoleAccount}/>
      </div>
    );
  }
});

var AccountList = React.createClass({
  handleDelete: function(email){
    return this.props.deleteAccount(email);
  },
  handleChange: function(email){
    return this.props.changeRoleAccount(email);
  },
  render: function() {
    var accountNodes = this.props.data.map(function (account) {
      return (
         <div>
        <Account keyId={account.id} name={account.Name} role={account.Role} email={account.Email} onDelete={this.handleDelete} onRoleChange={this.handleChange}>          
        </Account> 
        </div>
      );
    }.bind(this));

    return (
      <div className="container">
        {accountNodes}
      </div>
    );
  }
});

var Account = React.createClass({
  handleClick: function(e, email){        
    var accountId = this.props.email;
    return this.props.onDelete(accountId);
  },
  handleRoleClick: function(e, email){        
    var accountId = this.props.email;
    return this.props.onRoleChange(accountId);
  },
  render: function() {    
    var rawMarkup = this.props.name;
    return (
            <div className="row">                
                <div className="col-md-2"><b>{this.props.name}</b></div>     
                <div className="col-md-2">{this.props.email}</div> 
                <div className="col-md-2"><a onClick={this.handleRoleClick.bind(null, this.props.email)}>{this.props.role}</a></div> 
                <div className="col-md-2"><a onClick={this.handleClick.bind(null, this.props.email)}>delete</a></div>
                <div className="col-md-4"></div>
            </div>
            
    );
  }
});

React.render(
  <AccountBox url="/loadAccounts" pollInterval={20000} />,
  document.getElementById('accountNode'));