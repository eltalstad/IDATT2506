import React, { useEffect } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import AppNavigator from './AppNavigator';
import { initializeStorageWithDefaultBoards } from './storage';

export default function App() {

    useEffect(() => {
        async function initialize() {
            await initializeStorageWithDefaultBoards();
        }
        initialize();
    }, []);

    return (
            <AppNavigator />
    );
}
