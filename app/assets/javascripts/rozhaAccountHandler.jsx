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
  handleAddAcount: function(account) {
    var accountUrl = "/addAccount?email=" + account.email + "&name=" + account.name + "&password=" + account.password;

    $.ajax({
      url: accountUrl,
      dataType: 'json',
      type: 'POST',
      data: account,
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
        <AccountList data={this.state.data} />
        <AccountForm onAccountSubmit={this.handleAddAcount}/>
      </div>
    );
  }
});

var AccountList = React.createClass({
  render: function() {
    var accountNodes = this.props.data.map(function (account) {
      return (
              <div>
        <Account key={account.id} name={account.Name} role={account.Role}>          
        </Account>    
        </div>
      );
    });

    return (
      <div className="container">
        {accountNodes}
      </div>
    );
  }
});

var AccountForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var email = React.findDOMNode(this.refs.email).value.trim();
    var name = React.findDOMNode(this.refs.name).value.trim();
    var password = React.findDOMNode(this.refs.password).value.trim();
    if (!name || !password) {
      return;
    }

    this.props.onAccountSubmit({email: email, name: name, password: password});

    // clears the form fields
    React.findDOMNode(this.refs.email).value = '';
    React.findDOMNode(this.refs.name).value = '';
    React.findDOMNode(this.refs.password).value = '';
    return;
  },
  render: function() {
    return (
      <form className="accountForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Your email" ref="email" />
        <input type="text" placeholder="Your name" ref="name" />
        <input type="text" placeholder="your password" ref="password" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});

var Account = React.createClass({
  render: function() {
    var rawMarkup = this.props.name;

    return (
            <div className="row">
                <div className="col-md-1"><b>{this.props.name}</b></div>     
                <div className="col-md-5">{this.props.role}</div> 
                <div className="col-md-2"></div>
                <div className="col-md-4"></div>
            </div>
            
    );
  }
});


React.render(
  <AccountBox url="/loadAccounts" pollInterval={20000}/>,
  document.getElementById('accountNode'));