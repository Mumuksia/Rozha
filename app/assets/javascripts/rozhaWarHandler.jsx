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
        <CommentList data={this.state.data} />
        <CommentForm />
      </div>
    );
  }
});

var CommentList = React.createClass({
  render: function() {
    var commentNodes = this.props.data.map(function (comment) {
      return (
        <Comment key={comment.id} approvedBy={comment.ApprovedBy} number={comment.Number} name={comment.Name}>          
        </Comment>        
      );
    });

    return (
      <div className="commentList"> 
      <span>test</span>
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

    var commentUrl = "http://localhost:9000/addReservation?approvedBy=" + approvedBy + "&name=" + name + "&number=" + number;
    $.ajax({
          url: commentUrl,
          method: 'POST',
          dataType: 'json',
          cache: false,
          success: function(data) {
            console.log(data)
          }.bind(this),
          error: function(xhr, status, err) {
            console.error(commentUrl, status, err);
          }.bind(this)
        });

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
  render: function() {
    var rawMarkup = this.props.name;

    return (
            <div class="row">
                <div class="col-md-4">{this.props.name}</div>     
                <div class="col-md-4">{this.props.number}</div> 
                <div class="col-md-4">{this.props.approvedBy}</div>
            </div>
    );
  }
});

React.render(
  <CommentBox url="http://localhost:9000/loadWar" pollInterval={20000} />,
  document.getElementById('warNode'));