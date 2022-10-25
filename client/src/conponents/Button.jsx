import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";

function BasicExample() {
  return (
    <ButtonGroup aria-label="Basic example">
      <Button variant="primary">Left</Button>
      <Button variant="secondary">Middle</Button>
      <Button variant="secondary">Right</Button>
      <Button variant="secondary">Right1</Button>
    </ButtonGroup>
  );
}

export default BasicExample;
