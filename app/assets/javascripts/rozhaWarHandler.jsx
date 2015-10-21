var CommentBox = React.createClass({
  loadCommentsFromServer: function() {
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
  handleCommentSubmit: function(comment) {
    var commentUrl = "/addReservation?approvedBy=" + comment.approvedBy + "&name=" + comment.name + "&number=" + comment.number;

    $.ajax({
      url: commentUrl,
      dataType: 'json',
      type: 'POST',
      data: comment,
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },
  delc: function(commentId){
    $.ajax({
      url: this.props.url,
      data: {"id" : commentId},
      type: 'DELETE',
      dataType: 'json',
      success: function (data) {
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
    this.loadCommentsFromServer();
    setInterval(this.loadCommentsFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="commentBox">
        <h1>Reservations</h1>        
        <CommentList data={this.state.data} del={this.delc} />
        <CommentForm onCommentSubmit={this.handleCommentSubmit} />
      </div>
    );
  }
});

var CommentList = React.createClass({
  handleDelete: function(commentId){
    return this.props.del(commentId);
  },
  render: function() {
    var commentNodes = this.props.data.map(function (comment) {
      return (
        <Comment key={comment.id} approvedBy={comment.ApprovedBy} number={comment.Number} name={comment.Name} onDelete = {this.handleDelete}>          
        </Comment>        
      );
    }.bind(this));

    return (
      <div className="container">
        {commentNodes}
      </div>
    );
  }
});

var CommentForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var approvedBy = React.findDOMNode(this.refs.approvedBy).value.trim();
    var name = React.findDOMNode(this.refs.name).value.trim();
    var number = React.findDOMNode(this.refs.number).value.trim();
    if (!name || !approvedBy) {
      return;
    }

   this.props.onCommentSubmit({approvedBy: approvedBy, name: name, number: number});
    
    // clears the form fields
    React.findDOMNode(this.refs.approvedBy).value = '';
    React.findDOMNode(this.refs.name).value = '';
    React.findDOMNode(this.refs.number).value = '';
    return;
  },
  render: function() {
    return (
      <form className="commentForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Your name" ref="approvedBy" />
        <input type="text" placeholder="Clan Name" ref="name" />
        <input type="text" placeholder="Enemy number" ref="number" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});

var Comment = React.createClass({
  handleClick: function(e){
    e.preventDefault();
    var commentId = this.props.key;
    return this.props.onDelete(commentId);
  },
  render: function() {
    var rawMarkup = this.props.name;

    return (
            <div className="row">
                <div className="col-md-3">{this.props.name}</div>     
                <div className="col-md-2">{this.props.number}</div> 
                <div className="col-md-3">{this.props.approvedBy}</div>
                <div className="col-md-4"><a onClick={this.handleClick}>some text</a></div>
            </div>
    );
  }
});

React.render(
  <CommentBox url="/loadWar" pollInterval={20000} />,
  document.getElementById('warNode'));