import React from 'react';
import { View, StyleSheet } from 'react-native';
import Cell from './Cell';

const Board = ({ boardData, onCellChange, onCellPress, isEditable = true }) => {
    return (
        <View style={styles.container}>
            {boardData.map((row, rowIndex) => (
                <View key={rowIndex} style={[
                    styles.row,
                    rowIndex % 3 === 2 && styles.thickBottomBorder
                ]}>
                    {row.map((cell, colIndex) => (
                        <Cell
                            key={colIndex}
                            value={cell}
                            onValueChange={(value) => isEditable && onCellChange(rowIndex, colIndex, value)}
                            onCellPress={() => onCellPress(rowIndex, colIndex)}
                            hasThickRightBorder={colIndex % 3 === 2}
                            isEditable={isEditable}
                        />
                    ))}
                </View>
            ))}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        padding: 10,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        alignSelf: 'center'
    },
    row: {
        flexDirection: 'row'
    },
    thickBottomBorder: {
        borderBottomWidth: 2
    },
});

export default Board;
