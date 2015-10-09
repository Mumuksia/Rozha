var AboutBox = React.createClass({
  render: function() {
    return (
      <div className="AboutBox">
        <h1>About</h1>
      </div>
    );
  }
});



React.render(
  <AboutBox />,
  document.getElementById('aboutNode'));