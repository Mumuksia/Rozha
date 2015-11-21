var App = React.createClass({
    render: function() {
        return (
            <div class="container">
<table className="table table-stripped">
    <thead>
      <tr>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>Email</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>todo</td>
        <td>todo</td>
        <td>todo</td>
      </tr>
    </tbody>
  </table>
            </div>
        
        );
    }
});
 
React.render(<App />, document.getElementById('statisticsNode'));