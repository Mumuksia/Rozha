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
    return {data: []};
  },
  componentDidMount: function() {
    this.loadCommentsFromServer();
    setInterval(this.loadCommentsFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="warInfoBox">
        <h4>Current War</h4>        
        <span data={this.state.data.name} />
      </div>
    );
  }
});


React.render(
  <WarInfoBox url="/loadCurrentWarInfo" pollInterval={20000} />,
  document.getElementById('currentWarNode'));