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
  render: function() {
    return (
      <div className="warInfoBox">     
        <h3> {this.state.data.Name}</h3>
        <h3> {this.state.data.Note}</h3>
      </div>
    );
  }
});


React.render(
  <WarInfoBox url="/loadCurrentWarInfo" pollInterval={20000} />,
  document.getElementById('currentWarNode'));