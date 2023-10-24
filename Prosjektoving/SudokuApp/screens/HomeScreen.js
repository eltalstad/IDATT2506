import React from 'react';
import { View, Button } from 'react-native';

export default function HomeScreen({ navigation }) {
    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Button
                title="Play Easy Sudoku"
                onPress={() => navigation.navigate('PlaySudoku', { difficulty: 'easy', mode: 'play' })}
            />
            <Button
                title="Play Medium Sudoku"
                onPress={() => navigation.navigate('PlaySudoku', { difficulty: 'medium', mode: 'play' })}
            />
            <Button
                title="Play Hard Sudoku"
                onPress={() => navigation.navigate('PlaySudoku', { difficulty: 'hard', mode: 'play' })}
            />
            <Button
                title="Create New Board"
                onPress={() => navigation.navigate('CreateBoard', { mode: 'create' })}
            />
            <Button
                title="Instructions"
                onPress={() => navigation.navigate('Instructions')}
            />
        </View>
    );
}

