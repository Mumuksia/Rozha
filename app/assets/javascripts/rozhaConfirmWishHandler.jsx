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
  deleteTask: function(commentId) {
    var deleteUrl = "/deleteWish?id=" + commentId;
    $.ajax({
      url: deleteUrl,
      dataType: 'json',
      type: 'POST',
      data: commentId,
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
        <WishList data={this.state.data} deleteTask={this.deleteTask}/>
      </div>
    );
  }
});

var WishList = React.createClass({
  handleDelete: function(commentId){
    console.log(commentId)
    return this.props.deleteTask(commentId);
  },
  render: function() {
    var wishNodes = this.props.data.map(function (wish) {
      return (
         <div>
        <Wish keyId={wish.id} name={wish.Name} number={wish.Number} address={wish.RemoteAddress} onDelete={this.handleDelete}>          
        </Wish> 
        </div>
      );
    }.bind(this));

    return (
      <div className="container">
        {wishNodes}
      </div>
    );
  }
});

var Wish = React.createClass({
  handleClick: function(e, keyId){        
    var commentId = this.props.keyId;
    return this.props.onDelete(commentId);
  },
  render: function() {    
    var rawMarkup = this.props.name;
    return (
            <div className="row">                
                <div className="col-md-3"><b>{this.props.name}</b></div>     
                <div className="col-md-1">{this.props.number}</div> 
                <div className="col-md-2">{this.props.address}</div> 
                <div className="col-md-2"><a onClick={this.handleClick.bind(null, this.props.keyId)}>delete</a></div>
                <div className="col-md-4"></div>
            </div>
            
    );
  }
});

React.render(
  <WishBox url="/loadWishes" pollInterval={20000} />,
  document.getElementById('wishNode'));