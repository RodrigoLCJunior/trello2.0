import React, { useEffect, useState } from 'react';
import axios from 'axios';

const TaskList = ({ boardId }) => {
    const [taskLists, setTaskLists] = useState([]);

    useEffect(() => {
        const fetchTaskLists = async () => {
            const response = await axios.get(`http://localhost:8080/api/boards/${boardId}/tasklists`);
            setTaskLists(response.data);
        };

        fetchTaskLists();
    }, [boardId]);

    return (
        <div>
            <h2>Task Lists</h2>
            <ul>
                {taskLists.map(taskList => (
                    <li key={taskList.id}>{taskList.titulo}</li>
                ))}
            </ul>
        </div>
    );
};

export default TaskList;
