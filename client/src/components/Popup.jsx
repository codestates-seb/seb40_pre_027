import React, { useState } from 'react';
import Modal from 'react-modal';

function Title() {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  return (
    <>
      <button onClick={() => setModalIsOpen(true)}>Modal Open</button>
      <Modal isOpen={modalIsOpen} onRequestClose={() => setModalIsOpen(false)}>
        This is Modal content
      </Modal>
    </>
  );
}

export default Title;