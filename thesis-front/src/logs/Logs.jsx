import React, { useState, useEffect } from 'react';
import * as SockJS from "sockjs-client";
import { WEB_SOCKET_PATH } from '../utils/environment';
import Stomp from 'webstomp-client';
import LogsTable from './logs-table/LogsTable';
import './logs.css'

const Logs = () => {
    const [logMessages, setLogMessages] = useState([]);
    const [stompClient, setStompClient] = useState(null);

    useEffect(() => {
        const socket = new SockJS(WEB_SOCKET_PATH); // Replace with your WebSocket URL
        const stomp = Stomp.over(socket);
        stomp.connect({}, () => {
            setStompClient(stomp);
            console.log('WebSocket connected');
        });

        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, []);

    useEffect(() => {
        if (stompClient) {
            const subscription = stompClient.subscribe('/topic/messages', (response) => {
                setLogMessages((prevMessages) => [...prevMessages, JSON.parse(response.body)]);
            });

            // Clean up the subscription when the component unmounts
            return () => {
                if (subscription) {
                    subscription.unsubscribe();
                }
            };
        }
    }, [stompClient]);

  return (
    <div className='logs-container'>
      <h1>Log Messages</h1>
      <LogsTable logMessages={logMessages}></LogsTable>
    </div>
  );
};

export default Logs;



