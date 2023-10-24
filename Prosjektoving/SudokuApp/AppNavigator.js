import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import HomeScreen from './screens/HomeScreen';
import PlaySudokuScreen from './screens/PlaySudokuScreen';
import CreateBoardScreen from "./screens/CreateBoardScreen";
import InstructionsScreen from "./screens/InstructionsScreen";

const Stack = createStackNavigator();

const AppNavigator = () => {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="Home">
                <Stack.Screen name="Home" component={HomeScreen} />
                <Stack.Screen name="PlaySudoku" component={PlaySudokuScreen} />
                <Stack.Screen name="CreateBoard" component={CreateBoardScreen} />
                <Stack.Screen name="Instructions" component={InstructionsScreen} />
            </Stack.Navigator>
        </NavigationContainer>
    );
};

export default AppNavigator;