import React, { useEffect, useState } from 'react';
import { View, Text, Modal, TouchableOpacity } from 'react-native';
import Board from '../components/Board';
import { getRandomBoardFromStorage } from '../storage';

export default function PlayScreen({ route }) {
    const { difficulty, mode } = route.params;
    const [board, setBoard] = useState(null);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedCell, setSelectedCell] = useState({ row: null, col: null });

    useEffect(() => {
        async function loadRandomBoard() {
            const randomBoard = await getRandomBoardFromStorage(difficulty);
            if(randomBoard !== null) {
                setBoard(randomBoard.board);
            } else {
                console.warn('Random board returned is null');
                // Handle it further as needed.
            }

        }
        loadRandomBoard();
    }, [difficulty]);

    const handleCellChange = (row, col, value) => {
        const newBoard = board.map(row => row.slice());
        newBoard[row][col] = value;
        setBoard(newBoard);
    };

    const handleCellPress = (row, col) => {
        setSelectedCell({ row, col });
        setIsModalVisible(true);
    };

    const handleNumberSelect = (number) => {
        if (selectedCell.row !== null && selectedCell.col !== null) {
            handleCellChange(selectedCell.row, selectedCell.col, number === 'clear' ? null : number);
        }
        setIsModalVisible(false);
    };

    const isBoardSolved = (board) => {
        for (let i = 0; i < 9; i++) {
            let rowSet = new Set();
            let colSet = new Set();
            let gridSet = new Set();

            for (let j = 0; j < 9; j++) {
                // Check if any cell is empty
                if (board[i][j] === null) {
                    return false;
                }

                // Check Rows
                if (rowSet.has(board[i][j])) {
                    return false;
                }
                rowSet.add(board[i][j]);

                // Check Columns
                if (colSet.has(board[j][i])) {
                    return false;
                }
                colSet.add(board[j][i]);

                // Check 3x3 grid
                let rowIndex = 3 * (i % 3);
                let colIndex = 3 * Math.floor(i / 3);
                let gridValue = board[rowIndex + j % 3][colIndex + Math.floor(j / 3)];
                if (gridSet.has(gridValue)) {
                    return false;
                }
                gridSet.add(gridValue);
            }
        }

        return true;
    };


    const handleSubmitBoard = () => {
        if (isBoardSolved(board)) {
            alert("Congratulations! The board is solved correctly.");
        } else {
            alert("Oops! There seems to be an error in your solution.");
        }
    };



    const renderModal = () => {
        return (
            <Modal
                transparent={true}
                animationType="slide"
                visible={isModalVisible}
                onRequestClose={() => setIsModalVisible(false)}
            >
                <View style={styles.modal}>
                    <View style={styles.modalGrid}>
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((number) => (
                            <TouchableOpacity
                                key={number}
                                style={styles.modalItem}
                                onPress={() => handleNumberSelect(number)}
                            >
                                <Text style={styles.modalNumber}>{number}</Text>
                            </TouchableOpacity>
                        ))}
                        <TouchableOpacity
                            style={{ ...styles.modalItem, backgroundColor: 'red' }} // You can style it differently
                            onPress={() => handleNumberSelect('clear')}
                        >
                            <Text style={styles.modalNumber}>X</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </Modal>
        );
    };

    return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Text>This will be the Sudoku play area.</Text>
            { board &&
                <Board boardData={board} onCellChange={handleCellChange} onCellPress={handleCellPress} isEditable={mode === 'create'}
                />
            }
            { renderModal() }
            <TouchableOpacity onPress={handleSubmitBoard}>
                <Text>Submit Board</Text>
            </TouchableOpacity>
        </View>
    );
}

const styles = {
    modal: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    modalGrid: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        width: 200,
        backgroundColor: 'white',
    },
    modalItem: {
        width: 60,
        height: 60,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 10
    },
    modalNumber: {
        fontSize: 24
    }
};
