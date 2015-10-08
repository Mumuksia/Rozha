var Rozha = React.createClass({
  loadDataFromServer: function() {
    $.ajax({
      url: this.props.url,
      dataType: 'json',
      cache: false,
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
    this.loadDataFromServer();
    setInterval(this.loadDataFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="mainBox">        
        <RozhaTabs data={this.state.data} />        
      </div>
    );
  }
});

var RozhaTabs = React.createClass({
    render: function(){
        return(
                    <div className="mainBox">        
                    {this.props.data}      
      </div>  
                );
    }
});

React.render(<Rozha url="http://localhost:9000/loadData" pollInterval={2000} />, rozhaNode);