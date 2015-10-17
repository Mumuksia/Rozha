var WishBox = React.createClass({
  loadWishesFromServer: function() {
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
  getInitialState: function() {
    return {data: []};
  },
  componentDidMount: function() {
    this.loadWishesFromServer();
    setInterval(this.loadWishesFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="wishesBox">
        <h1>Wishes</h1>        
        <WishList data={this.state.data} />
        <WishForm onWishSubmit={this.handleWishSubmit}/>
      </div>
    );
  }
});

var WishList = React.createClass({
  render: function() {
    var wishNodes = this.props.data.map(function (wish) {
      return (
              <div>
        <Wish key={wish.id} name={wish.Name} number={wish.Number}>          
        </Wish>    
        </div>
      );
    });

    return (
      <div className="container">
        {wishNodes}
      </div>
    );
  }
});

var WishForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var name = React.findDOMNode(this.refs.name).value.trim();
    var number = React.findDOMNode(this.refs.number).value.trim();
    if (!name || !number) {
      return;
    }

    this.props.onWishSubmit({name: name, number: number});

    React.findDOMNode(this.refs.name).value = '';
    React.findDOMNode(this.refs.number).value = '';
    return;
  },
  render: function() {
    return (
      <form className="wishForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Clan Name" ref="name" />
        <input type="text" placeholder="Wish Number" ref="number" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});

var Wish = React.createClass({
  render: function() {
    var rawMarkup = this.props.name;

    return (
            <div className="row">
                <div className="col-md-3">{this.props.name}</div>     
                <div className="col-md-2">{this.props.number}</div> 
                <div className="col-md-3"></div>
                <div className="col-md-4"></div>
            </div>
            
    );
  }
});

React.render(
  <WishBox url="/loadWishes" pollInterval={20000} />,
  document.getElementById('wishNode'));