import "./App.css";
import Nav from "./conponents/home/Nav";
import TagsBox from "./conponents/home/TagsBox";
import BasicExample from "./conponents/Button";

function App() {
  return (
    <div className="App">
      <BasicExample></BasicExample>
      <Nav></Nav>
      <TagsBox></TagsBox>
    </div>
  );
}

export default App;
