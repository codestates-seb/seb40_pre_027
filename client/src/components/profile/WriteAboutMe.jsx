import React from 'react'
import { Editor } from '@toast-ui/react-editor';

function WriteAboutMe() {
  return (
    <div>
        <Editor
            initialValue=" "
            height="300px"
            initialEditType="markdown"
            useCommandShortcut={true}
        >
        <div>About Me</div>

        </Editor>
    </div>
  )
}

export default WriteAboutMe