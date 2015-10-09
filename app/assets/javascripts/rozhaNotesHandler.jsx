var NotesBox = React.createClass({
  render: function() {
    return (
      <div className="commentBox">
        <h1>Notes</h1>
      </div>
    );
  }
});


React.render(
  <NotesBox />,
  document.getElementById('notesNode'));