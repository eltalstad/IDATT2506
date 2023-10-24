import React, { useState } from 'react';
import {View, Text, Button, Alert, TouchableWithoutFeedback, Keyboard} from 'react-native';
import Board from '../components/Board';
import sudoku from '../sudoku.js';
import {addBoardToStorage} from "../storage";

export default function CreateBoardScreen({ route }) {
    const { mode } = route.params;
    const [board, setBoard] = useState(createEmptyBoard());
    const [difficulty, setDifficulty] = useState('easy');

    function createEmptyBoard() {
        let board = [];
        for (let i = 0; i < 9; i++) {
            board.push(new Array(9).fill(0));
        }
        return board;
    }

    function generateBoard() {
        const generatedBoard = sudoku.generate(difficulty);
        const newBoard = [];
        for (let i = 0; i < 9; i++) {
            newBoard.push(generatedBoard.slice(i * 9, (i + 1) * 9).split('').map(Number));
        }
        setBoard(newBoard);
    }

    const handleCellChange = (row, col, value) => {
        const newBoard = [...board];
        newBoard[row][col] = parseInt(value, 10) || 0; // Convert string to number or zero
        setBoard(newBoard);
    };

    const isSafe = (board, row, col, num) => {
        for (let x = 0; x < 9; x++) {
            if (board[row][x] === num || board[x][col] === num ||
                board[3 * Math.floor(row / 3) + Math.floor(x / 3)][3 * Math.floor(col / 3) + x % 3] === num) {
                return false;
            }
        }
        return true;
    };

    const validateBoard = (board) => {
        // Function to check if the provided set of numbers is valid
        const isValidSet = (arr) => {
            const filteredArr = arr.filter(num => num !== 0);
            return new Set(filteredArr).size === filteredArr.length;
        };

        // Validate rows and columns
        for (let i = 0; i < 9; i++) {
            if (!isValidSet(board[i]) || // validate rows
                !isValidSet(board.map(row => row[i]))) { // validate columns
                return false;
            }
        }

        // Validate 3x3 boxes
        for (let boxRow = 0; boxRow < 9; boxRow += 3) {
            for (let boxCol = 0; boxCol < 9; boxCol += 3) {
                const box = [
                    board[boxRow][boxCol], board[boxRow][boxCol + 1], board[boxRow][boxCol + 2],
                    board[boxRow + 1][boxCol], board[boxRow + 1][boxCol + 1], board[boxRow + 1][boxCol + 2],
                    board[boxRow + 2][boxCol], board[boxRow + 2][boxCol + 1], board[boxRow + 2][boxCol + 2],
                ];
                if (!isValidSet(box)) {
                    return false;
                }
            }
        }

        return true;
    };



    const solveSudoku = (board) => {
        let row = -1;
        let col = -1;
        let isEmpty = true;

        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                if (board[i][j] === 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (let num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board)) {
                    return true;
                }
                board[row][col] = 0;  // backtrack
            }
        }

        return false;  // triggers backtracking
    };

    function saveBoard() {
        if (!validateBoard(board)) {
            Alert.alert("Error", "The board is invalid (e.g. duplicates in a row/column/box) and won't be saved.");
            return;
        }

        if (!solveSudoku(board.map(row => row.slice()))) {
            Alert.alert("Error", "The board is not solvable and won't be saved.");
            return;
        }

        console.log('Saving board:', board, 'with difficulty:', difficulty);
        addBoardToStorage(difficulty, board);
    }


    return (
        <TouchableWithoutFeedback onPress={Keyboard.dismiss} accessible={false}>
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <Text>Create your custom Sudoku board:</Text>
                <Board
                    boardData={board}
                    onCellChange={handleCellChange}
                    isEditable={mode === 'create'}
                />

                <Text>Set Difficulty:</Text>
                <View style={{ flexDirection: 'row', marginVertical: 10 }}>
                    <Button title="Easy" onPress={() => setDifficulty('easy')} />
                    <Button title="Medium" onPress={() => setDifficulty('medium')} />
                    <Button title="Hard" onPress={() => setDifficulty('hard')} />
                </View>

                <Button title="Generate Board" onPress={generateBoard} />
                <Button title="Save Board" onPress={saveBoard} />
            </View>
        </TouchableWithoutFeedback>
    );

}
