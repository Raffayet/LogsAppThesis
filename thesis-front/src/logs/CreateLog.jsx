import React from 'react'
import { LOG_TYPES } from '../utils/logTypes';
import axios from 'axios';
import { SERVER_PATH } from '../utils/environment';

const CONTEXT_PATH = 'logging';

const CreateLog = () => {

    const createLog = async (logType) => {
        axios
            .post(SERVER_PATH + CONTEXT_PATH + `/create-log/?logType=${logType}`)
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
      };

    return (
    <div>
        {LOG_TYPES.map((logType, id) => <button key={id} onClick={() => createLog(logType)}>{logType}</button>)}
    </div>
    )
}

export default CreateLog