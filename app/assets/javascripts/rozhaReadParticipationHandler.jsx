var ParticipantBox = React.createClass({
  loadParticipantsFromServer: function() {
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
  handleParticipantSubmit: function(participant) {
    var participantUrl = "/addParticipant?name=" + participant.name;

    $.ajax({
      url: participantUrl,
      dataType: 'json',
      type: 'POST',
      data: participant,
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
    this.loadParticipantsFromServer();
    setInterval(this.loadParticipantsFromServer, this.props.pollInterval);
  },
  render: function() {
    return (
      <div className="wishesBox">
        <h1>Хочу в кв</h1>        
        <ParticipantList data={this.state.data} />
        <ParticipantForm onParticipantSubmit={this.handleParticipantSubmit}/>
      </div>
    );
  }
});

var ParticipantList = React.createClass({
  render: function() {
    var participantNodes = this.props.data.map(function (participant) {
      return (
              <div>
        <Participant key={participant.id} name={participant.Name}>          
        </Participant>    
        </div>
      );
    });

    return (
      <div className="container">
        {participantNodes}
      </div>
    );
  }
});

var ParticipantForm = React.createClass({
  handleSubmit: function(e) {
    e.preventDefault();
    var name = React.findDOMNode(this.refs.name).value.trim();
    if (!name) {
      return;
    }

    this.props.onParticipantSubmit({name: name});

    React.findDOMNode(this.refs.name).value = '';
    return;
  },
  render: function() {
    return (
      <form className="participantForm" onSubmit={this.handleSubmit}>
        <input type="text" placeholder="Clan Name" ref="name" />
        <input type="submit" value="Post" />
      </form>
    );
  }
});

var Participant = React.createClass({
  render: function() {
    var rawMarkup = this.props.name;

    return (
            <div className="row">
                <div className="col-md-3">{this.props.name}</div>     
                <div className="col-md-2"></div> 
                <div className="col-md-3"></div>
                <div className="col-md-4"></div>
            </div>
            
    );
  }
});

React.render(
  <ParticipantBox url="/loadParticipants" pollInterval={20000} />,
  document.getElementById('participationNode'));