var WarInfoBox = React.createClass({
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
    return {data: {}};
  },
  componentDidMount: function() {
    this.loadCommentsFromServer();
    setInterval(this.loadCommentsFromServer, this.props.pollInterval);
  },
  handleSubmit: function(e) {
    e.preventDefault();
    var warName = React.findDOMNode(this.refs.warName).value.trim();
    var note = React.findDOMNode(this.refs.note).value.trim();

    var startWarUrl = "/startWar?name=" + warName + "&note=" + note;

    $.ajax({
      url: startWarUrl,
      dataType: 'json',
      type: 'POST',
      success: function(data) {
        this.setState({data: data});
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });

    return;
  },
  render: function() {
    return (
      <form className="commentForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Clan War Name" ref="warName" />
        <input type="text" placeholder="Clan War Note" ref="note" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});


React.render(
  <WarInfoBox url="/loadCurrentWarInfo" pollInterval={20000} />,
  document.getElementById('currentWarNodeEdit'));