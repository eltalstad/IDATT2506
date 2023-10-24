import AsyncStorage from '@react-native-async-storage/async-storage';

const DEFAULT_BOARDS = {
    easy: [
        {
            board: [
                [0, 0, 0, 2, 6, 0, 7, 0, 1],
                [6, 8, 0, 0, 7, 0, 0, 9, 0],
                [1, 9, 0, 0, 0, 4, 5, 0, 0],
                [8, 2, 0, 1, 0, 0, 0, 4, 0],
                [0, 0, 4, 6, 0, 2, 9, 0, 0],
                [0, 5, 0, 0, 0, 3, 0, 2, 8],
                [0, 0, 9, 3, 0, 0, 0, 7, 4],
                [0, 4, 0, 0, 5, 0, 0, 3, 6],
                [7, 0, 3, 0, 1, 8, 0, 0, 0]
            ],
}],
    medium: [
        {
            board: [
                [0, 2, 0, 6, 0, 8, 0, 0, 0],
                [5, 8, 0, 0, 0, 9, 7, 0, 0],
                [0, 0, 0, 0, 4, 0, 0, 0, 0],
                [3, 7, 0, 0, 0, 0, 5, 0, 0],
                [6, 0, 0, 0, 0, 0, 0, 0, 4],
                [0, 0, 8, 0, 0, 0, 0, 1, 3],
                [0, 0, 0, 0, 2, 0, 0, 0, 0],
                [0, 0, 9, 8, 0, 0, 0, 3, 6],
                [0, 0, 0, 3, 0, 6, 0, 9, 0]
            ],
        },
        ],
    hard: [
        {
            board: [
                [0, 0, 0, 6, 0, 0, 4, 0, 0],
                [7, 0, 0, 0, 0, 3, 6, 0, 0],
                [0, 0, 0, 0, 9, 1, 0, 8, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 5, 0, 1, 8, 0, 0, 0, 3],
                [0, 0, 0, 3, 0, 6, 0, 4, 5],
                [0, 4, 0, 2, 0, 0, 0, 6, 0],
                [9, 0, 3, 0, 0, 0, 0, 0, 0],
                [0, 2, 0, 0, 0, 0, 1, 0, 0]
            ],
        },
    ],
}

export const initializeStorageWithDefaultBoards = async () => {
    try {
        const existingBoards = await AsyncStorage.getItem('sudokuBoards');
        if (!existingBoards) {
            await AsyncStorage.setItem('sudokuBoards', JSON.stringify(DEFAULT_BOARDS));
        }
    } catch (error) {
        console.error('Failed to initialize default boards:', error);
    }
};

export const addBoardToStorage = async (difficulty, board ) => {
    try {
        const existingBoards = JSON.parse(await AsyncStorage.getItem('sudokuBoards')) || {};
        if (!existingBoards[difficulty]) {
            existingBoards[difficulty] = [];
        }
        existingBoards[difficulty].push({ board });
        await AsyncStorage.setItem('sudokuBoards', JSON.stringify(existingBoards));
    } catch (error) {
        console.error('Failed to save board:', error);
    }
};

export const getRandomBoardFromStorage = async (difficulty) => {
    try {
        const existingBoards = JSON.parse(await AsyncStorage.getItem('sudokuBoards')) || {};
        const boardsOfDifficulty = existingBoards[difficulty];
        if (boardsOfDifficulty && boardsOfDifficulty.length > 0) {
            const randomIndex = Math.floor(Math.random() * boardsOfDifficulty.length);
            return boardsOfDifficulty[randomIndex];
        }
        return null; // Handle the case where no board exists for the selected difficulty
    } catch (error) {
        console.error('Failed to fetch board:', error);
        return null;
    }
};
